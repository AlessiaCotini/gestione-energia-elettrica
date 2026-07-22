package gestione.elettrica.gestione_energia_elettrica.specifications;

import gestione.elettrica.gestione_energia_elettrica.entities.Cliente;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ClientiSpecifications {
    public static Specification<Cliente> hasRagioneSociale(String ragioneSociale) {
        return ((root, query, builder) ->
                ragioneSociale == null || ragioneSociale.isBlank() ? null : builder.like(builder.lower(root.get("ragioneSociale")), "%" + ragioneSociale.toLowerCase() + "%"));
    }

    public static Specification<Cliente> hasFatturatoAnnuale(Double fatturato) {
        return (root, query, builder) ->
                fatturato == null ? null : builder.equal(root.get("fatturatoAnnuale"), fatturato);
    }

    public static Specification<Cliente> hasDataInserimento(LocalDate dataInserimento) {
        return (root, query, builder) ->
                dataInserimento == null ? null : builder.equal(root.get("dataInserimento"), dataInserimento);
    }

    public static Specification<Cliente> hasDataUltimoContatto(LocalDate dataUltimoContatto) {
        return (root, query, builder) ->
                dataUltimoContatto == null ? null : builder.equal(root.get("dataUltimoContatto"), dataUltimoContatto);
    }
}
