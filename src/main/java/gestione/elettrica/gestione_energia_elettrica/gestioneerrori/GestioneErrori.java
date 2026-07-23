package gestione.elettrica.gestione_energia_elettrica.gestioneerrori;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GestioneErrori {

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> badRequest(HttpClientErrorException.BadRequest exception) {
        return creaRispostaErrore(HttpStatus.BAD_REQUEST, "Bad Request", exception.getMessage());
    }

    @ExceptionHandler(AccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> accessDenied(HttpClientErrorException.Forbidden exception) {
        return creaRispostaErrore(HttpStatus.FORBIDDEN, "Access Denied", exception.getMessage());
    }

    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> notFound(HttpClientErrorException.NotFound exception) {
        return creaRispostaErrore(HttpStatus.NOT_FOUND, "Not Found", exception.getMessage());
    }

    @ExceptionHandler(UnAuthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> unAuthorized(HttpClientErrorException.Unauthorized exception) {
        return creaRispostaErrore(HttpStatus.UNAUTHORIZED, "UnAuthorized", exception.getMessage());
    }


    private Map<String, Object> creaRispostaErrore(HttpStatus status, String errore, String messaggio) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", errore);
        response.put("message", messaggio);
        return response;
    }

    @ExceptionHandler(Validation.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErroreRecord handleValidation(Validation ex) {
        return new ErroreRecord(ex.getMessage(), LocalDateTime.now(), ex.getErrorMessages());
    }
}
