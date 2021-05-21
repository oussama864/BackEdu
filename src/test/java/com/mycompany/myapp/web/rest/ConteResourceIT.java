package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Conte;
import com.mycompany.myapp.repository.ConteRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ConteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIX = 1L;
    private static final Long UPDATED_PRIX = 2L;

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_PAGE = 1;
    private static final Integer UPDATED_NB_PAGE = 2;

    private static final String DEFAULT_MAISON_EDITION = "AAAAAAAAAA";
    private static final String UPDATED_MAISON_EDITION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String DEFAULT_DELETED_BY = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DELETED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELETED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/contes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ConteRepository conteRepository;

    @Autowired
    private MockMvc restConteMockMvc;

    private Conte conte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conte createEntity() {
        Conte conte = new Conte()
            .nom(DEFAULT_NOM)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .prix(DEFAULT_PRIX)
            .language(DEFAULT_LANGUAGE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .titre(DEFAULT_TITRE)
            .nbPage(DEFAULT_NB_PAGE)
            .maisonEdition(DEFAULT_MAISON_EDITION)
            .createdBy(DEFAULT_CREATED_BY)
            .deleted(DEFAULT_DELETED)
            .deletedBy(DEFAULT_DELETED_BY);
        return conte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conte createUpdatedEntity() {
        Conte conte = new Conte()
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .language(UPDATED_LANGUAGE)
            .imageUrl(UPDATED_IMAGE_URL)
            .titre(UPDATED_TITRE)
            .nbPage(UPDATED_NB_PAGE)
            .maisonEdition(UPDATED_MAISON_EDITION)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY);
        return conte;
    }

    @BeforeEach
    public void initTest() {
        conteRepository.deleteAll();
        conte = createEntity();
    }

    @Test
    void createConte() throws Exception {
        int databaseSizeBeforeCreate = conteRepository.findAll().size();
        // Create the Conte
        restConteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conte)))
            .andExpect(status().isCreated());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeCreate + 1);
        Conte testConte = conteList.get(conteList.size() - 1);
        assertThat(testConte.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testConte.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testConte.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConte.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testConte.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testConte.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testConte.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testConte.getNbPage()).isEqualTo(DEFAULT_NB_PAGE);
        assertThat(testConte.getMaisonEdition()).isEqualTo(DEFAULT_MAISON_EDITION);
        assertThat(testConte.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testConte.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConte.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testConte.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testConte.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void createConteWithExistingId() throws Exception {
        // Create the Conte with an existing ID
        conte.setId("existing_id");

        int databaseSizeBeforeCreate = conteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conte)))
            .andExpect(status().isBadRequest());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllContes() throws Exception {
        // Initialize the database
        conteRepository.save(conte);

        // Get all the conteList
        restConteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conte.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].nbPage").value(hasItem(DEFAULT_NB_PAGE)))
            .andExpect(jsonPath("$.[*].maisonEdition").value(hasItem(DEFAULT_MAISON_EDITION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    void getConte() throws Exception {
        // Initialize the database
        conteRepository.save(conte);

        // Get the conte
        restConteMockMvc
            .perform(get(ENTITY_API_URL_ID, conte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conte.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.nbPage").value(DEFAULT_NB_PAGE))
            .andExpect(jsonPath("$.maisonEdition").value(DEFAULT_MAISON_EDITION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getNonExistingConte() throws Exception {
        // Get the conte
        restConteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewConte() throws Exception {
        // Initialize the database
        conteRepository.save(conte);

        int databaseSizeBeforeUpdate = conteRepository.findAll().size();

        // Update the conte
        Conte updatedConte = conteRepository.findById(conte.getId()).get();
        updatedConte
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .language(UPDATED_LANGUAGE)
            .imageUrl(UPDATED_IMAGE_URL)
            .titre(UPDATED_TITRE)
            .nbPage(UPDATED_NB_PAGE)
            .maisonEdition(UPDATED_MAISON_EDITION)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY);
        restConteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConte))
            )
            .andExpect(status().isOk());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeUpdate);
        Conte testConte = conteList.get(conteList.size() - 1);
        assertThat(testConte.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testConte.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConte.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConte.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testConte.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testConte.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testConte.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testConte.getNbPage()).isEqualTo(UPDATED_NB_PAGE);
        assertThat(testConte.getMaisonEdition()).isEqualTo(UPDATED_MAISON_EDITION);
        assertThat(testConte.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testConte.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConte.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testConte.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testConte.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void putNonExistingConte() throws Exception {
        int databaseSizeBeforeUpdate = conteRepository.findAll().size();
        conte.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchConte() throws Exception {
        int databaseSizeBeforeUpdate = conteRepository.findAll().size();
        conte.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamConte() throws Exception {
        int databaseSizeBeforeUpdate = conteRepository.findAll().size();
        conte.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConteWithPatch() throws Exception {
        // Initialize the database
        conteRepository.save(conte);

        int databaseSizeBeforeUpdate = conteRepository.findAll().size();

        // Update the conte using partial update
        Conte partialUpdatedConte = new Conte();
        partialUpdatedConte.setId(conte.getId());

        partialUpdatedConte
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .language(UPDATED_LANGUAGE)
            .imageUrl(UPDATED_IMAGE_URL)
            .titre(UPDATED_TITRE)
            .nbPage(UPDATED_NB_PAGE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY);

        restConteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConte))
            )
            .andExpect(status().isOk());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeUpdate);
        Conte testConte = conteList.get(conteList.size() - 1);
        assertThat(testConte.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testConte.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testConte.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConte.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testConte.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testConte.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testConte.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testConte.getNbPage()).isEqualTo(UPDATED_NB_PAGE);
        assertThat(testConte.getMaisonEdition()).isEqualTo(DEFAULT_MAISON_EDITION);
        assertThat(testConte.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testConte.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConte.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testConte.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testConte.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void fullUpdateConteWithPatch() throws Exception {
        // Initialize the database
        conteRepository.save(conte);

        int databaseSizeBeforeUpdate = conteRepository.findAll().size();

        // Update the conte using partial update
        Conte partialUpdatedConte = new Conte();
        partialUpdatedConte.setId(conte.getId());

        partialUpdatedConte
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .language(UPDATED_LANGUAGE)
            .imageUrl(UPDATED_IMAGE_URL)
            .titre(UPDATED_TITRE)
            .nbPage(UPDATED_NB_PAGE)
            .maisonEdition(UPDATED_MAISON_EDITION)
            .createdBy(UPDATED_CREATED_BY)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY);

        restConteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConte))
            )
            .andExpect(status().isOk());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeUpdate);
        Conte testConte = conteList.get(conteList.size() - 1);
        assertThat(testConte.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testConte.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConte.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConte.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testConte.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testConte.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testConte.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testConte.getNbPage()).isEqualTo(UPDATED_NB_PAGE);
        assertThat(testConte.getMaisonEdition()).isEqualTo(UPDATED_MAISON_EDITION);
        assertThat(testConte.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testConte.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConte.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testConte.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testConte.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void patchNonExistingConte() throws Exception {
        int databaseSizeBeforeUpdate = conteRepository.findAll().size();
        conte.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchConte() throws Exception {
        int databaseSizeBeforeUpdate = conteRepository.findAll().size();
        conte.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamConte() throws Exception {
        int databaseSizeBeforeUpdate = conteRepository.findAll().size();
        conte.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(conte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conte in the database
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteConte() throws Exception {
        // Initialize the database
        conteRepository.save(conte);

        int databaseSizeBeforeDelete = conteRepository.findAll().size();

        // Delete the conte
        restConteMockMvc
            .perform(delete(ENTITY_API_URL_ID, conte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conte> conteList = conteRepository.findAll();
        assertThat(conteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
