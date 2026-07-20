# Crossword  Jeu de Mots Croisés Web

> Application web de mots croisés thématiques (animaux, plage, fruits) avec gestion de joueurs, niveaux progressifs et sauvegarde de parties.


 ![Gameplay](docs/screenshot-login.png) 
 ![Gameplay](docs/screenshot-levels.png)   ![Gameplay](docs/screenshot-level3.png) 
 

## 🎯 Contexte

Projet backend Java orienté architecture MVC classique : gestion de joueurs, de niveaux thématiques et de sauvegardes de parties, avec rendu de vues côté serveur.

## ✨ Fonctionnalités principales

- **Système de niveaux** thématiques (animaux, plage, fruits) avec difficulté progressive
- **Comptes joueurs** : inscription, connexion, profil
- **Sauvegarde de parties** en cours pour reprendre plus tard
- **Rendu serveur** des grilles de mots croisés via Thymeleaf

## 🛠️ Stack technique

| Couche | Technologies |
|---|---|
| Backend | Java, Spring (MVC), Thymeleaf |
| Base de données | via repositories (JPA/Hibernate) |
| Build | Maven |

## 📁 Structure du projet

```
Crossword/
└── crossword/
    ├── src/main/java/com/example/crossword/
    │   ├── controller/    # Contrôleurs (Game, Home)
    │   ├── service/       # Logique métier (Game, Player)
    │   ├── model/         # Entités (Level, Player, SavedGame)
    │   └── repository/    # Accès données
    └── src/main/resources/templates/  # Vues Thymeleaf
```

## 🚀 Installation

```bash
cd Crossword/crossword
mvn clean install
mvn spring-boot:run
```
Application accessible sur `http://localhost:8080`.

## 🔭 Pistes d'amélioration

- Ajout d'une API REST pour découpler frontend/backend
- Tests unitaires (JUnit) sur `GameService`
- Génération procédurale des grilles au lieu de niveaux fixes
