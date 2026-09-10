// commit 08/29 (Guilherme): feat(common) - cria componentes de interface reutilizáveis (Button, SectionHeading, PageHero, IconCard, StatCard, Reveal), sem nenhuma biblioteca externa
import type { ReactNode } from 'react'
import { Link } from 'react-router-dom'

type Variant = 'primary' | 'secondary' | 'accent'

const variantClasses: Record<Variant, string> = {
  primary:
    'bg-primary text-white hover:bg-primary-dark shadow-md hover:shadow-lg',
  secondary:
    'bg-white text-primary border border-border hover:border-primary-light',
  accent: 'bg-accent text-text-dark hover:brightness-95 shadow-md',
}

const baseClasses =
  'inline-flex items-center justify-center gap-2 rounded-full px-6 py-3 font-semibold text-sm transition-all duration-200'

interface ButtonProps {
  children: ReactNode
  variant?: Variant
  to?: string
  href?: string
  type?: 'button' | 'submit'
  onClick?: () => void
  disabled?: boolean
  className?: string
  ariaLabel?: string
}

export default function Button({
  children,
  variant = 'primary',
  to,
  href,
  type = 'button',
  onClick,
  disabled,
  className = '',
  ariaLabel,
}: ButtonProps) {
  const classes = `${baseClasses} ${variantClasses[variant]} ${disabled ? 'opacity-50 cursor-not-allowed' : ''} ${className}`

  if (to) {
    return (
      <Link to={to} className={classes} aria-label={ariaLabel}>
        {children}
      </Link>
    )
  }

  if (href) {
    return (
      <a href={href} target="_blank" rel="noopener noreferrer" className={classes} aria-label={ariaLabel}>
        {children}
      </a>
    )
  }

  return (
    <button type={type} onClick={onClick} disabled={disabled} className={classes} aria-label={ariaLabel}>
      {children}
    </button>
  )
}
