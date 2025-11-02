-- ============================================================================
-- GestTravaux Pro - Test Data
-- ============================================================================
-- Description: Realistic test data for the GestTravaux Pro database
-- Version: 1.0
-- Created: 2025-10-21
-- ============================================================================

USE gesttravaux_pro;

-- Disable foreign key checks temporarily for easier insertion
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================================
-- CATEGORIES - 5 main categories of construction work
-- ============================================================================
INSERT INTO CATEGORIES (idCategorie, type) VALUES
(1, 'Plomberie'),
(2, 'Électricité'),
(3, 'Maçonnerie'),
(4, 'Peinture'),
(5, 'Menuiserie');

-- ============================================================================
-- ENTREPRENEURS - 15 contractors in different cities
-- ============================================================================
INSERT INTO ENTREPRENEURS (idEntrepreneur, villeDeploiement, nomEntrepreneur, prenomEntrepreneur, emailEntrepreneur, telEntrepreneur) VALUES
(1, 'Paris', 'Dupont', 'Jean', 'jean.dupont@plomberie-paris.fr', '0145678901'),
(2, 'Lyon', 'Martin', 'Sophie', 'sophie.martin@elec-lyon.fr', '0478123456'),
(3, 'Marseille', 'Bernard', 'Pierre', 'pierre.bernard@maconnerie-marseille.fr', '0491234567'),
(4, 'Toulouse', 'Petit', 'Marie', 'marie.petit@peinture-toulouse.fr', '0561345678'),
(5, 'Nice', 'Dubois', 'Luc', 'luc.dubois@menuiserie-nice.fr', '0493456789'),
(6, 'Nantes', 'Thomas', 'Claire', 'claire.thomas@plomberie-nantes.fr', '0240567890'),
(7, 'Strasbourg', 'Robert', 'Antoine', 'antoine.robert@elec-strasbourg.fr', '0388678901'),
(8, 'Montpellier', 'Richard', 'Julie', 'julie.richard@maconnerie-montpellier.fr', '0467789012'),
(9, 'Bordeaux', 'Moreau', 'François', 'francois.moreau@peinture-bordeaux.fr', '0556890123'),
(10, 'Lille', 'Simon', 'Isabelle', 'isabelle.simon@menuiserie-lille.fr', '0320901234'),
(11, 'Paris', 'Laurent', 'Nicolas', 'nicolas.laurent@multiservices-paris.fr', '0147012345'),
(12, 'Lyon', 'Lefevre', 'Caroline', 'caroline.lefevre@renovation-lyon.fr', '0472123456'),
(13, 'Rennes', 'Garcia', 'David', 'david.garcia@plomberie-rennes.fr', '0299234567'),
(14, 'Reims', 'Martinez', 'Sylvie', 'sylvie.martinez@elec-reims.fr', '0326345678'),
(15, 'Tours', 'Rodriguez', 'Marc', 'marc.rodriguez@batiment-tours.fr', '0247456789');

-- ============================================================================
-- ENTREPRENEURS_has_CATEGORIES - Link entrepreneurs to their specialties
-- ============================================================================
INSERT INTO ENTREPRENEURS_has_CATEGORIES (ENTREPRENEURS_idEntrepreneur, CATEGORIES_idCategorie) VALUES
-- Jean Dupont - Plomberie specialist
(1, 1),
-- Sophie Martin - Electricité specialist
(2, 2),
-- Pierre Bernard - Maçonnerie specialist
(3, 3),
-- Marie Petit - Peinture specialist
(4, 4),
-- Luc Dubois - Menuiserie specialist
(5, 5),
-- Claire Thomas - Plomberie specialist
(6, 1),
-- Antoine Robert - Electricité specialist
(7, 2),
-- Julie Richard - Maçonnerie specialist
(8, 3),
-- François Moreau - Peinture specialist
(9, 4),
-- Isabelle Simon - Menuiserie specialist
(10, 5),
-- Nicolas Laurent - Multi-services (Plomberie, Electricité)
(11, 1),
(11, 2),
-- Caroline Lefevre - Multi-services (Maçonnerie, Peinture)
(12, 3),
(12, 4),
-- David Garcia - Plomberie specialist
(13, 1),
-- Sylvie Martinez - Electricité specialist
(14, 2),
-- Marc Rodriguez - Multi-services (Maçonnerie, Menuiserie)
(15, 3),
(15, 5);

