# SoulUp API — Equipe EcoEye

API REST em **Java 21 + Spring Boot 3.5** da disciplina *Domain Driven Design Using Java* (Challenge FIAP 2026 · Turma 1TDSPK).
Ela persiste os dados no **Oracle da FIAP** usando o modelo de **9 tabelas** da disciplina *Building Relational Database* e é consumida pelo front-end React do SoulUp.

| Integrante | RM |
| --- | --- |
| Guilherme Souza Suzart | 569884 |
| Matheus Vecchi | 569749 |
| Kaio Marcus Macedo Chiappinelli | 573365 |
| Henry Vitor Medeiros Bascope | 573576 |

---

## Como rodar no IntelliJ

1. **File > Open** e escolha esta pasta (`SoulUp`, a que tem o `pom.xml`). Use o **JDK 21**.
2. No SQL Developer, abra o [`modelo.sql`](modelo.sql) no seu schema Oracle da FIAP e execute como script (**F5**). Ele cria as 9 tabelas e a carga inicial (pode rodar de novo quando quiser zerar os dados).
3. Informe usuário e senha do Oracle de **uma** das formas abaixo (a senha nunca fica no código):
   - **Run > Edit Configurations > SoulUpApplication > Environment variables:** `DB_USER=rm000000;DB_PASSWORD=sua_senha`
   - ou copie o `.env.example` para `.env` nesta pasta e preencha (o `.env` está no `.gitignore`).
4. Rode a classe `br.com.fiap.soulup.SoulUpApplication` e abra:
   - <http://localhost:8080/> → `"status": "online"` (a API está no ar);
   - <http://localhost:8080/status/banco> → `"banco": "conectado"` (usuário, senha e tabelas OK).

### Variáveis de ambiente

| Variável | Obrigatória | Descrição |
| --- | --- | --- |
| `DB_USER` | sim | RM usado no Oracle da FIAP (ex.: `rm569884`) |
| `DB_PASSWORD` | sim | Senha do Oracle |
| `DB_URL` | não | Padrão `jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl` |
| `CORS_ORIGINS` | não | URLs do front liberadas, separadas por vírgula. Padrão `http://localhost:5173,http://127.0.0.1:5173,https://ecoeye-soulup*.vercel.app` |
| `PORT` | não | Porta HTTP. Padrão `8080` (no Docker/Vercel, `80`) |

> Os nomes das variáveis precisam ser exatamente esses: em maiúsculas e sem espaços. Se `DB_PASSWORD` estiver com outro nome (ex.: `DB_password` ou com espaço antes), a API avisa no console ao subir e `/status/banco` responde 503.

### Testes automatizados

`mvn test` sobe a API com um banco **H2 em memória no modo Oracle**, criado com a mesma estrutura e a mesma carga do `modelo.sql`, e testa as rotas e as regras de negócio (29 testes). No IntelliJ: botão direito em `src/test/java` > **Run 'All Tests'**.

---

## Arquitetura (pacote `br.com.fiap.soulup`)

| Pacote | Responsabilidade |
| --- | --- |
| `model` | Entidades de domínio, uma por tabela, com as regras de cada uma (ex.: `UsuarioMissao.atualizarProgresso`) |
| `repository` | DAOs com JDBC (`JdbcTemplate`): SQL de cada tabela e geração das chaves (MAX + 1) |
| `service` | Regras de negócio e transações (`@Transactional`) |
| `controller` | Endpoints REST e códigos HTTP |
| `dto` | Corpos de requisição/resposta que não são a entidade inteira |
| `exception` | Exceções de negócio e o `GlobalExceptionHandler` (erros em JSON) |
| `config` | CORS para o front-end |

