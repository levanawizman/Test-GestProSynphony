-- ============================================================================
-- GestTravaux Pro - Complete MySQL Database Schema
-- ============================================================================
-- Description: This schema implements the complete database structure for
--              the GestTravaux Pro project management system
-- Version: 1.0
-- Created: 2025-10-21
-- ============================================================================

-- Drop database if exists and create new one
DROP DATABASE IF EXISTS gesttravaux_pro;
CREATE DATABASE gesttravaux_pro CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gesttravaux_pro;

-- ============================================================================
-- Table: CATEGORIES
-- Description: Stores different categories of construction work
--              (e.g., Plumbing, Electricity, Masonry, Painting, Carpentry)
-- ============================================================================
CREATE TABLE CATEGORIES (
    idCategorie INT AUTO_INCREMENT,
    type VARCHAR(45) NOT NULL,

    PRIMARY KEY (idCategorie),
    UNIQUE KEY UK_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Categories of construction work';

-- ============================================================================
-- Table: ENTREPRENEURS
-- Description: Stores information about contractors/entrepreneurs
-- ============================================================================
CREATE TABLE ENTREPRENEURS (
    idEntrepreneur INT AUTO_INCREMENT,
    villeDeploiement VARCHAR(100) NOT NULL,
    nomEntrepreneur VARCHAR(100) NOT NULL,
    prenomEntrepreneur VARCHAR(100) NOT NULL,
    emailEntrepreneur VARCHAR(100) NOT NULL,
    telEntrepreneur VARCHAR(45) NOT NULL,

    PRIMARY KEY (idEntrepreneur),
    UNIQUE KEY UK_email_entrepreneur (emailEntrepreneur),
    INDEX IDX_ville (villeDeploiement)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Contractors and entrepreneurs';

-- ============================================================================
-- Table: ENTREPRENEURS_has_CATEGORIES
-- Description: Junction table for many-to-many relationship between
--              entrepreneurs and categories (an entrepreneur can work in
--              multiple categories)
-- ============================================================================
CREATE TABLE ENTREPRENEURS_has_CATEGORIES (
    ENTREPRENEURS_idEntrepreneur INT NOT NULL,
    CATEGORIES_idCategorie INT NOT NULL,

    PRIMARY KEY (ENTREPRENEURS_idEntrepreneur, CATEGORIES_idCategorie),
    INDEX IDX_entrepreneur (ENTREPRENEURS_idEntrepreneur),
    INDEX IDX_categorie (CATEGORIES_idCategorie),

    CONSTRAINT FK_EntrepreneurCateg_Entrepreneur
        FOREIGN KEY (ENTREPRENEURS_idEntrepreneur)
        REFERENCES ENTREPRENEURS(idEntrepreneur)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_EntrepreneurCateg_Categorie
        FOREIGN KEY (CATEGORIES_idCategorie)
        REFERENCES CATEGORIES(idCategorie)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Junction table linking entrepreneurs to their work categories';

-- ============================================================================
-- Table: PRESTATIONS
-- Description: Stores different types of services/work within each category
-- ============================================================================
CREATE TABLE PRESTATIONS (
    idPrestation INT AUTO_INCREMENT,
    libelle VARCHAR(45) NOT NULL,
    CATEGORIES_idCategorie INT NOT NULL,

    PRIMARY KEY (idPrestation),
    INDEX IDX_categorie (CATEGORIES_idCategorie),

    CONSTRAINT FK_Prestation_Categorie
        FOREIGN KEY (CATEGORIES_idCategorie)
        REFERENCES CATEGORIES(idCategorie)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Services/work types within each category';

-- ============================================================================
-- Table: PROPRIETAIRES
-- Description: Stores information about property owners
-- ============================================================================
CREATE TABLE PROPRIETAIRES (
    idProprietaire INT AUTO_INCREMENT,
    nomProprietaire VARCHAR(45) NOT NULL,
    prenomProprietaire VARCHAR(45) NOT NULL,
    emailProprietaire VARCHAR(45) NOT NULL,
    telProprietaire VARCHAR(45) NOT NULL,

    PRIMARY KEY (idProprietaire),
    UNIQUE KEY UK_email_proprietaire (emailProprietaire)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Property owners';

-- ============================================================================
-- Table: BIENS
-- Description: Stores information about properties/real estate
-- ============================================================================
CREATE TABLE BIENS (
    idBien INT AUTO_INCREMENT,
    adresseBien VARCHAR(255) NOT NULL,
    villeBien VARCHAR(45) NOT NULL,
    PROPRIETAIRES_idProprietaire INT NOT NULL,

    PRIMARY KEY (idBien),
    INDEX IDX_proprietaire (PROPRIETAIRES_idProprietaire),
    INDEX IDX_ville (villeBien),

    CONSTRAINT FK_Bien_Proprietaire
        FOREIGN KEY (PROPRIETAIRES_idProprietaire)
        REFERENCES PROPRIETAIRES(idProprietaire)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Real estate properties';

-- ============================================================================
-- Table: INSPECTEURS
-- Description: Stores information about inspectors assigned to construction sites
-- ============================================================================
CREATE TABLE INSPECTEURS (
    idInspecteur INT AUTO_INCREMENT,
    nomInspecteur VARCHAR(45) NOT NULL,
    prenomInspecteur VARCHAR(45) NOT NULL,
    emailInspecteur VARCHAR(45) NOT NULL,
    telInspecteur VARCHAR(45) NOT NULL,
    secteurInspecteur VARCHAR(45) NOT NULL,

    PRIMARY KEY (idInspecteur),
    UNIQUE KEY UK_email_inspecteur (emailInspecteur),
    INDEX IDX_secteur (secteurInspecteur)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Construction site inspectors';

-- ============================================================================
-- Table: DOCUMENTS
-- Description: Stores documents related to construction sites
-- ============================================================================
CREATE TABLE DOCUMENTS (
    idDocuments INT AUTO_INCREMENT,
    libelleDoc VARCHAR(255) NOT NULL,
    typeDoc VARCHAR(45) NOT NULL,
    cheminDoc VARCHAR(500),
    dateUpload DATETIME DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (idDocuments),
    INDEX IDX_type (typeDoc),
    INDEX IDX_date (dateUpload)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Documents related to construction sites';

-- ============================================================================
-- Table: CHANTIERS
-- Description: Stores information about construction sites/projects
-- ============================================================================
CREATE TABLE CHANTIERS (
    idChantier INT AUTO_INCREMENT,
    villeChantier VARCHAR(45) NOT NULL,
    adresseChantier VARCHAR(255) NOT NULL,
    infoChantier VARCHAR(255),
    statutChantier INT NOT NULL DEFAULT 0,
    dateDebut DATE,
    dateFin DATE,
    INSPECTEURS_idInspecteur INT NOT NULL,
    BIENS_idBien INT NOT NULL,
    DOCUMENTS_idDocuments INT,

    PRIMARY KEY (idChantier),
    INDEX IDX_inspecteur (INSPECTEURS_idInspecteur),
    INDEX IDX_bien (BIENS_idBien),
    INDEX IDX_document (DOCUMENTS_idDocuments),
    INDEX IDX_statut (statutChantier),
    INDEX IDX_ville (villeChantier),

    CONSTRAINT FK_Chantier_Inspecteur
        FOREIGN KEY (INSPECTEURS_idInspecteur)
        REFERENCES INSPECTEURS(idInspecteur)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT FK_Chantier_Bien
        FOREIGN KEY (BIENS_idBien)
        REFERENCES BIENS(idBien)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT FK_Chantier_Document
        FOREIGN KEY (DOCUMENTS_idDocuments)
        REFERENCES DOCUMENTS(idDocuments)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Construction sites/projects';

-- ============================================================================
-- Table: DEVIS
-- Description: Stores quotes/estimates for construction work
-- ============================================================================
CREATE TABLE DEVIS (
    idDevis INT AUTO_INCREMENT,
    prix DECIMAL(10,2) NOT NULL,
    duree VARCHAR(45) NOT NULL,
    dateCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
    statutDevis VARCHAR(45) DEFAULT 'En attente',
    PRESTATIONS_idPrestation INT NOT NULL,
    ENTREPRENEURS_idEntrepreneur INT NOT NULL,
    CHANTIERS_idChantier INT NOT NULL,

    PRIMARY KEY (idDevis),
    INDEX IDX_prestation (PRESTATIONS_idPrestation),
    INDEX IDX_entrepreneur (ENTREPRENEURS_idEntrepreneur),
    INDEX IDX_chantier (CHANTIERS_idChantier),
    INDEX IDX_statut (statutDevis),

    CONSTRAINT FK_Devis_Prestation
        FOREIGN KEY (PRESTATIONS_idPrestation)
        REFERENCES PRESTATIONS(idPrestation)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT FK_Devis_Entrepreneur
        FOREIGN KEY (ENTREPRENEURS_idEntrepreneur)
        REFERENCES ENTREPRENEURS(idEntrepreneur)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT FK_Devis_Chantier
        FOREIGN KEY (CHANTIERS_idChantier)
        REFERENCES CHANTIERS(idChantier)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Quotes and estimates for construction work';

-- ============================================================================
-- Table: ADMINISTRATEURS
-- Description: Stores administrator accounts for system access
-- ============================================================================
CREATE TABLE ADMINISTRATEURS (
    idAdministrateur INT AUTO_INCREMENT,
    nomAdministrateur VARCHAR(45) NOT NULL,
    prenomAdministrateur VARCHAR(45) NOT NULL,
    mdpAdministrateur VARCHAR(255) NOT NULL,
    droitAdministrateur VARCHAR(45) NOT NULL,
    emailAdministrateur VARCHAR(100),
    dateCreation DATETIME DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (idAdministrateur),
    UNIQUE KEY UK_email_admin (emailAdministrateur),
    INDEX IDX_droits (droitAdministrateur)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='System administrators';

-- ============================================================================
-- Table: APPEL_OFFRE
-- Description: Stores call for tenders/bids
-- ============================================================================
CREATE TABLE APPEL_OFFRE (
    idAppelOffre INT AUTO_INCREMENT,
    prix VARCHAR(45),
    duree VARCHAR(45),
    description TEXT,
    datePublication DATETIME DEFAULT CURRENT_TIMESTAMP,
    dateEcheance DATE,
    statut VARCHAR(45) DEFAULT 'Ouvert',

    PRIMARY KEY (idAppelOffre),
    INDEX IDX_statut (statut),
    INDEX IDX_date_echeance (dateEcheance)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Call for tenders and bids';

-- ============================================================================
-- Additional Views for common queries
-- ============================================================================

-- View: List of chantiers with complete information
CREATE OR REPLACE VIEW v_chantiers_complets AS
SELECT
    c.idChantier,
    c.adresseChantier,
    c.villeChantier,
    c.infoChantier,
    CASE
        WHEN c.statutChantier = 0 THEN 'En attente'
        WHEN c.statutChantier = 1 THEN 'En cours'
        WHEN c.statutChantier = 2 THEN 'Termine'
        WHEN c.statutChantier = 3 THEN 'Annule'
        ELSE 'Inconnu'
    END AS statutLibelle,
    c.dateDebut,
    c.dateFin,
    CONCAT(i.prenomInspecteur, ' ', i.nomInspecteur) AS inspecteur,
    i.emailInspecteur,
    i.telInspecteur,
    b.adresseBien,
    b.villeBien,
    CONCAT(p.prenomProprietaire, ' ', p.nomProprietaire) AS proprietaire,
    p.emailProprietaire,
    d.libelleDoc AS documentPrincipal
FROM CHANTIERS c
JOIN INSPECTEURS i ON c.INSPECTEURS_idInspecteur = i.idInspecteur
JOIN BIENS b ON c.BIENS_idBien = b.idBien
JOIN PROPRIETAIRES p ON b.PROPRIETAIRES_idProprietaire = p.idProprietaire
LEFT JOIN DOCUMENTS d ON c.DOCUMENTS_idDocuments = d.idDocuments;

-- View: List of entrepreneurs with their categories
CREATE OR REPLACE VIEW v_entrepreneurs_categories AS
SELECT
    e.idEntrepreneur,
    CONCAT(e.prenomEntrepreneur, ' ', e.nomEntrepreneur) AS nomComplet,
    e.emailEntrepreneur,
    e.telEntrepreneur,
    e.villeDeploiement,
    GROUP_CONCAT(cat.type ORDER BY cat.type SEPARATOR ', ') AS categories
FROM ENTREPRENEURS e
LEFT JOIN ENTREPRENEURS_has_CATEGORIES ec ON e.idEntrepreneur = ec.ENTREPRENEURS_idEntrepreneur
LEFT JOIN CATEGORIES cat ON ec.CATEGORIES_idCategorie = cat.idCategorie
GROUP BY e.idEntrepreneur, e.nomEntrepreneur, e.prenomEntrepreneur,
         e.emailEntrepreneur, e.telEntrepreneur, e.villeDeploiement;

-- View: Summary of devis per chantier
CREATE OR REPLACE VIEW v_devis_chantiers AS
SELECT
    d.idDevis,
    c.idChantier,
    c.adresseChantier,
    c.villeChantier,
    prest.libelle AS prestation,
    cat.type AS categorie,
    CONCAT(e.prenomEntrepreneur, ' ', e.nomEntrepreneur) AS entrepreneur,
    d.prix,
    d.duree,
    d.statutDevis,
    d.dateCreation
FROM DEVIS d
JOIN CHANTIERS c ON d.CHANTIERS_idChantier = c.idChantier
JOIN PRESTATIONS prest ON d.PRESTATIONS_idPrestation = prest.idPrestation
JOIN CATEGORIES cat ON prest.CATEGORIES_idCategorie = cat.idCategorie
JOIN ENTREPRENEURS e ON d.ENTREPRENEURS_idEntrepreneur = e.idEntrepreneur;

-- ============================================================================
-- End of schema.sql
-- ============================================================================
