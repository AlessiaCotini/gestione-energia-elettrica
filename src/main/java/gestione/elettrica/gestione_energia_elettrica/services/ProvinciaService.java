package gestione.elettrica.gestione_energia_elettrica.services;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.Provincia;
import gestione.elettrica.gestione_energia_elettrica.repositories.ProvinciaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProvinciaService {
    private ProvinciaRepository provinciaRepository;

    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public Provincia findById(UUID id) {
        return provinciaRepository.findById(id).orElseThrow(() -> new NotFound("Provincia con id " + id + " non trovata"));
    }

    public Page<Provincia> findAll(int page, int size, String sortBy) {
        if (size > 20) size = 20;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return provinciaRepository.findAll(pageable);
    }
}
