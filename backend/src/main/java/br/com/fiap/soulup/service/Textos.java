package br.com.fiap.soulup.service;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/** Utilitarios de texto usados pelos services. */
final class Textos {

    private Textos() {
    }

    static String limpar(String valor) {
        return valor == null ? null : valor.trim().replaceAll("\\s+", " ");
    }

    static String emailNormalizado(String email) {
        return email == null ? null : email.trim().toLowerCase(Locale.ROOT);
    }

    static boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }

    /** Corta o texto para caber na coluna VARCHAR2 do banco. */
    static String limitar(String valor, int tamanho) {
        if (valor == null || valor.length() <= tamanho) {
            return valor;
        }
        return valor.substring(0, tamanho - 3).trim() + "...";
    }

    /** {"logs": 2, "missoes": 0} -> "2 logs" (ignora as contagens zeradas). */
    static String resumirDependencias(Map<String, Integer> dependencias) {
        return dependencias.entrySet().stream()
                .filter(item -> item.getValue() > 0)
                .map(item -> item.getValue() + " " + item.getKey())
                .collect(Collectors.joining(", "));
    }
}
