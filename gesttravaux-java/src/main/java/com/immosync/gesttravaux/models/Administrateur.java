package com.immosync.gesttravaux.models;

import jakarta.persistence.*;

/**
 * Entity representing an administrator user.
 * Administrators have access rights to manage the system.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "ADMINISTRATEURS")
public class Administrateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAdministrateur")
    private Integer idAdministrateur;

    @Column(name = "nomAdministrateur", length = 45, nullable = false)
    private String nomAdministrateur;

    @Column(name = "prenomAdministrateur", length = 45, nullable = false)
    private String prenomAdministrateur;

    @Column(name = "mdpAdministrateur", length = 45, nullable = false)
    private String mdpAdministrateur;

    @Column(name = "droitAdministrateur", length = 45)
    private String droitAdministrateur;

    /**
     * Default constructor required by JPA.
     */
    public Administrateur() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param nomAdministrateur administrator last name
     * @param prenomAdministrateur administrator first name
     * @param mdpAdministrateur administrator password
     */
    public Administrateur(String nomAdministrateur, String prenomAdministrateur, String mdpAdministrateur) {
        this.nomAdministrateur = nomAdministrateur;
        this.prenomAdministrateur = prenomAdministrateur;
        this.mdpAdministrateur = mdpAdministrateur;
    }

    // Getters and Setters

    /**
     * Gets the administrator ID.
     *
     * @return the administrator ID
     */
    public Integer getIdAdministrateur() {
        return idAdministrateur;
    }

    /**
     * Sets the administrator ID.
     *
     * @param idAdministrateur the administrator ID
     */
    public void setIdAdministrateur(Integer idAdministrateur) {
        this.idAdministrateur = idAdministrateur;
    }

    /**
     * Gets the administrator last name.
     *
     * @return the last name
     */
    public String getNomAdministrateur() {
        return nomAdministrateur;
    }

    /**
     * Sets the administrator last name.
     *
     * @param nomAdministrateur the last name
     */
    public void setNomAdministrateur(String nomAdministrateur) {
        this.nomAdministrateur = nomAdministrateur;
    }

    /**
     * Gets the administrator first name.
     *
     * @return the first name
     */
    public String getPrenomAdministrateur() {
        return prenomAdministrateur;
    }

    /**
     * Sets the administrator first name.
     *
     * @param prenomAdministrateur the first name
     */
    public void setPrenomAdministrateur(String prenomAdministrateur) {
        this.prenomAdministrateur = prenomAdministrateur;
    }

    /**
     * Gets the administrator password.
     *
     * @return the password
     */
    public String getMdpAdministrateur() {
        return mdpAdministrateur;
    }

    /**
     * Sets the administrator password.
     *
     * @param mdpAdministrateur the password
     */
    public void setMdpAdministrateur(String mdpAdministrateur) {
        this.mdpAdministrateur = mdpAdministrateur;
    }

    /**
     * Gets the administrator rights.
     *
     * @return the rights
     */
    public String getDroitAdministrateur() {
        return droitAdministrateur;
    }

    /**
     * Sets the administrator rights.
     *
     * @param droitAdministrateur the rights
     */
    public void setDroitAdministrateur(String droitAdministrateur) {
        this.droitAdministrateur = droitAdministrateur;
    }

    /**
     * Gets the full name of the administrator.
     *
     * @return full name (first name + last name)
     */
    public String getFullName() {
        return prenomAdministrateur + " " + nomAdministrateur;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Administrateur)) return false;
        Administrateur that = (Administrateur) o;
        return idAdministrateur != null && idAdministrateur.equals(that.getIdAdministrateur());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
