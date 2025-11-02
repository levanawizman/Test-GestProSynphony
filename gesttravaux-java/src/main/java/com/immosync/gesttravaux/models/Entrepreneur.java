package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an entrepreneur who can work on construction projects.
 * Entrepreneurs can have multiple categories of expertise.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "ENTREPRENEURS")
public class Entrepreneur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEntrepreneur")
    private Integer idEntrepreneur;

    @Column(name = "nomEntrepreneur", length = 100, nullable = false)
    private String nomEntrepreneur;

    @Column(name = "prenomEntrepreneur", length = 100, nullable = false)
    private String prenomEntrepreneur;

    @Column(name = "emailEntrepreneur", length = 100, nullable = false)
    private String emailEntrepreneur;

    @Column(name = "telEntrepreneur", length = 45)
    private String telEntrepreneur;

    @Column(name = "villeDeploiement", length = 255)
    private String villeDeploiement;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "ENTREPRENEURS_has_CATEGORIES",
        joinColumns = @JoinColumn(name = "ENTREPRENEURS_idEntrepreneur"),
        inverseJoinColumns = @JoinColumn(name = "CATEGORIES_idCategorie")
    )
    private Set<Categorie> categories = new HashSet<>();

    @OneToMany(mappedBy = "entrepreneur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Devis> devisList = new HashSet<>();

    /**
     * Default constructor required by JPA.
     */
    public Entrepreneur() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param nomEntrepreneur entrepreneur last name
     * @param prenomEntrepreneur entrepreneur first name
     * @param emailEntrepreneur entrepreneur email
     */
    public Entrepreneur(String nomEntrepreneur, String prenomEntrepreneur, String emailEntrepreneur) {
        this.nomEntrepreneur = nomEntrepreneur;
        this.prenomEntrepreneur = prenomEntrepreneur;
        this.emailEntrepreneur = emailEntrepreneur;
    }

    // Getters and Setters

    /**
     * Gets the entrepreneur ID.
     *
     * @return the entrepreneur ID
     */
    public Integer getIdEntrepreneur() {
        return idEntrepreneur;
    }

    /**
     * Sets the entrepreneur ID.
     *
     * @param idEntrepreneur the entrepreneur ID
     */
    public void setIdEntrepreneur(Integer idEntrepreneur) {
        this.idEntrepreneur = idEntrepreneur;
    }

    /**
     * Gets the entrepreneur last name.
     *
     * @return the last name
     */
    public String getNomEntrepreneur() {
        return nomEntrepreneur;
    }

    /**
     * Sets the entrepreneur last name.
     *
     * @param nomEntrepreneur the last name
     */
    public void setNomEntrepreneur(String nomEntrepreneur) {
        this.nomEntrepreneur = nomEntrepreneur;
    }

    /**
     * Gets the entrepreneur first name.
     *
     * @return the first name
     */
    public String getPrenomEntrepreneur() {
        return prenomEntrepreneur;
    }

    /**
     * Sets the entrepreneur first name.
     *
     * @param prenomEntrepreneur the first name
     */
    public void setPrenomEntrepreneur(String prenomEntrepreneur) {
        this.prenomEntrepreneur = prenomEntrepreneur;
    }

    /**
     * Gets the entrepreneur email.
     *
     * @return the email
     */
    public String getEmailEntrepreneur() {
        return emailEntrepreneur;
    }

    /**
     * Sets the entrepreneur email.
     *
     * @param emailEntrepreneur the email
     */
    public void setEmailEntrepreneur(String emailEntrepreneur) {
        this.emailEntrepreneur = emailEntrepreneur;
    }

    /**
     * Gets the entrepreneur phone number.
     *
     * @return the phone number
     */
    public String getTelEntrepreneur() {
        return telEntrepreneur;
    }

    /**
     * Sets the entrepreneur phone number.
     *
     * @param telEntrepreneur the phone number
     */
    public void setTelEntrepreneur(String telEntrepreneur) {
        this.telEntrepreneur = telEntrepreneur;
    }

    /**
     * Gets the deployment city.
     *
     * @return the deployment city
     */
    public String getVilleDeploiement() {
        return villeDeploiement;
    }

    /**
     * Sets the deployment city.
     *
     * @param villeDeploiement the deployment city
     */
    public void setVilleDeploiement(String villeDeploiement) {
        this.villeDeploiement = villeDeploiement;
    }

    /**
     * Gets the categories associated with this entrepreneur.
     *
     * @return set of categories
     */
    public Set<Categorie> getCategories() {
        return categories;
    }

    /**
     * Sets the categories associated with this entrepreneur.
     *
     * @param categories set of categories
     */
    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    /**
     * Gets the list of devis for this entrepreneur.
     *
     * @return set of devis
     */
    public Set<Devis> getDevisList() {
        return devisList;
    }

    /**
     * Sets the list of devis for this entrepreneur.
     *
     * @param devisList set of devis
     */
    public void setDevisList(Set<Devis> devisList) {
        this.devisList = devisList;
    }

    /**
     * Adds a category to this entrepreneur.
     *
     * @param categorie the category to add
     */
    public void addCategorie(Categorie categorie) {
        categories.add(categorie);
        categorie.getEntrepreneurs().add(this);
    }

    /**
     * Removes a category from this entrepreneur.
     *
     * @param categorie the category to remove
     */
    public void removeCategorie(Categorie categorie) {
        categories.remove(categorie);
        categorie.getEntrepreneurs().remove(this);
    }

    /**
     * Gets the full name of the entrepreneur.
     *
     * @return full name (first name + last name)
     */
    public String getFullName() {
        return prenomEntrepreneur + " " + nomEntrepreneur;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entrepreneur)) return false;
        Entrepreneur that = (Entrepreneur) o;
        return idEntrepreneur != null && idEntrepreneur.equals(that.getIdEntrepreneur());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
