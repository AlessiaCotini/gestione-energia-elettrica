package gestione.elettrica.gestione_energia_elettrica.eccezioni;

public class AccessDenied extends RuntimeException {
    public AccessDenied(String message) {
        super(message);
    }
}
