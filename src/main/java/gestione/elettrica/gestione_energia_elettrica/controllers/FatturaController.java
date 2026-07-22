package gestione.elettrica.gestione_energia_elettrica.controllers;

import gestione.elettrica.gestione_energia_elettrica.entities.Cliente;
import gestione.elettrica.gestione_energia_elettrica.entities.Fattura;
import gestione.elettrica.gestione_energia_elettrica.entities.StatoFattura;
import gestione.elettrica.gestione_energia_elettrica.payloads.FatturaDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.FatturaRespDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.UpdateFatturaDTO;
import gestione.elettrica.gestione_energia_elettrica.services.ClientiService;
import gestione.elettrica.gestione_energia_elettrica.services.FatturaService;
import gestione.elettrica.gestione_energia_elettrica.services.StatoFatturaService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    private final FatturaService fatturaService;
    private final ClientiService clienteService;
    private final StatoFatturaService statoFatturaService;

    public FatturaController(FatturaService fatturaService, ClientiService clienteService, StatoFatturaService statoFatturaService) {
        this.fatturaService = fatturaService;
        this.clienteService = clienteService;
        this.statoFatturaService = statoFatturaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_FATTURA','ADMIN')")
    public Page<Fattura> getAllFatture(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "data") String orderBy
    ) {
        return fatturaService.findAll(page, size, orderBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_FATTURA','ADMIN')")
    public Fattura getFatturaById(@PathVariable UUID id) {
        return fatturaService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_FATTURA','ADMIN')")
    public FatturaRespDTO save(@RequestBody @Validated FatturaDTO body) {

        Cliente cliente = clienteService.findById(body.clienteId());
        StatoFattura stato = statoFatturaService.findById(body.statoFatturaId());

        Fattura fattura = new Fattura(
                body.data(),
                body.importo(),
                body.numero(),
                cliente,
                stato
        );

        Fattura saved = fatturaService.save(fattura);

        return new FatturaRespDTO(saved.getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_FATTURA','ADMIN')")
    public Fattura update(
            @PathVariable UUID id,
            @RequestBody @Validated UpdateFatturaDTO body) {

        Cliente cliente = clienteService.findById(body.clienteId());
        StatoFattura stato = statoFatturaService.findById(body.statoFatturaId());

        Fattura fattura = new Fattura(
                body.data(),
                body.importo(),
                body.numero(),
                cliente,
                stato
        );

        return fatturaService.update(id, fattura);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_FATTURA','ADMIN')")
    public List<Fattura> search(

            @RequestParam(required = false)
            UUID clienteId,
            @RequestParam(required = false)
            UUID statoId,
            @RequestParam(required = false)
            LocalDate data,
            @RequestParam(required = false)
            LocalDate start,
            @RequestParam(required = false)
            LocalDate end,
            @RequestParam(required = false)
            BigDecimal min,
            @RequestParam(required = false)
            BigDecimal max,
            @RequestParam(required = false)
            Integer anno
    ) {

        return fatturaService.search(
                clienteId,
                statoId,
                data,
                start,
                end,
                min,
                max,
                anno
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_FATTURA','ADMIN')")
    public void delete(@PathVariable UUID id) {
        fatturaService.delete(id);
    }
}