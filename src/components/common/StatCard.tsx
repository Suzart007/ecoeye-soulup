// commit 08/29 (Guilherme): feat(common) - cria componentes de interface reutilizáveis (Button, SectionHeading, PageHero, IconCard, StatCard, Reveal), sem nenhuma biblioteca externa
interface StatCardProps {
  value: string
  label: string
  variant?: 'light' | 'dark'
}

export default function StatCard({ value, label, variant = 'light' }: StatCardProps) {
  const isDark = variant === 'dark'
  return (
    <div className={`text-center ${isDark ? 'text-white' : 'text-text-dark'}`}>
      <div className="text-2xl md:text-3xl font-extrabold font-heading">{value}</div>
      <div className={`text-xs md:text-sm mt-1 ${isDark ? 'text-secondary' : 'text-text-muted'}`}>{label}</div>
    </div>
  )
}
