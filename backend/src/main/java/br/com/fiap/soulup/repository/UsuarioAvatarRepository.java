package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.UsuarioAvatar;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** DAO da tabela associativa TB_USUARIOS_AVATARES (vinculos usuario x avatar). */
@Repository
public class UsuarioAvatarRepository extends BaseRepository {

    private static final String SELECT =
            "SELECT ua.usuario_avatar_id, ua.usuario_id, ua.avatar_id, ua.data_vinculo, ua.status_vinculo "
                    + "FROM TB_USUARIOS_AVATARES ua";

    private static final RowMapper<UsuarioAvatar> MAPPER = (rs, linha) -> new UsuarioAvatar(
            rs.getLong("usuario_avatar_id"),
            rs.getLong("usuario_id"),
            rs.getLong("avatar_id"),
            lerData(rs, "data_vinculo"),
            rs.getString("status_vinculo"));

    public UsuarioAvatarRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<UsuarioAvatar> listar(Long usuarioId, Long avatarId, String status) {
        Filtro filtro = new Filtro()
                .igual("ua.usuario_id", usuarioId)
                .igual("ua.avatar_id", avatarId)
                .igual("ua.status_vinculo", status);
        return jdbc.query(SELECT + filtro.where() + " ORDER BY ua.usuario_avatar_id", MAPPER, filtro.parametros());
    }

    public Optional<UsuarioAvatar> buscarPorId(long id) {
        return Optional.ofNullable(primeiro(jdbc.query(SELECT + " WHERE ua.usuario_avatar_id = ?", MAPPER, id)));
    }

    /** Impede dois vinculos ATIVOS entre o mesmo usuario e o mesmo avatar. */
    public boolean existeVinculoAtivo(long usuarioId, long avatarId, Long ignorarId) {
        return existe("SELECT COUNT(*) FROM TB_USUARIOS_AVATARES "
                        + "WHERE usuario_id = ? AND avatar_id = ? AND status_vinculo = 'ATIVO' AND usuario_avatar_id <> ?",
                usuarioId, avatarId, ignorarId == null ? -1L : ignorarId);
    }

    /**
     * Vinculo ATIVO mais recente do usuario com um avatar ATIVO
     * (se avatarId for informado, procura so com aquele avatar).
     */
    public Optional<UsuarioAvatar> buscarVinculoAtivo(long usuarioId, Long avatarId) {
        String sql = SELECT + " JOIN TB_AVATARES a ON a.avatar_id = ua.avatar_id "
                + "WHERE ua.usuario_id = ? AND ua.status_vinculo = 'ATIVO' AND a.situacao_avatar = 'ATIVO'"
                + (avatarId == null ? "" : " AND ua.avatar_id = ?")
                + " ORDER BY ua.data_vinculo DESC, ua.usuario_avatar_id DESC";
        Object[] parametros = avatarId == null ? new Object[]{usuarioId} : new Object[]{usuarioId, avatarId};
        return Optional.ofNullable(primeiro(jdbc.query(sql, MAPPER, parametros)));
    }

    public UsuarioAvatar inserir(UsuarioAvatar vinculo) {
        return inserirComNovoId("TB_USUARIOS_AVATARES", "usuario_avatar_id", id -> {
            jdbc.update("INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo) "
                            + "VALUES (?, ?, ?, ?, ?)",
                    id, vinculo.getUsuarioId(), vinculo.getAvatarId(), data(vinculo.getDataVinculo()), vinculo.getStatus());
            vinculo.setId(id);
            return vinculo;
        });
    }

    public void atualizarStatus(long id, String status) {
        jdbc.update("UPDATE TB_USUARIOS_AVATARES SET status_vinculo = ? WHERE usuario_avatar_id = ?", status, id);
    }

    /** Avatar desativado: os vinculos ATIVOS com ele passam para INATIVO. */
    public int inativarVinculosDoAvatar(long avatarId) {
        return jdbc.update("UPDATE TB_USUARIOS_AVATARES SET status_vinculo = 'INATIVO' "
                + "WHERE avatar_id = ? AND status_vinculo = 'ATIVO'", avatarId);
    }
}
