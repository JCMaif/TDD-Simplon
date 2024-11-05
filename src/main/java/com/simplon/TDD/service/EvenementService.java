package com.simplon.TDD.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplon.TDD.model.Evenement;
import com.simplon.TDD.repository.EvenementRepository;

@Service
public class EvenementService {

    @Autowired
    EvenementRepository evenementRepository;

    public EvenementService(EvenementRepository evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    public Evenement createEvenement(Evenement evenement){
        return evenementRepository.save(evenement);
    }

    public List<Evenement> readAgenda() {
        LocalDateTime currentTime = LocalDateTime.now();
        return evenementRepository.findAll().stream()
            .filter(e -> e.getDateHeureDebut().isAfter(currentTime))
            .collect(Collectors.toList())
        ;
    }

}
