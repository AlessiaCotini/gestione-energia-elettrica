package gestione.elettrica.gestione_energia_elettrica.services;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.Indirizzo;
import gestione.elettrica.gestione_energia_elettrica.repositories.IndirizzoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IndirizzoService {
    private IndirizzoRepository indirizzoRepository;

    public IndirizzoService(IndirizzoRepository indirizzoRepository) {
        this.indirizzoRepository = indirizzoRepository;
    }

    public Indirizzo findById(UUID id) {
        return indirizzoRepository.findById(id).orElseThrow(() -> new NotFound("Indirizzo con id " + id + " non trovato"));
    }
}
