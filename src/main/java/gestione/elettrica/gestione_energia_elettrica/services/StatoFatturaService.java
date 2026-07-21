package gestione.elettrica.gestione_energia_elettrica.services;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.StatoFattura;
import gestione.elettrica.gestione_energia_elettrica.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StatoFatturaService {

    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    public StatoFattura save(StatoFattura statoFattura) {
        return statoFatturaRepository.save(statoFattura);
    }

    public List<StatoFattura> findAll() {
        return statoFatturaRepository.findAll();
    }

    public StatoFattura findById(UUID id) {
        return statoFatturaRepository.findById(id)
                .orElseThrow(() -> new NotFound("Stato fattura non trovato con id: " + id));
    }

    public StatoFattura update(UUID id, StatoFattura statoAggiornato) {

        StatoFattura stato = findById(id);

        stato.setNome(statoAggiornato.getNome());

        return statoFatturaRepository.save(stato);
    }

    public void delete(UUID id) {

        StatoFattura stato = findById(id);

        statoFatturaRepository.delete(stato);
    }
}
