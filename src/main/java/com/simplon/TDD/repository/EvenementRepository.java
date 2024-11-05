package com.simplon.TDD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplon.TDD.model.Evenement;

public interface EvenementRepository extends JpaRepository<Evenement, Long>{
    

}
