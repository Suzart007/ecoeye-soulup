package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.Missao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** DAO da tabela TB_MISSOES. */
@Repository
public class MissaoRepository extends BaseRepository {

    private static final String SELECT =
            "SELECT missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao FROM TB_MISSOES";

    private static final RowMapper<Missao> MAPPER = (rs, linha) -> new Missao(
            rs.getLong("missao_id"),
            rs.getString("titulo_missao"),
            rs.getString("descricao_missao"),
            rs.getInt("pontuacao_missao"),
            rs.getString("situacao_missao"));

    public MissaoRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<Missao> listar(String situacao) {
        Filtro filtro = new Filtro().igual("situacao_missao", situacao);
        return jdbc.query(SELECT + filtro.where() + " ORDER BY missao_id", MAPPER, filtro.parametros());
    }

    public Optional<Missao> buscarPorId(long id) {
        return Optional.ofNullable(primeiro(jdbc.query(SELECT + " WHERE missao_id = ?", MAPPER, id)));
    }

    public Missao inserir(Missao missao) {
        return inserirComNovoId("TB_MISSOES", "missao_id", id -> {
            jdbc.update("INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao) "
                            + "VALUES (?, ?, ?, ?, ?)",
                    id, missao.getTitulo(), missao.getDescricao(), missao.getPontuacao(), missao.getSituacao());
            missao.setId(id);
            return missao;
        });
    }

    public void atualizar(Missao missao) {
        jdbc.update("UPDATE TB_MISSOES SET titulo_missao = ?, descricao_missao = ?, pontuacao_missao = ?, situacao_missao = ? "
                        + "WHERE missao_id = ?",
                missao.getTitulo(), missao.getDescricao(), missao.getPontuacao(), missao.getSituacao(), missao.getId());
    }

    public void excluir(long id) {
        jdbc.update("DELETE FROM TB_MISSOES WHERE missao_id = ?", id);
    }

    /** Registros filhos que impedem a exclusao (FKs fk_um_missao e fk_sug_missao). */
    public Map<String, Integer> contarDependencias(long id) {
        Map<String, Integer> dependencias = new LinkedHashMap<>();
        dependencias.put("participação(ões) de usuários", contar("SELECT COUNT(*) FROM TB_USUARIOS_MISSOES WHERE missao_id = ?", id));
        dependencias.put("sugestão(ões) do avatar", contar("SELECT COUNT(*) FROM TB_SUGESTOES_AVATAR WHERE missao_id = ?", id));
        return dependencias;
    }

    /**
     * Consulta C8 do banco: missoes ATIVAS que o usuario nunca realizou e que
     * nao tem sugestao aguardando resposta. Base da proxima sugestao do avatar.
     * A API tambem nao repete missoes que o usuario ja recusou.
     */
    public List<Missao> listarDisponiveisParaSugestao(long usuarioId) {
        return jdbc.query(SELECT + " m WHERE m.situacao_missao = 'ATIVA' "
                        + "AND NOT EXISTS (SELECT 1 FROM TB_USUARIOS_MISSOES um "
                        + "                 WHERE um.usuario_id = ? AND um.missao_id = m.missao_id) "
                        + "AND NOT EXISTS (SELECT 1 FROM TB_SUGESTOES_AVATAR s "
                        + "                 WHERE s.usuario_id = ? AND s.missao_id = m.missao_id "
                        + "                   AND s.status_sugestao IN ('ENVIADA', 'RECUSADA')) "
                        + "ORDER BY m.pontuacao_missao DESC, m.missao_id",
                MAPPER, usuarioId, usuarioId);
    }
}
