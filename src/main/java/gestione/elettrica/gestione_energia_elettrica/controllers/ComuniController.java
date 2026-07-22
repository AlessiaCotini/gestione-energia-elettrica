package gestione.elettrica.gestione_energia_elettrica.controllers;

import gestione.elettrica.gestione_energia_elettrica.entities.Comune;
import gestione.elettrica.gestione_energia_elettrica.services.ComuneService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comuni")
public class ComuniController {
    private final ComuneService comuneService;

    public ComuniController(ComuneService comuneService) {
        this.comuneService = comuneService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Comune> getComuni(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return comuneService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Comune findById(@PathVariable UUID id) {
        return comuneService.findById(id);
    }
}
