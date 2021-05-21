package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Ecolier;
import com.mycompany.myapp.repository.EcolierRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Ecolier}.
 */
@RestController
@RequestMapping("/api")
public class EcolierResource {

    private final Logger log = LoggerFactory.getLogger(EcolierResource.class);

    private static final String ENTITY_NAME = "ecolier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcolierRepository ecolierRepository;

    public EcolierResource(EcolierRepository ecolierRepository) {
        this.ecolierRepository = ecolierRepository;
    }

    /**
     * {@code POST  /ecoliers} : Create a new ecolier.
     *
     * @param ecolier the ecolier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecolier, or with status {@code 400 (Bad Request)} if the ecolier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ecoliers")
    public ResponseEntity<Ecolier> createEcolier(@RequestBody Ecolier ecolier) throws URISyntaxException {
        log.debug("REST request to save Ecolier : {}", ecolier);
        if (ecolier.getId() != null) {
            throw new BadRequestAlertException("A new ecolier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ecolier result = ecolierRepository.save(ecolier);
        return ResponseEntity
            .created(new URI("/api/ecoliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /ecoliers/:id} : Updates an existing ecolier.
     *
     * @param id the id of the ecolier to save.
     * @param ecolier the ecolier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecolier,
     * or with status {@code 400 (Bad Request)} if the ecolier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecolier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ecoliers/{id}")
    public ResponseEntity<Ecolier> updateEcolier(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Ecolier ecolier
    ) throws URISyntaxException {
        log.debug("REST request to update Ecolier : {}, {}", id, ecolier);
        if (ecolier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecolier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecolierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ecolier result = ecolierRepository.save(ecolier);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecolier.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /ecoliers/:id} : Partial updates given fields of an existing ecolier, field will ignore if it is null
     *
     * @param id the id of the ecolier to save.
     * @param ecolier the ecolier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecolier,
     * or with status {@code 400 (Bad Request)} if the ecolier is not valid,
     * or with status {@code 404 (Not Found)} if the ecolier is not found,
     * or with status {@code 500 (Internal Server Error)} if the ecolier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ecoliers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Ecolier> partialUpdateEcolier(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Ecolier ecolier
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ecolier partially : {}, {}", id, ecolier);
        if (ecolier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecolier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecolierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ecolier> result = ecolierRepository
            .findById(ecolier.getId())
            .map(
                existingEcolier -> {
                    if (ecolier.getFirstName() != null) {
                        existingEcolier.setFirstName(ecolier.getFirstName());
                    }
                    if (ecolier.getLastName() != null) {
                        existingEcolier.setLastName(ecolier.getLastName());
                    }
                    if (ecolier.getEmail() != null) {
                        existingEcolier.setEmail(ecolier.getEmail());
                    }
                    if (ecolier.getRefUser() != null) {
                        existingEcolier.setRefUser(ecolier.getRefUser());
                    }
                    if (ecolier.getAge() != null) {
                        existingEcolier.setAge(ecolier.getAge());
                    }
                    if (ecolier.getNiveau() != null) {
                        existingEcolier.setNiveau(ecolier.getNiveau());
                    }
                    if (ecolier.getEcole() != null) {
                        existingEcolier.setEcole(ecolier.getEcole());
                    }
                    if (ecolier.getDateDeNaissance() != null) {
                        existingEcolier.setDateDeNaissance(ecolier.getDateDeNaissance());
                    }
                    if (ecolier.getNomParent() != null) {
                        existingEcolier.setNomParent(ecolier.getNomParent());
                    }
                    if (ecolier.getPassword() != null) {
                        existingEcolier.setPassword(ecolier.getPassword());
                    }
                    if (ecolier.getCreatedBy() != null) {
                        existingEcolier.setCreatedBy(ecolier.getCreatedBy());
                    }
                    if (ecolier.getCreatedDate() != null) {
                        existingEcolier.setCreatedDate(ecolier.getCreatedDate());
                    }
                    if (ecolier.getDeleted() != null) {
                        existingEcolier.setDeleted(ecolier.getDeleted());
                    }
                    if (ecolier.getDeletedBy() != null) {
                        existingEcolier.setDeletedBy(ecolier.getDeletedBy());
                    }
                    if (ecolier.getDeletedDate() != null) {
                        existingEcolier.setDeletedDate(ecolier.getDeletedDate());
                    }

                    return existingEcolier;
                }
            )
            .map(ecolierRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecolier.getId()));
    }

    /**
     * {@code GET  /ecoliers} : get all the ecoliers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecoliers in body.
     */
    @GetMapping("/ecoliers")
    public List<Ecolier> getAllEcoliers() {
        log.debug("REST request to get all Ecoliers");
        return ecolierRepository.findAll();
    }

    /**
     * {@code GET  /ecoliers/:id} : get the "id" ecolier.
     *
     * @param id the id of the ecolier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecolier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ecoliers/{id}")
    public ResponseEntity<Ecolier> getEcolier(@PathVariable String id) {
        log.debug("REST request to get Ecolier : {}", id);
        Optional<Ecolier> ecolier = ecolierRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ecolier);
    }

    /**
     * {@code DELETE  /ecoliers/:id} : delete the "id" ecolier.
     *
     * @param id the id of the ecolier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ecoliers/{id}")
    public ResponseEntity<Void> deleteEcolier(@PathVariable String id) {
        log.debug("REST request to delete Ecolier : {}", id);
        ecolierRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
