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
    @PreAuthorize("hasAuthority('READ_FATTURA')")
    public Page<Fattura> getAllFatture(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "data") String orderBy
    ) {
        return fatturaService.findAll(page, size, orderBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_FATTURA')")
    public Fattura getFatturaById(@PathVariable UUID id) {
        return fatturaService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_FATTURA')")
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
    @PreAuthorize("hasAuthority('UPDATE_FATTURA')")
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

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasAuthority('READ_FATTURA')")
    public List<Fattura> findByCliente(@PathVariable UUID clienteId) {

        Cliente cliente = clienteService.findById(clienteId);

        return fatturaService.findByCliente(cliente);
    }

    @GetMapping("/stato/{statoId}")
    @PreAuthorize("hasAuthority('READ_FATTURA')")
    public List<Fattura> findByStato(@PathVariable UUID statoId) {

        StatoFattura stato = statoFatturaService.findById(statoId);

        return fatturaService.findByStato(stato);
    }

    @GetMapping("/data")
    @PreAuthorize("hasAuthority('READ_FATTURA')")
    public List<Fattura> findByData(@RequestParam LocalDate data) {

        return fatturaService.findByData(data);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasAuthority('READ_FATTURA')")
    public List<Fattura> findByPeriodo(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return fatturaService.findByPeriodo(start, end);
    }

    @GetMapping("/importo")
    @PreAuthorize("hasAuthority('READ_FATTURA')")
    public List<Fattura> findByImporto(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {

        return fatturaService.findByImporto(min, max);
    }

    @GetMapping("/anno/{anno}")
    @PreAuthorize("hasAuthority('READ_FATTURA')")
    public List<Fattura> findByAnno(@PathVariable int anno) {

        return fatturaService.findByAnno(anno);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_FATTURA')")
    public void delete(@PathVariable UUID id) {
        fatturaService.delete(id);
    }
}