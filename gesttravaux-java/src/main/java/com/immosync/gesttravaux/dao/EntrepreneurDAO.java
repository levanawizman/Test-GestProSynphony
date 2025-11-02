package com.immosync.gesttravaux.dao;

import com.immosync.gesttravaux.config.HibernateUtil;
import com.immosync.gesttravaux.models.Entrepreneur;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO for Entrepreneur entity.
 * Provides specific queries for entrepreneurs.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class EntrepreneurDAO extends GenericDAO<Entrepreneur, Integer> {

    /**
     * Constructor.
     */
    public EntrepreneurDAO() {
        super(Entrepreneur.class);
    }

    /**
     * Finds entrepreneurs by city.
     *
     * @param ville the city name
     * @return list of entrepreneurs in the specified city
     */
    public List<Entrepreneur> findByVille(String ville) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Entrepreneur> query = session.createQuery(
                    "FROM Entrepreneur e WHERE e.villeDeploiement = :ville", Entrepreneur.class);
            query.setParameter("ville", ville);
            return query.getResultList();
        }
    }

    /**
     * Finds entrepreneurs by email.
     *
     * @param email the email
     * @return entrepreneur with the specified email or null
     */
    public Entrepreneur findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Entrepreneur> query = session.createQuery(
                    "FROM Entrepreneur e WHERE e.emailEntrepreneur = :email", Entrepreneur.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        }
    }

    /**
     * Searches entrepreneurs by name.
     *
     * @param searchTerm the search term
     * @return list of matching entrepreneurs
     */
    public List<Entrepreneur> searchByName(String searchTerm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Entrepreneur> query = session.createQuery(
                    "FROM Entrepreneur e WHERE LOWER(e.nomEntrepreneur) LIKE LOWER(:term) " +
                    "OR LOWER(e.prenomEntrepreneur) LIKE LOWER(:term)", Entrepreneur.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        }
    }

    /**
     * Finds entrepreneurs by category.
     *
     * @param categorieId the category ID
     * @return list of entrepreneurs with the specified category
     */
    public List<Entrepreneur> findByCategorie(Integer categorieId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Entrepreneur> query = session.createQuery(
                    "SELECT DISTINCT e FROM Entrepreneur e JOIN e.categories c WHERE c.idCategorie = :categorieId",
                    Entrepreneur.class);
            query.setParameter("categorieId", categorieId);
            return query.getResultList();
        }
    }
}
