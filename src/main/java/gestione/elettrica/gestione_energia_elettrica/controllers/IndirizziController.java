package gestione.elettrica.gestione_energia_elettrica.controllers;

import gestione.elettrica.gestione_energia_elettrica.entities.Indirizzo;
import gestione.elettrica.gestione_energia_elettrica.payloads.IndirizzoDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.IndirizzoResponseDTO;
import gestione.elettrica.gestione_energia_elettrica.services.IndirizzoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/indirizzi")
@RequiredArgsConstructor
public class IndirizziController {

    private final IndirizzoService indirizzoService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<Indirizzo> findAll() {
        return indirizzoService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Indirizzo findById(@PathVariable UUID id) {
        return indirizzoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public IndirizzoResponseDTO save(@RequestBody @Validated IndirizzoDTO body) {
        Indirizzo nuovoIndirizzo = indirizzoService.save(body);
        return new IndirizzoResponseDTO(nuovoIndirizzo.getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Indirizzo findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated IndirizzoDTO body) {
        return indirizzoService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID id) {
        indirizzoService.findByIdAndDelete(id);
    }
}
