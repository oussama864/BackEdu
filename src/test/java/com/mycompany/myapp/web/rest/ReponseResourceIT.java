package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Reponse;
import com.mycompany.myapp.repository.ReponseRepository;
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
 * Integration tests for the {@link ReponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReponseResourceIT {

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

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

    private static final String ENTITY_API_URL = "/api/reponses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ReponseRepository reponseRepository;

    @Autowired
    private MockMvc restReponseMockMvc;

    private Reponse reponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reponse createEntity() {
        Reponse reponse = new Reponse()
            .score(DEFAULT_SCORE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .deleted(DEFAULT_DELETED)
            .deletedBy(DEFAULT_DELETED_BY)
            .deletedDate(DEFAULT_DELETED_DATE);
        return reponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reponse createUpdatedEntity() {
        Reponse reponse = new Reponse()
            .score(UPDATED_SCORE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);
        return reponse;
    }

    @BeforeEach
    public void initTest() {
        reponseRepository.deleteAll();
        reponse = createEntity();
    }

    @Test
    void createReponse() throws Exception {
        int databaseSizeBeforeCreate = reponseRepository.findAll().size();
        // Create the Reponse
        restReponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reponse)))
            .andExpect(status().isCreated());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeCreate + 1);
        Reponse testReponse = reponseList.get(reponseList.size() - 1);
        assertThat(testReponse.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testReponse.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testReponse.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReponse.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testReponse.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testReponse.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void createReponseWithExistingId() throws Exception {
        // Create the Reponse with an existing ID
        reponse.setId("existing_id");

        int databaseSizeBeforeCreate = reponseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reponse)))
            .andExpect(status().isBadRequest());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllReponses() throws Exception {
        // Initialize the database
        reponseRepository.save(reponse);

        // Get all the reponseList
        restReponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reponse.getId())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    void getReponse() throws Exception {
        // Initialize the database
        reponseRepository.save(reponse);

        // Get the reponse
        restReponseMockMvc
            .perform(get(ENTITY_API_URL_ID, reponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reponse.getId()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getNonExistingReponse() throws Exception {
        // Get the reponse
        restReponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewReponse() throws Exception {
        // Initialize the database
        reponseRepository.save(reponse);

        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();

        // Update the reponse
        Reponse updatedReponse = reponseRepository.findById(reponse.getId()).get();
        updatedReponse
            .score(UPDATED_SCORE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restReponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReponse))
            )
            .andExpect(status().isOk());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
        Reponse testReponse = reponseList.get(reponseList.size() - 1);
        assertThat(testReponse.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testReponse.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testReponse.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReponse.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testReponse.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testReponse.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void putNonExistingReponse() throws Exception {
        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();
        reponse.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchReponse() throws Exception {
        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();
        reponse.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamReponse() throws Exception {
        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();
        reponse.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReponseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateReponseWithPatch() throws Exception {
        // Initialize the database
        reponseRepository.save(reponse);

        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();

        // Update the reponse using partial update
        Reponse partialUpdatedReponse = new Reponse();
        partialUpdatedReponse.setId(reponse.getId());

        partialUpdatedReponse.score(UPDATED_SCORE).createdBy(UPDATED_CREATED_BY).deletedDate(UPDATED_DELETED_DATE);

        restReponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReponse))
            )
            .andExpect(status().isOk());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
        Reponse testReponse = reponseList.get(reponseList.size() - 1);
        assertThat(testReponse.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testReponse.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testReponse.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReponse.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testReponse.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testReponse.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void fullUpdateReponseWithPatch() throws Exception {
        // Initialize the database
        reponseRepository.save(reponse);

        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();

        // Update the reponse using partial update
        Reponse partialUpdatedReponse = new Reponse();
        partialUpdatedReponse.setId(reponse.getId());

        partialUpdatedReponse
            .score(UPDATED_SCORE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restReponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReponse))
            )
            .andExpect(status().isOk());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
        Reponse testReponse = reponseList.get(reponseList.size() - 1);
        assertThat(testReponse.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testReponse.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testReponse.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReponse.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testReponse.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testReponse.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void patchNonExistingReponse() throws Exception {
        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();
        reponse.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchReponse() throws Exception {
        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();
        reponse.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamReponse() throws Exception {
        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();
        reponse.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReponseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(reponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteReponse() throws Exception {
        // Initialize the database
        reponseRepository.save(reponse);

        int databaseSizeBeforeDelete = reponseRepository.findAll().size();

        // Delete the reponse
        restReponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, reponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
