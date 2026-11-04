import { useMemo } from 'react'
import { useSearchParams } from 'react-router-dom'
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import { useApi } from '../hooks/useApi'
import PageHero from '../components/common/PageHero'
import Button from '../components/common/Button'
import FilterChips from '../components/common/FilterChips'
import SearchField from '../components/common/SearchField'
import UsuarioList from '../components/usuarios/UsuarioList'
import LoadingState from '../components/feedback/LoadingState'
import ErrorState from '../components/feedback/ErrorState'
import EmptyState from '../components/feedback/EmptyState'
import { PlusIcon, SearchIcon, TeamIcon } from '../components/icons/Icons'
import { listarUsuarios } from '../services/usuariosService'
import { API_URL } from '../services/api'
import { SITUACOES_USUARIO, getSituacaoUsuarioInfo } from '../data/situacoes'
import { normalizeText, pluralize } from '../utils/format'
import type { FilterOption } from '../types'
import type { SituacaoUsuario, Usuario } from '../types/api'

type FiltroSituacao = SituacaoUsuario | 'TODOS'

function isFiltroSituacao(value: string | null): value is FiltroSituacao {
  return value === 'TODOS' || SITUACOES_USUARIO.includes(value as SituacaoUsuario)
}

export default function Usuarios() {
  useDocumentTitle('Usuários')

  const [searchParams, setSearchParams] = useSearchParams()
  const situacaoParam = searchParams.get('situacao')
  const filtro: FiltroSituacao = isFiltroSituacao(situacaoParam) ? situacaoParam : 'TODOS'
  const busca = searchParams.get('busca') ?? ''

  const { state, retry } = useApi((signal) => listarUsuarios(signal), [])
  const usuarios: Usuario[] = state.status === 'success' ? state.data : []

  const usuariosFiltrados = useMemo(() => {
    const termo = normalizeText(busca)
    return usuarios
      .filter((usuario) => {
        const matchesSituacao = filtro === 'TODOS' || usuario.situacao === filtro
        const matchesBusca =
          termo.length === 0 || normalizeText(usuario.nome).includes(termo) || normalizeText(usuario.email).includes(termo)
        return matchesSituacao && matchesBusca
      })
      .sort((a, b) => a.nome.localeCompare(b.nome, 'pt-BR'))
  }, [usuarios, filtro, busca])

  const filterOptions: FilterOption<FiltroSituacao>[] = [
    { value: 'TODOS', label: 'Todos', count: usuarios.length },
    ...SITUACOES_USUARIO.map((situacao) => ({
      value: situacao,
      label: getSituacaoUsuarioInfo(situacao).label,
      count: usuarios.filter((usuario) => usuario.situacao === situacao).length,
    })),
  ]

  const updateParam = (key: 'situacao' | 'busca', value: string) => {
    setSearchParams(
      (current) => {
        const next = new URLSearchParams(current)
        const isDefault = value === '' || (key === 'situacao' && value === 'TODOS')
        if (isDefault) next.delete(key)
        else next.set(key, value)
        return next
      },
      { replace: true },
    )
  }

  const renderContent = () => {
    if (state.status === 'loading') return <LoadingState message="Carregando usuários da API..." />

    if (state.status === 'error') {
      return <ErrorState title="Não foi possível carregar os usuários" message={state.error.message} onRetry={retry} />
    }

    if (usuarios.length === 0) {
      return (
        <EmptyState
          icon={<TeamIcon />}
          title="Nenhum usuário cadastrado"
          description="Cadastre o primeiro usuário da comunidade SoulUp para começar a acompanhar a jornada sustentável."
          action={
            <Button to="/usuarios/novo">
              <PlusIcon />
              Cadastrar usuário
            </Button>
          }
        />
      )
    }

    if (usuariosFiltrados.length === 0) {
      return (
        <EmptyState
          icon={<SearchIcon />}
          title="Nenhum usuário encontrado"
          description="Nenhum usuário corresponde à busca ou ao filtro selecionado. Ajuste os critérios e tente novamente."
          action={
            <Button variant="secondary" onClick={() => setSearchParams({}, { replace: true })}>
              Limpar filtros
            </Button>
          }
        />
      )
    }

    return (
      <>
        <p className="text-sm text-text-muted mb-4" aria-live="polite">
          {pluralize(usuariosFiltrados.length, 'usuário encontrado', 'usuários encontrados')}
        </p>
        <UsuarioList usuarios={usuariosFiltrados} />
      </>
    )
  }

  return (
    <>
      <PageHero
        badge="Comunidade SoulUp"
        title="Usuários da plataforma"
        description="Acompanhe quem participa da jornada sustentável. Os dados vêm da API Java do SoulUp e podem ser cadastrados, editados ou removidos por aqui."
      >
        <div className="mt-8 flex flex-col sm:flex-row gap-3">
          <Button to="/usuarios/novo">
            <PlusIcon />
            Novo usuário
          </Button>
          <Button to="/missoes" variant="secondary">
            Ver missões
          </Button>
        </div>
      </PageHero>

      <section className="py-12 md:py-16">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          {state.status === 'success' && usuarios.length > 0 && (
            <div className="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4 mb-8">
              <FilterChips
                label="Filtrar usuários por situação"
                options={filterOptions}
                active={filtro}
                onChange={(value) => updateParam('situacao', value)}
              />
              <div className="w-full lg:w-80">
                <SearchField
                  id="usuarios-busca"
                  label="Buscar usuários"
                  value={busca}
                  placeholder="Buscar por nome ou e-mail..."
                  onChange={(value) => updateParam('busca', value)}
                />
              </div>
            </div>
          )}

          {renderContent()}

          <p className="mt-10 text-xs text-text-muted text-center break-all">
            Fonte dos dados: <code className="bg-bg-alt rounded px-1.5 py-0.5">GET {API_URL}/usuarios</code>
          </p>
        </div>
      </section>
    </>
  )
}
