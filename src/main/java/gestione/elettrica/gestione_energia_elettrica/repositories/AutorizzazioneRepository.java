package gestione.elettrica.gestione_energia_elettrica.repositories;

import gestione.elettrica.gestione_energia_elettrica.entities.Autorizzazione;
import gestione.elettrica.gestione_energia_elettrica.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutorizzazioneRepository extends JpaRepository<Autorizzazione, UUID> {
    Optional<Autorizzazione> findByNome(String nomeAutorizzazione);
}
