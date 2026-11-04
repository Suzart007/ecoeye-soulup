package br.com.fiap.soulup.model;

import java.time.LocalDate;

/**
 * Algo que o avatar aprendeu sobre o usuario para personalizar a conversa
 * (tabela TB_MEMORIAS_USUARIO). A chave e unica dentro de cada vinculo usuario-avatar.
 */
public class MemoriaUsuario {

    public static final String ATIVA = "ATIVA";
    public static final String ARQUIVADA = "ARQUIVADA";
    public static final String EXPIRADA = "EXPIRADA";

    private Long id;
    private Long usuarioAvatarId;
    private String categoria;
    private String chave;
    private String valor;
    private Integer relevancia;
    private LocalDate dataRegistro;
    private LocalDate dataUltimaAtualizacao;
    private String status;

    public MemoriaUsuario() {
    }

    public MemoriaUsuario(Long id, Long usuarioAvatarId, String categoria, String chave, String valor,
                          Integer relevancia, LocalDate dataRegistro, LocalDate dataUltimaAtualizacao, String status) {
        this.id = id;
        this.usuarioAvatarId = usuarioAvatarId;
        this.categoria = categoria;
        this.chave = chave;
        this.valor = valor;
        this.relevancia = relevancia;
        this.dataRegistro = dataRegistro;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioAvatarId() {
        return usuarioAvatarId;
    }

    public void setUsuarioAvatarId(Long usuarioAvatarId) {
        this.usuarioAvatarId = usuarioAvatarId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getRelevancia() {
        return relevancia;
    }

    public void setRelevancia(Integer relevancia) {
        this.relevancia = relevancia;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDate getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(LocalDate dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
