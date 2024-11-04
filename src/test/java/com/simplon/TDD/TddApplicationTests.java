package com.simplon.TDD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.simplon.TDD.model.Utilisateur;
import com.simplon.TDD.service.utilisateurService;

@SpringBootTest
class TddApplicationTests {

	@Test
	void contextLoads() {
	}

	/*
	 * On veut créer un événement :
	 * - creation utilisateur
	 * - sélectionner un utilisateur
	 * - vérifier ses événements pour voir ses non-disponibilités
	 * - sélectionner une date
	 * - sélectionner une heure de début
	 * - choisir une durée
	 * - ajouter un titre à l'événement
	 * - créer l'événement
	*/

	@Test
	void shouldCreateUtilisateur() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(1L);
		utilisateur.setUsername("Bilbon");
		utilisateur.setEvents(Collections.emptyList());

		Utilisateur savedUtilisateur = utilisateurService.save(utilisateur);

		assertEquals(utilisateur.getId(), savedUtilisateur.getId());
	}

}
