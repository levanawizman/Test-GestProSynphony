package com.immosync.gesttravaux.dao;

import com.immosync.gesttravaux.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Generic DAO implementation providing basic CRUD operations.
 * All specific DAOs should extend this class.
 *
 * @param <T> the entity type
 * @param <ID> the ID type
 * @author GestTravaux Pro
 * @version 1.0
 */
public abstract class GenericDAO<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);
    private final Class<T> entityClass;

    /**
     * Constructor with entity class.
     *
     * @param entityClass the entity class
     */
    protected GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Saves a new entity to the database.
     *
     * @param entity the entity to save
     * @return the saved entity with generated ID
     */
    public T save(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            logger.info("Entity saved: {}", entity);
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error saving entity", e);
            throw new RuntimeException("Error saving entity", e);
        }
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity to update
     * @return the updated entity
     */
    public T update(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T merged = session.merge(entity);
            transaction.commit();
            logger.info("Entity updated: {}", merged);
            return merged;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error updating entity", e);
            throw new RuntimeException("Error updating entity", e);
        }
    }

    /**
     * Deletes an entity from the database.
     *
     * @param entity the entity to delete
     */
    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            logger.info("Entity deleted: {}", entity);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error deleting entity", e);
            throw new RuntimeException("Error deleting entity", e);
        }
    }

    /**
     * Finds an entity by its ID.
     *
     * @param id the entity ID
     * @return Optional containing the entity if found
     */
    public Optional<T> findById(ID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            T entity = session.get(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            logger.error("Error finding entity by ID: {}", id, e);
            throw new RuntimeException("Error finding entity by ID", e);
        }
    }

    /**
     * Retrieves all entities of this type.
     *
     * @return list of all entities
     */
    public List<T> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<T> query = session.createQuery("FROM " + entityClass.getSimpleName(), entityClass);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding all entities", e);
            throw new RuntimeException("Error finding all entities", e);
        }
    }

    /**
     * Counts the total number of entities.
     *
     * @return the count
     */
    public long count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            logger.error("Error counting entities", e);
            throw new RuntimeException("Error counting entities", e);
        }
    }

    /**
     * Checks if an entity with the given ID exists.
     *
     * @param id the entity ID
     * @return true if exists, false otherwise
     */
    public boolean exists(ID id) {
        return findById(id).isPresent();
    }

    /**
     * Gets the entity class.
     *
     * @return the entity class
     */
    protected Class<T> getEntityClass() {
        return entityClass;
    }
}
