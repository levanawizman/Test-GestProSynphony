package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an inspector who supervises construction sites.
 * Inspectors can be assigned to multiple chantiers.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "INSPECTEURS")
public class Inspecteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInspecteur")
    private Integer idInspecteur;

    @Column(name = "nomInspecteur", length = 45, nullable = false)
    private String nomInspecteur;

    @Column(name = "prenomInspecteur", length = 45, nullable = false)
    private String prenomInspecteur;

    @Column(name = "emailInspecteur", length = 45, nullable = false)
    private String emailInspecteur;

    @Column(name = "telInspecteur", length = 45)
    private String telInspecteur;

    @Column(name = "secteurInspecteur", length = 45)
    private String secteurInspecteur;

    @OneToMany(mappedBy = "inspecteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Chantier> chantiers = new HashSet<>();

    /**
     * Default constructor required by JPA.
     */
    public Inspecteur() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param nomInspecteur inspector last name
     * @param prenomInspecteur inspector first name
     * @param emailInspecteur inspector email
     */
    public Inspecteur(String nomInspecteur, String prenomInspecteur, String emailInspecteur) {
        this.nomInspecteur = nomInspecteur;
        this.prenomInspecteur = prenomInspecteur;
        this.emailInspecteur = emailInspecteur;
    }

    // Getters and Setters

    /**
     * Gets the inspector ID.
     *
     * @return the inspector ID
     */
    public Integer getIdInspecteur() {
        return idInspecteur;
    }

    /**
     * Sets the inspector ID.
     *
     * @param idInspecteur the inspector ID
     */
    public void setIdInspecteur(Integer idInspecteur) {
        this.idInspecteur = idInspecteur;
    }

    /**
     * Gets the inspector last name.
     *
     * @return the last name
     */
    public String getNomInspecteur() {
        return nomInspecteur;
    }

    /**
     * Sets the inspector last name.
     *
     * @param nomInspecteur the last name
     */
    public void setNomInspecteur(String nomInspecteur) {
        this.nomInspecteur = nomInspecteur;
    }

    /**
     * Gets the inspector first name.
     *
     * @return the first name
     */
    public String getPrenomInspecteur() {
        return prenomInspecteur;
    }

    /**
     * Sets the inspector first name.
     *
     * @param prenomInspecteur the first name
     */
    public void setPrenomInspecteur(String prenomInspecteur) {
        this.prenomInspecteur = prenomInspecteur;
    }

    /**
     * Gets the inspector email.
     *
     * @return the email
     */
    public String getEmailInspecteur() {
        return emailInspecteur;
    }

    /**
     * Sets the inspector email.
     *
     * @param emailInspecteur the email
     */
    public void setEmailInspecteur(String emailInspecteur) {
        this.emailInspecteur = emailInspecteur;
    }

    /**
     * Gets the inspector phone number.
     *
     * @return the phone number
     */
    public String getTelInspecteur() {
        return telInspecteur;
    }

    /**
     * Sets the inspector phone number.
     *
     * @param telInspecteur the phone number
     */
    public void setTelInspecteur(String telInspecteur) {
        this.telInspecteur = telInspecteur;
    }

    /**
     * Gets the inspector sector/area.
     *
     * @return the sector
     */
    public String getSecteurInspecteur() {
        return secteurInspecteur;
    }

    /**
     * Sets the inspector sector/area.
     *
     * @param secteurInspecteur the sector
     */
    public void setSecteurInspecteur(String secteurInspecteur) {
        this.secteurInspecteur = secteurInspecteur;
    }

    /**
     * Gets the chantiers assigned to this inspector.
     *
     * @return set of chantiers
     */
    public Set<Chantier> getChantiers() {
        return chantiers;
    }

    /**
     * Sets the chantiers assigned to this inspector.
     *
     * @param chantiers set of chantiers
     */
    public void setChantiers(Set<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    /**
     * Gets the full name of the inspector.
     *
     * @return full name (first name + last name)
     */
    public String getFullName() {
        return prenomInspecteur + " " + nomInspecteur;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inspecteur)) return false;
        Inspecteur that = (Inspecteur) o;
        return idInspecteur != null && idInspecteur.equals(that.getIdInspecteur());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
