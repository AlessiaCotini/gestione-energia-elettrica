package gestione.elettrica.gestione_energia_elettrica.gestioneerrori;

import java.time.LocalDateTime;

public record ErroreRecord (String message, LocalDateTime localDateTime){
}
