package gestione.elettrica.gestione_energia_elettrica.eccezioni;

public class UnAuthorized extends RuntimeException {
    public UnAuthorized(String message) {
        super(message);
    }
}