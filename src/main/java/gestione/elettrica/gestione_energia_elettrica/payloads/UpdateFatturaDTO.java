package gestione.elettrica.gestione_energia_elettrica.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateFatturaDTO(
        @NotBlank(message = "Il numero fattura è obbligatorio")
        String numero,

        @NotNull(message = "La data è obbligatoria")
        LocalDate data,

        @NotNull(message = "L'importo è obbligatorio")
        @Positive(message = "L'importo deve essere maggiore di 0")
        BigDecimal importo,

        @NotNull(message = "Il cliente è obbligatorio")
        UUID clienteId,

        @NotNull(message = "Lo stato fattura è obbligatorio")
        UUID statoFatturaId
) {

}
