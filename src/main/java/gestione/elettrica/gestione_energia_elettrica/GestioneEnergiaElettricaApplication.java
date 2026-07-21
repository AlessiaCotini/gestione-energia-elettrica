package gestione.elettrica.gestione_energia_elettrica;

import gestione.elettrica.gestione_energia_elettrica.repositories.ComuneRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.ProvinciaRepository;
import gestione.elettrica.gestione_energia_elettrica.services.ImportCsvService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GestioneEnergiaElettricaApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(GestioneEnergiaElettricaApplication.class, args);
    }

}
