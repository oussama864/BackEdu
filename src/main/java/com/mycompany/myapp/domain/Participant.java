package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Participant.
 */
@Document(collection = "participant")
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

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
    @Field("ecolier")
    private Ecolier ecolier;

    @DBRef
    @Field("reponse")
    private Reponse reponse;

    @DBRef
    @Field("resultatEcole")
    @JsonIgnoreProperties(value = { "ecole", "participants", "competition" }, allowSetters = true)
    private ResultatEcole resultatEcole;

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

    public Participant id(String id) {
        this.id = id;
        return this;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Participant createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Participant createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Participant deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public Participant deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedDate() {
        return this.deletedDate;
    }

    public Participant deletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Ecolier getEcolier() {
        return this.ecolier;
    }

    public Participant ecolier(Ecolier ecolier) {
        this.setEcolier(ecolier);
        return this;
    }

    public void setEcolier(Ecolier ecolier) {
        this.ecolier = ecolier;
    }

    public Reponse getReponse() {
        return this.reponse;
    }

    public Participant reponse(Reponse reponse) {
        this.setReponse(reponse);
        return this;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    public ResultatEcole getResultatEcole() {
        return this.resultatEcole;
    }

    public Participant resultatEcole(ResultatEcole resultatEcole) {
        this.setResultatEcole(resultatEcole);
        return this;
    }

    public void setResultatEcole(ResultatEcole resultatEcole) {
        this.resultatEcole = resultatEcole;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public Participant competition(Competition competition) {
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
        if (!(o instanceof Participant)) {
            return false;
        }
        return id != null && id.equals(((Participant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Participant{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
