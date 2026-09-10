// commit 04/29 (Henry): feat(data) - cria base de dados das perguntas frequentes
import type { FaqEntry } from '../types'

export const FAQ_ENTRIES: FaqEntry[] = [
  {
    id: 'o-que-e-soulup',
    category: 'plataforma',
    question: 'O que é a plataforma SoulUp?',
    answer:
      'O SoulUp é uma plataforma digital criada pela Prospera em parceria com a startup SoulPrime. Ela transforma suas interações online em benefícios reais — como desconto na fatura de energia, vouchers e experiências sustentáveis — enquanto gera impacto positivo no meio ambiente.',
  },
  {
    id: 'precisa-pagar',
    category: 'plataforma',
    question: 'Preciso pagar para usar o SoulUp?',
    answer:
      'Não! A plataforma é 100% gratuita. Basta criar uma conta e começar a interagir. Quanto mais você participa, mais pontos acumula e mais recompensas pode resgatar.',
  },
  {
    id: 'dispositivos',
    category: 'plataforma',
    question: 'Em quais dispositivos posso usar o SoulUp?',
    answer:
      'Você pode acessar o SoulUp por navegadores web (Chrome, Firefox, Edge, Safari) em desktop, tablet e mobile. Nosso layout é totalmente responsivo. Em breve teremos também o aplicativo nativo para iOS e Android.',
  },
  {
    id: 'quem-e-neo',
    category: 'avatar',
    question: 'Quem é o Neo, seu avatar inteligente?',
    answer:
      'O Neo é nosso avatar inteligente, um assistente virtual que guia sua jornada na SoulUp. Ele conhece seus interesses e propõe missões personalizadas, acompanha seu progresso, sugere ações sustentáveis e gera conteúdo educativo automaticamente para você.',
  },
  {
    id: 'tempo-real',
    category: 'avatar',
    question: 'O Avatar conversa em tempo real comigo?',
    answer:
      'Sim! O Neo responde em tempo real, com sugestões personalizadas a partir do seu histórico de interações. Você pode conhecer a jornada completa na página de experiência do usuário.',
  },
  {
    id: 'personalizar-neo',
    category: 'avatar',
    question: 'Posso personalizar a aparência do Neo?',
    answer:
      'No momento o Neo tem uma identidade visual fixa pensada para refletir os valores da SoulUp. Em sprints futuras, vamos adicionar opções de personalização (cor, nome e estilo) — fique de olho no nosso roadmap em Sobre.',
  },
  {
    id: 'como-funcionam-pontos',
    category: 'pontos',
    question: 'Como funcionam os pontos?',
    answer:
      'Cada ação sustentável dentro da plataforma é avaliada e gera pontos automaticamente. A pontuação varia conforme o impacto e a complexidade da missão. Você pode usar seus pontos para subsidiar a conta de energia, resgatar vouchers em parceiros sustentáveis ou acessar experiências exclusivas.',
  },
  {
    id: 'pontos-expiram',
    category: 'pontos',
    question: 'Os pontos expiram?',
    answer:
      'Os pontos têm validade de 12 meses a partir da data em que são conquistados. Você pode acompanhar todos os pontos no seu perfil, com data de validade clara para cada conjunto.',
  },
  {
    id: 'dados-seguros',
    category: 'seguranca',
    question: 'Meus dados pessoais estão seguros?',
    answer:
      'Sim. Seguimos integralmente a LGPD (Lei Geral de Proteção de Dados) e implementamos validação e sanitização de todas as entradas, autenticação segura com criptografia, controle de acesso por perfil e registro de logs e auditoria.',
  },
  {
    id: 'excluir-conta',
    category: 'seguranca',
    question: 'Como faço para excluir minha conta?',
    answer:
      'Você pode solicitar a exclusão da sua conta diretamente em Perfil → Configurações → Excluir conta. Todos os seus dados serão removidos em até 30 dias úteis, conforme a LGPD. Você também pode entrar em contato pelo nosso formulário se preferir.',
  },
]

export const FAQ_CATEGORY_LABELS: Record<string, string> = {
  todas: 'Todas',
  plataforma: 'Plataforma',
  avatar: 'Avatar',
  pontos: 'Pontos e recompensas',
  seguranca: 'Segurança e dados',
}
