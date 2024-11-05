package com.simplon.TDD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplon.TDD.model.Evenement;
import com.simplon.TDD.repository.EvenementRepository;

@Service
public class EvenementService {

    @Autowired
    EvenementRepository evenementRepository;

    public Evenement createEvenement(Evenement evenement){
        return evenementRepository.save(evenement);
    }

}
