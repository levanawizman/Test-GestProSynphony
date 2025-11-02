package com.immosync.gesttravaux.dao;

import com.immosync.gesttravaux.config.HibernateUtil;
import com.immosync.gesttravaux.models.Proprietaire;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO for Proprietaire entity.
 * Provides specific queries for property owners.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class ProprietaireDAO extends GenericDAO<Proprietaire, Integer> {

    /**
     * Constructor.
     */
    public ProprietaireDAO() {
        super(Proprietaire.class);
    }

    /**
     * Finds a proprietaire by email.
     *
     * @param email the email
     * @return proprietaire with the specified email or null
     */
    public Proprietaire findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Proprietaire> query = session.createQuery(
                    "FROM Proprietaire p WHERE p.emailProprietaire = :email", Proprietaire.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        }
    }

    /**
     * Searches proprietaires by name.
     *
     * @param searchTerm the search term
     * @return list of matching proprietaires
     */
    public List<Proprietaire> searchByName(String searchTerm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Proprietaire> query = session.createQuery(
                    "FROM Proprietaire p WHERE LOWER(p.nomProprietaire) LIKE LOWER(:term) " +
                    "OR LOWER(p.prenomProprietaire) LIKE LOWER(:term)", Proprietaire.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        }
    }

    /**
     * Gets all proprietaires ordered by name.
     *
     * @return list of proprietaires ordered by last name
     */
    public List<Proprietaire> findAllOrdered() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Proprietaire> query = session.createQuery(
                    "FROM Proprietaire p ORDER BY p.nomProprietaire, p.prenomProprietaire", Proprietaire.class);
            return query.getResultList();
        }
    }
}
