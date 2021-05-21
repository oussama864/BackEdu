package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Auteur;
import com.mycompany.myapp.repository.AuteurRepository;
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
 * Integration tests for the {@link AuteurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuteurResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_REF_USER = "AAAAAAAAAA";
    private static final String UPDATED_REF_USER = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/auteurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AuteurRepository auteurRepository;

    @Autowired
    private MockMvc restAuteurMockMvc;

    private Auteur auteur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auteur createEntity() {
        Auteur auteur = new Auteur()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .refUser(DEFAULT_REF_USER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .deleted(DEFAULT_DELETED)
            .deletedBy(DEFAULT_DELETED_BY)
            .deletedDate(DEFAULT_DELETED_DATE);
        return auteur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auteur createUpdatedEntity() {
        Auteur auteur = new Auteur()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .refUser(UPDATED_REF_USER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);
        return auteur;
    }

    @BeforeEach
    public void initTest() {
        auteurRepository.deleteAll();
        auteur = createEntity();
    }

    @Test
    void createAuteur() throws Exception {
        int databaseSizeBeforeCreate = auteurRepository.findAll().size();
        // Create the Auteur
        restAuteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auteur)))
            .andExpect(status().isCreated());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeCreate + 1);
        Auteur testAuteur = auteurList.get(auteurList.size() - 1);
        assertThat(testAuteur.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAuteur.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAuteur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAuteur.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testAuteur.getRefUser()).isEqualTo(DEFAULT_REF_USER);
        assertThat(testAuteur.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAuteur.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAuteur.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testAuteur.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testAuteur.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void createAuteurWithExistingId() throws Exception {
        // Create the Auteur with an existing ID
        auteur.setId("existing_id");

        int databaseSizeBeforeCreate = auteurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auteur)))
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAuteurs() throws Exception {
        // Initialize the database
        auteurRepository.save(auteur);

        // Get all the auteurList
        restAuteurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auteur.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].refUser").value(hasItem(DEFAULT_REF_USER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    void getAuteur() throws Exception {
        // Initialize the database
        auteurRepository.save(auteur);

        // Get the auteur
        restAuteurMockMvc
            .perform(get(ENTITY_API_URL_ID, auteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auteur.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.refUser").value(DEFAULT_REF_USER))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getNonExistingAuteur() throws Exception {
        // Get the auteur
        restAuteurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewAuteur() throws Exception {
        // Initialize the database
        auteurRepository.save(auteur);

        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();

        // Update the auteur
        Auteur updatedAuteur = auteurRepository.findById(auteur.getId()).get();
        updatedAuteur
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .refUser(UPDATED_REF_USER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restAuteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAuteur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAuteur))
            )
            .andExpect(status().isOk());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeUpdate);
        Auteur testAuteur = auteurList.get(auteurList.size() - 1);
        assertThat(testAuteur.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAuteur.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAuteur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAuteur.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAuteur.getRefUser()).isEqualTo(UPDATED_REF_USER);
        assertThat(testAuteur.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuteur.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAuteur.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testAuteur.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testAuteur.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void putNonExistingAuteur() throws Exception {
        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();
        auteur.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auteur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAuteur() throws Exception {
        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();
        auteur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAuteur() throws Exception {
        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();
        auteur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auteur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAuteurWithPatch() throws Exception {
        // Initialize the database
        auteurRepository.save(auteur);

        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();

        // Update the auteur using partial update
        Auteur partialUpdatedAuteur = new Auteur();
        partialUpdatedAuteur.setId(auteur.getId());

        partialUpdatedAuteur
            .firstName(UPDATED_FIRST_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED);

        restAuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuteur))
            )
            .andExpect(status().isOk());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeUpdate);
        Auteur testAuteur = auteurList.get(auteurList.size() - 1);
        assertThat(testAuteur.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAuteur.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAuteur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAuteur.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAuteur.getRefUser()).isEqualTo(DEFAULT_REF_USER);
        assertThat(testAuteur.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuteur.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAuteur.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testAuteur.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testAuteur.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void fullUpdateAuteurWithPatch() throws Exception {
        // Initialize the database
        auteurRepository.save(auteur);

        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();

        // Update the auteur using partial update
        Auteur partialUpdatedAuteur = new Auteur();
        partialUpdatedAuteur.setId(auteur.getId());

        partialUpdatedAuteur
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .refUser(UPDATED_REF_USER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restAuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuteur))
            )
            .andExpect(status().isOk());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeUpdate);
        Auteur testAuteur = auteurList.get(auteurList.size() - 1);
        assertThat(testAuteur.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAuteur.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAuteur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAuteur.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAuteur.getRefUser()).isEqualTo(UPDATED_REF_USER);
        assertThat(testAuteur.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuteur.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAuteur.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testAuteur.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testAuteur.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void patchNonExistingAuteur() throws Exception {
        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();
        auteur.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAuteur() throws Exception {
        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();
        auteur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAuteur() throws Exception {
        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();
        auteur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(auteur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auteur in the database
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAuteur() throws Exception {
        // Initialize the database
        auteurRepository.save(auteur);

        int databaseSizeBeforeDelete = auteurRepository.findAll().size();

        // Delete the auteur
        restAuteurMockMvc
            .perform(delete(ENTITY_API_URL_ID, auteur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Auteur> auteurList = auteurRepository.findAll();
        assertThat(auteurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
