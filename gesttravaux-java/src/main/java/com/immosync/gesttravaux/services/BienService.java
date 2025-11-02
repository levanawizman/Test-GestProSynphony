package com.immosync.gesttravaux.services;

import com.immosync.gesttravaux.dao.BienDAO;
import com.immosync.gesttravaux.models.Bien;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Bien business logic.
 * Handles validation and business rules for properties.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class BienService {

    private final BienDAO bienDAO;

    /**
     * Constructor.
     */
    public BienService() {
        this.bienDAO = new BienDAO();
    }

    /**
     * Creates a new property.
     *
     * @param bien the property to create
     * @return the created property
     * @throws IllegalArgumentException if validation fails
     */
    public Bien createBien(Bien bien) {
        validateBien(bien);
        return bienDAO.save(bien);
    }

    /**
     * Updates an existing property.
     *
     * @param bien the property to update
     * @return the updated property
     * @throws IllegalArgumentException if validation fails
     */
    public Bien updateBien(Bien bien) {
        validateBien(bien);

        if (bien.getIdBien() == null) {
            throw new IllegalArgumentException("L'ID du bien est requis pour la mise à jour");
        }

        return bienDAO.update(bien);
    }

    /**
     * Deletes a property.
     *
     * @param bienId the property ID
     */
    public void deleteBien(Integer bienId) {
        Optional<Bien> bien = bienDAO.findById(bienId);
        if (bien.isPresent()) {
            bienDAO.delete(bien.get());
        } else {
            throw new IllegalArgumentException("Bien non trouvé");
        }
    }

    /**
     * Finds a property by ID.
     *
     * @param id the property ID
     * @return the property if found
     */
    public Optional<Bien> findById(Integer id) {
        return bienDAO.findById(id);
    }

    /**
     * Gets all properties.
     *
     * @return list of all properties
     */
    public List<Bien> getAllBiens() {
        return bienDAO.findAllOrdered();
    }

    /**
     * Searches properties by address.
     *
     * @param searchTerm the search term
     * @return list of matching properties
     */
    public List<Bien> searchByAddress(String searchTerm) {
        return bienDAO.searchByAddress(searchTerm);
    }

    /**
     * Finds properties by city.
     *
     * @param ville the city name
     * @return list of properties in the specified city
     */
    public List<Bien> findByVille(String ville) {
        return bienDAO.findByVille(ville);
    }

    /**
     * Finds properties by owner.
     *
     * @param proprietaireId the owner ID
     * @return list of properties owned by the specified proprietaire
     */
    public List<Bien> findByProprietaire(Integer proprietaireId) {
        return bienDAO.findByProprietaire(proprietaireId);
    }

    /**
     * Validates a property.
     *
     * @param bien the property to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateBien(Bien bien) {
        if (bien == null) {
            throw new IllegalArgumentException("Le bien ne peut pas être null");
        }

        if (bien.getAdresseBien() == null || bien.getAdresseBien().trim().isEmpty()) {
            throw new IllegalArgumentException("L'adresse du bien est requise");
        }

        if (bien.getVilleBien() == null || bien.getVilleBien().trim().isEmpty()) {
            throw new IllegalArgumentException("La ville du bien est requise");
        }

        if (bien.getProprietaire() == null) {
            throw new IllegalArgumentException("Le propriétaire du bien est requis");
        }
    }
}
