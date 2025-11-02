package com.immosync.gesttravaux.dao;

import com.immosync.gesttravaux.config.HibernateUtil;
import com.immosync.gesttravaux.models.Devis;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO for Devis entity.
 * Provides specific queries for quotes.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class DevisDAO extends GenericDAO<Devis, Integer> {

    /**
     * Constructor.
     */
    public DevisDAO() {
        super(Devis.class);
    }

    /**
     * Finds devis by chantier.
     *
     * @param chantierId the chantier ID
     * @return list of devis for the specified chantier
     */
    public List<Devis> findByChantier(Integer chantierId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Devis> query = session.createQuery(
                    "FROM Devis d WHERE d.chantier.idChantier = :chantierId", Devis.class);
            query.setParameter("chantierId", chantierId);
            return query.getResultList();
        }
    }

    /**
     * Finds devis by entrepreneur.
     *
     * @param entrepreneurId the entrepreneur ID
     * @return list of devis from the specified entrepreneur
     */
    public List<Devis> findByEntrepreneur(Integer entrepreneurId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Devis> query = session.createQuery(
                    "FROM Devis d WHERE d.entrepreneur.idEntrepreneur = :entrepreneurId", Devis.class);
            query.setParameter("entrepreneurId", entrepreneurId);
            return query.getResultList();
        }
    }

    /**
     * Finds devis by prestation.
     *
     * @param prestationId the prestation ID
     * @return list of devis for the specified prestation
     */
    public List<Devis> findByPrestation(Integer prestationId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Devis> query = session.createQuery(
                    "FROM Devis d WHERE d.prestation.idPrestation = :prestationId", Devis.class);
            query.setParameter("prestationId", prestationId);
            return query.getResultList();
        }
    }

    /**
     * Finds devis by status.
     *
     * @param statut the status
     * @return list of devis with the specified status
     */
    public List<Devis> findByStatut(String statut) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Devis> query = session.createQuery(
                    "FROM Devis d WHERE d.statut = :statut", Devis.class);
            query.setParameter("statut", statut);
            return query.getResultList();
        }
    }

    /**
     * Finds devis within a price range.
     *
     * @param minPrix minimum price
     * @param maxPrix maximum price
     * @return list of devis within the price range
     */
    public List<Devis> findByPriceRange(Integer minPrix, Integer maxPrix) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Devis> query = session.createQuery(
                    "FROM Devis d WHERE d.prix BETWEEN :minPrix AND :maxPrix", Devis.class);
            query.setParameter("minPrix", minPrix);
            query.setParameter("maxPrix", maxPrix);
            return query.getResultList();
        }
    }

    /**
     * Gets all devis ordered by date.
     *
     * @return list of devis ordered by creation date
     */
    public List<Devis> findAllOrdered() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Devis> query = session.createQuery(
                    "FROM Devis d ORDER BY d.dateCreation DESC", Devis.class);
            return query.getResultList();
        }
    }
}
