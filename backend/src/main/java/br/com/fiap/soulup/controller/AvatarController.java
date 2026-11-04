package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.model.Avatar;
import br.com.fiap.soulup.service.AvatarService;
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

/** CRUD de avatares (TB_AVATARES). */
@RestController
@RequestMapping("/avatares")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    /** GET /avatares?situacao=ATIVO */
    @GetMapping
    public List<Avatar> listar(@RequestParam(required = false) String situacao) {
        return avatarService.listar(situacao);
    }

    @GetMapping("/{id}")
    public Avatar buscar(@PathVariable long id) {
        return avatarService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Avatar> cadastrar(@Valid @RequestBody Avatar avatar) {
        Avatar criado = avatarService.cadastrar(avatar);
        return RespostaHttp.criado("/avatares", criado.getId(), criado);
    }

    @PutMapping("/{id}")
    public Avatar atualizar(@PathVariable long id, @Valid @RequestBody Avatar avatar) {
        return avatarService.atualizar(id, avatar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        avatarService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
