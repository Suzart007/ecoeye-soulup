// commit 03/29 (Henry): feat(data) - cria dados de navegação e da equipe
import type { NavItem } from '../types'

export const NAV_ITEMS: NavItem[] = [
  { label: 'Home', to: '/' },
  { label: 'Sobre', to: '/sobre' },
  { label: 'Solução', to: '/solucao' },
  { label: 'Experiência', to: '/experiencia' },
  { label: 'FAQ', to: '/faq' },
  { label: 'Equipe', to: '/integrantes' },
  { label: 'Falar conosco', to: '/contato', isCta: true },
]
