package gestione.elettrica.gestione_energia_elettrica.runners;

import gestione.elettrica.gestione_energia_elettrica.entities.Autorizzazione;
import gestione.elettrica.gestione_energia_elettrica.entities.Role;
import gestione.elettrica.gestione_energia_elettrica.repositories.AutorizzazioneRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.ComuneRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.ProvinciaRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.RoleRepository;
import gestione.elettrica.gestione_energia_elettrica.services.ImportCsvService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CsvRunner implements CommandLineRunner {

    private final ImportCsvService importCsvService;
    private final ProvinciaRepository provinciaRepository;
    private final ComuneRepository comuneRepository;

    private final RoleRepository roleRepository;
    private final AutorizzazioneRepository autorizzazioneRepository;

    @Override
    @Transactional
    public void run(String... args) throws IOException {

        if (provinciaRepository.count() == 0) {
            importCsvService.importaProvince();
        }
        if (comuneRepository.count() == 0) {
            importCsvService.importaComuni();
        }

        initRuoliEAutorizzazioni();
    }

    private void initRuoliEAutorizzazioni() {

        if (roleRepository.count() > 0) {
            return;
        }


        Autorizzazione readFattura = createAutorizzazioneIfNotFound("READ_FATTURA");
        Autorizzazione createFattura = createAutorizzazioneIfNotFound("CREATE_FATTURA");
        Autorizzazione updateFattura = createAutorizzazioneIfNotFound("UPDATE_FATTURA");
        Autorizzazione deleteFattura = createAutorizzazioneIfNotFound("DELETE_FATTURA");

        Autorizzazione readStatoFattura = createAutorizzazioneIfNotFound("READ_STATO_FATTURA");
        Autorizzazione createStatoFattura = createAutorizzazioneIfNotFound("CREATE_STATO_FATTURA");
        Autorizzazione updateStatoFattura = createAutorizzazioneIfNotFound("UPDATE_STATO_FATTURA");
        Autorizzazione deleteStatoFattura = createAutorizzazioneIfNotFound("DELETE_STATO_FATTURA");

        Autorizzazione gestisciUtenti = createAutorizzazioneIfNotFound("GESTISCI_UTENTI");
        Autorizzazione assegnaRuoli = createAutorizzazioneIfNotFound("ASSEGNA_RUOLI");

        Autorizzazione clientiCreate = createAutorizzazioneIfNotFound("CLIENTI_CREATE");
        Autorizzazione clientiUpdate = createAutorizzazioneIfNotFound("CLIENTI_UPDATE");


        Role userRole = new Role("USER");
        userRole.setAutorizzazioni(Set.of(
                readFattura,
                readStatoFattura
        ));
        roleRepository.save(userRole);

        Role adminRole = new Role("ADMIN");
        adminRole.setAutorizzazioni(Set.of(
                readFattura, createFattura, updateFattura, deleteFattura,
                readStatoFattura, createStatoFattura, updateStatoFattura, deleteStatoFattura,
                gestisciUtenti, assegnaRuoli, clientiCreate, clientiUpdate
        ));
        roleRepository.save(adminRole);
    }

    private Autorizzazione createAutorizzazioneIfNotFound(String nome) {
        return autorizzazioneRepository.findByNome(nome)
                .orElseGet(() -> autorizzazioneRepository.save(new Autorizzazione(nome)));
    }
}
