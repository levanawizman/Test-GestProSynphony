package com.immosync.gesttravaux.services;

import com.immosync.gesttravaux.dao.CategorieDAO;
import com.immosync.gesttravaux.models.Categorie;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Categorie business logic.
 * Handles validation and business rules for categories.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class CategorieService {

    private final CategorieDAO categorieDAO;

    /**
     * Constructor.
     */
    public CategorieService() {
        this.categorieDAO = new CategorieDAO();
    }

    /**
     * Creates a new category.
     *
     * @param categorie the category to create
     * @return the created category
     * @throws IllegalArgumentException if validation fails
     */
    public Categorie createCategorie(Categorie categorie) {
        validateCategorie(categorie);

        // Check if type already exists
        Categorie existing = categorieDAO.findByType(categorie.getType());
        if (existing != null) {
            throw new IllegalArgumentException("Une catégorie avec ce type existe déjà");
        }

        return categorieDAO.save(categorie);
    }

    /**
     * Updates an existing category.
     *
     * @param categorie the category to update
     * @return the updated category
     * @throws IllegalArgumentException if validation fails
     */
    public Categorie updateCategorie(Categorie categorie) {
        validateCategorie(categorie);

        if (categorie.getIdCategorie() == null) {
            throw new IllegalArgumentException("L'ID de la catégorie est requis pour la mise à jour");
        }

        // Check if type is unique (excluding current category)
        Categorie existing = categorieDAO.findByType(categorie.getType());
        if (existing != null && !existing.getIdCategorie().equals(categorie.getIdCategorie())) {
            throw new IllegalArgumentException("Une autre catégorie avec ce type existe déjà");
        }

        return categorieDAO.update(categorie);
    }

    /**
     * Deletes a category.
     *
     * @param categorieId the category ID
     */
    public void deleteCategorie(Integer categorieId) {
        Optional<Categorie> categorie = categorieDAO.findById(categorieId);
        if (categorie.isPresent()) {
            categorieDAO.delete(categorie.get());
        } else {
            throw new IllegalArgumentException("Catégorie non trouvée");
        }
    }

    /**
     * Finds a category by ID.
     *
     * @param id the category ID
     * @return the category if found
     */
    public Optional<Categorie> findById(Integer id) {
        return categorieDAO.findById(id);
    }

    /**
     * Gets all categories.
     *
     * @return list of all categories
     */
    public List<Categorie> getAllCategories() {
        return categorieDAO.findAllOrdered();
    }

    /**
     * Searches categories by type.
     *
     * @param searchTerm the search term
     * @return list of matching categories
     */
    public List<Categorie> searchByType(String searchTerm) {
        return categorieDAO.searchByType(searchTerm);
    }

    /**
     * Validates a category.
     *
     * @param categorie the category to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateCategorie(Categorie categorie) {
        if (categorie == null) {
            throw new IllegalArgumentException("La catégorie ne peut pas être null");
        }

        if (categorie.getType() == null || categorie.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Le type de la catégorie est requis");
        }
    }
}
