package br.com.fiap.soulup.exception;

/** Os dados sao validos, mas violam uma regra de negocio do SoulUp (HTTP 422). */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
