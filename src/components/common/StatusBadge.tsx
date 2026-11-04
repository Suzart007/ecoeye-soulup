import type { BadgeTone } from '../../types'

const toneClasses: Record<BadgeTone, string> = {
  success: 'bg-success/10 text-success',
  info: 'bg-info/10 text-info',
  danger: 'bg-danger/10 text-danger',
  neutral: 'bg-bg-alt text-text-muted',
}

interface StatusBadgeProps {
  label: string
  tone: BadgeTone
}

export default function StatusBadge({ label, tone }: StatusBadgeProps) {
  return (
    <span className={`inline-flex items-center gap-1.5 rounded-full px-2.5 py-1 text-xs font-semibold ${toneClasses[tone]}`}>
      <span className="w-1.5 h-1.5 rounded-full bg-current" aria-hidden="true" />
      {label}
    </span>
  )
}