-- ============================================================================
-- PRESTATIONS - 20 different services across categories
-- ============================================================================
INSERT INTO PRESTATIONS (idPrestation, libelle, CATEGORIES_idCategorie) VALUES
-- Plomberie (Category 1)
(1, 'Installation sanitaire', 1),
(2, 'Réparation fuite', 1),
(3, 'Installation chauffage', 1),
(4, 'Débouchage canalisation', 1),
-- Électricité (Category 2)
(5, 'Installation électrique complète', 2),
(6, 'Rénovation tableau électrique', 2),
(7, 'Installation domotique', 2),
(8, 'Mise aux normes électrique', 2),
-- Maçonnerie (Category 3)
(9, 'Construction mur', 3),
(10, 'Réparation façade', 3),
(11, 'Création ouverture', 3),
(12, 'Dalle béton', 3),
-- Peinture (Category 4)
(13, 'Peinture intérieure', 4),
(14, 'Peinture extérieure', 4),
(15, 'Ravalement façade', 4),
(16, 'Enduit décoratif', 4),
-- Menuiserie (Category 5)
(17, 'Installation fenêtres', 5),
(18, 'Pose parquet', 5),
(19, 'Installation cuisine', 5),
(20, 'Fabrication placard', 5);

-- ============================================================================
-- PROPRIETAIRES - 8 property owners
-- ============================================================================
INSERT INTO PROPRIETAIRES (idProprietaire, nomProprietaire, prenomProprietaire, emailProprietaire, telProprietaire) VALUES
(1, 'Lefebvre', 'Jacques', 'jacques.lefebvre@gmail.com', '0601234567'),
(2, 'Roux', 'Brigitte', 'brigitte.roux@outlook.fr', '0612345678'),
(3, 'Vincent', 'Christophe', 'christophe.vincent@yahoo.fr', '0623456789'),
(4, 'Fournier', 'Nathalie', 'nathalie.fournier@gmail.com', '0634567890'),
(5, 'Girard', 'Philippe', 'philippe.girard@hotmail.fr', '0645678901'),
(6, 'Andre', 'Monique', 'monique.andre@gmail.com', '0656789012'),
(7, 'Mercier', 'Alain', 'alain.mercier@orange.fr', '0667890123'),
(8, 'Blanc', 'Catherine', 'catherine.blanc@free.fr', '0678901234');

-- ============================================================================
-- BIENS - 12 properties in various cities
-- ============================================================================
INSERT INTO BIENS (idBien, adresseBien, villeBien, PROPRIETAIRES_idProprietaire) VALUES
(1, '15 Rue de la Paix', 'Paris', 1),
(2, '32 Avenue Victor Hugo', 'Lyon', 1),
(3, '8 Boulevard de la Liberté', 'Marseille', 2),
(4, '45 Rue Jean Jaurès', 'Toulouse', 3),
(5, '12 Promenade des Anglais', 'Nice', 3),
(6, '67 Cours Cambronne', 'Nantes', 4),
(7, '23 Place Kléber', 'Strasbourg', 5),
(8, '91 Rue de la République', 'Montpellier', 5),
(9, '56 Cours de l\'Intendance', 'Bordeaux', 6),
(10, '34 Rue Nationale', 'Lille', 7),
(11, '78 Boulevard Haussmann', 'Paris', 7),
(12, '19 Quai Saint-Antoine', 'Lyon', 8);

-- ============================================================================
-- INSPECTEURS - 5 inspectors with different sectors
-- ============================================================================
INSERT INTO INSPECTEURS (idInspecteur, nomInspecteur, prenomInspecteur, emailInspecteur, telInspecteur, secteurInspecteur) VALUES
(1, 'Chevalier', 'François', 'francois.chevalier@gesttravaux.fr', '0689012345', 'Île-de-France'),
(2, 'Bonnet', 'Sandrine', 'sandrine.bonnet@gesttravaux.fr', '0690123456', 'Auvergne-Rhône-Alpes'),
(3, 'Colin', 'Thierry', 'thierry.colin@gesttravaux.fr', '0601234568', 'Provence-Alpes-Côte d\'Azur'),
(4, 'Faure', 'Céline', 'celine.faure@gesttravaux.fr', '0612345679', 'Occitanie'),
(5, 'Rousseau', 'Benjamin', 'benjamin.rousseau@gesttravaux.fr', '0623456780', 'Pays de la Loire');

