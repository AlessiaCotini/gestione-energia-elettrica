package gestione.elettrica.gestione_energia_elettrica.services;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.Cliente;
import gestione.elettrica.gestione_energia_elettrica.entities.Fattura;
import gestione.elettrica.gestione_energia_elettrica.entities.StatoFattura;
import gestione.elettrica.gestione_energia_elettrica.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    public Fattura save(Fattura fattura) {
        return fatturaRepository.save(fattura);
    }

    public Page<Fattura> findAll(int page, int size, String orderBy) {
        if (size <= 0) size = 10;
        if (size > 15) size = 15;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return fatturaRepository.findAll(pageable);
    }

    public Fattura findById(UUID id) {
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFound("Fattura non trovata con id: " + id));
    }

    public Fattura update(UUID id, Fattura fatturaAggiornata) {

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

    public List<Fattura> findByCliente(Cliente cliente) {
        return fatturaRepository.findByCliente(cliente);
    }

    public List<Fattura> findByStato(StatoFattura stato) {
        return fatturaRepository.findByStatoFattura(stato);
    }

    public List<Fattura> findByData(LocalDate data) {
        return fatturaRepository.findByData(data);
    }

    public List<Fattura> findByImporto(BigDecimal min, BigDecimal max) {
        return fatturaRepository.findByImportoBetween(min, max);
    }

    public List<Fattura> findByPeriodo(LocalDate start, LocalDate end) {
        return fatturaRepository.findByDataBetween(start, end);
    }

    public List<Fattura> findByAnno(int anno) {
        return fatturaRepository.findByAnno(anno);
    }
}
