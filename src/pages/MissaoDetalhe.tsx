import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import { useApi } from '../hooks/useApi'
import { useIdParam } from '../hooks/useIdParam'
import { useToast } from '../hooks/useToast'
import Button from '../components/common/Button'
import BackLink from '../components/common/BackLink'
import StatusBadge from '../components/common/StatusBadge'
import LoadingState from '../components/feedback/LoadingState'
import ErrorState from '../components/feedback/ErrorState'
import RedirectNotice from '../components/feedback/RedirectNotice'
import ConfirmDialog from '../components/feedback/ConfirmDialog'
import { EditIcon, TrashIcon, TrendUpIcon } from '../components/icons/Icons'
import { buscarMissao, excluirMissao } from '../services/missoesService'
import { ApiError } from '../services/api'
import { getSituacaoMissaoInfo } from '../data/situacoes'

type NivelImpacto = 'baixo' | 'medio' | 'alto'

const IMPACTO_INFO: Record<NivelImpacto, { label: string; description: string }> = {
  baixo: { label: 'Impacto inicial', description: 'Boa porta de entrada: o Neo costuma sugeri-la a usuários iniciantes.' },
  medio: { label: 'Impacto moderado', description: 'Exige constância por alguns dias e mantém o usuário engajado.' },
  alto: { label: 'Alto impacto', description: 'Missão desafiadora, com grande ganho de pontos e de impacto ambiental.' },
}

function classificarImpacto(pontuacao: number): NivelImpacto {
  if (pontuacao >= 70) return 'alto'
  if (pontuacao >= 40) return 'medio'
  return 'baixo'
}

/** Rota dinâmica /missoes/:id — valida o parâmetro antes de consultar a API. */
export default function MissaoDetalhe() {
  const missaoId = useIdParam()

  if (missaoId === null) {
    return (
      <RedirectNotice
        title="Endereço inválido"
        message="O código informado na URL não é um identificador de missão válido."
        to="/missoes"
        linkLabel="Ver todas as missões"
      />
    )
  }

  return <MissaoDetalheConteudo id={missaoId} />
}

function MissaoDetalheConteudo({ id }: { id: number }) {
  const navigate = useNavigate()
  const { showToast } = useToast()
  const { state, retry } = useApi((signal) => buscarMissao(id, signal), [id])
  const [isConfirmOpen, setIsConfirmOpen] = useState(false)
  const [isDeleting, setIsDeleting] = useState(false)

  useDocumentTitle(state.status === 'success' ? state.data.titulo : 'Detalhes da missão')

  if (state.status === 'loading') {
    return <LoadingState message="Carregando detalhes da missão..." />
  }

  if (state.status === 'error') {
    if (state.error.kind === 'not_found') {
      return (
        <RedirectNotice
          title="Missão não encontrada"
          message={`Não existe nenhuma missão com o código #${id}. Ela pode ter sido excluída.`}
          to="/missoes"
          linkLabel="Ver todas as missões"
        />
      )
    }

    return (
      <section className="py-16 px-4">
        <ErrorState title="Não foi possível carregar a missão" message={state.error.message} onRetry={retry}>
          <Button to="/missoes" variant="secondary">
            Voltar para missões
          </Button>
        </ErrorState>
      </section>
    )
  }

  const missao = state.data
  const situacao = getSituacaoMissaoInfo(missao.situacao)
  const impacto = IMPACTO_INFO[classificarImpacto(missao.pontuacao)]
  const progresso = Math.min(100, Math.max(0, missao.pontuacao))

  const handleDelete = async () => {
    setIsDeleting(true)
    try {
      await excluirMissao(missao.id)
      showToast(`Missão "${missao.titulo}" excluída com sucesso.`)
      navigate('/missoes', { replace: true })
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
          <BackLink to="/missoes" label="Todas as missões" />

          <div className="flex flex-col md:flex-row md:items-start md:justify-between gap-6">
            <div>
              <div className="flex flex-wrap items-center gap-3 mb-3">
                <span className="text-xs font-semibold uppercase tracking-wide text-primary">Missão #{missao.id}</span>
                <StatusBadge label={situacao.label} tone={situacao.tone} />
              </div>
              <h1 className="text-3xl md:text-4xl font-bold leading-tight mb-3">{missao.titulo}</h1>
              <p className="text-text text-base md:text-lg max-w-2xl leading-relaxed">{missao.descricao}</p>
            </div>

            <div className="flex flex-col sm:flex-row md:flex-col gap-3 md:flex-shrink-0">
              <Button to={`/missoes/${missao.id}/editar`} variant="secondary">
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
        <div className="max-w-5xl mx-auto px-4 md:px-8 grid md:grid-cols-3 gap-6">
          <article className="md:col-span-2 rounded-2xl border border-border bg-white p-6">
            <h2 className="font-semibold text-lg mb-4">Recompensa da missão</h2>
            <div className="flex items-baseline gap-2 mb-3">
              <span className="text-4xl font-extrabold text-primary font-heading">{missao.pontuacao}</span>
              <span className="text-sm text-text-muted">de 100 pontos possíveis</span>
            </div>
            <div
              className="h-3 rounded-full bg-bg-alt overflow-hidden"
              role="progressbar"
              aria-label="Pontuação da missão"
              aria-valuemin={0}
              aria-valuemax={100}
              aria-valuenow={progresso}
            >
              <div className="h-full bg-primary rounded-full" style={{ width: `${progresso}%` }} />
            </div>
            <div className="flex gap-3 mt-5 rounded-xl bg-bg-soft p-4">
              <TrendUpIcon className="w-5 h-5 text-primary flex-shrink-0" />
              <div>
                <h3 className="font-semibold text-sm text-text-dark">{impacto.label}</h3>
                <p className="text-sm text-text-muted">{impacto.description}</p>
              </div>
            </div>
          </article>

          <article className="rounded-2xl border border-border bg-white p-6">
            <h2 className="font-semibold text-lg mb-3">Situação</h2>
            <StatusBadge label={situacao.label} tone={situacao.tone} />
            <p className="text-sm text-text-muted leading-relaxed mt-3">{situacao.description}</p>
          </article>
        </div>
      </section>

      <ConfirmDialog
        isOpen={isConfirmOpen}
        title="Excluir missão?"
        description={
          <>
            A missão <strong>{missao.titulo}</strong> será removida da API e deixará de ser sugerida pelo Neo. Esta ação não
            pode ser desfeita.
          </>
        }
        confirmLabel="Excluir missão"
        isProcessing={isDeleting}
        onConfirm={handleDelete}
        onCancel={() => setIsConfirmOpen(false)}
      />
    </>
  )
}
