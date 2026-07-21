package gestione.elettrica.gestione_energia_elettrica.runners;

import gestione.elettrica.gestione_energia_elettrica.repositories.ComuneRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.ProvinciaRepository;
import gestione.elettrica.gestione_energia_elettrica.services.ImportCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CsvRunner implements CommandLineRunner {
    private final ImportCsvService importCsvService;
    private final ProvinciaRepository provinciaRepository;
    private final ComuneRepository comuneRepository;

    @Override
    public void run(String... args) throws IOException {

        if (provinciaRepository.count() == 0) {
            importCsvService.importaProvince();
        }
        if (comuneRepository.count() == 0) {
            importCsvService.importaComuni();
        }
    }
}
