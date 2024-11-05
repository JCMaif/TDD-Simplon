package com.simplon.TDD.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.simplon.TDD.model.Evenement;
import com.simplon.TDD.model.Utilisateur;
import com.simplon.TDD.repository.UtilisateurRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UtilisateurServiceTest {

    @Mock
    UtilisateurRepository utilisateurRepository;

    @InjectMocks
    UtilisateurService utilisateurService;

    private Evenement event1;
    private Evenement event2;
    private Evenement event3;
    private Evenement event4;
    private Evenement event5;

    private Utilisateur utilisateur;

    private DateTimeFormatter formatter;

      @BeforeEach
    void setup() {
        LocalDateTime now = LocalDateTime.now();
        Evenement event1 = new Evenement(1L, "Daily Scrum", utilisateur, now.minusDays(1), now.minusDays(1).plusHours(1), List.of(utilisateur));
        Evenement event2 = new Evenement(2L, "Workshop", utilisateur, now.plusDays(1), now.plusDays(1).plusHours(1), List.of(utilisateur));
        Evenement event3 = new Evenement(3L, "Meeting", utilisateur, now.plusDays(2), now.plusDays(2).plusHours(1), List.of(utilisateur));
        Evenement event4 = new Evenement(4L, "Webinar", utilisateur, now.minusDays(2), now.minusDays(2).plusHours(1), List.of(utilisateur));
        
        utilisateur = new Utilisateur(1L, "Bilbon", List.of(event1, event2, event3, event4));
    }

    @Test
    @DisplayName("Retourner l'agenda d'un utilisateur")
    void shouldReturnAllEventsFromAUserById(){

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        List<Evenement> futurEvenements = utilisateurService.getFutureEventsByUserId(1L);
        

         // Assert
        assertEquals(2, futurEvenements.size(), "Le nombre d'événements futurs pour l'utilisateur est incorrect");
        assertEquals("Workshop", futurEvenements.get(0).getTitle(), "Le premier événement futur est incorrect");
        assertEquals("Meeting", futurEvenements.get(1).getTitle(), "Le deuxième événement futur est incorrect");
    }
    

}
