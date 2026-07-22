package gestione.elettrica.gestione_energia_elettrica.payloads;

import lombok.Getter;

import java.util.Set;


public record UserResponseDTO(
        String username,
        String email,
        String name,
        String surname,
        String avatar,
        Set<String> ruoli,
        Set<String> autorizzazioni
) {}


