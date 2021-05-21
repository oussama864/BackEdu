package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QcmRTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QcmR.class);
        QcmR qcmR1 = new QcmR();
        qcmR1.setId("id1");
        QcmR qcmR2 = new QcmR();
        qcmR2.setId(qcmR1.getId());
        assertThat(qcmR1).isEqualTo(qcmR2);
        qcmR2.setId("id2");
        assertThat(qcmR1).isNotEqualTo(qcmR2);
        qcmR1.setId(null);
        assertThat(qcmR1).isNotEqualTo(qcmR2);
    }
}
