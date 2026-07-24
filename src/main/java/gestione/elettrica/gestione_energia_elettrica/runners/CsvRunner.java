package gestione.elettrica.gestione_energia_elettrica.runners;

import gestione.elettrica.gestione_energia_elettrica.entities.*;
import gestione.elettrica.gestione_energia_elettrica.enums.TipoCliente;
import gestione.elettrica.gestione_energia_elettrica.repositories.*;
import gestione.elettrica.gestione_energia_elettrica.services.ImportCsvService;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private final StatoFatturaRepository statoFatturaRepository;
    private final ClientiRepository clientiRepository;
    private final FatturaRepository fatturaRepository;
    private final UserRepository userRepository;

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
        initStatiFattura();
        initClienti();
        initFatture();
        initUsers();
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
            List<String> vie = List.of("Via Roma", "Corso Vittorio Emanuele", "Via Milano", "Via Garibaldi",
                    "Via Dante Alighieri");

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


    private void initStatiFattura() {

        if (statoFatturaRepository.count() > 0) {
            return;
        }

        statoFatturaRepository.saveAll(
                List.of(
                        new StatoFattura("EMESSA"),
                        new StatoFattura("IN_ATTESA_DI_PAGAMENTO"),
                        new StatoFattura("PAGATA"),
                        new StatoFattura("SCADUTA"),
                        new StatoFattura("ANNULLATA")
                )
        );

        System.out.println("Stati fattura creati correttamente!");
    }

    private void initClienti() {

        if (clientiRepository.count() > 0) {
            return;
        }

        List<Indirizzo> indirizzi = indirizzoRepository.findAll();

        if (indirizzi.size() < 2) {
            System.out.println(
                    "Impossibile creare clienti: servono almeno 2 indirizzi."
            );
            return;
        }

        List<Cliente> clienti = List.of(

                new Cliente(
                        TipoCliente.SRL,
                        "Tech Solutions SRL",
                        "12345678901",
                        "info@techsolutions.it",
                        null,
                        250000.00,
                        "techsolutions@pec.it",
                        "0911234567",
                        "marco.rossi@techsolutions.it",
                        "Marco",
                        "Rossi",
                        "3331234567",
                        indirizzi.get(0),
                        indirizzi.get(1)
                ),

                new Cliente(
                        TipoCliente.SPA,
                        "Energia Italia SPA",
                        "23456789012",
                        "info@energiaitalia.it",
                        null,
                        850000.00,
                        "energiaitalia@pec.it",
                        "0912345678",
                        "luca.bianchi@energiaitalia.it",
                        "Luca",
                        "Bianchi",
                        "3332345678",
                        indirizzi.get(1),
                        indirizzi.get(2)
                ),

                new Cliente(
                        TipoCliente.SAS,
                        "Costruzioni Mediterranee SAS",
                        "34567890123",
                        "info@costruzionimediterranee.it",
                        null,
                        420000.00,
                        "costruzionimediterranee@pec.it",
                        "0921234567",
                        "anna.romano@costruzionimediterranee.it",
                        "Anna",
                        "Romano",
                        "3333456789",
                        indirizzi.get(2),
                        indirizzi.get(3)
                ),

                new Cliente(
                        TipoCliente.SRL,
                        "Sicilia Digital SRL",
                        "45678901234",
                        "info@siciliadigital.it",
                        null,
                        180000.00,
                        "siciliadigital@pec.it",
                        "0922234567",
                        "giuseppe.esposito@siciliadigital.it",
                        "Giuseppe",
                        "Esposito",
                        "3334567890",
                        indirizzi.get(3),
                        indirizzi.get(4)
                ),

                new Cliente(
                        TipoCliente.PA,
                        "Comune di Palermo",
                        "56789012345",
                        "info@comunepalermo.it",
                        null,
                        1500000.00,
                        "comunepalermo@pec.it",
                        "0915678901",
                        "maria.conti@comunepalermo.it",
                        "Maria",
                        "Conti",
                        "3335678901",
                        indirizzi.get(4),
                        indirizzi.get(0)
                ),

                new Cliente(
                        TipoCliente.SRL,
                        "Green Energy SRL",
                        "67890123456",
                        "info@greenenergy.it",
                        null,
                        320000.00,
                        "greenenergy@pec.it",
                        "0916789012",
                        "alessandro.ferrari@greenenergy.it",
                        "Alessandro",
                        "Ferrari",
                        "3336789012",
                        indirizzi.get(0),
                        indirizzi.get(2)
                ),

                new Cliente(
                        TipoCliente.SPA,
                        "Mediterranea Logistics SPA",
                        "78901234567",
                        "info@mediterranealogistics.it",
                        null,
                        1200000.00,
                        "mediterranealogistics@pec.it",
                        "0917890123",
                        "francesca.gallo@mediterranealogistics.it",
                        "Francesca",
                        "Gallo",
                        "3337890123",
                        indirizzi.get(1),
                        indirizzi.get(3)
                ),

                new Cliente(
                        TipoCliente.SAS,
                        "Rossi & Partners SAS",
                        "89012345678",
                        "info@rossipartners.it",
                        null,
                        275000.00,
                        "rossipartners@pec.it",
                        "0918901234",
                        "paolo.rossi@rossipartners.it",
                        "Paolo",
                        "Rossi",
                        "3338901234",
                        indirizzi.get(2),
                        indirizzi.get(4)
                ),

                new Cliente(
                        TipoCliente.SRL,
                        "Innovazione Sicilia SRL",
                        "90123456789",
                        "info@innovazionesicilia.it",
                        null,
                        390000.00,
                        "innovazionesicilia@pec.it",
                        "0919012345",
                        "elena.mancini@innovazionesicilia.it",
                        "Elena",
                        "Mancini",
                        "3339012345",
                        indirizzi.get(3),
                        indirizzi.get(0)
                ),

                new Cliente(
                        TipoCliente.PA,
                        "Azienda Servizi Municipali",
                        "01234567890",
                        "info@servizimunicipali.it",
                        null,
                        950000.00,
                        "servizimunicipali@pec.it",
                        "0910123456",
                        "stefano.moretti@servizimunicipali.it",
                        "Stefano",
                        "Moretti",
                        "3330123456",
                        indirizzi.get(4),
                        indirizzi.get(1)
                )
        );

        clientiRepository.saveAll(clienti);

        System.out.println("10 clienti creati correttamente!");
    }

    private void initFatture() {

        if (fatturaRepository.count() > 0) {
            return;
        }

        List<Cliente> clienti = clientiRepository.findAll();
        List<StatoFattura> stati = statoFatturaRepository.findAll();

        if (clienti.size() < 10) {
            System.out.println(
                    "Impossibile creare fatture: servono almeno 10 clienti."
            );
            return;
        }

        if (stati.size() < 5) {
            System.out.println(
                    "Impossibile creare fatture: servono almeno 5 stati fattura."
            );
            return;
        }

        List<Fattura> fatture = List.of(

                new Fattura(
                        LocalDate.of(2026, 1, 15),
                        new BigDecimal("1250.50"),
                        "FT-2026-001",
                        clienti.get(0),
                        stati.get(0)
                ),

                new Fattura(
                        LocalDate.of(2026, 1, 28),
                        new BigDecimal("3450.00"),
                        "FT-2026-002",
                        clienti.get(1),
                        stati.get(1)
                ),

                new Fattura(
                        LocalDate.of(2026, 2, 10),
                        new BigDecimal("875.75"),
                        "FT-2026-003",
                        clienti.get(2),
                        stati.get(2)
                ),

                new Fattura(
                        LocalDate.of(2026, 2, 25),
                        new BigDecimal("5200.00"),
                        "FT-2026-004",
                        clienti.get(3),
                        stati.get(3)
                ),

                new Fattura(
                        LocalDate.of(2026, 3, 5),
                        new BigDecimal("1890.30"),
                        "FT-2026-005",
                        clienti.get(4),
                        stati.get(4)
                ),

                new Fattura(
                        LocalDate.of(2026, 3, 20),
                        new BigDecimal("760.00"),
                        "FT-2026-006",
                        clienti.get(5),
                        stati.get(0)
                ),

                new Fattura(
                        LocalDate.of(2026, 4, 12),
                        new BigDecimal("4300.99"),
                        "FT-2026-007",
                        clienti.get(6),
                        stati.get(1)
                ),

                new Fattura(
                        LocalDate.of(2026, 5, 8),
                        new BigDecimal("2150.00"),
                        "FT-2026-008",
                        clienti.get(7),
                        stati.get(2)
                ),

                new Fattura(
                        LocalDate.of(2026, 6, 15),
                        new BigDecimal("980.45"),
                        "FT-2026-009",
                        clienti.get(8),
                        stati.get(3)
                ),

                new Fattura(
                        LocalDate.of(2026, 7, 1),
                        new BigDecimal("6750.00"),
                        "FT-2026-010",
                        clienti.get(9),
                        stati.get(4)
                )
        );

        fatturaRepository.saveAll(fatture);

        System.out.println("10 fatture create correttamente!");
    }

    private void initUsers() {

        if (userRepository.count() > 0) {
            return;
        }

        Role userRole = roleRepository.findByNomeRuolo("USER")
                .orElseThrow(() -> new RuntimeException(
                        "Ruolo USER non trovato"
                ));

        Role adminRole = roleRepository.findByNomeRuolo("ADMIN")
                .orElseThrow(() -> new RuntimeException(
                        "Ruolo ADMIN non trovato"
                ));

        PasswordEncoder passwordEncoder =
                new BCryptPasswordEncoder();

        User user1 = new User(
                "mario.rossi",
                "mario.rossi@email.it",
                passwordEncoder.encode("1234"),
                "Mario",
                "Rossi"
        );

        user1.setRuoli(Set.of(userRole));


        User user2 = new User(
                "anna.bianchi",
                "anna.bianchi@email.it",
                passwordEncoder.encode("1234"),
                "Anna",
                "Bianchi"
        );

        user2.setRuoli(Set.of(userRole));


        User user3 = new User(
                "luca.verdi",
                "luca.verdi@email.it",
                passwordEncoder.encode("1234"),
                "Luca",
                "Verdi"
        );

        user3.setRuoli(Set.of(userRole));


        User user4 = new User(
                "giulia.romano",
                "giulia.romano@email.it",
                passwordEncoder.encode("1234"),
                "Giulia",
                "Romano"
        );

        user4.setRuoli(Set.of(userRole));


        User admin = new User(
                "admin",
                "admin@email.it",
                passwordEncoder.encode("1234"),
                "Admin",
                "System"
        );

        admin.setRuoli(Set.of(adminRole));


        userRepository.saveAll(
                List.of(
                        user1,
                        user2,
                        user3,
                        user4,
                        admin
                )
        );

        System.out.println("4 utenti USER e 1 utente ADMIN creati correttamente!");
    }
}
