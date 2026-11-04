package br.com.fiap.soulup.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Converte as excecoes da API em respostas JSON padronizadas:
 * { "status": 404, "erro": "Not Found", "mensagem": "...", "caminho": "/usuarios/99", "timestamp": "..." }
 * O front-end exibe o campo "mensagem" para o usuario.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** Codigo do RAISE_APPLICATION_ERROR da trigger TRG_SUG_USUARIO_ATIVO (regra de negocio 10). */
    private static final int ORA_REGRA_10 = 20010;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ErroResposta(int status, String erro, String mensagem, String caminho,
                               LocalDateTime timestamp, Map<String, String> campos) {
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> naoEncontrado(RecursoNaoEncontradoException e, HttpServletRequest req) {
        return resposta(HttpStatus.NOT_FOUND, e.getMessage(), req, null);
    }

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<ErroResposta> conflito(ConflitoException e, HttpServletRequest req) {
        return resposta(HttpStatus.CONFLICT, e.getMessage(), req, null);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResposta> regraNegocio(RegraNegocioException e, HttpServletRequest req) {
        return resposta(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), req, null);
    }

    /** Falhas do Bean Validation (@NotBlank, @Email, @Min...) no corpo da requisicao. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> validacao(MethodArgumentNotValidException e, HttpServletRequest req) {
        Map<String, String> campos = new LinkedHashMap<>();
        for (FieldError erro : e.getBindingResult().getFieldErrors()) {
            campos.putIfAbsent(erro.getField(), erro.getDefaultMessage());
        }
        String mensagem = campos.isEmpty()
                ? "Dados inválidos."
                : "Dados inválidos: " + String.join("; ", campos.values());
        return resposta(HttpStatus.BAD_REQUEST, mensagem, req, campos);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> jsonInvalido(HttpMessageNotReadableException e, HttpServletRequest req) {
        return resposta(HttpStatus.BAD_REQUEST,
                "O corpo da requisição não é um JSON válido ou tem campos com tipo incorreto.", req, null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResposta> parametroInvalido(MethodArgumentTypeMismatchException e, HttpServletRequest req) {
        return resposta(HttpStatus.BAD_REQUEST,
                "O parâmetro '" + e.getName() + "' recebeu um valor inválido: " + e.getValue(), req, null);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroResposta> parametroAusente(MissingServletRequestParameterException e, HttpServletRequest req) {
        return resposta(HttpStatus.BAD_REQUEST, "O parâmetro '" + e.getParameterName() + "' é obrigatório.", req, null);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroResposta> rotaInexistente(NoResourceFoundException e, HttpServletRequest req) {
        return resposta(HttpStatus.NOT_FOUND, "Rota não encontrada: " + req.getRequestURI(), req, null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErroResposta> metodoNaoSuportado(HttpRequestMethodNotSupportedException e, HttpServletRequest req) {
        return resposta(HttpStatus.METHOD_NOT_ALLOWED,
                "O método " + e.getMethod() + " não é suportado nesta rota.", req, null);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErroResposta> tipoNaoSuportado(HttpMediaTypeNotSupportedException e, HttpServletRequest req) {
        return resposta(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Envie o corpo da requisição em JSON (Content-Type: application/json).", req, null);
    }

    /** Violacao de UNIQUE (ORA-00001) que escapou das validacoes do service. */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErroResposta> chaveDuplicada(DuplicateKeyException e, HttpServletRequest req) {
        return resposta(HttpStatus.CONFLICT, "Já existe um registro com esses dados.", req, null);
    }

    /** FK (ORA-02291/02292) ou CHECK (ORA-02290) recusados pelo banco. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResposta> integridade(DataIntegrityViolationException e, HttpServletRequest req) {
        LOG.warn("Restricao do banco violada: {}", e.getMostSpecificCause().getMessage());
        return resposta(HttpStatus.CONFLICT,
                "O banco de dados recusou a operação porque ela viola uma restrição de integridade.", req, null);
    }

    @ExceptionHandler({CannotGetJdbcConnectionException.class, DataAccessResourceFailureException.class})
    public ResponseEntity<ErroResposta> bancoIndisponivel(DataAccessException e, HttpServletRequest req) {
        LOG.error("Falha ao conectar no banco: {}", e.getMostSpecificCause().getMessage());
        return resposta(HttpStatus.SERVICE_UNAVAILABLE,
                "Não foi possível conectar ao banco de dados Oracle. Confira DB_USER, DB_PASSWORD e o acesso à rede da FIAP.",
                req, null);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErroResposta> erroBanco(DataAccessException e, HttpServletRequest req) {
        if (e.getMostSpecificCause() instanceof SQLException sql && sql.getErrorCode() == ORA_REGRA_10) {
            return resposta(HttpStatus.UNPROCESSABLE_ENTITY, mensagemOracle(sql), req, null);
        }
        LOG.error("Erro de banco de dados", e);
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao acessar o banco de dados.", req, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> erroInesperado(Exception e, HttpServletRequest req) {
        LOG.error("Erro inesperado", e);
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", req, null);
    }

    /** "ORA-20010: RN10: usuario 8 esta BLOQUEADO..." -> "RN10: usuario 8 esta BLOQUEADO..." */
    private static String mensagemOracle(SQLException sql) {
        String texto = sql.getMessage() == null ? "" : sql.getMessage().split("\\R")[0];
        return texto.replaceFirst("^ORA-\\d+:\\s*", "");
    }

    private static ResponseEntity<ErroResposta> resposta(HttpStatus status, String mensagem,
                                                         HttpServletRequest req, Map<String, String> campos) {
        ErroResposta corpo = new ErroResposta(status.value(), status.getReasonPhrase(), mensagem,
                req.getRequestURI(), LocalDateTime.now().withNano(0), campos);
        return ResponseEntity.status(status).body(corpo);
    }
}
