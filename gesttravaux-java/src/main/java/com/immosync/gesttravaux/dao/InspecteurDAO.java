package com.immosync.gesttravaux.dao;

import com.immosync.gesttravaux.config.HibernateUtil;
import com.immosync.gesttravaux.models.Inspecteur;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO for Inspecteur entity.
 * Provides specific queries for inspectors.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class InspecteurDAO extends GenericDAO<Inspecteur, Integer> {

    /**
     * Constructor.
     */
    public InspecteurDAO() {
        super(Inspecteur.class);
    }

    /**
     * Finds inspectors by sector.
     *
     * @param secteur the sector name
     * @return list of inspectors in the specified sector
     */
    public List<Inspecteur> findBySecteur(String secteur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Inspecteur> query = session.createQuery(
                    "FROM Inspecteur i WHERE i.secteurInspecteur = :secteur", Inspecteur.class);
            query.setParameter("secteur", secteur);
            return query.getResultList();
        }
    }

    /**
     * Finds an inspector by email.
     *
     * @param email the email
     * @return inspector with the specified email or null
     */
    public Inspecteur findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Inspecteur> query = session.createQuery(
                    "FROM Inspecteur i WHERE i.emailInspecteur = :email", Inspecteur.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        }
    }

    /**
     * Searches inspectors by name.
     *
     * @param searchTerm the search term
     * @return list of matching inspectors
     */
    public List<Inspecteur> searchByName(String searchTerm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Inspecteur> query = session.createQuery(
                    "FROM Inspecteur i WHERE LOWER(i.nomInspecteur) LIKE LOWER(:term) " +
                    "OR LOWER(i.prenomInspecteur) LIKE LOWER(:term)", Inspecteur.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        }
    }

    /**
     * Gets all inspectors ordered by name.
     *
     * @return list of inspectors ordered by last name
     */
    public List<Inspecteur> findAllOrdered() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Inspecteur> query = session.createQuery(
                    "FROM Inspecteur i ORDER BY i.nomInspecteur, i.prenomInspecteur", Inspecteur.class);
            return query.getResultList();
        }
    }
}
