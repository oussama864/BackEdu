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
 * A ResultatEcole.
 */
@Document(collection = "resultat_ecole")
public class ResultatEcole implements Serializable {

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
    @Field("ecole")
    private Ecole ecole;

    @DBRef
    @Field("participant")
    @JsonIgnoreProperties(value = { "ecolier", "reponse", "resultatEcole", "competition" }, allowSetters = true)
    private Set<Participant> participants = new HashSet<>();

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

    public ResultatEcole id(String id) {
        this.id = id;
        return this;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ResultatEcole createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public ResultatEcole createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public ResultatEcole deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public ResultatEcole deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedDate() {
        return this.deletedDate;
    }

    public ResultatEcole deletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Ecole getEcole() {
        return this.ecole;
    }

    public ResultatEcole ecole(Ecole ecole) {
        this.setEcole(ecole);
        return this;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
    }

    public Set<Participant> getParticipants() {
        return this.participants;
    }

    public ResultatEcole participants(Set<Participant> participants) {
        this.setParticipants(participants);
        return this;
    }

    public ResultatEcole addParticipant(Participant participant) {
        this.participants.add(participant);
        participant.setResultatEcole(this);
        return this;
    }

    public ResultatEcole removeParticipant(Participant participant) {
        this.participants.remove(participant);
        participant.setResultatEcole(null);
        return this;
    }

    public void setParticipants(Set<Participant> participants) {
        if (this.participants != null) {
            this.participants.forEach(i -> i.setResultatEcole(null));
        }
        if (participants != null) {
            participants.forEach(i -> i.setResultatEcole(this));
        }
        this.participants = participants;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public ResultatEcole competition(Competition competition) {
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
        if (!(o instanceof ResultatEcole)) {
            return false;
        }
        return id != null && id.equals(((ResultatEcole) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultatEcole{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
