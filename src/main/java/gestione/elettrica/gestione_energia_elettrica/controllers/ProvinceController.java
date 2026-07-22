package gestione.elettrica.gestione_energia_elettrica.controllers;

import gestione.elettrica.gestione_energia_elettrica.entities.Provincia;
import gestione.elettrica.gestione_energia_elettrica.services.ProvinciaService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/province")
public class ProvinceController {
    private final ProvinciaService provinciaService;

    public ProvinceController(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Provincia> getProvince(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return provinciaService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Provincia findById(@PathVariable UUID id) {
        return provinciaService.findById(id);
    }
}
