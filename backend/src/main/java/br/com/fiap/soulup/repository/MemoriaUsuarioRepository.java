package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.MemoriaUsuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** DAO da tabela TB_MEMORIAS_USUARIO. */
@Repository
public class MemoriaUsuarioRepository extends BaseRepository {

    private static final String SELECT =
            "SELECT memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, "
                    + "data_registro, data_ultima_atualizacao, status_memoria FROM TB_MEMORIAS_USUARIO";

    private static final RowMapper<MemoriaUsuario> MAPPER = (rs, linha) -> new MemoriaUsuario(
            rs.getLong("memoria_id"),
            rs.getLong("usuario_avatar_id"),
            rs.getString("categoria_memoria"),
            rs.getString("chave_memoria"),
            rs.getString("valor_memoria"),
            rs.getInt("relevancia_memoria"),
            lerData(rs, "data_registro"),
            lerData(rs, "data_ultima_atualizacao"),
            rs.getString("status_memoria"));

    public MemoriaUsuarioRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<MemoriaUsuario> listar(Long usuarioAvatarId, String categoria, String status) {
        Filtro filtro = new Filtro()
                .igual("usuario_avatar_id", usuarioAvatarId)
                .igual("categoria_memoria", categoria)
                .igual("status_memoria", status);
        return jdbc.query(SELECT + filtro.where() + " ORDER BY relevancia_memoria DESC, memoria_id", MAPPER,
                filtro.parametros());
    }

    public Optional<MemoriaUsuario> buscarPorId(long id) {
        return Optional.ofNullable(primeiro(jdbc.query(SELECT + " WHERE memoria_id = ?", MAPPER, id)));
    }

    /** Chave unica por vinculo (UK_TB_MEMORIAS_CHAVE). */
    public boolean existeChave(long usuarioAvatarId, String chave, Long ignorarId) {
        return existe("SELECT COUNT(*) FROM TB_MEMORIAS_USUARIO "
                        + "WHERE usuario_avatar_id = ? AND chave_memoria = ? AND memoria_id <> ?",
                usuarioAvatarId, chave, ignorarId == null ? -1L : ignorarId);
    }

    public MemoriaUsuario inserir(MemoriaUsuario memoria) {
        return inserirComNovoId("TB_MEMORIAS_USUARIO", "memoria_id", id -> {
            jdbc.update("INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, "
                            + "valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    id, memoria.getUsuarioAvatarId(), memoria.getCategoria(), memoria.getChave(), memoria.getValor(),
                    memoria.getRelevancia(), data(memoria.getDataRegistro()), data(memoria.getDataUltimaAtualizacao()),
                    memoria.getStatus());
            memoria.setId(id);
            return memoria;
        });
    }

    public void atualizar(MemoriaUsuario memoria) {
        jdbc.update("UPDATE TB_MEMORIAS_USUARIO SET categoria_memoria = ?, chave_memoria = ?, valor_memoria = ?, "
                        + "relevancia_memoria = ?, data_ultima_atualizacao = ?, status_memoria = ? WHERE memoria_id = ?",
                memoria.getCategoria(), memoria.getChave(), memoria.getValor(), memoria.getRelevancia(),
                data(memoria.getDataUltimaAtualizacao()), memoria.getStatus(), memoria.getId());
    }

    public void excluir(long id) {
        jdbc.update("DELETE FROM TB_MEMORIAS_USUARIO WHERE memoria_id = ?", id);
    }
}
