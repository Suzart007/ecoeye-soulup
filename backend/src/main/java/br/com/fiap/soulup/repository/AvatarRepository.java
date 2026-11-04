package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.Avatar;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** DAO da tabela TB_AVATARES. */
@Repository
public class AvatarRepository extends BaseRepository {

    private static final String SELECT =
            "SELECT avatar_id, nome_avatar, descricao_avatar, personalidade_avatar, situacao_avatar FROM TB_AVATARES";

    private static final RowMapper<Avatar> MAPPER = (rs, linha) -> new Avatar(
            rs.getLong("avatar_id"),
            rs.getString("nome_avatar"),
            rs.getString("descricao_avatar"),
            rs.getString("personalidade_avatar"),
            rs.getString("situacao_avatar"));

    public AvatarRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<Avatar> listar(String situacao) {
        Filtro filtro = new Filtro().igual("situacao_avatar", situacao);
        return jdbc.query(SELECT + filtro.where() + " ORDER BY avatar_id", MAPPER, filtro.parametros());
    }

    public Optional<Avatar> buscarPorId(long id) {
        return Optional.ofNullable(primeiro(jdbc.query(SELECT + " WHERE avatar_id = ?", MAPPER, id)));
    }

    public Avatar inserir(Avatar avatar) {
        return inserirComNovoId("TB_AVATARES", "avatar_id", id -> {
            jdbc.update("INSERT INTO TB_AVATARES (avatar_id, nome_avatar, descricao_avatar, personalidade_avatar, situacao_avatar) "
                            + "VALUES (?, ?, ?, ?, ?)",
                    id, avatar.getNome(), avatar.getDescricao(), avatar.getPersonalidade(), avatar.getSituacao());
            avatar.setId(id);
            return avatar;
        });
    }

    public void atualizar(Avatar avatar) {
        jdbc.update("UPDATE TB_AVATARES SET nome_avatar = ?, descricao_avatar = ?, personalidade_avatar = ?, situacao_avatar = ? "
                        + "WHERE avatar_id = ?",
                avatar.getNome(), avatar.getDescricao(), avatar.getPersonalidade(), avatar.getSituacao(), avatar.getId());
    }

    public void excluir(long id) {
        jdbc.update("DELETE FROM TB_AVATARES WHERE avatar_id = ?", id);
    }

    /** Registros filhos que impedem a exclusao (FKs fk_ua_avatar e fk_sug_avatar). */
    public Map<String, Integer> contarDependencias(long id) {
        Map<String, Integer> dependencias = new LinkedHashMap<>();
        dependencias.put("vínculo(s) com usuários", contar("SELECT COUNT(*) FROM TB_USUARIOS_AVATARES WHERE avatar_id = ?", id));
        dependencias.put("sugestão(ões) enviadas", contar("SELECT COUNT(*) FROM TB_SUGESTOES_AVATAR WHERE avatar_id = ?", id));
        return dependencias;
    }
}
