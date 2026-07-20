package gestione.elettrica.gestione_energia_elettrica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "utenti")
@Table(name = "ruoli")
public class Role {

    @Id
    @GeneratedValue
    private UUID ruoloId;

    @Column(nullable = false)
    private String nomeRuolo;

    @ManyToMany(mappedBy = "ruoli")
    private List<User> utenti = new ArrayList<>();

    public Role(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }
}
