package gestione.elettrica.gestione_energia_elettrica.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "province")
@Getter
@Setter
@NoArgsConstructor
public class Provincia {
    @Id
    @GeneratedValue

    private UUID id;

    private String nome;

    private String sigla;

    private String regione;

    public Provincia(String nome, String sigla, String regione) {
        this.nome = nome;
        this.sigla = sigla;
        this.regione = regione;
    }
}
