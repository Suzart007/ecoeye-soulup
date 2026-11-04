package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.Requisicoes.ParticipacaoRequest;
import br.com.fiap.soulup.dto.Requisicoes.ProgressoRequest;
import br.com.fiap.soulup.dto.Respostas.ProgressoAtualizado;
import br.com.fiap.soulup.model.UsuarioMissao;
import br.com.fiap.soulup.service.ParticipacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Participacao dos usuarios nas missoes e progresso (TB_USUARIOS_MISSOES). */
@RestController
@RequestMapping("/participacoes")
public class ParticipacaoController {

    private final ParticipacaoService participacaoService;

    public ParticipacaoController(ParticipacaoService participacaoService) {
        this.participacaoService = participacaoService;
    }

    /** GET /participacoes?usuarioId=1&missaoId=4&status=EM_ANDAMENTO (filtros opcionais) */
    @GetMapping
    public List<UsuarioMissao> listar(@RequestParam(required = false) Long usuarioId,
                                      @RequestParam(required = false) Long missaoId,
                                      @RequestParam(required = false) String status) {
        return participacaoService.listar(usuarioId, missaoId, status);
    }

    @GetMapping("/{id}")
    public UsuarioMissao buscar(@PathVariable long id) {
        return participacaoService.buscar(id);
    }

    /** Inicia uma missao para o usuario: { "usuarioId": 1, "missaoId": 6 } */
    @PostMapping
    public ResponseEntity<UsuarioMissao> iniciar(@Valid @RequestBody ParticipacaoRequest req) {
        UsuarioMissao criada = participacaoService.iniciar(req.usuarioId(), req.missaoId());
        return RespostaHttp.criado("/participacoes", criada.getId(), criada);
    }

    /** Atualiza o progresso: { "progresso": 100 } conclui a missao e soma os pontos. */
    @PutMapping("/{id}/progresso")
    public ProgressoAtualizado atualizarProgresso(@PathVariable long id, @Valid @RequestBody ProgressoRequest req) {
        return participacaoService.atualizarProgresso(id, req.progresso());
    }

    @PutMapping("/{id}/cancelar")
    public UsuarioMissao cancelar(@PathVariable long id) {
        return participacaoService.cancelar(id);
    }
}
