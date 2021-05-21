package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A QcmR.
 */
@Document(collection = "qcm_r")
public class QcmR implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("question")
    private String question;

    @Field("choix_participant")
    private String choixParticipant;

    @DBRef
    @Field("reponse")
    @JsonIgnoreProperties(value = { "qcmRS", "refEcole", "competition" }, allowSetters = true)
    private Reponse reponse;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QcmR id(String id) {
        this.id = id;
        return this;
    }

    public String getQuestion() {
        return this.question;
    }

    public QcmR question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoixParticipant() {
        return this.choixParticipant;
    }

    public QcmR choixParticipant(String choixParticipant) {
        this.choixParticipant = choixParticipant;
        return this;
    }

    public void setChoixParticipant(String choixParticipant) {
        this.choixParticipant = choixParticipant;
    }

    public Reponse getReponse() {
        return this.reponse;
    }

    public QcmR reponse(Reponse reponse) {
        this.setReponse(reponse);
        return this;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QcmR)) {
            return false;
        }
        return id != null && id.equals(((QcmR) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QcmR{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", choixParticipant='" + getChoixParticipant() + "'" +
            "}";
    }
}
