// commit 21/29 (Matheus): feat(sobre) - migra página Sobre para componente React
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import Reveal from '../components/common/Reveal'
import PageHero from '../components/common/PageHero'
import SectionHeading from '../components/common/SectionHeading'
import IconCard from '../components/common/IconCard'
import { LeafIcon, TeamIcon, TrendUpIcon } from '../components/icons/Icons'

const INFO_BOXES = [
  { icon: <LeafIcon className="w-5 h-5" />, title: 'Impacto ambiental', description: 'Cada interação contribui para um futuro mais sustentável.' },
  { icon: <TeamIcon className="w-5 h-5" />, title: 'Comunidade ativa', description: '+50.000 usuários trocando experiências e dicas.' },
  { icon: <TrendUpIcon className="w-5 h-5" />, title: 'Avatar em tempo real', description: 'Sugestões personalizadas que aprendem com você.' },
]

const TECH_STACK = [
  { title: 'React + Vite', description: 'Interface moderna em SPA' },
  { title: 'TypeScript', description: 'Tipagem segura em todo o código' },
  { title: 'Tailwind CSS', description: 'Estilização responsiva' },
  { title: 'Git + GitHub', description: 'Versionamento' },
]

const ROADMAP = [
  { label: 'Sprint 1 e 2 · Concluído', title: 'Desenvolvimento do Front-End', description: 'Páginas estruturadas, design responsivo, interatividade básica e identidade visual consolidada.', done: true },
  { label: 'Sprint 3 · 2º semestre', title: 'Migração para React + Vite + TypeScript', description: 'Reestruturação total da interface em componentes, com Tailwind CSS, React Router e React Hook Form.', done: true },
  { label: 'Sprint 4 · 2º semestre', title: 'Integração com o Avatar Inteligente', description: 'Conexão do widget do Neo com a IA (Watson Assistant) e refinamento para a apresentação no NEXT 2026.', done: false },
]

export default function Sobre() {
  useDocumentTitle('Sobre o projeto')

  return (
    <>
      <PageHero
        badge="Sobre o projeto"
        title="Conectando pessoas e propósito"
        description="O SoulUp é o resultado da parceria entre a Prospera e a SoulPrime, com o desafio de transformar pequenas ações sustentáveis em grandes mudanças coletivas. Esta é a história e a visão por trás do projeto."
      />

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8 grid lg:grid-cols-2 gap-12 items-start">
          <Reveal className="space-y-4 text-text leading-relaxed">
            <h2 className="text-2xl font-bold text-text-dark mb-2">O contexto da plataforma</h2>
            <p>
              Em 2025, a <strong>Prospera</strong>, empresa brasileira focada em sustentabilidade, adquiriu a
              startup <strong>SoulPrime</strong> e deu origem à plataforma <strong>SoulUp</strong>: um ecossistema
              digital que recompensa comportamentos conscientes com benefícios reais para os usuários.
            </p>
            <p>
              Hoje, milhões de pessoas interagem online sem perceber o impacto que essas ações poderiam gerar. A
              SoulUp resolve isso ao transformar engajamento em pontos, e pontos em desconto na fatura de energia,
              experiências sustentáveis e benefícios exclusivos.
            </p>
            <p>
              Nossa missão neste challenge é desenvolver o <strong>Avatar Inteligente e Interativo</strong>, que
              guia o usuário, sugere ações personalizadas e mantém o engajamento dentro da plataforma.
            </p>
          </Reveal>

          <Reveal className="space-y-4">
            {INFO_BOXES.map((box) => (
              <IconCard key={box.title} icon={box.icon} title={box.title} description={box.description} layout="inline" />
            ))}
          </Reveal>
        </div>
      </section>

      <section className="py-16 md:py-20 bg-bg-alt">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <SectionHeading badge="Stack" title="Tecnologias utilizadas" description="Construído com React, Vite e TypeScript, com estilização em Tailwind CSS." />
          </Reveal>

          <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {TECH_STACK.map((tech) => (
              <Reveal key={tech.title}>
                <div className="bg-white rounded-2xl border border-border p-6 text-center">
                  <h4 className="font-semibold text-text-dark mb-1">{tech.title}</h4>
                  <p className="text-sm text-text-muted">{tech.description}</p>
                </div>
              </Reveal>
            ))}
          </div>
        </div>
      </section>

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <SectionHeading badge="Cronograma" title="Roadmap do projeto" description="Acompanhe a evolução do nosso desenvolvimento ao longo do ano letivo de 2026." />
          </Reveal>

          <div className="space-y-4 max-w-3xl mx-auto">
            {ROADMAP.map((item) => (
              <Reveal key={item.title}>
                <div className={`rounded-2xl border p-6 ${item.done ? 'border-primary-light bg-bg-soft' : 'border-border bg-white'}`}>
                  <span className={`text-xs font-semibold uppercase tracking-wide ${item.done ? 'text-primary' : 'text-text-muted'}`}>
                    {item.label}
                  </span>
                  <h4 className="font-semibold text-text-dark text-lg mt-1 mb-1">{item.title}</h4>
                  <p className="text-sm text-text-muted leading-relaxed">{item.description}</p>
                </div>
              </Reveal>
            ))}
          </div>
        </div>
      </section>
    </>
  )
}
