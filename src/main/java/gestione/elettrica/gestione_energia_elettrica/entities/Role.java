package gestione.elettrica.gestione_energia_elettrica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

import java.util.*;

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
    private Set<User> utenti = new HashSet<>();

    public Role(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ruoli_autorizzazioni",
            joinColumns = @JoinColumn(name = "ruoloId"),
            inverseJoinColumns = @JoinColumn(name = "autorizzazioneId")
    )
    private
    Set<Autorizzazione> autorizzazioni = new HashSet<>();
}
