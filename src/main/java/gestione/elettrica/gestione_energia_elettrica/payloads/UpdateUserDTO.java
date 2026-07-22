package gestione.elettrica.gestione_energia_elettrica.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
        @NotBlank(message = "Username obbligatorio") String username,
        @Email @NotBlank(message = "Email obbligatoria") String email,
        String password, // Opzionale: se null o blank non viene aggiornata
        @NotBlank(message = "Nome obbligatorio") String name,
        @NotBlank(message = "Cognome obbligatorio") String surname
) {}
