// commit 22/29 (Matheus): feat(equipe) - cria página de Integrantes com grid de cards
import { useNavigate } from 'react-router-dom'
import type { TeamMember } from '../../types'
import { ArrowRightIcon } from '../icons/Icons'

interface TeamMemberCardProps {
  member: TeamMember
}

export default function TeamMemberCard({ member }: TeamMemberCardProps) {
  const navigate = useNavigate()
  const initials = member.name
    .split(' ')
    .slice(0, 2)
    .map((part) => part[0])
    .join('')

  return (
    <article className="rounded-2xl border border-border bg-white p-6 text-center flex flex-col items-center">
      {member.photoUrl ? (
        <img
          src={member.photoUrl}
          alt={member.name}
          className="w-16 h-16 rounded-full object-cover mb-4"
        />
      ) : (
        <div className="w-16 h-16 rounded-full bg-primary text-white flex items-center justify-center font-bold text-lg mb-4">
          {initials}
        </div>
      )}
      <h3 className="font-semibold text-text-dark mb-4">{member.name}</h3>

      <button
        type="button"
        onClick={() => navigate(`/integrantes/${member.id}`)}
        className="mt-auto inline-flex items-center gap-1.5 text-sm font-semibold text-primary hover:text-primary-dark"
      >
        Ver perfil
        <ArrowRightIcon />
      </button>
    </article>
  )
}
