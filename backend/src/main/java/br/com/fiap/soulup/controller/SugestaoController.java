package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.Requisicoes.GerarSugestaoRequest;
import br.com.fiap.soulup.dto.Respostas.RespostaSugestao;
import br.com.fiap.soulup.model.SugestaoAvatar;
import br.com.fiap.soulup.service.SugestaoService;
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

/** Sugestoes do avatar (TB_SUGESTOES_AVATAR). */
@RestController
@RequestMapping("/sugestoes")
public class SugestaoController {

    private final SugestaoService sugestaoService;

    public SugestaoController(SugestaoService sugestaoService) {
        this.sugestaoService = sugestaoService;
    }

    /** GET /sugestoes?usuarioId=1&avatarId=1&status=ENVIADA (filtros opcionais) */
    @GetMapping
    public List<SugestaoAvatar> listar(@RequestParam(required = false) Long usuarioId,
                                       @RequestParam(required = false) Long avatarId,
                                       @RequestParam(required = false) String status) {
        return sugestaoService.listar(usuarioId, avatarId, status);
    }

    @GetMapping("/{id}")
    public SugestaoAvatar buscar(@PathVariable long id) {
        return sugestaoService.buscar(id);
    }

    /** O avatar escolhe a proxima missao do usuario: { "usuarioId": 4 } ou { "usuarioId": 4, "avatarId": 2 } */
    @PostMapping("/gerar")
    public ResponseEntity<SugestaoAvatar> gerar(@Valid @RequestBody GerarSugestaoRequest req) {
        SugestaoAvatar criada = sugestaoService.gerar(req.usuarioId(), req.avatarId());
        return RespostaHttp.criado("/sugestoes", criada.getId(), criada);
    }

    /** Aceita a sugestao e ja inicia a missao sugerida. */
    @PutMapping("/{id}/aceitar")
    public RespostaSugestao aceitar(@PathVariable long id) {
        return sugestaoService.aceitar(id);
    }

    @PutMapping("/{id}/recusar")
    public SugestaoAvatar recusar(@PathVariable long id) {
        return sugestaoService.recusar(id);
    }
}
