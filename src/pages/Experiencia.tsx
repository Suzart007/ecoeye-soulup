// commit 18/29 (Kaio): feat(experiencia) - migra página Experiência do Usuário para componente React
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import Reveal from '../components/common/Reveal'
import PageHero from '../components/common/PageHero'
import SectionHeading from '../components/common/SectionHeading'
import Button from '../components/common/Button'
import { CheckIcon, ClockIcon, BotIcon } from '../components/icons/Icons'

const MISSIONS = [
  { text: 'Curtir 3 posts da comunidade', points: '+15', done: true },
  { text: 'Apagar luzes ao sair do cômodo', points: '+20', done: true },
  { text: 'Compartilhar uma dica sustentável', points: '+25', done: false },
  { text: 'Convidar 1 amigo para a plataforma', points: '+50', done: false },
  { text: 'Completar o quiz semanal', points: '+30', done: false },
]

const JOURNEY = [
  { step: 1, title: 'Cadastro e onboarding', description: 'O usuário cria a conta e responde algumas perguntas iniciais. O Neo personaliza a experiência com base no perfil declarado (interesses, rotina, objetivos).', tags: ['Onboarding rápido', 'Personalização inicial'] },
  { step: 2, title: 'Recebimento das primeiras missões', description: 'O Neo sugere missões simples e relevantes para o perfil do usuário. Cada missão tem uma pontuação e um impacto ambiental estimado.', tags: ['Missões personalizadas', 'Impacto mensurável'] },
  { step: 3, title: 'Execução e validação', description: 'O usuário cumpre a missão (no app ou no mundo real) e marca como concluída. O sistema valida a ação e o Neo confirma a pontuação.', tags: ['Validação automática', 'Feedback em tempo real'] },
  { step: 4, title: 'Acúmulo de pontos e evolução', description: 'Os pontos são somados ao perfil do usuário, que evolui de nível, conquista badges e melhora a posição no ranking da comunidade.', tags: ['Sistema de níveis', 'Ranking comunitário'] },
  { step: 5, title: 'Resgate de recompensas reais', description: 'Quando atinge o saldo necessário, o usuário troca pontos por descontos na fatura de energia, vouchers de parceiros e experiências sustentáveis.', tags: ['Benefícios reais', 'Impacto físico'] },
]

