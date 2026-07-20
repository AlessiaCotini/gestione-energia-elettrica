package gestione.elettrica.gestione_energia_elettrica.services;

import gestione.elettrica.gestione_energia_elettrica.eccezioni.AccessDenied;
import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.Role;
import gestione.elettrica.gestione_energia_elettrica.entities.User;
import gestione.elettrica.gestione_energia_elettrica.payloads.UserDTO;
import gestione.elettrica.gestione_energia_elettrica.repositories.RoleRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bcrypt;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder bcrypt) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bcrypt = bcrypt;
    }

    public User save(UserDTO body) {
        if (this.userRepository.existsByEmail(body.email())) {
            throw new AccessDenied("Email già in utilizzo");
        }

        User nuovo = new User();
        nuovo.setUsername(body.username());
        nuovo.setEmail(body.email());
        nuovo.setPassword(this.bcrypt.encode(body.password()));
        nuovo.setName(body.name());
        nuovo.setSurname(body.surname());

        Role ruolo = roleRepository.findByNomeRuolo("USER")
                .orElseThrow(() -> new NotFound("Ruolo USER non trovato"));

        nuovo.setRuoli(List.of(ruolo));

        return userRepository.save(nuovo);
    }

    public User findById(UUID userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("Utente con ID " + userId + " non trovato"));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFound("Email non trovata"));
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findByIdAndUpdate(UUID userId, UserDTO body) {
        User trovato = this.findById(userId);

        if (!trovato.getEmail().equals(body.email()) && this.userRepository.existsByEmail(body.email())) {
            throw new AccessDenied("Email già in utilizzo");
        }

        trovato.setUsername(body.username());
        trovato.setEmail(body.email());
        if (body.password() != null && !body.password().isBlank()) {
            trovato.setPassword(this.bcrypt.encode(body.password()));
        }
        trovato.setName(body.name());
        trovato.setSurname(body.surname());

        return this.userRepository.save(trovato);
    }

    public void findByIdAndDelete(UUID userId) {
        User trovato = this.findById(userId);
        this.userRepository.delete(trovato);
    }
}

