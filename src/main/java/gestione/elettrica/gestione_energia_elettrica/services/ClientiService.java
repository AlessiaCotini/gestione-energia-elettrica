package gestione.elettrica.gestione_energia_elettrica.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gestione.elettrica.gestione_energia_elettrica.entities.Cliente;
import gestione.elettrica.gestione_energia_elettrica.entities.Indirizzo;
import gestione.elettrica.gestione_energia_elettrica.gestioneerrori.BadRequestException;
import gestione.elettrica.gestione_energia_elettrica.gestioneerrori.NotFoundException;
import gestione.elettrica.gestione_energia_elettrica.payloads.ClientiDTO;
import gestione.elettrica.gestione_energia_elettrica.repositories.ClientiRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.IndirizzoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class ClientiService {
    private ClientiRepository clientiRepository;
    private IndirizzoService indirizzoService;
    private final Cloudinary cloudinary;


    public ClientiService(ClientiRepository clientiRepository, IndirizzoService indirizzoService, Cloudinary cloudinary) {
        this.clientiRepository = clientiRepository;
        this.indirizzoService = indirizzoService;
        this.cloudinary = cloudinary;
    }

    public Cliente save(ClientiDTO payload) {
        if (this.clientiRepository.existsByPIva(payload.pIva())) {
            throw new BadRequestException("La partita IVA " + payload.pIva() + " risulta già registrata!");
        }
        if (this.clientiRepository.existsByEmail(payload.email())) {
            throw new BadRequestException("La email " + payload.email() + " risulta già registrata!");
        }
        if (this.clientiRepository.existsByPec(payload.pec())) {
            throw new BadRequestException("La pec " + payload.pec() + " risulta già registrata!");
        }
        if (this.clientiRepository.existsByTelefono(payload.telefono())) {
            throw new BadRequestException("Il telefono " + payload.telefono() + " risulta già registrato!");
        }

        Cliente cliente = new Cliente(
                payload.tipoCliente(),
                payload.ragioneSociale(),
                payload.pIva(),
                payload.email(),
                null,
                payload.fatturatoAnnuale(),
                payload.pec(),
                payload.telefono(),
                payload.emailContatto(),
                payload.nomeContatto(),
                payload.cognomeContatto(),
                payload.telefonoContatto(),
                indirizzoService.findById(payload.sedeOperativa()),
                indirizzoService.findById(payload.sedeLegale())
        );

        return clientiRepository.save(cliente);
    }


    public Page<Cliente> findAll(int page, int size, String sortBy) {
        if (size > 20) size = 20;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clientiRepository.findAll(pageable);
    }


    public Cliente findById(UUID id) {
        return clientiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente con ID " + id + " non trovato!"));
    }

    public Page<Cliente> filterByFatturato(double fatturato, int page, int size, String sortBy) {
        return clientiRepository.findByFatturatoAnnuale(fatturato, PageRequest.of(page, size, Sort.by(sortBy)));
    }

    public Page<Cliente> filterByDataInserimento(LocalDate data, int page, int size, String sortBy) {
        return clientiRepository.findByDataInserimento(data, PageRequest.of(page, size, Sort.by(sortBy)));
    }

    public Page<Cliente> filterByDataUltimoContatto(LocalDate data, int page, int size, String sortBy) {
        return clientiRepository.findByDataUltimoContatto(data, PageRequest.of(page, size, Sort.by(sortBy)));
    }

    public Page<Cliente> filterByRagioneSociale(String ragioneSociale, int page, int size, String sortBy) {
        return clientiRepository.findByRagioneSocialeContaining(ragioneSociale, PageRequest.of(page, size, Sort.by(sortBy)));
    }

    public Cliente uploadAvatar(UUID clienteId, MultipartFile file) throws IOException {
        Cliente cliente = clientiRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String url = uploadResult.get("secure_url").toString();
        String publicId = uploadResult.get("public_id").toString();

        cliente.setLogoAziendale(url);

        return clientiRepository.save(cliente);
    }


}
