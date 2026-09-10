// commit 08/29 (Guilherme): feat(common) - cria componentes de interface reutilizáveis (Button, SectionHeading, PageHero, IconCard, StatCard, Reveal), sem nenhuma biblioteca externa
import type { ReactNode } from 'react'
import { useScrollReveal } from '../../hooks/useScrollReveal'

interface RevealProps {
  children: ReactNode
  className?: string
}

export default function Reveal({ children, className = '' }: RevealProps) {
  const { ref, isVisible } = useScrollReveal<HTMLDivElement>()

  return (
    <div ref={ref} className={`reveal ${isVisible ? 'reveal-visible' : ''} ${className}`}>
      {children}
    </div>
  )
}
