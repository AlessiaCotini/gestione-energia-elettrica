package gestione.elettrica.gestione_energia_elettrica.repositories;

import gestione.elettrica.gestione_energia_elettrica.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByNomeRuolo(String nomeRuolo);
}
