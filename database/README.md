# GestTravaux Pro - Database Documentation

## Overview

This database implements the complete data model for **GestTravaux Pro**, a construction project management system. The database is designed to manage construction sites (chantiers), contractors (entrepreneurs), property owners (propriétaires), inspectors (inspecteurs), quotes (devis), and administrative functions.

## Database Information

- **Database Name**: `gesttravaux_pro`
- **Engine**: InnoDB
- **Character Set**: UTF8MB4
- **Collation**: utf8mb4_unicode_ci
- **Normalization**: 3NF (Third Normal Form)
- **DBMS**: MySQL 5.7+ / MariaDB 10.2+

## Table Structure

### 1. CATEGORIES

Stores the main categories of construction work.

| Column       | Type        | Description                           |
|--------------|-------------|---------------------------------------|
| idCategorie  | INT         | Primary Key, Auto-increment           |
| type         | VARCHAR(45) | Category name (unique)                |

**Sample Data**: Plomberie, Électricité, Maçonnerie, Peinture, Menuiserie

**Relationships**:
- One-to-Many with `PRESTATIONS`
- Many-to-Many with `ENTREPRENEURS` (via junction table)

---

### 2. ENTREPRENEURS

Stores information about contractors and construction companies.

| Column               | Type          | Description                        |
|----------------------|---------------|------------------------------------|
| idEntrepreneur       | INT           | Primary Key, Auto-increment        |
| villeDeploiement     | VARCHAR(100)  | City of operation                  |
| nomEntrepreneur      | VARCHAR(100)  | Last name                          |
| prenomEntrepreneur   | VARCHAR(100)  | First name                         |
| emailEntrepreneur    | VARCHAR(100)  | Email (unique)                     |
| telEntrepreneur      | VARCHAR(45)   | Phone number                       |

**Indexes**:
- `UK_email_entrepreneur` (Unique on email)
- `IDX_ville` (On villeDeploiement)

**Relationships**:
- Many-to-Many with `CATEGORIES` (via `ENTREPRENEURS_has_CATEGORIES`)
- One-to-Many with `DEVIS`

---

### 3. ENTREPRENEURS_has_CATEGORIES

Junction table linking entrepreneurs to their work categories (many-to-many relationship).

| Column                        | Type | Description                     |
|-------------------------------|------|---------------------------------|
| ENTREPRENEURS_idEntrepreneur  | INT  | Foreign Key to ENTREPRENEURS    |
| CATEGORIES_idCategorie        | INT  | Foreign Key to CATEGORIES       |

**Primary Key**: Composite (both columns)

**Constraints**:
- `ON DELETE CASCADE` - If entrepreneur or category is deleted, links are removed
- `ON UPDATE CASCADE` - Updates propagate to this table

---

### 4. PRESTATIONS

Stores specific services/work types within each category.

| Column                  | Type        | Description                    |
|-------------------------|-------------|--------------------------------|
| idPrestation            | INT         | Primary Key, Auto-increment    |
| libelle                 | VARCHAR(45) | Service name                   |
| CATEGORIES_idCategorie  | INT         | Foreign Key to CATEGORIES      |

**Example**: "Installation sanitaire" (Plomberie), "Rénovation tableau électrique" (Électricité)

**Relationships**:
- Many-to-One with `CATEGORIES`
- One-to-Many with `DEVIS`

**Constraints**:
- `ON DELETE RESTRICT` - Cannot delete category if prestations exist
- `ON UPDATE CASCADE` - Updates propagate

---

### 5. PROPRIETAIRES

Stores information about property owners.

| Column              | Type        | Description                     |
|---------------------|-------------|---------------------------------|
| idProprietaire      | INT         | Primary Key, Auto-increment     |
| nomProprietaire     | VARCHAR(45) | Last name                       |
| prenomProprietaire  | VARCHAR(45) | First name                      |
| emailProprietaire   | VARCHAR(45) | Email (unique)                  |
| telProprietaire     | VARCHAR(45) | Phone number                    |