| Tabela | Entidade | Endpoint |
| --- | --- | --- |
| `TB_USUARIOS` | `Usuario` | `/usuarios` |
| `TB_MISSOES` | `Missao` | `/missoes` |
| `TB_AVATARES` | `Avatar` | `/avatares` |
| `TB_USUARIOS_AVATARES` | `UsuarioAvatar` | `/vinculos` |
| `TB_INTERACOES` | `Interacao` | `/interacoes` |
| `TB_USUARIOS_MISSOES` | `UsuarioMissao` | `/participacoes` |
| `TB_SUGESTOES_AVATAR` | `SugestaoAvatar` | `/sugestoes` |
| `TB_LOGS_SEGURANCA` | `LogSeguranca` | `/logs` |
| `TB_MEMORIAS_USUARIO` | `MemoriaUsuario` | `/memorias` |

---

## Endpoints

Todas as listagens aceitam filtros opcionais por query string (ex.: `/missoes?situacao=ATIVA`, `/participacoes?usuarioId=1`).

| Método | Rota | O que faz |
| --- | --- | --- |
| GET | `/` | Status da API |
| GET | `/status/banco` | Testa a conexão com o Oracle |
| GET · POST | `/usuarios` | Lista · cadastra usuário |
| GET · PUT · DELETE | `/usuarios/{id}` | Busca · edita · exclui usuário |
| GET | `/usuarios/{id}/perfil` | Pontos, nível de engajamento e resumo das missões |
| GET | `/usuarios/{id}/participacoes` · `/sugestoes` · `/vinculos` · `/logs` | Histórico do usuário |
| GET | `/ranking` | Ranking dos usuários ativos por pontos |
| GET · POST | `/missoes` | Lista · cadastra missão |
| GET · PUT · DELETE | `/missoes/{id}` | Busca · edita · exclui missão |
| GET | `/missoes/{id}/participacoes` | Quem participa da missão |
| GET · POST | `/avatares` | Lista · cadastra avatar |
| GET · PUT · DELETE | `/avatares/{id}` | Busca · edita · exclui avatar |
| GET · POST | `/vinculos` | Lista · vincula usuário a avatar `{ "usuarioId": 2, "avatarId": 3 }` |
| GET | `/vinculos/{id}` · `/interacoes` · `/memorias` | Vínculo, conversas e memórias dele |
| PUT | `/vinculos/{id}/status` | `{ "status": "ATIVO" \| "INATIVO" \| "ENCERRADO" }` |
| GET · POST | `/interacoes` | Lista · envia mensagem `{ "usuarioAvatarId": 1, "mensagemUsuario": "Oi!" }` |
| GET · DELETE | `/interacoes/{id}` | Busca · exclui interação |
| GET · POST | `/participacoes` | Lista · inicia missão `{ "usuarioId": 4, "missaoId": 1 }` |
| GET | `/participacoes/{id}` | Busca participação |
| PUT | `/participacoes/{id}/progresso` | `{ "progresso": 100 }` (100% conclui e soma os pontos) |
| PUT | `/participacoes/{id}/cancelar` | Cancela a participação |
| GET | `/sugestoes` · `/sugestoes/{id}` | Lista · busca sugestões |
| POST | `/sugestoes/gerar` | O avatar escolhe a próxima missão `{ "usuarioId": 4 }` |
| PUT | `/sugestoes/{id}/aceitar` · `/recusar` | Responde a sugestão (aceitar já inicia a missão) |
| GET · POST | `/logs` | Lista · registra evento `{ "usuarioId": 2, "tipoEvento": "LOGIN_FALHA", "descricao": "Senha incorreta." }` |
| GET | `/logs/{id}` | Busca log |
| GET · POST | `/memorias` | Lista · cadastra memória do avatar |
| GET · PUT · DELETE | `/memorias/{id}` | Busca · edita · exclui memória |

### Formato usado pelo front-end

```jsonc
// Missão (TB_MISSOES)
{ "id": 3, "titulo": "Banho Consciente", "descricao": "Reduzir o tempo de banho para no máximo 5 minutos durante 20 dias.", "pontuacao": 50, "situacao": "ATIVA" }

// Usuário (TB_USUARIOS)
{ "id": 1, "nome": "Ana Beatriz Lima", "email": "ana.lima@example.com", "dataCadastro": "2026-01-12", "situacao": "ATIVO" }
```

### Erros

Todos os erros voltam em JSON com o campo `mensagem` (exibido pelo front):

