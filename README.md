# SoulUp — Avatar Inteligente e Interativo

> **Challenge 2026 — FIAP × Prospera**
> **Turma:** 1TDSPK (Fevereiro) · **Grupo:** Nexus

O **SoulUp** é um ecossistema digital sustentável que conecta o engajamento diário de usuários a benefícios físicos e econômicos reais, como descontos diretos na fatura de energia elétrica. 

Esta entrega é focada no front-end responsivo do produto e na modelagem visual do **Desafio 3 (Avatar Inteligente)**. A personagem **NEO** atua como um guia gamificado que realiza onboarding personalizado, sugere missões ecológicas de forma contextual e gerencia o progresso e recompensas dos usuários.

---

## 🛠️ Stack Tecnológica & Padrões

Seguindo estritamente as diretrizes e restrições do Challenge FIAP, a interface foi desenvolvida **sem o uso de frameworks, bibliotecas ou pré-processadores externos** (Vanilla Stack):

* **HTML5 Semântico**: Tags estruturais e atributos para garantir acessibilidade (`role`, `aria-*`, `skip-link`).
* **CSS3 Avançado**: Arquitetura modular de estilos, variáveis nativas (:root), layout responsivo baseado em **Flexbox** e **CSS Grid**, adotando a metodologia *Mobile-First*.
* **JavaScript Nativo (Vanilla ES6)**: Manipulação dinâmica do DOM para componentes interativos e lógica de negócios local.
* **Git & GitHub**: Fluxo de versionamento distribuído e colaboração através de branches estruturadas por feature.

---

## 📂 Arquitetura de Páginas e Soluções

O projeto foi estruturado de forma modular para cobrir todos os requisitos obrigatórios e detalhar a proposta de valor:

1. **Home (`index.html`)**: Banner principal (Hero), apresentação dos pilares da plataforma (sustentabilidade, gamificação e IA) e CTAs para conversão.
2. **Sobre o Projeto (`sobre.html`)**: Contexto histórico da fusão entre Prospera e SoulPrime, pilares de impacto e o roadmap técnico de sprints para 2026.
3. **Explicação da Solução (`solucao.html`)**: Matriz comparativa de problemas (Cenário Sem o Avatar vs. Cenário Com a Luma), capacidades do agente cognitivo e métricas de impacto projetadas.
4. **Experiência do Usuário (`experiencia.html`)**: Painel visual simulado (Mockup do App com saldo de pontos e missões da semana) e o mapeamento da jornada do usuário em 5 macro-etapas.
5. **Central de Ajuda / FAQ (`faq.html`)**: Interface de autoatendimento com componente *Accordion* interativo, filtro dinâmico por categoria e busca em tempo real com tratamento de estado vazio.
6. **Formulário de Contato (`contato.html`)**: Canais oficiais de atendimento e formulário de envio robusto com máscara em tempo real para telefone brasileiro e validações de segurança client-side.
7. **Equipe de Desenvolvimento (`integrantes.html`)**: Card de identificação acadêmica e profissional de todos os desenvolvedores envolvidos no projeto.

---

## 🔄 Funcionalidades Interativas (JavaScript)

As interações nativas desenvolvidas para melhorar a usabilidade da plataforma incluem:
* **Menu Hambúrguer Responsivo**: Gerenciamento de estado de abertura/fechamento da navegação móvel e controle de overflow do body.
* **Menu Link Highlighter**: Leitura automática do pathname da URL para marcar visualmente o link ativo do cabeçalho.
* **Mecanismo de FAQ Dinâmico**: Controle de transição e altura (`scrollHeight`) das respostas do FAQ, combinado com escuta de input para filtros imediatos de palavras-chave.
* **Validador Estatístico de Formulário**: Lógica para intercepção de inputs inválidos, aplicação de masks dinâmicas de telefone `(XX) XXXXX-XXXX` e exibição de feedbacks customizados de sucesso ou erro.

---

## 👥 Organização do Grupo & Commits

Para atender às regras de distribuição igualitária e comprovação de autoria do Challenge, o desenvolvimento de arquivos e as branches foram divididas estritamente conforme a organização abaixo:

* **Patrick Ariel** (RM 573589) — *Representante do Grupo* | Responsável pela **Estrutura Base + Home** (`index.html`, CSS global, menu responsivo móvel e arquitetura base do projeto).
* **Rafael Rodrigues** (RM 564303) — Responsável pelas **Páginas Sobre + Equipe** (design, layout e estruturação semântica de `sobre.html` e `integrantes.html`).
* **Guilherme Suzart** (RM 569884) — Responsável pelo **FAQ + Interatividade** (arquitetura de `faq.html` e lógicas de manipulação de buscas e accordion em `faq.js`).
* **Gustavo Kerr** (RM 572418) — Responsável por **Contato + Validação** (página `contato.html` e algoritmos de validação de dados e máscara de Regex em `contato.js`).
* **Franco Jered** (RM 572901) — Responsável pelas **Páginas da Solução** (desenvolvimento, escrita técnica e mockups visuais em `solucao.html` e `experiencia.html`).

---

## 🚀 Execução Local

Como a aplicação utiliza estritamente recursos nativos da Web API, ela pode ser inicializada sem a necessidade de servidores ou instaladores de pacotes:

1. Efetue o clone do repositório remoto:
   ```bash ;;
   git clone [https://github.com/felipecvo-fiap-fde-26-fev/ProjetoSoulUP.git](https://github.com/felipecvo-fiap-fde-26-fev/ProjetoSoulUP.git)"# ecoeye-soulup" 
