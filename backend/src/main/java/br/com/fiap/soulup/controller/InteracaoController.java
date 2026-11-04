package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.Requisicoes.InteracaoRequest;
import br.com.fiap.soulup.model.Interacao;
import br.com.fiap.soulup.service.InteracaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Conversas entre usuario e avatar (TB_INTERACOES). */
@RestController
@RequestMapping("/interacoes")
public class InteracaoController {

    private final InteracaoService interacaoService;

    public InteracaoController(InteracaoService interacaoService) {
        this.interacaoService = interacaoService;
    }

    /** GET /interacoes?usuarioAvatarId=1&tipo=MISSAO (filtros opcionais) */
    @GetMapping
    public List<Interacao> listar(@RequestParam(required = false) Long usuarioAvatarId,
                                  @RequestParam(required = false) String tipo) {
        return interacaoService.listar(usuarioAvatarId, tipo);
    }

    @GetMapping("/{id}")
    public Interacao buscar(@PathVariable long id) {
        return interacaoService.buscar(id);
    }

    /** O usuario envia uma mensagem; se "respostaAvatar" nao vier, o avatar responde sozinho. */
    @PostMapping
    public ResponseEntity<Interacao> registrar(@Valid @RequestBody InteracaoRequest req) {
        Interacao criada = interacaoService.registrar(req);
        return RespostaHttp.criado("/interacoes", criada.getId(), criada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        interacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