-- ============================================================================
-- DOCUMENTS - Sample documents
-- ============================================================================
INSERT INTO DOCUMENTS (idDocuments, libelleDoc, typeDoc, cheminDoc, dateUpload) VALUES
(1, 'Plan architecte - Rénovation Paris 15ème', 'Plan', '/documents/chantiers/plan_paris_15.pdf', '2025-09-15 10:30:00'),
(2, 'Permis de construire - Lyon Centre', 'Permis', '/documents/chantiers/permis_lyon_centre.pdf', '2025-09-20 14:20:00'),
(3, 'Devis détaillé - Marseille Vieux Port', 'Devis', '/documents/chantiers/devis_marseille.pdf', '2025-09-25 09:15:00'),
(4, 'Rapport inspection - Toulouse Capitole', 'Rapport', '/documents/chantiers/rapport_toulouse.pdf', '2025-10-01 16:45:00'),
(5, 'Photos avant travaux - Nice Centre', 'Photos', '/documents/chantiers/photos_nice.zip', '2025-10-05 11:20:00'),
(6, 'Contrat travaux - Nantes Erdre', 'Contrat', '/documents/chantiers/contrat_nantes.pdf', '2025-10-08 13:30:00'),
(7, 'Plan électrique - Strasbourg', 'Plan', '/documents/chantiers/plan_elec_strasbourg.pdf', '2025-10-10 08:50:00'),
(8, 'Certificat conformité - Bordeaux', 'Certificat', '/documents/chantiers/cert_bordeaux.pdf', '2025-10-12 15:10:00');

-- ============================================================================
-- CHANTIERS - 8 construction sites with different statuses
-- ============================================================================
INSERT INTO CHANTIERS (idChantier, villeChantier, adresseChantier, infoChantier, statutChantier, dateDebut, dateFin, INSPECTEURS_idInspecteur, BIENS_idBien, DOCUMENTS_idDocuments) VALUES
(1, 'Paris', '15 Rue de la Paix', 'Rénovation complète appartement 3 pièces', 1, '2025-09-15', '2025-12-15', 1, 1, 1),
(2, 'Lyon', '32 Avenue Victor Hugo', 'Installation système électrique et chauffage', 1, '2025-09-20', '2025-11-30', 2, 2, 2),
(3, 'Marseille', '8 Boulevard de la Liberté', 'Réparation façade et peinture extérieure', 0, '2025-10-01', '2025-12-20', 3, 3, 3),
(4, 'Toulouse', '45 Rue Jean Jaurès', 'Création ouverture et aménagement cuisine', 1, '2025-09-25', '2025-11-25', 4, 4, 4),
(5, 'Nice', '12 Promenade des Anglais', 'Rénovation salle de bain et plomberie', 2, '2025-08-01', '2025-10-15', 3, 5, 5),
(6, 'Nantes', '67 Cours Cambronne', 'Installation fenêtres double vitrage', 1, '2025-10-08', '2025-11-08', 5, 6, 6),
(7, 'Strasbourg', '23 Place Kléber', 'Mise aux normes électrique', 0, '2025-10-15', '2025-11-15', 1, 7, 7),
(8, 'Bordeaux', '56 Cours de l\'Intendance', 'Rénovation complète maison', 2, '2025-07-01', '2025-10-01', 4, 9, 8);

-- ============================================================================
-- DEVIS - Quotes for various construction sites
-- ============================================================================
INSERT INTO DEVIS (idDevis, prix, duree, dateCreation, statutDevis, PRESTATIONS_idPrestation, ENTREPRENEURS_idEntrepreneur, CHANTIERS_idChantier) VALUES
-- Chantier 1 (Paris - Rénovation complète)
(1, 8500.00, '2 mois', '2025-09-10 10:00:00', 'Accepté', 1, 1, 1),
(2, 6200.00, '1 mois', '2025-09-10 11:00:00', 'Accepté', 5, 2, 1),
(3, 4500.00, '3 semaines', '2025-09-10 14:00:00', 'Accepté', 13, 4, 1),
(4, 5800.00, '1 mois', '2025-09-10 15:00:00', 'Accepté', 17, 5, 1),

-- Chantier 2 (Lyon - Électricité et chauffage)
(5, 7200.00, '6 semaines', '2025-09-15 09:00:00', 'Accepté', 5, 2, 2),
(6, 9500.00, '2 mois', '2025-09-15 10:30:00', 'Accepté', 3, 6, 2),
(7, 6800.00, '5 semaines', '2025-09-15 11:00:00', 'Refusé', 5, 7, 2),

-- Chantier 3 (Marseille - Façade et peinture)
(8, 12000.00, '10 semaines', '2025-09-22 08:30:00', 'En attente', 10, 3, 3),
(9, 8900.00, '8 semaines', '2025-09-22 09:00:00', 'En attente', 14, 4, 3),
(10, 11500.00, '9 semaines', '2025-09-22 10:00:00', 'En attente', 10, 8, 3),

-- Chantier 4 (Toulouse - Ouverture et cuisine)
(11, 4200.00, '2 semaines', '2025-09-20 14:00:00', 'Accepté', 11, 3, 4),
(12, 7800.00, '4 semaines', '2025-09-20 15:00:00', 'Accepté', 19, 5, 4),
(13, 8200.00, '4 semaines', '2025-09-20 16:00:00', 'Refusé', 19, 10, 4),