```json
{ "status": 409, "erro": "Conflict", "mensagem": "Já existe um usuário cadastrado com o e-mail ana.lima@example.com.", "caminho": "/usuarios", "timestamp": "2026-11-03T20:15:00" }
```

| Código | Quando |
| --- | --- |
| 400 | Campo inválido (`campos` traz o erro de cada campo), JSON malformado ou parâmetro inválido |
| 404 | Registro ou rota não encontrados |
| 409 | E-mail repetido, vínculo/participação duplicados ou exclusão de registro com histórico |
| 422 | Regra de negócio violada (ex.: RN10, missão encerrada, progresso regredindo) |
| 503 | Banco Oracle indisponível (usuário/senha errados ou rede) |

---

## Regras de negócio

1. **E-mail único** por usuário, sem diferenciar maiúsculas/minúsculas (409).
2. **Pontuação da missão de 0 a 100** e situações restritas aos valores dos CHECKs do banco (400).
3. **Registros com histórico não são excluídos** (usuário, missão, avatar): a API responde 409 e orienta a inativar.
4. **Vínculo usuário-avatar** só entre usuário ATIVO e avatar ATIVO, sem duplicar vínculos ativos; vínculo ENCERRADO é definitivo.
5. **Conversas** só por vínculo ATIVO; se a resposta não for enviada, o avatar responde de acordo com o tipo (CONVERSA, SUGESTAO, MISSAO, SUPORTE).
6. **Participação em missões:** só usuário ATIVO inicia missão ATIVA; não pode ter duas abertas na mesma missão; cada missão rende pontos uma única vez.
7. **Progresso** de 0 a 100, sem regredir; 100% conclui a missão com a data de hoje (CHECK `ck_um_conclusao`).
8. **Pontos e nível de engajamento:** soma das missões concluídas → Iniciante (0), Engajado (100), Guardião Sustentável (250), Lenda SoulUp (500).
9. **Segurança:** 3 `LOGIN_FALHA` seguidas bloqueiam a conta (situação BLOQUEADO + log `BLOQUEIO_CONTA` automático); conta bloqueada não registra `LOGIN_SUCESSO`.
10. **RN10 — sugestões só para usuários ATIVOS** (validada na API e pela trigger `TRG_SUG_USUARIO_ATIVO`). O avatar sugere missões ATIVAS que o usuário nunca fez nem recusou: a mais leve para iniciantes e a de maior impacto para os demais. Aceitar a sugestão já inicia a missão. Quando o usuário ou a missão deixam de estar ativos, as sugestões pendentes expiram.
11. **Memórias do avatar:** só em vínculos ATIVOS, com chave única por vínculo (409).

---

## Deploy na Vercel (Docker)

A Vercel publica a API a partir do [`Dockerfile.vercel`](Dockerfile.vercel) desta pasta (Container Runtime, em beta em todos os planos). No repositório [Suzart007/ecoeye-soulup](https://github.com/Suzart007/ecoeye-soulup) a API fica na pasta `backend`, ao lado do front.

1. Em vercel.com: **Add New > Project** e importe o repositório `ecoeye-soulup`. Dê um nome ao projeto (ex.: `ecoeye-soulup-api`).
2. Em **Root Directory**, escolha a pasta `backend`. O **Framework Preset** deve aparecer como **Container** (a Vercel detecta o `Dockerfile.vercel` e o `vercel.json` desta pasta).
3. Em **Environment Variables**, cadastre `DB_USER` e `DB_PASSWORD`. `CORS_ORIGINS` só é necessária se a URL do front não começar com `https://ecoeye-soulup`.
4. Clique em **Deploy** e abra a URL gerada: `/` deve responder `"status": "online"` e `/status/banco` deve responder `"conectado"`.
5. No projeto do **front** na Vercel, cadastre `VITE_API_URL` com a URL da API e faça um novo deploy.

Sem uso por 5 minutos, a Vercel desliga o container: a primeira requisição depois disso demora alguns segundos, enquanto o Spring Boot sobe.
