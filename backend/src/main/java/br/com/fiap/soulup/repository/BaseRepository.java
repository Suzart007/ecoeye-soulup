package br.com.fiap.soulup.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

/**
 * Base dos DAOs: conversao de datas Java/Oracle e geracao das chaves primarias.
 * As tabelas do modelo nao usam IDENTITY nem SEQUENCE, entao a API calcula o
 * proximo codigo (MAX + 1) e tenta de novo se outro cadastro usar o mesmo numero.
 */
public abstract class BaseRepository {

    private static final int TENTATIVAS_INSERT = 3;

    protected final JdbcTemplate jdbc;

    protected BaseRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    protected <T> T inserirComNovoId(String tabela, String colunaId, LongFunction<T> insert) {
        for (int tentativa = 1; ; tentativa++) {
            Long id = jdbc.queryForObject("SELECT COALESCE(MAX(" + colunaId + "), 0) + 1 FROM " + tabela, Long.class);
            try {
                return insert.apply(id);
            } catch (DuplicateKeyException e) {
                if (tentativa >= TENTATIVAS_INSERT) {
                    throw e;
                }
            }
        }
    }

    protected boolean existe(String sql, Object... parametros) {
        Integer total = jdbc.queryForObject(sql, Integer.class, parametros);
        return total != null && total > 0;
    }

    protected int contar(String sql, Object... parametros) {
        Integer total = jdbc.queryForObject(sql, Integer.class, parametros);
        return total == null ? 0 : total;
    }

    protected static <T> T primeiro(List<T> resultados) {
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    /** Monta o WHERE das listagens com filtros opcionais (?usuarioId=1&status=ATIVA...). */
    protected static final class Filtro {

        private final StringBuilder where = new StringBuilder();
        private final List<Object> parametros = new ArrayList<>();

        public Filtro igual(String coluna, Object valor) {
            if (valor != null) {
                where.append(where.isEmpty() ? " WHERE " : " AND ").append(coluna).append(" = ?");
                parametros.add(valor);
            }
            return this;
        }

        public String where() {
            return where.toString();
        }

        public Object[] parametros() {
            return parametros.toArray();
        }
    }

    // ---- conversoes Java -> JDBC -----------------------------------------

    protected static SqlParameterValue data(LocalDate valor) {
        return new SqlParameterValue(Types.DATE, valor == null ? null : Date.valueOf(valor));
    }

    protected static SqlParameterValue dataHora(LocalDateTime valor) {
        return new SqlParameterValue(Types.TIMESTAMP, valor == null ? null : Timestamp.valueOf(valor));
    }

    protected static SqlParameterValue numero(Long valor) {
        return new SqlParameterValue(Types.NUMERIC, valor);
    }

    protected static SqlParameterValue texto(String valor) {
        return new SqlParameterValue(Types.VARCHAR, valor);
    }

    // ---- conversoes JDBC -> Java -----------------------------------------

    protected static LocalDate lerData(ResultSet rs, String coluna) throws SQLException {
        Timestamp valor = rs.getTimestamp(coluna);
        return valor == null ? null : valor.toLocalDateTime().toLocalDate();
    }

    protected static LocalDateTime lerDataHora(ResultSet rs, String coluna) throws SQLException {
        Timestamp valor = rs.getTimestamp(coluna);
        return valor == null ? null : valor.toLocalDateTime();
    }

    protected static Long lerLong(ResultSet rs, String coluna) throws SQLException {
        long valor = rs.getLong(coluna);
        return rs.wasNull() ? null : valor;
    }
}
