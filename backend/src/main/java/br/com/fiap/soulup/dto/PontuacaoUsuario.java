package br.com.fiap.soulup.dto;

/** Soma dos pontos das missoes CONCLUIDAS de um usuario. */
public record PontuacaoUsuario(Long usuarioId, String nome, int missoesConcluidas, int pontos) {
}
