package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Reponse.
 */
@Document(collection = "reponse")
public class Reponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("score")
    private Integer score;

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
    @Field("qcmR")
    @JsonIgnoreProperties(value = { "reponse" }, allowSetters = true)
    private Set<QcmR> qcmRS = new HashSet<>();

    @DBRef
    @Field("refEcole")
    @JsonIgnoreProperties(value = { "localisation", "reponses" }, allowSetters = true)
    private Ecole refEcole;

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

    public Reponse id(String id) {
        this.id = id;
        return this;
    }

    public Integer getScore() {
        return this.score;
    }

    public Reponse score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Reponse createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Reponse createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Reponse deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public Reponse deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedDate() {
        return this.deletedDate;
    }

    public Reponse deletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Set<QcmR> getQcmRS() {
        return this.qcmRS;
    }

    public Reponse qcmRS(Set<QcmR> qcmRS) {
        this.setQcmRS(qcmRS);
        return this;
    }

    public Reponse addQcmR(QcmR qcmR) {
        this.qcmRS.add(qcmR);
        qcmR.setReponse(this);
        return this;
    }

    public Reponse removeQcmR(QcmR qcmR) {
        this.qcmRS.remove(qcmR);
        qcmR.setReponse(null);
        return this;
    }

    public void setQcmRS(Set<QcmR> qcmRS) {
        if (this.qcmRS != null) {
            this.qcmRS.forEach(i -> i.setReponse(null));
        }
        if (qcmRS != null) {
            qcmRS.forEach(i -> i.setReponse(this));
        }
        this.qcmRS = qcmRS;
    }

    public Ecole getRefEcole() {
        return this.refEcole;
    }

    public Reponse refEcole(Ecole ecole) {
        this.setRefEcole(ecole);
        return this;
    }

    public void setRefEcole(Ecole ecole) {
        this.refEcole = ecole;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public Reponse competition(Competition competition) {
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
        if (!(o instanceof Reponse)) {
            return false;
        }
        return id != null && id.equals(((Reponse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reponse{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
