package com.immosync.gesttravaux.dao;

import com.immosync.gesttravaux.config.HibernateUtil;
import com.immosync.gesttravaux.models.Bien;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO for Bien entity.
 * Provides specific queries for properties.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class BienDAO extends GenericDAO<Bien, Integer> {

    /**
     * Constructor.
     */
    public BienDAO() {
        super(Bien.class);
    }

    /**
     * Finds properties by city.
     *
     * @param ville the city name
     * @return list of properties in the specified city
     */
    public List<Bien> findByVille(String ville) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bien> query = session.createQuery(
                    "FROM Bien b WHERE b.villeBien = :ville", Bien.class);
            query.setParameter("ville", ville);
            return query.getResultList();
        }
    }

    /**
     * Finds properties by owner.
     *
     * @param proprietaireId the owner ID
     * @return list of properties owned by the specified proprietaire
     */
    public List<Bien> findByProprietaire(Integer proprietaireId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bien> query = session.createQuery(
                    "FROM Bien b WHERE b.proprietaire.idProprietaire = :proprietaireId", Bien.class);
            query.setParameter("proprietaireId", proprietaireId);
            return query.getResultList();
        }
    }

    /**
     * Searches properties by address or city.
     *
     * @param searchTerm the search term
     * @return list of matching properties
     */
    public List<Bien> searchByAddress(String searchTerm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bien> query = session.createQuery(
                    "FROM Bien b WHERE LOWER(b.adresseBien) LIKE LOWER(:term) " +
                    "OR LOWER(b.villeBien) LIKE LOWER(:term)", Bien.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        }
    }

    /**
     * Gets all properties ordered by city and address.
     *
     * @return list of properties ordered by location
     */
    public List<Bien> findAllOrdered() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bien> query = session.createQuery(
                    "FROM Bien b ORDER BY b.villeBien, b.adresseBien", Bien.class);
            return query.getResultList();
        }
    }
}
