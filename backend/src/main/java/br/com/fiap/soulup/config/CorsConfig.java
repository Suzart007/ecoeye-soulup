package br.com.fiap.soulup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * Libera o front-end React (Vite em http://localhost:5173 e a URL publicada)
 * para chamar a API. As origens vem da variavel CORS_ORIGINS, separadas por
 * virgula; padroes como https://*.vercel.app tambem sao aceitos.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final String[] origens;

    public CorsConfig(@Value("${soulup.cors.origins}") String origens) {
        this.origens = Arrays.stream(origens.split(","))
                .map(String::trim)
                .map(origem -> origem.endsWith("/") ? origem.substring(0, origem.length() - 1) : origem)
                .filter(origem -> !origem.isEmpty())
                .toArray(String[]::new);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(origens)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Location")
                .maxAge(3600);
    }
}
