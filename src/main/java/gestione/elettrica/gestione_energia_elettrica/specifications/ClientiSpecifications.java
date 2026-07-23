package gestione.elettrica.gestione_energia_elettrica.specifications;

import gestione.elettrica.gestione_energia_elettrica.entities.Cliente;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ClientiSpecifications {

    public static Specification<Cliente> hasRagioneSociale(String ragioneSociale) {
        return (root, query, builder) ->
                ragioneSociale == null || ragioneSociale.isBlank()
                        ? null
                        : builder.like(builder.lower(root.get("ragioneSociale")), "%" + ragioneSociale.toLowerCase() + "%");
    }

    public static Specification<Cliente> hasFatturatoAnnuale(Double fatturato) {
        return (root, query, builder) ->
                fatturato == null ? null : builder.equal(root.get("fatturatoAnnuale"), fatturato);
    }

    public static Specification<Cliente> fatturatoBetween(Double fatturatoMin, Double fatturatoMax) {
        return (root, query, builder) -> {
            if (fatturatoMin == null && fatturatoMax == null) return null;
            if (fatturatoMin != null && fatturatoMax != null)
                return builder.between(root.get("fatturatoAnnuale"), fatturatoMin, fatturatoMax);
            if (fatturatoMin != null)
                return builder.greaterThanOrEqualTo(root.get("fatturatoAnnuale"), fatturatoMin);
            return builder.lessThanOrEqualTo(root.get("fatturatoAnnuale"), fatturatoMax);
        };
    }

    public static Specification<Cliente> hasDataInserimento(LocalDate dataInserimento) {
        return (root, query, builder) ->
                dataInserimento == null ? null : builder.equal(root.get("dataInserimento"), dataInserimento);
    }

    public static Specification<Cliente> dataInserimentoBetween(LocalDate dataInserimentoStart, LocalDate dataInserimentoEnd) {
        return (root, query, builder) -> {
            if (dataInserimentoStart == null && dataInserimentoEnd == null) return null;
            if (dataInserimentoStart != null && dataInserimentoEnd != null)
                return builder.between(root.get("dataInserimento"), dataInserimentoStart, dataInserimentoEnd);
            if (dataInserimentoStart != null)
                return builder.greaterThanOrEqualTo(root.get("dataInserimento"), dataInserimentoStart);
            return builder.lessThanOrEqualTo(root.get("dataInserimento"), dataInserimentoEnd);
        };
    }

    public static Specification<Cliente> hasDataUltimoContatto(LocalDate dataUltimoContatto) {
        return (root, query, builder) ->
                dataUltimoContatto == null ? null : builder.equal(root.get("dataUltimoContatto"), dataUltimoContatto);
    }

    public static Specification<Cliente> dataUltimoContattoBetween(LocalDate dataUltimoContattoStart, LocalDate dataUltimoContattoEnd) {
        return (root, query, builder) -> {
            if (dataUltimoContattoStart == null && dataUltimoContattoEnd == null) return null;
            if (dataUltimoContattoStart != null && dataUltimoContattoEnd != null)
                return builder.between(root.get("dataUltimoContatto"), dataUltimoContattoStart, dataUltimoContattoEnd);
            if (dataUltimoContattoStart != null)
                return builder.greaterThanOrEqualTo(root.get("dataUltimoContatto"), dataUltimoContattoStart);
            return builder.lessThanOrEqualTo(root.get("dataUltimoContatto"), dataUltimoContattoEnd);
        };
    }
}