package br.com.fiap.soulup.service;

import br.com.fiap.soulup.exception.ConflitoException;
import br.com.fiap.soulup.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulup.model.Missao;
import br.com.fiap.soulup.repository.MissaoRepository;
import br.com.fiap.soulup.repository.SugestaoAvatarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Regras de negocio das missoes:
 *  - pontuacao de 0 a 100 e situacao ATIVA/INATIVA/ENCERRADA (validadas no model);
 *  - missao que ja foi realizada ou sugerida nao e excluida, e sim encerrada (409);
 *  - quando a missao deixa de ser ATIVA, as sugestoes que aguardavam resposta expiram.
 */
@Service
public class MissaoService {

    private final MissaoRepository missoes;
    private final SugestaoAvatarRepository sugestoes;

    public MissaoService(MissaoRepository missoes, SugestaoAvatarRepository sugestoes) {
        this.missoes = missoes;
        this.sugestoes = sugestoes;
    }

    public List<Missao> listar(String situacao) {
        return missoes.listar(situacao);
    }

    public Missao buscar(long id) {
        return missoes.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Missão", id));
    }

    @Transactional
    public Missao cadastrar(Missao missao) {
        normalizar(missao);
        if (missao.getSituacao() == null) {
            missao.setSituacao(Missao.ATIVA);
        }
        return missoes.inserir(missao);
    }

    @Transactional
    public Missao atualizar(long id, Missao dados) {
        Missao atual = buscar(id);
        normalizar(dados);
        dados.setId(id);
        if (dados.getSituacao() == null) {
            dados.setSituacao(atual.getSituacao());
        }
        missoes.atualizar(dados);
        if (atual.isAtiva() && !dados.isAtiva()) {
            sugestoes.expirarEnviadasDaMissao(id);
        }
        return dados;
    }

    @Transactional
    public void excluir(long id) {
        Missao missao = buscar(id);
        String historico = Textos.resumirDependencias(missoes.contarDependencias(id));
        if (!historico.isEmpty()) {
            throw new ConflitoException("Não é possível excluir a missão \"" + missao.getTitulo()
                    + "\": ela já faz parte do histórico (" + historico + "). "
                    + "Para tirá-la do ar, altere a situação para INATIVA ou ENCERRADA.");
        }
        missoes.excluir(id);
    }

    private void normalizar(Missao missao) {
        missao.setTitulo(Textos.limpar(missao.getTitulo()));
        missao.setDescricao(Textos.limpar(missao.getDescricao()));
    }
}
