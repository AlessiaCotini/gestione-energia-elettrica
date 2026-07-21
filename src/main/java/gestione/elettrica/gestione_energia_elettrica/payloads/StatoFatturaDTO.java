package gestione.elettrica.gestione_energia_elettrica.payloads;

import jakarta.validation.constraints.NotBlank;

public record StatoFatturaDTO(
        @NotBlank(message = "Il nome dello stato è obbligatorio")
        String nome
) {
}
