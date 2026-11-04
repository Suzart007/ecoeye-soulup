package br.com.fiap.soulup.service;

import br.com.fiap.soulup.exception.ConflitoException;
import br.com.fiap.soulup.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulup.model.Avatar;
import br.com.fiap.soulup.repository.AvatarRepository;
import br.com.fiap.soulup.repository.UsuarioAvatarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Regras de negocio dos avatares:
 *  - avatar com vinculos ou sugestoes nao e excluido, e sim inativado (409);
 *  - ao inativar um avatar, os vinculos ATIVOS com ele passam para INATIVO.
 */
@Service
public class AvatarService {

    private final AvatarRepository avatares;
    private final UsuarioAvatarRepository vinculos;

    public AvatarService(AvatarRepository avatares, UsuarioAvatarRepository vinculos) {
        this.avatares = avatares;
        this.vinculos = vinculos;
    }

    public List<Avatar> listar(String situacao) {
        return avatares.listar(situacao);
    }

    public Avatar buscar(long id) {
        return avatares.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Avatar", id));
    }

    @Transactional
    public Avatar cadastrar(Avatar avatar) {
        normalizar(avatar);
        if (avatar.getSituacao() == null) {
            avatar.setSituacao(Avatar.ATIVO);
        }
        return avatares.inserir(avatar);
    }

    @Transactional
    public Avatar atualizar(long id, Avatar dados) {
        Avatar atual = buscar(id);
        normalizar(dados);
        dados.setId(id);
        if (dados.getSituacao() == null) {
            dados.setSituacao(atual.getSituacao());
        }
        avatares.atualizar(dados);
        if (atual.isAtivo() && !dados.isAtivo()) {
            vinculos.inativarVinculosDoAvatar(id);
        }
        return dados;
    }

    @Transactional
    public void excluir(long id) {
        Avatar avatar = buscar(id);
        String historico = Textos.resumirDependencias(avatares.contarDependencias(id));
        if (!historico.isEmpty()) {
            throw new ConflitoException("Não é possível excluir o avatar \"" + avatar.getNome()
                    + "\": ele possui histórico (" + historico + "). Para desativá-lo, altere a situação para INATIVO.");
        }
        avatares.excluir(id);
    }

    private void normalizar(Avatar avatar) {
        avatar.setNome(Textos.limpar(avatar.getNome()));
        avatar.setDescricao(Textos.limpar(avatar.getDescricao()));
        avatar.setPersonalidade(Textos.limpar(avatar.getPersonalidade()));
    }
}
