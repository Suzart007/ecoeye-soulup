import { apiRequest, expectArray } from './api'
import type { Usuario, UsuarioPayload } from '../types/api'

const RESOURCE = '/usuarios'

export async function listarUsuarios(signal?: AbortSignal): Promise<Usuario[]> {
  return expectArray<Usuario>(await apiRequest<unknown>(RESOURCE, { signal }))
}

export function buscarUsuario(id: number, signal?: AbortSignal): Promise<Usuario> {
  return apiRequest<Usuario>(`${RESOURCE}/${id}`, { signal })
}

/** Resolve `undefined` se a API responder 201 sem corpo. */
export function criarUsuario(payload: UsuarioPayload): Promise<Usuario | undefined> {
  return apiRequest<Usuario | undefined>(RESOURCE, { method: 'POST', body: payload })
}

export async function atualizarUsuario(id: number, payload: UsuarioPayload & Pick<Usuario, 'dataCadastro'>): Promise<Usuario> {
  const atualizado = await apiRequest<Usuario | undefined>(`${RESOURCE}/${id}`, { method: 'PUT', body: payload })
  return atualizado ?? { id, ...payload }
}

export function excluirUsuario(id: number): Promise<void> {
  return apiRequest<void>(`${RESOURCE}/${id}`, { method: 'DELETE' })
}
