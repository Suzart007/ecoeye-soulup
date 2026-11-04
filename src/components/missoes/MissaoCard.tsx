import { Link } from 'react-router-dom'
import StatusBadge from '../common/StatusBadge'
import { ArrowRightIcon, TargetIcon } from '../icons/Icons'
import { getSituacaoMissaoInfo } from '../../data/situacoes'
import type { Missao } from '../../types/api'

interface MissaoCardProps {
  missao: Missao
}

export default function MissaoCard({ missao }: MissaoCardProps) {
  const situacao = getSituacaoMissaoInfo(missao.situacao)

  return (
    <article className="flex flex-col h-full rounded-2xl border border-border bg-white p-5 md:p-6 shadow-sm hover:shadow-md transition-shadow">
      <div className="flex items-start justify-between gap-3 mb-4">
        <span className="w-11 h-11 rounded-xl bg-bg-soft text-primary flex items-center justify-center flex-shrink-0">
          <TargetIcon className="w-5 h-5" />
        </span>
        <StatusBadge label={situacao.label} tone={situacao.tone} />
      </div>

      <h3 className="font-semibold text-lg text-text-dark mb-2">{missao.titulo}</h3>
      <p className="text-sm text-text-muted leading-relaxed mb-5 line-clamp-3">{missao.descricao}</p>

      <div className="mt-auto flex items-center justify-between gap-3 pt-4 border-t border-border">
        <span className="text-sm font-bold text-primary">+{missao.pontuacao} pts</span>
        <Link
          to={`/missoes/${missao.id}`}
          className="inline-flex items-center gap-1.5 text-sm font-semibold text-primary hover:text-primary-dark"
          aria-label={`Ver detalhes da missão ${missao.titulo}`}
        >
          Ver detalhes
          <ArrowRightIcon />
        </Link>
      </div>
    </article>
  )
}
