package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conte.class);
        Conte conte1 = new Conte();
        conte1.setId("id1");
        Conte conte2 = new Conte();
        conte2.setId(conte1.getId());
        assertThat(conte1).isEqualTo(conte2);
        conte2.setId("id2");
        assertThat(conte1).isNotEqualTo(conte2);
        conte1.setId(null);
        assertThat(conte1).isNotEqualTo(conte2);
    }
}
