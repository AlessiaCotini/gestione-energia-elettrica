package gestione.elettrica.gestione_energia_elettrica.repositories;

import gestione.elettrica.gestione_energia_elettrica.entities.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo, UUID> {
}
