package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.Requisicoes.MemoriaRequest;
import br.com.fiap.soulup.model.MemoriaUsuario;
import br.com.fiap.soulup.service.MemoriaService;
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

/** O que o avatar lembra sobre cada usuario (TB_MEMORIAS_USUARIO). */
@RestController
@RequestMapping("/memorias")
public class MemoriaController {

    private final MemoriaService memoriaService;

    public MemoriaController(MemoriaService memoriaService) {
        this.memoriaService = memoriaService;
    }

    /** GET /memorias?usuarioAvatarId=1&categoria=ROTINA&status=ATIVA (filtros opcionais) */
    @GetMapping
    public List<MemoriaUsuario> listar(@RequestParam(required = false) Long usuarioAvatarId,
                                       @RequestParam(required = false) String categoria,
                                       @RequestParam(required = false) String status) {
        return memoriaService.listar(usuarioAvatarId, categoria, status);
    }

    @GetMapping("/{id}")
    public MemoriaUsuario buscar(@PathVariable long id) {
        return memoriaService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<MemoriaUsuario> cadastrar(@Valid @RequestBody MemoriaRequest req) {
        MemoriaUsuario criada = memoriaService.cadastrar(req);
        return RespostaHttp.criado("/memorias", criada.getId(), criada);
    }

    @PutMapping("/{id}")
    public MemoriaUsuario atualizar(@PathVariable long id, @Valid @RequestBody MemoriaRequest req) {
        return memoriaService.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        memoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
