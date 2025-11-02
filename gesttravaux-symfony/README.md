# GestTravaux Pro - Symfony 7 Web Application

Application web mobile pour la gestion de chantiers de construction et rénovation - Interface Inspecteur (Release 1)

## Description

GestTravaux Pro est une Progressive Web App (PWA) développée avec Symfony 7, permettant aux inspecteurs de chantier de :
- Consulter leurs chantiers assignés
- Visualiser les informations détaillées des chantiers sur une carte interactive
- Ajouter des observations avec photos depuis leur mobile
- Télécharger des documents techniques (DPE, diagnostics, etc.)

## Fonctionnalités Principales

### Fonctionnalité 2 - Interface Inspecteur Mobile (PWA)

1. **Authentification sécurisée**
   - Connexion par email/mot de passe
   - Gestion des rôles (ROLE_INSPECTEUR)

2. **Tableau de bord**
   - Vue d'ensemble des chantiers assignés
   - Statistiques par statut (En attente, En cours, Terminés)
   - Accès rapide aux chantiers

3. **Détail du chantier**
   - Informations complètes du chantier
   - Carte interactive (Leaflet.js) avec localisation du bien
   - Liste des devis et prestations
   - Informations du propriétaire

4. **Observations**
   - Formulaire d'ajout d'observations textuelles
   - Capture photo directe depuis mobile (HTML5 Camera API)
   - Upload de photos avec aperçu

5. **Documents**
   - Upload de documents PDF (DPE, diagnostics bruit/amiante/plomb, etc.)
   - Gestion des types de documents
   - Notes et commentaires

## Technologies Utilisées

- **Backend**: Symfony 7.0
- **ORM**: Doctrine ORM
- **Template Engine**: Twig
- **Frontend**: Bootstrap 5.3.2
- **Maps**: Leaflet.js 1.9.4
- **Icons**: Bootstrap Icons
- **File Upload**: VichUploaderBundle
- **Testing**: PHPUnit 10.5
- **PWA**: Service Worker, Web App Manifest

## Structure du Projet

```
gesttravaux-symfony/
├── config/                  # Configuration Symfony
│   ├── packages/           # Configuration des bundles
│   └── routes.yaml         # Routes
├── public/                 # Assets publics
│   ├── css/               # Styles CSS
│   ├── js/                # JavaScript
│   ├── uploads/           # Fichiers uploadés
│   ├── manifest.json      # PWA manifest
│   └── service-worker.js  # Service worker
├── src/
│   ├── Controller/        # Contrôleurs
│   ├── Entity/            # Entités Doctrine
│   ├── Form/              # Types de formulaires
│   ├── Repository/        # Repositories
│   └── Service/           # Services métier
├── templates/             # Templates Twig
│   ├── inspecteur/       # Vues inspecteur
│   └── security/         # Vues authentification
├── tests/                # Tests PHPUnit
└── composer.json         # Dépendances PHP
```

## Entités Principales

### Database Schema (conforme au MLD)

- **User**: Authentification (lié à Inspecteur)
- **Inspecteur**: Inspecteurs de chantier
- **Chantier**: Chantiers de construction/rénovation
- **Bien**: Biens immobiliers (propriétés)
- **Proprietaire**: Propriétaires des biens
- **Devis**: Devis/estimations
- **Prestation**: Types de prestations
- **Categorie**: Catégories de travaux
- **Entrepreneur**: Entrepreneurs/artisans
- **Document**: Documents et photos

## Installation

### Prérequis

- PHP 8.2 ou supérieur
- Composer
- MySQL 8.0 ou MariaDB
- Node.js (optionnel, pour les assets)

### Étapes d'installation

1. **Cloner/copier le projet**
   ```bash
   cd /chemin/vers/gesttravaux-symfony
   ```

2. **Installer les dépendances**
   ```bash
   composer install
   ```

3. **Configurer la base de données**

   Éditer le fichier `.env` et configurer la connexion:
   ```env
   DATABASE_URL="mysql://username:password@127.0.0.1:3306/gesttravaux_db?serverVersion=8.0.32&charset=utf8mb4"
   ```

