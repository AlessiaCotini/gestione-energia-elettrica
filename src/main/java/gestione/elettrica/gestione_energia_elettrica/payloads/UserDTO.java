package gestione.elettrica.gestione_energia_elettrica.payloads;

import gestione.elettrica.gestione_energia_elettrica.entities.Role;
import jakarta.validation.constraints.*;

public record UserDTO(
        @NotBlank
        String username,

        @NotBlank(message = "Email necessaria")
        @Email
        String email,

        @NotBlank(message = "Password necessaria")
        @Size(min = 5)
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "La password deve contenere i caratteri necessari")
        String password,

        @NotBlank
        String name,

        @NotBlank
        String surname,

        String avatar,

        @NotBlank
        Role ruolo
) {
}
