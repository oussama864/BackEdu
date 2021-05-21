package com.mycompany.myapp.web.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cloudyrock.mongock.driver.api.entry.ChangeEntry;
import com.github.cloudyrock.mongock.driver.api.entry.ChangeEntryService;
import com.mycompany.myapp.domain.Conte;
import com.mycompany.myapp.repository.ConteRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.sun.mail.iap.Response;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.ServletContext;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Conte}.
 */
@RestController
@RequestMapping("/api")
public class ConteResource {

    private final Logger log = LoggerFactory.getLogger(ConteResource.class);

    private static final String ENTITY_NAME = "conte";

    @Autowired
    ServletContext context;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConteRepository conteRepository;

    public ConteResource(ConteRepository conteRepository) {
        this.conteRepository = conteRepository;
    }

    /**
     * {@code POST  /contes} : Create a new conte.
     *
     * @param conte the conte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conte, or with status {@code 400 (Bad Request)} if the conte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    /**  5dmti  **/
    @PostMapping("/conte/ajout")
    public ResponseEntity<Conte> createConte(@RequestBody Conte conte) throws URISyntaxException {
        log.debug("REST request to save Conte : {}", conte);
        if (conte.getId() != null) {
            throw new BadRequestAlertException("A new conte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conte result = conteRepository.save(conte);
        return ResponseEntity
            .created(new URI("/api/contes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @PostMapping("/conte/ajouter_conte")
    public ResponseEntity<Response> ajouterconte(@RequestParam("file") MultipartFile file, @RequestParam("conte") String conte)
        throws JsonParseException, JsonMappingException, Exception {
        System.out.println("Ok .............");
        Conte conteToSave = new ObjectMapper().readValue(conte, Conte.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit) {
            new File(context.getRealPath("/Images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FileNameUtils.getBaseName(filename) + "." + FileNameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
        try {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        conteToSave.setImageUrl(newFileName);
        Conte art = conteRepository.save(conteToSave);
        if (art != null) {
            return new ResponseEntity<Response>(new Response(""), HttpStatus.OK);
        } else {
            return new ResponseEntity<Response>(new Response("Article not saved"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * {@code PUT  /contes/:id} : Updates an existing conte.
     *
     * @param id the id of the conte to save.
     * @param conte the conte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conte,
     * or with status {@code 400 (Bad Request)} if the conte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contes/{id}")
    public ResponseEntity<Conte> updateConte(@PathVariable(value = "id", required = false) final String id, @RequestBody Conte conte)
        throws URISyntaxException {
        log.debug("REST request to update Conte : {}, {}", id, conte);
        if (conte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Conte result = conteRepository.save(conte);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conte.getId()))
            .body(result);
    }

    /*tjib l'image */
    @GetMapping(path = "/ImageConte/{id}")
    public byte[] getPhoto(@PathVariable("id") String id) throws Exception {
        Conte conte = conteRepository.findById(id).get();
        log.debug(conte.toString() + "-------------------------------------------");
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/") + "/" + conte.getImageUrl()));
    }

    /**
     * {@code PATCH  /contes/:id} : Partial updates given fields of an existing conte, field will ignore if it is null
     *
     * @param id the id of the conte to save.
     * @param conte the conte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conte,
     * or with status {@code 400 (Bad Request)} if the conte is not valid,
     * or with status {@code 404 (Not Found)} if the conte is not found,
     * or with status {@code 500 (Internal Server Error)} if the conte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Conte> partialUpdateConte(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Conte conte
    ) throws URISyntaxException {
        log.debug("REST request to partial update Conte partially : {}, {}", id, conte);
        if (conte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Conte> result = conteRepository
            .findById(conte.getId())
            .map(
                existingConte -> {
                    if (conte.getNom() != null) {
                        existingConte.setNom(conte.getNom());
                    }
                    if (conte.getType() != null) {
                        existingConte.setType(conte.getType());
                    }
                    if (conte.getDescription() != null) {
                        existingConte.setDescription(conte.getDescription());
                    }
                    if (conte.getPrix() != null) {
                        existingConte.setPrix(conte.getPrix());
                    }
                    if (conte.getLanguage() != null) {
                        existingConte.setLanguage(conte.getLanguage());
                    }
                    if (conte.getImageUrl() != null) {
                        existingConte.setImageUrl(conte.getImageUrl());
                    }
                    if (conte.getTitre() != null) {
                        existingConte.setTitre(conte.getTitre());
                    }
                    if (conte.getNbPage() != null) {
                        existingConte.setNbPage(conte.getNbPage());
                    }
                    if (conte.getMaisonEdition() != null) {
                        existingConte.setMaisonEdition(conte.getMaisonEdition());
                    }
                    if (conte.getCreatedBy() != null) {
                        existingConte.setCreatedBy(conte.getCreatedBy());
                    }
                    if (conte.getCreatedDate() != null) {
                        existingConte.setCreatedDate(conte.getCreatedDate());
                    }
                    if (conte.getDeleted() != null) {
                        existingConte.setDeleted(conte.getDeleted());
                    }
                    if (conte.getDeletedBy() != null) {
                        existingConte.setDeletedBy(conte.getDeletedBy());
                    }
                    if (conte.getDeletedDate() != null) {
                        existingConte.setDeletedDate(conte.getDeletedDate());
                    }

                    return existingConte;
                }
            )
            .map(conteRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conte.getId()));
    }

    /**
     * {@code GET  /contes} : get all the contes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contes in body.
     */
    @GetMapping("/contes")
    public List<Conte> getAllContes() {
        log.debug("REST request to get all Contes");
        return conteRepository.findAll();
    }

    /* jeb les contes bel auteur kol wa7da*/
    @GetMapping("/conte/all-contes/{emailAuteur}")
    public List<Conte> getAllContesByAuteur(@PathVariable String emailAuteur) {
        log.debug("REST request to get all Contes");
        return conteRepository.findByEmailAuteur(emailAuteur);
    }

    /**
     * {@code GET  /contes/:id} : get the "id" conte.
     *
     * @param id the id of the conte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contes/{id}")
    public ResponseEntity<Conte> getConte(@PathVariable String id) {
        log.debug("REST request to get Conte : {}", id);
        Optional<Conte> conte = conteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conte);
    }

    /**
     * {@code DELETE  /contes/:id} : delete the "id" conte.
     *
     * @param id the id of the conte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contes/{id}")
    public ResponseEntity<Void> deleteConte(@PathVariable String id) {
        log.debug("REST request to delete Conte : {}", id);
        conteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
