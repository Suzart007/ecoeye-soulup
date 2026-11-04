package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.Requisicoes.LogRequest;
import br.com.fiap.soulup.dto.Respostas.RegistroLog;
import br.com.fiap.soulup.model.LogSeguranca;
import br.com.fiap.soulup.service.LogSegurancaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Logs de seguranca e auditoria (TB_LOGS_SEGURANCA). */
@RestController
@RequestMapping("/logs")
public class LogSegurancaController {

    private final LogSegurancaService logService;

    public LogSegurancaController(LogSegurancaService logService) {
        this.logService = logService;
    }

    /** GET /logs?usuarioId=8&tipoEvento=LOGIN_FALHA (filtros opcionais) */
    @GetMapping
    public List<LogSeguranca> listar(@RequestParam(required = false) Long usuarioId,
                                     @RequestParam(required = false) String tipoEvento) {
        return logService.listar(usuarioId, tipoEvento);
    }

    @GetMapping("/{id}")
    public LogSeguranca buscar(@PathVariable long id) {
        return logService.buscar(id);
    }

    /** Registra um evento; a 3a LOGIN_FALHA seguida bloqueia a conta automaticamente. */
    @PostMapping
    public ResponseEntity<RegistroLog> registrar(@Valid @RequestBody LogRequest req, HttpServletRequest http) {
        RegistroLog registro = logService.registrar(req, ipDoCliente(http));
        return RespostaHttp.criado("/logs", registro.log().getId(), registro);
    }

    /** Na Vercel a requisicao passa por um proxy, que informa o IP original em X-Forwarded-For. */
    private static String ipDoCliente(HttpServletRequest http) {
        String encaminhado = http.getHeader("X-Forwarded-For");
        if (encaminhado != null && !encaminhado.isBlank()) {
            return encaminhado.split(",")[0].trim();
        }
        return http.getRemoteAddr();
    }
}
