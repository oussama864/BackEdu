package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Ecole;
import com.mycompany.myapp.repository.EcoleRepository;
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
 * Integration tests for the {@link EcoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EcoleResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_LISTE_CLASSES = "AAAAAAAAAA";
    private static final String UPDATED_LISTE_CLASSES = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/ecoles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EcoleRepository ecoleRepository;

    @Autowired
    private MockMvc restEcoleMockMvc;

    private Ecole ecole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecole createEntity() {
        Ecole ecole = new Ecole()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .email(DEFAULT_EMAIL)
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .listeClasses(DEFAULT_LISTE_CLASSES)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .deleted(DEFAULT_DELETED)
            .deletedBy(DEFAULT_DELETED_BY)
            .deletedDate(DEFAULT_DELETED_DATE);
        return ecole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecole createUpdatedEntity() {
        Ecole ecole = new Ecole()
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .listeClasses(UPDATED_LISTE_CLASSES)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);
        return ecole;
    }

    @BeforeEach
    public void initTest() {
        ecoleRepository.deleteAll();
        ecole = createEntity();
    }

    @Test
    void createEcole() throws Exception {
        int databaseSizeBeforeCreate = ecoleRepository.findAll().size();
        // Create the Ecole
        restEcoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecole)))
            .andExpect(status().isCreated());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeCreate + 1);
        Ecole testEcole = ecoleList.get(ecoleList.size() - 1);
        assertThat(testEcole.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEcole.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEcole.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEcole.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testEcole.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testEcole.getListeClasses()).isEqualTo(DEFAULT_LISTE_CLASSES);
        assertThat(testEcole.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEcole.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEcole.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testEcole.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testEcole.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void createEcoleWithExistingId() throws Exception {
        // Create the Ecole with an existing ID
        ecole.setId("existing_id");

        int databaseSizeBeforeCreate = ecoleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecole)))
            .andExpect(status().isBadRequest());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEcoles() throws Exception {
        // Initialize the database
        ecoleRepository.save(ecole);

        // Get all the ecoleList
        restEcoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecole.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].listeClasses").value(hasItem(DEFAULT_LISTE_CLASSES)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    void getEcole() throws Exception {
        // Initialize the database
        ecoleRepository.save(ecole);

        // Get the ecole
        restEcoleMockMvc
            .perform(get(ENTITY_API_URL_ID, ecole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecole.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.listeClasses").value(DEFAULT_LISTE_CLASSES))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getNonExistingEcole() throws Exception {
        // Get the ecole
        restEcoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewEcole() throws Exception {
        // Initialize the database
        ecoleRepository.save(ecole);

        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();

        // Update the ecole
        Ecole updatedEcole = ecoleRepository.findById(ecole.getId()).get();
        updatedEcole
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .listeClasses(UPDATED_LISTE_CLASSES)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restEcoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEcole))
            )
            .andExpect(status().isOk());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
        Ecole testEcole = ecoleList.get(ecoleList.size() - 1);
        assertThat(testEcole.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEcole.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEcole.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEcole.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testEcole.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testEcole.getListeClasses()).isEqualTo(UPDATED_LISTE_CLASSES);
        assertThat(testEcole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEcole.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEcole.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testEcole.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testEcole.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void putNonExistingEcole() throws Exception {
        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();
        ecole.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ecole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecole))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEcole() throws Exception {
        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();
        ecole.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ecole))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEcole() throws Exception {
        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();
        ecole.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcoleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ecole)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEcoleWithPatch() throws Exception {
        // Initialize the database
        ecoleRepository.save(ecole);

        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();

        // Update the ecole using partial update
        Ecole partialUpdatedEcole = new Ecole();
        partialUpdatedEcole.setId(ecole.getId());

        partialUpdatedEcole
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .login(UPDATED_LOGIN)
            .listeClasses(UPDATED_LISTE_CLASSES)
            .createdBy(UPDATED_CREATED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restEcoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcole))
            )
            .andExpect(status().isOk());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
        Ecole testEcole = ecoleList.get(ecoleList.size() - 1);
        assertThat(testEcole.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEcole.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEcole.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEcole.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testEcole.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testEcole.getListeClasses()).isEqualTo(UPDATED_LISTE_CLASSES);
        assertThat(testEcole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEcole.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEcole.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testEcole.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testEcole.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void fullUpdateEcoleWithPatch() throws Exception {
        // Initialize the database
        ecoleRepository.save(ecole);

        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();

        // Update the ecole using partial update
        Ecole partialUpdatedEcole = new Ecole();
        partialUpdatedEcole.setId(ecole.getId());

        partialUpdatedEcole
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .listeClasses(UPDATED_LISTE_CLASSES)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restEcoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcole))
            )
            .andExpect(status().isOk());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
        Ecole testEcole = ecoleList.get(ecoleList.size() - 1);
        assertThat(testEcole.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEcole.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEcole.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEcole.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testEcole.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testEcole.getListeClasses()).isEqualTo(UPDATED_LISTE_CLASSES);
        assertThat(testEcole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEcole.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEcole.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testEcole.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testEcole.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void patchNonExistingEcole() throws Exception {
        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();
        ecole.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecole))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEcole() throws Exception {
        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();
        ecole.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ecole))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEcole() throws Exception {
        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();
        ecole.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcoleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ecole)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEcole() throws Exception {
        // Initialize the database
        ecoleRepository.save(ecole);

        int databaseSizeBeforeDelete = ecoleRepository.findAll().size();

        // Delete the ecole
        restEcoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecole.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
