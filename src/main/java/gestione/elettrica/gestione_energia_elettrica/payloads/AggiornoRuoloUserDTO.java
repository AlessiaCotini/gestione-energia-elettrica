package gestione.elettrica.gestione_energia_elettrica.payloads;

import java.util.Set;
import java.util.UUID;

public record AggiornoRuoloUserDTO(Set<UUID> roleIds) {
}
