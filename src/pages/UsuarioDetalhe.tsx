import { useState, type ReactNode } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import { useApi } from '../hooks/useApi'
import { useIdParam } from '../hooks/useIdParam'
import { useToast } from '../hooks/useToast'
import Button from '../components/common/Button'
import BackLink from '../components/common/BackLink'
import StatusBadge from '../components/common/StatusBadge'
import UsuarioAvatar from '../components/usuarios/UsuarioAvatar'
import LoadingState from '../components/feedback/LoadingState'
import ErrorState from '../components/feedback/ErrorState'
import RedirectNotice from '../components/feedback/RedirectNotice'
import ConfirmDialog from '../components/feedback/ConfirmDialog'
import { ArrowRightIcon, BotIcon, CalendarIcon, ClockIcon, EditIcon, MailIcon, TrashIcon } from '../components/icons/Icons'
import { buscarUsuario, excluirUsuario } from '../services/usuariosService'
import { listarMissoes } from '../services/missoesService'
import { ApiError } from '../services/api'
import { getSituacaoUsuarioInfo } from '../data/situacoes'
import { daysSince, formatApiDate, pluralize } from '../utils/format'
import type { Usuario } from '../types/api'

const MAX_SUGESTOES = 3

/** Rota dinâmica /usuarios/:id — valida o parâmetro antes de consultar a API. */
export default function UsuarioDetalhe() {
  const usuarioId = useIdParam()

  if (usuarioId === null) {
    return (
      <RedirectNotice
        title="Endereço inválido"
        message="O código informado na URL não é um identificador de usuário válido."
        to="/usuarios"
        linkLabel="Ver todos os usuários"
      />
    )
  }

  return <UsuarioDetalheConteudo id={usuarioId} />
}

function UsuarioDetalheConteudo({ id }: { id: number }) {
  const navigate = useNavigate()
  const { showToast } = useToast()
  const { state, retry } = useApi((signal) => buscarUsuario(id, signal), [id])
  const [isConfirmOpen, setIsConfirmOpen] = useState(false)
  const [isDeleting, setIsDeleting] = useState(false)

  useDocumentTitle(state.status === 'success' ? state.data.nome : 'Perfil do usuário')

  if (state.status === 'loading') return <LoadingState message="Carregando perfil do usuário..." />

  if (state.status === 'error') {
    if (state.error.kind === 'not_found') {
      return (
        <RedirectNotice
          title="Usuário não encontrado"
          message={`Não existe nenhum usuário com o código #${id}. Ele pode ter sido removido.`}
          to="/usuarios"
          linkLabel="Ver todos os usuários"
        />
      )
    }

    return (
      <section className="py-16 px-4">
        <ErrorState title="Não foi possível carregar o usuário" message={state.error.message} onRetry={retry}>
          <Button to="/usuarios" variant="secondary">
            Voltar para usuários
          </Button>
        </ErrorState>
      </section>
    )
  }

  const usuario = state.data
  const situacao = getSituacaoUsuarioInfo(usuario.situacao)
  const dias = daysSince(usuario.dataCadastro)

  const handleDelete = async () => {
    setIsDeleting(true)
    try {
      await excluirUsuario(usuario.id)
      showToast(`Usuário "${usuario.nome}" removido com sucesso.`)
      navigate('/usuarios', { replace: true })
    } catch (error) {
      showToast(ApiError.from(error).message, 'error')
      setIsDeleting(false)
      setIsConfirmOpen(false)
    }
  }

  return (
    <>
      <section className="bg-gradient-to-b from-bg-soft to-bg pt-10 pb-12 md:pt-12 md:pb-16">
        <div className="max-w-5xl mx-auto px-4 md:px-8">
          <BackLink to="/usuarios" label="Todos os usuários" />

          <div className="flex flex-col md:flex-row md:items-center gap-6">
            <UsuarioAvatar usuario={usuario} size="lg" />

            <div className="flex-1 min-w-0">
              <div className="flex flex-wrap items-center gap-3 mb-2">
                <span className="text-xs font-semibold uppercase tracking-wide text-primary">Usuário #{usuario.id}</span>
                <StatusBadge label={situacao.label} tone={situacao.tone} />
              </div>
              <h1 className="text-2xl md:text-4xl font-bold leading-tight mb-2 break-words">{usuario.nome}</h1>
              <a
                href={`mailto:${usuario.email}`}
                className="inline-flex items-center gap-2 text-sm text-text hover:text-primary break-all"
              >
                <MailIcon className="w-4 h-4 flex-shrink-0" />
                {usuario.email}
              </a>
            </div>

            <div className="flex flex-col sm:flex-row md:flex-col gap-3 md:flex-shrink-0">
              <Button to={`/usuarios/${usuario.id}/editar`} variant="secondary">
                <EditIcon />
                Editar
              </Button>
              <Button variant="danger" onClick={() => setIsConfirmOpen(true)}>
                <TrashIcon />
                Excluir
              </Button>
            </div>
          </div>
        </div>
      </section>

      <section className="py-12 md:py-16">
        <div className="max-w-5xl mx-auto px-4 md:px-8 space-y-6">
          <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4 md:gap-6">
            <InfoCard icon={<CalendarIcon className="w-5 h-5" />} label="Data de cadastro" value={formatApiDate(usuario.dataCadastro)} />
            <InfoCard
              icon={<ClockIcon className="w-5 h-5" />}
              label="Tempo na plataforma"
              value={dias === null ? 'Não informado' : dias === 0 ? 'Entrou hoje' : pluralize(dias, 'dia', 'dias')}
            />
            <div className="sm:col-span-2 lg:col-span-1 rounded-2xl border border-border bg-white p-5">
              <span className="block text-xs text-text-muted mb-2">Situação da conta</span>
              <StatusBadge label={situacao.label} tone={situacao.tone} />
              <p className="text-sm text-text-muted mt-2">{situacao.description}</p>
            </div>
          </div>

          <SugestoesNeo usuario={usuario} />
        </div>
      </section>

      <ConfirmDialog
        isOpen={isConfirmOpen}
        title="Excluir usuário?"
        description={
          <>
            O cadastro de <strong>{usuario.nome}</strong> será removido da API. Esta ação não pode ser desfeita.
          </>
        }
        confirmLabel="Excluir usuário"
        isProcessing={isDeleting}
        onConfirm={handleDelete}
        onCancel={() => setIsConfirmOpen(false)}
      />
    </>
  )
}

