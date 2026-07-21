package gestione.elettrica.gestione_energia_elettrica.repositories;

import gestione.elettrica.gestione_energia_elettrica.entities.Fattura;
import gestione.elettrica.gestione_energia_elettrica.entities.StatoFattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

public interface FatturaRepository extends JpaRepository<Fattura, UUID> {
    Optional<Fattura> findByNumero(String numero);

    List<Fattura> findByCliente(Cliente cliente);

    List<Fattura> findByStatoFattura(StatoFattura statoFattura);

    List<Fattura> findByData(LocalDate data);

    List<Fattura> findByImportoBetween(BigDecimal min, BigDecimal max);

    List<Fattura> findByDataBetween(LocalDate start, LocalDate end);

    Page<Fattura> findAll(Pageable pageable);

    @Query("SELECT f FROM Fattura f WHERE YEAR(f.data) = :anno")
    List<Fattura> findByAnno(@Param("anno") int anno);
}
