# Serveur MMORPG - Projet Intégré Master 1

## Description

Ce projet correspond au **serveur de jeu** pour un MMORPG développé dans le cadre du Projet Intégré de Master 1.
Le serveur gère toutes les fonctionnalités principales du jeu : connexion des joueurs, gestion du monde (chunks, cases), communication client-serveur, gestion des combats, inventaires, compétences et sauvegarde des données.

Le serveur est développé en **Java 11** en utilisant **Spring Boot** pour organiser les différents composants (Controllers, Entities, DTO).

---

## Fonctionnalités principales

- Gestion des connexions de joueurs
- Gestion des déplacements sur la carte (chunks/cases)
- Création et gestion de personnages
- Gestion de l'inventaire et des compétences
- Combat joueur contre monstre et joueur contre joueur
- Sauvegarde automatique des données des joueurs
- Communication client-serveur par Sockets TCP/IP
- Interfaces API pour interaction avec un site web externe

---

## Technologies utilisées

- Java 11
- Spring Boot
- Maven 
- Sockets TCP/IP classiques
- Architecture MVC

