package gestione.elettrica.gestione_energia_elettrica.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(
        @NotBlank(message = "Username obbligatorio") String username,
        @Email @NotBlank(message = "Email obbligatoria") String email,
        @NotBlank(message = "Password obbligatoria") String password,
        @NotBlank(message = "Nome obbligatorio") String name,
        @NotBlank(message = "Cognome obbligatorio") String surname
) {
}

