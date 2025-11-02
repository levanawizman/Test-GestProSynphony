package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a service/prestation.
 * Each prestation belongs to one category and can be included in multiple devis.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "PRESTATIONS")
public class Prestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrestation")
    private Integer idPrestation;

    @Column(name = "libelle", length = 45, nullable = false)
    private String libelle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORIES_idCategorie", nullable = false)
    private Categorie categorie;

    @OneToMany(mappedBy = "prestation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Devis> devisList = new HashSet<>();

    /**
     * Default constructor required by JPA.
     */
    public Prestation() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param libelle prestation label/name
     * @param categorie prestation category
     */
    public Prestation(String libelle, Categorie categorie) {
        this.libelle = libelle;
        this.categorie = categorie;
    }

    // Getters and Setters

    /**
     * Gets the prestation ID.
     *
     * @return the prestation ID
     */
    public Integer getIdPrestation() {
        return idPrestation;
    }

    /**
     * Sets the prestation ID.
     *
     * @param idPrestation the prestation ID
     */
    public void setIdPrestation(Integer idPrestation) {
        this.idPrestation = idPrestation;
    }

    /**
     * Gets the prestation label.
     *
     * @return the label
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Sets the prestation label.
     *
     * @param libelle the label
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Gets the prestation category.
     *
     * @return the category
     */
    public Categorie getCategorie() {
        return categorie;
    }

    /**
     * Sets the prestation category.
     *
     * @param categorie the category
     */
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    /**
     * Gets the list of devis for this prestation.
     *
     * @return set of devis
     */
    public Set<Devis> getDevisList() {
        return devisList;
    }

    /**
     * Sets the list of devis for this prestation.
     *
     * @param devisList set of devis
     */
    public void setDevisList(Set<Devis> devisList) {
        this.devisList = devisList;
    }

    @Override
    public String toString() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prestation)) return false;
        Prestation that = (Prestation) o;
        return idPrestation != null && idPrestation.equals(that.getIdPrestation());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
