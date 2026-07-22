package gestione.elettrica.gestione_energia_elettrica.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "fatture")
public class Fattura {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal importo;

    @Column(nullable = false, unique = true)
    private String numeroFattura;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "stato_fattura_id", nullable = false)
    private StatoFattura statoFattura;

    public Fattura() {
    }

    public Fattura(LocalDate data, BigDecimal importo, String numeroFattura, Cliente cliente, StatoFattura statoFattura) {
        this.data = data;
        this.importo = importo;
        this.numeroFattura = numeroFattura;
        this.cliente = cliente;
        this.statoFattura = statoFattura;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public String getNumeroFattura() {
        return numeroFattura;
    }

    public void setNumeroFattura(String numeroFattura) {
        this.numeroFattura = numeroFattura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public StatoFattura getStatoFattura() {
        return statoFattura;
    }

    public void setStatoFattura(StatoFattura statoFattura) {
        this.statoFattura = statoFattura;
    }

    @Override
    public String toString() {
        return "Fattura{" +
                "id=" + id +
                ", data=" + data +
                ", importo=" + importo +
                ", numeroFattura='" + numeroFattura + '\'' +
                ", cliente=" + cliente +
                ", statoFattura=" + statoFattura +
                '}';
    }
}
