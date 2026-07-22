package gestione.elettrica.gestione_energia_elettrica.eccezioni;

public class FileUpload extends RuntimeException {
    public FileUpload(String message) {
        super(message);
    }
}
