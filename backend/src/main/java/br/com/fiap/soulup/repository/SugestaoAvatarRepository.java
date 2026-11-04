package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.SugestaoAvatar;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** DAO da tabela TB_SUGESTOES_AVATAR. */
@Repository
public class SugestaoAvatarRepository extends BaseRepository {

    private static final String SELECT =
            "SELECT sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao "
                    + "FROM TB_SUGESTOES_AVATAR";

    private static final RowMapper<SugestaoAvatar> MAPPER = (rs, linha) -> new SugestaoAvatar(
            rs.getLong("sugestao_id"),
            rs.getLong("usuario_id"),
            rs.getLong("avatar_id"),
            lerLong(rs, "missao_id"),
            lerDataHora(rs, "data_sugestao"),
            rs.getString("texto_sugestao"),
            rs.getString("status_sugestao"));

    public SugestaoAvatarRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<SugestaoAvatar> listar(Long usuarioId, Long avatarId, String status) {
        Filtro filtro = new Filtro()
                .igual("usuario_id", usuarioId)
                .igual("avatar_id", avatarId)
                .igual("status_sugestao", status);
        return jdbc.query(SELECT + filtro.where() + " ORDER BY data_sugestao DESC, sugestao_id DESC", MAPPER,
                filtro.parametros());
    }

    public Optional<SugestaoAvatar> buscarPorId(long id) {
        return Optional.ofNullable(primeiro(jdbc.query(SELECT + " WHERE sugestao_id = ?", MAPPER, id)));
    }

    public int contarEnviadas(long usuarioId) {
        return contar("SELECT COUNT(*) FROM TB_SUGESTOES_AVATAR WHERE usuario_id = ? AND status_sugestao = 'ENVIADA'", usuarioId);
    }

    public SugestaoAvatar inserir(SugestaoAvatar sugestao) {
        return inserirComNovoId("TB_SUGESTOES_AVATAR", "sugestao_id", id -> {
            jdbc.update("INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, "
                            + "texto_sugestao, status_sugestao) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    id, sugestao.getUsuarioId(), sugestao.getAvatarId(), numero(sugestao.getMissaoId()),
                    dataHora(sugestao.getDataSugestao()), sugestao.getTexto(), sugestao.getStatus());
            sugestao.setId(id);
            return sugestao;
        });
    }

    public void atualizarStatus(long id, String status) {
        jdbc.update("UPDATE TB_SUGESTOES_AVATAR SET status_sugestao = ? WHERE sugestao_id = ?", status, id);
    }

    /** Usuario deixou de ser ATIVO: as sugestoes que aguardavam resposta expiram. */
    public int expirarEnviadasDoUsuario(long usuarioId) {
        return jdbc.update("UPDATE TB_SUGESTOES_AVATAR SET status_sugestao = 'EXPIRADA' "
                + "WHERE usuario_id = ? AND status_sugestao = 'ENVIADA'", usuarioId);
    }

    /** Missao deixou de ser ATIVA: as sugestoes dela que aguardavam resposta expiram. */
    public int expirarEnviadasDaMissao(long missaoId) {
        return jdbc.update("UPDATE TB_SUGESTOES_AVATAR SET status_sugestao = 'EXPIRADA' "
                + "WHERE missao_id = ? AND status_sugestao = 'ENVIADA'", missaoId);
    }
}
