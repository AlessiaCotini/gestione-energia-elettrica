package gestione.elettrica.gestione_energia_elettrica.eccezioni;

import lombok.Getter;

import java.util.List;

@Getter
public class Validation extends RuntimeException {
    private final List<String> errorMessages;

    public Validation(List<String> errorMessages) {
        super("Validation failed with multiple errors.");
        this.errorMessages = errorMessages;
    }
}
