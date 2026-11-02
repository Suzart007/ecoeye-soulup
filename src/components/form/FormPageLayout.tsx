import type { ReactNode } from 'react'
import BackLink from '../common/BackLink'

interface FormPageLayoutProps {
  backTo: string
  backLabel: string
  badge: string
  title: string
  description: string
  children: ReactNode
}

/** Estrutura comum das páginas de cadastro e edição. */
export default function FormPageLayout({ backTo, backLabel, badge, title, description, children }: FormPageLayoutProps) {
  return (
    <section className="bg-gradient-to-b from-bg-soft to-bg py-10 md:py-16">
      <div className="max-w-3xl mx-auto px-4 md:px-8">
        <BackLink to={backTo} label={backLabel} />

        <span className="inline-block rounded-full bg-white text-primary text-xs font-semibold px-3 py-1 mb-4 tracking-wide uppercase shadow-sm">
          {badge}
        </span>
        <h1 className="text-2xl md:text-4xl font-bold leading-tight mb-3">{title}</h1>
        <p className="text-text text-sm md:text-base leading-relaxed mb-8">{description}</p>

        <div className="bg-white rounded-2xl border border-border p-5 sm:p-6 md:p-8 shadow-sm">{children}</div>
      </div>
    </section>
  )
}
