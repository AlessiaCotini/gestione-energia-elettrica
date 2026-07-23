package gestione.elettrica.gestione_energia_elettrica.controllers;

import gestione.elettrica.gestione_energia_elettrica.entities.User;
import gestione.elettrica.gestione_energia_elettrica.payloads.AggiornoRuoloUserDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.UpdateUserDTO;
import gestione.elettrica.gestione_energia_elettrica.payloads.UserResponseDTO;
import gestione.elettrica.gestione_energia_elettrica.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // OTTENGO IL MIO PROFILO
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(mapToDTO(currentUser));
    }

    // LISTA UTENTI
    @GetMapping
    @PreAuthorize("hasAuthority('GESTISCI_UTENTI')")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(Pageable pageable) {
        Page<UserResponseDTO> usersPage = userService.findAll(pageable)
                .map(this::mapToDTO);

        return ResponseEntity.ok(usersPage);    }

    // ASSEGNA RUOLI
    @PutMapping("/{userId}/ruoli")
    @PreAuthorize("hasAuthority('ASSEGNA_RUOLI')")
    public ResponseEntity<UserResponseDTO> updateUserRoles(
            @PathVariable UUID userId,
            @RequestBody AggiornoRuoloUserDTO dto) {

        User updatedUser = userService.updateUserRoles(userId, dto.roleIds());
        return ResponseEntity.ok(mapToDTO(updatedUser));
    }

    //GESTISCO CAMBIO

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('GESTISCI_UTENTI')")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable UUID userId,
            @RequestBody @Validated UpdateUserDTO body) {

        User updatedUser = userService.findByIdAndUpdate(userId, body);
        return ResponseEntity.ok(mapToDTO(updatedUser));
    }

    // CONVERTO User IN DTO
    private UserResponseDTO mapToDTO(User user) {
        Set<String> ruoliNames = user.getRuoli().stream()
                .map(r -> r.getNomeRuolo())
                .collect(Collectors.toSet());

        Set<String> autorizzazioniNames = user.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toSet());

        return new UserResponseDTO(
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getAvatar(),
                ruoliNames,
                autorizzazioniNames
        );
    }
}
