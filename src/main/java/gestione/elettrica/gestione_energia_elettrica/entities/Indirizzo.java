package gestione.elettrica.gestione_energia_elettrica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@NoArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue
    private UUID id;

    private String via;

    private String civico;

    private String localita;

    private int cap;

    @ManyToOne
    @JoinColumn(name = "id_comune")
    private Comune comune;

    public Indirizzo(String via, String civico, String localita, int cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}
