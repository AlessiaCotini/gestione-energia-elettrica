package gestione.elettrica.gestione_energia_elettrica.repositories;

import gestione.elettrica.gestione_energia_elettrica.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, UUID> {
}
