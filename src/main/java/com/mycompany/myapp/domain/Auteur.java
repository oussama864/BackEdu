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
 * A Auteur.
 */
@Document(collection = "auteur")
public class Auteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("ref_user")
    private String refUser;

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



    @Field("points")
    private int points;

    @DBRef
    @Field("conte")
    @JsonIgnoreProperties(value = { "qcms", "auteur", "competition" }, allowSetters = true)
    private Set<Conte> contes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Auteur id(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Auteur firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Auteur lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Auteur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Auteur password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefUser() {
        return this.refUser;
    }

    public Auteur refUser(String refUser) {
        this.refUser = refUser;
        return this;
    }

    public void setRefUser(String refUser) {
        this.refUser = refUser;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Auteur createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Auteur createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Auteur deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public Auteur deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedDate() {
        return this.deletedDate;
    }

    public Auteur deletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Set<Conte> getContes() {
        return this.contes;
    }

    public Auteur contes(Set<Conte> contes) {
        this.setContes(contes);
        return this;
    }

    public Auteur addConte(Conte conte) {
        this.contes.add(conte);
        conte.setAuteur(this);
        return this;
    }

    public Auteur removeConte(Conte conte) {
        this.contes.remove(conte);
        conte.setAuteur(null);
        return this;
    }

    public void setContes(Set<Conte> contes) {
        if (this.contes != null) {
            this.contes.forEach(i -> i.setAuteur(null));
        }
        if (contes != null) {
            contes.forEach(i -> i.setAuteur(this));
        }
        this.contes = contes;
    }
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auteur)) {
            return false;
        }
        return id != null && id.equals(((Auteur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Auteur{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", refUser='" + getRefUser() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
