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
 * A Competition.
 */
@Document(collection = "competition")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date")
    private String date;

    @Field("code")
    private Double code;

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
    @Field("reponse")
    @JsonIgnoreProperties(value = { "qcmRS", "refEcole", "competition" }, allowSetters = true)
    private Set<Reponse> reponses = new HashSet<>();

    @DBRef
    @Field("ecolier")
    @JsonIgnoreProperties(value = { "competition" }, allowSetters = true)
    private Set<Ecolier> ecoliers = new HashSet<>();

    @DBRef
    @Field("participant")
    @JsonIgnoreProperties(value = { "ecolier", "reponse", "resultatEcole", "competition" }, allowSetters = true)
    private Set<Participant> participants = new HashSet<>();

    @DBRef
    @Field("qcm")
    @JsonIgnoreProperties(value = { "refcon", "competition" }, allowSetters = true)
    private Set<Qcm> qcms = new HashSet<>();

    @DBRef
    @Field("conte")
    @JsonIgnoreProperties(value = { "qcms", "auteur", "competition" }, allowSetters = true)
    private Set<Conte> contes = new HashSet<>();

    @DBRef
    @Field("resultatEcole")
    @JsonIgnoreProperties(value = { "ecole", "participants", "competition" }, allowSetters = true)
    private Set<ResultatEcole> resultatEcoles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Competition id(String id) {
        this.id = id;
        return this;
    }

    public String getDate() {
        return this.date;
    }

    public Competition date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getCode() {
        return this.code;
    }

    public Competition code(Double code) {
        this.code = code;
        return this;
    }

    public void setCode(Double code) {
        this.code = code;
    }

    public Integer getScore() {
        return this.score;
    }

    public Competition score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Competition createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Competition createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Competition deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public Competition deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedDate() {
        return this.deletedDate;
    }

    public Competition deletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Set<Reponse> getReponses() {
        return this.reponses;
    }

    public Competition reponses(Set<Reponse> reponses) {
        this.setReponses(reponses);
        return this;
    }

    public Competition addReponse(Reponse reponse) {
        this.reponses.add(reponse);
        reponse.setCompetition(this);
        return this;
    }

    public Competition removeReponse(Reponse reponse) {
        this.reponses.remove(reponse);
        reponse.setCompetition(null);
        return this;
    }

    public void setReponses(Set<Reponse> reponses) {
        if (this.reponses != null) {
            this.reponses.forEach(i -> i.setCompetition(null));
        }
        if (reponses != null) {
            reponses.forEach(i -> i.setCompetition(this));
        }
        this.reponses = reponses;
    }

    public Set<Ecolier> getEcoliers() {
        return this.ecoliers;
    }

    public Competition ecoliers(Set<Ecolier> ecoliers) {
        this.setEcoliers(ecoliers);
        return this;
    }

    public Competition addEcolier(Ecolier ecolier) {
        this.ecoliers.add(ecolier);
        ecolier.setCompetition(this);
        return this;
    }

    public Competition removeEcolier(Ecolier ecolier) {
        this.ecoliers.remove(ecolier);
        ecolier.setCompetition(null);
        return this;
    }

    public void setEcoliers(Set<Ecolier> ecoliers) {
        if (this.ecoliers != null) {
            this.ecoliers.forEach(i -> i.setCompetition(null));
        }
        if (ecoliers != null) {
            ecoliers.forEach(i -> i.setCompetition(this));
        }
        this.ecoliers = ecoliers;
    }

    public Set<Participant> getParticipants() {
        return this.participants;
    }

    public Competition participants(Set<Participant> participants) {
        this.setParticipants(participants);
        return this;
    }

    public Competition addParticipant(Participant participant) {
        this.participants.add(participant);
        participant.setCompetition(this);
        return this;
    }

    public Competition removeParticipant(Participant participant) {
        this.participants.remove(participant);
        participant.setCompetition(null);
        return this;
    }

    public void setParticipants(Set<Participant> participants) {
        if (this.participants != null) {
            this.participants.forEach(i -> i.setCompetition(null));
        }
        if (participants != null) {
            participants.forEach(i -> i.setCompetition(this));
        }
        this.participants = participants;
    }

    public Set<Qcm> getQcms() {
        return this.qcms;
    }

    public Competition qcms(Set<Qcm> qcms) {
        this.setQcms(qcms);
        return this;
    }

    public Competition addQcm(Qcm qcm) {
        this.qcms.add(qcm);
        qcm.setCompetition(this);
        return this;
    }

    public Competition removeQcm(Qcm qcm) {
        this.qcms.remove(qcm);
        qcm.setCompetition(null);
        return this;
    }

    public void setQcms(Set<Qcm> qcms) {
        if (this.qcms != null) {
            this.qcms.forEach(i -> i.setCompetition(null));
        }
        if (qcms != null) {
            qcms.forEach(i -> i.setCompetition(this));
        }
        this.qcms = qcms;
    }

    public Set<Conte> getContes() {
        return this.contes;
    }

    public Competition contes(Set<Conte> contes) {
        this.setContes(contes);
        return this;
    }

    public Competition addConte(Conte conte) {
        this.contes.add(conte);
        conte.setCompetition(this);
        return this;
    }

    public Competition removeConte(Conte conte) {
        this.contes.remove(conte);
        conte.setCompetition(null);
        return this;
    }

    public void setContes(Set<Conte> contes) {
        if (this.contes != null) {
            this.contes.forEach(i -> i.setCompetition(null));
        }
        if (contes != null) {
            contes.forEach(i -> i.setCompetition(this));
        }
        this.contes = contes;
    }

    public Set<ResultatEcole> getResultatEcoles() {
        return this.resultatEcoles;
    }

    public Competition resultatEcoles(Set<ResultatEcole> resultatEcoles) {
        this.setResultatEcoles(resultatEcoles);
        return this;
    }

    public Competition addResultatEcole(ResultatEcole resultatEcole) {
        this.resultatEcoles.add(resultatEcole);
        resultatEcole.setCompetition(this);
        return this;
    }

    public Competition removeResultatEcole(ResultatEcole resultatEcole) {
        this.resultatEcoles.remove(resultatEcole);
        resultatEcole.setCompetition(null);
        return this;
    }

    public void setResultatEcoles(Set<ResultatEcole> resultatEcoles) {
        if (this.resultatEcoles != null) {
            this.resultatEcoles.forEach(i -> i.setCompetition(null));
        }
        if (resultatEcoles != null) {
            resultatEcoles.forEach(i -> i.setCompetition(this));
        }
        this.resultatEcoles = resultatEcoles;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competition)) {
            return false;
        }
        return id != null && id.equals(((Competition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competition{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", code=" + getCode() +
            ", score=" + getScore() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
