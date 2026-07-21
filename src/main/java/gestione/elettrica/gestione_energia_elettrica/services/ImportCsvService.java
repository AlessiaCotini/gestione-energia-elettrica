package gestione.elettrica.gestione_energia_elettrica.services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import gestione.elettrica.gestione_energia_elettrica.entities.Comune;
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
import java.util.Map;

@Service
public class ImportCsvService {
    private final ProvinciaRepository provinciaRepository;
    private final ComuneRepository comuneRepository;

    private static final Map<String, String> NOMI_PROVINCE = Map.ofEntries(
            Map.entry("Ascoli Piceno", "Ascoli-Piceno"),
            Map.entry("La Spezia", "La-Spezia"),
            Map.entry("Forlì-Cesena", "Forli-Cesena"),
            Map.entry("Pesaro e Urbino", "Pesaro-Urbino"),
            Map.entry("Reggio nell'Emilia", "Reggio-Emilia"),
            Map.entry("Reggio Calabria", "Reggio-Calabria"),
            Map.entry("Verbano-Cusio-Ossola", "Verbania"),
            Map.entry("Valle d'Aosta/Vallée d'Aoste", "Aosta"),
            Map.entry("Monza e della Brianza", "Monza-Brianza"),
            Map.entry("Bolzano/Bozen", "Bolzano"),
            Map.entry("Vibo Valentia", "Vibo-Valentia")


    );

    public ImportCsvService(ProvinciaRepository provinciaRepository, ComuneRepository comuneRepository) {
        this.provinciaRepository = provinciaRepository;
        this.comuneRepository = comuneRepository;
    }

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

    public void importaComuni() throws IOException {
        try (
                Reader reader = new InputStreamReader(
                        new ClassPathResource("CSV/comuni-italiani.csv").getInputStream()
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
                String nomeProvincia = NOMI_PROVINCE.getOrDefault(riga[3], riga[3]);
                if (nomeProvincia.equals("Sud Sardegna")) {
                    switch (riga[2]) {
                        case "Carbonia":
                        case "Iglesias":
                        case "Sant'Antioco":
                        case "Calasetta":
                            nomeProvincia = "Carbonia Iglesias";
                            break;
                        default:
                            nomeProvincia = "Medio Campidano";
                    }
                }
                String finalNomeProvincia = nomeProvincia;
                Provincia provincia = provinciaRepository.findByNome(nomeProvincia).orElseThrow(() -> new RuntimeException("Provincia non trovata" + finalNomeProvincia));

                Comune comune = new Comune(riga[2], riga[1], riga[0], provincia);
                comuneRepository.save(comune);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
