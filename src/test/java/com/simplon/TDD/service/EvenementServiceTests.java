package com.simplon.TDD.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import com.simplon.TDD.repository.EvenementRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EvenementServiceTests {

    @Mock
    private EvenementRepository evenementRepository;

    @InjectMocks
    private EvenementService evenementService;

    private Utilisateur utilisateur1;
    private Utilisateur utilisateur2;
    private Utilisateur utilisateur3;
    private Utilisateur utilisateur4;
    private List<Utilisateur> participants;
    private DateTimeFormatter formatter;

    @BeforeEach
    void setup() {
        utilisateur1 = new Utilisateur(1L, "Bilbon", List.of());
        utilisateur2 = new Utilisateur(2L, "Frodon", List.of());
        utilisateur3 = new Utilisateur(3L, "Pippin", List.of());
        utilisateur4 = new Utilisateur(4L, "Sauron", List.of());
        
        participants = List.of(utilisateur1, utilisateur2, utilisateur3, utilisateur4);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    @DisplayName("Création d'un événement")
    void shouldCreateAnEvent() {
        // Arrange ----------------------
        Evenement reunion1 = createEvenement("Daily Scrum", utilisateur1, "2024-11-05 10:00:00", "2024-11-05 11:00:00", participants);
        when(evenementRepository.save(reunion1)).thenReturn(reunion1);

        // Act --------------------------
        Evenement evenement = evenementService.createEvenement(reunion1);

        // Assert -----------------------
        assertAll(
            () -> assertEquals(reunion1.getAuteur().getUsername(), evenement.getAuteur().getUsername(), "Le username de l'auteur est incorrect"),
            () -> assertEquals(reunion1.getUtilisateurs().size(), evenement.getUtilisateurs().size(), "Le nombre de participants est incorrect"),
            () -> assertEquals(reunion1.getDateHeureDebut(), evenement.getDateHeureDebut(), "La date de début est incorrecte"),
            () -> assertEquals(reunion1.getTitle(), evenement.getTitle(), "Le titre de l'événement est incorrect")
        );
    }

    private Evenement createEvenement(String title, Utilisateur auteur, String dateDebut, String dateFin, List<Utilisateur> participants) {
        LocalDateTime dateHeureDebut = LocalDateTime.parse(dateDebut, formatter);
        LocalDateTime dateHeureFin = LocalDateTime.parse(dateFin, formatter);
        return new Evenement(null, title, auteur, dateHeureDebut, dateHeureFin, participants);
    }

    @Test
    @DisplayName("Agenda : lire les evenements à venir")
    void shouldReadFutureEvents() {
        //Arrange : creation en mock d'évènements passés et futurs
        LocalDateTime now = LocalDateTime.now();
        Evenement eventFuture1 = createEvenement("Conférence", utilisateur1, now.plusDays(1).format(formatter), now.plusDays(1).plusHours(1).format(formatter), participants);
        Evenement eventFuture2 = createEvenement("Atelier", utilisateur2, now.plusDays(2).format(formatter), now.plusDays(2).plusHours(1).format(formatter), participants);
        Evenement eventPast = createEvenement("Réunion terminée", utilisateur3, now.minusDays(1).format(formatter), now.minusDays(1).plusHours(1).format(formatter), participants);

        when(evenementRepository.findAll()).thenReturn(List.of(eventFuture1, eventFuture2, eventPast));

        //Act
        List<Evenement> agenda = evenementService.readAgenda();

        // Assert -----------------------
        assertEquals(2, agenda.size(), "Le nombre d'événements à venir est incorrect");
        assertEquals("Conférence", agenda.get(0).getTitle(), "Le premier événement à venir est incorrect");
        assertEquals("Atelier", agenda.get(1).getTitle(), "Le deuxième événement à venir est incorrect");
    }
}
