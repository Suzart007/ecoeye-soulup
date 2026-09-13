<!-- commit 29/29 (Gustavo Tenório): docs - atualiza README com identidade Ecoeye, stack e instruções do projeto -->

# SoulUp — Avatar Inteligente e Interativo

> **Challenge 2026 — FIAP × Prospera**
> **Turma:** 1TDSPK (Fevereiro) · **Equipe:** Ecoeye

O **SoulUp** é um ecossistema digital sustentável que conecta o engajamento diário de usuários a benefícios físicos e econômicos reais, como descontos diretos na fatura de energia elétrica. A personagem **Neo** atua como um guia gamificado que realiza onboarding personalizado, sugere missões ecológicas de forma contextual e gerencia o progresso e recompensas dos usuários.

Nesta **Sprint 3**, o projeto foi totalmente reestruturado: saiu do modelo de páginas HTML estáticas e passou a ser uma **Single Page Application em React + Vite + TypeScript**, com componentização, roteamento client-side e estilização 100% em Tailwind CSS.

---

## 🛠️ Tecnologias utilizadas

| Tecnologia | Uso no projeto |
| --- | --- |
| **React 18** | Interface e componentização |
| **Vite** | Build e ambiente de desenvolvimento |
| **TypeScript** | Tipagem estática em todo o código |
| **Tailwind CSS v4** | Estilização de toda a interface |
| **React Router DOM** | Navegação SPA, rotas estáticas e dinâmicas |
| **React Hook Form** | Validação do formulário de contato |
| **Git & GitHub** | Versionamento e histórico de desenvolvimento |

Sem bibliotecas de UI (Bootstrap, Material UI, Chakra UI), sem Axios e sem templates prontos — apenas as tecnologias acima, conforme as regras do Challenge.

---

## 📂 Estrutura de pastas

```
ecoeye-soulup/
├── public/
│   ├── favicon.svg
│   └── team/               # Fotos dos integrantes
├── src/
│   ├── components/
│   │   ├── contact/        # ContactForm, ContactChannelCard
│   │   ├── faq/             # FaqAccordionItem
│   │   ├── icons/           # Ícones SVG reutilizáveis
│   │   ├── layout/          # Header, Footer, Layout (Outlet)
│   │   ├── neo/              # NeoChatWidget (UI, sem lógica de IA)
│   │   ├── team/              # TeamMemberCard
│   │   └── common/             # Button, PageHero, SectionHeading, IconCard, StatCard, Reveal, ScrollToTop (100% autorais, sem libs de UI)
│   ├── data/                    # Dados estáticos (navegação, equipe, FAQ)
│   ├── hooks/                    # useDocumentTitle, useScrollReveal
│   ├── pages/                     # Uma página por rota (/src/pages)
│   ├── types/                      # Interfaces TypeScript compartilhadas
│   ├── App.tsx                      # Definição das rotas (React Router)
│   ├── main.tsx                      # Entry point da aplicação
│   └── index.css                      # Tailwind + design tokens do projeto
├── index.html
├── package.json
├── tsconfig.json / tsconfig.app.json / tsconfig.node.json
└── vite.config.ts
```

## 🗺️ Páginas e rotas

| Rota | Página | Observação |
| --- | --- | --- |
| `/` | Home | Hero, diferenciais, como funciona |
| `/sobre` | Sobre o projeto | Contexto, stack e roadmap |
| `/solucao` | Nossa Solução | Problema × solução, capacidades do Neo |
| `/experiencia` | Experiência do Usuário | Mockup do painel e jornada em 5 etapas |
| `/faq` | Perguntas Frequentes | Busca e filtro por categoria |
| `/contato` | Contato | Formulário com React Hook Form |
| `/integrantes` | Equipe | Grid com os integrantes |
| `/integrantes/:id` | Perfil do integrante | Rota **dinâmica** (`useParams` + `useNavigate`) |

---

## 🤖 Widget do Neo

O widget flutuante do Neo já está presente em todas as páginas, com toda a interface pronta (botão, painel, mensagem e campo de envio). **Ele ainda não está funcional de propósito**: a integração com a IA (Watson Assistant) está planejada para a próxima sprint. Por enquanto o campo de mensagem fica desabilitado.

---

## 🚀 Como executar localmente

Pré-requisitos: [Node.js](https://nodejs.org/) 18+ e npm.

```bash
# 1. Clone o repositório
git clone https://github.com/Suzart007/ecoeye-soulup.git
cd ecoeye-soulup

# 2. Instale as dependências
npm install

# 3. Rode o servidor de desenvolvimento
npm run dev
```

A aplicação abre em `http://localhost:5173`.

Outros comandos:

```bash
npm run build     # build de produção (TypeScript + Vite)
npm run preview   # serve o build de produção localmente
```

---

## 🔗 Repositório

[github.com/Suzart007/ecoeye-soulup](https://github.com/Suzart007/ecoeye-soulup)

---

## 👥 Equipe Ecoeye

| Integrante | RM | Turma | LinkedIn | GitHub |
| --- | --- | --- | --- | --- |
| Guilherme Souza Suzart | 569884 | 1TDSPK | [LinkedIn](https://www.linkedin.com/in/guilherme-suzart-ba83193b3) | [@Suzart007](https://github.com/Suzart007) |
| Kaio Marcus Macedo Chiappinelli | 573365 | 1TDSPK | [LinkedIn](https://www.linkedin.com/in/kaio-chiappinelli-183106322) | [@KaioChiappinelli](https://github.com/KaioChiappinelli) |
| Matheus Vecchi | 569749 | 1TDSPK | [LinkedIn](https://www.linkedin.com/in/matheusvecchi-) | [@ogvecchi](https://github.com/ogvecchi) |
| Gustavo Tenório | A definir | 1TDSPK | [LinkedIn](https://www.linkedin.com/in/gustavo-souza-9bb62a303) | [@gustavoTT10](https://github.com/gustavoTT10) |
| Henry Bascope | A definir | 1TDSPK | [LinkedIn](https://www.linkedin.com/in/henry-bascope-5a10933a1) | [@lupitakilla7-ui](https://github.com/lupitakilla7-ui) |

---

## 📬 Contato

Dúvidas sobre o projeto? Fale com a equipe pela [página de contato](/contato) da aplicação ou em `contato@soulup.fiap.br`.
