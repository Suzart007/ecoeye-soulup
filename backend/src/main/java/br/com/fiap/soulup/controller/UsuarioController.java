package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.Respostas.PerfilUsuario;
import br.com.fiap.soulup.model.LogSeguranca;
import br.com.fiap.soulup.model.SugestaoAvatar;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.model.UsuarioAvatar;
import br.com.fiap.soulup.model.UsuarioMissao;
import br.com.fiap.soulup.service.LogSegurancaService;
import br.com.fiap.soulup.service.ParticipacaoService;
import br.com.fiap.soulup.service.SugestaoService;
import br.com.fiap.soulup.service.UsuarioService;
import br.com.fiap.soulup.service.VinculoService;
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

/** CRUD de usuarios (TB_USUARIOS) e consultas do historico de cada usuario. */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ParticipacaoService participacaoService;
    private final SugestaoService sugestaoService;
    private final VinculoService vinculoService;
    private final LogSegurancaService logService;

    public UsuarioController(UsuarioService usuarioService, ParticipacaoService participacaoService,
                             SugestaoService sugestaoService, VinculoService vinculoService,
                             LogSegurancaService logService) {
        this.usuarioService = usuarioService;
        this.participacaoService = participacaoService;
        this.sugestaoService = sugestaoService;
        this.vinculoService = vinculoService;
        this.logService = logService;
    }

    /** GET /usuarios?situacao=ATIVO */
    @GetMapping
    public List<Usuario> listar(@RequestParam(required = false) String situacao) {
        return usuarioService.listar(situacao);
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable long id) {
        return usuarioService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody Usuario usuario) {
        Usuario criado = usuarioService.cadastrar(usuario);
        return RespostaHttp.criado("/usuarios", criado.getId(), criado);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable long id, @Valid @RequestBody Usuario usuario) {
        return usuarioService.atualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    /** Pontos, nivel de engajamento e resumo das missoes do usuario. */
    @GetMapping("/{id}/perfil")
    public PerfilUsuario perfil(@PathVariable long id) {
        return usuarioService.perfil(id);
    }

    @GetMapping("/{id}/participacoes")
    public List<UsuarioMissao> participacoes(@PathVariable long id, @RequestParam(required = false) String status) {
        usuarioService.buscar(id);
        return participacaoService.listar(id, null, status);
    }

    @GetMapping("/{id}/sugestoes")
    public List<SugestaoAvatar> sugestoes(@PathVariable long id, @RequestParam(required = false) String status) {
        usuarioService.buscar(id);
        return sugestaoService.listar(id, null, status);
    }

    @GetMapping("/{id}/vinculos")
    public List<UsuarioAvatar> vinculos(@PathVariable long id) {
        usuarioService.buscar(id);
        return vinculoService.listar(id, null, null);
    }

    @GetMapping("/{id}/logs")
    public List<LogSeguranca> logs(@PathVariable long id) {
        usuarioService.buscar(id);
        return logService.listar(id, null);
    }
}
