package gestione.elettrica.gestione_energia_elettrica.services;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.Comune;
import gestione.elettrica.gestione_energia_elettrica.repositories.ComuneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ComuneService {
    private ComuneRepository comuneRepository;

    public ComuneService(ComuneRepository comuneRepository) {
        this.comuneRepository = comuneRepository;
    }

    public Comune findById(UUID id) {
        return comuneRepository.findById(id).orElseThrow(() -> new NotFound("Comune con id " + id + " non trovato"));
    }

    public Page<Comune> findAll(int page, int size, String sortBy) {
        if (size > 20) size = 20;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return comuneRepository.findAll(pageable);
    }
}
