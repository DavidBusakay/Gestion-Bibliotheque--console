# Gestion Bibliothèque (Console)

## Description
Ce projet est une application Java console de gestion de bibliothèque, permettant d'ajouter des documents, des utilisateurs, et de gérer les emprunts via une interface utilisateur.

## Structure du projet
- `Main.java` : Point d'entrée de l'application.
- `Bibliotheque.java` : Logique métier.
- `documents/` : Objets liés aux documents.
- `utilisateurs/` : Objets liés aux utilisateurs.

## Compilation
Pour compiler tous les fichiers Java :

1. Ouvre un terminal dans le dossier racine du projet.
2. Lance la commande suivante :

```bash
javac -d bin -cp . Main.java Bibliotheque.java documents/*.java utilisateurs/*.java
```

- Cette commande compile tous les fichiers Java et place les fichiers `.class` dans le dossier `bin`.

## Exécution
Pour lancer l'application :

```
java -cp bin Main
```

## Auteur
[David Busakay](https://davidbusakay.vercel.app/)
