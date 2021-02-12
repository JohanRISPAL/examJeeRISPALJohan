package com.humanbooster.examJee.repository;

import com.humanbooster.examJee.model.Annonce;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnnonceRepository extends CrudRepository<Annonce, Integer> {

    @Override
    List<Annonce> findAll();
}
