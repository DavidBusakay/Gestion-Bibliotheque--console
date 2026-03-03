import java.util.Scanner;

import documents.Document;
import documents.Livre;
import documents.Revue;
import utilisateurs.Etudiant;
import utilisateurs.Professeur;
import utilisateurs.Utilisateur;

public class Main {
  public static void main(String[] args) {
    Bibliotheque bibliotheque = new Bibliotheque();
    Scanner scanner = new Scanner(System.in);

    // On ajoute des données de test pour ne pas avoir une liste vide
    bibliotheque.ajouterDocument(new Livre("Apprendre Java de A a Z", 2026, "John Doe"));
    bibliotheque.ajouterDocument(new Revue("Science & Vie", 2023, 1250));
    bibliotheque.ajouterDocument(new Livre("Programmation Orientée Objet", 2022, "Jean-Michel"));
    bibliotheque.ajouterDocument(new Revue("L'Express", 2024, 1300));
    bibliotheque.ajouterUtilisateur(new Etudiant("Luc"));
    bibliotheque.ajouterUtilisateur(new Etudiant("Matthieu"));
    bibliotheque.ajouterUtilisateur(new Professeur("Professeur Efrem"));

    boolean quitter = false;

    System.out.println("\n=====================================");
    System.out.println("BIENVENUE A LA GESTION DE LA BIBLIOTHÈQUE");
    System.out.println("=====================================\n");

    while (!quitter) {
      System.out.println("\n========== MENU PRINCIPAL ==========");
      System.out.println("1. Afficher tous les documents");
      System.out.println("2. Rechercher un document");
      System.out.println("3. Afficher tous les utilisateurs");
      System.out.println("4. Effectuer un emprunt");
      System.out.println("5. Retourner un document");
      System.out.println("6. Afficher les emprunts actifs");
      System.out.println("7. Afficher mes emprunts");
      System.out.println("8. Ajouter un nouveau document");
      System.out.println("9. Ajouter un nouvel utilisateur");
      System.out.println("10. Statistiques");
      System.out.println("0. Quitter");
      System.out.println("===================================");
      System.out.print("Votre choix : ");

      int choix = scanner.nextInt();
      scanner.nextLine();

      switch (choix) {
        case 1:
          bibliotheque.afficherTousDocuments();
          break;
          
        case 2:
          System.out.print("Entrez le titre du document à rechercher : ");
          String titreRecherche = scanner.nextLine();
          bibliotheque.rechercherDocument(titreRecherche);
          break;
          
        case 3:
          bibliotheque.afficherTousUtilisateurs();
          break;
          
        case 4:
          System.out.println("\n=== EFFECTUER UN EMPRUNT ===");
          System.out.println("Entrez l'ID de l'utilisateur : ");
          int idEmprunt = scanner.nextInt();
          scanner.nextLine();
          
          Utilisateur utilisateurEmprunt = bibliotheque.rechercherUtilisateurParId(idEmprunt);
          if (utilisateurEmprunt == null) {
            System.out.println("\n*** ERREUR: Utilisateur introuvable ***\n");
            break;
          }
          
          System.out.println("Entrez le titre du document à emprunter : ");
          String titreEmprunt = scanner.nextLine();
          
          Document documentEmprunt = bibliotheque.rechercherDocumentParTitre(titreEmprunt);
          if (documentEmprunt == null) {
            System.out.println("\n*** ERREUR: Document introuvable ***\n");
            break;
          }
          
          bibliotheque.effectuerEmprunt(utilisateurEmprunt, documentEmprunt);
          break;
          
        case 5:
          System.out.println("\n=== RETOURNER UN DOCUMENT ===");
          System.out.println("Entrez l'ID de l'utilisateur : ");
          int idRetour = scanner.nextInt();
          scanner.nextLine();
          
          Utilisateur utilisateurRetour = bibliotheque.rechercherUtilisateurParId(idRetour);
          if (utilisateurRetour == null) {
            System.out.println("\n*** ERREUR: Utilisateur introuvable ***\n");
            break;
          }
          
          // Afficher les emprunts de l'utilisateur
          bibliotheque.afficherEmpruntsUtilisateur(utilisateurRetour);
          
          System.out.println("Entrez le titre du document à retourner : ");
          String titreRetour = scanner.nextLine();
          
          Document documentRetour = bibliotheque.rechercherDocumentParTitre(titreRetour);
          if (documentRetour == null) {
            System.out.println("\n*** ERREUR: Document introuvable ***\n");
            break;
          }
          
          bibliotheque.retournerDocument(utilisateurRetour, documentRetour);
          break;
          
        case 6:
          bibliotheque.afficherEmpruntsActifs();
          break;
          
        case 7:
          System.out.println("\n=== MES EMPRUNTS ===");
          System.out.println("Entrez votre ID : ");
          int idMesEmprunts = scanner.nextInt();
          scanner.nextLine();
          
          Utilisateur utilisateurMesEmprunts = bibliotheque.rechercherUtilisateurParId(idMesEmprunts);
          if (utilisateurMesEmprunts == null) {
            System.out.println("\n*** ERREUR: Utilisateur introuvable ***\n");
          } else {
            bibliotheque.afficherEmpruntsUtilisateur(utilisateurMesEmprunts);
          }
          break;
          
        case 8:
          System.out.println("\n=== AJOUTER UN NOUVEAU DOCUMENT ===");
          System.out.println("Type de document (1: Livre, 2: Revue): ");
          int typeDoc = scanner.nextInt();
          scanner.nextLine();
          
          System.out.print("Titre: ");
          String nouveauTitre = scanner.nextLine();
          System.out.print("Année de publication: ");
          int nouvelleAnnee = scanner.nextInt();
          scanner.nextLine();
          
          if (typeDoc == 1) {
            System.out.print("Auteur: ");
            String auteur = scanner.nextLine();
            bibliotheque.ajouterDocument(new Livre(nouveauTitre, nouvelleAnnee, auteur));
          } else if (typeDoc == 2) {
            System.out.print("Numéro d'édition: ");
            int numero = scanner.nextInt();
            scanner.nextLine();
            bibliotheque.ajouterDocument(new Revue(nouveauTitre, nouvelleAnnee, numero));
          } else {
            System.out.println("Type de document invalide.");
          }
          break;
          
        case 9:
          System.out.println("\n=== AJOUTER UN NOUVEL UTILISATEUR ===");
          System.out.println("Type d'utilisateur (1: Étudiant, 2: Professeur): ");
          int typeUser = scanner.nextInt();
          scanner.nextLine();
          
          System.out.print("Nom: ");
          String nomNouvelUtilisateur = scanner.nextLine();
          
          if (typeUser == 1) {
            bibliotheque.ajouterUtilisateur(new Etudiant(nomNouvelUtilisateur));
          } else if (typeUser == 2) {
            bibliotheque.ajouterUtilisateur(new Professeur(nomNouvelUtilisateur));
          } else {
            System.out.println("Type d'utilisateur invalide.");
          }
          break;
          
        case 10:
          bibliotheque.afficherStatistiques();
          break;
          
        case 0:
          quitter = true;
          System.out.println("\n=====================================");
          System.out.println("Au revoir et à bientôt!");
          System.out.println("=====================================\n");
          break;
          
        default:
          System.out.println("\n*** Choix invalide, réessayez. ***\n");
      }
    }
    scanner.close();
  }
}
