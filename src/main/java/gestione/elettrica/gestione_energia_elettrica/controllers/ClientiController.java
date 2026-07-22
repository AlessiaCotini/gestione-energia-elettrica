package gestione.elettrica.gestione_energia_elettrica.controllers;


import gestione.elettrica.gestione_energia_elettrica.entities.Cliente;
import gestione.elettrica.gestione_energia_elettrica.payloads.ClientiDTO;
import gestione.elettrica.gestione_energia_elettrica.services.ClientiService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/clienti")
public class ClientiController {

    private final ClientiService clientiService;

    public ClientiController(ClientiService clientiService) {
        this.clientiService = clientiService;
    }

    @GetMapping
    public Page<Cliente> getAllClienti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy
    ) {
        return clientiService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable UUID id) {
        return clientiService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CLIENTI_CREATE')")
    public Cliente createCliente(@RequestBody @Validated ClientiDTO payload) {
        return clientiService.save(payload);
    }

    @PatchMapping("/{id}/logo")
    @PreAuthorize("hasAuthority('CLIENTI_UPDATE')")
    public Cliente uploadLogo(@PathVariable UUID id, @RequestParam("logo") MultipartFile file) throws IOException {
        return clientiService.uploadAvatar(id, file);
    }

    @GetMapping("/fatturato")
    public Page<Cliente> filterByFatturato(
            @RequestParam double fatturato,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy
    ) {
        return clientiService.filterByFatturato(fatturato, page, size, sortBy);
    }

    @GetMapping("/data-inserimento")
    public Page<Cliente> filterByDataInserimento(
            @RequestParam LocalDate dataInserimento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy
    ) {
        return clientiService.filterByDataInserimento(dataInserimento, page, size, sortBy);
    }

    @GetMapping("/data-ultimo-contatto")
    public Page<Cliente> filterByDaaUltimoContatto(
            @RequestParam LocalDate dataUltimoContatto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy
    ) {
        return clientiService.filterByDataUltimoContatto(dataUltimoContatto, page, size, sortBy);
    }

    @GetMapping("/ragione-sociale")
    public Page<Cliente> filterByRagioneSociale(
            @RequestParam String ragioneSociale,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy
    ) {
        return clientiService.filterByRagioneSociale(ragioneSociale, page, size, sortBy);
    }

}
