// commit 20/29 (Matheus): feat(home) - migra página Home para componente React
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import Reveal from '../components/common/Reveal'
import Button from '../components/common/Button'
import SectionHeading from '../components/common/SectionHeading'
import IconCard from '../components/common/IconCard'
import StatCard from '../components/common/StatCard'
import { LeafIcon, SparkleIcon, GiftIcon, ArrowRightIcon, ClockIcon } from '../components/icons/Icons'

const FEATURES = [
  {
    icon: <LeafIcon />,
    title: 'Sustentabilidade real',
    description:
      'Cada ação dentro do aplicativo gera impacto positivo mensurável no meio ambiente e na sua comunidade.',
  },
  {
    icon: <SparkleIcon />,
    title: 'Avatar Inteligente',
    description: 'Neo, seu guia personalizado, acompanha sua evolução, sugere missões e gera conteúdo automaticamente para você.',
  },
  {
    icon: <GiftIcon />,
    title: 'Recompensas reais',
    description: 'Pontos que se convertem em descontos na fatura de energia, vouchers, experiências sustentáveis e muito mais.',
  },
]

const STEPS = [
  { number: 1, title: 'Crie sua conta', description: 'Cadastro rápido para entrar na comunidade SoulUp.' },
  { number: 2, title: 'Converse com Neo', description: 'O avatar conhece seus interesses e cria um plano personalizado.' },
  { number: 3, title: 'Cumpra missões', description: 'Ações sustentáveis viram pontos no seu perfil.' },
  { number: 4, title: 'Resgate benefícios', description: 'Troque seus pontos por recompensas reais.' },
]

export default function Home() {
  useDocumentTitle('Home')

  return (
    <>
      <section className="bg-gradient-to-b from-bg-soft to-bg py-16 md:py-24">
        <div className="max-w-7xl mx-auto px-4 md:px-8 grid lg:grid-cols-2 gap-12 items-center">
          <Reveal>
            <span className="inline-block rounded-full bg-white text-primary text-xs font-semibold px-3 py-1 mb-4 tracking-wide uppercase shadow-sm">
              Challenge 2026 · Avatar Inteligente
            </span>
            <h1 className="text-3xl md:text-5xl font-bold leading-tight mb-4">
              Transforme suas ações em <span className="text-primary">impacto sustentável</span>
            </h1>
            <p className="text-text text-base md:text-lg leading-relaxed mb-8 max-w-xl">
              Conheça o SoulUp, a plataforma que conecta seu dia a dia digital a benefícios reais. Nosso Avatar
              Inteligente acompanha sua jornada, sugere missões sustentáveis e recompensa cada conquista com
              vantagens que fazem a diferença.
            </p>

            <div className="flex flex-wrap gap-3 mb-10">
              <Button to="/experiencia" ariaLabel="Conhecer a experiência do usuário">
                Conhecer a jornada
                <ArrowRightIcon />
              </Button>
              <Button to="/solucao" variant="secondary">
                Como funciona
              </Button>
            </div>

            <div className="flex flex-wrap gap-8">
              <StatCard value="+50k" label="Usuários engajados" />
              <StatCard value="120t" label="CO₂ evitado/ano" />
              <StatCard value="98%" label="Satisfação" />
            </div>
          </Reveal>

          <Reveal className="delay-100">
            <div className="bg-white rounded-2xl shadow-lg border border-border p-5">
              <div className="flex items-center gap-3 pb-4 border-b border-border">
                <div className="w-11 h-11 rounded-full bg-bg-soft text-primary flex items-center justify-center">
                  <SparkleIcon className="w-6 h-6" />
                </div>
                <div>
                  <h4 className="font-semibold text-sm">Neo · Sua guia SoulUp</h4>
                  <div className="text-xs text-success">● Online agora</div>
                </div>
              </div>

              <p className="text-sm text-text py-4 leading-relaxed">
                Oi! Notei que você completou 3 missões hoje. Quer descobrir como acelerar suas recompensas com
                ações simples no seu dia?
              </p>

              <div className="flex flex-wrap gap-2">
                <span className="inline-flex items-center gap-1.5 rounded-full border border-border px-3 py-1.5 text-xs font-medium text-text-dark">
                  Quero economizar energia
                </span>
                <span className="inline-flex items-center gap-1.5 rounded-full border border-border px-3 py-1.5 text-xs font-medium text-text-dark">
                  <ClockIcon className="w-3.5 h-3.5" /> Missões do dia
                </span>
                <span className="inline-flex items-center gap-1.5 rounded-full border border-border px-3 py-1.5 text-xs font-medium text-text-dark">
                  <GiftIcon className="w-3.5 h-3.5" /> Meu ranking
                </span>
              </div>
            </div>
          </Reveal>
        </div>
      </section>

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <SectionHeading
              badge="Diferenciais"
              title="Por que escolher a SoulUp?"
              description="Uma experiência única que conecta sustentabilidade, gamificação e inteligência artificial."
            />
          </Reveal>

          <div className="grid md:grid-cols-3 gap-6">
            {FEATURES.map((feature) => (
              <Reveal key={feature.title}>
                <IconCard icon={feature.icon} title={feature.title} description={feature.description} />
              </Reveal>
            ))}
          </div>
        </div>
      </section>

      <section className="py-16 md:py-20 bg-bg-alt">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <SectionHeading
              badge="Passo a passo"
              title="Como funciona"
              description="Em poucos passos você começa sua jornada sustentável com a SoulUp."
            />
          </Reveal>

          <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {STEPS.map((step) => (
              <Reveal key={step.number}>
                <div className="bg-white rounded-2xl border border-border p-6 h-full">
                  <div className="w-10 h-10 rounded-full bg-primary text-white flex items-center justify-center font-bold mb-4">
                    {step.number}
                  </div>
                  <h4 className="font-semibold text-text-dark mb-1">{step.title}</h4>
                  <p className="text-sm text-text-muted leading-relaxed">{step.description}</p>
                </div>
              </Reveal>
            ))}
          </div>
        </div>
      </section>

      <section className="py-16 md:py-20 bg-primary text-white text-center">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <h2 className="text-2xl md:text-3xl font-bold mb-3">Pronto para começar sua jornada sustentável?</h2>
            <p className="text-secondary max-w-xl mx-auto mb-8">
              Junte-se a milhares de pessoas que já transformam ações conscientes em recompensas reais.
            </p>
            <Button to="/experiencia" variant="accent">
              Ver a experiência completa
            </Button>
          </Reveal>
        </div>
      </section>
    </>
  )
}
