package com.immosync.gesttravaux.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a document (inspection report, photo, etc.).
 * Documents are associated with chantiers.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@Entity
@Table(name = "DOCUMENTS")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDOCUMENTS")
    private Integer idDocument;

    @Column(name = "libelleDoc", length = 255, nullable = false)
    private String libelleDoc;

    @Column(name = "typeDoc", length = 50)
    private String typeDoc;

    @Column(name = "cheminFichier", length = 500)
    private String cheminFichier;

    @Column(name = "dateCreation")
    private LocalDateTime dateCreation;

    @Column(name = "description", length = 500)
    private String description;

    /**
     * Default constructor required by JPA.
     */
    public Document() {
        this.dateCreation = LocalDateTime.now();
    }

    /**
     * Constructor with essential fields.
     *
     * @param libelleDoc document label
     * @param typeDoc document type (photo, report, etc.)
     */
    public Document(String libelleDoc, String typeDoc) {
        this.libelleDoc = libelleDoc;
        this.typeDoc = typeDoc;
        this.dateCreation = LocalDateTime.now();
    }

    // Getters and Setters

    /**
     * Gets the document ID.
     *
     * @return the document ID
     */
    public Integer getIdDocument() {
        return idDocument;
    }

    /**
     * Sets the document ID.
     *
     * @param idDocument the document ID
     */
    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

    /**
     * Gets the document label.
     *
     * @return the label
     */
    public String getLibelleDoc() {
        return libelleDoc;
    }

    /**
     * Sets the document label.
     *
     * @param libelleDoc the label
     */
    public void setLibelleDoc(String libelleDoc) {
        this.libelleDoc = libelleDoc;
    }

    /**
     * Gets the document type.
     *
     * @return the type
     */
    public String getTypeDoc() {
        return typeDoc;
    }

    /**
     * Sets the document type.
     *
     * @param typeDoc the type
     */
    public void setTypeDoc(String typeDoc) {
        this.typeDoc = typeDoc;
    }

    /**
     * Gets the file path.
     *
     * @return the file path
     */
    public String getCheminFichier() {
        return cheminFichier;
    }

    /**
     * Sets the file path.
     *
     * @param cheminFichier the file path
     */
    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
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
     * Gets the document description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the document description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return libelleDoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        Document document = (Document) o;
        return idDocument != null && idDocument.equals(document.getIdDocument());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