**Relationships**:
- One-to-Many with `BIENS`

---

### 6. BIENS

Stores information about properties/real estate.

| Column                        | Type         | Description                      |
|-------------------------------|--------------|----------------------------------|
| idBien                        | INT          | Primary Key, Auto-increment      |
| adresseBien                   | VARCHAR(255) | Property address                 |
| villeBien                     | VARCHAR(45)  | City                             |
| PROPRIETAIRES_idProprietaire  | INT          | Foreign Key to PROPRIETAIRES     |

**Indexes**:
- `IDX_proprietaire` (On PROPRIETAIRES_idProprietaire)
- `IDX_ville` (On villeBien)

**Relationships**:
- Many-to-One with `PROPRIETAIRES`
- One-to-Many with `CHANTIERS`

**Constraints**:
- `ON DELETE RESTRICT` - Cannot delete owner if properties exist
- `ON UPDATE CASCADE` - Updates propagate

---

### 7. INSPECTEURS

Stores information about construction site inspectors.

| Column              | Type        | Description                    |
|---------------------|-------------|--------------------------------|
| idInspecteur        | INT         | Primary Key, Auto-increment    |
| nomInspecteur       | VARCHAR(45) | Last name                      |
| prenomInspecteur    | VARCHAR(45) | First name                     |
| emailInspecteur     | VARCHAR(45) | Email (unique)                 |
| telInspecteur       | VARCHAR(45) | Phone number                   |
| secteurInspecteur   | VARCHAR(45) | Assigned sector/region         |

**Example Sectors**: Île-de-France, Auvergne-Rhône-Alpes, Provence-Alpes-Côte d'Azur

**Relationships**:
- One-to-Many with `CHANTIERS`

---

### 8. DOCUMENTS

Stores documents related to construction sites.

| Column      | Type         | Description                           |
|-------------|--------------|---------------------------------------|
| idDocuments | INT          | Primary Key, Auto-increment           |
| libelleDoc  | VARCHAR(255) | Document title/description            |
| typeDoc     | VARCHAR(45)  | Document type (Plan, Permis, etc.)    |
| cheminDoc   | VARCHAR(500) | File path                             |
| dateUpload  | DATETIME     | Upload timestamp                      |

**Document Types**: Plan, Permis, Devis, Rapport, Photos, Contrat, Certificat

**Relationships**:
- One-to-Many with `CHANTIERS`

---

### 9. CHANTIERS

Stores information about construction sites/projects. This is the central table of the system.

| Column                   | Type         | Description                          |
|--------------------------|--------------|--------------------------------------|
| idChantier               | INT          | Primary Key, Auto-increment          |
| villeChantier            | VARCHAR(45)  | City                                 |
| adresseChantier          | VARCHAR(255) | Site address                         |
| infoChantier             | VARCHAR(255) | Additional information               |
| statutChantier           | INT          | Status code (0-3)                    |
| dateDebut                | DATE         | Start date                           |
| dateFin                  | DATE         | End date                             |
| INSPECTEURS_idInspecteur | INT          | Foreign Key to INSPECTEURS           |
| BIENS_idBien             | INT          | Foreign Key to BIENS                 |
| DOCUMENTS_idDocuments    | INT          | Foreign Key to DOCUMENTS (nullable)  |

**Status Codes**:
- `0` = En attente (Pending)
- `1` = En cours (In progress)
- `2` = Terminé (Completed)
- `3` = Annulé (Cancelled)

**Indexes**:
- Multiple indexes on foreign keys and frequently queried fields

**Relationships**:
- Many-to-One with `INSPECTEURS`, `BIENS`, `DOCUMENTS`
- One-to-Many with `DEVIS`

