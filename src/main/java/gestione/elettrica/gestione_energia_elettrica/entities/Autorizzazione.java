package gestione.elettrica.gestione_energia_elettrica.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "autorizzazioni")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Autorizzazione {

    @Id
    @GeneratedValue
    private UUID autorizzazioneId;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "autorizzazioni")
    private Set<Role> ruoli;

    public Autorizzazione(String nome) {
        this.nome = nome;
    }
}
