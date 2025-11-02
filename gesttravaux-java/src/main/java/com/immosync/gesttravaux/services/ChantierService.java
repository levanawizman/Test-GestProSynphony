package com.immosync.gesttravaux.services;

import com.immosync.gesttravaux.dao.ChantierDAO;
import com.immosync.gesttravaux.models.Chantier;
import com.immosync.gesttravaux.models.Devis;
import com.immosync.gesttravaux.models.Document;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Chantier business logic.
 * Handles validation and business rules for construction sites.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class ChantierService {

    private final ChantierDAO chantierDAO;

    /**
     * Constructor.
     */
    public ChantierService() {
        this.chantierDAO = new ChantierDAO();
    }

    /**
     * Creates a new chantier.
     *
     * @param chantier the chantier to create
     * @return the created chantier
     * @throws IllegalArgumentException if validation fails
     */
    public Chantier createChantier(Chantier chantier) {
        validateChantier(chantier);
        return chantierDAO.save(chantier);
    }

    /**
     * Updates an existing chantier.
     *
     * @param chantier the chantier to update
     * @return the updated chantier
     * @throws IllegalArgumentException if validation fails
     */
    public Chantier updateChantier(Chantier chantier) {
        validateChantier(chantier);

        if (chantier.getIdChantier() == null) {
            throw new IllegalArgumentException("L'ID du chantier est requis pour la mise à jour");
        }

        return chantierDAO.update(chantier);
    }

    /**
     * Deletes a chantier.
     *
     * @param chantierId the chantier ID
     */
    public void deleteChantier(Integer chantierId) {
        Optional<Chantier> chantier = chantierDAO.findById(chantierId);
        if (chantier.isPresent()) {
            chantierDAO.delete(chantier.get());
        } else {
            throw new IllegalArgumentException("Chantier non trouvé");
        }
    }

    /**
     * Finds a chantier by ID.
     *
     * @param id the chantier ID
     * @return the chantier if found
     */
    public Optional<Chantier> findById(Integer id) {
        return chantierDAO.findById(id);
    }

    /**
     * Gets all chantiers.
     *
     * @return list of all chantiers
     */
    public List<Chantier> getAllChantiers() {
        return chantierDAO.findAllOrdered();
    }

    /**
     * Searches chantiers.
     *
     * @param searchTerm the search term
     * @return list of matching chantiers
     */
    public List<Chantier> search(String searchTerm) {
        return chantierDAO.search(searchTerm);
    }

    /**
     * Finds chantiers by status.
     *
     * @param statut the status
     * @return list of chantiers with the specified status
     */
    public List<Chantier> findByStatut(Integer statut) {
        return chantierDAO.findByStatut(statut);
    }

    /**
     * Finds chantiers by property.
     *
     * @param bienId the property ID
     * @return list of chantiers for the specified property
     */
    public List<Chantier> findByBien(Integer bienId) {
        return chantierDAO.findByBien(bienId);
    }

    /**
     * Finds chantiers by inspector.
     *
     * @param inspecteurId the inspector ID
     * @return list of chantiers assigned to the specified inspector
     */
    public List<Chantier> findByInspecteur(Integer inspecteurId) {
        return chantierDAO.findByInspecteur(inspecteurId);
    }

    /**
     * Assigns an inspector to a chantier.
     *
     * @param chantierId the chantier ID
     * @param inspecteur the inspector to assign
     * @return the updated chantier
     */
    public Chantier assignInspecteur(Integer chantierId, com.immosync.gesttravaux.models.Inspecteur inspecteur) {
        Optional<Chantier> optChantier = chantierDAO.findById(chantierId);
        if (optChantier.isPresent()) {
            Chantier chantier = optChantier.get();
            chantier.setInspecteur(inspecteur);
            return chantierDAO.update(chantier);
        }
        throw new IllegalArgumentException("Chantier non trouvé");
    }

    /**
     * Adds a document to a chantier.
     *
     * @param chantierId the chantier ID
     * @param document the document to add
     * @return the updated chantier
     */
    public Chantier addDocument(Integer chantierId, Document document) {
        Optional<Chantier> optChantier = chantierDAO.findById(chantierId);
        if (optChantier.isPresent()) {
            Chantier chantier = optChantier.get();
            chantier.addDocument(document);
            return chantierDAO.update(chantier);
        }
        throw new IllegalArgumentException("Chantier non trouvé");
    }

    /**
     * Adds a devis to a chantier.
     *
     * @param chantierId the chantier ID
     * @param devis the devis to add
     * @return the updated chantier
     */
    public Chantier addDevis(Integer chantierId, Devis devis) {
        Optional<Chantier> optChantier = chantierDAO.findById(chantierId);
        if (optChantier.isPresent()) {
            Chantier chantier = optChantier.get();
            chantier.addDevis(devis);
            return chantierDAO.update(chantier);
        }
        throw new IllegalArgumentException("Chantier non trouvé");
    }

    /**
     * Updates the status of a chantier.
     *
     * @param chantierId the chantier ID
     * @param statut the new status
     * @return the updated chantier
     */
    public Chantier updateStatut(Integer chantierId, Integer statut) {
        if (statut < 0 || statut > 2) {
            throw new IllegalArgumentException("Le statut doit être 0 (non démarré), 1 (en cours) ou 2 (terminé)");
        }

        Optional<Chantier> optChantier = chantierDAO.findById(chantierId);
        if (optChantier.isPresent()) {
            Chantier chantier = optChantier.get();
            chantier.setStatutChantier(statut);
            return chantierDAO.update(chantier);
        }
        throw new IllegalArgumentException("Chantier non trouvé");
    }

    /**
     * Validates a chantier.
     *
     * @param chantier the chantier to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateChantier(Chantier chantier) {
        if (chantier == null) {
            throw new IllegalArgumentException("Le chantier ne peut pas être null");
        }

        if (chantier.getAdresseChantier() == null || chantier.getAdresseChantier().trim().isEmpty()) {
            throw new IllegalArgumentException("L'adresse du chantier est requise");
        }

        if (chantier.getVilleChantier() == null || chantier.getVilleChantier().trim().isEmpty()) {
            throw new IllegalArgumentException("La ville du chantier est requise");
        }

        if (chantier.getBien() == null) {
            throw new IllegalArgumentException("Le bien associé au chantier est requis");
        }

        if (chantier.getStatutChantier() != null && (chantier.getStatutChantier() < 0 || chantier.getStatutChantier() > 2)) {
            throw new IllegalArgumentException("Le statut doit être 0 (non démarré), 1 (en cours) ou 2 (terminé)");
        }
    }
}
