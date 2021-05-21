package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Localisation;
import com.mycompany.myapp.repository.LocalisationRepository;
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
 * Integration tests for the {@link LocalisationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocalisationResourceIT {

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final Double DEFAULT_LNG = 1D;
    private static final Double UPDATED_LNG = 2D;

    private static final String ENTITY_API_URL = "/api/localisations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LocalisationRepository localisationRepository;

    @Autowired
    private MockMvc restLocalisationMockMvc;

    private Localisation localisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createEntity() {
        Localisation localisation = new Localisation().lat(DEFAULT_LAT).lng(DEFAULT_LNG);
        return localisation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createUpdatedEntity() {
        Localisation localisation = new Localisation().lat(UPDATED_LAT).lng(UPDATED_LNG);
        return localisation;
    }

    @BeforeEach
    public void initTest() {
        localisationRepository.deleteAll();
        localisation = createEntity();
    }

    @Test
    void createLocalisation() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();
        // Create the Localisation
        restLocalisationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isCreated());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate + 1);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testLocalisation.getLng()).isEqualTo(DEFAULT_LNG);
    }

    @Test
    void createLocalisationWithExistingId() throws Exception {
        // Create the Localisation with an existing ID
        localisation.setId("existing_id");

        int databaseSizeBeforeCreate = localisationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalisationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLocalisations() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        // Get all the localisationList
        restLocalisationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localisation.getId())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())));
    }

    @Test
    void getLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        // Get the localisation
        restLocalisationMockMvc
            .perform(get(ENTITY_API_URL_ID, localisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localisation.getId()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()));
    }

    @Test
    void getNonExistingLocalisation() throws Exception {
        // Get the localisation
        restLocalisationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation
        Localisation updatedLocalisation = localisationRepository.findById(localisation.getId()).get();
        updatedLocalisation.lat(UPDATED_LAT).lng(UPDATED_LNG);

        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocalisation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLocalisation))
            )
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testLocalisation.getLng()).isEqualTo(UPDATED_LNG);
    }

    @Test
    void putNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, localisation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLocalisationWithPatch() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation using partial update
        Localisation partialUpdatedLocalisation = new Localisation();
        partialUpdatedLocalisation.setId(localisation.getId());

        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocalisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocalisation))
            )
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testLocalisation.getLng()).isEqualTo(DEFAULT_LNG);
    }

    @Test
    void fullUpdateLocalisationWithPatch() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation using partial update
        Localisation partialUpdatedLocalisation = new Localisation();
        partialUpdatedLocalisation.setId(localisation.getId());

        partialUpdatedLocalisation.lat(UPDATED_LAT).lng(UPDATED_LNG);

        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocalisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocalisation))
            )
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testLocalisation.getLng()).isEqualTo(UPDATED_LNG);
    }

    @Test
    void patchNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, localisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(localisation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation);

        int databaseSizeBeforeDelete = localisationRepository.findAll().size();

        // Delete the localisation
        restLocalisationMockMvc
            .perform(delete(ENTITY_API_URL_ID, localisation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
