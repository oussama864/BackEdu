package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ResultatEcole;
import com.mycompany.myapp.repository.ResultatEcoleRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ResultatEcole}.
 */
@RestController
@RequestMapping("/api")
public class ResultatEcoleResource {

    private final Logger log = LoggerFactory.getLogger(ResultatEcoleResource.class);

    private static final String ENTITY_NAME = "resultatEcole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultatEcoleRepository resultatEcoleRepository;

    public ResultatEcoleResource(ResultatEcoleRepository resultatEcoleRepository) {
        this.resultatEcoleRepository = resultatEcoleRepository;
    }

    /**
     * {@code POST  /resultat-ecoles} : Create a new resultatEcole.
     *
     * @param resultatEcole the resultatEcole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultatEcole, or with status {@code 400 (Bad Request)} if the resultatEcole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultat-ecoles")
    public ResponseEntity<ResultatEcole> createResultatEcole(@RequestBody ResultatEcole resultatEcole) throws URISyntaxException {
        log.debug("REST request to save ResultatEcole : {}", resultatEcole);
        if (resultatEcole.getId() != null) {
            throw new BadRequestAlertException("A new resultatEcole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultatEcole result = resultatEcoleRepository.save(resultatEcole);
        return ResponseEntity
            .created(new URI("/api/resultat-ecoles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /resultat-ecoles/:id} : Updates an existing resultatEcole.
     *
     * @param id the id of the resultatEcole to save.
     * @param resultatEcole the resultatEcole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultatEcole,
     * or with status {@code 400 (Bad Request)} if the resultatEcole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultatEcole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultat-ecoles/{id}")
    public ResponseEntity<ResultatEcole> updateResultatEcole(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ResultatEcole resultatEcole
    ) throws URISyntaxException {
        log.debug("REST request to update ResultatEcole : {}, {}", id, resultatEcole);
        if (resultatEcole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultatEcole.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultatEcoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResultatEcole result = resultatEcoleRepository.save(resultatEcole);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultatEcole.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /resultat-ecoles/:id} : Partial updates given fields of an existing resultatEcole, field will ignore if it is null
     *
     * @param id the id of the resultatEcole to save.
     * @param resultatEcole the resultatEcole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultatEcole,
     * or with status {@code 400 (Bad Request)} if the resultatEcole is not valid,
     * or with status {@code 404 (Not Found)} if the resultatEcole is not found,
     * or with status {@code 500 (Internal Server Error)} if the resultatEcole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resultat-ecoles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResultatEcole> partialUpdateResultatEcole(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ResultatEcole resultatEcole
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResultatEcole partially : {}, {}", id, resultatEcole);
        if (resultatEcole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultatEcole.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultatEcoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResultatEcole> result = resultatEcoleRepository
            .findById(resultatEcole.getId())
            .map(
                existingResultatEcole -> {
                    if (resultatEcole.getCreatedBy() != null) {
                        existingResultatEcole.setCreatedBy(resultatEcole.getCreatedBy());
                    }
                    if (resultatEcole.getCreatedDate() != null) {
                        existingResultatEcole.setCreatedDate(resultatEcole.getCreatedDate());
                    }
                    if (resultatEcole.getDeleted() != null) {
                        existingResultatEcole.setDeleted(resultatEcole.getDeleted());
                    }
                    if (resultatEcole.getDeletedBy() != null) {
                        existingResultatEcole.setDeletedBy(resultatEcole.getDeletedBy());
                    }
                    if (resultatEcole.getDeletedDate() != null) {
                        existingResultatEcole.setDeletedDate(resultatEcole.getDeletedDate());
                    }

                    return existingResultatEcole;
                }
            )
            .map(resultatEcoleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultatEcole.getId())
        );
    }

    /**
     * {@code GET  /resultat-ecoles} : get all the resultatEcoles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultatEcoles in body.
     */
    @GetMapping("/resultat-ecoles")
    public List<ResultatEcole> getAllResultatEcoles() {
        log.debug("REST request to get all ResultatEcoles");
        return resultatEcoleRepository.findAll();
    }

    /**
     * {@code GET  /resultat-ecoles/:id} : get the "id" resultatEcole.
     *
     * @param id the id of the resultatEcole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultatEcole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultat-ecoles/{id}")
    public ResponseEntity<ResultatEcole> getResultatEcole(@PathVariable String id) {
        log.debug("REST request to get ResultatEcole : {}", id);
        Optional<ResultatEcole> resultatEcole = resultatEcoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultatEcole);
    }

    /**
     * {@code DELETE  /resultat-ecoles/:id} : delete the "id" resultatEcole.
     *
     * @param id the id of the resultatEcole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultat-ecoles/{id}")
    public ResponseEntity<Void> deleteResultatEcole(@PathVariable String id) {
        log.debug("REST request to delete ResultatEcole : {}", id);
        resultatEcoleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
