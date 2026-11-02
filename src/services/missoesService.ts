import { apiRequest, expectArray } from './api'
import type { Missao, MissaoPayload } from '../types/api'

const RESOURCE = '/missoes'

export async function listarMissoes(signal?: AbortSignal): Promise<Missao[]> {
  return expectArray<Missao>(await apiRequest<unknown>(RESOURCE, { signal }))
}

export function buscarMissao(id: number, signal?: AbortSignal): Promise<Missao> {
  return apiRequest<Missao>(`${RESOURCE}/${id}`, { signal })
}

/** Resolve `undefined` se a API responder 201 sem corpo. */
export function criarMissao(payload: MissaoPayload): Promise<Missao | undefined> {
  return apiRequest<Missao | undefined>(RESOURCE, { method: 'POST', body: payload })
}

export async function atualizarMissao(id: number, payload: MissaoPayload): Promise<Missao> {
  const atualizada = await apiRequest<Missao | undefined>(`${RESOURCE}/${id}`, { method: 'PUT', body: payload })
  return atualizada ?? { id, ...payload }
}

export function excluirMissao(id: number): Promise<void> {
  return apiRequest<void>(`${RESOURCE}/${id}`, { method: 'DELETE' })
}
