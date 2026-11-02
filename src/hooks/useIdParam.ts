import { useParams } from 'react-router-dom'

/** Lê o parâmetro `:id` da rota e devolve um número inteiro positivo, ou `null` se for inválido. */
export function useIdParam(): number | null {
  const { id } = useParams<{ id: string }>()
  if (!id || !/^\d+$/.test(id)) return null

  const parsed = Number(id)
  return parsed > 0 ? parsed : null
}
