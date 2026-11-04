package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.dto.PontuacaoUsuario;
import br.com.fiap.soulup.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** DAO da tabela TB_USUARIOS. */
@Repository
public class UsuarioRepository extends BaseRepository {

    private static final String SELECT =
            "SELECT usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario FROM TB_USUARIOS";

    private static final RowMapper<Usuario> MAPPER = (rs, linha) -> new Usuario(
            rs.getLong("usuario_id"),
            rs.getString("nome_usuario"),
            rs.getString("email_usuario"),
            lerData(rs, "data_cadastro"),
            rs.getString("situacao_usuario"));

    public UsuarioRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<Usuario> listar(String situacao) {
        Filtro filtro = new Filtro().igual("situacao_usuario", situacao);
        return jdbc.query(SELECT + filtro.where() + " ORDER BY usuario_id", MAPPER, filtro.parametros());
    }

    public Optional<Usuario> buscarPorId(long id) {
        return Optional.ofNullable(primeiro(jdbc.query(SELECT + " WHERE usuario_id = ?", MAPPER, id)));
    }

    /** E-mail unico (UK_TB_USUARIOS_EMAIL), sem diferenciar maiusculas de minusculas. */
    public boolean existeEmail(String email, Long ignorarId) {
        return existe("SELECT COUNT(*) FROM TB_USUARIOS WHERE LOWER(email_usuario) = LOWER(?) AND usuario_id <> ?",
                email, ignorarId == null ? -1L : ignorarId);
    }

    public Usuario inserir(Usuario usuario) {
        return inserirComNovoId("TB_USUARIOS", "usuario_id", id -> {
            jdbc.update("INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario) "
                            + "VALUES (?, ?, ?, ?, ?)",
                    id, usuario.getNome(), usuario.getEmail(), data(usuario.getDataCadastro()), usuario.getSituacao());
            usuario.setId(id);
            return usuario;
        });
    }

    public void atualizar(Usuario usuario) {
        jdbc.update("UPDATE TB_USUARIOS SET nome_usuario = ?, email_usuario = ?, data_cadastro = ?, situacao_usuario = ? "
                        + "WHERE usuario_id = ?",
                usuario.getNome(), usuario.getEmail(), data(usuario.getDataCadastro()), usuario.getSituacao(), usuario.getId());
    }

    public void atualizarSituacao(long id, String situacao) {
        jdbc.update("UPDATE TB_USUARIOS SET situacao_usuario = ? WHERE usuario_id = ?", situacao, id);
    }

    public void excluir(long id) {
        jdbc.update("DELETE FROM TB_USUARIOS WHERE usuario_id = ?", id);
    }

    /** Registros filhos que impedem a exclusao (FKs fk_ua_usuario, fk_um_usuario, fk_sug_usuario e fk_logs_usuario). */
    public Map<String, Integer> contarDependencias(long id) {
        Map<String, Integer> dependencias = new LinkedHashMap<>();
        dependencias.put("vínculo(s) com avatares", contar("SELECT COUNT(*) FROM TB_USUARIOS_AVATARES WHERE usuario_id = ?", id));
        dependencias.put("participação(ões) em missões", contar("SELECT COUNT(*) FROM TB_USUARIOS_MISSOES WHERE usuario_id = ?", id));
        dependencias.put("sugestão(ões) do avatar", contar("SELECT COUNT(*) FROM TB_SUGESTOES_AVATAR WHERE usuario_id = ?", id));
        dependencias.put("log(s) de segurança", contar("SELECT COUNT(*) FROM TB_LOGS_SEGURANCA WHERE usuario_id = ?", id));
        return dependencias;
    }

    /** Pontos e missoes concluidas de um usuario. */
    public PontuacaoUsuario pontuacao(long usuarioId) {
        return jdbc.queryForObject(
                "SELECT COUNT(um.usuario_missao_id) AS concluidas, COALESCE(SUM(m.pontuacao_missao), 0) AS pontos "
                        + "FROM TB_USUARIOS_MISSOES um JOIN TB_MISSOES m ON m.missao_id = um.missao_id "
                        + "WHERE um.usuario_id = ? AND um.status_missao = 'CONCLUIDA'",
                (rs, linha) -> new PontuacaoUsuario(usuarioId, null, rs.getInt("concluidas"), rs.getInt("pontos")),
                usuarioId);
    }

    /** Consulta C1 do banco: ranking dos usuarios ATIVOS pelos pontos das missoes concluidas. */
    public List<PontuacaoUsuario> ranking() {
        return jdbc.query(
                "SELECT u.usuario_id, u.nome_usuario, COUNT(um.usuario_missao_id) AS concluidas, "
                        + "SUM(m.pontuacao_missao) AS pontos "
                        + "FROM TB_USUARIOS u "
                        + "JOIN TB_USUARIOS_MISSOES um ON um.usuario_id = u.usuario_id "
                        + "JOIN TB_MISSOES m ON m.missao_id = um.missao_id "
                        + "WHERE um.status_missao = 'CONCLUIDA' AND u.situacao_usuario = 'ATIVO' "
                        + "GROUP BY u.usuario_id, u.nome_usuario "
                        + "ORDER BY pontos DESC, u.nome_usuario",
                (rs, linha) -> new PontuacaoUsuario(rs.getLong("usuario_id"), rs.getString("nome_usuario"),
                        rs.getInt("concluidas"), rs.getInt("pontos")));
    }
}
