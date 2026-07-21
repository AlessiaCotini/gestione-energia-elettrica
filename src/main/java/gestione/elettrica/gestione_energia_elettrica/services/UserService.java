package gestione.elettrica.gestione_energia_elettrica.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gestione.elettrica.gestione_energia_elettrica.configuration.CloudinaryConfig;
import gestione.elettrica.gestione_energia_elettrica.eccezioni.AccessDenied;
import gestione.elettrica.gestione_energia_elettrica.eccezioni.NotFound;
import gestione.elettrica.gestione_energia_elettrica.entities.Role;
import gestione.elettrica.gestione_energia_elettrica.entities.User;
import gestione.elettrica.gestione_energia_elettrica.payloads.UserResponseDTO;
import gestione.elettrica.gestione_energia_elettrica.repositories.RoleRepository;
import gestione.elettrica.gestione_energia_elettrica.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bcrypt;
    private final Cloudinary cloudinaryConfig;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder bcrypt, CloudinaryConfig cloudinaryConfig, Cloudinary cloudinaryConfig1) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bcrypt = bcrypt;
        this.cloudinaryConfig = cloudinaryConfig1;
    }

    public User uploadAvatar(UUID userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Map uploadResult = cloudinaryConfig.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String url = uploadResult.get("secure_url").toString();
        String publicId = uploadResult.get("public_id").toString();

        user.setAvatar(url);

        return userRepository.save(user);
    }



    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User save(UserResponseDTO body) {
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

        nuovo.setRuoli(Set.of(ruolo));

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


    public User findByIdAndUpdate(UUID userId, UserResponseDTO body) {
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

    public User updateUserRoles(UUID userId, Set<UUID> roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        List<Role> newRoles = roleRepository.findAllById(roleId);

        user.getRuoli().clear();
        user.getRuoli().addAll(newRoles);

        return userRepository.save(user);
    }

    //ADMIN DEVE POTER INVIARE LA MAIL
}

