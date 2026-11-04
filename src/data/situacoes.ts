import type { BadgeTone } from '../types'
import type { SituacaoMissao, SituacaoUsuario } from '../types/api'

interface SituacaoInfo {
  label: string
  tone: BadgeTone
  description: string
}

export const SITUACOES_MISSAO: SituacaoMissao[] = ['ATIVA', 'INATIVA', 'ENCERRADA']

export const SITUACAO_MISSAO_INFO: Record<SituacaoMissao, SituacaoInfo> = {
  ATIVA: { label: 'Ativa', tone: 'success', description: 'Disponível para os usuários e pode ser sugerida pelo Neo.' },
  INATIVA: { label: 'Inativa', tone: 'neutral', description: 'Pausada temporariamente: não aparece nas sugestões.' },
  ENCERRADA: { label: 'Encerrada', tone: 'info', description: 'Missão sazonal ou finalizada; mantida apenas no histórico.' },
}

export const SITUACOES_USUARIO: SituacaoUsuario[] = ['ATIVO', 'INATIVO', 'BLOQUEADO']

export const SITUACAO_USUARIO_INFO: Record<SituacaoUsuario, SituacaoInfo> = {
  ATIVO: { label: 'Ativo', tone: 'success', description: 'Participa da plataforma e recebe sugestões do Neo.' },
  INATIVO: { label: 'Inativo', tone: 'neutral', description: 'Conta sem uso recente; não recebe novas sugestões.' },
  BLOQUEADO: { label: 'Bloqueado', tone: 'danger', description: 'Acesso suspenso por motivo de segurança.' },
}

/** Protege a interface caso a API devolva uma situação fora do CHECK do banco. */
function fallbackInfo(value: string): SituacaoInfo {
  return { label: value || 'Não informada', tone: 'neutral', description: '' }
}

export function getSituacaoMissaoInfo(situacao: SituacaoMissao): SituacaoInfo {
  return SITUACAO_MISSAO_INFO[situacao] ?? fallbackInfo(situacao)
}

export function getSituacaoUsuarioInfo(situacao: SituacaoUsuario): SituacaoInfo {
  return SITUACAO_USUARIO_INFO[situacao] ?? fallbackInfo(situacao)
}
