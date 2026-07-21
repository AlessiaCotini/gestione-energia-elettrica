package gestione.elettrica.gestione_energia_elettrica.configuration;

import gestione.elettrica.gestione_energia_elettrica.entities.Autorizzazione;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AutorizzazzioniConfig
{


    @Bean(name = "gestisci_utenti")
    public Autorizzazione getGestisciUtenti(){
        return new Autorizzazione("GESTISCI_UTENTI");
    }

    @Bean(name = "assegna_ruoli")
    public Autorizzazione getNuoviRuoli(){return new Autorizzazione("ASSEGNA_RUOLI");}

    @Bean(name = "crea_fattura")
    public Autorizzazione getNuovaFattura(){return new Autorizzazione("CREA_FATTURA");}

    @Bean(name = "modifica_fattura")
    public Autorizzazione getFatturaModificata(){return new Autorizzazione("MODIFICA_FATTURA");}

}
