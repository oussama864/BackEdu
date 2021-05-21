package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Auteur;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Auteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuteurRepository extends MongoRepository<Auteur, String> {
    Optional<Auteur> findByEmail(String email);
}
