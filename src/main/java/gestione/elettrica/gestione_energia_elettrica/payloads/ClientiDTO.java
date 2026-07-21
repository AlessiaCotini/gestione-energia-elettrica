package gestione.elettrica.gestione_energia_elettrica.payloads;

import gestione.elettrica.gestione_energia_elettrica.entities.TipoCliente;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record ClientiDTO(
        @NotNull(message = "Il tipo cliente è obbligatorio (PA, SAS, SPA, SRL)")
        TipoCliente tipoCliente,

        @NotBlank(message = "La ragione sociale è obbligatoria")
        String ragioneSociale,

        @NotBlank(message = "La partita IVA è obbligatoria")
        @Size(min = 11, max = 11, message = "La partita IVA deve essere di 11 caratteri")
        String pIva,

        @NotBlank(message = "L'email aziendale è obbligatoria")
        @Email(message = "Inserire un indirizzo email valido")
        String email,

        double fatturatoAnnuale,

        @NotBlank(message = "La PEC è obbligatoria")
        @Email(message = "Inserire una PEC valida")
        String pec,

        @NotBlank(message = "Il telefono è obbligatorio")
        String telefono,

        @NotBlank(message = "L'email del contatto è obbligatoria")
        @Email(message = "Inserire un'email contatto valida")
        String emailContatto,

        @NotBlank(message = "Il nome del contatto è obbligatorio")
        String nomeContatto,

        @NotBlank(message = "Il cognome del contatto è obbligatorio")
        String cognomeContatto,

        @NotBlank(message = "Il telefono del contatto è obbligatorio")
        String telefonoContatto,

        UUID sedeOperativa,

        UUID sedeLegale
) {
}
