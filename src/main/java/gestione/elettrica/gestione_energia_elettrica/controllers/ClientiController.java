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
            @RequestParam(required = false) String ragioneSociale,
            @RequestParam(required = false) Double fatturato,
            @RequestParam(required = false) LocalDate dataInserimento,
            @RequestParam(required = false) LocalDate dataUltimoContatto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy
    ) {
        return clientiService.findAllFiltered(ragioneSociale, fatturato, dataInserimento, dataUltimoContatto, page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable UUID id) {
        return clientiService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente createCliente(@RequestBody @Validated ClientiDTO payload) {
        return clientiService.save(payload);
    }

    @PatchMapping("/{id}/logo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente uploadLogo(@PathVariable UUID id, @RequestParam("logo") MultipartFile file) throws IOException {
        return clientiService.uploadAvatar(id, file);
    }

}
