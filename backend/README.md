# Backend VoyageConnect

Ce répertoire contient le service backend pour l'application VoyageConnect. Il s'agit d'une application **Spring Boot** qui expose une **API RESTful** pour gérer toutes les opérations de l'application, telles que la gestion des utilisateurs, les réservations, les offres, etc.

## Stack Technique

-   **Framework**: Java 17, Spring Boot 3.x
-   **Accès aux données**: Spring Data JPA
-   **Sécurité**: Spring Security
-   **Base de données**: PostgreSQL (configurable via `application.properties`)
-   **Tests**: JUnit 5, Mockito, H2 (pour les tests d'intégration)

## Structure du Projet

Le projet suit une structure Maven standard :

```
.
├── pom.xml         # Fichier de configuration Maven
└── src
    ├── main
    │   ├── java        # Code source de l'application
    │   └── resources   # Fichiers de configuration (ex: application.properties)
    └── test
        └── java        # Code source des tests
```

## Démarrage

Pour lancer le service backend, exécutez la commande suivante depuis le répertoire `backend/` :

```bash
mvn spring-boot:run
```

Par défaut, le serveur démarre sur le port `8080`.

## Configuration

Avant de lancer l'application pour la première fois, vous devez créer votre propre fichier de configuration local.

1.  **Copiez le fichier d'exemple :**
    ```bash
    cp src/main/resources/application.properties.example src/main/resources/application.properties
    ```
2.  **Modifiez `application.properties` :**
    Ouvrez le nouveau fichier `src/main/resources/application.properties` et remplacez les placeholders (`your_username`, `your_password`, etc.) par vos informations d'identification de base de données PostgreSQL.

Ce fichier est ignoré par Git (`.gitignore`) pour garantir que vos informations sensibles ne soient jamais versionnées.

## Endpoints de l'API

Une fois le service démarré, les endpoints de l'API seront disponibles sous le préfixe `/api/v1/`. La documentation complète de l'API sera fournie via Swagger/OpenAPI.
