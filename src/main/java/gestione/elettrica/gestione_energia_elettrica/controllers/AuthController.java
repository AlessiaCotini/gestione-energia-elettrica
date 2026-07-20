package gestione.elettrica.gestione_energia_elettrica.controllers;

import gestione.elettrica.gestione_energia_elettrica.entities.User;
import gestione.elettrica.gestione_energia_elettrica.payloads.LoginRispostaDto;
import gestione.elettrica.gestione_energia_elettrica.payloads.UserDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.UserLoginDTO;
import gestione.elettrica.gestione_energia_elettrica.services.AuthService;
import gestione.elettrica.gestione_energia_elettrica.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginRispostaDto login(@RequestBody @Validated UserLoginDTO body) {
        String token = authService.authenticateUserAndGenerateToken(body);
        return new LoginRispostaDto(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Validated UserDTO body) {
        return userService.save(body);
    }
}