-- Chantier 5 (Nice - Salle de bain)
(14, 6500.00, '3 semaines', '2025-07-25 10:00:00', 'Accepté', 1, 1, 5),
(15, 3200.00, '2 semaines', '2025-07-25 11:00:00', 'Accepté', 13, 9, 5),

-- Chantier 6 (Nantes - Fenêtres)
(16, 9800.00, '3 semaines', '2025-10-01 09:00:00', 'Accepté', 17, 5, 6),
(17, 9200.00, '3 semaines', '2025-10-01 10:00:00', 'Refusé', 17, 10, 6),

-- Chantier 7 (Strasbourg - Électricité)
(18, 5400.00, '4 semaines', '2025-10-10 11:00:00', 'En attente', 8, 7, 7),
(19, 5100.00, '4 semaines', '2025-10-10 12:00:00', 'En attente', 8, 14, 7),

-- Chantier 8 (Bordeaux - Rénovation complète)
(20, 15000.00, '2.5 mois', '2025-06-20 10:00:00', 'Accepté', 9, 3, 8),
(21, 8500.00, '2 mois', '2025-06-20 11:00:00', 'Accepté', 5, 2, 8),
(22, 12000.00, '2 mois', '2025-06-20 12:00:00', 'Accepté', 14, 9, 8),
(23, 6800.00, '1.5 mois', '2025-06-20 13:00:00', 'Accepté', 18, 10, 8);

-- ============================================================================
-- ADMINISTRATEURS - 2 system administrators
-- ============================================================================
INSERT INTO ADMINISTRATEURS (idAdministrateur, nomAdministrateur, prenomAdministrateur, mdpAdministrateur, droitAdministrateur, emailAdministrateur, dateCreation) VALUES
(1, 'Admin', 'Système', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'SuperAdmin', 'admin@gesttravaux.fr', '2025-08-01 08:00:00'),
(2, 'Gestionnaire', 'Principal', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Admin', 'gestionnaire@gesttravaux.fr', '2025-08-15 09:00:00');
-- Note: The password hash is for 'password' - should be changed in production!

-- ============================================================================
-- APPEL_OFFRE - Call for tenders
-- ============================================================================
INSERT INTO APPEL_OFFRE (idAppelOffre, prix, duree, description, datePublication, dateEcheance, statut) VALUES
(1, '50000-80000', '6 mois', 'Rénovation complète immeuble de bureaux - 5 étages - Paris 8ème', '2025-10-01 09:00:00', '2025-11-01', 'Ouvert'),
(2, '30000-45000', '3 mois', 'Installation système électrique et domotique - Villa moderne Lyon', '2025-10-05 10:00:00', '2025-10-25', 'Ouvert'),
(3, '20000-25000', '2 mois', 'Réfection façade et ravalement - Immeuble 3 étages Marseille', '2025-09-15 08:00:00', '2025-10-15', 'Clos'),
(4, '15000-20000', '6 semaines', 'Aménagement intérieur appartement standing - Toulouse', '2025-10-10 11:00:00', '2025-11-10', 'Ouvert'),
(5, '35000-50000', '4 mois', 'Rénovation complète maison ancienne - Bordeaux Centre', '2025-09-20 09:30:00', '2025-10-20', 'Clos');

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================================
-- Verification queries to check data integrity
-- ============================================================================

-- Count records in each table
SELECT 'CATEGORIES' as TableName, COUNT(*) as RecordCount FROM CATEGORIES
UNION ALL
SELECT 'ENTREPRENEURS', COUNT(*) FROM ENTREPRENEURS
UNION ALL
SELECT 'ENTREPRENEURS_has_CATEGORIES', COUNT(*) FROM ENTREPRENEURS_has_CATEGORIES
UNION ALL
SELECT 'PRESTATIONS', COUNT(*) FROM PRESTATIONS
UNION ALL
SELECT 'PROPRIETAIRES', COUNT(*) FROM PROPRIETAIRES
UNION ALL
SELECT 'BIENS', COUNT(*) FROM BIENS
UNION ALL
SELECT 'INSPECTEURS', COUNT(*) FROM INSPECTEURS
UNION ALL
SELECT 'DOCUMENTS', COUNT(*) FROM DOCUMENTS
UNION ALL
SELECT 'CHANTIERS', COUNT(*) FROM CHANTIERS
UNION ALL
SELECT 'DEVIS', COUNT(*) FROM DEVIS
UNION ALL
SELECT 'ADMINISTRATEURS', COUNT(*) FROM ADMINISTRATEURS
UNION ALL
SELECT 'APPEL_OFFRE', COUNT(*) FROM APPEL_OFFRE;

-- ============================================================================
-- End of data.sql
-- ============================================================================
