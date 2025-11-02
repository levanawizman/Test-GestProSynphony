package com.immosync.gesttravaux.services;

import com.immosync.gesttravaux.dao.PrestationDAO;
import com.immosync.gesttravaux.models.Prestation;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Prestation business logic.
 * Handles validation and business rules for services/prestations.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class PrestationService {

    private final PrestationDAO prestationDAO;

    /**
     * Constructor.
     */
    public PrestationService() {
        this.prestationDAO = new PrestationDAO();
    }

    /**
     * Creates a new prestation.
     *
     * @param prestation the prestation to create
     * @return the created prestation
     * @throws IllegalArgumentException if validation fails
     */
    public Prestation createPrestation(Prestation prestation) {
        validatePrestation(prestation);
        return prestationDAO.save(prestation);
    }

    /**
     * Updates an existing prestation.
     *
     * @param prestation the prestation to update
     * @return the updated prestation
     * @throws IllegalArgumentException if validation fails
     */
    public Prestation updatePrestation(Prestation prestation) {
        validatePrestation(prestation);

        if (prestation.getIdPrestation() == null) {
            throw new IllegalArgumentException("L'ID de la prestation est requis pour la mise à jour");
        }

        return prestationDAO.update(prestation);
    }

    /**
     * Deletes a prestation.
     *
     * @param prestationId the prestation ID
     */
    public void deletePrestation(Integer prestationId) {
        Optional<Prestation> prestation = prestationDAO.findById(prestationId);
        if (prestation.isPresent()) {
            prestationDAO.delete(prestation.get());
        } else {
            throw new IllegalArgumentException("Prestation non trouvée");
        }
    }

    /**
     * Finds a prestation by ID.
     *
     * @param id the prestation ID
     * @return the prestation if found
     */
    public Optional<Prestation> findById(Integer id) {
        return prestationDAO.findById(id);
    }

    /**
     * Gets all prestations.
     *
     * @return list of all prestations
     */
    public List<Prestation> getAllPrestations() {
        return prestationDAO.findAllOrdered();
    }

    /**
     * Searches prestations by libelle.
     *
     * @param searchTerm the search term
     * @return list of matching prestations
     */
    public List<Prestation> searchByLibelle(String searchTerm) {
        return prestationDAO.searchByLibelle(searchTerm);
    }

    /**
     * Finds prestations by category.
     *
     * @param categorieId the category ID
     * @return list of prestations in the specified category
     */
    public List<Prestation> findByCategorie(Integer categorieId) {
        return prestationDAO.findByCategorie(categorieId);
    }

    /**
     * Validates a prestation.
     *
     * @param prestation the prestation to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validatePrestation(Prestation prestation) {
        if (prestation == null) {
            throw new IllegalArgumentException("La prestation ne peut pas être null");
        }

        if (prestation.getLibelle() == null || prestation.getLibelle().trim().isEmpty()) {
            throw new IllegalArgumentException("Le libellé de la prestation est requis");
        }

        if (prestation.getCategorie() == null) {
            throw new IllegalArgumentException("La catégorie de la prestation est requise");
        }
    }
}
