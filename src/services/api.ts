import type { ApiErrorKind } from '../types/api'

/** URL base da API Java, definida em VITE_API_URL (.env.local ou painel da Vercel). */
export const API_URL = (import.meta.env.VITE_API_URL || 'http://localhost:8080').trim().replace(/\/+$/, '')

/** Na Vercel o container da API "dorme" sem uso; a primeira chamada pode levar alguns segundos a mais. */
const TIMEOUT_MS = 30000

type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE'

interface RequestOptions {
  method?: HttpMethod
  body?: unknown
  signal?: AbortSignal
}

const DEFAULT_MESSAGES: Record<ApiErrorKind, string> = {
  network: 'Não foi possível conectar à API do SoulUp. Verifique sua conexão ou tente novamente em instantes.',
  timeout: 'A API demorou demais para responder (ela pode estar iniciando). Tente novamente em alguns segundos.',
  not_found: 'O registro solicitado não foi encontrado.',
  validation: 'A API recusou os dados enviados. Revise os campos e tente novamente.',
  conflict: 'Já existe um registro com esses dados (por exemplo, o mesmo e-mail).',
  server: 'O servidor encontrou um erro ao processar a solicitação. Tente novamente mais tarde.',
  unexpected: 'A API retornou uma resposta inesperada.',
}

export class ApiError extends Error {
  readonly kind: ApiErrorKind
  readonly status?: number

  constructor(kind: ApiErrorKind, message = DEFAULT_MESSAGES[kind], status?: number) {
    super(message)
    this.name = 'ApiError'
    this.kind = kind
    this.status = status
  }

  /** Converte qualquer falha capturada em um ApiError com mensagem amigável. */
  static from(error: unknown): ApiError {
    return error instanceof ApiError ? error : new ApiError('unexpected')
  }
}

function kindFromStatus(status: number): ApiErrorKind {
  if (status === 404) return 'not_found'
  if (status === 409) return 'conflict'
  if (status === 400 || status === 422) return 'validation'
  if (status >= 500) return 'server'
  return 'unexpected'
}

/** Lê a mensagem enviada pelo back-end ({ message }, { mensagem }, { erro } ou texto puro). */
async function readServerMessage(response: Response): Promise<string | undefined> {
  const text = await response.text().catch(() => '')
  if (!text) return undefined

  try {
    const body: unknown = JSON.parse(text)
    if (typeof body === 'object' && body !== null) {
      const { message, mensagem, erro, error } = body as Record<string, unknown>
      const found = [mensagem, message, erro, error].find((value) => typeof value === 'string' && value.trim())
      return found as string | undefined
    }
  } catch {
    // não era JSON: usa o texto se for uma frase curta (e não uma página HTML de erro)
  }

  return text.length <= 200 && !text.trimStart().startsWith('<') ? text : undefined
}

async function toApiError(response: Response): Promise<ApiError> {
  const kind = kindFromStatus(response.status)
  // Mensagens do servidor só são exibidas em erros 4xx; erros 5xx costumam trazer detalhes técnicos.
  const serverMessage = response.status < 500 ? await readServerMessage(response) : undefined
  return new ApiError(kind, serverMessage ?? DEFAULT_MESSAGES[kind], response.status)
}

/**
 * Faz uma requisição HTTP à API e devolve o corpo já convertido de JSON.
 * Respostas sem corpo (ex.: 204 No Content) resolvem como `undefined`.
 */
export async function apiRequest<T>(path: string, { method = 'GET', body, signal }: RequestOptions = {}): Promise<T> {
  const controller = new AbortController()
  let timedOut = false
  const timeoutId = setTimeout(() => {
    timedOut = true
    controller.abort()
  }, TIMEOUT_MS)

  const abortFromCaller = () => controller.abort()
  if (signal?.aborted) controller.abort()
  signal?.addEventListener('abort', abortFromCaller)

  try {
    const response = await fetch(`${API_URL}${path}`, {
      method,
      headers: body === undefined ? { Accept: 'application/json' } : { Accept: 'application/json', 'Content-Type': 'application/json' },
      body: body === undefined ? undefined : JSON.stringify(body),
      signal: controller.signal,
    })

    if (!response.ok) throw await toApiError(response)

    const text = await response.text()
    if (!text) return undefined as T

    try {
      return JSON.parse(text) as T
    } catch {
      throw new ApiError('unexpected', DEFAULT_MESSAGES.unexpected, response.status)
    }
  } catch (error) {
    if (error instanceof ApiError) throw error
    if (timedOut) throw new ApiError('timeout')
    // Cancelamento pedido pelo componente (ex.: saiu da página): repassa o AbortError original.
    if (signal?.aborted) throw error
    throw new ApiError('network')
  } finally {
    clearTimeout(timeoutId)
    signal?.removeEventListener('abort', abortFromCaller)
  }
}

/** Garante que uma listagem veio como array antes de a interface tentar percorrê-la. */
export function expectArray<T>(value: unknown): T[] {
  if (!Array.isArray(value)) {
    throw new ApiError('unexpected', 'A API não retornou uma lista no formato esperado.')
  }
  return value as T[]
}
