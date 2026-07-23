package gestione.elettrica.gestione_energia_elettrica.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gestione.elettrica.gestione_energia_elettrica.eccezioni.BadRequest;
import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.Cliente;
import gestione.elettrica.gestione_energia_elettrica.payloads.ClientiDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.ClientiUpdateDTO;
import gestione.elettrica.gestione_energia_elettrica.repositories.ClientiRepository;
import gestione.elettrica.gestione_energia_elettrica.specifications.ClientiSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class ClientiService {
    private final Cloudinary cloudinary;
    private final ClientiRepository clientiRepository;
    private final IndirizzoService indirizzoService;


    public ClientiService(ClientiRepository clientiRepository, IndirizzoService indirizzoService, Cloudinary cloudinary) {
        this.clientiRepository = clientiRepository;
        this.indirizzoService = indirizzoService;
        this.cloudinary = cloudinary;
    }

    public Cliente save(ClientiDTO payload) {
        if (this.clientiRepository.existsByPartitaIva(payload.partitaIva())) {
            throw new BadRequest("La partita IVA " + payload.partitaIva() + " risulta già registrata!");
        }
        if (this.clientiRepository.existsByEmail(payload.email())) {
            throw new BadRequest("La email " + payload.email() + " risulta già registrata!");
        }
        if (this.clientiRepository.existsByPec(payload.pec())) {
            throw new BadRequest("La pec " + payload.pec() + " risulta già registrata!");
        }
        if (this.clientiRepository.existsByTelefono(payload.telefono())) {
            throw new BadRequest("Il telefono " + payload.telefono() + " risulta già registrato!");
        }

        Cliente cliente = new Cliente(
                payload.tipoCliente(),
                payload.ragioneSociale(),
                payload.partitaIva(),
                payload.email(),
                null,
                payload.fatturatoAnnuale(),
                payload.pec(),
                payload.telefono(),
                payload.emailContatto(),
                payload.nomeContatto(),
                payload.cognomeContatto(),
                payload.telefonoContatto(),
                payload.sedeOperativa() != null ? indirizzoService.findById(payload.sedeOperativa()) : null,
                payload.sedeLegale() != null ? indirizzoService.findById(payload.sedeLegale()) : null
        );

        return clientiRepository.save(cliente);
    }


    public Cliente findById(UUID id) {
        return clientiRepository.findById(id)
                .orElseThrow(() -> new NotFound("Cliente con ID " + id + " non trovato!"));
    }

    public Page<Cliente> findAllFiltered(String ragioneSociale, Double fatturato, LocalDate dataInserimento, LocalDate dataUltimoContatto, Double fatturatoMin, Double fatturatoMax, LocalDate dataInserimentoStart, LocalDate dataInserimentoEnd, LocalDate dataUltimoContattoStart, LocalDate dataUltimoContattoEnd, int page, int size, String sortBy) {
        if (size > 20) size = 20;
        if (size < 0) size = 10;
        if (page < 0) page = 0;

        Specification<Cliente> specification = Specification.where(
                        ClientiSpecifications.hasRagioneSociale(ragioneSociale))
                .and(ClientiSpecifications.hasFatturatoAnnuale(fatturato))
                .and(ClientiSpecifications.hasDataInserimento(dataInserimento))
                .and(ClientiSpecifications.hasDataUltimoContatto(dataUltimoContatto))
                .and(ClientiSpecifications.fatturatoBetween(fatturatoMin, fatturatoMax))
                .and(ClientiSpecifications.dataInserimentoBetween(dataInserimentoStart, dataInserimentoEnd))
                .and(ClientiSpecifications.dataUltimoContattoBetween(dataUltimoContattoStart, dataUltimoContattoEnd));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clientiRepository.findAll(specification, pageable);
    }

    public Cliente uploadAvatar(UUID clienteId, MultipartFile file) throws IOException {
        Cliente cliente = clientiRepository.findById(clienteId)
                .orElseThrow(() -> new NotFound("Cliente non trovato"));
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String url = uploadResult.get("secure_url").toString();
        String publicId = uploadResult.get("public_id").toString();

        cliente.setLogoAziendale(url);

        return clientiRepository.save(cliente);
    }

    public Cliente update(UUID id, ClientiUpdateDTO payload) {
        Cliente cliente = this.findById(id);

        if (payload.partitaIva() != null && !payload.partitaIva().equals(cliente.getPartitaIva())) {
            if (this.clientiRepository.existsByPartitaIva(payload.partitaIva())) {
                throw new BadRequest("La partita IVA " + payload.partitaIva() + " risulta già registrata da un altro cliente!");
            }
            cliente.setPartitaIva(payload.partitaIva());
        }

        if (payload.email() != null && !payload.email().equals(cliente.getEmail())) {
            if (this.clientiRepository.existsByEmail(payload.email())) {
                throw new BadRequest("La email " + payload.email() + " risulta già registrata da un altro cliente!");
            }
            cliente.setEmail(payload.email());
        }

        if (payload.pec() != null && !payload.pec().equals(cliente.getPec())) {
            if (this.clientiRepository.existsByPec(payload.pec())) {
                throw new BadRequest("La PEC " + payload.pec() + " risulta già registrata da un altro cliente!");
            }
            cliente.setPec(payload.pec());
        }

        if (payload.telefono() != null && !payload.telefono().equals(cliente.getTelefono())) {
            if (this.clientiRepository.existsByTelefono(payload.telefono())) {
                throw new BadRequest("Il telefono " + payload.telefono() + " risulta già registrato da un altro cliente!");
            }
            cliente.setTelefono(payload.telefono());
        }

        if (payload.tipoCliente() != null) cliente.setTipoCliente(payload.tipoCliente());
        if (payload.ragioneSociale() != null) cliente.setRagioneSociale(payload.ragioneSociale());
        if (payload.fatturatoAnnuale() != null) cliente.setFatturatoAnnuale(payload.fatturatoAnnuale());
        if (payload.dataUltimoContatto() != null) cliente.setDataUltimoContatto(payload.dataUltimoContatto());
        if (payload.emailContatto() != null) cliente.setEmailContatto(payload.emailContatto());
        if (payload.nomeContatto() != null) cliente.setNomeContatto(payload.nomeContatto());
        if (payload.cognomeContatto() != null) cliente.setCognomeContatto(payload.cognomeContatto());
        if (payload.telefonoContatto() != null) cliente.setTelefonoContatto(payload.telefonoContatto());

        if (payload.sedeOperativa() != null) {
            cliente.setSedeOperativa(indirizzoService.findById(payload.sedeOperativa()));
        }
        if (payload.sedeLegale() != null) {
            cliente.setSedeLegale(indirizzoService.findById(payload.sedeLegale()));
        }

        return clientiRepository.save(cliente);
    }


}
