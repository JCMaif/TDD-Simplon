import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.simplon.TDD.model.Evenement;
import com.simplon.TDD.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Evenement> getFutureEventsByUserId(Long userId) {
        return utilisateurRepository.findById(userId)
                .map(utilisateur -> utilisateur.getEvenements().stream()
                    .filter(event -> event.getDateHeureDebut().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
