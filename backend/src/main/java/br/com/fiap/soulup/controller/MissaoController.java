package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.model.Missao;
import br.com.fiap.soulup.model.UsuarioMissao;
import br.com.fiap.soulup.service.MissaoService;
import br.com.fiap.soulup.service.ParticipacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** CRUD de missoes sustentaveis (TB_MISSOES). */
@RestController
@RequestMapping("/missoes")
public class MissaoController {

    private final MissaoService missaoService;
    private final ParticipacaoService participacaoService;

    public MissaoController(MissaoService missaoService, ParticipacaoService participacaoService) {
        this.missaoService = missaoService;
        this.participacaoService = participacaoService;
    }

    /** GET /missoes?situacao=ATIVA */
    @GetMapping
    public List<Missao> listar(@RequestParam(required = false) String situacao) {
        return missaoService.listar(situacao);
    }

    @GetMapping("/{id}")
    public Missao buscar(@PathVariable long id) {
        return missaoService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Missao> cadastrar(@Valid @RequestBody Missao missao) {
        Missao criada = missaoService.cadastrar(missao);
        return RespostaHttp.criado("/missoes", criada.getId(), criada);
    }

    @PutMapping("/{id}")
    public Missao atualizar(@PathVariable long id, @Valid @RequestBody Missao missao) {
        return missaoService.atualizar(id, missao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        missaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    /** Quem participa ou participou da missao. */
    @GetMapping("/{id}/participacoes")
    public List<UsuarioMissao> participacoes(@PathVariable long id, @RequestParam(required = false) String status) {
        missaoService.buscar(id);
        return participacaoService.listar(null, id, status);
    }
}
