package gestione.elettrica.gestione_energia_elettrica.configuration;

import gestione.elettrica.gestione_energia_elettrica.entities.User;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {

    private String mailgunApiKey;
    private String mailgunDomainname;

    public MailgunSender(@Value("${mailgun.apikey}") String mailgunApiKey,
                         @Value("${mailgun.domainname}") String mailgunDomainName) {
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunDomainname = mailgunDomainName;
    }

    public void sendRegistrationEmail(User recipient) {


        HttpResponse<JsonNode> response = Unirest.post(
                        "https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", "Kevin Donati <kevindonati5@gmail.com>")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Benvenuto in Gestione Energia Elettrica!")
                .queryString("text",
                        "Gentile " + recipient.getName() + " " + recipient.getSurname() + ",\n\n" +
                                "siamo lieti di confermare che la tua registrazione alla piattaforma." +
                                "\n\nCordiali saluti,\n" +
                                "Il team di Gestione Energia Elettrica")
                .asJson();

        System.out.println("Status: " + response.getStatus());
        System.out.println("Body: " + response.getBody());
    }
}
