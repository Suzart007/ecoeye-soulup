package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.Respostas.ItemRanking;
import br.com.fiap.soulup.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Ranking dos usuarios ativos pelos pontos das missoes concluidas (consulta C1 do banco). */
@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final UsuarioService usuarioService;

    public RankingController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<ItemRanking> ranking() {
        return usuarioService.ranking();
    }
}
