package gestione.elettrica.gestione_energia_elettrica.services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import gestione.elettrica.gestione_energia_elettrica.entities.Provincia;
import gestione.elettrica.gestione_energia_elettrica.repositories.ComuneRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportCsvService {
    private final ProvinciaRepository provinciaRepository;
    private final ComuneRepository comuneRepository;

    public void importaProvince() throws IOException {
        try (
                Reader reader = new InputStreamReader(
                        new ClassPathResource("CSV/province-italiane.csv").getInputStream()
                );

                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withCSVParser(new CSVParserBuilder()
                                .withSeparator(';')
                                .build())
                        .build()
        ) {
            csvReader.readNext();

            String[] riga;

            while ((riga = csvReader.readNext()) != null) {
                Provincia provincia = new Provincia(riga[1], riga[0], riga[2]);
                provinciaRepository.save(provincia);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
