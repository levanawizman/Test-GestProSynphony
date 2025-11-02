package com.immosync.gesttravaux.dao;

import com.immosync.gesttravaux.config.HibernateUtil;
import com.immosync.gesttravaux.models.Prestation;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO for Prestation entity.
 * Provides specific queries for prestations/services.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class PrestationDAO extends GenericDAO<Prestation, Integer> {

    /**
     * Constructor.
     */
    public PrestationDAO() {
        super(Prestation.class);
    }

    /**
     * Finds prestations by category.
     *
     * @param categorieId the category ID
     * @return list of prestations in the specified category
     */
    public List<Prestation> findByCategorie(Integer categorieId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Prestation> query = session.createQuery(
                    "FROM Prestation p WHERE p.categorie.idCategorie = :categorieId", Prestation.class);
            query.setParameter("categorieId", categorieId);
            return query.getResultList();
        }
    }

    /**
     * Searches prestations by libelle.
     *
     * @param searchTerm the search term
     * @return list of matching prestations
     */
    public List<Prestation> searchByLibelle(String searchTerm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Prestation> query = session.createQuery(
                    "FROM Prestation p WHERE LOWER(p.libelle) LIKE LOWER(:term)", Prestation.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        }
    }

    /**
     * Gets all prestations ordered by category and libelle.
     *
     * @return list of prestations ordered
     */
    public List<Prestation> findAllOrdered() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Prestation> query = session.createQuery(
                    "FROM Prestation p ORDER BY p.categorie.type, p.libelle", Prestation.class);
            return query.getResultList();
        }
    }
}
