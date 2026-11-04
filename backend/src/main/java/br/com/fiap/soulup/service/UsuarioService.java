package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.PontuacaoUsuario;
import br.com.fiap.soulup.dto.Respostas.ItemRanking;
import br.com.fiap.soulup.dto.Respostas.PerfilUsuario;
import br.com.fiap.soulup.exception.ConflitoException;
import br.com.fiap.soulup.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulup.exception.RegraNegocioException;
import br.com.fiap.soulup.model.NivelEngajamento;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.model.UsuarioMissao;
import br.com.fiap.soulup.repository.SugestaoAvatarRepository;
import br.com.fiap.soulup.repository.UsuarioMissaoRepository;
import br.com.fiap.soulup.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Regras de negocio dos usuarios:
 *  - e-mail unico na plataforma (409);
 *  - data de cadastro nao pode estar no futuro;
 *  - usuario com historico nao e excluido, e sim inativado (409);
 *  - ao deixar de ser ATIVO, as sugestoes que aguardavam resposta expiram (regra 10);
 *  - pontos = soma das missoes CONCLUIDAS, que define o nivel de engajamento.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarios;
    private final UsuarioMissaoRepository participacoes;
    private final SugestaoAvatarRepository sugestoes;

    public UsuarioService(UsuarioRepository usuarios, UsuarioMissaoRepository participacoes,
                          SugestaoAvatarRepository sugestoes) {
        this.usuarios = usuarios;
        this.participacoes = participacoes;
        this.sugestoes = sugestoes;
    }

    public List<Usuario> listar(String situacao) {
        return usuarios.listar(situacao);
    }

    public Usuario buscar(long id) {
        return usuarios.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", id));
    }

    @Transactional
    public Usuario cadastrar(Usuario usuario) {
        normalizar(usuario);
        if (usuario.getSituacao() == null) {
            usuario.setSituacao(Usuario.ATIVO);
        }
        if (usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(LocalDate.now());
        }
        validarDataCadastro(usuario.getDataCadastro());
        validarEmailUnico(usuario.getEmail(), null);
        return usuarios.inserir(usuario);
    }

    @Transactional
    public Usuario atualizar(long id, Usuario dados) {
        Usuario atual = buscar(id);
        normalizar(dados);
        dados.setId(id);
        if (dados.getSituacao() == null) {
            dados.setSituacao(atual.getSituacao());
        }
        if (dados.getDataCadastro() == null) {
            dados.setDataCadastro(atual.getDataCadastro());
        }
        validarDataCadastro(dados.getDataCadastro());
        validarEmailUnico(dados.getEmail(), id);

        usuarios.atualizar(dados);
        if (atual.isAtivo() && !dados.isAtivo()) {
            sugestoes.expirarEnviadasDoUsuario(id);
        }
        return dados;
    }

    @Transactional
    public void excluir(long id) {
        Usuario usuario = buscar(id);
        String historico = Textos.resumirDependencias(usuarios.contarDependencias(id));
        if (!historico.isEmpty()) {
            throw new ConflitoException("Não é possível excluir o usuário \"" + usuario.getNome()
                    + "\": ele possui histórico na plataforma (" + historico + "). "
                    + "Para desativar a conta, altere a situação para INATIVO.");
        }
        usuarios.excluir(id);
    }

    public PerfilUsuario perfil(long id) {
        Usuario usuario = buscar(id);
        PontuacaoUsuario pontuacao = usuarios.pontuacao(id);
        NivelEngajamento nivel = NivelEngajamento.dePontos(pontuacao.pontos());
        int emAndamento = participacoes.contarPorStatus(id, UsuarioMissao.PENDENTE, UsuarioMissao.EM_ANDAMENTO);
        return new PerfilUsuario(usuario, pontuacao.pontos(), nivel, nivel.pontosParaProximoNivel(pontuacao.pontos()),
                pontuacao.missoesConcluidas(), emAndamento, sugestoes.contarEnviadas(id));
    }

    public PontuacaoUsuario pontuacao(long id) {
        return usuarios.pontuacao(id);
    }

    /** Ranking com empates na mesma posicao (1, 2, 2, 4...), como o RANK() da consulta C1. */
    public List<ItemRanking> ranking() {
        List<ItemRanking> ranking = new ArrayList<>();
        List<PontuacaoUsuario> linhas = usuarios.ranking();
        for (int i = 0; i < linhas.size(); i++) {
            PontuacaoUsuario linha = linhas.get(i);
            int posicao = (i > 0 && linha.pontos() == linhas.get(i - 1).pontos()) ? ranking.get(i - 1).posicao() : i + 1;
            ranking.add(new ItemRanking(posicao, linha.usuarioId(), linha.nome(), linha.missoesConcluidas(),
                    linha.pontos(), NivelEngajamento.dePontos(linha.pontos())));
        }
        return ranking;
    }

    private void normalizar(Usuario usuario) {
        usuario.setNome(Textos.limpar(usuario.getNome()));
        usuario.setEmail(Textos.emailNormalizado(usuario.getEmail()));
    }

    private void validarDataCadastro(LocalDate dataCadastro) {
        if (dataCadastro.isAfter(LocalDate.now())) {
            throw new RegraNegocioException("A data de cadastro não pode estar no futuro.");
        }
    }

    private void validarEmailUnico(String email, Long ignorarId) {
        if (usuarios.existeEmail(email, ignorarId)) {
            throw new ConflitoException("Já existe um usuário cadastrado com o e-mail " + email + ".");
        }
    }
}
