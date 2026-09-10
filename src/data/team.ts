// commit 03/29 (Henry): feat(data) - cria dados de navegação e da equipe
import type { TeamMember } from '../types'

export const TEAM_MEMBERS: TeamMember[] = [
  {
    id: 'guilherme-suzart',
    name: 'Guilherme Souza Suzart',
    role: 'Front-End · Arquitetura e Interatividade',
    rm: '569884',
    turma: '1TDSPK',
    linkedin:
      'https://www.linkedin.com/in/guilherme-suzart-ba83193b3?utm_source=share_via&utm_content=profile&utm_medium=member_ios',
    github: 'https://github.com/Suzart007',
    bio: 'Responsável pela arquitetura da aplicação React: rotas, hooks, componentes de UI reutilizáveis, formulário de contato e a lógica de busca/filtro do FAQ.',
    photoUrl: '/team/guilherme.jpeg',
  },
  {
    id: 'kaio-chiappinelli',
    name: 'Kaio Marcus Macedo Chiappinelli',
    role: 'Front-End · Solução e Experiência',
    rm: '573365',
    turma: '1TDSPK',
    linkedin: 'https://www.linkedin.com/in/kaio-chiappinelli-183106322',
    github: 'https://github.com/KaioChiappinelli',
    bio: 'Atua nas páginas de Solução e Experiência do Usuário, com foco em componentização e responsividade.',
    photoUrl: '/team/kaio.jpeg',
  },
  {
    id: 'matheus-vecchi',
    name: 'Matheus Vecchi',
    role: 'Front-End · Home e FAQ',
    rm: '569749',
    turma: '1TDSPK',
    linkedin:
      'https://www.linkedin.com/in/matheusvecchi-?utm_source=share_via&utm_content=profile&utm_medium=member_ios',
    github: 'https://github.com/ogvecchi',
    bio: 'Responsável pela Home e pela página de FAQ, incluindo busca e filtros por categoria.',
  },
  {
    id: 'gustavo-tenorio',
    name: 'Gustavo Tenório',
    role: 'Front-End · Contato',
    rm: 'A definir',
    turma: '1TDSPK',
    linkedin: '#',
    github: '#',
    bio: 'Responsável pela página de Contato, pelo favicon do projeto e pela documentação (README).',
    photoUrl: '/team/gustavo-tenorio.jpeg',
  },
  {
    id: 'henry',
    name: 'Henry',
    role: 'Front-End · Configuração e Dados',
    rm: 'A definir',
    turma: '1TDSPK',
    linkedin: '#',
    github: '#',
    bio: 'Responsável pela configuração inicial do projeto (Vite, TypeScript, Tailwind) e pelas bases de dados estáticas (navegação, equipe e FAQ).',
  },
]
