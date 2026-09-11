// commit 12/29 (Guilherme): feat(faq) - migra página FAQ com busca, filtro por categoria e accordion
import { useMemo, useState } from 'react'
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import Reveal from '../components/common/Reveal'
import PageHero from '../components/common/PageHero'
import Button from '../components/common/Button'
import FaqAccordionItem from '../components/faq/FaqAccordionItem'
import { FAQ_ENTRIES, FAQ_CATEGORY_LABELS } from '../data/faq'
import { SearchIcon } from '../components/icons/Icons'
import type { FaqCategory } from '../types'

const CATEGORIES: Array<FaqCategory | 'todas'> = ['todas', 'plataforma', 'avatar', 'pontos', 'seguranca']

export default function Faq() {
  useDocumentTitle('Perguntas Frequentes')

  const [activeCategory, setActiveCategory] = useState<FaqCategory | 'todas'>('todas')
  const [searchTerm, setSearchTerm] = useState('')
  const [openId, setOpenId] = useState<string | null>(null)

  const filteredEntries = useMemo(() => {
    const term = searchTerm.trim().toLowerCase()
    return FAQ_ENTRIES.filter((entry) => {
      const matchesCategory = activeCategory === 'todas' || entry.category === activeCategory
      const matchesSearch =
        term.length === 0 ||
        entry.question.toLowerCase().includes(term) ||
        entry.answer.toLowerCase().includes(term)
      return matchesCategory && matchesSearch
    })
  }, [activeCategory, searchTerm])

  const countByCategory = useMemo(() => {
    const counts: Record<string, number> = { todas: FAQ_ENTRIES.length }
    FAQ_ENTRIES.forEach((entry) => {
      counts[entry.category] = (counts[entry.category] ?? 0) + 1
    })
    return counts
  }, [])

  return (
    <>
      <PageHero
        badge="Tire suas dúvidas"
        title="Perguntas Frequentes"
        description="Reunimos as principais dúvidas sobre o funcionamento da plataforma SoulUp, o Avatar Inteligente Neo e o nosso sistema de recompensas."
      />

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8 grid lg:grid-cols-[260px_1fr] gap-8">
          <Reveal>
            <aside className="lg:sticky lg:top-24">
              <h3 className="font-semibold text-text-dark mb-3">Categorias</h3>
              <div className="flex lg:flex-col gap-2 flex-wrap mb-6">
                {CATEGORIES.map((category) => (
                  <button
                    key={category}
                    type="button"
                    onClick={() => setActiveCategory(category)}
                    className={`flex items-center justify-between gap-2 rounded-full lg:rounded-xl px-4 py-2 text-sm font-medium transition-colors ${
                      activeCategory === category
                        ? 'bg-primary text-white'
                        : 'bg-white border border-border text-text-dark hover:border-primary-light'
                    }`}
                  >
                    {FAQ_CATEGORY_LABELS[category]}
                    <span
                      className={`text-xs rounded-full px-1.5 ${
                        activeCategory === category ? 'bg-white/20' : 'bg-bg-alt'
                      }`}
                    >
                      {countByCategory[category] ?? 0}
                    </span>
                  </button>
                ))}
              </div>

              <div className="rounded-2xl bg-bg-soft p-5">
                <h4 className="font-semibold text-text-dark text-sm mb-1">Não achou sua resposta?</h4>
                <p className="text-xs text-text-muted mb-3">Fale com a nossa equipe.</p>
                <Button to="/contato" className="w-full">
                  Falar conosco
                </Button>
              </div>
            </aside>
          </Reveal>

          <div>
            <Reveal>
              <label htmlFor="faq-search-input" className="sr-only">
                Buscar nas perguntas
              </label>
              <div className="flex items-center gap-3 rounded-full border border-border bg-white px-4 py-3 mb-6">
                <SearchIcon className="w-5 h-5 text-text-muted flex-shrink-0" />
                <input
                  id="faq-search-input"
                  type="search"
                  value={searchTerm}
                  onChange={(event) => setSearchTerm(event.target.value)}
                  placeholder="Digite o que você quer saber..."
                  className="flex-1 outline-none text-sm bg-transparent"
                />
              </div>
            </Reveal>

            <div className="space-y-3">
              {filteredEntries.map((entry) => (
                <Reveal key={entry.id}>
                  <FaqAccordionItem
                    entry={entry}
                    isOpen={openId === entry.id}
                    onToggle={() => setOpenId((current) => (current === entry.id ? null : entry.id))}
                  />
                </Reveal>
              ))}

              {filteredEntries.length === 0 && (
                <div className="text-center py-16" role="status">
                  <SearchIcon className="w-10 h-10 text-text-muted mx-auto mb-3" />
                  <h3 className="font-semibold text-text-dark">Nenhuma pergunta encontrada</h3>
                  <p className="text-sm text-text-muted mt-1">
                    Tente outro termo de busca ou entre em contato com nossa equipe.
                  </p>
                </div>
              )}
            </div>
          </div>
        </div>
      </section>
    </>
  )
}
