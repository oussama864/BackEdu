package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Competition;
import com.mycompany.myapp.repository.CompetitionRepository;
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
 * Integration tests for the {@link CompetitionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetitionResourceIT {

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final Double DEFAULT_CODE = 1D;
    private static final Double UPDATED_CODE = 2D;

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

    private static final String ENTITY_API_URL = "/api/competitions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private MockMvc restCompetitionMockMvc;

    private Competition competition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competition createEntity() {
        Competition competition = new Competition()
            .date(DEFAULT_DATE)
            .code(DEFAULT_CODE)
            .score(DEFAULT_SCORE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .deleted(DEFAULT_DELETED)
            .deletedBy(DEFAULT_DELETED_BY)
            .deletedDate(DEFAULT_DELETED_DATE);
        return competition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competition createUpdatedEntity() {
        Competition competition = new Competition()
            .date(UPDATED_DATE)
            .code(UPDATED_CODE)
            .score(UPDATED_SCORE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);
        return competition;
    }

    @BeforeEach
    public void initTest() {
        competitionRepository.deleteAll();
        competition = createEntity();
    }

    @Test
    void createCompetition() throws Exception {
        int databaseSizeBeforeCreate = competitionRepository.findAll().size();
        // Create the Competition
        restCompetitionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competition)))
            .andExpect(status().isCreated());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeCreate + 1);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCompetition.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCompetition.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testCompetition.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCompetition.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompetition.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testCompetition.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testCompetition.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    void createCompetitionWithExistingId() throws Exception {
        // Create the Competition with an existing ID
        competition.setId("existing_id");

        int databaseSizeBeforeCreate = competitionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competition)))
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCompetitions() throws Exception {
        // Initialize the database
        competitionRepository.save(competition);

        // Get all the competitionList
        restCompetitionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competition.getId())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.doubleValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    void getCompetition() throws Exception {
        // Initialize the database
        competitionRepository.save(competition);

        // Get the competition
        restCompetitionMockMvc
            .perform(get(ENTITY_API_URL_ID, competition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competition.getId()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.doubleValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    void getNonExistingCompetition() throws Exception {
        // Get the competition
        restCompetitionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewCompetition() throws Exception {
        // Initialize the database
        competitionRepository.save(competition);

        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();

        // Update the competition
        Competition updatedCompetition = competitionRepository.findById(competition.getId()).get();
        updatedCompetition
            .date(UPDATED_DATE)
            .code(UPDATED_CODE)
            .score(UPDATED_SCORE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restCompetitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompetition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompetition))
            )
            .andExpect(status().isOk());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCompetition.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCompetition.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testCompetition.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCompetition.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompetition.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testCompetition.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testCompetition.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void putNonExistingCompetition() throws Exception {
        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();
        competition.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCompetition() throws Exception {
        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();
        competition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCompetition() throws Exception {
        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();
        competition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competition)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCompetitionWithPatch() throws Exception {
        // Initialize the database
        competitionRepository.save(competition);

        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();

        // Update the competition using partial update
        Competition partialUpdatedCompetition = new Competition();
        partialUpdatedCompetition.setId(competition.getId());

        partialUpdatedCompetition.date(UPDATED_DATE).score(UPDATED_SCORE).deletedDate(UPDATED_DELETED_DATE);

        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetition))
            )
            .andExpect(status().isOk());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCompetition.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCompetition.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testCompetition.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCompetition.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompetition.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testCompetition.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testCompetition.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void fullUpdateCompetitionWithPatch() throws Exception {
        // Initialize the database
        competitionRepository.save(competition);

        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();

        // Update the competition using partial update
        Competition partialUpdatedCompetition = new Competition();
        partialUpdatedCompetition.setId(competition.getId());

        partialUpdatedCompetition
            .date(UPDATED_DATE)
            .code(UPDATED_CODE)
            .score(UPDATED_SCORE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .deleted(UPDATED_DELETED)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedDate(UPDATED_DELETED_DATE);

        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetition))
            )
            .andExpect(status().isOk());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCompetition.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCompetition.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testCompetition.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCompetition.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompetition.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testCompetition.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testCompetition.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    void patchNonExistingCompetition() throws Exception {
        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();
        competition.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCompetition() throws Exception {
        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();
        competition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCompetition() throws Exception {
        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();
        competition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetitionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(competition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCompetition() throws Exception {
        // Initialize the database
        competitionRepository.save(competition);

        int databaseSizeBeforeDelete = competitionRepository.findAll().size();

        // Delete the competition
        restCompetitionMockMvc
            .perform(delete(ENTITY_API_URL_ID, competition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
