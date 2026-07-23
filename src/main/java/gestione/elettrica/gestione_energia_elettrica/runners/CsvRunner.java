package gestione.elettrica.gestione_energia_elettrica.runners;

import gestione.elettrica.gestione_energia_elettrica.entities.Autorizzazione;
import gestione.elettrica.gestione_energia_elettrica.entities.Comune;
import gestione.elettrica.gestione_energia_elettrica.entities.Indirizzo;
import gestione.elettrica.gestione_energia_elettrica.entities.Role;
import gestione.elettrica.gestione_energia_elettrica.repositories.*;
import gestione.elettrica.gestione_energia_elettrica.services.ImportCsvService;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CsvRunner implements CommandLineRunner {


    private final ImportCsvService importCsvService;
    private final ProvinciaRepository provinciaRepository;
    private final ComuneRepository comuneRepository;

    private final RoleRepository roleRepository;
    private final AutorizzazioneRepository autorizzazioneRepository;
    private final IndirizzoRepository indirizzoRepository;

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
        initIndirizzi();
    }

    @PrePersist
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
        Autorizzazione clientiPatchLogo = createAutorizzazioneIfNotFound("CLIENTI_PATCH_LOGO");


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
                gestisciUtenti, assegnaRuoli, clientiCreate, clientiUpdate, clientiPatchLogo
        ));
        roleRepository.save(adminRole);
    }

    private void initIndirizzi() {
        System.out.println("Controllo se inserire indirizzi...");
        long countIndirizzi = indirizzoRepository.count();
        System.out.println("Indirizzi attuali nel DB: " + countIndirizzi);

        if (countIndirizzi == 0) {
            List<Comune> comuni = comuneRepository.findAll();
            System.out.println("Comuni trovati nel DB: " + comuni.size());

            if (comuni.isEmpty()) {
                System.err.println("IMPOSSIBILE CREARE INDIRIZZI: La tabella dei comuni è VUOTA!");
                return;
            }

            Random random = new Random();
            List<String> vie = List.of("Via Roma", "Corso Vittorio Emanuele", "Via Milano", "Via Garibaldi", "Via Dante Alighieri");

            for (int i = 0; i < 5; i++) {
                Comune comuneCasuale = comuni.get(random.nextInt(comuni.size()));

                Indirizzo indirizzo = new Indirizzo();
                indirizzo.setVia(vie.get(i));
                indirizzo.setCivico(String.valueOf((i + 1) * 12));
                indirizzo.setLocalita("Centro");
                indirizzo.setCap(15478);
                indirizzo.setComune(comuneCasuale);

                indirizzoRepository.save(indirizzo);
            }

            System.out.println("Indirizzi salvati correttamente!");
        }
    }

    private Autorizzazione createAutorizzazioneIfNotFound(String nome) {
        return autorizzazioneRepository.findByNome(nome)
                .orElseGet(() -> autorizzazioneRepository.save(new Autorizzazione(nome)));
    }
}
