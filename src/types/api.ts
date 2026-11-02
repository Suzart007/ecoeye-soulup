// Tipos dos recursos expostos pela API Java do SoulUp.
// Os campos seguem o modelo do banco da Sprint 4 (TB_MISSOES e TB_USUARIOS).
import type { ApiError } from '../services/api'

/** Valores aceitos pelo CHECK ck_missoes_situacao. */
export type SituacaoMissao = 'ATIVA' | 'INATIVA' | 'ENCERRADA'

/** Valores aceitos pelo CHECK ck_tb_usuarios_situacao. */
export type SituacaoUsuario = 'ATIVO' | 'INATIVO' | 'BLOQUEADO'

/**
 * O Jackson serializa LocalDate como "2026-01-12" (padrão do Quarkus/Spring)
 * ou como [2026, 1, 12] quando o módulo de datas não está configurado.
 */
export type ApiDate = string | [number, number, number]

/** Todo recurso persistido recebe um identificador gerado pela API. */
export interface ApiEntity {
  id: number
}

/** Corpo enviado no POST/PUT de /missoes. */
export interface MissaoPayload {
  titulo: string
  descricao: string
  /** De 0 a 100 pontos (CHECK ck_missoes_pontos). */
  pontuacao: number
  situacao: SituacaoMissao
}

/** Missão retornada pela API: o payload acrescido do id. */
export type Missao = ApiEntity & MissaoPayload

/** Corpo enviado no POST/PUT de /usuarios. */
export interface UsuarioPayload {
  nome: string
  email: string
  situacao: SituacaoUsuario
  dataCadastro?: ApiDate
}

/** Usuário retornado pela API: o payload com id e data de cadastro obrigatória. */
export type Usuario = ApiEntity & UsuarioPayload & { dataCadastro: ApiDate }

/** Categorias de falha tratadas pela camada de serviço. */
export type ApiErrorKind = 'network' | 'timeout' | 'not_found' | 'validation' | 'conflict' | 'server' | 'unexpected'

/** Estado de uma requisição: só existe `data` quando ela termina com sucesso. */
export type RequestState<T> =
  | { status: 'loading' }
  | { status: 'error'; error: ApiError }
  | { status: 'success'; data: T }
