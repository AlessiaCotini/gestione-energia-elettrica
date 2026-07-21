package gestione.elettrica.gestione_energia_elettrica.controllers;

import gestione.elettrica.gestione_energia_elettrica.entities.StatoFattura;
import gestione.elettrica.gestione_energia_elettrica.payloads.StatoFatturaDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.StatoFatturaRespDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.UpdateStatoFatturaDTO;
import gestione.elettrica.gestione_energia_elettrica.services.StatoFatturaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stati-fattura")
public class StatoFatturaController {

    private final StatoFatturaService statoFatturaService;

    public StatoFatturaController(StatoFatturaService statoFatturaService) {
        this.statoFatturaService = statoFatturaService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_STATO_FATTURA')")
    public List<StatoFattura> getAll() {
        return statoFatturaService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_STATO_FATTURA')")
    public StatoFattura getById(@PathVariable UUID id) {
        return statoFatturaService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_STATO_FATTURA')")
    public StatoFatturaRespDTO save(@RequestBody @Validated StatoFatturaDTO body) {

        StatoFattura stato = new StatoFattura();
        stato.setNome(body.nome());

        StatoFattura saved = statoFatturaService.save(stato);

        return new StatoFatturaRespDTO(saved.getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_STATO_FATTURA')")
    public StatoFattura update(
            @PathVariable UUID id,
            @RequestBody @Validated UpdateStatoFatturaDTO body) {

        StatoFattura stato = new StatoFattura();
        stato.setNome(body.nome());

        return statoFatturaService.update(id, stato);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_STATO_FATTURA')")
    public void delete(@PathVariable UUID id) {
        statoFatturaService.delete(id);
    }

}
