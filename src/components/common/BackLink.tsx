import { Link } from 'react-router-dom'
import { ArrowRightIcon } from '../icons/Icons'

interface BackLinkProps {
  to: string
  label: string
}

export default function BackLink({ to, label }: BackLinkProps) {
  return (
    <Link to={to} className="inline-flex items-center gap-1.5 text-sm font-semibold text-primary hover:text-primary-dark mb-6">
      <ArrowRightIcon className="w-4 h-4 rotate-180" />
      {label}
    </Link>
  )
}
