package gestione.elettrica.gestione_energia_elettrica.payloads;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank String email,
        @NotBlank String password
) {}