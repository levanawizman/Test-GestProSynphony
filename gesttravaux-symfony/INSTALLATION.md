# Guide d'Installation - GestTravaux Pro

## Étapes d'installation complète

### 1. Prérequis

Vérifiez que vous avez :
- PHP 8.2 ou supérieur
- Composer installé
- MySQL 8.0 ou MariaDB
- Extensions PHP : pdo_mysql, ctype, iconv, xml, mbstring

Vérification :
```bash
php -v
composer -v
mysql --version
```

### 2. Installation des dépendances

```bash
cd /mnt/c/Users/Ichai\ Wizman/Desktop/Utils/Lev/SLAM/gesttravaux-symfony
composer install
```

### 3. Configuration de la base de données

1. Créer la base de données MySQL :
```sql
CREATE DATABASE gesttravaux_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'gesttravaux_user'@'localhost' IDENTIFIED BY 'votre_mot_de_passe';
GRANT ALL PRIVILEGES ON gesttravaux_db.* TO 'gesttravaux_user'@'localhost';
FLUSH PRIVILEGES;
```

2. Éditer le fichier `.env` :
```env
DATABASE_URL="mysql://gesttravaux_user:votre_mot_de_passe@127.0.0.1:3306/gesttravaux_db?serverVersion=8.0.32&charset=utf8mb4"
```

3. Générer un APP_SECRET :
```bash
php -r "echo bin2hex(random_bytes(16)) . PHP_EOL;"
```
Copier le résultat dans `.env` :
```env
APP_SECRET=votre_secret_généré
```

### 4. Créer les tables de la base de données

```bash
# Vérifier la configuration
php bin/console doctrine:database:create

# Créer les migrations (déjà faites dans le projet)
# php bin/console make:migration

# Exécuter les migrations
php bin/console doctrine:migrations:migrate
```

**Note**: Si les migrations n'existent pas encore, vous devrez les créer :
```bash
php bin/console make:migration
```

### 5. Créer un utilisateur inspecteur

#### Option A : Via console Symfony (recommandé)

Créer un fichier de commande pour créer un utilisateur :
```bash
php bin/console security:hash-password
```

Copier le hash et l'insérer manuellement en base.

#### Option B : Via SQL

```sql
-- Hasher le mot de passe 'password' avec bcrypt coût 13
-- Le hash ci-dessous correspond à 'password123'
INSERT INTO users (email, roles, password) VALUES
('inspecteur@gesttravaux.fr', '["ROLE_INSPECTEUR"]', '$2y$13$kIXAMPLEHashGoesHere');

-- Créer l'inspecteur
INSERT INTO inspecteurs (nomInspecteur, prenomInspecteur, emailInspecteur, telInspecteur, secteurInspecteur)
VALUES ('Test', 'Inspecteur', 'inspecteur@gesttravaux.fr', '0612345678', 'Paris');

-- Lier l'utilisateur à l'inspecteur
UPDATE inspecteurs SET user_id = (SELECT id FROM users WHERE email = 'inspecteur@gesttravaux.fr')
WHERE emailInspecteur = 'inspecteur@gesttravaux.fr';
```

### 6. Charger les données de test (optionnel)

```bash
mysql -u gesttravaux_user -p gesttravaux_db < sample_data.sql
```

**Important** : Vous devrez modifier les mots de passe hashés dans le fichier SQL.

### 7. Configurer les permissions des dossiers

```bash
# Linux/Mac
chmod -R 777 var/
chmod -R 777 public/uploads/

# Windows WSL
sudo chmod -R 777 var/
sudo chmod -R 777 public/uploads/
```

### 8. Lancer le serveur de développement

```bash
# Avec Symfony CLI (recommandé)
symfony server:start

# Ou avec PHP built-in server
php -S localhost:8000 -t public/
```

### 9. Accéder à l'application

Ouvrez votre navigateur :
- URL : http://localhost:8000/login
- Email : inspecteur@gesttravaux.fr (ou celui que vous avez créé)
- Mot de passe : password123 (ou celui que vous avez défini)

### 10. Tester sur mobile (PWA)

1. Sur votre réseau local, trouvez votre IP :
```bash
# Linux/Mac
ifconfig | grep inet

# Windows
ipconfig
```

2. Accédez depuis votre mobile à : `http://VOTRE_IP:8000/login`

3. Une fois connecté, utilisez "Ajouter à l'écran d'accueil" dans le menu du navigateur

## Vérification de l'installation

### Tests unitaires

```bash
php vendor/bin/phpunit
```

### Vérifier les routes

```bash
php bin/console debug:router
```

Vous devriez voir :
- `app_login` (GET|POST) : /login
- `inspecteur_dashboard` (GET) : /inspecteur/dashboard
- `inspecteur_chantiers_list` (GET) : /inspecteur/chantiers
- `inspecteur_chantier_detail` (GET) : /inspecteur/chantier/{id}
- `inspecteur_add_observation` (GET|POST) : /inspecteur/chantier/{chantierId}/add-observation
- `inspecteur_upload_document` (GET|POST) : /inspecteur/chantier/{chantierId}/upload-document

### Vérifier les entités

```bash
php bin/console doctrine:schema:validate
```

Doit afficher : "[OK] The database schema is in sync with the mapping files."

## Dépannage

### Erreur : "Connection refused"

Vérifiez que MySQL est démarré :
```bash
# Linux
sudo service mysql start

# Mac
brew services start mysql

# Windows
net start MySQL80
```

### Erreur : "Access denied for user"

Vérifiez les credentials dans `.env` et les permissions MySQL.

### Erreur : "Class not found"

```bash
composer dump-autoload
php bin/console cache:clear
```

### Erreur : "Permission denied" sur uploads/

```bash
chmod -R 777 public/uploads/
# ou
sudo chown -R www-data:www-data public/uploads/
```

### Les migrations ne fonctionnent pas

Si vous devez tout recréer :
```bash
php bin/console doctrine:database:drop --force
php bin/console doctrine:database:create
php bin/console make:migration
php bin/console doctrine:migrations:migrate
```

## Production

Pour déployer en production :

1. Configurer `.env.local` avec `APP_ENV=prod`
2. Optimiser Composer :
```bash
composer install --no-dev --optimize-autoloader
```
3. Vider et warmer le cache :
```bash
APP_ENV=prod php bin/console cache:clear
```
4. Configurer Apache/Nginx pour pointer vers `/public`
5. Désactiver le mode debug
6. Utiliser HTTPS
7. Sécuriser les uploads

## Support

En cas de problème, vérifiez :
- Les logs : `var/log/dev.log` ou `var/log/prod.log`
- La configuration : `php bin/console debug:config`
- Les services : `php bin/console debug:container`

Pour les erreurs Doctrine :
```bash
php bin/console doctrine:query:sql "SHOW TABLES;"
```
