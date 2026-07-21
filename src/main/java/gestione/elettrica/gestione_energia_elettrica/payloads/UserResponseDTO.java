package gestione.elettrica.gestione_energia_elettrica.payloads;

import lombok.Getter;

import java.util.Set;


public record UserResponseDTO(
        String username,
        String email,
        String password,
        String name,
        String surname,
        Set<String> ruoli,
        Set<String> autorizzazioni
) {}


