package com.immosync.gesttravaux.services;

import com.immosync.gesttravaux.dao.ProprietaireDAO;
import com.immosync.gesttravaux.models.Proprietaire;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Proprietaire business logic.
 * Handles validation and business rules for property owners.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class ProprietaireService {

    private final ProprietaireDAO proprietaireDAO;

    /**
     * Constructor.
     */
    public ProprietaireService() {
        this.proprietaireDAO = new ProprietaireDAO();
    }

    /**
     * Creates a new proprietaire.
     *
     * @param proprietaire the proprietaire to create
     * @return the created proprietaire
     * @throws IllegalArgumentException if validation fails
     */
    public Proprietaire createProprietaire(Proprietaire proprietaire) {
        validateProprietaire(proprietaire);

        // Check if email already exists
        Proprietaire existing = proprietaireDAO.findByEmail(proprietaire.getEmailProprietaire());
        if (existing != null) {
            throw new IllegalArgumentException("Un propriétaire avec cet email existe déjà");
        }

        return proprietaireDAO.save(proprietaire);
    }

    /**
     * Updates an existing proprietaire.
     *
     * @param proprietaire the proprietaire to update
     * @return the updated proprietaire
     * @throws IllegalArgumentException if validation fails
     */
    public Proprietaire updateProprietaire(Proprietaire proprietaire) {
        validateProprietaire(proprietaire);

        if (proprietaire.getIdProprietaire() == null) {
            throw new IllegalArgumentException("L'ID du propriétaire est requis pour la mise à jour");
        }

        // Check if email is unique (excluding current proprietaire)
        Proprietaire existing = proprietaireDAO.findByEmail(proprietaire.getEmailProprietaire());
        if (existing != null && !existing.getIdProprietaire().equals(proprietaire.getIdProprietaire())) {
            throw new IllegalArgumentException("Un autre propriétaire avec cet email existe déjà");
        }

        return proprietaireDAO.update(proprietaire);
    }

    /**
     * Deletes a proprietaire.
     *
     * @param proprietaireId the proprietaire ID
     */
    public void deleteProprietaire(Integer proprietaireId) {
        Optional<Proprietaire> proprietaire = proprietaireDAO.findById(proprietaireId);
        if (proprietaire.isPresent()) {
            proprietaireDAO.delete(proprietaire.get());
        } else {
            throw new IllegalArgumentException("Propriétaire non trouvé");
        }
    }

    /**
     * Finds a proprietaire by ID.
     *
     * @param id the proprietaire ID
     * @return the proprietaire if found
     */
    public Optional<Proprietaire> findById(Integer id) {
        return proprietaireDAO.findById(id);
    }

    /**
     * Gets all proprietaires.
     *
     * @return list of all proprietaires
     */
    public List<Proprietaire> getAllProprietaires() {
        return proprietaireDAO.findAllOrdered();
    }

    /**
     * Searches proprietaires by name.
     *
     * @param searchTerm the search term
     * @return list of matching proprietaires
     */
    public List<Proprietaire> searchByName(String searchTerm) {
        return proprietaireDAO.searchByName(searchTerm);
    }

    /**
     * Validates a proprietaire.
     *
     * @param proprietaire the proprietaire to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateProprietaire(Proprietaire proprietaire) {
        if (proprietaire == null) {
            throw new IllegalArgumentException("Le propriétaire ne peut pas être null");
        }

        if (proprietaire.getNomProprietaire() == null || proprietaire.getNomProprietaire().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du propriétaire est requis");
        }

        if (proprietaire.getPrenomProprietaire() == null || proprietaire.getPrenomProprietaire().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom du propriétaire est requis");
        }

        if (proprietaire.getEmailProprietaire() == null || proprietaire.getEmailProprietaire().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email du propriétaire est requis");
        }

        // Basic email validation
        if (!proprietaire.getEmailProprietaire().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("L'email du propriétaire n'est pas valide");
        }
    }
}
