package gestione.elettrica.gestione_energia_elettrica.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "stati_fattura")
public class StatoFattura {
    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    public StatoFattura() {
    }

    public StatoFattura(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "StatoFattura{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
