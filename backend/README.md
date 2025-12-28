# Backend VoyageConnect

Ce répertoire contient le service backend pour l'application VoyageConnect. Il s'agit d'une application **Spring Boot** qui expose une **API RESTful** pour gérer toutes les opérations de l'application, telles que la gestion des utilisateurs, les réservations, les offres, etc.

## Stack Technique

-   **Framework**: Java 17, Spring Boot 3.x
-   **Accès aux données**: Spring Data JPA
-   **Sécurité**: Spring Security
 -   **Docker**: `docker-compose.yml` fourni pour dev (Postgres + backend + frontend)
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

## Démarrage local (dev)

Le backend peut être démarré seul ou via `docker compose` depuis la racine du repo.

Lancer avec Maven :

```bash
cd backend
mvn spring-boot:run
```

Le service écoute par défaut sur le port `8080`.

Via Docker Compose (recommandé pour dev local) :

```bash
docker compose build backend
docker compose up -d backend db
```

## Configuration et variables d'environnement

- `app.jwt.secret` (ou `APP_JWT_SECRET`) : secret pour signer les JWT. **Doit être changé en production**.
- `app.jwt.expire-ms` (ou `APP_JWT_EXPIRE_MS`) : temps d'expiration du token en millisecondes (par défaut 86400000).
- `app.cookie.secure` (ou `APP_COOKIE_SECURE`) : si `true`, le cookie `VC_TOKEN` est marqué `Secure` (nécessite HTTPS).
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` : connexion à PostgreSQL.

Vous pouvez copier `src/main/resources/application.properties.example` en `application.properties` pour une configuration locale, mais en prod utilisez des variables d'environnement ou un gestionnaire de secrets.

## Endpoints importants

- Auth:
    - `POST /api/auth/register` — enregistrement utilisateur
    - `POST /api/auth/login` — authentification (retourne cookie HttpOnly `VC_TOKEN`)
    - `POST /api/auth/logout` — efface le cookie `VC_TOKEN`
- Destinations (public):
    - `GET /api/destinations` — recherche des destinations (exclut `deleted` et `inactive`)
- Admin (nécessite rôle ADMIN via JWT):
    - `GET /api/admin/destinations` — liste complète (inclut `deleted` flag)
    - `POST /api/admin/destinations` — création
    - `PUT /api/admin/destinations/{id}` — mise à jour
    - `DELETE /api/admin/destinations/{id}` — soft-delete (`deleted=true`)
    - `PATCH /api/admin/destinations/{id}/restore` — restore soft-delete
    - `DELETE /api/admin/destinations/{id}/purge` — purge définitive (option `?force=true` pour supprimer bookings liés)

## Base de données & migrations

Les migrations Flyway sont dans `src/main/resources/db/migration`. Lors du démarrage, Flyway applique automatiquement les migrations.

## Tests

Exécuter les tests unitaires et d'intégration :

```bash
cd backend
./mvnw test
```

## Production

- Utilisez HTTPS et activez `APP_COOKIE_SECURE=true`.
- Définissez `APP_JWT_SECRET` via votre système d'orchestration (Kubernetes secret, variable d'environnement CI/CD).
- Ne laissez pas `SPRING_JPA_HIBERNATE_DDL_AUTO=update` en production sans audit—préférez des migrations contrôlées via Flyway.

