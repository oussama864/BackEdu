package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Ecolier;
import com.mycompany.myapp.repository.EcolierRepository;
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
 * Integration tests for the {@link EcolierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EcolierResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_REF_USER = "AAAAAAAAAA";
    private static final String UPDATED_REF_USER = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_NIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_NIVEAU = "BBBBBBBBBB";

    private static final String DEFAULT_ECOLE = "AAAAAAAAAA";
    private static final String UPDATED_ECOLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOM_PARENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PARENT = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/ecoliers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EcolierRepository ecolierRepository;

    @Autowired
    private MockMvc restEcolierMockMvc;

    private Ecolier ecolier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecolier createEntity() {
        Ecolier ecolier = new Ecolier()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .refUser(DEFAULT_REF_USER)
            .age(DEFAULT_AGE)
            .niveau(DEFAULT_NIVEAU)
            .ecole(DEFAULT_ECOLE)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
            .nomParent(DEFAULT_NOM_PARENT)
            .password(DEFAULT_PASSWORD)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .deleted(DEFAULT_DELETED)
            .deletedBy(DEFAULT_DELETED_BY)
            .deletedDate(DEFAULT_DELETED_DATE);
        return ecolier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecolier createUpdatedEntity() {
        Ecolier ecolier = new Ecolier()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .refUser(UPDATED_REF_USER)
            .age(UPDATED_AGE)
            .niveau(UPDATED_NIVEAU)
            .ecole(UPDATED_ECOLE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .nomParent(UPDATED_NOM_PARENT)
            .password(UPDATED_PASSWORD)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);
        return ecolier;
    }

    @BeforeEach
    public void initTest() {
        ecolierRepository.deleteAll();
        ecolier = createEntity();
    }

    @Test
    void createEcolier() throws Exception {
        int databaseSizeBeforeCreate = ecolierRepository.findAll().size();
        // Create the Ecolier
        restEcolierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecolier)))
            .andExpect(status().isCreated());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeCreate + 1);
        Ecolier testEcolier = ecolierList.get(ecolierList.size() - 1);
        assertThat(testEcolier.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEcolier.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEcolier.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEcolier.getRefUser()).isEqualTo(DEFAULT_REF_USER);
        assertThat(testEcolier.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testEcolier.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testEcolier.getEcole()).isEqualTo(DEFAULT_ECOLE);
        assertThat(testEcolier.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testEcolier.getNomParent()).isEqualTo(DEFAULT_NOM_PARENT);
        assertThat(testEcolier.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testEcolier.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEcolier.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEcolier.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testEcolier.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testEcolier.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void createEcolierWithExistingId() throws Exception {
        // Create the Ecolier with an existing ID
        ecolier.setId("existing_id");

        int databaseSizeBeforeCreate = ecolierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcolierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecolier)))
            .andExpect(status().isBadRequest());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEcoliers() throws Exception {
        // Initialize the database
        ecolierRepository.save(ecolier);

        // Get all the ecolierList
        restEcolierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecolier.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].refUser").value(hasItem(DEFAULT_REF_USER)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)))
            .andExpect(jsonPath("$.[*].ecole").value(hasItem(DEFAULT_ECOLE)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].nomParent").value(hasItem(DEFAULT_NOM_PARENT)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    void getEcolier() throws Exception {
        // Initialize the database
        ecolierRepository.save(ecolier);

        // Get the ecolier
        restEcolierMockMvc
            .perform(get(ENTITY_API_URL_ID, ecolier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecolier.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.refUser").value(DEFAULT_REF_USER))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU))
            .andExpect(jsonPath("$.ecole").value(DEFAULT_ECOLE))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.nomParent").value(DEFAULT_NOM_PARENT))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getNonExistingEcolier() throws Exception {
        // Get the ecolier
        restEcolierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewEcolier() throws Exception {
        // Initialize the database
        ecolierRepository.save(ecolier);

        int databaseSizeBeforeUpdate = ecolierRepository.findAll().size();

        // Update the ecolier
        Ecolier updatedEcolier = ecolierRepository.findById(ecolier.getId()).get();
        updatedEcolier
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .refUser(UPDATED_REF_USER)
            .age(UPDATED_AGE)
            .niveau(UPDATED_NIVEAU)
            .ecole(UPDATED_ECOLE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .nomParent(UPDATED_NOM_PARENT)
            .password(UPDATED_PASSWORD)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restEcolierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcolier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEcolier))
            )
            .andExpect(status().isOk());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeUpdate);
        Ecolier testEcolier = ecolierList.get(ecolierList.size() - 1);
        assertThat(testEcolier.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEcolier.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEcolier.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEcolier.getRefUser()).isEqualTo(UPDATED_REF_USER);
        assertThat(testEcolier.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEcolier.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testEcolier.getEcole()).isEqualTo(UPDATED_ECOLE);
        assertThat(testEcolier.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testEcolier.getNomParent()).isEqualTo(UPDATED_NOM_PARENT);
        assertThat(testEcolier.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testEcolier.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEcolier.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEcolier.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testEcolier.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testEcolier.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void putNonExistingEcolier() throws Exception {
        int databaseSizeBeforeUpdate = ecolierRepository.findAll().size();
        ecolier.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcolierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ecolier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecolier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEcolier() throws Exception {
        int databaseSizeBeforeUpdate = ecolierRepository.findAll().size();
        ecolier.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcolierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecolier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEcolier() throws Exception {
        int databaseSizeBeforeUpdate = ecolierRepository.findAll().size();
        ecolier.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcolierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecolier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEcolierWithPatch() throws Exception {
        // Initialize the database
        ecolierRepository.save(ecolier);

        int databaseSizeBeforeUpdate = ecolierRepository.findAll().size();

        // Update the ecolier using partial update
        Ecolier partialUpdatedEcolier = new Ecolier();
        partialUpdatedEcolier.setId(ecolier.getId());

        partialUpdatedEcolier
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .refUser(UPDATED_REF_USER)
            .age(UPDATED_AGE)
            .ecole(UPDATED_ECOLE);

        restEcolierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcolier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcolier))
            )
            .andExpect(status().isOk());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeUpdate);
        Ecolier testEcolier = ecolierList.get(ecolierList.size() - 1);
        assertThat(testEcolier.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEcolier.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEcolier.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEcolier.getRefUser()).isEqualTo(UPDATED_REF_USER);
        assertThat(testEcolier.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEcolier.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testEcolier.getEcole()).isEqualTo(UPDATED_ECOLE);
        assertThat(testEcolier.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testEcolier.getNomParent()).isEqualTo(DEFAULT_NOM_PARENT);
        assertThat(testEcolier.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testEcolier.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEcolier.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEcolier.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testEcolier.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testEcolier.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void fullUpdateEcolierWithPatch() throws Exception {
        // Initialize the database
        ecolierRepository.save(ecolier);

        int databaseSizeBeforeUpdate = ecolierRepository.findAll().size();

        // Update the ecolier using partial update
        Ecolier partialUpdatedEcolier = new Ecolier();
        partialUpdatedEcolier.setId(ecolier.getId());

        partialUpdatedEcolier
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .refUser(UPDATED_REF_USER)
            .age(UPDATED_AGE)
            .niveau(UPDATED_NIVEAU)
            .ecole(UPDATED_ECOLE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .nomParent(UPDATED_NOM_PARENT)
            .password(UPDATED_PASSWORD)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restEcolierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcolier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcolier))
            )
            .andExpect(status().isOk());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeUpdate);
        Ecolier testEcolier = ecolierList.get(ecolierList.size() - 1);
        assertThat(testEcolier.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEcolier.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEcolier.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEcolier.getRefUser()).isEqualTo(UPDATED_REF_USER);
        assertThat(testEcolier.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEcolier.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testEcolier.getEcole()).isEqualTo(UPDATED_ECOLE);
        assertThat(testEcolier.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testEcolier.getNomParent()).isEqualTo(UPDATED_NOM_PARENT);
        assertThat(testEcolier.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testEcolier.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEcolier.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEcolier.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testEcolier.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testEcolier.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void patchNonExistingEcolier() throws Exception {
        int databaseSizeBeforeUpdate = ecolierRepository.findAll().size();
        ecolier.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcolierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecolier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecolier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEcolier() throws Exception {
        int databaseSizeBeforeUpdate = ecolierRepository.findAll().size();
        ecolier.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcolierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecolier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEcolier() throws Exception {
        int databaseSizeBeforeUpdate = ecolierRepository.findAll().size();
        ecolier.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcolierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ecolier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecolier in the database
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEcolier() throws Exception {
        // Initialize the database
        ecolierRepository.save(ecolier);

        int databaseSizeBeforeDelete = ecolierRepository.findAll().size();

        // Delete the ecolier
        restEcolierMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecolier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ecolier> ecolierList = ecolierRepository.findAll();
        assertThat(ecolierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
