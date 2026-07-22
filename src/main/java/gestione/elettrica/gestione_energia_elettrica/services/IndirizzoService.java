package gestione.elettrica.gestione_energia_elettrica.services;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.Comune;
import gestione.elettrica.gestione_energia_elettrica.entities.Indirizzo;
import gestione.elettrica.gestione_energia_elettrica.payloads.IndirizzoDTO;
import gestione.elettrica.gestione_energia_elettrica.repositories.ComuneRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.IndirizzoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IndirizzoService {
    private IndirizzoRepository indirizzoRepository;
    private ComuneRepository comuneRepository;

    public IndirizzoService(IndirizzoRepository indirizzoRepository, ComuneRepository comuneRepository) {
        this.indirizzoRepository = indirizzoRepository;
        this.comuneRepository = comuneRepository;
    }

    public Indirizzo save(IndirizzoDTO body) {
        Comune comune = comuneRepository.findById(body.comuneId()).orElseThrow(() -> new NotFound("Comune con id \" + id + \" non trovato"));
        Indirizzo newIndirizzo = new Indirizzo(body.via(), body.civico(), body.localita(), body.cap(), comune);

        return indirizzoRepository.save(newIndirizzo);
    }

    public Indirizzo findById(UUID id) {
        return indirizzoRepository.findById(id).orElseThrow(() -> new NotFound("Indirizzo con id " + id + " non trovato"));
    }

    public List<Indirizzo> findAll() {
        return indirizzoRepository.findAll();
    }

    public void findByIdAndDelete(UUID id) {
        Indirizzo indirizzo = this.findById(id);
        indirizzoRepository.delete(indirizzo);
    }

    public Indirizzo findByIdAndUpdate(UUID id, IndirizzoDTO body) {
        Indirizzo indirizzo = this.findById(id);
        Comune comune = comuneRepository.findById(body.comuneId()).orElseThrow(() -> new NotFound("Comune con id \" + id + \" non trovato"));

        indirizzo.setVia(body.via());
        indirizzo.setCivico(body.civico());
        indirizzo.setLocalita(body.localita());
        indirizzo.setCap(body.cap());
        indirizzo.setComune(comune);

        return indirizzoRepository.save(indirizzo);
    }
}
