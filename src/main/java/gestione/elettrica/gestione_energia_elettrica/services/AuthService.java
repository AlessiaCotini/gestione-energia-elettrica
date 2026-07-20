package gestione.elettrica.gestione_energia_elettrica.services;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.UnAuthorized;
import gestione.elettrica.gestione_energia_elettrica.entities.User;
import gestione.elettrica.gestione_energia_elettrica.payloads.UserLoginDTO;
import gestione.elettrica.gestione_energia_elettrica.security.JWTTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final JWTTools jwtTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UserService userService, JWTTools jwtTools, PasswordEncoder bcrypt) {
        this.userService = userService;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public String authenticateUserAndGenerateToken(UserLoginDTO body) {
        User user = userService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.generoToken(user);
        } else {
            throw new UnAuthorized("Credenziali errate");
        }
    }
}
