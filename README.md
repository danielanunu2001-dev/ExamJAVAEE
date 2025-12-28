# VoyageConnect 

Ce dépôt contient la refonte complète du projet VoyageConnect, structuré en monorepo avec un backend moderne et une application frontend monopage (SPA).

## Structure du Projet

-   `backend/`: Contient l'application backend développée avec **Spring Boot**. Elle expose une API REST pour toutes les fonctionnalités de l'application. Pour plus de détails, consultez le `README.md` situé dans ce répertoire.
-   `frontend/`: Contient l'application frontend développée avec **React** et **TypeScript**. C'est une application monopage qui consomme l'API du backend.

## Démarrage Rapide

### Prérequis

-   Java 17+
-   Maven 3.8+
-   Node.js 18+
-   npm 9+

### Lancement du Backend

1.  Accédez au répertoire du backend :
    ```bash
    cd backend
    ```
2.  Lancez l'application Spring Boot :
    ```bash
    mvn spring-boot:run
    ```

L'API sera accessible à l'adresse `http://localhost:8080`.

### Lancement du Frontend

1.  Dans un autre terminal, accédez au répertoire du frontend :
    ```bash
    cd frontend
    ```
2.  Installez les dépendances :
    ```bash
    npm install
    ```
3.  Lancez le serveur de développement :
    ```bash
    npm start
    ```

L'application sera accessible à l'adresse `http://localhost:3000`.

## Notes de déploiement & sécurité

Quelques points importants pour une mise en production sécurisée :

- Variables d'environnement (définies dans `docker-compose.yml` pour l'environnement de développement) :
    - `APP_JWT_SECRET` : **obligatoire en production** — mettez une valeur secrète forte.
    - `APP_JWT_EXPIRE_MS` : durée d'expiration du token JWT en millisecondes (ex. `86400000`).
    - `APP_COOKIE_SECURE` : `true` pour marquer le cookie `VC_TOKEN` comme `Secure` (nécessite HTTPS).

- Authentification : le backend utilise des JWT HMAC et émet un cookie HttpOnly `VC_TOKEN` lors du `POST /api/auth/login`. Utiliser `credentials: 'include'` côté frontend.

- Logout : `POST /api/auth/logout` efface le cookie `VC_TOKEN` côté serveur.

- Soft-delete : les destinations utilisent désormais un flag `deleted` (soft-delete). Les appels publics (`GET /api/destinations`) excluent les destinations `deleted` et `inactive`. L'admin peut :
    - marquer une destination comme `deleted` via `DELETE /api/admin/destinations/{id}` (soft-delete),
    - restaurer via `PATCH /api/admin/destinations/{id}/restore`.

- Purge définitive : pour supprimer physiquement une destination, utiliser `DELETE /api/admin/destinations/{id}/purge`. Si des réservations (`bookings`) pointent sur la destination :
    - sans `force` → réponse HTTP 409 (prévenir l'admin),
    - avec `?force=true` → le backend supprime d'abord les bookings liés puis supprime la destination.

Veillez à ne pas exposer `APP_JWT_SECRET` dans un dépôt public et à utiliser HTTPS + `APP_COOKIE_SECURE=true` en production.

## Lancer l'application (Docker Compose) - développement

1. Construire et démarrer les services (Postgres, backend, frontend) :

```bash
docker compose build
docker compose up -d
```

2. Vérifier que les services sont opérationnels :

```bash
docker compose ps
```

3. Frontend : http://localhost:3000
4. Backend API : http://localhost:8080

## Variables d'environnement importantes

- `APP_JWT_SECRET` : secret JWT (à définir en production)
- `APP_JWT_EXPIRE_MS` : durée de validité du token en ms
- `APP_COOKIE_SECURE` : `true` pour forcer le cookie `VC_TOKEN` Secure (HTTPS requis)
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` : connexion à Postgres

Dans le fichier `docker-compose.yml` (dev) ces variables sont déjà définies mais **changez** `APP_JWT_SECRET` avant déploiement.

## Créer un compte admin (dev rapide)

1. Compte admin par défaut créé durant l'itération: email `admin@voyage.local` / mot de passe `Admin1234`.
     - Si vous préférez créer un nouvel admin :
         - Enregistrer un utilisateur via `POST /api/auth/register` puis promouvoir via la table `users` (SQL) ou en utilisant un endpoint admin existant si déjà connecté.

## Tests end-to-end rapides

Un script d'exemple est présent pour exécuter une suite E2E basique via curl et stocke le rapport dans `/tmp/e2e_report.txt` :

```bash
bash /tmp/run_e2e.sh
cat /tmp/e2e_report.txt
```

## Mise en production (recommandations)

- Ne pas stocker le secret JWT dans le dépôt ; utilisez un gestionnaire de secrets ou variables d'environnement CI/CD.
- Servir le frontend via HTTPS (nginx/Cloud CDN) et activer `APP_COOKIE_SECURE=true`.
- Utiliser une base de données managée ou sauvegardes régulières.
- Ajouter une stratégie de rotation des secrets et politiques de sauvegarde.

Pour toute aide sur le déploiement, je peux fournir des manifests Kubernetes ou des templates Terraform.
