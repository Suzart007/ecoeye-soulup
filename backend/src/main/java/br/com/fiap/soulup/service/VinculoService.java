package br.com.fiap.soulup.service;

import br.com.fiap.soulup.exception.ConflitoException;
import br.com.fiap.soulup.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulup.exception.RegraNegocioException;
import br.com.fiap.soulup.model.Avatar;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.model.UsuarioAvatar;
import br.com.fiap.soulup.repository.UsuarioAvatarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Regras de negocio dos vinculos usuario x avatar (TB_USUARIOS_AVATARES):
 *  - so usuarios ATIVOS se vinculam a avatares ATIVOS;
 *  - nao pode haver dois vinculos ATIVOS entre o mesmo usuario e o mesmo avatar (409);
 *  - um vinculo ENCERRADO e definitivo (regra no model UsuarioAvatar).
 */
@Service
public class VinculoService {

    private final UsuarioAvatarRepository vinculos;
    private final UsuarioService usuarioService;
    private final AvatarService avatarService;

    public VinculoService(UsuarioAvatarRepository vinculos, UsuarioService usuarioService, AvatarService avatarService) {
        this.vinculos = vinculos;
        this.usuarioService = usuarioService;
        this.avatarService = avatarService;
    }

    public List<UsuarioAvatar> listar(Long usuarioId, Long avatarId, String status) {
        return vinculos.listar(usuarioId, avatarId, status);
    }

    public UsuarioAvatar buscar(long id) {
        return vinculos.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Vínculo usuário-avatar", id));
    }

    @Transactional
    public UsuarioAvatar vincular(long usuarioId, long avatarId) {
        validarParticipantes(usuarioId, avatarId, null);
        return vinculos.inserir(new UsuarioAvatar(null, usuarioId, avatarId, LocalDate.now(), UsuarioAvatar.ATIVO));
    }

    @Transactional
    public UsuarioAvatar alterarStatus(long id, String novoStatus) {
        UsuarioAvatar vinculo = buscar(id);
        boolean estavaAtivo = vinculo.isAtivo();
        vinculo.alterarStatus(novoStatus);
        if (vinculo.isAtivo() && !estavaAtivo) {
            validarParticipantes(vinculo.getUsuarioId(), vinculo.getAvatarId(), id);
        }
        vinculos.atualizarStatus(id, novoStatus);
        return vinculo;
    }

    private void validarParticipantes(long usuarioId, long avatarId, Long ignorarVinculoId) {
        Usuario usuario = usuarioService.buscar(usuarioId);
        if (!usuario.isAtivo()) {
            throw new RegraNegocioException("Só usuários ATIVOS podem ter vínculo ativo com um avatar (o usuário "
                    + usuarioId + " está " + usuario.getSituacao() + ").");
        }
        Avatar avatar = avatarService.buscar(avatarId);
        if (!avatar.isAtivo()) {
            throw new RegraNegocioException("O avatar \"" + avatar.getNome() + "\" está INATIVO e não aceita novos vínculos.");
        }
        if (vinculos.existeVinculoAtivo(usuarioId, avatarId, ignorarVinculoId)) {
            throw new ConflitoException("O usuário " + usuarioId + " já possui um vínculo ATIVO com o avatar \""
                    + avatar.getNome() + "\".");
        }
    }
}
