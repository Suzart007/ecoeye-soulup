package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.Requisicoes.MemoriaRequest;
import br.com.fiap.soulup.exception.ConflitoException;
import br.com.fiap.soulup.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulup.exception.RegraNegocioException;
import br.com.fiap.soulup.model.MemoriaUsuario;
import br.com.fiap.soulup.model.UsuarioAvatar;
import br.com.fiap.soulup.repository.MemoriaUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Memorias do avatar sobre o usuario (TB_MEMORIAS_USUARIO):
 *  - novas memorias so em vinculos ATIVOS;
 *  - a chave e unica dentro do vinculo (409), ex.: "horario_pico_uso";
 *  - a memoria nao muda de vinculo e a data de atualizacao nunca fica antes do registro.
 */
@Service
public class MemoriaService {

    private final MemoriaUsuarioRepository memorias;
    private final VinculoService vinculoService;

    public MemoriaService(MemoriaUsuarioRepository memorias, VinculoService vinculoService) {
        this.memorias = memorias;
        this.vinculoService = vinculoService;
    }

    public List<MemoriaUsuario> listar(Long usuarioAvatarId, String categoria, String status) {
        return memorias.listar(usuarioAvatarId, categoria, status);
    }

    public MemoriaUsuario buscar(long id) {
        return memorias.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Memória", id));
    }

    @Transactional
    public MemoriaUsuario cadastrar(MemoriaRequest req) {
        UsuarioAvatar vinculo = vinculoService.buscar(req.usuarioAvatarId());
        if (!vinculo.isAtivo()) {
            throw new RegraNegocioException("O vínculo " + vinculo.getId() + " está " + vinculo.getStatus()
                    + ": só vínculos ATIVOS registram novas memórias.");
        }
        String chave = req.chave().trim();
        validarChaveUnica(vinculo.getId(), chave, null);

        LocalDate hoje = LocalDate.now();
        MemoriaUsuario memoria = new MemoriaUsuario(null, vinculo.getId(), req.categoria(), chave, req.valor().trim(),
                req.relevancia(), hoje, hoje, req.status() == null ? MemoriaUsuario.ATIVA : req.status());
        return memorias.inserir(memoria);
    }

    @Transactional
    public MemoriaUsuario atualizar(long id, MemoriaRequest req) {
        MemoriaUsuario memoria = buscar(id);
        if (!memoria.getUsuarioAvatarId().equals(req.usuarioAvatarId())) {
            throw new RegraNegocioException("Uma memória não pode ser transferida para outro vínculo "
                    + "(vínculo atual: " + memoria.getUsuarioAvatarId() + ").");
        }
        String chave = req.chave().trim();
        validarChaveUnica(memoria.getUsuarioAvatarId(), chave, id);

        LocalDate hoje = LocalDate.now();
        memoria.setCategoria(req.categoria());
        memoria.setChave(chave);
        memoria.setValor(req.valor().trim());
        memoria.setRelevancia(req.relevancia());
        memoria.setDataUltimaAtualizacao(hoje.isBefore(memoria.getDataRegistro()) ? memoria.getDataRegistro() : hoje);
        if (req.status() != null) {
            memoria.setStatus(req.status());
        }
        memorias.atualizar(memoria);
        return memoria;
    }

    @Transactional
    public void excluir(long id) {
        buscar(id);
        memorias.excluir(id);
    }

    private void validarChaveUnica(long usuarioAvatarId, String chave, Long ignorarId) {
        if (memorias.existeChave(usuarioAvatarId, chave, ignorarId)) {
            throw new ConflitoException("O avatar já guarda uma memória com a chave \"" + chave
                    + "\" neste vínculo. Atualize a memória existente.");
        }
    }
}
