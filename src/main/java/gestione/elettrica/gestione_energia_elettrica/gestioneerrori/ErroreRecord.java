package gestione.elettrica.gestione_energia_elettrica.gestioneerrori;

import java.time.LocalDateTime;
import java.util.List;

public record ErroreRecord(String message, LocalDateTime timestamp, List<String> errorsList) {
}
