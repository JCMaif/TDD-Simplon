package com.simplon.TDD.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @Test
    @DisplayName("Création d'un événement")
    void shouldCreateAnEvent() {
        // Arrange ----------------------

        Utilisateur utilisateur1 = new Utilisateur(1L, "Bilbon", List.of());
        Utilisateur utilisateur2 = new Utilisateur(2L, "Frodon", List.of());
        Utilisateur utilisateur3 = new Utilisateur(3L, "Pippin", List.of());
        Utilisateur utilisateur4 = new Utilisateur(4L, "Sauron", List.of());

        // Auteur de l'événement
        Utilisateur auteur = utilisateur1;

        // Date et heure de l'événement
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateHeureDebut = LocalDateTime.parse("2024-11-05 10:00:00", formatter);
        LocalDateTime dateHeureFin = LocalDateTime.parse("2024-11-05 11:00:00", formatter);

        // Participants à l'événement
        List<Utilisateur> participants = new ArrayList<>();
        participants.add(utilisateur1);
        participants.add(utilisateur2);
        participants.add(utilisateur3);
        participants.add(utilisateur4);

        // Création de l'événement
        Evenement reunion1 = new Evenement(1L, "Daily Scrum", auteur, dateHeureDebut, dateHeureFin, participants);

        when(evenementRepository.save(reunion1)).thenReturn(reunion1);

        // Act -------------------
        Evenement evenement = evenementService.createEvenement(reunion1);

        // Assert ---------------------
        assertAll(
            () -> assertEquals(reunion1.getAuteur().getUsername(), evenement.getAuteur().getUsername(), "Le username de l'auteur est incorrect"),
            () -> assertEquals(reunion1.getUtilisateurs().size(), evenement.getUtilisateurs().size(), "Le nombre de participants est incorrect"),
            () -> assertEquals(reunion1.getDateHeureDebut(), evenement.getDateHeureDebut(), "La date de début est incorrecte"),
            () -> assertEquals(reunion1.getTitle(), evenement.getTitle(), "Le titre de l'événement est incorrect")
        );
    }
}
