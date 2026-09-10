// commit 08/29 (Guilherme): feat(common) - cria componentes de interface reutilizáveis (Button, SectionHeading, PageHero, IconCard, StatCard, Reveal), sem nenhuma biblioteca externa
interface SectionHeadingProps {
  badge: string
  title: string
  description?: string
  align?: 'left' | 'center'
}

export default function SectionHeading({ badge, title, description, align = 'center' }: SectionHeadingProps) {
  return (
    <div className={`max-w-2xl mb-10 ${align === 'center' ? 'mx-auto text-center' : ''}`}>
      <span className="inline-block rounded-full bg-bg-soft text-primary text-xs font-semibold px-3 py-1 mb-3 tracking-wide uppercase">
        {badge}
      </span>
      <h2 className="text-2xl md:text-3xl font-bold mb-3">{title}</h2>
      {description && <p className="text-text text-base leading-relaxed">{description}</p>}
    </div>
  )
}
