package gestione.elettrica.gestione_energia_elettrica.repositories;

import gestione.elettrica.gestione_energia_elettrica.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, UUID> {
    Optional<Provincia> findByNome(String nome);
}