4. **Créer la base de données**
   ```bash
   php bin/console doctrine:database:create
   php bin/console doctrine:migrations:migrate
   ```

5. **Créer un utilisateur inspecteur de test**
   ```bash
   php bin/console doctrine:fixtures:load
   # Ou créer manuellement via SQL
   ```

6. **Lancer le serveur de développement**
   ```bash
   symfony server:start
   # ou
   php -S localhost:8000 -t public/
   ```

7. **Accéder à l'application**
   - URL: http://localhost:8000/login
   - Email: inspecteur@gesttravaux.fr (à créer)
   - Mot de passe: (à définir)

## Configuration

### VichUploader

Les répertoires d'upload sont configurés dans `config/packages/vich_uploader.yaml`:
- Photos: `public/uploads/photos`
- Documents: `public/uploads/documents`

Assurez-vous que ces répertoires sont accessibles en écriture:
```bash
chmod -R 777 public/uploads/
```

### Security

La configuration de sécurité est dans `config/packages/security.yaml`:
- Firewall principal avec form_login
- Protection des routes `/inspecteur/*` avec ROLE_INSPECTEUR
- CSRF activé sur les formulaires

## Tests

### Lancer les tests

```bash
# Tous les tests
php vendor/bin/phpunit

# Avec couverture de code
php vendor/bin/phpunit --coverage-html coverage/
```

### Tests inclus

- `ChantierControllerTest`: Tests du contrôleur de chantiers
- `FileUploadServiceTest`: Tests du service d'upload de fichiers

**Note**: Les tests des contrôleurs nécessitent des fixtures de base de données pour être complets.

## PWA (Progressive Web App)

L'application est installable sur mobile:

1. **Manifest**: `public/manifest.json` définit l'apparence de l'app
2. **Service Worker**: `public/service-worker.js` gère le cache offline
3. **Installation**: Sur mobile, "Ajouter à l'écran d'accueil"

### Fonctionnalités PWA

- Installable sur écran d'accueil
- Mode hors ligne basique (cache statique)
- Optimisé pour mobile
- Accès caméra natif

## API REST

### Endpoints disponibles

- `GET /api/chantier/{id}/json`: Récupère les données d'un chantier en JSON

## Sécurité

- Authentification par formulaire (Symfony Security)
- CSRF protection activée
- Validation des uploads (types, tailles)
- Protection des routes par rôle
- Hachage sécurisé des mots de passe

## Responsive Design

- Mobile-first approach
- Bootstrap 5 responsive grid
- Touch-friendly UI (min 44px buttons)
- Optimisé pour tablettes et mobiles

## Développement

### Commandes utiles

```bash
# Créer une entité
php bin/console make:entity

# Créer une migration
php bin/console make:migration

# Créer un contrôleur
php bin/console make:controller

# Créer un formulaire
php bin/console make:form

# Vider le cache
php bin/console cache:clear
```

## Déploiement

### Production

1. Configurer les variables d'environnement dans `.env.local`
2. Optimiser l'autoloader:
   ```bash
   composer install --no-dev --optimize-autoloader
   ```
3. Compiler les assets si nécessaire
4. Configurer le serveur web (Apache/Nginx)
5. Sécuriser les uploads et permissions

## Auteur

GestTravaux Pro - Projet SLAM 2025-2026

## License

Propriétaire - Usage éducatif uniquement

## Support

Pour toute question ou problème:
- Vérifier les logs: `var/log/`
- Activer le mode debug dans `.env`: `APP_ENV=dev`
- Consulter la documentation Symfony: https://symfony.com/doc/current/

## Roadmap

- [ ] Fixtures de données de test
- [ ] Tests fonctionnels complets
- [ ] Optimisation des images uploadées
- [ ] Notifications push
- [ ] Mode hors ligne complet
- [ ] Export PDF des rapports
- [ ] Signature électronique
