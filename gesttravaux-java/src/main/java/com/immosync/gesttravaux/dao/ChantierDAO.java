package com.immosync.gesttravaux.dao;

import com.immosync.gesttravaux.config.HibernateUtil;
import com.immosync.gesttravaux.models.Chantier;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO for Chantier entity.
 * Provides specific queries for construction sites.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class ChantierDAO extends GenericDAO<Chantier, Integer> {

    /**
     * Constructor.
     */
    public ChantierDAO() {
        super(Chantier.class);
    }

    /**
     * Finds chantiers by status.
     *
     * @param statut the status (0=not started, 1=in progress, 2=completed)
     * @return list of chantiers with the specified status
     */
    public List<Chantier> findByStatut(Integer statut) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Chantier> query = session.createQuery(
                    "FROM Chantier c WHERE c.statutChantier = :statut", Chantier.class);
            query.setParameter("statut", statut);
            return query.getResultList();
        }
    }

    /**
     * Finds chantiers by property.
     *
     * @param bienId the property ID
     * @return list of chantiers for the specified property
     */
    public List<Chantier> findByBien(Integer bienId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Chantier> query = session.createQuery(
                    "FROM Chantier c WHERE c.bien.idBien = :bienId", Chantier.class);
            query.setParameter("bienId", bienId);
            return query.getResultList();
        }
    }

    /**
     * Finds chantiers by inspector.
     *
     * @param inspecteurId the inspector ID
     * @return list of chantiers assigned to the specified inspector
     */
    public List<Chantier> findByInspecteur(Integer inspecteurId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Chantier> query = session.createQuery(
                    "FROM Chantier c WHERE c.inspecteur.idInspecteur = :inspecteurId", Chantier.class);
            query.setParameter("inspecteurId", inspecteurId);
            return query.getResultList();
        }
    }

    /**
     * Finds chantiers by city.
     *
     * @param ville the city name
     * @return list of chantiers in the specified city
     */
    public List<Chantier> findByVille(String ville) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Chantier> query = session.createQuery(
                    "FROM Chantier c WHERE c.villeChantier = :ville", Chantier.class);
            query.setParameter("ville", ville);
            return query.getResultList();
        }
    }

    /**
     * Searches chantiers by address or info.
     *
     * @param searchTerm the search term
     * @return list of matching chantiers
     */
    public List<Chantier> search(String searchTerm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Chantier> query = session.createQuery(
                    "FROM Chantier c WHERE LOWER(c.adresseChantier) LIKE LOWER(:term) " +
                    "OR LOWER(c.villeChantier) LIKE LOWER(:term) " +
                    "OR LOWER(c.infoChantier) LIKE LOWER(:term)", Chantier.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        }
    }

    /**
     * Gets all chantiers ordered by status and city.
     *
     * @return list of chantiers ordered
     */
    public List<Chantier> findAllOrdered() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Chantier> query = session.createQuery(
                    "FROM Chantier c ORDER BY c.statutChantier, c.villeChantier", Chantier.class);
            return query.getResultList();
        }
    }
}