**Constraints**:
- `ON DELETE RESTRICT` for inspectors and properties (cannot delete if chantiers exist)
- `ON DELETE SET NULL` for documents (document deletion doesn't affect chantier)

---

### 10. DEVIS

Stores quotes/estimates for construction work.

| Column                       | Type          | Description                       |
|------------------------------|---------------|-----------------------------------|
| idDevis                      | INT           | Primary Key, Auto-increment       |
| prix                         | DECIMAL(10,2) | Price in euros                    |
| duree                        | VARCHAR(45)   | Duration estimate                 |
| dateCreation                 | DATETIME      | Creation timestamp                |
| statutDevis                  | VARCHAR(45)   | Quote status                      |
| PRESTATIONS_idPrestation     | INT           | Foreign Key to PRESTATIONS        |
| ENTREPRENEURS_idEntrepreneur | INT           | Foreign Key to ENTREPRENEURS      |
| CHANTIERS_idChantier         | INT           | Foreign Key to CHANTIERS          |

**Quote Statuses**: "En attente" (Pending), "Accepté" (Accepted), "Refusé" (Rejected)

**Relationships**:
- Many-to-One with `PRESTATIONS`, `ENTREPRENEURS`, `CHANTIERS`

**Constraints**:
- `ON DELETE CASCADE` for chantiers (quotes deleted when chantier is deleted)
- `ON DELETE RESTRICT` for prestations and entrepreneurs

---

### 11. ADMINISTRATEURS

Stores system administrator accounts.

| Column                 | Type         | Description                        |
|------------------------|--------------|------------------------------------|
| idAdministrateur       | INT          | Primary Key, Auto-increment        |
| nomAdministrateur      | VARCHAR(45)  | Last name                          |
| prenomAdministrateur   | VARCHAR(45)  | First name                         |
| mdpAdministrateur      | VARCHAR(255) | Hashed password                    |
| droitAdministrateur    | VARCHAR(45)  | Permission level                   |
| emailAdministrateur    | VARCHAR(100) | Email (unique)                     |
| dateCreation           | DATETIME     | Account creation date              |

**Permission Levels**: "SuperAdmin", "Admin", "Gestionnaire"

**Security Note**: Passwords are stored as bcrypt hashes (never plain text!)

---

### 12. APPEL_OFFRE

Stores call for tenders/bids.

| Column          | Type         | Description                    |
|-----------------|--------------|--------------------------------|
| idAppelOffre    | INT          | Primary Key, Auto-increment    |
| prix            | VARCHAR(45)  | Price range                    |
| duree           | VARCHAR(45)  | Duration estimate              |
| description     | TEXT         | Detailed description           |
| datePublication | DATETIME     | Publication date               |
| dateEcheance    | DATE         | Deadline                       |
| statut          | VARCHAR(45)  | Status (Ouvert/Clos)           |

**Statuses**: "Ouvert" (Open), "Clos" (Closed)

---

## Database Views

The schema includes three views for common queries:

### v_chantiers_complets

Complete information about construction sites including inspector, property owner, and document details.

### v_entrepreneurs_categories

List of entrepreneurs with their associated categories (concatenated).

### v_devis_chantiers

Summary of quotes per construction site with all related information.

---

## Entity Relationships

```
PROPRIETAIRES (1) ----< (N) BIENS (1) ----< (N) CHANTIERS
                                                    |
                                                    | (N)
                                                    |
INSPECTEURS (1) ----< (N) CHANTIERS                |
                                                    |
DOCUMENTS (1) ----< (N) CHANTIERS (1) ----< (N) DEVIS
                                                    |
                                                    | (N)
                                                    |
PRESTATIONS (1) ----< (N) DEVIS                    |
                                                    |
CATEGORIES (1) ----< (N) PRESTATIONS               |
    |                                               |
    | (N)                                           |
    |                                               |
ENTREPRENEURS_has_CATEGORIES (N) ---- (1) ENTREPRENEURS (1) ----< (N) DEVIS
```

---

## Installation Instructions

### Prerequisites

- MySQL 5.7+ or MariaDB 10.2+
- MySQL client or GUI tool (phpMyAdmin, MySQL Workbench, DBeaver, etc.)
- Sufficient permissions to create databases

### Method 1: Command Line

```bash
# 1. Login to MySQL
mysql -u root -p

# 2. Create and import schema
source /path/to/database/schema.sql

# 3. Import test data
source /path/to/database/data.sql
```

### Method 2: Single Command

```bash
mysql -u root -p < /path/to/database/schema.sql
mysql -u root -p gesttravaux_pro < /path/to/database/data.sql
```

### Method 3: Using MySQL Workbench

1. Open MySQL Workbench
2. Connect to your MySQL server
3. Go to File → Run SQL Script
4. Select `schema.sql` and execute
5. Select `data.sql` and execute

### Method 4: Using phpMyAdmin

1. Login to phpMyAdmin
2. Click on "Import" tab
3. Choose `schema.sql` and click "Go"
4. After completion, choose `data.sql` and click "Go"

---

## Verification

After importing, verify the installation:

```sql
USE gesttravaux_pro;

-- Check all tables exist
SHOW TABLES;

-- Count records in each table
SELECT 'CATEGORIES' as TableName, COUNT(*) as RecordCount FROM CATEGORIES
UNION ALL SELECT 'ENTREPRENEURS', COUNT(*) FROM ENTREPRENEURS
UNION ALL SELECT 'PRESTATIONS', COUNT(*) FROM PRESTATIONS
UNION ALL SELECT 'PROPRIETAIRES', COUNT(*) FROM PROPRIETAIRES
UNION ALL SELECT 'BIENS', COUNT(*) FROM BIENS
UNION ALL SELECT 'INSPECTEURS', COUNT(*) FROM INSPECTEURS
UNION ALL SELECT 'DOCUMENTS', COUNT(*) FROM DOCUMENTS
UNION ALL SELECT 'CHANTIERS', COUNT(*) FROM CHANTIERS
UNION ALL SELECT 'DEVIS', COUNT(*) FROM DEVIS
UNION ALL SELECT 'ADMINISTRATEURS', COUNT(*) FROM ADMINISTRATEURS
UNION ALL SELECT 'APPEL_OFFRE', COUNT(*) FROM APPEL_OFFRE;

-- Test a view
SELECT * FROM v_chantiers_complets LIMIT 5;
```

Expected record counts:
- CATEGORIES: 5
- ENTREPRENEURS: 15
- ENTREPRENEURS_has_CATEGORIES: 17
- PRESTATIONS: 20
- PROPRIETAIRES: 8
- BIENS: 12
- INSPECTEURS: 5
- DOCUMENTS: 8
- CHANTIERS: 8
- DEVIS: 23
- ADMINISTRATEURS: 2
- APPEL_OFFRE: 5

---

## Sample Queries

### Get all active construction sites

```sql
SELECT *
FROM v_chantiers_complets
WHERE statutLibelle = 'En cours'
ORDER BY dateDebut DESC;
```

### Find entrepreneurs by category

```sql
SELECT e.*, c.type as categorie
FROM ENTREPRENEURS e
JOIN ENTREPRENEURS_has_CATEGORIES ec ON e.idEntrepreneur = ec.ENTREPRENEURS_idEntrepreneur
JOIN CATEGORIES c ON ec.CATEGORIES_idCategorie = c.idCategorie
WHERE c.type = 'Plomberie';
```

### Get all quotes for a specific construction site

```sql
SELECT
    d.idDevis,
    d.prix,
    d.duree,
    d.statutDevis,
    p.libelle as prestation,
    CONCAT(e.prenomEntrepreneur, ' ', e.nomEntrepreneur) as entrepreneur
FROM DEVIS d
JOIN PRESTATIONS p ON d.PRESTATIONS_idPrestation = p.idPrestation
JOIN ENTREPRENEURS e ON d.ENTREPRENEURS_idEntrepreneur = e.idEntrepreneur
WHERE d.CHANTIERS_idChantier = 1
ORDER BY d.prix ASC;
```

### List properties by owner

```sql
SELECT
    CONCAT(p.prenomProprietaire, ' ', p.nomProprietaire) as proprietaire,
    b.adresseBien,
    b.villeBien,
    COUNT(c.idChantier) as nombre_chantiers
FROM PROPRIETAIRES p
LEFT JOIN BIENS b ON p.idProprietaire = b.PROPRIETAIRES_idProprietaire
LEFT JOIN CHANTIERS c ON b.idBien = c.BIENS_idBien
GROUP BY p.idProprietaire, b.idBien
ORDER BY proprietaire;
```

### Find construction sites by inspector

```sql
SELECT
    i.nomInspecteur,
    i.prenomInspecteur,
    i.secteurInspecteur,
    COUNT(c.idChantier) as nombre_chantiers,
    SUM(CASE WHEN c.statutChantier = 1 THEN 1 ELSE 0 END) as chantiers_en_cours
FROM INSPECTEURS i
LEFT JOIN CHANTIERS c ON i.idInspecteur = c.INSPECTEURS_idInspecteur
GROUP BY i.idInspecteur
ORDER BY nombre_chantiers DESC;
```

### Calculate total quotes per construction site

```sql
SELECT
    c.idChantier,
    c.adresseChantier,
    c.villeChantier,
    COUNT(d.idDevis) as nombre_devis,
    SUM(CASE WHEN d.statutDevis = 'Accepté' THEN d.prix ELSE 0 END) as total_accepte,
    SUM(CASE WHEN d.statutDevis = 'En attente' THEN d.prix ELSE 0 END) as total_en_attente
FROM CHANTIERS c
LEFT JOIN DEVIS d ON c.idChantier = d.CHANTIERS_idChantier
GROUP BY c.idChantier
ORDER BY total_accepte DESC;
```

---

## Test Administrator Credentials

**Default Login**:
- Email: `admin@gesttravaux.fr`
- Password: `password` (hashed in database)
- Rights: SuperAdmin

**IMPORTANT**: Change the default password in production!

To update the password:
```sql
UPDATE ADMINISTRATEURS
SET mdpAdministrateur = '$2y$10$YOUR_NEW_HASH_HERE'
WHERE emailAdministrateur = 'admin@gesttravaux.fr';
```

---

## Maintenance

### Backup Database

```bash
mysqldump -u root -p gesttravaux_pro > backup_$(date +%Y%m%d).sql
```

### Restore from Backup

```bash
mysql -u root -p gesttravaux_pro < backup_20251021.sql
```

### Reset to Initial State

```bash
mysql -u root -p < schema.sql
mysql -u root -p gesttravaux_pro < data.sql
```

---

## Data Integrity Rules

1. **Cannot delete a CATEGORIE** if PRESTATIONS reference it
2. **Cannot delete a PROPRIETAIRE** if BIENS reference it
3. **Cannot delete a BIEN** if CHANTIERS reference it
4. **Cannot delete an INSPECTEUR** if CHANTIERS reference it
5. **Cannot delete a PRESTATION** if DEVIS reference it
6. **Cannot delete an ENTREPRENEUR** if DEVIS reference it
7. **Deleting a CHANTIER** cascades to all related DEVIS
8. **Deleting a DOCUMENT** sets DOCUMENTS_idDocuments to NULL in CHANTIERS

---

## Performance Optimization

The schema includes indexes on:
- All foreign keys
- Frequently queried columns (ville, secteur, statut)
- Unique constraints on email fields
- Date fields for temporal queries

For large datasets, consider:
- Partitioning DEVIS table by date
- Archiving old CHANTIERS
- Regular ANALYZE TABLE operations

---

## Future Enhancements

Potential additions to the schema:
- History tracking tables (audit logs)
- File upload metadata table
- Messaging system between entrepreneurs and owners
- Payment tracking
- Material/equipment inventory
- Subcontractor management
- Project timeline/Gantt chart data

---

## Contact & Support

For questions about this database schema, please refer to the project documentation or contact the development team.

**Project**: GestTravaux Pro
**Version**: 1.0
**Last Updated**: 2025-10-21
**Database Schema**: Based on MLD PROJET BTS 3.pdf
