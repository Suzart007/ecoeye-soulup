package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.Requisicoes.StatusVinculoRequest;
import br.com.fiap.soulup.dto.Requisicoes.VinculoRequest;
import br.com.fiap.soulup.model.Interacao;
import br.com.fiap.soulup.model.MemoriaUsuario;
import br.com.fiap.soulup.model.UsuarioAvatar;
import br.com.fiap.soulup.service.InteracaoService;
import br.com.fiap.soulup.service.MemoriaService;
import br.com.fiap.soulup.service.VinculoService;
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

/** Vinculos usuario x avatar (TB_USUARIOS_AVATARES), com as conversas e memorias de cada vinculo. */
@RestController
@RequestMapping("/vinculos")
public class VinculoController {

    private final VinculoService vinculoService;
    private final InteracaoService interacaoService;
    private final MemoriaService memoriaService;

    public VinculoController(VinculoService vinculoService, InteracaoService interacaoService,
                             MemoriaService memoriaService) {
        this.vinculoService = vinculoService;
        this.interacaoService = interacaoService;
        this.memoriaService = memoriaService;
    }

    /** GET /vinculos?usuarioId=1&avatarId=2&status=ATIVO (filtros opcionais) */
    @GetMapping
    public List<UsuarioAvatar> listar(@RequestParam(required = false) Long usuarioId,
                                      @RequestParam(required = false) Long avatarId,
                                      @RequestParam(required = false) String status) {
        return vinculoService.listar(usuarioId, avatarId, status);
    }

    @GetMapping("/{id}")
    public UsuarioAvatar buscar(@PathVariable long id) {
        return vinculoService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<UsuarioAvatar> vincular(@Valid @RequestBody VinculoRequest req) {
        UsuarioAvatar criado = vinculoService.vincular(req.usuarioId(), req.avatarId());
        return RespostaHttp.criado("/vinculos", criado.getId(), criado);
    }

    /** Ativa, inativa ou encerra o vinculo: { "status": "ENCERRADO" } */
    @PutMapping("/{id}/status")
    public UsuarioAvatar alterarStatus(@PathVariable long id, @Valid @RequestBody StatusVinculoRequest req) {
        return vinculoService.alterarStatus(id, req.status());
    }

    @GetMapping("/{id}/interacoes")
    public List<Interacao> interacoes(@PathVariable long id) {
        vinculoService.buscar(id);
        return interacaoService.listar(id, null);
    }

    @GetMapping("/{id}/memorias")
    public List<MemoriaUsuario> memorias(@PathVariable long id) {
        vinculoService.buscar(id);
        return memoriaService.listar(id, null, null);
    }
}
