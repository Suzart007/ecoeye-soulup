package br.com.fiap.soulup;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes de ponta a ponta da API (controller -> service -> repository -> banco).
 * Cada teste roda em uma transacao desfeita no final, entao sempre parte da carga inicial.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class SoulUpApiTest {

    @Autowired
    private MockMvc mvc;

    private ResultActions enviar(String metodo, String url, String json) throws Exception {
        var requisicao = switch (metodo) {
            case "POST" -> post(url);
            case "PUT" -> put(url);
            default -> throw new IllegalArgumentException(metodo);
        };
        return mvc.perform(requisicao.contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Nested
    class Status {

        @Test
        void raizInformaQueAApiEstaOnline() throws Exception {
            mvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("online"));
        }

        @Test
        void statusDoBancoConsultaAsTabelas() throws Exception {
            mvc.perform(get("/status/banco"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.banco").value("conectado"))
                    .andExpect(jsonPath("$.usuariosCadastrados").value(10));
        }

        @Test
        void corsLiberaOFrontLocal() throws Exception {
            mvc.perform(options("/usuarios")
                            .header("Origin", "http://localhost:5173")
                            .header("Access-Control-Request-Method", "POST"))
                    .andExpect(status().isOk())
                    .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"));
        }

        @Test
        void corsLiberaOFrontPublicadoNaVercelEBloqueiaOutrosSites() throws Exception {
            mvc.perform(options("/missoes")
                            .header("Origin", "https://ecoeye-soulup.vercel.app")
                            .header("Access-Control-Request-Method", "PUT"))
                    .andExpect(status().isOk())
                    .andExpect(header().string("Access-Control-Allow-Origin", "https://ecoeye-soulup.vercel.app"));
            mvc.perform(options("/missoes")
                            .header("Origin", "https://site-qualquer.com")
                            .header("Access-Control-Request-Method", "PUT"))
                    .andExpect(status().isForbidden());
        }

        @Test
        void rotaInexistenteEParametroInvalidoRespondemEmJson() throws Exception {
            mvc.perform(get("/nao-existe"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value(containsString("Rota não encontrada")));
            mvc.perform(get("/usuarios/abc"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value(containsString("'id'")));
        }
    }

    /** Rotas usadas pelo front-end React (paginas Usuarios). */
    @Nested
    class Usuarios {

        @Test
        void listaACargaInicialNoFormatoDoFront() throws Exception {
            mvc.perform(get("/usuarios"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(10)))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].nome").value("Ana Beatriz Lima"))
                    .andExpect(jsonPath("$[0].email").value("ana.lima@example.com"))
                    .andExpect(jsonPath("$[0].dataCadastro").value("2026-01-12"))
                    .andExpect(jsonPath("$[0].situacao").value("ATIVO"));
            mvc.perform(get("/usuarios").param("situacao", "INATIVO"))
                    .andExpect(jsonPath("$", hasSize(2)));
        }

        @Test
        void crudCompleto() throws Exception {
            enviar("POST", "/usuarios", """
                    {"nome": "  Teste   da Silva ", "email": "Teste@SoulUp.com", "situacao": "ATIVO", "dataCadastro": "%s"}
                    """.formatted(LocalDate.now()))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", endsWith("/usuarios/11")))
                    .andExpect(jsonPath("$.id").value(11))
                    .andExpect(jsonPath("$.nome").value("Teste da Silva"))
                    .andExpect(jsonPath("$.email").value("teste@soulup.com"));

            mvc.perform(get("/usuarios/11")).andExpect(jsonPath("$.nome").value("Teste da Silva"));

            enviar("PUT", "/usuarios/11", """
                    {"nome": "Teste Editado", "email": "teste@soulup.com", "situacao": "INATIVO"}
                    """)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.situacao").value("INATIVO"))
                    .andExpect(jsonPath("$.dataCadastro").value(LocalDate.now().toString()));

            mvc.perform(delete("/usuarios/11")).andExpect(status().isNoContent());
            mvc.perform(get("/usuarios/11"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Usuário de código 11 não encontrado(a)."));
        }

        @Test
        void emailRepetidoRetorna409() throws Exception {
            enviar("POST", "/usuarios", """
                    {"nome": "Ana Repetida", "email": "ANA.LIMA@example.com", "situacao": "ATIVO"}
                    """)
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.mensagem").value(containsString("ana.lima@example.com")));
        }

        @Test
        void dadosInvalidosRetornam400ComOsCampos() throws Exception {
            enviar("POST", "/usuarios", """
                    {"nome": "", "email": "sem-arroba", "situacao": "SUSPENSO"}
                    """)
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.campos.email").value("Informe um e-mail válido."))
                    .andExpect(jsonPath("$.campos.situacao").exists())
                    .andExpect(jsonPath("$.campos.nome").exists());
        }

        @Test
        void usuarioComHistoricoNaoEExcluido() throws Exception {
            mvc.perform(delete("/usuarios/1"))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.mensagem").value(containsString("INATIVO")));
        }

        @Test
        void usuarioQueDeixaDeSerAtivoTemSugestoesExpiradas() throws Exception {
            enviar("PUT", "/usuarios/4", """
                    {"nome": "Diego Fernandes Alves", "email": "diego.alves@example.com", "situacao": "INATIVO"}
                    """).andExpect(status().isOk());
            mvc.perform(get("/sugestoes/9")).andExpect(jsonPath("$.status").value("EXPIRADA"));
        }

        @Test
        void perfilSomaOsPontosDasMissoesConcluidas() throws Exception {
            mvc.perform(get("/usuarios/1/perfil"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.pontos").value(110))
                    .andExpect(jsonPath("$.nivel").value("Engajado"))
                    .andExpect(jsonPath("$.pontosParaProximoNivel").value(140))
                    .andExpect(jsonPath("$.missoesConcluidas").value(2))
                    .andExpect(jsonPath("$.missoesEmAndamento").value(2));
        }

        @Test
        void rankingSegueAConsultaC1() throws Exception {
            mvc.perform(get("/ranking"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(6)))
                    .andExpect(jsonPath("$[0].nome").value("Camila Rocha Mendes"))
                    .andExpect(jsonPath("$[0].pontos").value(135))
                    .andExpect(jsonPath("$[0].posicao").value(1))
                    .andExpect(jsonPath("$[5].nome").value("Isabela Moura Freitas"));
        }
    }

    /** Rotas usadas pelo front-end React (paginas Missoes). */
    @Nested
    class Missoes {

        @Test
        void listaACargaInicialNoFormatoDoFront() throws Exception {
            mvc.perform(get("/missoes"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(11)))
                    .andExpect(jsonPath("$[2].id").value(3))
                    .andExpect(jsonPath("$[2].titulo").value("Banho Consciente"))
                    .andExpect(jsonPath("$[2].pontuacao").value(50))
                    .andExpect(jsonPath("$[2].situacao").value("ATIVA"));
        }

        @Test
        void crudCompleto() throws Exception {
            enviar("POST", "/missoes", """
                    {"titulo": "Garrafa Reutilizável", "descricao": "Usar garrafa reutilizável por 15 dias seguidos.", "pontuacao": 20, "situacao": "ATIVA"}
                    """)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(12));
            enviar("PUT", "/missoes/12", """
                    {"titulo": "Garrafa Reutilizável", "descricao": "Usar garrafa reutilizável por 30 dias seguidos.", "pontuacao": 35, "situacao": "INATIVA"}
                    """)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.pontuacao").value(35));
            mvc.perform(delete("/missoes/12")).andExpect(status().isNoContent());
            mvc.perform(get("/missoes/12")).andExpect(status().isNotFound());
        }

        @Test
        void pontuacaoForaDoIntervaloRetorna400() throws Exception {
            enviar("POST", "/missoes", """
                    {"titulo": "Missão inválida", "descricao": "Pontuação acima do CHECK do banco.", "pontuacao": 150, "situacao": "ATIVA"}
                    """)
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.campos.pontuacao").value("A pontuação máxima é 100."));
        }

        @Test
        void missaoComHistoricoNaoEExcluida() throws Exception {
            mvc.perform(delete("/missoes/1"))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.mensagem").value(containsString("ENCERRADA")));
        }
    }

    @Nested
    class Participacoes {

        @Test
        void cicloCompletoAteConcluirAMissao() throws Exception {
            enviar("POST", "/participacoes", """
                    {"usuarioId": 4, "missaoId": 1}
                    """)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(26))
                    .andExpect(jsonPath("$.status").value("PENDENTE"))
                    .andExpect(jsonPath("$.progresso").value(0));

            enviar("PUT", "/participacoes/26/progresso", "{\"progresso\": 40}")
                    .andExpect(jsonPath("$.participacao.status").value("EM_ANDAMENTO"))
                    .andExpect(jsonPath("$.pontosGanhos").value(0));

            enviar("PUT", "/participacoes/26/progresso", "{\"progresso\": 20}")
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.mensagem").value(containsString("regredir")));

            enviar("PUT", "/participacoes/26/progresso", "{\"progresso\": 100}")
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.participacao.status").value("CONCLUIDA"))
                    .andExpect(jsonPath("$.participacao.dataConclusao").value(LocalDate.now().toString()))
                    .andExpect(jsonPath("$.pontosGanhos").value(40))
                    .andExpect(jsonPath("$.pontosTotais").value(40));

            enviar("POST", "/participacoes", """
                    {"usuarioId": 4, "missaoId": 1}
                    """)
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.mensagem").value(containsString("uma única vez")));
        }

        @Test
        void usuarioBloqueadoEMissaoEncerradaNaoIniciam() throws Exception {
            enviar("POST", "/participacoes", "{\"usuarioId\": 8, \"missaoId\": 1}")
                    .andExpect(status().isUnprocessableEntity());
            enviar("POST", "/participacoes", "{\"usuarioId\": 1, \"missaoId\": 10}")
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.mensagem").value(containsString("ENCERRADA")));
        }

        @Test
        void naoPermiteDuasParticipacoesAbertas() throws Exception {
            enviar("POST", "/participacoes", "{\"usuarioId\": 1, \"missaoId\": 4}")
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    class Sugestoes {

        @Test
        void regra10BloqueiaSugestaoParaUsuarioBloqueado() throws Exception {
            enviar("POST", "/sugestoes/gerar", "{\"usuarioId\": 8}")
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.mensagem").value(containsString("RN10")));
        }

        @Test
        void avatarSugereAMissaoMaisLeveParaIniciantesEAceitarIniciaAMissao() throws Exception {
            enviar("POST", "/sugestoes/gerar", "{\"usuarioId\": 4}")
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(19))
                    .andExpect(jsonPath("$.avatarId").value(2))
                    .andExpect(jsonPath("$.missaoId").value(9))
                    .andExpect(jsonPath("$.status").value("ENVIADA"))
                    .andExpect(jsonPath("$.texto").value(containsString("Sacola Retornável")));

            mvc.perform(put("/sugestoes/19/aceitar"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sugestao.status").value("ACEITA"))
                    .andExpect(jsonPath("$.participacao.missaoId").value(9))
                    .andExpect(jsonPath("$.participacao.status").value("PENDENTE"));

            mvc.perform(put("/sugestoes/19/recusar"))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void usuarioEngajadoRecebeAMissaoDeMaiorImpacto() throws Exception {
            enviar("POST", "/sugestoes/gerar", "{\"usuarioId\": 1}")
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.missaoId").value(6)) // a composteira (90) foi recusada pela Ana
                    .andExpect(jsonPath("$.texto").value(containsString("Engajado")));
        }
    }

    @Nested
    class Seguranca {

        @Test
        void tresFalhasSeguidasBloqueiamAConta() throws Exception {
            String falha = """
                    {"usuarioId": 2, "tipoEvento": "LOGIN_FALHA", "descricao": "Senha incorreta informada.", "enderecoIp": "203.0.113.5"}
                    """;
            enviar("POST", "/logs", falha).andExpect(jsonPath("$.usuarioBloqueado").value(false));
            enviar("POST", "/logs", falha).andExpect(jsonPath("$.usuarioBloqueado").value(false));
            enviar("POST", "/logs", falha)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.usuarioBloqueado").value(true));

            mvc.perform(get("/usuarios/2")).andExpect(jsonPath("$.situacao").value("BLOQUEADO"));
            mvc.perform(get("/logs").param("usuarioId", "2").param("tipoEvento", "BLOQUEIO_CONTA"))
                    .andExpect(jsonPath("$", hasSize(1)));

            enviar("POST", "/logs", """
                    {"usuarioId": 2, "tipoEvento": "LOGIN_SUCESSO", "descricao": "Login realizado."}
                    """)
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void ipDaRequisicaoEUsadoQuandoNaoInformado() throws Exception {
            enviar("POST", "/logs", """
                    {"usuarioId": 1, "tipoEvento": "LOGIN_SUCESSO", "descricao": "Login pelo navegador."}
                    """)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.log.enderecoIp").value("127.0.0.1"));
        }
    }

    @Nested
    class AvatarVinculosConversasEMemorias {

        @Test
        void vinculoSegueAsRegrasDeStatus() throws Exception {
            enviar("POST", "/vinculos", "{\"usuarioId\": 2, \"avatarId\": 3}")
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(15))
                    .andExpect(jsonPath("$.status").value("ATIVO"));
            enviar("POST", "/vinculos", "{\"usuarioId\": 2, \"avatarId\": 3}")
                    .andExpect(status().isConflict());
            enviar("POST", "/vinculos", "{\"usuarioId\": 2, \"avatarId\": 5}")
                    .andExpect(status().isUnprocessableEntity());
            enviar("PUT", "/vinculos/15/status", "{\"status\": \"ENCERRADO\"}")
                    .andExpect(jsonPath("$.status").value("ENCERRADO"));
            enviar("PUT", "/vinculos/15/status", "{\"status\": \"ATIVO\"}")
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void avatarRespondeQuandoARespostaNaoEEnviada() throws Exception {
            enviar("POST", "/interacoes", """
                    {"usuarioAvatarId": 1, "mensagemUsuario": "Oi, tudo bem?"}
                    """)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.tipo").value("CONVERSA"))
                    .andExpect(jsonPath("$.respostaAvatar").value(containsString("Ana")))
                    .andExpect(jsonPath("$.respostaAvatar").value(containsString("Gaia")));
            enviar("POST", "/interacoes", """
                    {"usuarioAvatarId": 5, "mensagemUsuario": "Vínculo encerrado"}
                    """)
                    .andExpect(status().isUnprocessableEntity());
            mvc.perform(get("/vinculos/1/interacoes")).andExpect(jsonPath("$", hasSize(4)));
        }

        @Test
        void memoriaTemChaveUnicaPorVinculo() throws Exception {
            String memoria = """
                    {"usuarioAvatarId": 1, "categoria": "PREFERENCIA", "chave": "%s", "valor": "Prefere receber dicas pela manhã.", "relevancia": 80}
                    """;
            enviar("POST", "/memorias", memoria.formatted("atividade_fisica"))
                    .andExpect(status().isConflict());
            enviar("POST", "/memorias", memoria.formatted("horario_dicas"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(18))
                    .andExpect(jsonPath("$.status").value("ATIVA"));
            enviar("PUT", "/memorias/18", memoria.formatted("horario_dicas").replace("80", "95"))
                    .andExpect(jsonPath("$.relevancia").value(95));
            mvc.perform(delete("/memorias/18")).andExpect(status().isNoContent());
        }

        @Test
        void avatarComHistoricoNaoEExcluidoEAvatarNovoSim() throws Exception {
            mvc.perform(delete("/avatares/1")).andExpect(status().isConflict());
            enviar("POST", "/avatares", """
                    {"nome": "Brisa", "descricao": "Avatar focado em qualidade do ar.", "personalidade": "Leve e curiosa"}
                    """)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.situacao").value("ATIVO"));
            mvc.perform(delete("/avatares/6")).andExpect(status().isNoContent());
        }
    }
}
