// commit 25/29 (Gustavo Tenório): feat(contato) - migra página Contato para componente React
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import Reveal from '../components/common/Reveal'
import PageHero from '../components/common/PageHero'
import ContactChannelCard from '../components/contact/ContactChannelCard'
import ContactForm from '../components/contact/ContactForm'
import { MailIcon, PhoneIcon, MapPinIcon, ShareIcon } from '../components/icons/Icons'

export default function Contato() {
  useDocumentTitle('Contato')

  return (
    <>
      <PageHero
        badge="Estamos aqui para ajudar"
        title="Fale com a equipe SoulUp"
        description="Dúvidas, sugestões, parcerias ou imprensa: escolha o canal de sua preferência ou envie a mensagem através do formulário ao lado. Respondemos em até 48 horas úteis."
      />

      <section className="py-16 md:py-20">
        <div className="max-w-7xl mx-auto px-4 md:px-8 grid lg:grid-cols-[1fr_1.4fr] gap-8">
          <Reveal>
            <div className="space-y-4">
              <ContactChannelCard icon={<MailIcon className="w-5 h-5" />} title="E-mail" description="Resposta em até 48h úteis.">
                <a href="mailto:contato@soulup.fiap.br">contato@soulup.fiap.br</a>
              </ContactChannelCard>

              <ContactChannelCard icon={<PhoneIcon className="w-5 h-5" />} title="Telefone" description="Segunda a sexta, das 9h às 18h.">
                <a href="tel:+551134566789">+55 (11) 3456-6789</a>
              </ContactChannelCard>

              <ContactChannelCard icon={<MapPinIcon className="w-5 h-5" />} title="Endereço" description="">
                Av. Lins de Vasconcelos, 1264 · Aclimação · São Paulo · SP
              </ContactChannelCard>

              <ContactChannelCard icon={<ShareIcon className="w-5 h-5" />} title="Mídias sociais" description="Acompanhe nossas novidades.">
                <a href="https://www.linkedin.com" target="_blank" rel="noopener noreferrer">
                  LinkedIn, Instagram, TikTok
                </a>
              </ContactChannelCard>
            </div>
          </Reveal>

          <Reveal>
            <ContactForm />
          </Reveal>
        </div>
      </section>
    </>
  )
}
