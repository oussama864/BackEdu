package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Competition;
import com.mycompany.myapp.repository.CompetitionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Competition}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionResource.class);

    private static final String ENTITY_NAME = "competition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionRepository competitionRepository;

    public CompetitionResource(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    /**
     * {@code POST  /competitions} : Create a new competition.
     *
     * @param competition the competition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competition, or with status {@code 400 (Bad Request)} if the competition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitions")
    public ResponseEntity<Competition> createCompetition(@RequestBody Competition competition) throws URISyntaxException {
        log.debug("REST request to save Competition : {}", competition);
        if (competition.getId() != null) {
            throw new BadRequestAlertException("A new competition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competition result = competitionRepository.save(competition);
        return ResponseEntity
            .created(new URI("/api/competitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /competitions/:id} : Updates an existing competition.
     *
     * @param id the id of the competition to save.
     * @param competition the competition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competition,
     * or with status {@code 400 (Bad Request)} if the competition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitions/{id}")
    public ResponseEntity<Competition> updateCompetition(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Competition competition
    ) throws URISyntaxException {
        log.debug("REST request to update Competition : {}, {}", id, competition);
        if (competition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Competition result = competitionRepository.save(competition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competition.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /competitions/:id} : Partial updates given fields of an existing competition, field will ignore if it is null
     *
     * @param id the id of the competition to save.
     * @param competition the competition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competition,
     * or with status {@code 400 (Bad Request)} if the competition is not valid,
     * or with status {@code 404 (Not Found)} if the competition is not found,
     * or with status {@code 500 (Internal Server Error)} if the competition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competitions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Competition> partialUpdateCompetition(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Competition competition
    ) throws URISyntaxException {
        log.debug("REST request to partial update Competition partially : {}, {}", id, competition);
        if (competition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Competition> result = competitionRepository
            .findById(competition.getId())
            .map(
                existingCompetition -> {
                    if (competition.getDate() != null) {
                        existingCompetition.setDate(competition.getDate());
                    }
                    if (competition.getCode() != null) {
                        existingCompetition.setCode(competition.getCode());
                    }
                    if (competition.getScore() != null) {
                        existingCompetition.setScore(competition.getScore());
                    }
                    if (competition.getCreatedBy() != null) {
                        existingCompetition.setCreatedBy(competition.getCreatedBy());
                    }
                    if (competition.getCreatedDate() != null) {
                        existingCompetition.setCreatedDate(competition.getCreatedDate());
                    }
                    if (competition.getDeleted() != null) {
                        existingCompetition.setDeleted(competition.getDeleted());
                    }
                    if (competition.getDeletedBy() != null) {
                        existingCompetition.setDeletedBy(competition.getDeletedBy());
                    }
                    if (competition.getDeletedDate() != null) {
                        existingCompetition.setDeletedDate(competition.getDeletedDate());
                    }

                    return existingCompetition;
                }
            )
            .map(competitionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competition.getId())
        );
    }

    /**
     * {@code GET  /competitions} : get all the competitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitions in body.
     */
    @GetMapping("/competitions")
    public List<Competition> getAllCompetitions() {
        log.debug("REST request to get all Competitions");
        return competitionRepository.findAll();
    }

    /**
     * {@code GET  /competitions/:id} : get the "id" competition.
     *
     * @param id the id of the competition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitions/{id}")
    public ResponseEntity<Competition> getCompetition(@PathVariable String id) {
        log.debug("REST request to get Competition : {}", id);
        Optional<Competition> competition = competitionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competition);
    }

    /**
     * {@code DELETE  /competitions/:id} : delete the "id" competition.
     *
     * @param id the id of the competition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitions/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable String id) {
        log.debug("REST request to delete Competition : {}", id);
        competitionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
