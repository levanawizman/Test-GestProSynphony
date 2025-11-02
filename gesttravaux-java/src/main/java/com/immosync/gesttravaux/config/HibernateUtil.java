package com.immosync.gesttravaux.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate utility class for managing SessionFactory.
 * This class follows the Singleton pattern to ensure only one SessionFactory instance.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class HibernateUtil {

    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    /**
     * Private constructor to prevent instantiation.
     */
    private HibernateUtil() {
    }

    /**
     * Gets the SessionFactory instance.
     * Creates a new instance if it doesn't exist.
     *
     * @return the SessionFactory instance
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                logger.info("Initializing Hibernate SessionFactory...");

                // Create the SessionFactory from hibernate.cfg.xml
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");

                logger.info("Hibernate Configuration loaded");

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                logger.info("Hibernate SessionFactory created successfully");

            } catch (Exception ex) {
                logger.error("Failed to create SessionFactory", ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    /**
     * Closes the SessionFactory and releases all resources.
     * Should be called when the application is shutting down.
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            logger.info("Closing Hibernate SessionFactory...");
            sessionFactory.close();
            logger.info("Hibernate SessionFactory closed");
        }
    }

    /**
     * Checks if the SessionFactory is open.
     *
     * @return true if SessionFactory is open, false otherwise
     */
    public static boolean isSessionFactoryOpen() {
        return sessionFactory != null && !sessionFactory.isClosed();
    }
}
