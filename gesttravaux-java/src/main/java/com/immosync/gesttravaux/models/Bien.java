package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a property (bien immobilier).
 * Each property belongs to one proprietaire and can have multiple chantiers.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "BIENS")
public class Bien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBien")
    private Integer idBien;

    @Column(name = "adresseBien", length = 45, nullable = false)
    private String adresseBien;

    @Column(name = "villeBien", length = 45, nullable = false)
    private String villeBien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROPRIETAIRES_idProprietaire", nullable = false)
    private Proprietaire proprietaire;

    @OneToMany(mappedBy = "bien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Chantier> chantiers = new HashSet<>();

    /**
     * Default constructor required by JPA.
     */
    public Bien() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param adresseBien property address
     * @param villeBien property city
     * @param proprietaire property owner
     */
    public Bien(String adresseBien, String villeBien, Proprietaire proprietaire) {
        this.adresseBien = adresseBien;
        this.villeBien = villeBien;
        this.proprietaire = proprietaire;
    }

    // Getters and Setters

    /**
     * Gets the property ID.
     *
     * @return the property ID
     */
    public Integer getIdBien() {
        return idBien;
    }

    /**
     * Sets the property ID.
     *
     * @param idBien the property ID
     */
    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
    }

    /**
     * Gets the property address.
     *
     * @return the address
     */
    public String getAdresseBien() {
        return adresseBien;
    }

    /**
     * Sets the property address.
     *
     * @param adresseBien the address
     */
    public void setAdresseBien(String adresseBien) {
        this.adresseBien = adresseBien;
    }

    /**
     * Gets the property city.
     *
     * @return the city
     */
    public String getVilleBien() {
        return villeBien;
    }

    /**
     * Sets the property city.
     *
     * @param villeBien the city
     */
    public void setVilleBien(String villeBien) {
        this.villeBien = villeBien;
    }

    /**
     * Gets the property owner.
     *
     * @return the proprietaire
     */
    public Proprietaire getProprietaire() {
        return proprietaire;
    }

    /**
     * Sets the property owner.
     *
     * @param proprietaire the proprietaire
     */
    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }

    /**
     * Gets the chantiers associated with this property.
     *
     * @return set of chantiers
     */
    public Set<Chantier> getChantiers() {
        return chantiers;
    }

    /**
     * Sets the chantiers associated with this property.
     *
     * @param chantiers set of chantiers
     */
    public void setChantiers(Set<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    /**
     * Adds a chantier to this property.
     *
     * @param chantier the chantier to add
     */
    public void addChantier(Chantier chantier) {
        chantiers.add(chantier);
        chantier.setBien(this);
    }

    /**
     * Removes a chantier from this property.
     *
     * @param chantier the chantier to remove
     */
    public void removeChantier(Chantier chantier) {
        chantiers.remove(chantier);
        chantier.setBien(null);
    }

    /**
     * Gets the full address of the property.
     *
     * @return full address (address, city)
     */
    public String getFullAddress() {
        return adresseBien + ", " + villeBien;
    }

    @Override
    public String toString() {
        return getFullAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bien)) return false;
        Bien bien = (Bien) o;
        return idBien != null && idBien.equals(bien.getIdBien());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
