import type { ReactNode } from 'react'

interface EmptyStateProps {
  icon: ReactNode
  title: string
  description: string
  action?: ReactNode
}

export default function EmptyState({ icon, title, description, action }: EmptyStateProps) {
  return (
    <div role="status" className="max-w-md mx-auto text-center py-12 md:py-16">
      <div className="w-14 h-14 rounded-2xl bg-bg-soft text-primary flex items-center justify-center mx-auto mb-4">{icon}</div>
      <h2 className="text-lg font-semibold text-text-dark mb-1">{title}</h2>
      <p className="text-sm text-text-muted leading-relaxed mb-6">{description}</p>
      {action}
    </div>
  )
}
