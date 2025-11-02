package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a category of work/services.
 * Categories can be associated with multiple entrepreneurs and prestations.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "CATEGORIES")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategorie")
    private Integer idCategorie;

    @Column(name = "type", length = 45, nullable = false)
    private String type;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Entrepreneur> entrepreneurs = new HashSet<>();

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Prestation> prestations = new HashSet<>();

    /**
     * Default constructor required by JPA.
     */
    public Categorie() {
    }

    /**
     * Constructor with type.
     *
     * @param type the category type
     */
    public Categorie(String type) {
        this.type = type;
    }

    // Getters and Setters

    /**
     * Gets the category ID.
     *
     * @return the category ID
     */
    public Integer getIdCategorie() {
        return idCategorie;
    }

    /**
     * Sets the category ID.
     *
     * @param idCategorie the category ID
     */
    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }

    /**
     * Gets the category type.
     *
     * @return the category type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the category type.
     *
     * @param type the category type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the entrepreneurs associated with this category.
     *
     * @return set of entrepreneurs
     */
    public Set<Entrepreneur> getEntrepreneurs() {
        return entrepreneurs;
    }

    /**
     * Sets the entrepreneurs associated with this category.
     *
     * @param entrepreneurs set of entrepreneurs
     */
    public void setEntrepreneurs(Set<Entrepreneur> entrepreneurs) {
        this.entrepreneurs = entrepreneurs;
    }

    /**
     * Gets the prestations associated with this category.
     *
     * @return set of prestations
     */
    public Set<Prestation> getPrestations() {
        return prestations;
    }

    /**
     * Sets the prestations associated with this category.
     *
     * @param prestations set of prestations
     */
    public void setPrestations(Set<Prestation> prestations) {
        this.prestations = prestations;
    }

    /**
     * Adds a prestation to this category.
     *
     * @param prestation the prestation to add
     */
    public void addPrestation(Prestation prestation) {
        prestations.add(prestation);
        prestation.setCategorie(this);
    }

    /**
     * Removes a prestation from this category.
     *
     * @param prestation the prestation to remove
     */
    public void removePrestation(Prestation prestation) {
        prestations.remove(prestation);
        prestation.setCategorie(null);
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categorie)) return false;
        Categorie categorie = (Categorie) o;
        return idCategorie != null && idCategorie.equals(categorie.getIdCategorie());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
