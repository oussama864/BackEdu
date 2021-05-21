package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Ecolier.
 */
@Document(collection = "ecolier")
public class Ecolier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("email")
    private String email;

    @Field("ref_user")
    private String refUser;

    @Field("age")
    private Integer age;

    @Field("niveau")
    private String niveau;

    @Field("ecole")
    private String ecole;

    @Field("date_de_naissance")
    private LocalDate dateDeNaissance;

    @Field("nom_parent")
    private String nomParent;

    @Field("password")
    private String password;

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

    public Ecolier id(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Ecolier firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Ecolier lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Ecolier email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefUser() {
        return this.refUser;
    }

    public Ecolier refUser(String refUser) {
        this.refUser = refUser;
        return this;
    }

    public void setRefUser(String refUser) {
        this.refUser = refUser;
    }

    public Integer getAge() {
        return this.age;
    }

    public Ecolier age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNiveau() {
        return this.niveau;
    }

    public Ecolier niveau(String niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getEcole() {
        return this.ecole;
    }

    public Ecolier ecole(String ecole) {
        this.ecole = ecole;
        return this;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public LocalDate getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    public Ecolier dateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
        return this;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getNomParent() {
        return this.nomParent;
    }

    public Ecolier nomParent(String nomParent) {
        this.nomParent = nomParent;
        return this;
    }

    public void setNomParent(String nomParent) {
        this.nomParent = nomParent;
    }

    public String getPassword() {
        return this.password;
    }

    public Ecolier password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Ecolier createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Ecolier createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Ecolier deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public Ecolier deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedDate() {
        return this.deletedDate;
    }

    public Ecolier deletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public Ecolier competition(Competition competition) {
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
        if (!(o instanceof Ecolier)) {
            return false;
        }
        return id != null && id.equals(((Ecolier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ecolier{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", refUser='" + getRefUser() + "'" +
            ", age=" + getAge() +
            ", niveau='" + getNiveau() + "'" +
            ", ecole='" + getEcole() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", nomParent='" + getNomParent() + "'" +
            ", password='" + getPassword() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
