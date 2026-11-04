package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.Interacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** DAO da tabela TB_INTERACOES. */
@Repository
public class InteracaoRepository extends BaseRepository {

    private static final String SELECT =
            "SELECT interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao "
                    + "FROM TB_INTERACOES";

    private static final RowMapper<Interacao> MAPPER = (rs, linha) -> new Interacao(
            rs.getLong("interacao_id"),
            rs.getLong("usuario_avatar_id"),
            lerDataHora(rs, "data_interacao"),
            rs.getString("mensagem_usuario"),
            rs.getString("resposta_avatar"),
            rs.getString("tipo_interacao"));

    public InteracaoRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<Interacao> listar(Long usuarioAvatarId, String tipo) {
        Filtro filtro = new Filtro()
                .igual("usuario_avatar_id", usuarioAvatarId)
                .igual("tipo_interacao", tipo);
        return jdbc.query(SELECT + filtro.where() + " ORDER BY data_interacao, interacao_id", MAPPER, filtro.parametros());
    }

    public Optional<Interacao> buscarPorId(long id) {
        return Optional.ofNullable(primeiro(jdbc.query(SELECT + " WHERE interacao_id = ?", MAPPER, id)));
    }

    public Interacao inserir(Interacao interacao) {
        return inserirComNovoId("TB_INTERACOES", "interacao_id", id -> {
            jdbc.update("INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, "
                            + "resposta_avatar, tipo_interacao) VALUES (?, ?, ?, ?, ?, ?)",
                    id, interacao.getUsuarioAvatarId(), dataHora(interacao.getDataInteracao()),
                    interacao.getMensagemUsuario(), interacao.getRespostaAvatar(), interacao.getTipo());
            interacao.setId(id);
            return interacao;
        });
    }

    public void excluir(long id) {
        jdbc.update("DELETE FROM TB_INTERACOES WHERE interacao_id = ?", id);
    }
}
