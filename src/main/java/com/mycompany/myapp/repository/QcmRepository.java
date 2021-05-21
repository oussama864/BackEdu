package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Qcm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Qcm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QcmRepository extends MongoRepository<Qcm, String> {}
