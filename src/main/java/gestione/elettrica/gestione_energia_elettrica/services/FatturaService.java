package gestione.elettrica.gestione_energia_elettrica.services;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.Fattura;
import gestione.elettrica.gestione_energia_elettrica.repositories.FatturaRepository;
import gestione.elettrica.gestione_energia_elettrica.specifications.FatturaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    public Fattura save(Fattura fattura) {
        return fatturaRepository.save(fattura);
    }

    public Page<Fattura> findAll(
            int page,
            int size,
            String sortBy,
            String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return fatturaRepository.findAll(pageable);
    }

    public Fattura findById(UUID id) {

        return fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFound("Fattura non trovata con id: " + id));
    }

    public Fattura update(
            UUID id,
            Fattura fatturaAggiornata) {
        Fattura fattura = findById(id);
        fattura.setNumeroFattura(fatturaAggiornata.getNumeroFattura());
        fattura.setData(fatturaAggiornata.getData());
        fattura.setImporto(fatturaAggiornata.getImporto());
        fattura.setCliente(fatturaAggiornata.getCliente());
        fattura.setStatoFattura(fatturaAggiornata.getStatoFattura());
        return fatturaRepository.save(fattura);
    }


    public void delete(UUID id) {
        Fattura fattura = findById(id);

        fatturaRepository.delete(fattura);
    }


    public Page<Fattura> search(
            int page,
            int size,
            String sortBy,
            String order,
            UUID clienteId,
            UUID statoId,
            LocalDate data,
            LocalDate start,
            LocalDate end,
            BigDecimal min,
            BigDecimal max,
            Integer anno

    ) {
        Specification<Fattura> specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (clienteId != null) {
            specification = specification.and(FatturaSpecification.hasCliente(clienteId));
        }


        if (statoId != null) {
            specification = specification.and(FatturaSpecification.hasStato(statoId));
        }


        if (data != null) {
            specification = specification.and(FatturaSpecification.hasData(data));
        }

        if (start != null && end != null) {
            specification = specification.and(FatturaSpecification.dataBetween(start, end));

        } else if (start != null) {
            specification = specification.and(FatturaSpecification.dataGreaterThanOrEqualTo(start));

        } else if (end != null) {
            specification = specification.and(FatturaSpecification.dataLessThanOrEqualTo(end));
        }


        if (min != null && max != null) {
            specification = specification.and(FatturaSpecification.importoBetween(min, max));
        } else if (min != null) {
            specification = specification.and(FatturaSpecification.importoGreaterThanOrEqualTo(min));
        } else if (max != null) {
            specification = specification.and(FatturaSpecification.importoLessThanOrEqualTo(max));
        }
        if (anno != null) {
            specification = specification.and(FatturaSpecification.hasAnno(anno));
        }


        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;


        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return fatturaRepository.findAll(specification, pageable);
    }
}