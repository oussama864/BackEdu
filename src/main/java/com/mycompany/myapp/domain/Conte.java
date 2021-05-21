package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Conte.
 */
@Document(collection = "conte")
public class Conte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("type")
    private String type;

    @Field("description")
    private String description;

    @Field("prix")
    private Long prix;

    @Field("language")
    private String language;

    @Field("image_url")
    private String imageUrl;

    @Field("titre")
    private String titre;

    @Field("nb_page")
    private Integer nbPage;

    @Field("maison_edition")
    private String maisonEdition;

    @Field("created_by")
    private String createdBy;

    @Field("created_date")
    private Date createdDate;

    @Field("deleted")
    private Boolean deleted;

    @Field("deleted_by")
    private String deletedBy;

    @Field("deleted_date")
    private Date deletedDate;

    private String emailAuteur;

    public boolean isReadyForCompetetion() {
        return readyForCompetetion;
    }

    public void setReadyForCompetetion(boolean readyForCompetetion) {
        this.readyForCompetetion = readyForCompetetion;
    }

    @Field("readyForCompetetion")
    private boolean readyForCompetetion;

    public Date getReadyForCompetitionDate() {
        return readyForCompetitionDate;
    }

    public void setReadyForCompetitionDate(Date readyForCompetitionDate) {
        this.readyForCompetitionDate = readyForCompetitionDate;
    }

    @Field("readyForCompetitionDate")
    private Date readyForCompetitionDate;

    @DBRef
    @Field("qcm")
    @JsonIgnoreProperties(value = { "refcon", "competition" }, allowSetters = true)
    private Set<Qcm> qcms = new HashSet<>();

    @DBRef
    @Field("auteur")
    @JsonIgnoreProperties(value = { "contes" }, allowSetters = true)
    private Auteur auteur;

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

    public Conte id(String id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public Conte nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return this.type;
    }

    public Conte type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public Conte description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrix() {
        return this.prix;
    }

    public Conte prix(Long prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public String getLanguage() {
        return this.language;
    }

    public Conte language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Conte imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitre() {
        return this.titre;
    }

    public Conte titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getNbPage() {
        return this.nbPage;
    }

    public Conte nbPage(Integer nbPage) {
        this.nbPage = nbPage;
        return this;
    }

    public void setNbPage(Integer nbPage) {
        this.nbPage = nbPage;
    }

    public String getMaisonEdition() {
        return this.maisonEdition;
    }

    public Conte maisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
        return this;
    }

    public void setMaisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Conte createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public Conte createdDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Conte deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public Conte deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedDate() {
        return this.deletedDate;
    }

    public Conte deletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Set<Qcm> getQcms() {
        return this.qcms;
    }

    public Conte qcms(Set<Qcm> qcms) {
        this.setQcms(qcms);
        return this;
    }

    public Conte addQcm(Qcm qcm) {
        this.qcms.add(qcm);
        qcm.setRefcon(this);
        return this;
    }

    public Conte removeQcm(Qcm qcm) {
        this.qcms.remove(qcm);
        qcm.setRefcon(null);
        return this;
    }

    public void setQcms(Set<Qcm> qcms) {
        if (this.qcms != null) {
            this.qcms.forEach(i -> i.setRefcon(null));
        }
        if (qcms != null) {
            qcms.forEach(i -> i.setRefcon(this));
        }
        this.qcms = qcms;
    }

    public Auteur getAuteur() {
        return this.auteur;
    }

    public Conte auteur(Auteur auteur) {
        this.setAuteur(auteur);
        return this;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public Conte competition(Competition competition) {
        this.setCompetition(competition);
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public String getEmailAuteur() {
        return emailAuteur;
    }

    public void setEmailAuteur(String emailAuteur) {
        this.emailAuteur = emailAuteur;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conte)) {
            return false;
        }
        return id != null && id.equals(((Conte) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conte{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", prix=" + getPrix() +
            ", language='" + getLanguage() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", titre='" + getTitre() + "'" +
            ", nbPage=" + getNbPage() +
            ", maisonEdition='" + getMaisonEdition() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
