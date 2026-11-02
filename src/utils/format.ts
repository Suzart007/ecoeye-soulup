import type { ApiDate } from '../types/api'

/** Converte a data recebida da API (string ISO ou array do Jackson) em Date local. */
export function parseApiDate(value: ApiDate | null | undefined): Date | null {
  if (!value) return null

  if (Array.isArray(value)) {
    const [year, month, day] = value
    return new Date(year, month - 1, day)
  }

  const match = /^(\d{4})-(\d{2})-(\d{2})/.exec(value)
  if (!match) return null
  return new Date(Number(match[1]), Number(match[2]) - 1, Number(match[3]))
}

/** Ex.: "2026-01-12" → "12/01/2026". */
export function formatApiDate(value: ApiDate | null | undefined): string {
  const date = parseApiDate(value)
  return date ? date.toLocaleDateString('pt-BR') : 'Data não informada'
}

/** Data de hoje no formato aceito pelo LocalDate do Java (AAAA-MM-DD). */
export function todayIsoDate(): string {
  const now = new Date()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${now.getFullYear()}-${month}-${day}`
}

export function daysSince(value: ApiDate | null | undefined): number | null {
  const date = parseApiDate(value)
  if (!date) return null
  const diff = Date.now() - date.getTime()
  return Math.max(0, Math.floor(diff / (1000 * 60 * 60 * 24)))
}

export function getInitials(name: string): string {
  return name
    .trim()
    .split(/\s+/)
    .slice(0, 2)
    .map((part) => part[0]?.toUpperCase() ?? '')
    .join('')
}

/** Remove acentos e caixa para comparar textos em buscas (ex.: "Água" encontra "agua"). */
export function normalizeText(value: string | null | undefined): string {
  return (value ?? '')
    .normalize('NFD')
    .replace(/[̀-ͯ]/g, '')
    .toLowerCase()
    .trim()
}

/** Ex.: pluralize(1, 'missão', 'missões') → "1 missão". */
export function pluralize(count: number, singular: string, plural: string): string {
  return `${count} ${count === 1 ? singular : plural}`
}
