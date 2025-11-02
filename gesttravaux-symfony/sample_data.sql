-- GestTravaux Pro - Sample Data
-- This file contains sample data for testing the application

-- Note: Run migrations first to create the tables
-- php bin/console doctrine:migrations:migrate

-- Insert sample users (password is 'password' hashed with bcrypt)
-- In production, use: php bin/console security:hash-password
INSERT INTO users (email, roles, password) VALUES
('inspecteur1@gesttravaux.fr', '["ROLE_INSPECTEUR"]', '$2y$13$QEXAMPLEHashedPasswordHere'),
('inspecteur2@gesttravaux.fr', '["ROLE_INSPECTEUR"]', '$2y$13$QEXAMPLEHashedPasswordHere');

-- Insert sample inspecteurs
INSERT INTO inspecteurs (nomInspecteur, prenomInspecteur, emailInspecteur, telInspecteur, secteurInspecteur) VALUES
('Dupont', 'Jean', 'inspecteur1@gesttravaux.fr', '0612345678', 'Paris Nord'),
('Martin', 'Marie', 'inspecteur2@gesttravaux.fr', '0623456789', 'Paris Sud');

-- Link users to inspecteurs (update user_id after users are created)
-- UPDATE inspecteurs SET user_id = 1 WHERE emailInspecteur = 'inspecteur1@gesttravaux.fr';
-- UPDATE inspecteurs SET user_id = 2 WHERE emailInspecteur = 'inspecteur2@gesttravaux.fr';

-- Insert sample categories
INSERT INTO categories (type) VALUES
('Plomberie'),
('Électricité'),
('Maçonnerie'),
('Menuiserie'),
('Peinture');

-- Insert sample prestations
INSERT INTO prestations (libelle, categories_idCategorie) VALUES
('Installation chaudière', 1),
('Réparation fuite', 1),
('Installation électrique complète', 2),
('Mise aux normes électriques', 2),
('Construction mur porteur', 3),
('Rénovation façade', 3),
('Pose fenêtres PVC', 4),
('Pose parquet', 4),
('Peinture intérieure', 5),
('Peinture extérieure', 5);

-- Insert sample proprietaires
INSERT INTO proprietaires (nomProprietaire, prenomProprietaire, emailProprietaire, telProprietaire) VALUES
('Leroy', 'Pierre', 'pierre.leroy@email.com', '0634567890'),
('Bernard', 'Sophie', 'sophie.bernard@email.com', '0645678901'),
('Petit', 'Luc', 'luc.petit@email.com', '0656789012');

-- Insert sample biens
INSERT INTO biens (adresseBien, villeBien, fk_idProprietaire, proprietaires_idProprietaire, latitude, longitude) VALUES
('12 Rue de la République', 'Paris', '1', 1, 48.8566, 2.3522),
('45 Avenue des Champs', 'Lyon', '2', 2, 45.7640, 4.8357),
('78 Boulevard Victor Hugo', 'Marseille', '3', 3, 43.2965, 5.3698);

-- Insert sample entrepreneurs
INSERT INTO entrepreneurs (villeDeploiment, nomEntrepreneur, prenomEntrepreneur, emailEntrepreneur, telEntrepreneur) VALUES
('Paris', 'Rousseau', 'Marc', 'marc.rousseau@entreprise.fr', '0667890123'),
('Lyon', 'Moreau', 'Julie', 'julie.moreau@entreprise.fr', '0678901234'),
('Marseille', 'Simon', 'Paul', 'paul.simon@entreprise.fr', '0689012345');

-- Link entrepreneurs to categories
INSERT INTO entrepreneurs_has_categories (entrepreneurs_idEntrepreneur, categories_idCategorie) VALUES
(1, 1), (1, 2),  -- Marc Rousseau: Plomberie, Électricité
(2, 3), (2, 4),  -- Julie Moreau: Maçonnerie, Menuiserie
(3, 5), (3, 1);  -- Paul Simon: Peinture, Plomberie

-- Insert sample chantiers
INSERT INTO chantiers (villeChantier, adresseChantier, infoChantier, statutChantier, inspecteurs_idInspecteur, biens_idBien) VALUES
('Paris', '12 Rue de la République', 'Rénovation complète appartement', 1, 1, 1),
('Lyon', '45 Avenue des Champs', 'Installation nouvelle cuisine', 1, 1, 2),
('Marseille', '78 Boulevard Victor Hugo', 'Réfection toiture', 0, 2, 3);

-- Insert sample devis
INSERT INTO devis (prix, duree, prestations_idPrestation, entrepreneurs_idEntrepreneur, chantiers_idChantier) VALUES
(5500, '2 semaines', 1, 1, 1),
(3200, '1 semaine', 2, 1, 1),
(8900, '3 semaines', 3, 2, 2),
(12000, '4 semaines', 5, 3, 3);

-- Insert sample documents (without actual files - files need to be uploaded via the app)
INSERT INTO documents (libelleDoc, fileType, uploadedAt) VALUES
('DPE Appartement Paris', 'dpe', NOW()),
('Diagnostic Bruit Lyon', 'diagnostic_bruit', NOW()),
('Photos état initial Marseille', 'photo', NOW());

-- Link documents to chantiers
UPDATE chantiers SET documents_idDocuments = 1, documents_libelleDoc = 'DPE Appartement Paris' WHERE idChantier = 1;
UPDATE chantiers SET documents_idDocuments = 2, documents_libelleDoc = 'Diagnostic Bruit Lyon' WHERE idChantier = 2;

-- Note: Passwords need to be hashed properly
-- To create a user with hashed password, use:
-- php bin/console security:hash-password

-- Example commands after loading this data:
-- 1. Login with inspecteur1@gesttravaux.fr
-- 2. View chantiers #1 and #2 (assigned to inspecteur 1)
-- 3. Add observations and upload documents
