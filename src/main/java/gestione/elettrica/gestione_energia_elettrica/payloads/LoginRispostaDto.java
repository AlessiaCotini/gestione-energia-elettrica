package gestione.elettrica.gestione_energia_elettrica.payloads;

import gestione.elettrica.gestione_energia_elettrica.entities.Role;

import java.util.Set;

public record LoginRispostaDto(String accessToken, Set<Role> ruolo) {
}
