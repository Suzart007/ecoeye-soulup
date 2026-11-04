package br.com.fiap.soulup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

/**
 * API REST do SoulUp (Equipe EcoEye).
 *
 * Variaveis de ambiente usadas:
 *   DB_USER / DB_PASSWORD  usuario (RM) e senha do Oracle da FIAP
 *   DB_URL                 opcional, padrao jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
 *   CORS_ORIGINS           URLs do front liberadas no CORS, separadas por virgula
 *   PORT                   porta HTTP (na Vercel o Dockerfile define 80), padrao 8080
 */
@SpringBootApplication
public class SoulUpApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SoulUpApplication.class);

    public static void main(String[] args) {
        // As colunas DATE do Oracle nao guardam fuso: fixamos o horario de Brasilia
        // para que "hoje" seja o mesmo dia no IntelliJ e no servidor da Vercel.
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        // Evita o erro ORA-01882 (timezone region not found) em alguns servidores.
        System.setProperty("oracle.jdbc.timezoneAsRegion", "false");
        SpringApplication.run(SoulUpApplication.class, args);
    }

    @Bean
    ApplicationRunner avisoCredenciais(@Value("${spring.datasource.username:}") String usuario,
                                       @Value("${spring.datasource.password:}") String senha,
                                       @Value("${spring.datasource.url:}") String url) {
        return args -> {
            boolean oracle = url != null && url.startsWith("jdbc:oracle");
            if (usuario == null || usuario.isBlank()) {
                LOG.warn("DB_USER e DB_PASSWORD nao foram configurados: as rotas que usam o banco vao responder 503.");
            } else if (oracle && (senha == null || senha.isEmpty())) {
                LOG.warn("DB_USER foi lido ({}), mas DB_PASSWORD nao: confira se o nome da variavel e exatamente "
                        + "DB_PASSWORD, em maiusculas e sem espacos. As rotas que usam o banco vao responder 503.", usuario);
            } else {
                LOG.info("Banco configurado: {} (usuario {})", url, usuario);
            }
        };
    }
}
