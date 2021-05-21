package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultatEcoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultatEcole.class);
        ResultatEcole resultatEcole1 = new ResultatEcole();
        resultatEcole1.setId("id1");
        ResultatEcole resultatEcole2 = new ResultatEcole();
        resultatEcole2.setId(resultatEcole1.getId());
        assertThat(resultatEcole1).isEqualTo(resultatEcole2);
        resultatEcole2.setId("id2");
        assertThat(resultatEcole1).isNotEqualTo(resultatEcole2);
        resultatEcole1.setId(null);
        assertThat(resultatEcole1).isNotEqualTo(resultatEcole2);
    }
}
