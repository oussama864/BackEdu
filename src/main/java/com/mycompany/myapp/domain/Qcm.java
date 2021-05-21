package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Qcm.
 */
@Document(collection = "qcm")
public class Qcm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("question")
    private String question;

    @Field("choix_dispo")
    private String choixDispo;

    @Field("choix_correct")
    private String choixCorrect;

    @Field("created_by")
    private String createdBy;

    @Field("created_date")
    private LocalDate createdDate;

    @Field("deleted")
    private Boolean deleted;

    @Field("deleted_by")
    private String deletedBy;

    @Field("deleted_date")
    private LocalDate deletedDate;

    @DBRef
    @Field("refcon")
    @JsonIgnoreProperties(value = { "qcms", "auteur", "competition" }, allowSetters = true)
    private Conte refcon;

    @DBRef
    @Field("competition")
    @JsonIgnoreProperties(value = { "reponses", "ecoliers", "participants", "qcms", "contes", "resultatEcoles" }, allowSetters = true)
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Qcm id(String id) {
        this.id = id;
        return this;
    }

    public String getQuestion() {
        return this.question;
    }

    public Qcm question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoixDispo() {
        return this.choixDispo;
    }

    public Qcm choixDispo(String choixDispo) {
        this.choixDispo = choixDispo;
        return this;
    }

    public void setChoixDispo(String choixDispo) {
        this.choixDispo = choixDispo;
    }

    public String getChoixCorrect() {
        return this.choixCorrect;
    }

    public Qcm choixCorrect(String choixCorrect) {
        this.choixCorrect = choixCorrect;
        return this;
    }

    public void setChoixCorrect(String choixCorrect) {
        this.choixCorrect = choixCorrect;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Qcm createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Qcm createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Qcm deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public Qcm deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedDate() {
        return this.deletedDate;
    }

    public Qcm deletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Conte getRefcon() {
        return this.refcon;
    }

    public Qcm refcon(Conte conte) {
        this.setRefcon(conte);
        return this;
    }

    public void setRefcon(Conte conte) {
        this.refcon = conte;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public Qcm competition(Competition competition) {
        this.setCompetition(competition);
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Qcm)) {
            return false;
        }
        return id != null && id.equals(((Qcm) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Qcm{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", choixDispo='" + getChoixDispo() + "'" +
            ", choixCorrect='" + getChoixCorrect() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
