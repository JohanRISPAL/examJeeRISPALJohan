package com.humanbooster.examJee.service;

import com.humanbooster.examJee.model.Annonce;
import com.humanbooster.examJee.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnonceService{

    @Autowired
    private AnnonceRepository annonceRepository;


    public List<Annonce> getAll() {
        return this.annonceRepository.findAll();
    }

    public Annonce save(Annonce annonce) {
        this.annonceRepository.save(annonce);
        return annonce;
    }

    public void deleteAnnonce(Annonce annonce){
        this.annonceRepository.delete(annonce);
    }

}
