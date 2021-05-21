package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.QcmR;
import com.mycompany.myapp.repository.QcmRRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.QcmR}.
 */
@RestController
@RequestMapping("/api")
public class QcmRResource {

    private final Logger log = LoggerFactory.getLogger(QcmRResource.class);

    private static final String ENTITY_NAME = "qcmR";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QcmRRepository qcmRRepository;

    public QcmRResource(QcmRRepository qcmRRepository) {
        this.qcmRRepository = qcmRRepository;
    }

    /**
     * {@code POST  /qcm-rs} : Create a new qcmR.
     *
     * @param qcmR the qcmR to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qcmR, or with status {@code 400 (Bad Request)} if the qcmR has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qcm-rs")
    public ResponseEntity<QcmR> createQcmR(@RequestBody QcmR qcmR) throws URISyntaxException {
        log.debug("REST request to save QcmR : {}", qcmR);
        if (qcmR.getId() != null) {
            throw new BadRequestAlertException("A new qcmR cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QcmR result = qcmRRepository.save(qcmR);
        return ResponseEntity
            .created(new URI("/api/qcm-rs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /qcm-rs/:id} : Updates an existing qcmR.
     *
     * @param id the id of the qcmR to save.
     * @param qcmR the qcmR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qcmR,
     * or with status {@code 400 (Bad Request)} if the qcmR is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qcmR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qcm-rs/{id}")
    public ResponseEntity<QcmR> updateQcmR(@PathVariable(value = "id", required = false) final String id, @RequestBody QcmR qcmR)
        throws URISyntaxException {
        log.debug("REST request to update QcmR : {}, {}", id, qcmR);
        if (qcmR.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qcmR.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qcmRRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QcmR result = qcmRRepository.save(qcmR);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qcmR.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /qcm-rs/:id} : Partial updates given fields of an existing qcmR, field will ignore if it is null
     *
     * @param id the id of the qcmR to save.
     * @param qcmR the qcmR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qcmR,
     * or with status {@code 400 (Bad Request)} if the qcmR is not valid,
     * or with status {@code 404 (Not Found)} if the qcmR is not found,
     * or with status {@code 500 (Internal Server Error)} if the qcmR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qcm-rs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QcmR> partialUpdateQcmR(@PathVariable(value = "id", required = false) final String id, @RequestBody QcmR qcmR)
        throws URISyntaxException {
        log.debug("REST request to partial update QcmR partially : {}, {}", id, qcmR);
        if (qcmR.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qcmR.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qcmRRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QcmR> result = qcmRRepository
            .findById(qcmR.getId())
            .map(
                existingQcmR -> {
                    if (qcmR.getQuestion() != null) {
                        existingQcmR.setQuestion(qcmR.getQuestion());
                    }
                    if (qcmR.getChoixParticipant() != null) {
                        existingQcmR.setChoixParticipant(qcmR.getChoixParticipant());
                    }

                    return existingQcmR;
                }
            )
            .map(qcmRRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qcmR.getId()));
    }

    /**
     * {@code GET  /qcm-rs} : get all the qcmRS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qcmRS in body.
     */
    @GetMapping("/qcm-rs")
    public List<QcmR> getAllQcmRS() {
        log.debug("REST request to get all QcmRS");
        return qcmRRepository.findAll();
    }

    /**
     * {@code GET  /qcm-rs/:id} : get the "id" qcmR.
     *
     * @param id the id of the qcmR to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qcmR, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qcm-rs/{id}")
    public ResponseEntity<QcmR> getQcmR(@PathVariable String id) {
        log.debug("REST request to get QcmR : {}", id);
        Optional<QcmR> qcmR = qcmRRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qcmR);
    }

    /**
     * {@code DELETE  /qcm-rs/:id} : delete the "id" qcmR.
     *
     * @param id the id of the qcmR to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qcm-rs/{id}")
    public ResponseEntity<Void> deleteQcmR(@PathVariable String id) {
        log.debug("REST request to delete QcmR : {}", id);
        qcmRRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
