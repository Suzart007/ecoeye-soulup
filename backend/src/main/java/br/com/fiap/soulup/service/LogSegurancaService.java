package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.Requisicoes.LogRequest;
import br.com.fiap.soulup.dto.Respostas.RegistroLog;
import br.com.fiap.soulup.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulup.exception.RegraNegocioException;
import br.com.fiap.soulup.model.LogSeguranca;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.repository.LogSegurancaRepository;
import br.com.fiap.soulup.repository.SugestaoAvatarRepository;
import br.com.fiap.soulup.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Regras de negocio dos logs de seguranca (TB_LOGS_SEGURANCA), regra 9 do banco:
 *  - 3 LOGIN_FALHA seguidas (sem LOGIN_SUCESSO entre elas) bloqueiam a conta:
 *    a API muda o usuario para BLOQUEADO e grava o log BLOQUEIO_CONTA sozinha;
 *  - conta BLOQUEADA nao registra LOGIN_SUCESSO;
 *  - se o IP nao for enviado, a API usa o IP de quem fez a requisicao.
 */
@Service
public class LogSegurancaService {

    static final int FALHAS_PARA_BLOQUEIO = 3;

    private final LogSegurancaRepository logs;
    private final UsuarioRepository usuarios;
    private final SugestaoAvatarRepository sugestoes;
    private final UsuarioService usuarioService;

    public LogSegurancaService(LogSegurancaRepository logs, UsuarioRepository usuarios,
                               SugestaoAvatarRepository sugestoes, UsuarioService usuarioService) {
        this.logs = logs;
        this.usuarios = usuarios;
        this.sugestoes = sugestoes;
        this.usuarioService = usuarioService;
    }

    public List<LogSeguranca> listar(Long usuarioId, String tipoEvento) {
        return logs.listar(usuarioId, tipoEvento);
    }

    public LogSeguranca buscar(long id) {
        return logs.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Log de segurança", id));
    }

    @Transactional
    public RegistroLog registrar(LogRequest req, String ipRequisicao) {
        Usuario usuario = usuarioService.buscar(req.usuarioId());
        String tipo = req.tipoEvento();
        if (LogSeguranca.BLOQUEIO_CONTA.equals(tipo)) {
            throw new RegraNegocioException("O evento BLOQUEIO_CONTA é registrado automaticamente pela API "
                    + "após " + FALHAS_PARA_BLOQUEIO + " falhas de login seguidas.");
        }
        if (LogSeguranca.LOGIN_SUCESSO.equals(tipo) && Usuario.BLOQUEADO.equals(usuario.getSituacao())) {
            throw new RegraNegocioException("A conta do usuário " + usuario.getId() + " está BLOQUEADA: "
                    + "não é possível registrar login com sucesso antes do desbloqueio.");
        }

        String ip = Textos.limitar(Textos.vazio(req.enderecoIp()) ? ipRequisicao : req.enderecoIp().trim(), 45);
        LocalDateTime agora = LocalDateTime.now().withNano(0);
        LogSeguranca log = logs.inserir(new LogSeguranca(null, usuario.getId(), agora, tipo, req.descricao().trim(), ip));

        boolean bloqueado = false;
        if (LogSeguranca.LOGIN_FALHA.equals(tipo) && usuario.isAtivo() && atingiuLimiteDeFalhas(usuario.getId())) {
            usuarios.atualizarSituacao(usuario.getId(), Usuario.BLOQUEADO);
            sugestoes.expirarEnviadasDoUsuario(usuario.getId());
            logs.inserir(new LogSeguranca(null, usuario.getId(), agora, LogSeguranca.BLOQUEIO_CONTA,
                    "Conta bloqueada automaticamente após " + FALHAS_PARA_BLOQUEIO + " tentativas de login malsucedidas.", ip));
            bloqueado = true;
        }
        return new RegistroLog(log, bloqueado);
    }

    /** As ultimas N tentativas de acesso foram todas LOGIN_FALHA? */
    private boolean atingiuLimiteDeFalhas(long usuarioId) {
        List<String> ultimos = logs.ultimosEventosDeAcesso(usuarioId, FALHAS_PARA_BLOQUEIO);
        return ultimos.size() == FALHAS_PARA_BLOQUEIO
                && ultimos.stream().allMatch(LogSeguranca.LOGIN_FALHA::equals);
    }
}