export default function Experiencia() {
  useDocumentTitle('Experiência do Usuário')

  return (
    <>
      <PageHero
        badge="Jornada do usuário"
        title="A experiência completa na SoulUp"
        description="Veja como Neo, nosso Avatar Inteligente, acompanha cada etapa da sua jornada sustentável — do primeiro acesso até as recompensas reais convertidas em benefícios."
      />

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <SectionHeading
              badge="Demonstração visual"
              title="Assim será o painel do usuário"
              description="Cada usuário tem um perfil único, acompanhado em tempo real pelo Neo. Esta é uma prévia da interface que será integrada ao Avatar Inteligente na próxima sprint."
            />
          </Reveal>

          <div className="grid lg:grid-cols-3 gap-6">
            <Reveal>
              <div className="bg-white rounded-2xl border border-border p-6 h-full">
                <span className="inline-block text-xs font-semibold uppercase tracking-wide text-primary mb-4">Perfil</span>
                <div className="flex items-center gap-3 mb-5">
                  <div className="w-14 h-14 rounded-full bg-primary text-white flex items-center justify-center font-bold text-lg">M</div>
                  <div>
                    <div className="font-semibold text-text-dark">Maria Souza</div>
                    <div className="text-xs text-text-muted">Nível 4 · Eco Consciente</div>
                  </div>
                </div>

                <div className="flex items-baseline gap-2 mb-2">
                  <span className="text-2xl font-extrabold text-primary font-heading">820</span>
                  <span className="text-xs text-text-muted">pontos acumulados</span>
                </div>

                <div className="h-2 rounded-full bg-bg-alt overflow-hidden mb-2">
                  <div className="h-full bg-primary rounded-full" style={{ width: '65%' }} />
                </div>
                <div className="flex justify-between text-xs text-text-muted">
                  <span>Próximo nível</span>
                  <span>820 / 1250 XP</span>
                </div>
              </div>
            </Reveal>

            <Reveal>
              <div className="bg-white rounded-2xl border border-border p-6 h-full">
                <span className="inline-block text-xs font-semibold uppercase tracking-wide text-primary mb-4">Missões da semana</span>
                <h3 className="font-semibold text-text-dark mb-4">Missões ativas</h3>
                <ul className="space-y-3">
                  {MISSIONS.map((mission) => (
                    <li key={mission.text} className="flex items-center gap-3 text-sm">
                      <span
                        className={`w-5 h-5 rounded-full flex items-center justify-center flex-shrink-0 ${
                          mission.done ? 'bg-primary text-white' : 'border border-border'
                        }`}
                      >
                        {mission.done && <CheckIcon className="w-3 h-3" />}
                      </span>
                      <span className={`flex-1 ${mission.done ? 'text-text-muted line-through' : 'text-text-dark'}`}>
                        {mission.text}
                      </span>
                      <span className="text-primary font-semibold text-xs">{mission.points}</span>
                    </li>
                  ))}
                </ul>
              </div>
            </Reveal>

            <Reveal>
              <div className="bg-white rounded-2xl border border-border p-6 h-full">
                <span className="inline-block text-xs font-semibold uppercase tracking-wide text-primary mb-4">Avatar</span>
                <div className="flex items-center gap-3 mb-4">
                  <div className="w-10 h-10 rounded-full bg-bg-soft text-primary flex items-center justify-center">
                    <BotIcon className="w-5 h-5" />
                  </div>
                  <div>
                    <h4 className="font-semibold text-sm">Neo</h4>
                    <span className="text-xs text-text-muted">Avatar Inteligente · IBM Watson</span>
                  </div>
                </div>

                <p className="text-sm bg-bg-alt rounded-xl rounded-tl-sm p-3 text-text-dark leading-relaxed mb-4">
                  <strong>Oi, Maria!</strong> Você completou 2 missões hoje. Que tal compartilhar uma dica
                  sustentável com a comunidade? Vale +25 pontos.
                </p>

                <div className="flex gap-2 text-xs text-text-muted bg-info/5 border border-info/20 rounded-xl p-3">
                  <ClockIcon className="w-4 h-4 flex-shrink-0 text-info" />
                  O widget de chat com o Neo já está no ar em todas as páginas (canto inferior direito) — a
                  integração com a IA acontece na próxima sprint.
                </div>
              </div>
            </Reveal>
          </div>
        </div>
      </section>

      <section className="py-16 md:py-20 bg-bg-alt">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <SectionHeading
              badge="Passo a passo"
              title="A jornada SoulUp em 5 etapas"
              description="Cada etapa é acompanhada pelo Avatar Inteligente, que personaliza a experiência conforme o perfil do usuário."
            />
          </Reveal>

          <ol className="space-y-6 max-w-3xl mx-auto">
            {JOURNEY.map((item) => (
              <Reveal key={item.step}>
                <li className="flex gap-4">
                  <div className="w-10 h-10 rounded-full bg-primary text-white flex items-center justify-center font-bold flex-shrink-0">
                    {item.step}
                  </div>
                  <div className="bg-white rounded-2xl border border-border p-5 flex-1">
                    <h3 className="font-semibold text-text-dark mb-1">{item.title}</h3>
                    <p className="text-sm text-text-muted leading-relaxed mb-3">{item.description}</p>
                    <div className="flex flex-wrap gap-2">
                      {item.tags.map((tag) => (
                        <span key={tag} className="text-xs bg-bg-soft text-primary px-2.5 py-1 rounded-full font-medium">
                          {tag}
                        </span>
                      ))}
                    </div>
                  </div>
                </li>
              </Reveal>
            ))}
          </ol>
        </div>
      </section>

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <div className="flex gap-4 rounded-2xl border border-border bg-bg-soft p-6 max-w-3xl mx-auto">
              <ClockIcon className="w-8 h-8 text-primary flex-shrink-0" />
              <div>
                <h3 className="font-semibold text-text-dark mb-1">O que vem na próxima entrega</h3>
                <p className="text-sm text-text leading-relaxed">
                  Nesta Sprint 3, migramos toda a interface para <strong>React + Vite + TypeScript</strong>, com
                  componentização e navegação em SPA. Na <strong>Sprint 4</strong>, conectaremos o widget do Neo ao{' '}
                  <strong>Avatar Inteligente desenvolvido em IBM Watson Assistant</strong>, integrando os fluxos do
                  chatbot (intenções, entidades, contexto e Webchat) diretamente no front-end.
                </p>
              </div>
            </div>
          </Reveal>
        </div>
      </section>

      <section className="py-16 md:py-20 bg-primary text-white text-center">
        <div className="max-w-7xl mx-auto px-4 md:px-8">
          <Reveal>
            <h2 className="text-2xl md:text-3xl font-bold mb-3">Quer entender a estratégia técnica?</h2>
            <p className="text-secondary max-w-xl mx-auto mb-8">
              Acesse a página da Solução e veja o problema, as capacidades do Neo e as métricas projetadas.
            </p>
            <Button to="/solucao" variant="accent">
              Ver detalhes da solução
            </Button>
          </Reveal>
        </div>
      </section>
    </>
  )
}
