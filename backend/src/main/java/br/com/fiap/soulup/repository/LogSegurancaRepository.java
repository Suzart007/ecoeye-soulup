package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.LogSeguranca;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** DAO da tabela TB_LOGS_SEGURANCA. */
@Repository
public class LogSegurancaRepository extends BaseRepository {

    private static final String SELECT =
            "SELECT log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip FROM TB_LOGS_SEGURANCA";

    private static final RowMapper<LogSeguranca> MAPPER = (rs, linha) -> new LogSeguranca(
            rs.getLong("log_id"),
            rs.getLong("usuario_id"),
            lerDataHora(rs, "data_log"),
            rs.getString("tipo_evento"),
            rs.getString("descricao_evento"),
            rs.getString("endereco_ip"));

    public LogSegurancaRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<LogSeguranca> listar(Long usuarioId, String tipoEvento) {
        Filtro filtro = new Filtro()
                .igual("usuario_id", usuarioId)
                .igual("tipo_evento", tipoEvento);
        return jdbc.query(SELECT + filtro.where() + " ORDER BY data_log DESC, log_id DESC", MAPPER, filtro.parametros());
    }

    public Optional<LogSeguranca> buscarPorId(long id) {
        return Optional.ofNullable(primeiro(jdbc.query(SELECT + " WHERE log_id = ?", MAPPER, id)));
    }

    /** Eventos de acesso do usuario, do mais recente para o mais antigo (base da regra de bloqueio). */
    public List<String> ultimosEventosDeAcesso(long usuarioId, int limite) {
        List<String> eventos = jdbc.queryForList(
                "SELECT tipo_evento FROM TB_LOGS_SEGURANCA WHERE usuario_id = ? "
                        + "AND tipo_evento IN ('LOGIN_SUCESSO', 'LOGIN_FALHA', 'BLOQUEIO_CONTA') "
                        + "ORDER BY data_log DESC, log_id DESC",
                String.class, usuarioId);
        return eventos.size() > limite ? eventos.subList(0, limite) : eventos;
    }

    public LogSeguranca inserir(LogSeguranca log) {
        return inserirComNovoId("TB_LOGS_SEGURANCA", "log_id", id -> {
            jdbc.update("INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip) "
                            + "VALUES (?, ?, ?, ?, ?, ?)",
                    id, log.getUsuarioId(), dataHora(log.getDataLog()), log.getTipoEvento(), log.getDescricao(),
                    texto(log.getEnderecoIp()));
            log.setId(id);
            return log;
        });
    }
}
