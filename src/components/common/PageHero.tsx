// commit 08/29 (Guilherme): feat(common) - cria componentes de interface reutilizáveis (Button, SectionHeading, PageHero, IconCard, StatCard, Reveal), sem nenhuma biblioteca externa
import type { ReactNode } from 'react'

interface PageHeroProps {
  badge: string
  title: string
  description: string
  children?: ReactNode
}

export default function PageHero({ badge, title, description, children }: PageHeroProps) {
  return (
    <section className="bg-gradient-to-b from-bg-soft to-bg py-16 md:py-20">
      <div className="max-w-7xl mx-auto px-4 md:px-8">
        <span className="inline-block rounded-full bg-white text-primary text-xs font-semibold px-3 py-1 mb-4 tracking-wide uppercase shadow-sm">
          {badge}
        </span>
        <h1 className="text-3xl md:text-5xl font-bold max-w-3xl mb-4 leading-tight">{title}</h1>
        <p className="text-text text-base md:text-lg max-w-2xl leading-relaxed">{description}</p>
        {children}
      </div>
    </section>
  )
}
