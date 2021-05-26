package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Competition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionRepository extends MongoRepository<Competition, String> {

}
