package com.immosync.gesttravaux.services;

import com.immosync.gesttravaux.dao.EntrepreneurDAO;
import com.immosync.gesttravaux.models.Categorie;
import com.immosync.gesttravaux.models.Entrepreneur;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Entrepreneur business logic.
 * Handles validation and business rules for entrepreneurs.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class EntrepreneurService {

    private final EntrepreneurDAO entrepreneurDAO;

    /**
     * Constructor.
     */
    public EntrepreneurService() {
        this.entrepreneurDAO = new EntrepreneurDAO();
    }

    /**
     * Creates a new entrepreneur.
     *
     * @param entrepreneur the entrepreneur to create
     * @return the created entrepreneur
     * @throws IllegalArgumentException if validation fails
     */
    public Entrepreneur createEntrepreneur(Entrepreneur entrepreneur) {
        validateEntrepreneur(entrepreneur);

        // Check if email already exists
        Entrepreneur existing = entrepreneurDAO.findByEmail(entrepreneur.getEmailEntrepreneur());
        if (existing != null) {
            throw new IllegalArgumentException("Un entrepreneur avec cet email existe déjà");
        }

        return entrepreneurDAO.save(entrepreneur);
    }

    /**
     * Updates an existing entrepreneur.
     *
     * @param entrepreneur the entrepreneur to update
     * @return the updated entrepreneur
     * @throws IllegalArgumentException if validation fails
     */
    public Entrepreneur updateEntrepreneur(Entrepreneur entrepreneur) {
        validateEntrepreneur(entrepreneur);

        if (entrepreneur.getIdEntrepreneur() == null) {
            throw new IllegalArgumentException("L'ID de l'entrepreneur est requis pour la mise à jour");
        }

        // Check if email is unique (excluding current entrepreneur)
        Entrepreneur existing = entrepreneurDAO.findByEmail(entrepreneur.getEmailEntrepreneur());
        if (existing != null && !existing.getIdEntrepreneur().equals(entrepreneur.getIdEntrepreneur())) {
            throw new IllegalArgumentException("Un autre entrepreneur avec cet email existe déjà");
        }

        return entrepreneurDAO.update(entrepreneur);
    }

    /**
     * Deletes an entrepreneur.
     *
     * @param entrepreneurId the entrepreneur ID
     */
    public void deleteEntrepreneur(Integer entrepreneurId) {
        Optional<Entrepreneur> entrepreneur = entrepreneurDAO.findById(entrepreneurId);
        if (entrepreneur.isPresent()) {
            entrepreneurDAO.delete(entrepreneur.get());
        } else {
            throw new IllegalArgumentException("Entrepreneur non trouvé");
        }
    }

    /**
     * Finds an entrepreneur by ID.
     *
     * @param id the entrepreneur ID
     * @return the entrepreneur if found
     */
    public Optional<Entrepreneur> findById(Integer id) {
        return entrepreneurDAO.findById(id);
    }

    /**
     * Gets all entrepreneurs.
     *
     * @return list of all entrepreneurs
     */
    public List<Entrepreneur> getAllEntrepreneurs() {
        return entrepreneurDAO.findAll();
    }

    /**
     * Searches entrepreneurs by name.
     *
     * @param searchTerm the search term
     * @return list of matching entrepreneurs
     */
    public List<Entrepreneur> searchByName(String searchTerm) {
        return entrepreneurDAO.searchByName(searchTerm);
    }

    /**
     * Finds entrepreneurs by city.
     *
     * @param ville the city name
     * @return list of entrepreneurs in the specified city
     */
    public List<Entrepreneur> findByVille(String ville) {
        return entrepreneurDAO.findByVille(ville);
    }

    /**
     * Finds entrepreneurs by category.
     *
     * @param categorieId the category ID
     * @return list of entrepreneurs with the specified category
     */
    public List<Entrepreneur> findByCategorie(Integer categorieId) {
        return entrepreneurDAO.findByCategorie(categorieId);
    }

    /**
     * Adds a category to an entrepreneur.
     *
     * @param entrepreneurId the entrepreneur ID
     * @param categorie the category to add
     * @return the updated entrepreneur
     */
    public Entrepreneur addCategorie(Integer entrepreneurId, Categorie categorie) {
        Optional<Entrepreneur> optEntrepreneur = entrepreneurDAO.findById(entrepreneurId);
        if (optEntrepreneur.isPresent()) {
            Entrepreneur entrepreneur = optEntrepreneur.get();
            entrepreneur.addCategorie(categorie);
            return entrepreneurDAO.update(entrepreneur);
        }
        throw new IllegalArgumentException("Entrepreneur non trouvé");
    }

    /**
     * Removes a category from an entrepreneur.
     *
     * @param entrepreneurId the entrepreneur ID
     * @param categorie the category to remove
     * @return the updated entrepreneur
     */
    public Entrepreneur removeCategorie(Integer entrepreneurId, Categorie categorie) {
        Optional<Entrepreneur> optEntrepreneur = entrepreneurDAO.findById(entrepreneurId);
        if (optEntrepreneur.isPresent()) {
            Entrepreneur entrepreneur = optEntrepreneur.get();
            entrepreneur.removeCategorie(categorie);
            return entrepreneurDAO.update(entrepreneur);
        }
        throw new IllegalArgumentException("Entrepreneur non trouvé");
    }

    /**
     * Validates an entrepreneur.
     *
     * @param entrepreneur the entrepreneur to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateEntrepreneur(Entrepreneur entrepreneur) {
        if (entrepreneur == null) {
            throw new IllegalArgumentException("L'entrepreneur ne peut pas être null");
        }

        if (entrepreneur.getNomEntrepreneur() == null || entrepreneur.getNomEntrepreneur().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'entrepreneur est requis");
        }

        if (entrepreneur.getPrenomEntrepreneur() == null || entrepreneur.getPrenomEntrepreneur().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom de l'entrepreneur est requis");
        }

        if (entrepreneur.getEmailEntrepreneur() == null || entrepreneur.getEmailEntrepreneur().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email de l'entrepreneur est requis");
        }

        // Basic email validation
        if (!entrepreneur.getEmailEntrepreneur().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("L'email de l'entrepreneur n'est pas valide");
        }
    }
}
