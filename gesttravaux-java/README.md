# GestTravaux Pro - Application de Gestion de Travaux Immobiliers

Version 1.0 - Release 1

## Description

GestTravaux Pro est une application de bureau Java développée pour ImmοSync permettant la gestion complète des travaux immobiliers. L'application permet de gérer les entrepreneurs, les propriétaires, les biens, les chantiers et les prestations associées.

## Technologies Utilisées

- **Java 17** - Langage de programmation
- **JavaFX 21** - Framework d'interface graphique
- **Hibernate 6.4.1** - ORM pour la persistance des données
- **MySQL 8.0** - Base de données relationnelle
- **Maven 3.x** - Gestion des dépendances et build
- **JUnit 5** - Framework de tests unitaires
- **SLF4J/Logback** - Logging

## Architecture

L'application suit une architecture MVC (Model-View-Controller) en couches :

```
gesttravaux-java/
├── src/main/java/com/immosync/gesttravaux/
│   ├── MainApp.java                    # Point d'entrée de l'application
│   ├── config/
│   │   └── HibernateUtil.java          # Configuration Hibernate
│   ├── models/                          # Entités JPA
│   │   ├── Entrepreneur.java
│   │   ├── Categorie.java
│   │   ├── Proprietaire.java
│   │   ├── Bien.java
│   │   ├── Prestation.java
│   │   ├── Inspecteur.java
│   │   ├── Chantier.java
│   │   ├── Devis.java
│   │   ├── Document.java
│   │   ├── Administrateur.java
│   │   └── AppelOffre.java
│   ├── dao/                             # Couche d'accès aux données
│   │   ├── GenericDAO.java
│   │   ├── EntrepreneurDAO.java
│   │   ├── CategorieDAO.java
│   │   ├── ProprietaireDAO.java
│   │   ├── BienDAO.java
│   │   ├── PrestationDAO.java
│   │   ├── InspecteurDAO.java
│   │   ├── ChantierDAO.java
│   │   └── DevisDAO.java
│   ├── services/                        # Logique métier
│   │   ├── EntrepreneurService.java
│   │   ├── CategorieService.java
│   │   ├── ProprietaireService.java
│   │   ├── BienService.java
│   │   ├── PrestationService.java
│   │   └── ChantierService.java
│   ├── controllers/                     # Contrôleurs JavaFX
│   │   ├── MainController.java
│   │   ├── EntrepreneurController.java
│   │   ├── CategorieController.java
│   │   ├── ProprietaireController.java
│   │   ├── BienController.java
│   │   ├── PrestationController.java
│   │   └── ChantierController.java
│   └── utils/
│       └── AlertUtil.java               # Utilitaires d'interface
├── src/main/resources/
│   ├── hibernate.cfg.xml                # Configuration Hibernate
│   ├── fxml/                            # Vues JavaFX
│   │   ├── Main.fxml
│   │   ├── Entrepreneurs.fxml
│   │   ├── Categories.fxml
│   │   ├── Proprietaires.fxml
│   │   ├── Biens.fxml
│   │   ├── Prestations.fxml
│   │   └── Chantiers.fxml
│   └── css/
│       └── styles.css                   # Styles CSS
└── src/test/java/com/immosync/gesttravaux/tests/
    ├── EntrepreneurServiceTest.java     # Tests unitaires
    ├── CategorieServiceTest.java
    └── ChantierServiceTest.java
```

## Fonctionnalités

### Fonctionnalité 1 - CRUD Complet

L'application permet la gestion complète (Create, Read, Update, Delete) des entités suivantes :

1. **Entrepreneurs**
   - Gestion des informations personnelles (nom, prénom, email, téléphone)
   - Ville de déploiement
   - Relation many-to-many avec les catégories

2. **Catégories**
   - Types de travaux (Plomberie, Électricité, Maçonnerie, etc.)

3. **Propriétaires**
   - Gestion des informations personnelles
   - Relation one-to-many avec les biens

4. **Biens**
   - Adresse et ville du bien
   - Lien vers le propriétaire

5. **Prestations**
   - Libellé de la prestation
   - Lien vers la catégorie

### Fonctionnalité 2 - Gestion des Chantiers

1. **Création de chantiers**
   - Sélection du bien associé
   - Adresse et ville du chantier
   - Informations complémentaires

2. **Affectation d'inspecteurs**
   - Association d'un inspecteur au chantier
   - Suivi par secteur géographique

3. **Création de devis**
   - Sélection de prestations
   - Association à un entrepreneur
   - Prix et durée estimée

