package com.simplon.TDD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.simplon.TDD.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{

}
