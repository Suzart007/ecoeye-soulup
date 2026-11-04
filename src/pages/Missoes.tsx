import { useMemo } from 'react'
import { useSearchParams } from 'react-router-dom'
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import { useApi } from '../hooks/useApi'
import PageHero from '../components/common/PageHero'
import Button from '../components/common/Button'
import FilterChips from '../components/common/FilterChips'
import SearchField from '../components/common/SearchField'
import MissaoCard from '../components/missoes/MissaoCard'
import LoadingState from '../components/feedback/LoadingState'
import ErrorState from '../components/feedback/ErrorState'
import EmptyState from '../components/feedback/EmptyState'
import { PlusIcon, SearchIcon, TargetIcon } from '../components/icons/Icons'
import { listarMissoes } from '../services/missoesService'
import { API_URL } from '../services/api'
import { SITUACOES_MISSAO, getSituacaoMissaoInfo } from '../data/situacoes'
import { normalizeText, pluralize } from '../utils/format'
import type { FilterOption } from '../types'
import type { Missao, SituacaoMissao } from '../types/api'

type FiltroSituacao = SituacaoMissao | 'TODAS'

function isFiltroSituacao(value: string | null): value is FiltroSituacao {
  return value === 'TODAS' || SITUACOES_MISSAO.includes(value as SituacaoMissao)
}

export default function Missoes() {
  useDocumentTitle('Missões')

  // Filtro e busca ficam na URL (?situacao=ATIVA&busca=agua) para que o link possa ser compartilhado.
  const [searchParams, setSearchParams] = useSearchParams()
  const situacaoParam = searchParams.get('situacao')
  const filtro: FiltroSituacao = isFiltroSituacao(situacaoParam) ? situacaoParam : 'TODAS'
  const busca = searchParams.get('busca') ?? ''

  const { state, retry } = useApi((signal) => listarMissoes(signal), [])
  const missoes: Missao[] = state.status === 'success' ? state.data : []

  const missoesFiltradas = useMemo(() => {
    const termo = normalizeText(busca)
    return missoes.filter((missao) => {
      const matchesSituacao = filtro === 'TODAS' || missao.situacao === filtro
      const matchesBusca =
        termo.length === 0 ||
        normalizeText(missao.titulo).includes(termo) ||
        normalizeText(missao.descricao).includes(termo)
      return matchesSituacao && matchesBusca
    })
  }, [missoes, filtro, busca])

  const filterOptions: FilterOption<FiltroSituacao>[] = [
    { value: 'TODAS', label: 'Todas', count: missoes.length },
    ...SITUACOES_MISSAO.map((situacao) => ({
      value: situacao,
      label: getSituacaoMissaoInfo(situacao).label,
      count: missoes.filter((missao) => missao.situacao === situacao).length,
    })),
  ]

  const updateParam = (key: 'situacao' | 'busca', value: string) => {
    setSearchParams(
      (current) => {
        const next = new URLSearchParams(current)
        const isDefault = value === '' || (key === 'situacao' && value === 'TODAS')
        if (isDefault) next.delete(key)
        else next.set(key, value)
        return next
      },
      { replace: true },
    )
  }

  const clearFilters = () => setSearchParams({}, { replace: true })

  const renderContent = () => {
    if (state.status === 'loading') return <LoadingState message="Carregando missões da API..." />

    if (state.status === 'error') {
      return <ErrorState title="Não foi possível carregar as missões" message={state.error.message} onRetry={retry} />
    }

    if (missoes.length === 0) {
      return (
        <EmptyState
          icon={<TargetIcon />}
          title="Nenhuma missão cadastrada ainda"
          description="Cadastre a primeira missão sustentável para que o Neo possa sugeri-la aos usuários."
          action={
            <Button to="/missoes/nova">
              <PlusIcon />
              Cadastrar primeira missão
            </Button>
          }
        />
      )
    }

    if (missoesFiltradas.length === 0) {
      return (
        <EmptyState
          icon={<SearchIcon />}
          title="Nenhuma missão encontrada"
          description="Nenhuma missão corresponde à busca ou ao filtro selecionado. Ajuste os critérios e tente novamente."
          action={
            <Button variant="secondary" onClick={clearFilters}>
              Limpar filtros
            </Button>
          }
        />
      )
    }

    return (
      <>
        <p className="text-sm text-text-muted mb-4" aria-live="polite">
          {pluralize(missoesFiltradas.length, 'missão encontrada', 'missões encontradas')}
        </p>
        <div className="grid sm:grid-cols-2 xl:grid-cols-3 gap-4 md:gap-6">
          {missoesFiltradas.map((missao) => (
            <MissaoCard key={missao.id} missao={missao} />
          ))}
        </div>
      </>
    )
  }

  return (
    <>
      <PageHero
        badge="Missões sustentáveis"
        title="Missões que o Neo sugere aos usuários"
        description="Catálogo de missões cadastradas na API Java do SoulUp. Cada missão concluída gera pontos que o usuário troca por benefícios reais."
      >
        <div className="mt-8 flex flex-col sm:flex-row gap-3">
          <Button to="/missoes/nova">
            <PlusIcon />
            Nova missão
          </Button>
          <Button to="/usuarios" variant="secondary">
            Ver usuários
          </Button>
        </div>
      </PageHero>

      <section className="py-12 md:py-16">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          {state.status === 'success' && missoes.length > 0 && (
            <div className="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4 mb-8">
              <FilterChips
                label="Filtrar missões por situação"
                options={filterOptions}
                active={filtro}
                onChange={(value) => updateParam('situacao', value)}
              />
              <div className="w-full lg:w-80">
                <SearchField
                  id="missoes-busca"
                  label="Buscar missões"
                  value={busca}
                  placeholder="Buscar por título ou descrição..."
                  onChange={(value) => updateParam('busca', value)}
                />
              </div>
            </div>
          )}

          {renderContent()}

          <p className="mt-10 text-xs text-text-muted text-center break-all">
            Fonte dos dados: <code className="bg-bg-alt rounded px-1.5 py-0.5">GET {API_URL}/missoes</code>
          </p>
        </div>
      </section>
    </>
  )
}
