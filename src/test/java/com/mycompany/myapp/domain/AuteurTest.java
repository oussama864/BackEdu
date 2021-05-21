package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuteurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auteur.class);
        Auteur auteur1 = new Auteur();
        auteur1.setId("id1");
        Auteur auteur2 = new Auteur();
        auteur2.setId(auteur1.getId());
        assertThat(auteur1).isEqualTo(auteur2);
        auteur2.setId("id2");
        assertThat(auteur1).isNotEqualTo(auteur2);
        auteur1.setId(null);
        assertThat(auteur1).isNotEqualTo(auteur2);
    }
}
