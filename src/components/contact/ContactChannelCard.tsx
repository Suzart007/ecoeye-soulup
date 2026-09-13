// commit 24/29 (Matheus): feat(contato) - cria componente de canal de contato reutilizável
import type { ReactNode } from 'react'

interface ContactChannelCardProps {
  icon: ReactNode
  title: string
  description: string
  children: ReactNode
}

export default function ContactChannelCard({ icon, title, description, children }: ContactChannelCardProps) {
  return (
    <div className="flex gap-4 rounded-2xl border border-border bg-white p-5">
      <div className="w-11 h-11 rounded-xl bg-bg-soft text-primary flex items-center justify-center flex-shrink-0">
        {icon}
      </div>
      <div>
        <h4 className="font-semibold text-text-dark mb-0.5">{title}</h4>
        <p className="text-sm text-text-muted mb-1">{description}</p>
        <div className="text-sm font-medium text-primary">{children}</div>
      </div>
    </div>
  )
}
