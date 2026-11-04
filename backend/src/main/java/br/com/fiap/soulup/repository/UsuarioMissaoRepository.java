package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.UsuarioMissao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** DAO da tabela associativa TB_USUARIOS_MISSOES (participacoes e progresso). */
@Repository
public class UsuarioMissaoRepository extends BaseRepository {

    private static final String SELECT =
            "SELECT usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual "
                    + "FROM TB_USUARIOS_MISSOES";

    private static final RowMapper<UsuarioMissao> MAPPER = (rs, linha) -> new UsuarioMissao(
            rs.getLong("usuario_missao_id"),
            rs.getLong("usuario_id"),
            rs.getLong("missao_id"),
            lerData(rs, "data_inicio"),
            lerData(rs, "data_conclusao"),
            rs.getString("status_missao"),
            rs.getInt("progresso_percentual"));

    public UsuarioMissaoRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<UsuarioMissao> listar(Long usuarioId, Long missaoId, String status) {
        Filtro filtro = new Filtro()
                .igual("usuario_id", usuarioId)
                .igual("missao_id", missaoId)
                .igual("status_missao", status);
        return jdbc.query(SELECT + filtro.where() + " ORDER BY usuario_missao_id", MAPPER, filtro.parametros());
    }

    public Optional<UsuarioMissao> buscarPorId(long id) {
        return Optional.ofNullable(primeiro(jdbc.query(SELECT + " WHERE usuario_missao_id = ?", MAPPER, id)));
    }

    public boolean existeParticipacao(long usuarioId, long missaoId, String... status) {
        String marcadores = String.join(", ", java.util.Collections.nCopies(status.length, "?"));
        Object[] parametros = new Object[status.length + 2];
        parametros[0] = usuarioId;
        parametros[1] = missaoId;
        System.arraycopy(status, 0, parametros, 2, status.length);
        return existe("SELECT COUNT(*) FROM TB_USUARIOS_MISSOES WHERE usuario_id = ? AND missao_id = ? "
                + "AND status_missao IN (" + marcadores + ")", parametros);
    }

    public int contarPorStatus(long usuarioId, String... status) {
        String marcadores = String.join(", ", java.util.Collections.nCopies(status.length, "?"));
        Object[] parametros = new Object[status.length + 1];
        parametros[0] = usuarioId;
        System.arraycopy(status, 0, parametros, 1, status.length);
        return contar("SELECT COUNT(*) FROM TB_USUARIOS_MISSOES WHERE usuario_id = ? AND status_missao IN (" + marcadores + ")",
                parametros);
    }

    public UsuarioMissao inserir(UsuarioMissao participacao) {
        return inserirComNovoId("TB_USUARIOS_MISSOES", "usuario_missao_id", id -> {
            jdbc.update("INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, "
                            + "status_missao, progresso_percentual) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    id, participacao.getUsuarioId(), participacao.getMissaoId(), data(participacao.getDataInicio()),
                    data(participacao.getDataConclusao()), participacao.getStatus(), participacao.getProgresso());
            participacao.setId(id);
            return participacao;
        });
    }

    public void atualizar(UsuarioMissao participacao) {
        jdbc.update("UPDATE TB_USUARIOS_MISSOES SET data_conclusao = ?, status_missao = ?, progresso_percentual = ? "
                        + "WHERE usuario_missao_id = ?",
                data(participacao.getDataConclusao()), participacao.getStatus(), participacao.getProgresso(),
                participacao.getId());
    }
}