interface InfoCardProps {
  icon: ReactNode
  label: string
  value: string
}

function InfoCard({ icon, label, value }: InfoCardProps) {
  return (
    <div className="flex items-center gap-4 rounded-2xl border border-border bg-white p-5">
      <span className="w-11 h-11 rounded-xl bg-bg-soft text-primary flex items-center justify-center flex-shrink-0">{icon}</span>
      <div>
        <span className="block text-xs text-text-muted">{label}</span>
        <strong className="text-text-dark">{value}</strong>
      </div>
    </div>
  )
}

/**
 * O Neo sugere as missões ativas de maior pontuação.
 * Regra de negócio 10 do banco: usuários inativos ou bloqueados não recebem novas sugestões.
 */
function SugestoesNeo({ usuario }: { usuario: Usuario }) {
  const primeiroNome = usuario.nome.split(' ')[0]

  return (
    <article className="rounded-2xl border border-border bg-white p-6 md:p-8">
      <div className="flex items-center gap-3 mb-5">
        <span className="w-11 h-11 rounded-full bg-bg-soft text-primary flex items-center justify-center flex-shrink-0">
          <BotIcon className="w-5 h-5" />
        </span>
        <div>
          <h2 className="font-semibold text-lg text-text-dark">Sugestões do Neo para {primeiroNome}</h2>
          <p className="text-xs text-text-muted">Missões ativas de maior impacto, buscadas na API</p>
        </div>
      </div>

      {usuario.situacao === 'ATIVO' ? (
        <ListaSugestoes />
      ) : (
        <p className="rounded-xl bg-bg-alt p-4 text-sm text-text leading-relaxed">
          {primeiroNome} está com a conta <strong>{usuario.situacao === 'BLOQUEADO' ? 'bloqueada' : 'inativa'}</strong> e,
          por isso, não recebe novas sugestões do Neo (regra de negócio RN10 do banco de dados). Reative a conta para
          voltar a receber missões.
        </p>
      )}
    </article>
  )
}

function ListaSugestoes() {
  const { state, retry } = useApi((signal) => listarMissoes(signal), [])

  if (state.status === 'loading') return <LoadingState message="O Neo está escolhendo as missões..." />

  if (state.status === 'error') {
    return (
      <div role="alert" className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 rounded-xl bg-danger/5 p-4">
        <p className="text-sm text-danger">{state.error.message}</p>
        <Button variant="secondary" onClick={retry}>
          Tentar novamente
        </Button>
      </div>
    )
  }

  const sugestoes = state.data
    .filter((missao) => missao.situacao === 'ATIVA')
    .sort((a, b) => b.pontuacao - a.pontuacao)
    .slice(0, MAX_SUGESTOES)

  if (sugestoes.length === 0) {
    return <p className="text-sm text-text-muted">Ainda não há missões ativas para sugerir. Cadastre uma nova missão.</p>
  }

  return (
    <ul className="grid md:grid-cols-3 gap-4">
      {sugestoes.map((missao) => (
        <li key={missao.id}>
          <Link
            to={`/missoes/${missao.id}`}
            className="flex h-full flex-col rounded-xl border border-border p-4 hover:border-primary-light hover:bg-bg-soft/40 transition-colors"
          >
            <span className="text-xs font-bold text-primary mb-1">+{missao.pontuacao} pts</span>
            <span className="font-semibold text-text-dark mb-1">{missao.titulo}</span>
            <span className="text-xs text-text-muted line-clamp-2 mb-3">{missao.descricao}</span>
            <span className="mt-auto inline-flex items-center gap-1 text-xs font-semibold text-primary">
              Ver missão <ArrowRightIcon className="w-3.5 h-3.5" />
            </span>
          </Link>
        </li>
      ))}
    </ul>
  )
}
