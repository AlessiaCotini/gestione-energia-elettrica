package gestione.elettrica.gestione_energia_elettrica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "comuni")
@Getter
@Setter
@NoArgsConstructor
public class Comune {
    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    private String progressivoComune;

    private String codiceProvincia;

    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private Provincia provincia;

    public Comune(String nome, String progressivoComune, String codiceProvincia, Provincia provincia) {
        this.nome = nome;
        this.progressivoComune = progressivoComune;
        this.codiceProvincia = codiceProvincia;
        this.provincia = provincia;
    }
}
