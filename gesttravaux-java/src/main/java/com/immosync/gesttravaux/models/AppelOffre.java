package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a call for tenders (appel d'offre).
 * Used to request quotes from entrepreneurs for specific work.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "APPEL_OFFRE")
public class AppelOffre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAPPEL_OFFRE")
    private Integer idAppelOffre;

    @Column(name = "Prix", length = 45)
    private String prix;

    @Column(name = "duree", length = 45)
    private String duree;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "dateCreation")
    private LocalDateTime dateCreation;

    @Column(name = "statut", length = 20)
    private String statut;

    /**
     * Default constructor required by JPA.
     */
    public AppelOffre() {
        this.dateCreation = LocalDateTime.now();
        this.statut = "Ouvert";
    }

    /**
     * Constructor with essential fields.
     *
     * @param prix expected price range
     * @param duree expected duration
     */
    public AppelOffre(String prix, String duree) {
        this.prix = prix;
        this.duree = duree;
        this.dateCreation = LocalDateTime.now();
        this.statut = "Ouvert";
    }

    // Getters and Setters

    /**
     * Gets the appel d'offre ID.
     *
     * @return the appel d'offre ID
     */
    public Integer getIdAppelOffre() {
        return idAppelOffre;
    }

    /**
     * Sets the appel d'offre ID.
     *
     * @param idAppelOffre the appel d'offre ID
     */
    public void setIdAppelOffre(Integer idAppelOffre) {
        this.idAppelOffre = idAppelOffre;
    }

    /**
     * Gets the price range.
     *
     * @return the price
     */
    public String getPrix() {
        return prix;
    }

    /**
     * Sets the price range.
     *
     * @param prix the price
     */
    public void setPrix(String prix) {
        this.prix = prix;
    }

    /**
     * Gets the expected duration.
     *
     * @return the duration
     */
    public String getDuree() {
        return duree;
    }

    /**
     * Sets the expected duration.
     *
     * @param duree the duration
     */
    public void setDuree(String duree) {
        this.duree = duree;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Gets the status.
     *
     * @return the status
     */
    public String getStatut() {
        return statut;
    }

    /**
     * Sets the status.
     *
     * @param statut the status
     */
    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Appel d'offre #" + idAppelOffre + " - " + prix + " - " + duree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppelOffre)) return false;
        AppelOffre that = (AppelOffre) o;
        return idAppelOffre != null && idAppelOffre.equals(that.getIdAppelOffre());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
