import { useNavigate, useParams } from 'react-router-dom'
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import { useApi } from '../hooks/useApi'
import { useIdParam } from '../hooks/useIdParam'
import { useToast } from '../hooks/useToast'
import Button from '../components/common/Button'
import UsuarioForm, { type UsuarioFormValues } from '../components/usuarios/UsuarioForm'
import FormPageLayout from '../components/form/FormPageLayout'
import LoadingState from '../components/feedback/LoadingState'
import ErrorState from '../components/feedback/ErrorState'
import RedirectNotice from '../components/feedback/RedirectNotice'
import { atualizarUsuario, buscarUsuario, criarUsuario } from '../services/usuariosService'
import { todayIsoDate } from '../utils/format'

/** Atende /usuarios/novo (cadastro) e /usuarios/:id/editar (edição). */
export default function UsuarioFormPage() {
  const { id } = useParams<{ id: string }>()
  const usuarioId = useIdParam()

  if (id === undefined) return <NovoUsuario />

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

  return <EditarUsuario id={usuarioId} />
}

function NovoUsuario() {
  useDocumentTitle('Novo usuário')
  const navigate = useNavigate()
  const { showToast } = useToast()

  const handleCreate = async (values: UsuarioFormValues) => {
    const criado = await criarUsuario({ ...values, dataCadastro: todayIsoDate() })
    showToast(`Usuário "${values.nome}" cadastrado com sucesso! Bem-vindo(a) à SoulUp.`)
    navigate(criado?.id ? `/usuarios/${criado.id}` : '/usuarios')
  }

  return (
    <FormPageLayout
      backTo="/usuarios"
      backLabel="Todos os usuários"
      badge="Cadastro"
      title="Novo usuário"
      description="Cadastre um novo participante da comunidade SoulUp. Os dados são enviados para a API Java e a data de cadastro é registrada automaticamente."
    >
      <UsuarioForm submitLabel="Cadastrar usuário" cancelTo="/usuarios" onSubmit={handleCreate} />
    </FormPageLayout>
  )
}

function EditarUsuario({ id }: { id: number }) {
  useDocumentTitle('Editar usuário')
  const navigate = useNavigate()
  const { showToast } = useToast()
  const { state, retry } = useApi((signal) => buscarUsuario(id, signal), [id])

  if (state.status === 'loading') return <LoadingState message="Carregando dados do usuário..." />

  if (state.status === 'error') {
    if (state.error.kind === 'not_found') {
      return (
        <RedirectNotice
          title="Usuário não encontrado"
          message={`Não existe nenhum usuário com o código #${id} para editar.`}
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

  const handleUpdate = async (values: UsuarioFormValues) => {
    // A data de cadastro original é reenviada para que o PUT não a apague no banco.
    await atualizarUsuario(id, { ...values, dataCadastro: usuario.dataCadastro })
    showToast(`Dados de "${values.nome}" atualizados com sucesso.`)
    navigate(`/usuarios/${id}`)
  }

  return (
    <FormPageLayout
      backTo={`/usuarios/${id}`}
      backLabel="Voltar para o perfil"
      badge={`Edição · Usuário #${id}`}
      title={`Editar ${usuario.nome}`}
      description="Atualize os dados do usuário. Alterar a situação para inativo ou bloqueado suspende as sugestões do Neo."
    >
      <UsuarioForm
        defaultValues={{ nome: usuario.nome ?? '', email: usuario.email ?? '', situacao: usuario.situacao ?? 'ATIVO' }}
        submitLabel="Salvar alterações"
        cancelTo={`/usuarios/${id}`}
        onSubmit={handleUpdate}
      />
    </FormPageLayout>
  )
}
