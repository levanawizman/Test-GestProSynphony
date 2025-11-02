# Quick Start Guide - GestTravaux Pro

## 5-Minute Setup

### 1. Install Dependencies (2 min)
```bash
cd "/mnt/c/Users/Ichai Wizman/Desktop/Utils/Lev/SLAM/gesttravaux-symfony"
composer install
```

### 2. Configure Database (1 min)
Edit `.env`:
```env
DATABASE_URL="mysql://root:@127.0.0.1:3306/gesttravaux_db?serverVersion=8.0.32&charset=utf8mb4"
APP_SECRET=ChangeThisToASecretRandomString32Chars
```

### 3. Create Database (1 min)
```bash
php bin/console doctrine:database:create
php bin/console make:migration
php bin/console doctrine:migrations:migrate
```

### 4. Create Test User (1 min)

**Option A - Hash a password:**
```bash
php bin/console security:hash-password
# Enter: password123
# Copy the hash
```

**Option B - Use SQL directly:**
```sql
-- Connect to MySQL
mysql -u root -p gesttravaux_db

-- Create user (password is 'password123')
INSERT INTO users (email, roles, password) VALUES
('inspecteur@test.fr', '["ROLE_INSPECTEUR"]', '$2y$13$YourHashedPasswordHere');

-- Create inspecteur
INSERT INTO inspecteurs (nomInspecteur, prenomInspecteur, emailInspecteur, telInspecteur, secteurInspecteur)
VALUES ('Test', 'Inspecteur', 'inspecteur@test.fr', '0612345678', 'Paris');

-- Link them (get the IDs from previous inserts)
UPDATE inspecteurs SET user_id = 1 WHERE id = 1;
```

### 5. Start Server
```bash
php -S localhost:8000 -t public/
```

### 6. Login
- Open: http://localhost:8000/login
- Email: inspecteur@test.fr
- Password: password123

## Common Commands

### Development
```bash
# Start server
php -S localhost:8000 -t public/

# Clear cache
php bin/console cache:clear

# Check routes
php bin/console debug:router

# Validate database schema
php bin/console doctrine:schema:validate
```

### Database
```bash
# Create database
php bin/console doctrine:database:create

# Drop database
php bin/console doctrine:database:drop --force

# Generate migration
php bin/console make:migration

# Run migrations
php bin/console doctrine:migrations:migrate

# Run SQL query
php bin/console doctrine:query:sql "SELECT * FROM users"
```

### Testing
```bash
# Run all tests
php vendor/bin/phpunit

# Run specific test
php vendor/bin/phpunit tests/Service/FileUploadServiceTest.php

# Generate coverage
php vendor/bin/phpunit --coverage-html coverage/
```

### Create Entities/Controllers
```bash
# Create entity
php bin/console make:entity

# Create controller
php bin/console make:controller

# Create form
php bin/console make:form
```

## Troubleshooting Quick Fixes

### "Connection refused"
```bash
# Check MySQL is running
sudo service mysql start    # Linux
brew services start mysql   # Mac
net start MySQL80           # Windows
```

### "Permission denied" on uploads
```bash
chmod -R 777 public/uploads/
chmod -R 777 var/
```

### "Class not found"
```bash
composer dump-autoload
php bin/console cache:clear
```

### Reset Everything
```bash
# Drop and recreate database
php bin/console doctrine:database:drop --force
php bin/console doctrine:database:create
php bin/console doctrine:migrations:migrate

# Clear all caches
rm -rf var/cache/*
php bin/console cache:clear
```

## Sample Data

Load sample data (optional):
```bash
mysql -u root -p gesttravaux_db < sample_data.sql
```

**Note**: You'll need to update the password hashes in sample_data.sql first!

## Test on Mobile

### Find your local IP:
```bash
# Linux/Mac
ifconfig | grep inet

# Windows
ipconfig
```

### Access from mobile:
```
http://YOUR_LOCAL_IP:8000/login
```

### Install as PWA:
1. Open in mobile browser
2. Menu → "Add to Home Screen"
3. Launch from home screen

## File Permissions

Required permissions:
```bash
# Upload directories (must be writable)
chmod -R 777 public/uploads/photos/
chmod -R 777 public/uploads/documents/

# Cache and logs (must be writable)
chmod -R 777 var/cache/
chmod -R 777 var/log/

# Console (must be executable)
chmod +x bin/console
```

## URLs Map

- `/` → Redirects to `/login`
- `/login` → Login page
- `/logout` → Logout
- `/inspecteur/dashboard` → Inspector dashboard
- `/inspecteur/chantiers` → List all chantiers
- `/inspecteur/chantier/{id}` → Chantier detail
- `/inspecteur/chantier/{id}/add-observation` → Add observation
- `/inspecteur/chantier/{id}/upload-document` → Upload document
- `/api/chantier/{id}/json` → Get chantier JSON

## Environment Variables

Key variables in `.env`:

```env
# Application
APP_ENV=dev                 # dev or prod
APP_SECRET=YourSecretKey    # Random 32+ chars

# Database
DATABASE_URL="mysql://user:password@host:port/database"

# For production
APP_ENV=prod
APP_DEBUG=0
```

## Production Checklist

Before deploying:
1. Set `APP_ENV=prod`
2. Set strong `APP_SECRET`
3. Configure real database
4. Run: `composer install --no-dev --optimize-autoloader`
5. Run: `php bin/console cache:clear --env=prod`
6. Set file permissions properly
7. Configure web server
8. Enable HTTPS

## Need Help?

1. Check logs: `var/log/dev.log`
2. See full docs: `README.md`
3. Installation guide: `INSTALLATION.md`
4. Verify setup: `CHECKLIST.md`

## Default Test Data

After running migrations, you need to create:
- At least 1 User with ROLE_INSPECTEUR
- At least 1 Inspecteur linked to that User
- At least 1 Chantier assigned to that Inspecteur
- Optional: Biens, Proprietaires, Devis, Prestations

See `sample_data.sql` for complete example dataset.

---

**Ready to go!** 🚀

Login and start managing chantiers from your mobile device!
