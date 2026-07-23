package gestione.elettrica.gestione_energia_elettrica.controllers;

import gestione.elettrica.gestione_energia_elettrica.entities.Role;
import gestione.elettrica.gestione_energia_elettrica.entities.User;
import gestione.elettrica.gestione_energia_elettrica.payloads.LoginRispostaDto;
import gestione.elettrica.gestione_energia_elettrica.payloads.UserLoginDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.UserRegisterDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.UserResponseDTO;
import gestione.elettrica.gestione_energia_elettrica.services.AuthService;
import gestione.elettrica.gestione_energia_elettrica.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
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
    public UserResponseDTO register(@RequestBody @Validated UserRegisterDTO body) {
        User savedUser = userService.save(body);
        return mapToDTO(savedUser);
    }

    private UserResponseDTO mapToDTO(User user) {
        Set<String> ruoliNames = user.getRuoli()
                .stream()
                .map(Role::getNomeRuolo)
                .collect(Collectors.toSet());
        Set<String> autorizzazioniNames = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return new UserResponseDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getName(),
                user.getSurname(), user.getAvatar(), ruoliNames, autorizzazioniNames);
    }
}
