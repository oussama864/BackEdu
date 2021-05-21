package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcolierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ecolier.class);
        Ecolier ecolier1 = new Ecolier();
        ecolier1.setId("id1");
        Ecolier ecolier2 = new Ecolier();
        ecolier2.setId(ecolier1.getId());
        assertThat(ecolier1).isEqualTo(ecolier2);
        ecolier2.setId("id2");
        assertThat(ecolier1).isNotEqualTo(ecolier2);
        ecolier1.setId(null);
        assertThat(ecolier1).isNotEqualTo(ecolier2);
    }
}
