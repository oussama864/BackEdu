package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Qcm;
import com.mycompany.myapp.repository.QcmRepository;
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
 * Integration tests for the {@link QcmResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QcmResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_CHOIX_DISPO = "AAAAAAAAAA";
    private static final String UPDATED_CHOIX_DISPO = "BBBBBBBBBB";

    private static final String DEFAULT_CHOIX_CORRECT = "AAAAAAAAAA";
    private static final String UPDATED_CHOIX_CORRECT = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/qcms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private QcmRepository qcmRepository;

    @Autowired
    private MockMvc restQcmMockMvc;

    private Qcm qcm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qcm createEntity() {
        Qcm qcm = new Qcm()
            .question(DEFAULT_QUESTION)
            .choixDispo(DEFAULT_CHOIX_DISPO)
            .choixCorrect(DEFAULT_CHOIX_CORRECT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .deleted(DEFAULT_DELETED)
            .deletedBy(DEFAULT_DELETED_BY)
            .deletedDate(DEFAULT_DELETED_DATE);
        return qcm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qcm createUpdatedEntity() {
        Qcm qcm = new Qcm()
            .question(UPDATED_QUESTION)
            .choixDispo(UPDATED_CHOIX_DISPO)
            .choixCorrect(UPDATED_CHOIX_CORRECT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);
        return qcm;
    }

    @BeforeEach
    public void initTest() {
        qcmRepository.deleteAll();
        qcm = createEntity();
    }

    @Test
    void createQcm() throws Exception {
        int databaseSizeBeforeCreate = qcmRepository.findAll().size();
        // Create the Qcm
        restQcmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qcm)))
            .andExpect(status().isCreated());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeCreate + 1);
        Qcm testQcm = qcmList.get(qcmList.size() - 1);
        assertThat(testQcm.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQcm.getChoixDispo()).isEqualTo(DEFAULT_CHOIX_DISPO);
        assertThat(testQcm.getChoixCorrect()).isEqualTo(DEFAULT_CHOIX_CORRECT);
        assertThat(testQcm.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testQcm.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testQcm.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testQcm.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testQcm.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void createQcmWithExistingId() throws Exception {
        // Create the Qcm with an existing ID
        qcm.setId("existing_id");

        int databaseSizeBeforeCreate = qcmRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQcmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qcm)))
            .andExpect(status().isBadRequest());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllQcms() throws Exception {
        // Initialize the database
        qcmRepository.save(qcm);

        // Get all the qcmList
        restQcmMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qcm.getId())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].choixDispo").value(hasItem(DEFAULT_CHOIX_DISPO)))
            .andExpect(jsonPath("$.[*].choixCorrect").value(hasItem(DEFAULT_CHOIX_CORRECT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    void getQcm() throws Exception {
        // Initialize the database
        qcmRepository.save(qcm);

        // Get the qcm
        restQcmMockMvc
            .perform(get(ENTITY_API_URL_ID, qcm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qcm.getId()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.choixDispo").value(DEFAULT_CHOIX_DISPO))
            .andExpect(jsonPath("$.choixCorrect").value(DEFAULT_CHOIX_CORRECT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getNonExistingQcm() throws Exception {
        // Get the qcm
        restQcmMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewQcm() throws Exception {
        // Initialize the database
        qcmRepository.save(qcm);

        int databaseSizeBeforeUpdate = qcmRepository.findAll().size();

        // Update the qcm
        Qcm updatedQcm = qcmRepository.findById(qcm.getId()).get();
        updatedQcm
            .question(UPDATED_QUESTION)
            .choixDispo(UPDATED_CHOIX_DISPO)
            .choixCorrect(UPDATED_CHOIX_CORRECT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restQcmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQcm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQcm))
            )
            .andExpect(status().isOk());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeUpdate);
        Qcm testQcm = qcmList.get(qcmList.size() - 1);
        assertThat(testQcm.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQcm.getChoixDispo()).isEqualTo(UPDATED_CHOIX_DISPO);
        assertThat(testQcm.getChoixCorrect()).isEqualTo(UPDATED_CHOIX_CORRECT);
        assertThat(testQcm.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testQcm.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testQcm.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testQcm.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testQcm.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void putNonExistingQcm() throws Exception {
        int databaseSizeBeforeUpdate = qcmRepository.findAll().size();
        qcm.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQcmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qcm.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qcm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchQcm() throws Exception {
        int databaseSizeBeforeUpdate = qcmRepository.findAll().size();
        qcm.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qcm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamQcm() throws Exception {
        int databaseSizeBeforeUpdate = qcmRepository.findAll().size();
        qcm.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qcm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateQcmWithPatch() throws Exception {
        // Initialize the database
        qcmRepository.save(qcm);

        int databaseSizeBeforeUpdate = qcmRepository.findAll().size();

        // Update the qcm using partial update
        Qcm partialUpdatedQcm = new Qcm();
        partialUpdatedQcm.setId(qcm.getId());

        partialUpdatedQcm.choixDispo(UPDATED_CHOIX_DISPO).createdDate(UPDATED_CREATED_DATE).deletedDate(UPDATED_DELETED_DATE);

        restQcmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQcm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQcm))
            )
            .andExpect(status().isOk());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeUpdate);
        Qcm testQcm = qcmList.get(qcmList.size() - 1);
        assertThat(testQcm.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQcm.getChoixDispo()).isEqualTo(UPDATED_CHOIX_DISPO);
        assertThat(testQcm.getChoixCorrect()).isEqualTo(DEFAULT_CHOIX_CORRECT);
        assertThat(testQcm.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testQcm.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testQcm.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testQcm.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testQcm.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void fullUpdateQcmWithPatch() throws Exception {
        // Initialize the database
        qcmRepository.save(qcm);

        int databaseSizeBeforeUpdate = qcmRepository.findAll().size();

        // Update the qcm using partial update
        Qcm partialUpdatedQcm = new Qcm();
        partialUpdatedQcm.setId(qcm.getId());

        partialUpdatedQcm
            .question(UPDATED_QUESTION)
            .choixDispo(UPDATED_CHOIX_DISPO)
            .choixCorrect(UPDATED_CHOIX_CORRECT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restQcmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQcm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQcm))
            )
            .andExpect(status().isOk());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeUpdate);
        Qcm testQcm = qcmList.get(qcmList.size() - 1);
        assertThat(testQcm.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQcm.getChoixDispo()).isEqualTo(UPDATED_CHOIX_DISPO);
        assertThat(testQcm.getChoixCorrect()).isEqualTo(UPDATED_CHOIX_CORRECT);
        assertThat(testQcm.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testQcm.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testQcm.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testQcm.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testQcm.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void patchNonExistingQcm() throws Exception {
        int databaseSizeBeforeUpdate = qcmRepository.findAll().size();
        qcm.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQcmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qcm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qcm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchQcm() throws Exception {
        int databaseSizeBeforeUpdate = qcmRepository.findAll().size();
        qcm.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qcm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamQcm() throws Exception {
        int databaseSizeBeforeUpdate = qcmRepository.findAll().size();
        qcm.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qcm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Qcm in the database
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteQcm() throws Exception {
        // Initialize the database
        qcmRepository.save(qcm);

        int databaseSizeBeforeDelete = qcmRepository.findAll().size();

        // Delete the qcm
        restQcmMockMvc.perform(delete(ENTITY_API_URL_ID, qcm.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Qcm> qcmList = qcmRepository.findAll();
        assertThat(qcmList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
