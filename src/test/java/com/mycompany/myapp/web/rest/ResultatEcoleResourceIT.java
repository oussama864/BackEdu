package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ResultatEcole;
import com.mycompany.myapp.repository.ResultatEcoleRepository;
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
 * Integration tests for the {@link ResultatEcoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResultatEcoleResourceIT {

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

    private static final String ENTITY_API_URL = "/api/resultat-ecoles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ResultatEcoleRepository resultatEcoleRepository;

    @Autowired
    private MockMvc restResultatEcoleMockMvc;

    private ResultatEcole resultatEcole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultatEcole createEntity() {
        ResultatEcole resultatEcole = new ResultatEcole()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .deleted(DEFAULT_DELETED)
            .deletedBy(DEFAULT_DELETED_BY)
            .deletedDate(DEFAULT_DELETED_DATE);
        return resultatEcole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultatEcole createUpdatedEntity() {
        ResultatEcole resultatEcole = new ResultatEcole()
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);
        return resultatEcole;
    }

    @BeforeEach
    public void initTest() {
        resultatEcoleRepository.deleteAll();
        resultatEcole = createEntity();
    }

    @Test
    void createResultatEcole() throws Exception {
        int databaseSizeBeforeCreate = resultatEcoleRepository.findAll().size();
        // Create the ResultatEcole
        restResultatEcoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultatEcole)))
            .andExpect(status().isCreated());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeCreate + 1);
        ResultatEcole testResultatEcole = resultatEcoleList.get(resultatEcoleList.size() - 1);
        assertThat(testResultatEcole.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testResultatEcole.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testResultatEcole.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testResultatEcole.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testResultatEcole.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void createResultatEcoleWithExistingId() throws Exception {
        // Create the ResultatEcole with an existing ID
        resultatEcole.setId("existing_id");

        int databaseSizeBeforeCreate = resultatEcoleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultatEcoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultatEcole)))
            .andExpect(status().isBadRequest());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllResultatEcoles() throws Exception {
        // Initialize the database
        resultatEcoleRepository.save(resultatEcole);

        // Get all the resultatEcoleList
        restResultatEcoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultatEcole.getId())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    void getResultatEcole() throws Exception {
        // Initialize the database
        resultatEcoleRepository.save(resultatEcole);

        // Get the resultatEcole
        restResultatEcoleMockMvc
            .perform(get(ENTITY_API_URL_ID, resultatEcole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultatEcole.getId()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getNonExistingResultatEcole() throws Exception {
        // Get the resultatEcole
        restResultatEcoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewResultatEcole() throws Exception {
        // Initialize the database
        resultatEcoleRepository.save(resultatEcole);

        int databaseSizeBeforeUpdate = resultatEcoleRepository.findAll().size();

        // Update the resultatEcole
        ResultatEcole updatedResultatEcole = resultatEcoleRepository.findById(resultatEcole.getId()).get();
        updatedResultatEcole
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restResultatEcoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResultatEcole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResultatEcole))
            )
            .andExpect(status().isOk());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeUpdate);
        ResultatEcole testResultatEcole = resultatEcoleList.get(resultatEcoleList.size() - 1);
        assertThat(testResultatEcole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResultatEcole.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testResultatEcole.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testResultatEcole.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testResultatEcole.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void putNonExistingResultatEcole() throws Exception {
        int databaseSizeBeforeUpdate = resultatEcoleRepository.findAll().size();
        resultatEcole.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatEcoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultatEcole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultatEcole))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchResultatEcole() throws Exception {
        int databaseSizeBeforeUpdate = resultatEcoleRepository.findAll().size();
        resultatEcole.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultatEcoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultatEcole))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamResultatEcole() throws Exception {
        int databaseSizeBeforeUpdate = resultatEcoleRepository.findAll().size();
        resultatEcole.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultatEcoleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultatEcole)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateResultatEcoleWithPatch() throws Exception {
        // Initialize the database
        resultatEcoleRepository.save(resultatEcole);

        int databaseSizeBeforeUpdate = resultatEcoleRepository.findAll().size();

        // Update the resultatEcole using partial update
        ResultatEcole partialUpdatedResultatEcole = new ResultatEcole();
        partialUpdatedResultatEcole.setId(resultatEcole.getId());

        partialUpdatedResultatEcole
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restResultatEcoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultatEcole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResultatEcole))
            )
            .andExpect(status().isOk());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeUpdate);
        ResultatEcole testResultatEcole = resultatEcoleList.get(resultatEcoleList.size() - 1);
        assertThat(testResultatEcole.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testResultatEcole.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testResultatEcole.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testResultatEcole.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testResultatEcole.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void fullUpdateResultatEcoleWithPatch() throws Exception {
        // Initialize the database
        resultatEcoleRepository.save(resultatEcole);

        int databaseSizeBeforeUpdate = resultatEcoleRepository.findAll().size();

        // Update the resultatEcole using partial update
        ResultatEcole partialUpdatedResultatEcole = new ResultatEcole();
        partialUpdatedResultatEcole.setId(resultatEcole.getId());

        partialUpdatedResultatEcole
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restResultatEcoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultatEcole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResultatEcole))
            )
            .andExpect(status().isOk());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeUpdate);
        ResultatEcole testResultatEcole = resultatEcoleList.get(resultatEcoleList.size() - 1);
        assertThat(testResultatEcole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResultatEcole.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testResultatEcole.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testResultatEcole.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testResultatEcole.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void patchNonExistingResultatEcole() throws Exception {
        int databaseSizeBeforeUpdate = resultatEcoleRepository.findAll().size();
        resultatEcole.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatEcoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resultatEcole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultatEcole))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchResultatEcole() throws Exception {
        int databaseSizeBeforeUpdate = resultatEcoleRepository.findAll().size();
        resultatEcole.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultatEcoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultatEcole))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamResultatEcole() throws Exception {
        int databaseSizeBeforeUpdate = resultatEcoleRepository.findAll().size();
        resultatEcole.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultatEcoleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resultatEcole))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResultatEcole in the database
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteResultatEcole() throws Exception {
        // Initialize the database
        resultatEcoleRepository.save(resultatEcole);

        int databaseSizeBeforeDelete = resultatEcoleRepository.findAll().size();

        // Delete the resultatEcole
        restResultatEcoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, resultatEcole.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultatEcole> resultatEcoleList = resultatEcoleRepository.findAll();
        assertThat(resultatEcoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
