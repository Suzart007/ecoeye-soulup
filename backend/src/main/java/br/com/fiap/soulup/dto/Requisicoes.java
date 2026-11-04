package br.com.fiap.soulup.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Corpos (JSON) recebidos pelos endpoints que nao recebem a entidade inteira. */
public final class Requisicoes {

    private Requisicoes() {
    }

    /** POST /vinculos */
    public record VinculoRequest(
            @NotNull(message = "Informe o usuarioId.") Long usuarioId,
            @NotNull(message = "Informe o avatarId.") Long avatarId) {
    }

    /** PUT /vinculos/{id}/status */
    public record StatusVinculoRequest(
            @NotBlank(message = "Informe o status.")
            @Pattern(regexp = "ATIVO|INATIVO|ENCERRADO", message = "O status do vínculo deve ser ATIVO, INATIVO ou ENCERRADO.")
            String status) {
    }

    /** POST /interacoes - se respostaAvatar vier vazia, o proprio avatar responde. */
    public record InteracaoRequest(
            @NotNull(message = "Informe o usuarioAvatarId (vínculo usuário-avatar).") Long usuarioAvatarId,
            @NotBlank(message = "A mensagem do usuário é obrigatória.")
            @Size(max = 500, message = "A mensagem deve ter no máximo 500 caracteres.") String mensagemUsuario,
            @Size(max = 500, message = "A resposta deve ter no máximo 500 caracteres.") String respostaAvatar,
            @Pattern(regexp = "CONVERSA|SUGESTAO|MISSAO|SUPORTE", message = "O tipo deve ser CONVERSA, SUGESTAO, MISSAO ou SUPORTE.")
            String tipo) {
    }

    /** POST /participacoes */
    public record ParticipacaoRequest(
            @NotNull(message = "Informe o usuarioId.") Long usuarioId,
            @NotNull(message = "Informe o missaoId.") Long missaoId) {
    }

    /** PUT /participacoes/{id}/progresso */
    public record ProgressoRequest(
            @NotNull(message = "Informe o progresso.")
            @Min(value = 0, message = "O progresso mínimo é 0.")
            @Max(value = 100, message = "O progresso máximo é 100.") Integer progresso) {
    }

    /** POST /sugestoes/gerar - avatarId e opcional (usa o vinculo ativo mais recente). */
    public record GerarSugestaoRequest(
            @NotNull(message = "Informe o usuarioId.") Long usuarioId,
            Long avatarId) {
    }

    /** POST /logs */
    public record LogRequest(
            @NotNull(message = "Informe o usuarioId.") Long usuarioId,
            @NotBlank(message = "Informe o tipoEvento.")
            @Pattern(regexp = "[A-Z_]{3,80}", message = "O tipoEvento deve estar em MAIÚSCULAS, ex.: LOGIN_SUCESSO, LOGIN_FALHA.")
            String tipoEvento,
            @NotBlank(message = "A descrição do evento é obrigatória.")
            @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.") String descricao,
            @Size(max = 45, message = "O IP deve ter no máximo 45 caracteres.") String enderecoIp) {
    }

    /** POST e PUT /memorias */
    public record MemoriaRequest(
            @NotNull(message = "Informe o usuarioAvatarId (vínculo usuário-avatar).") Long usuarioAvatarId,
            @NotBlank(message = "A categoria é obrigatória.")
            @Pattern(regexp = "PREFERENCIA|ROTINA|INTERESSE|HUMOR|HORARIO_ATIVO|EVENTO_IMPORTANTE",
                    message = "A categoria deve ser PREFERENCIA, ROTINA, INTERESSE, HUMOR, HORARIO_ATIVO ou EVENTO_IMPORTANTE.")
            String categoria,
            @NotBlank(message = "A chave é obrigatória.")
            @Size(max = 100, message = "A chave deve ter no máximo 100 caracteres.") String chave,
            @NotBlank(message = "O valor é obrigatório.")
            @Size(max = 500, message = "O valor deve ter no máximo 500 caracteres.") String valor,
            @NotNull(message = "A relevância é obrigatória.")
            @Min(value = 0, message = "A relevância mínima é 0.")
            @Max(value = 100, message = "A relevância máxima é 100.") Integer relevancia,
            @Pattern(regexp = "ATIVA|ARQUIVADA|EXPIRADA", message = "O status deve ser ATIVA, ARQUIVADA ou EXPIRADA.")
            String status) {
    }
}
