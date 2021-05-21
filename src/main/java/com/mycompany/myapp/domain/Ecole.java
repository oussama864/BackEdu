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
 * A Ecole.
 */
@Document(collection = "ecole")
public class Ecole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("adresse")
    private String adresse;

    @Field("email")
    private String email;

    @Field("login")
    private String login;

    @Field("password")
    private String password;

    @Field("liste_classes")
    private String listeClasses;

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
    @Field("localisation")
    private Localisation localisation;

    @DBRef
    @Field("reponse")
    @JsonIgnoreProperties(value = { "qcmRS", "refEcole", "competition" }, allowSetters = true)
    private Set<Reponse> reponses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ecole id(String id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public Ecole nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Ecole adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public Ecole email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return this.login;
    }

    public Ecole login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public Ecole password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getListeClasses() {
        return this.listeClasses;
    }

    public Ecole listeClasses(String listeClasses) {
        this.listeClasses = listeClasses;
        return this;
    }

    public void setListeClasses(String listeClasses) {
        this.listeClasses = listeClasses;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Ecole createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Ecole createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Ecole deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public Ecole deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedDate() {
        return this.deletedDate;
    }

    public Ecole deletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Localisation getLocalisation() {
        return this.localisation;
    }

    public Ecole localisation(Localisation localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public Set<Reponse> getReponses() {
        return this.reponses;
    }

    public Ecole reponses(Set<Reponse> reponses) {
        this.setReponses(reponses);
        return this;
    }

    public Ecole addReponse(Reponse reponse) {
        this.reponses.add(reponse);
        reponse.setRefEcole(this);
        return this;
    }

    public Ecole removeReponse(Reponse reponse) {
        this.reponses.remove(reponse);
        reponse.setRefEcole(null);
        return this;
    }

    public void setReponses(Set<Reponse> reponses) {
        if (this.reponses != null) {
            this.reponses.forEach(i -> i.setRefEcole(null));
        }
        if (reponses != null) {
            reponses.forEach(i -> i.setRefEcole(this));
        }
        this.reponses = reponses;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ecole)) {
            return false;
        }
        return id != null && id.equals(((Ecole) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ecole{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", email='" + getEmail() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", listeClasses='" + getListeClasses() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
