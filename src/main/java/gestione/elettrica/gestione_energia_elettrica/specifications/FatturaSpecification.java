package gestione.elettrica.gestione_energia_elettrica.specifications;

import gestione.elettrica.gestione_energia_elettrica.entities.Fattura;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class FatturaSpecification {


    public static Specification<Fattura> hasCliente(
            UUID clienteId
    ) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.equal(
                        root.get("cliente")
                                .get("id"),
                        clienteId
                );
    }


    public static Specification<Fattura> hasStato(
            UUID statoId
    ) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.equal(
                        root.get("statoFattura")
                                .get("id"),
                        statoId
                );
    }

    public static Specification<Fattura> hasData(
            LocalDate data
    ) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.equal(
                        root.get("data"),
                        data
                );
    }


    public static Specification<Fattura> dataBetween(
            LocalDate start,
            LocalDate end
    ) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.between(
                        root.get("data"),
                        start,
                        end
                );
    }


    public static Specification<Fattura> dataGreaterThanOrEqualTo(
            LocalDate start
    ) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("data"),
                        start
                );
    }


    public static Specification<Fattura> dataLessThanOrEqualTo(
            LocalDate end
    ) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.lessThanOrEqualTo(
                        root.get("data"),
                        end
                );
    }


    public static Specification<Fattura> importoBetween(
            BigDecimal min,
            BigDecimal max
    ) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.between(
                        root.get("importo"),
                        min,
                        max
                );
    }


    public static Specification<Fattura> importoGreaterThanOrEqualTo(
            BigDecimal min
    ) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("importo"),
                        min
                );
    }


    public static Specification<Fattura> importoLessThanOrEqualTo(
            BigDecimal max
    ) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.lessThanOrEqualTo(
                        root.get("importo"),
                        max
                );
    }


    public static Specification<Fattura> hasAnno(
            int anno
    ) {

        LocalDate start = LocalDate.of(anno, 1, 1);

        LocalDate end = LocalDate.of(anno, 12, 31);

        return dataBetween(start, end);
    }
}