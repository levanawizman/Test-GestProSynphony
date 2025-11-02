package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a quote (devis) for a construction project.
 * A devis is associated with a prestation, entrepreneur, and chantier.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "DEVIS")
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDevis")
    private Integer idDevis;

    @Column(name = "prix")
    private Integer prix;

    @Column(name = "duree", length = 45)
    private String duree;

    @Column(name = "dateCreation")
    private LocalDateTime dateCreation;

    @Column(name = "statut", length = 20)
    private String statut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRESTATIONS_idPrestation", nullable = false)
    private Prestation prestation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ENTREPRENEURS_idEntrepreneur", nullable = false)
    private Entrepreneur entrepreneur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHANTIERS_idChantier", nullable = false)
    private Chantier chantier;

    /**
     * Default constructor required by JPA.
     */
    public Devis() {
        this.dateCreation = LocalDateTime.now();
        this.statut = "En attente";
    }

    /**
     * Constructor with essential fields.
     *
     * @param prix devis price
     * @param duree estimated duration
     * @param prestation associated prestation
     * @param entrepreneur assigned entrepreneur
     * @param chantier associated chantier
     */
    public Devis(Integer prix, String duree, Prestation prestation, Entrepreneur entrepreneur, Chantier chantier) {
        this.prix = prix;
        this.duree = duree;
        this.prestation = prestation;
        this.entrepreneur = entrepreneur;
        this.chantier = chantier;
        this.dateCreation = LocalDateTime.now();
        this.statut = "En attente";
    }

    // Getters and Setters

    /**
     * Gets the devis ID.
     *
     * @return the devis ID
     */
    public Integer getIdDevis() {
        return idDevis;
    }

    /**
     * Sets the devis ID.
     *
     * @param idDevis the devis ID
     */
    public void setIdDevis(Integer idDevis) {
        this.idDevis = idDevis;
    }

    /**
     * Gets the devis price.
     *
     * @return the price
     */
    public Integer getPrix() {
        return prix;
    }

    /**
     * Sets the devis price.
     *
     * @param prix the price
     */
    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    /**
     * Gets the estimated duration.
     *
     * @return the duration
     */
    public String getDuree() {
        return duree;
    }

    /**
     * Sets the estimated duration.
     *
     * @param duree the duration
     */
    public void setDuree(String duree) {
        this.duree = duree;
    }

    /**
     * Gets the creation date.
     *
     * @return the creation date
     */
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    /**
     * Sets the creation date.
     *
     * @param dateCreation the creation date
     */
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * Gets the devis status.
     *
     * @return the status
     */
    public String getStatut() {
        return statut;
    }

    /**
     * Sets the devis status.
     *
     * @param statut the status
     */
    public void setStatut(String statut) {
        this.statut = statut;
    }

    /**
     * Gets the associated prestation.
     *
     * @return the prestation
     */
    public Prestation getPrestation() {
        return prestation;
    }

    /**
     * Sets the associated prestation.
     *
     * @param prestation the prestation
     */
    public void setPrestation(Prestation prestation) {
        this.prestation = prestation;
    }

    /**
     * Gets the assigned entrepreneur.
     *
     * @return the entrepreneur
     */
    public Entrepreneur getEntrepreneur() {
        return entrepreneur;
    }

    /**
     * Sets the assigned entrepreneur.
     *
     * @param entrepreneur the entrepreneur
     */
    public void setEntrepreneur(Entrepreneur entrepreneur) {
        this.entrepreneur = entrepreneur;
    }

    /**
     * Gets the associated chantier.
     *
     * @return the chantier
     */
    public Chantier getChantier() {
        return chantier;
    }

    /**
     * Sets the associated chantier.
     *
     * @param chantier the chantier
     */
    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    @Override
    public String toString() {
        return "Devis #" + idDevis + " - " + prix + " EUR - " + duree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Devis)) return false;
        Devis devis = (Devis) o;
        return idDevis != null && idDevis.equals(devis.getIdDevis());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
