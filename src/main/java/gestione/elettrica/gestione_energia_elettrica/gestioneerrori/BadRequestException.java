package gestione.elettrica.gestione_energia_elettrica.gestioneerrori;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
