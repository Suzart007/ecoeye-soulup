package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.PontuacaoUsuario;
import br.com.fiap.soulup.dto.Respostas.ProgressoAtualizado;
import br.com.fiap.soulup.exception.ConflitoException;
import br.com.fiap.soulup.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulup.exception.RegraNegocioException;
import br.com.fiap.soulup.model.Missao;
import br.com.fiap.soulup.model.NivelEngajamento;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.model.UsuarioMissao;
import br.com.fiap.soulup.repository.UsuarioMissaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Regras de negocio da participacao em missoes (TB_USUARIOS_MISSOES):
 *  - so usuarios ATIVOS iniciam missoes ATIVAS;
 *  - o usuario nao pode ter duas participacoes abertas na mesma missao (409);
 *  - cada missao rende pontos uma unica vez: quem ja concluiu nao refaz (409);
 *  - progresso de 0 a 100, sem regredir; 100% conclui a missao (regras no model UsuarioMissao).
 */
@Service
public class ParticipacaoService {

    private final UsuarioMissaoRepository participacoes;
    private final UsuarioService usuarioService;
    private final MissaoService missaoService;

    public ParticipacaoService(UsuarioMissaoRepository participacoes, UsuarioService usuarioService,
                               MissaoService missaoService) {
        this.participacoes = participacoes;
        this.usuarioService = usuarioService;
        this.missaoService = missaoService;
    }

    public List<UsuarioMissao> listar(Long usuarioId, Long missaoId, String status) {
        return participacoes.listar(usuarioId, missaoId, status);
    }

    public UsuarioMissao buscar(long id) {
        return participacoes.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Participação em missão", id));
    }

    @Transactional
    public UsuarioMissao iniciar(long usuarioId, long missaoId) {
        Usuario usuario = exigirUsuarioAtivo(usuarioId);
        Missao missao = missaoService.buscar(missaoId);
        if (!missao.isAtiva()) {
            throw new RegraNegocioException("A missão \"" + missao.getTitulo() + "\" está " + missao.getSituacao()
                    + " e não pode ser iniciada.");
        }
        if (participacoes.existeParticipacao(usuarioId, missaoId, UsuarioMissao.PENDENTE, UsuarioMissao.EM_ANDAMENTO)) {
            throw new ConflitoException(usuario.getPrimeiroNome() + " já está participando da missão \"" + missao.getTitulo() + "\".");
        }
        if (participacoes.existeParticipacao(usuarioId, missaoId, UsuarioMissao.CONCLUIDA)) {
            throw new ConflitoException(usuario.getPrimeiroNome() + " já concluiu a missão \"" + missao.getTitulo()
                    + "\": cada missão rende pontos uma única vez.");
        }
        return participacoes.inserir(UsuarioMissao.iniciar(usuarioId, missaoId, LocalDate.now()));
    }

    @Transactional
    public ProgressoAtualizado atualizarProgresso(long id, int progresso) {
        UsuarioMissao participacao = buscar(id);
        exigirUsuarioAtivo(participacao.getUsuarioId());

        participacao.atualizarProgresso(progresso, LocalDate.now());
        participacoes.atualizar(participacao);

        int pontosGanhos = UsuarioMissao.CONCLUIDA.equals(participacao.getStatus())
                ? missaoService.buscar(participacao.getMissaoId()).getPontuacao()
                : 0;
        PontuacaoUsuario pontuacao = usuarioService.pontuacao(participacao.getUsuarioId());
        return new ProgressoAtualizado(participacao, pontosGanhos, pontuacao.pontos(),
                NivelEngajamento.dePontos(pontuacao.pontos()));
    }

    @Transactional
    public UsuarioMissao cancelar(long id) {
        UsuarioMissao participacao = buscar(id);
        participacao.cancelar();
        participacoes.atualizar(participacao);
        return participacao;
    }

    private Usuario exigirUsuarioAtivo(long usuarioId) {
        Usuario usuario = usuarioService.buscar(usuarioId);
        if (!usuario.isAtivo()) {
            throw new RegraNegocioException("O usuário " + usuarioId + " está " + usuario.getSituacao()
                    + " e não pode participar de missões.");
        }
        return usuario;
    }
}
