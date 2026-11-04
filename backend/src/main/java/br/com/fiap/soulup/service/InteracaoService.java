package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.Requisicoes.InteracaoRequest;
import br.com.fiap.soulup.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulup.exception.RegraNegocioException;
import br.com.fiap.soulup.model.Avatar;
import br.com.fiap.soulup.model.Interacao;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.model.UsuarioAvatar;
import br.com.fiap.soulup.repository.InteracaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Regras de negocio das interacoes (conversas com o avatar):
 *  - so acontecem por um vinculo ATIVO, com usuario ATIVO;
 *  - se a resposta nao for enviada, o proprio avatar responde de acordo com o tipo da interacao.
 */
@Service
public class InteracaoService {

    private final InteracaoRepository interacoes;
    private final VinculoService vinculoService;
    private final UsuarioService usuarioService;
    private final AvatarService avatarService;

    public InteracaoService(InteracaoRepository interacoes, VinculoService vinculoService,
                            UsuarioService usuarioService, AvatarService avatarService) {
        this.interacoes = interacoes;
        this.vinculoService = vinculoService;
        this.usuarioService = usuarioService;
        this.avatarService = avatarService;
    }

    public List<Interacao> listar(Long usuarioAvatarId, String tipo) {
        return interacoes.listar(usuarioAvatarId, tipo);
    }

    public Interacao buscar(long id) {
        return interacoes.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Interação", id));
    }

    @Transactional
    public Interacao registrar(InteracaoRequest req) {
        UsuarioAvatar vinculo = vinculoService.buscar(req.usuarioAvatarId());
        if (!vinculo.isAtivo()) {
            throw new RegraNegocioException("O vínculo " + vinculo.getId() + " está " + vinculo.getStatus()
                    + ": só é possível conversar com o avatar por um vínculo ATIVO.");
        }
        Usuario usuario = usuarioService.buscar(vinculo.getUsuarioId());
        if (!usuario.isAtivo()) {
            throw new RegraNegocioException("O usuário " + usuario.getId() + " está " + usuario.getSituacao()
                    + " e não pode interagir com o avatar.");
        }
        Avatar avatar = avatarService.buscar(vinculo.getAvatarId());

        String tipo = req.tipo() == null ? Interacao.CONVERSA : req.tipo();
        String resposta = Textos.vazio(req.respostaAvatar())
                ? responderComoAvatar(avatar, usuario, tipo)
                : req.respostaAvatar().trim();

        Interacao interacao = new Interacao(null, vinculo.getId(), LocalDateTime.now().withNano(0),
                req.mensagemUsuario().trim(), Textos.limitar(resposta, 500), tipo);
        return interacoes.inserir(interacao);
    }

    @Transactional
    public void excluir(long id) {
        buscar(id);
        interacoes.excluir(id);
    }

    /** Resposta padrao do avatar, personalizada com o nome do usuario e o tipo da conversa. */
    private String responderComoAvatar(Avatar avatar, Usuario usuario, String tipo) {
        String nome = usuario.getPrimeiroNome();
        return switch (tipo) {
            case Interacao.SUGESTAO -> "Boa ideia, " + nome + "! Vou olhar as missões ativas e já te mando uma sugestão "
                    + "que combina com você. — " + avatar.getNome();
            case Interacao.MISSAO -> "Que demais, " + nome + "! Atualize o progresso da sua missão para eu acompanhar "
                    + "sua evolução e somar os seus pontos. — " + avatar.getNome();
            case Interacao.SUPORTE -> "Entendi, " + nome + ". Vou te ajudar com isso. Se precisar de algo a mais, "
                    + "nossa equipe também atende pela página de contato. — " + avatar.getNome();
            default -> "Oi, " + nome + "! Aqui é " + avatar.getNome() + ". Adorei a conversa: conte comigo para deixar "
                    + "a sua rotina mais sustentável!";
        };
    }
}
