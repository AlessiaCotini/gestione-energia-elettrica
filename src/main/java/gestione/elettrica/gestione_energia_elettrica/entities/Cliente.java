package gestione.elettrica.gestione_energia_elettrica.entities;

import gestione.elettrica.gestione_energia_elettrica.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clienti")
@Getter
@Setter
@ToString(exclude = {"sedeOperativa", "sedeLegale"})
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente", nullable = false)
    private TipoCliente tipoCliente;

    @Column(name = "ragione_sociale", nullable = false)
    private String ragioneSociale;

    @Column(name = "p_iva", nullable = false, unique = true)
    private String partitaIva;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "data_inserimento", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDate dataInserimento;

    @Column(name = "data_ultimo_contatto")
    private LocalDate dataUltimoContatto;

    @Column(name = "fatturato_annuale", nullable = false)
    private double fatturatoAnnuale;

    @Column(unique = true, nullable = false)
    private String pec;

    @Column(unique = true, nullable = false)
    private String telefono;

    @Column(name = "email_contatto", nullable = false)
    private String emailContatto;

    @Column(name = "nome_contatto", nullable = false)
    private String nomeContatto;

    @Column(name = "cognome_contatto", nullable = false)
    private String cognomeContatto;

    @Column(name = "telefono_contatto", nullable = false)
    private String telefonoContatto;

    @Column(name = "logo_aziendale", nullable = false)
    private String logoAziendale;

    @ManyToOne
    @JoinColumn(name = "sede_operativa", nullable = false)
    private Indirizzo sedeOperativa;

    @ManyToOne
    @JoinColumn(name = "sede_legale", nullable = false)
    private Indirizzo sedeLegale;


    public Cliente(TipoCliente tipoCliente, String ragioneSociale, String partitaIva, String email, LocalDate dataUltimoContatto,
                   double fatturatoAnnuale, String pec, String telefono,
                   String emailContatto, String nomeContatto, String cognomeContatto,
                   String telefonoContatto, Indirizzo sedeOperativa, Indirizzo sedeLegale) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.dataInserimento = LocalDate.now();
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.tipoCliente = tipoCliente;
        this.sedeOperativa = sedeOperativa;
        this.sedeLegale = sedeLegale;
        this.logoAziendale = "https://res.cloudinary.com/qqqe0zym/image/upload/v1784802759/user_gzh6cc.png";
    }
}