4. **Consultation des rapports d'inspection**
   - Visualisation des documents
   - Accès aux photos et rapports de l'inspecteur

## Installation et Configuration

### Prérequis

- JDK 17 ou supérieur
- Maven 3.6 ou supérieur
- MySQL 8.0 ou supérieur
- IDE Java (IntelliJ IDEA, Eclipse, NetBeans)

### Installation de la Base de Données

1. Créer la base de données MySQL :

```sql
CREATE DATABASE gesttravaux_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Créer un utilisateur MySQL (optionnel) :

```sql
CREATE USER 'gesttravaux_user'@'localhost' IDENTIFIED BY 'votre_mot_de_passe';
GRANT ALL PRIVILEGES ON gesttravaux_db.* TO 'gesttravaux_user'@'localhost';
FLUSH PRIVILEGES;
```

### Configuration de l'Application

1. Modifier le fichier `src/main/resources/hibernate.cfg.xml` :

```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/gesttravaux_db?useSSL=false&amp;serverTimezone=UTC</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">votre_mot_de_passe</property>
```

### Compilation et Exécution

1. Cloner ou télécharger le projet

2. Compiler avec Maven :

```bash
cd gesttravaux-java
mvn clean install
```

3. Exécuter l'application :

```bash
mvn javafx:run
```

Ou depuis votre IDE, exécuter la classe `MainApp.java`.

## Tests Unitaires

L'application inclut des tests JUnit 5 pour les services principaux.

Exécuter les tests :

```bash
mvn test
```

Les tests couvrent :
- Opérations CRUD sur les entrepreneurs
- Opérations CRUD sur les catégories
- Gestion des chantiers
- Validation des données

## Utilisation

### Démarrage

1. Lancer l'application via `MainApp.java`
2. L'interface principale s'affiche avec un menu de navigation
3. Sélectionner le module souhaité dans le menu "Gestion"

### Navigation

- **Menu Fichier** : Quitter l'application
- **Menu Gestion** : Accès aux différents modules (Entrepreneurs, Catégories, etc.)
- **Menu Aide** : À propos de l'application

### Opérations CRUD

Chaque module offre les mêmes opérations de base :

1. **Nouveau** : Créer une nouvelle entité
2. **Modifier** : Modifier l'entité sélectionnée
3. **Supprimer** : Supprimer l'entité sélectionnée (avec confirmation)
4. **Rechercher** : Filtrer les résultats
5. **Actualiser** : Recharger les données

### Gestion des Entrepreneurs

1. Cliquer sur "Gestion > Entrepreneurs"
2. Utiliser le formulaire à droite pour saisir les informations
3. Cliquer sur "Gérer Catégories" pour associer des catégories
4. Cliquer sur "Enregistrer" pour créer/modifier

### Gestion des Chantiers

1. Cliquer sur "Gestion > Chantiers"
2. Sélectionner un bien dans la liste déroulante
3. Affecter un inspecteur (optionnel)
4. Définir le statut (Non démarré, En cours, Terminé)
5. Ajouter des informations complémentaires
6. Utiliser "Consulter Rapport" pour voir les documents

## Modèle de Données

### Relations Principales

- **Entrepreneur ↔ Categorie** : Many-to-Many
- **Proprietaire → Bien** : One-to-Many
- **Categorie → Prestation** : One-to-Many
- **Bien → Chantier** : One-to-Many
- **Inspecteur → Chantier** : One-to-Many
- **Chantier → Devis** : One-to-Many
- **Chantier ↔ Document** : Many-to-Many
- **Entrepreneur → Devis** : One-to-Many
- **Prestation → Devis** : One-to-Many

## Validation des Données

L'application inclut des validations complètes :

- Champs obligatoires marqués par un astérisque (*)
- Validation des formats email
- Vérification d'unicité pour les emails
- Validation des statuts de chantier (0-2)
- Messages d'erreur explicites en français

## Gestion des Erreurs

- Toutes les exceptions sont capturées et loggées
- Messages d'erreur conviviaux affichés à l'utilisateur
- Transactions Hibernate avec rollback automatique en cas d'erreur
- Logging avec SLF4J/Logback dans les fichiers de log

## Contributions et Support

Pour toute question ou demande de support :
- Créer une issue dans le gestionnaire de projets
- Contacter l'équipe de développement ImmοSync

## Licence

© 2025 ImmοSync - Tous droits réservés

## Auteurs

Développé par l'équipe GestTravaux Pro pour ImmοSync

---

**Version** : 1.0
**Date de Release** : Janvier 2025
**Dernière mise à jour** : Janvier 2025
