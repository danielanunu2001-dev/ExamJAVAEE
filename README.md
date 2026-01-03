# VoyageConnect - Refonte Moderne

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Test Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen)
![License](https://img.shields.io/badge/license-MIT-blue)

**VoyageConnect** est une plateforme web complète de réservation de voyages, entièrement refondue pour adopter une architecture moderne, sécurisée et évolutive. Ce projet sert de démonstration pour la mise en œuvre des meilleures pratiques de développement web, de la base de données à l'interface utilisateur.

## Fonctionnalités Clés

-   **Gestion des Utilisateurs :** Inscription, authentification sécurisée (JWT), et gestion de profil.
-   **Recherche de Destinations :** Exploration d'un catalogue de destinations de voyage.
-   **Système de Réservation :** Processus de réservation simple et gestion des réservations existantes.
-   **Interface d'Administration :** Panneau de contrôle pour la gestion des offres et des utilisateurs.
-   **Design Responsive :** Expérience utilisateur optimisée pour le web et le mobile.

## Architecture du Projet

Ce projet est un **monorepo** qui sépare clairement le backend et le frontend :

-   `backend/`: Une API RESTful développée avec **Spring Boot** qui gère toute la logique métier et la persistance des données.
-   `frontend/`: Une application monopage (SPA) développée avec **React** qui offre une expérience utilisateur riche et interactive.

Consultez les `README.md` respectifs dans chaque répertoire pour plus de détails techniques.

## Stack Technique

| Domaine           | Technologie                                                              |
| ----------------- | ------------------------------------------------------------------------ |
| **Backend**       | Java 17, Spring Boot 3, Spring Security, Spring Data JPA                 |
| **Frontend**      | React 18, TypeScript, React Router, Zustand, Tailwind CSS                |
| **Base de Données** | PostgreSQL, Flyway (pour les migrations)                                 |
| **Tests**         | JUnit 5 (Backend), React Testing Library (Frontend)                      |
| **Conteneurisation** | Docker (à venir)                                                         |

## Démarrage Rapide

### Prérequis

-   Java 17+ & Maven 3.8+
-   Node.js 18+ & npm 9+
-   PostgreSQL 14+

### Instructions

1.  **Clonez le dépôt :**
    ```bash
    git clone <URL_DU_DEPOT>
    cd voyageconnect
    ```

2.  **Configurez le Backend :**
    Suivez les instructions dans le `backend/README.md` pour configurer la base de données et lancer le serveur.

3.  **Lancez le Frontend :**
    Suivez les instructions dans le `frontend/README.md` pour installer les dépendances et démarrer le serveur de développement.

Une fois lancé, le backend sera accessible sur `http://localhost:8080` et le frontend sur `http://localhost:3000`.
