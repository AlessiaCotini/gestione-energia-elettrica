package gestione.elettrica.gestione_energia_elettrica.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record IndirizzoDTO(
        @NotBlank(message = "La via è obbligatoria")
        String via,

        @NotBlank(message = "Il civico è obbligatorio")
        String civico,

        @NotBlank(message = "La località è obbligatoria")
        String localita,

        @Min(value = 1, message = "Il cap deve essere maggiore di 0")
        int cap,

        @NotNull(message = "Il comune è obbligatorio")
        UUID comuneId
) {
}
