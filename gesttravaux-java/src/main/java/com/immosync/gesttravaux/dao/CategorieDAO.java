package com.immosync.gesttravaux.dao;

import com.immosync.gesttravaux.config.HibernateUtil;
import com.immosync.gesttravaux.models.Categorie;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO for Categorie entity.
 * Provides specific queries for categories.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class CategorieDAO extends GenericDAO<Categorie, Integer> {

    /**
     * Constructor.
     */
    public CategorieDAO() {
        super(Categorie.class);
    }

    /**
     * Finds a category by type.
     *
     * @param type the category type
     * @return category with the specified type or null
     */
    public Categorie findByType(String type) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Categorie> query = session.createQuery(
                    "FROM Categorie c WHERE c.type = :type", Categorie.class);
            query.setParameter("type", type);
            return query.uniqueResult();
        }
    }

    /**
     * Searches categories by type.
     *
     * @param searchTerm the search term
     * @return list of matching categories
     */
    public List<Categorie> searchByType(String searchTerm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Categorie> query = session.createQuery(
                    "FROM Categorie c WHERE LOWER(c.type) LIKE LOWER(:term)", Categorie.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        }
    }

    /**
     * Gets all categories ordered by type.
     *
     * @return list of categories ordered by type
     */
    public List<Categorie> findAllOrdered() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Categorie> query = session.createQuery(
                    "FROM Categorie c ORDER BY c.type", Categorie.class);
            return query.getResultList();
        }
    }
}
