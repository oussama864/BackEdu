package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.QcmR;
import com.mycompany.myapp.repository.QcmRRepository;
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
 * Integration tests for the {@link QcmRResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QcmRResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_CHOIX_PARTICIPANT = "AAAAAAAAAA";
    private static final String UPDATED_CHOIX_PARTICIPANT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/qcm-rs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private QcmRRepository qcmRRepository;

    @Autowired
    private MockMvc restQcmRMockMvc;

    private QcmR qcmR;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QcmR createEntity() {
        QcmR qcmR = new QcmR().question(DEFAULT_QUESTION).choixParticipant(DEFAULT_CHOIX_PARTICIPANT);
        return qcmR;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QcmR createUpdatedEntity() {
        QcmR qcmR = new QcmR().question(UPDATED_QUESTION).choixParticipant(UPDATED_CHOIX_PARTICIPANT);
        return qcmR;
    }

    @BeforeEach
    public void initTest() {
        qcmRRepository.deleteAll();
        qcmR = createEntity();
    }

    @Test
    void createQcmR() throws Exception {
        int databaseSizeBeforeCreate = qcmRRepository.findAll().size();
        // Create the QcmR
        restQcmRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qcmR)))
            .andExpect(status().isCreated());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeCreate + 1);
        QcmR testQcmR = qcmRList.get(qcmRList.size() - 1);
        assertThat(testQcmR.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQcmR.getChoixParticipant()).isEqualTo(DEFAULT_CHOIX_PARTICIPANT);
    }

    @Test
    void createQcmRWithExistingId() throws Exception {
        // Create the QcmR with an existing ID
        qcmR.setId("existing_id");

        int databaseSizeBeforeCreate = qcmRRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQcmRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qcmR)))
            .andExpect(status().isBadRequest());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllQcmRS() throws Exception {
        // Initialize the database
        qcmRRepository.save(qcmR);

        // Get all the qcmRList
        restQcmRMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qcmR.getId())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].choixParticipant").value(hasItem(DEFAULT_CHOIX_PARTICIPANT)));
    }

    @Test
    void getQcmR() throws Exception {
        // Initialize the database
        qcmRRepository.save(qcmR);

        // Get the qcmR
        restQcmRMockMvc
            .perform(get(ENTITY_API_URL_ID, qcmR.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qcmR.getId()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.choixParticipant").value(DEFAULT_CHOIX_PARTICIPANT));
    }

    @Test
    void getNonExistingQcmR() throws Exception {
        // Get the qcmR
        restQcmRMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewQcmR() throws Exception {
        // Initialize the database
        qcmRRepository.save(qcmR);

        int databaseSizeBeforeUpdate = qcmRRepository.findAll().size();

        // Update the qcmR
        QcmR updatedQcmR = qcmRRepository.findById(qcmR.getId()).get();
        updatedQcmR.question(UPDATED_QUESTION).choixParticipant(UPDATED_CHOIX_PARTICIPANT);

        restQcmRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQcmR.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQcmR))
            )
            .andExpect(status().isOk());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeUpdate);
        QcmR testQcmR = qcmRList.get(qcmRList.size() - 1);
        assertThat(testQcmR.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQcmR.getChoixParticipant()).isEqualTo(UPDATED_CHOIX_PARTICIPANT);
    }

    @Test
    void putNonExistingQcmR() throws Exception {
        int databaseSizeBeforeUpdate = qcmRRepository.findAll().size();
        qcmR.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQcmRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qcmR.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qcmR))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchQcmR() throws Exception {
        int databaseSizeBeforeUpdate = qcmRRepository.findAll().size();
        qcmR.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qcmR))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamQcmR() throws Exception {
        int databaseSizeBeforeUpdate = qcmRRepository.findAll().size();
        qcmR.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmRMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qcmR)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateQcmRWithPatch() throws Exception {
        // Initialize the database
        qcmRRepository.save(qcmR);

        int databaseSizeBeforeUpdate = qcmRRepository.findAll().size();

        // Update the qcmR using partial update
        QcmR partialUpdatedQcmR = new QcmR();
        partialUpdatedQcmR.setId(qcmR.getId());

        restQcmRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQcmR.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQcmR))
            )
            .andExpect(status().isOk());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeUpdate);
        QcmR testQcmR = qcmRList.get(qcmRList.size() - 1);
        assertThat(testQcmR.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQcmR.getChoixParticipant()).isEqualTo(DEFAULT_CHOIX_PARTICIPANT);
    }

    @Test
    void fullUpdateQcmRWithPatch() throws Exception {
        // Initialize the database
        qcmRRepository.save(qcmR);

        int databaseSizeBeforeUpdate = qcmRRepository.findAll().size();

        // Update the qcmR using partial update
        QcmR partialUpdatedQcmR = new QcmR();
        partialUpdatedQcmR.setId(qcmR.getId());

        partialUpdatedQcmR.question(UPDATED_QUESTION).choixParticipant(UPDATED_CHOIX_PARTICIPANT);

        restQcmRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQcmR.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQcmR))
            )
            .andExpect(status().isOk());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeUpdate);
        QcmR testQcmR = qcmRList.get(qcmRList.size() - 1);
        assertThat(testQcmR.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQcmR.getChoixParticipant()).isEqualTo(UPDATED_CHOIX_PARTICIPANT);
    }

    @Test
    void patchNonExistingQcmR() throws Exception {
        int databaseSizeBeforeUpdate = qcmRRepository.findAll().size();
        qcmR.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQcmRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qcmR.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qcmR))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchQcmR() throws Exception {
        int databaseSizeBeforeUpdate = qcmRRepository.findAll().size();
        qcmR.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qcmR))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamQcmR() throws Exception {
        int databaseSizeBeforeUpdate = qcmRRepository.findAll().size();
        qcmR.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmRMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qcmR)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QcmR in the database
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteQcmR() throws Exception {
        // Initialize the database
        qcmRRepository.save(qcmR);

        int databaseSizeBeforeDelete = qcmRRepository.findAll().size();

        // Delete the qcmR
        restQcmRMockMvc
            .perform(delete(ENTITY_API_URL_ID, qcmR.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QcmR> qcmRList = qcmRRepository.findAll();
        assertThat(qcmRList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
