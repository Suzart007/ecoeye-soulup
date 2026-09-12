// commit 17/29 (Kaio): feat(solucao) - migra página Solução para componente React
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import Reveal from '../components/common/Reveal'
import PageHero from '../components/common/PageHero'
import SectionHeading from '../components/common/SectionHeading'
import Button from '../components/common/Button'
import { XCircleIcon, CheckIcon } from '../components/icons/Icons'

const PROBLEMS = [
  'Usuário não sabe por onde começar dentro da plataforma.',
  'Conteúdos genéricos não engajam o público.',
  'Falta de feedback gera frustração e abandono.',
  'Time de suporte sobrecarregado com dúvidas básicas.',
]

const SOLUTIONS = [
  'O Neo faz o onboarding personalizado em segundos.',
  'Sugestões inteligentes baseadas no perfil de cada usuário.',
  'Feedback em tempo real a cada conquista.',
  'Resolve 80% das dúvidas comuns sem precisar de humano.',
]

const CAPABILITIES = [
  { title: 'Interage com o usuário', description: 'Conversa natural, em tempo real, com respostas contextuais às ações do usuário.' },
  { title: 'Sugere ações sustentáveis', description: 'Recomenda missões baseadas no histórico, no horário do dia e em datas comemorativas.' },
  { title: 'Gera conteúdo automaticamente', description: 'Cria dicas, lembretes e desafios personalizados sem dependência de equipe humana.' },
  { title: 'Aumenta o engajamento', description: 'Acompanha o progresso, celebra conquistas e incentiva o retorno do usuário à plataforma.' },
]

const METRICS = [
  { value: '+38%', label: 'Retenção semanal' },
  { value: '2.4x', label: 'Engajamento por sessão' },
  { value: '-60%', label: 'Tickets de suporte' },
  { value: '120t', label: 'CO₂ evitado / ano' },
]

export default function Solucao() {
  useDocumentTitle('Nossa Solução')

  return (
    <>
      <PageHero
        badge="Nossa proposta"
        title="Avatar Inteligente: o guia da sua jornada sustentável"
        description="Apresentamos a solução desenvolvida pelo grupo Ecoeye para o Desafio 3 do Challenge 2026: um Avatar Inteligente que personaliza a experiência do usuário, sugere ações conscientes e mantém o engajamento dentro da plataforma SoulUp."
      />

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <SectionHeading
              badge="Problema × Solução"
              title="O que estamos resolvendo"
              description="Sem um guia inteligente, usuários se perdem na plataforma e abandonam a jornada antes de atingir o engajamento sustentável esperado."
            />
          </Reveal>

          <div className="grid md:grid-cols-2 gap-6">
            <Reveal>
              <article className="rounded-2xl border border-danger/30 bg-danger/5 p-6 h-full">
                <span className="inline-block text-xs font-semibold uppercase tracking-wide text-danger mb-2">Sem o Avatar</span>
                <h3 className="font-semibold text-lg text-text-dark mb-4">O desafio enfrentado</h3>
                <ul className="space-y-3">
                  {PROBLEMS.map((item) => (
                    <li key={item} className="flex gap-2 text-sm text-text">
                      <XCircleIcon className="w-4 h-4 text-danger flex-shrink-0 mt-0.5" />
                      {item}
                    </li>
                  ))}
                </ul>
              </article>
            </Reveal>

            <Reveal>
              <article className="rounded-2xl border border-primary-light/40 bg-bg-soft p-6 h-full">
                <span className="inline-block text-xs font-semibold uppercase tracking-wide text-primary mb-2">Com o Avatar Neo</span>
                <h3 className="font-semibold text-lg text-text-dark mb-4">Como o SoulUp resolve</h3>
                <ul className="space-y-3">
                  {SOLUTIONS.map((item) => (
                    <li key={item} className="flex gap-2 text-sm text-text">
                      <CheckIcon className="w-4 h-4 text-primary flex-shrink-0 mt-0.5" />
                      {item}
                    </li>
                  ))}
                </ul>
              </article>
            </Reveal>
          </div>
        </div>
      </section>

      <section className="py-16 md:py-20 bg-bg-alt">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <SectionHeading badge="Capacidades" title="O que o Neo faz" description="Cada interação alimenta o sistema de pontuação e melhora a experiência personalizada." />
          </Reveal>

          <div className="grid sm:grid-cols-2 gap-6">
            {CAPABILITIES.map((cap) => (
              <Reveal key={cap.title}>
                <div className="bg-white rounded-2xl border border-border p-6">
                  <h4 className="font-semibold text-text-dark mb-1">{cap.title}</h4>
                  <p className="text-sm text-text-muted leading-relaxed">{cap.description}</p>
                </div>
              </Reveal>
            ))}
          </div>
        </div>
      </section>

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <SectionHeading badge="Indicadores" title="Métricas projetadas de impacto" description="Estimativas baseadas em benchmarks de plataformas similares e simulações internas." />
          </Reveal>

          <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
            {METRICS.map((metric) => (
              <Reveal key={metric.label}>
                <div className="text-center rounded-2xl border border-border bg-white py-8 px-4">
                  <div className="text-2xl md:text-3xl font-extrabold text-primary font-heading">{metric.value}</div>
                  <div className="text-sm text-text-muted mt-1">{metric.label}</div>
                </div>
              </Reveal>
            ))}
          </div>
        </div>
      </section>

      <section className="py-16 md:py-20 bg-primary text-white text-center">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <h2 className="text-2xl md:text-3xl font-bold mb-3">Veja a jornada completa do usuário</h2>
            <p className="text-secondary max-w-xl mx-auto mb-8">
              Explore como o Avatar Inteligente acompanha cada etapa da experiência SoulUp, do cadastro às
              recompensas.
            </p>
            <Button to="/experiencia" variant="accent">
              Ver a experiência do usuário
            </Button>
          </Reveal>
        </div>
      </section>
    </>
  )
}
