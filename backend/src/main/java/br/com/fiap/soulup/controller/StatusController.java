package br.com.fiap.soulup.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Rotas de verificacao: GET / (API no ar) e GET /status/banco (conexao com o Oracle). */
@RestController
public class StatusController {

    private final JdbcTemplate jdbc;

    public StatusController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/")
    public Map<String, Object> status() {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("status", "online");
        corpo.put("api", "SoulUp API - Equipe EcoEye");
        corpo.put("versao", "1.0.0");
        corpo.put("horario", LocalDateTime.now().withNano(0));
        corpo.put("recursos", List.of("/usuarios", "/missoes", "/avatares", "/vinculos", "/interacoes",
                "/participacoes", "/sugestoes", "/logs", "/memorias", "/ranking", "/status/banco"));
        return corpo;
    }

    /** Executa um SELECT simples no Oracle para conferir usuario, senha e rede. */
    @GetMapping("/status/banco")
    public ResponseEntity<Map<String, Object>> statusBanco() {
        Map<String, Object> corpo = new LinkedHashMap<>();
        try {
            Integer totalUsuarios = jdbc.queryForObject("SELECT COUNT(*) FROM TB_USUARIOS", Integer.class);
            corpo.put("banco", "conectado");
            corpo.put("usuariosCadastrados", totalUsuarios);
            return ResponseEntity.ok(corpo);
        } catch (Exception e) {
            corpo.put("banco", "indisponivel");
            corpo.put("mensagem", "Não foi possível consultar a TB_USUARIOS. Confira DB_USER/DB_PASSWORD, "
                    + "a rede da FIAP e se o modelo.sql já foi executado.");
            corpo.put("detalhe", causaRaiz(e));
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(corpo);
        }
    }

    private static String causaRaiz(Throwable erro) {
        Throwable causa = erro;
        while (causa.getCause() != null && causa.getCause() != causa) {
            causa = causa.getCause();
        }
        String mensagem = causa.getMessage() == null ? causa.getClass().getSimpleName() : causa.getMessage();
        return mensagem.split("\\R")[0];
    }
}
