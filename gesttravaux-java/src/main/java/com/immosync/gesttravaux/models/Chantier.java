package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a construction site (chantier).
 * A chantier is associated with a property (bien), an inspector, and can have multiple devis and documents.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "CHANTIERS")
public class Chantier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idChantier")
    private Integer idChantier;

    @Column(name = "villeChantier", length = 45, nullable = false)
    private String villeChantier;

    @Column(name = "adresseChantier", length = 255, nullable = false)
    private String adresseChantier;

    @Column(name = "infoChantier", length = 255)
    private String infoChantier;

    @Column(name = "statutChantier")
    private Integer statutChantier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INSPECTEURS_idInspecteur")
    private Inspecteur inspecteur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BIENS_idBien", nullable = false)
    private Bien bien;

    @OneToMany(mappedBy = "chantier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Devis> devisList = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "CHANTIERS_has_DOCUMENTS",
        joinColumns = @JoinColumn(name = "CHANTIERS_idChantier"),
        inverseJoinColumns = @JoinColumn(name = "DOCUMENTS_idDOCUMENTS")
    )
    private Set<Document> documents = new HashSet<>();

    /**
     * Default constructor required by JPA.
     */
    public Chantier() {
        this.statutChantier = 0; // Default status: not started
    }

    /**
     * Constructor with essential fields.
     *
     * @param villeChantier chantier city
     * @param adresseChantier chantier address
     * @param bien associated property
     */
    public Chantier(String villeChantier, String adresseChantier, Bien bien) {
        this.villeChantier = villeChantier;
        this.adresseChantier = adresseChantier;
        this.bien = bien;
        this.statutChantier = 0;
    }

    // Getters and Setters

    /**
     * Gets the chantier ID.
     *
     * @return the chantier ID
     */
    public Integer getIdChantier() {
        return idChantier;
    }

    /**
     * Sets the chantier ID.
     *
     * @param idChantier the chantier ID
     */
    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    /**
     * Gets the chantier city.
     *
     * @return the city
     */
    public String getVilleChantier() {
        return villeChantier;
    }

    /**
     * Sets the chantier city.
     *
     * @param villeChantier the city
     */
    public void setVilleChantier(String villeChantier) {
        this.villeChantier = villeChantier;
    }

    /**
     * Gets the chantier address.
     *
     * @return the address
     */
    public String getAdresseChantier() {
        return adresseChantier;
    }

    /**
     * Sets the chantier address.
     *
     * @param adresseChantier the address
     */
    public void setAdresseChantier(String adresseChantier) {
        this.adresseChantier = adresseChantier;
    }

    /**
     * Gets the chantier information.
     *
     * @return the information
     */
    public String getInfoChantier() {
        return infoChantier;
    }

    /**
     * Sets the chantier information.
     *
     * @param infoChantier the information
     */
    public void setInfoChantier(String infoChantier) {
        this.infoChantier = infoChantier;
    }

    /**
     * Gets the chantier status.
     * 0 = Not started, 1 = In progress, 2 = Completed
     *
     * @return the status
     */
    public Integer getStatutChantier() {
        return statutChantier;
    }

    /**
     * Sets the chantier status.
     *
     * @param statutChantier the status
     */
    public void setStatutChantier(Integer statutChantier) {
        this.statutChantier = statutChantier;
    }

    /**
     * Gets the assigned inspector.
     *
     * @return the inspector
     */
    public Inspecteur getInspecteur() {
        return inspecteur;
    }

    /**
     * Sets the assigned inspector.
     *
     * @param inspecteur the inspector
     */
    public void setInspecteur(Inspecteur inspecteur) {
        this.inspecteur = inspecteur;
    }

    /**
     * Gets the associated property.
     *
     * @return the bien
     */
    public Bien getBien() {
        return bien;
    }

    /**
     * Sets the associated property.
     *
     * @param bien the bien
     */
    public void setBien(Bien bien) {
        this.bien = bien;
    }

    /**
     * Gets the list of devis for this chantier.
     *
     * @return set of devis
     */
    public Set<Devis> getDevisList() {
        return devisList;
    }

    /**
     * Sets the list of devis for this chantier.
     *
     * @param devisList set of devis
     */
    public void setDevisList(Set<Devis> devisList) {
        this.devisList = devisList;
    }

    /**
     * Gets the documents associated with this chantier.
     *
     * @return set of documents
     */
    public Set<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the documents associated with this chantier.
     *
     * @param documents set of documents
     */
    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    /**
     * Adds a document to this chantier.
     *
     * @param document the document to add
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Removes a document from this chantier.
     *
     * @param document the document to remove
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Adds a devis to this chantier.
     *
     * @param devis the devis to add
     */
    public void addDevis(Devis devis) {
        devisList.add(devis);
        devis.setChantier(this);
    }

    /**
     * Removes a devis from this chantier.
     *
     * @param devis the devis to remove
     */
    public void removeDevis(Devis devis) {
        devisList.remove(devis);
        devis.setChantier(null);
    }

    /**
     * Gets the status as a readable string.
     *
     * @return status string
     */
    public String getStatutString() {
        if (statutChantier == null) return "Non défini";
        switch (statutChantier) {
            case 0: return "Non démarré";
            case 1: return "En cours";
            case 2: return "Terminé";
            default: return "Inconnu";
        }
    }

    @Override
    public String toString() {
        return "Chantier " + idChantier + " - " + adresseChantier + ", " + villeChantier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chantier)) return false;
        Chantier chantier = (Chantier) o;
        return idChantier != null && idChantier.equals(chantier.getIdChantier());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
