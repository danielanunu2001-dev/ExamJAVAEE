# Frontend - VoyageConnect UI

Bienvenue dans le frontend de **VoyageConnect**. Il s'agit d'une **application monopage (SPA)** moderne construite avec **React** et **TypeScript**, conçue pour offrir une expérience utilisateur fluide, rapide et responsive.

## Rôle de l'Application Frontend

Cette application est l'interface utilisateur de VoyageConnect. Elle a pour responsabilités de :

-   Présenter une interface utilisateur intuitive pour la recherche et la réservation de voyages.
-   Communiquer avec l'API REST du backend pour récupérer et envoyer des données.
-   Gérer l'état de l'application côté client (authentification, données de l'utilisateur, etc.).

## Stack Technique

-   **Framework** : React 18 avec Create React App
-   **Langage** : TypeScript
-   **Style** : Tailwind CSS
-   **Routage** : React Router
-   **Gestion d'État** : Zustand
-   **Tests** : React Testing Library & Jest

## Commandes du Projet

Toutes les commandes suivantes doivent être exécutées depuis le répertoire `frontend/`.

### `npm install`

Installe toutes les dépendances nécessaires pour le projet. À exécuter avant de lancer l'application pour la première fois.

### `npm start`

Lance le serveur de développement en mode "watch". L'application se rechargera automatiquement après chaque modification.

Ouvre [http://localhost:3000](http://localhost:3000) pour la visualiser dans votre navigateur.

### `npm test`

Lance la suite de tests avec Jest.

### `npm run build`

Compile l'application pour la production dans le répertoire `build/`. Cette version est optimisée pour la performance.

## Structure des Dossiers

Nous suivons une structure de dossiers organisée pour faciliter la maintenance :

```
src/
├── pages/          # Composants représentant des pages complètes (ex: HomePage, LoginPage)
├── components/     # Composants réutilisables (ex: Button, Input, Card)
├── services/       # Logique pour communiquer avec l'API backend
├── store/          # Fichiers de configuration pour la gestion d'état (Zustand)
└── styles/         # Fichiers CSS globaux ou de configuration
```
