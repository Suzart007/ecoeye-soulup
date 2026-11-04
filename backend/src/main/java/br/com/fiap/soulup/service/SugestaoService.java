package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.Respostas.RespostaSugestao;
import br.com.fiap.soulup.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulup.exception.RegraNegocioException;
import br.com.fiap.soulup.model.Avatar;
import br.com.fiap.soulup.model.Missao;
import br.com.fiap.soulup.model.NivelEngajamento;
import br.com.fiap.soulup.model.SugestaoAvatar;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.model.UsuarioAvatar;
import br.com.fiap.soulup.model.UsuarioMissao;
import br.com.fiap.soulup.repository.MissaoRepository;
import br.com.fiap.soulup.repository.SugestaoAvatarRepository;
import br.com.fiap.soulup.repository.UsuarioAvatarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Sugestoes inteligentes do avatar (TB_SUGESTOES_AVATAR):
 *  - regra 10: usuarios INATIVOS ou BLOQUEADOS nao recebem novas sugestoes
 *    (validada aqui e tambem pela trigger TRG_SUG_USUARIO_ATIVO no Oracle);
 *  - o avatar so sugere missoes ATIVAS que o usuario nunca fez, que nao tem
 *    sugestao aguardando resposta (consulta C8 do banco) e que ele nao recusou;
 *  - iniciantes recebem a missao mais leve (menos pontos) para nao desanimar;
 *    os demais niveis recebem a missao de maior impacto (mais pontos);
 *  - aceitar uma sugestao ja inicia a missao sugerida para o usuario.
 */
@Service
public class SugestaoService {

    private final SugestaoAvatarRepository sugestoes;
    private final UsuarioAvatarRepository vinculos;
    private final MissaoRepository missoes;
    private final UsuarioService usuarioService;
    private final AvatarService avatarService;
    private final ParticipacaoService participacaoService;

    public SugestaoService(SugestaoAvatarRepository sugestoes, UsuarioAvatarRepository vinculos, MissaoRepository missoes,
                           UsuarioService usuarioService, AvatarService avatarService,
                           ParticipacaoService participacaoService) {
        this.sugestoes = sugestoes;
        this.vinculos = vinculos;
        this.missoes = missoes;
        this.usuarioService = usuarioService;
        this.avatarService = avatarService;
        this.participacaoService = participacaoService;
    }

    public List<SugestaoAvatar> listar(Long usuarioId, Long avatarId, String status) {
        return sugestoes.listar(usuarioId, avatarId, status);
    }

    public SugestaoAvatar buscar(long id) {
        return sugestoes.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Sugestão", id));
    }

    @Transactional
    public SugestaoAvatar gerar(long usuarioId, Long avatarId) {
        Usuario usuario = usuarioService.buscar(usuarioId);
        if (!usuario.isAtivo()) {
            throw new RegraNegocioException("RN10: o usuário " + usuarioId + " está " + usuario.getSituacao()
                    + " e não pode receber novas sugestões do avatar.");
        }
        UsuarioAvatar vinculo = vinculos.buscarVinculoAtivo(usuarioId, avatarId).orElseThrow(() ->
                new RegraNegocioException("O usuário " + usuarioId + " precisa de um vínculo ATIVO com "
                        + (avatarId == null ? "um avatar ativo" : "o avatar " + avatarId) + " para receber sugestões."));
        Avatar avatar = avatarService.buscar(vinculo.getAvatarId());

        List<Missao> disponiveis = missoes.listarDisponiveisParaSugestao(usuarioId);
        if (disponiveis.isEmpty()) {
            throw new RegraNegocioException("Não há novas missões para sugerir: o usuário já participou, recusou "
                    + "ou tem sugestão aguardando resposta de todas as missões ativas.");
        }

        NivelEngajamento nivel = NivelEngajamento.dePontos(usuarioService.pontuacao(usuarioId).pontos());
        Comparator<Missao> porPontos = Comparator.comparing(Missao::getPontuacao).thenComparing(Missao::getId);
        Missao escolhida = nivel == NivelEngajamento.INICIANTE
                ? disponiveis.stream().min(porPontos).orElseThrow()
                : disponiveis.stream().max(Comparator.comparing(Missao::getPontuacao)).orElseThrow();

        SugestaoAvatar sugestao = new SugestaoAvatar(null, usuarioId, avatar.getId(), escolhida.getId(),
                LocalDateTime.now().withNano(0), Textos.limitar(montarTexto(usuario, escolhida, nivel), 255),
                SugestaoAvatar.ENVIADA);
        return sugestoes.inserir(sugestao);
    }

    @Transactional
    public RespostaSugestao aceitar(long id) {
        SugestaoAvatar sugestao = buscar(id);
        sugestao.responder(true);
        UsuarioMissao participacao = sugestao.getMissaoId() == null
                ? null
                : participacaoService.iniciar(sugestao.getUsuarioId(), sugestao.getMissaoId());
        sugestoes.atualizarStatus(id, sugestao.getStatus());
        return new RespostaSugestao(sugestao, participacao);
    }

    @Transactional
    public SugestaoAvatar recusar(long id) {
        SugestaoAvatar sugestao = buscar(id);
        sugestao.responder(false);
        sugestoes.atualizarStatus(id, sugestao.getStatus());
        return sugestao;
    }

    private String montarTexto(Usuario usuario, Missao missao, NivelEngajamento nivel) {
        String nome = usuario.getPrimeiroNome();
        if (nivel == NivelEngajamento.INICIANTE) {
            return "Olá, " + nome + "! Para começar no seu ritmo, que tal a missão \"" + missao.getTitulo()
                    + "\"? Ela vale " + missao.getPontuacao() + " pontos.";
        }
        return nome + ", você já é " + nivel.getDescricao() + "! Topa um desafio maior? A missão \""
                + missao.getTitulo() + "\" vale " + missao.getPontuacao() + " pontos.";
    }
}
