package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ecolier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Ecolier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcolierRepository extends MongoRepository<Ecolier, String> {}
