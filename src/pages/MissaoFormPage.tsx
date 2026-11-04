import { useNavigate, useParams } from 'react-router-dom'
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import { useApi } from '../hooks/useApi'
import { useIdParam } from '../hooks/useIdParam'
import { useToast } from '../hooks/useToast'
import Button from '../components/common/Button'
import MissaoForm from '../components/missoes/MissaoForm'
import FormPageLayout from '../components/form/FormPageLayout'
import LoadingState from '../components/feedback/LoadingState'
import ErrorState from '../components/feedback/ErrorState'
import RedirectNotice from '../components/feedback/RedirectNotice'
import { atualizarMissao, buscarMissao, criarMissao } from '../services/missoesService'
import type { MissaoPayload } from '../types/api'

/** Atende /missoes/nova (cadastro) e /missoes/:id/editar (edição). */
export default function MissaoFormPage() {
  const { id } = useParams<{ id: string }>()
  const missaoId = useIdParam()

  if (id === undefined) return <NovaMissao />

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

  return <EditarMissao id={missaoId} />
}

function NovaMissao() {
  useDocumentTitle('Nova missão')
  const navigate = useNavigate()
  const { showToast } = useToast()

  const handleCreate = async (payload: MissaoPayload) => {
    const criada = await criarMissao(payload)
    showToast(`Missão "${payload.titulo}" cadastrada com sucesso!`)
    navigate(criada?.id ? `/missoes/${criada.id}` : '/missoes')
  }

  return (
    <FormPageLayout
      backTo="/missoes"
      backLabel="Todas as missões"
      badge="Cadastro"
      title="Nova missão sustentável"
      description="Ao salvar, os dados são enviados para a API Java do SoulUp e gravados no banco de dados. Missões ativas passam a ser sugeridas pelo Neo."
    >
      <MissaoForm submitLabel="Cadastrar missão" cancelTo="/missoes" onSubmit={handleCreate} />
    </FormPageLayout>
  )
}

function EditarMissao({ id }: { id: number }) {
  useDocumentTitle('Editar missão')
  const navigate = useNavigate()
  const { showToast } = useToast()
  const { state, retry } = useApi((signal) => buscarMissao(id, signal), [id])

  if (state.status === 'loading') return <LoadingState message="Carregando dados da missão..." />

  if (state.status === 'error') {
    if (state.error.kind === 'not_found') {
      return (
        <RedirectNotice
          title="Missão não encontrada"
          message={`Não existe nenhuma missão com o código #${id} para editar.`}
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

  const handleUpdate = async (payload: MissaoPayload) => {
    await atualizarMissao(id, payload)
    showToast(`Alterações na missão "${payload.titulo}" salvas com sucesso.`)
    navigate(`/missoes/${id}`)
  }

  return (
    <FormPageLayout
      backTo={`/missoes/${id}`}
      backLabel="Voltar para a missão"
      badge={`Edição · Missão #${id}`}
      title={`Editar "${missao.titulo}"`}
      description="Atualize os dados da missão. As alterações são enviadas para a API e refletem imediatamente nas sugestões do Neo."
    >
      <MissaoForm
        defaultValues={{
          titulo: missao.titulo ?? '',
          descricao: missao.descricao ?? '',
          pontuacao: missao.pontuacao ?? 0,
          situacao: missao.situacao ?? 'ATIVA',
        }}
        submitLabel="Salvar alterações"
        cancelTo={`/missoes/${id}`}
        onSubmit={handleUpdate}
      />
    </FormPageLayout>
  )
}
