// commit 06/29 (Guilherme): feat(types) - define tipos compartilhados da aplicação
export interface NavItem {
  label: string
  to: string
  isCta?: boolean
}

export interface TeamMember {
  id: string
  name: string
  role: string
  rm: string
  turma: string
  linkedin: string
  github: string
  bio: string
  /** Preenchido quando a foto do integrante for adicionada em public/team/ */
  photoUrl?: string
}

export type FaqCategory = 'plataforma' | 'avatar' | 'pontos' | 'seguranca'

export interface FaqEntry {
  id: string
  category: FaqCategory
  question: string
  answer: string
}

export interface ContactFormData {
  nome: string
  email: string
  telefone?: string
  assunto: string
  mensagem: string
  privacidade: boolean
}
