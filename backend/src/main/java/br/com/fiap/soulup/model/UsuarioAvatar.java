package br.com.fiap.soulup.model;

import br.com.fiap.soulup.exception.RegraNegocioException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

/**
 * Vinculo entre um usuario e um avatar (tabela associativa TB_USUARIOS_AVATARES).
 * As interacoes e as memorias do avatar sempre pertencem a um vinculo.
 */
public class UsuarioAvatar {

    public static final String ATIVO = "ATIVO";
    public static final String INATIVO = "INATIVO";
    public static final String ENCERRADO = "ENCERRADO";

    private Long id;
    private Long usuarioId;
    private Long avatarId;
    private LocalDate dataVinculo;
    private String status;

    public UsuarioAvatar() {
    }

    public UsuarioAvatar(Long id, Long usuarioId, Long avatarId, LocalDate dataVinculo, String status) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.avatarId = avatarId;
        this.dataVinculo = dataVinculo;
        this.status = status;
    }

    @JsonIgnore
    public boolean isAtivo() {
        return ATIVO.equals(status);
    }

    /** Um vinculo ENCERRADO e definitivo: faz parte do historico e nao volta a ficar ativo. */
    public void alterarStatus(String novoStatus) {
        if (ENCERRADO.equals(status) && !ENCERRADO.equals(novoStatus)) {
            throw new RegraNegocioException("O vínculo " + id + " está ENCERRADO e não pode ser reativado. Crie um novo vínculo.");
        }
        this.status = novoStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public LocalDate getDataVinculo() {
        return dataVinculo;
    }

    public void setDataVinculo(LocalDate dataVinculo) {
        this.dataVinculo = dataVinculo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
