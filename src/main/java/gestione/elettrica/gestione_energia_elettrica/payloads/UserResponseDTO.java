package gestione.elettrica.gestione_energia_elettrica.payloads;

import java.util.Set;
import java.util.UUID;


public record UserResponseDTO(UUID id,
                              String username,
                              String email,
                              String name,
                              String surname,
                              String avatar,
                              Set<String> ruoli,
                              Set<String> autorizzazioni
) {
}


