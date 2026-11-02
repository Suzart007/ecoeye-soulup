import { useCallback, useEffect, useState, type DependencyList } from 'react'
import { ApiError } from '../services/api'
import type { RequestState } from '../types/api'

/**
 * Executa uma requisição à API sempre que `deps` mudar e expõe o estado
 * (loading, erro ou sucesso). A requisição é cancelada se o componente
 * desmontar antes da resposta chegar.
 */
export function useApi<T>(request: (signal: AbortSignal) => Promise<T>, deps: DependencyList) {
  const [state, setState] = useState<RequestState<T>>({ status: 'loading' })
  const [attempt, setAttempt] = useState(0)

  useEffect(() => {
    const controller = new AbortController()
    setState({ status: 'loading' })

    request(controller.signal)
      .then((data) => {
        if (!controller.signal.aborted) setState({ status: 'success', data })
      })
      .catch((error: unknown) => {
        if (!controller.signal.aborted) setState({ status: 'error', error: ApiError.from(error) })
      })

    return () => controller.abort()
  }, [...deps, attempt])

  const retry = useCallback(() => setAttempt((current) => current + 1), [])

  /** Atualiza os dados localmente (ex.: remover um item excluído) sem refazer a requisição. */
  const updateData = useCallback((updater: (data: T) => T) => {
    setState((current) => (current.status === 'success' ? { status: 'success', data: updater(current.data) } : current))
  }, [])

  return { state, retry, updateData }
}
