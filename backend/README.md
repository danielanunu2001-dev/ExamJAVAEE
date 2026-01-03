# Backend - VoyageConnect API

Bienvenue dans le backend de **VoyageConnect**. Ce service est une **API RESTful** construite avec **Spring Boot**, conçue pour être robuste, sécurisée et évolutive. Elle sert de cerveau à l'application VoyageConnect, gérant toutes les données et la logique métier.

## Rôle de l'API

L'API expose des endpoints pour gérer :

-   L'authentification et l'autorisation des utilisateurs.
-   Les opérations CRUD (Create, Read, Update, Delete) pour les destinations, les réservations, etc.
-   La logique métier complexe liée aux processus de réservation.

## Stack Technique

-   **Framework** : Java 17, Spring Boot 3.x
-   **Sécurité** : Spring Security (avec support JWT à venir)
-   **Accès aux Données** : Spring Data JPA / Hibernate
-   **Base de Données** : PostgreSQL
-   **Migrations** : Flyway
-   **Tests** : JUnit 5, Mockito, Testcontainers

## Démarrage

### 1. Configuration de la Base de Données

Ce projet utilise PostgreSQL. Avant de lancer l'application, assurez-vous d'avoir une instance PostgreSQL en cours d'exécution et d'avoir créé une base de données et un utilisateur dédiés.

**Exemple de commandes SQL :**
```sql
CREATE DATABASE voyageconnect;
CREATE USER voyageconnect_user WITH PASSWORD 'votre_mot_de_passe_securise';
GRANT ALL PRIVILEGES ON DATABASE voyageconnect TO voyageconnect_user;
```

### 2. Fichier de Configuration Local

Créez votre fichier de configuration local à partir de l'exemple fourni :

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Modifiez ensuite `src/main/resources/application.properties` avec les informations de connexion de votre base de données. Ce fichier est ignoré par Git pour des raisons de sécurité.

### 3. Lancer l'Application

Une fois la configuration terminée, lancez le serveur depuis le répertoire `backend/` :

```bash
mvn spring-boot:run
```

Le serveur démarrera par défaut sur `http://localhost:8080`.

## Migrations de la Base de Données

Les migrations du schéma de la base de données sont gérées par **Flyway**. Les scripts de migration sont situés dans `src/main/resources/db/migration`.

-   Flyway exécute automatiquement les nouveaux scripts de migration au démarrage de l'application.
-   Les scripts sont nommés selon le format `V<VERSION>__<DESCRIPTION>.sql` (ex: `V1__create_initial_tables.sql`).

## Tests

Pour lancer la suite de tests, qui utilise une base de données en mémoire H2 pour l'isolation, exécutez :

```bash
mvn test
```
