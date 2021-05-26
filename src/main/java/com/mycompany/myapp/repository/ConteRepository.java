package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Conte;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Conte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConteRepository extends MongoRepository<Conte, String> {
    List<Conte> findByAuteur_Id(String id);
    List<Conte> findByAuteur_Email(String email);
    List<Conte> findByEmailAuteur(String emailAuteur);
    List<Conte> findByReadyForCompetetionIsTrueAndReadyForCompetitionDateAfter(Date date);
}
