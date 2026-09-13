// commit 23/29 (Matheus): feat(faq) - cria componente de accordion reutilizável
import { ChevronDownIcon } from '../icons/Icons'
import type { FaqEntry } from '../../types'

interface FaqAccordionItemProps {
  entry: FaqEntry
  isOpen: boolean
  onToggle: () => void
}

export default function FaqAccordionItem({ entry, isOpen, onToggle }: FaqAccordionItemProps) {
  return (
    <article className="rounded-2xl border border-border bg-white overflow-hidden">
      <button
        type="button"
        onClick={onToggle}
        aria-expanded={isOpen}
        className="w-full flex items-center justify-between gap-4 px-5 py-4 text-left font-semibold text-text-dark hover:bg-bg-alt transition-colors"
      >
        <span>{entry.question}</span>
        <span className={`text-primary flex-shrink-0 transition-transform ${isOpen ? 'rotate-45' : ''}`}>
          <ChevronDownIcon className="w-5 h-5" />
        </span>
      </button>
      {isOpen && (
        <div className="px-5 pb-4 text-sm text-text leading-relaxed border-t border-border pt-4">
          {entry.answer}
        </div>
      )}
    </article>
  )
}
