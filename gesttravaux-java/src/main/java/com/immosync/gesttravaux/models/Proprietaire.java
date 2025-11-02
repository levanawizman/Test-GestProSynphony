package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a property owner.
 * A proprietaire can own multiple properties (biens).
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "PROPRIETAIRES")
public class Proprietaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProprietaire")
    private Integer idProprietaire;

    @Column(name = "nomProprietaire", length = 45, nullable = false)
    private String nomProprietaire;

    @Column(name = "prenomProprietaire", length = 45, nullable = false)
    private String prenomProprietaire;

    @Column(name = "emailProprietaire", length = 45, nullable = false)
    private String emailProprietaire;

    @Column(name = "telProprietaire", length = 45)
    private String telProprietaire;

    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Bien> biens = new HashSet<>();

    /**
     * Default constructor required by JPA.
     */
    public Proprietaire() {
    }

    /**
     * Constructor with essential fields.
     *
     * @param nomProprietaire owner last name
     * @param prenomProprietaire owner first name
     * @param emailProprietaire owner email
     */
    public Proprietaire(String nomProprietaire, String prenomProprietaire, String emailProprietaire) {
        this.nomProprietaire = nomProprietaire;
        this.prenomProprietaire = prenomProprietaire;
        this.emailProprietaire = emailProprietaire;
    }

    // Getters and Setters

    /**
     * Gets the proprietaire ID.
     *
     * @return the proprietaire ID
     */
    public Integer getIdProprietaire() {
        return idProprietaire;
    }

    /**
     * Sets the proprietaire ID.
     *
     * @param idProprietaire the proprietaire ID
     */
    public void setIdProprietaire(Integer idProprietaire) {
        this.idProprietaire = idProprietaire;
    }

    /**
     * Gets the proprietaire last name.
     *
     * @return the last name
     */
    public String getNomProprietaire() {
        return nomProprietaire;
    }

    /**
     * Sets the proprietaire last name.
     *
     * @param nomProprietaire the last name
     */
    public void setNomProprietaire(String nomProprietaire) {
        this.nomProprietaire = nomProprietaire;
    }

    /**
     * Gets the proprietaire first name.
     *
     * @return the first name
     */
    public String getPrenomProprietaire() {
        return prenomProprietaire;
    }

    /**
     * Sets the proprietaire first name.
     *
     * @param prenomProprietaire the first name
     */
    public void setPrenomProprietaire(String prenomProprietaire) {
        this.prenomProprietaire = prenomProprietaire;
    }

    /**
     * Gets the proprietaire email.
     *
     * @return the email
     */
    public String getEmailProprietaire() {
        return emailProprietaire;
    }

    /**
     * Sets the proprietaire email.
     *
     * @param emailProprietaire the email
     */
    public void setEmailProprietaire(String emailProprietaire) {
        this.emailProprietaire = emailProprietaire;
    }

    /**
     * Gets the proprietaire phone number.
     *
     * @return the phone number
     */
    public String getTelProprietaire() {
        return telProprietaire;
    }

    /**
     * Sets the proprietaire phone number.
     *
     * @param telProprietaire the phone number
     */
    public void setTelProprietaire(String telProprietaire) {
        this.telProprietaire = telProprietaire;
    }

    /**
     * Gets the properties owned by this proprietaire.
     *
     * @return set of biens
     */
    public Set<Bien> getBiens() {
        return biens;
    }

    /**
     * Sets the properties owned by this proprietaire.
     *
     * @param biens set of biens
     */
    public void setBiens(Set<Bien> biens) {
        this.biens = biens;
    }

    /**
     * Adds a property to this proprietaire.
     *
     * @param bien the property to add
     */
    public void addBien(Bien bien) {
        biens.add(bien);
        bien.setProprietaire(this);
    }

    /**
     * Removes a property from this proprietaire.
     *
     * @param bien the property to remove
     */
    public void removeBien(Bien bien) {
        biens.remove(bien);
        bien.setProprietaire(null);
    }

    /**
     * Gets the full name of the proprietaire.
     *
     * @return full name (first name + last name)
     */
    public String getFullName() {
        return prenomProprietaire + " " + nomProprietaire;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proprietaire)) return false;
        Proprietaire that = (Proprietaire) o;
        return idProprietaire != null && idProprietaire.equals(that.getIdProprietaire());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
