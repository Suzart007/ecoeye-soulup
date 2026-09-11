// commit 11/29 (Guilherme): feat(equipe) - implementa rota dinâmica de perfil com useParams/useNavigate
import { useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import { TEAM_MEMBERS } from '../data/team'
import { LinkedinIcon, GithubIcon, ArrowRightIcon } from '../components/icons/Icons'

export default function IntegranteDetalhe() {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  const member = TEAM_MEMBERS.find((item) => item.id === id)

  useDocumentTitle(member ? member.name : 'Integrante não encontrado')

  useEffect(() => {
    if (!member) {
      const timeout = setTimeout(() => navigate('/integrantes', { replace: true }), 2500)
      return () => clearTimeout(timeout)
    }
  }, [member, navigate])

  if (!member) {
    return (
      <section className="py-24 text-center px-4">
        <h1 className="text-2xl font-bold text-text-dark mb-2">Integrante não encontrado</h1>
        <p className="text-text-muted">Você será redirecionado para a página da equipe em instantes...</p>
      </section>
    )
  }

  const initials = member.name
    .split(' ')
    .slice(0, 2)
    .map((part) => part[0])
    .join('')

  return (
    <section className="py-16 md:py-20">
      <div className="max-w-3xl mx-auto px-4 md:px-8">
        <button
          type="button"
          onClick={() => navigate(-1)}
          className="inline-flex items-center gap-1.5 text-sm font-semibold text-primary hover:text-primary-dark mb-8"
        >
          <ArrowRightIcon className="w-4 h-4 rotate-180" />
          Voltar
        </button>

        <div className="rounded-2xl border border-border bg-white p-8 text-center">
          {member.photoUrl ? (
            <img
              src={member.photoUrl}
              alt={member.name}
              className="w-20 h-20 rounded-full object-cover mx-auto mb-5"
            />
          ) : (
            <div className="w-20 h-20 rounded-full bg-primary text-white flex items-center justify-center font-bold text-2xl mx-auto mb-5">
              {initials}
            </div>
          )}
          <h1 className="text-2xl font-bold text-text-dark mb-1">{member.name}</h1>

          <p className="text-text text-sm leading-relaxed max-w-lg mx-auto my-6">{member.bio}</p>

          <div className="grid sm:grid-cols-2 gap-3 max-w-sm mx-auto text-sm mb-6">
            <div className="rounded-xl bg-bg-alt px-4 py-3">
              <span className="block text-xs text-text-muted">RM</span>
              <strong className="text-text-dark">{member.rm}</strong>
            </div>
            <div className="rounded-xl bg-bg-alt px-4 py-3">
              <span className="block text-xs text-text-muted">Turma</span>
              <strong className="text-text-dark">{member.turma}</strong>
            </div>
          </div>

          <div className="flex justify-center gap-3">
            <a
              href={member.linkedin}
              target="_blank"
              rel="noopener noreferrer"
              className="inline-flex items-center gap-1.5 rounded-full border border-border px-4 py-2 text-sm font-medium hover:border-primary-light"
              aria-label={`LinkedIn de ${member.name}`}
            >
              <LinkedinIcon /> LinkedIn
            </a>
            <a
              href={member.github}
              target="_blank"
              rel="noopener noreferrer"
              className="inline-flex items-center gap-1.5 rounded-full border border-border px-4 py-2 text-sm font-medium hover:border-primary-light"
              aria-label={`GitHub de ${member.name}`}
            >
              <GithubIcon /> GitHub
            </a>
          </div>
        </div>
      </div>
    </section>
  )
}
