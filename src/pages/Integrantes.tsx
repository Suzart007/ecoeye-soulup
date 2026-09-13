// commit 22/29 (Matheus): feat(equipe) - cria página de Integrantes com grid de cards
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import Reveal from '../components/common/Reveal'
import PageHero from '../components/common/PageHero'
import TeamMemberCard from '../components/team/TeamMemberCard'
import { TEAM_MEMBERS } from '../data/team'
import { TeamIcon } from '../components/icons/Icons'

export default function Integrantes() {
  useDocumentTitle('Equipe')

  return (
    <>
      <PageHero
        badge="Quem somos"
        title="A equipe Ecoeye por trás do SoulUp"
        description="Somos um grupo de estudantes de Análise e Desenvolvimento de Sistemas da FIAP, comprometidos em construir soluções que unam tecnologia e impacto socioambiental positivo."
      />

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <div className="flex gap-4 rounded-2xl border border-border bg-bg-soft p-6 mb-10 max-w-3xl">
              <TeamIcon className="w-8 h-8 text-primary flex-shrink-0" />
              <div>
                <h3 className="font-semibold text-text-dark mb-1">Representante do grupo</h3>
                <p className="text-sm text-text-muted leading-relaxed">
                  Integrante responsável pela comunicação oficial com o Scrum Master e professores do Challenge.
                </p>
              </div>
            </div>
          </Reveal>

          <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {TEAM_MEMBERS.map((member) => (
              <Reveal key={member.id}>
                <TeamMemberCard member={member} />
              </Reveal>
            ))}
          </div>
        </div>
      </section>
    </>
  )
}
