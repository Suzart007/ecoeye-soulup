// commit 08/29 (Guilherme): feat(common) - cria componentes de interface reutilizáveis (Button, SectionHeading, PageHero, IconCard, StatCard, Reveal), sem nenhuma biblioteca externa
import type { ReactNode } from 'react'

interface IconCardProps {
  icon: ReactNode
  title: string
  description: string
  layout?: 'stacked' | 'inline'
}

export default function IconCard({ icon, title, description, layout = 'stacked' }: IconCardProps) {
  if (layout === 'inline') {
    return (
      <article className="flex gap-4 rounded-2xl border border-border bg-white p-5 shadow-sm hover:shadow-md transition-shadow">
        <div className="flex-shrink-0 w-11 h-11 rounded-xl bg-bg-soft text-primary flex items-center justify-center">
          {icon}
        </div>
        <div>
          <h4 className="font-semibold text-text-dark mb-1">{title}</h4>
          <p className="text-sm text-text-muted leading-relaxed">{description}</p>
        </div>
      </article>
    )
  }

  return (
    <article className="rounded-2xl border border-border bg-white p-6 shadow-sm hover:shadow-md transition-shadow">
      <div className="w-12 h-12 rounded-xl bg-bg-soft text-primary flex items-center justify-center mb-4">
        {icon}
      </div>
      <h3 className="font-semibold text-lg text-text-dark mb-2">{title}</h3>
      <p className="text-sm text-text-muted leading-relaxed">{description}</p>
    </article>
  )
}
