package gestione.elettrica.gestione_energia_elettrica.security;

import gestione.elettrica.gestione_energia_elettrica.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTTools {
    private final String secret;

    public JWTTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    //AGGIUNGO I METODI A DISPOSIZIONE DELLA LIBRERIA PER GENERARE E VERIFICARE I TOKEN

    // DATA EMISSIONE - DATA SCADENZA - ID DEL PROPRIETARIO - FIRMA DEL TOKEN

    public String generoToken(User user){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000 *60 *60 *24))
                .subject(String.valueOf(user.getUserId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verificoToken (String token){
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception e) {
            throw new RuntimeException("ERRORE TOKEN");
        }
    }

    public UUID checkIdDalToken(String token){
        return UUID.fromString(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject());
    }
}
