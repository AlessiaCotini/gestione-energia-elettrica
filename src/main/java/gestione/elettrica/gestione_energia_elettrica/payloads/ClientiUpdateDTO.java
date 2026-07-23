package gestione.elettrica.gestione_energia_elettrica.payloads;

import gestione.elettrica.gestione_energia_elettrica.enums.TipoCliente;

import java.time.LocalDate;
import java.util.UUID;

public record ClientiUpdateDTO(
        TipoCliente tipoCliente,
        String ragioneSociale,
        String partitaIva,
        String email,
        LocalDate dataUltimoContatto,
        Double fatturatoAnnuale,
        String pec,
        String telefono,
        String emailContatto,
        String nomeContatto,
        String cognomeContatto,
        String telefonoContatto,
        UUID sedeOperativa,
        UUID sedeLegale
) {
}
