package gestione.elettrica.gestione_energia_elettrica.eccezioni;

public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}
