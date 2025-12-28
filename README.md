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
