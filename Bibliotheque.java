import java.util.ArrayList;

import documents.Document;
import documents.Emprunt;
import utilisateurs.Utilisateur;

public class Bibliotheque {
  
  public ArrayList<Document> listeDocuments;
  public ArrayList<Utilisateur> listeUtilisateurs;
  public ArrayList<Emprunt> listeEmprunts;

  public Bibliotheque() {
    this.listeDocuments = new ArrayList<>();
    this.listeUtilisateurs = new ArrayList<>();
    this.listeEmprunts = new ArrayList<>();
  }

  public void ajouterDocument(Document document) {
    listeDocuments.add(document);
    System.out.println("Document ajouté : " + document.getTitre());
  }

  public void ajouterUtilisateur(Utilisateur utilisateur) {
    listeUtilisateurs.add(utilisateur);
    System.out.println("Utilisateur ajouté : " + utilisateur.getNom() + " (ID : " + utilisateur.getId() + ")");
  }

  public void effectuerEmprunt(Utilisateur utilisateur, Document document) {
    try {
      if (!document.estDisponible()) {
        throw new Exception("Le document '" + document.getTitre() + "' n'est pas disponible.");
      }

      // Vérifier la limite d'emprunt de l'utilisateur
      if (!utilisateur.peutEmprunter()) {
        throw new Exception(utilisateur.getNom() + " a atteint sa limite d'emprunt (" + utilisateur.getLimiteEmprunt() + " documents).");
      }

      // Créer l'emprunt avec la durée maximale de l'utilisateur
      int dureeMaximale = utilisateur.getDureeMaximale();
      Emprunt emprunt = new Emprunt(document, utilisateur, dureeMaximale);
      
      // Marquer le document comme emprunté
      document.emprunter();
      
      // Ajouter l'emprunt à la liste globale et à l'utilisateur
      listeEmprunts.add(emprunt);
      utilisateur.ajouterEmprunt(emprunt);
      
      System.out.println("\n=== EMPRUNT EFFECTUÉ AVEC SUCCÈS ===");
      System.out.println("Document: " + document.getTitre());
      System.out.println("Emprunteur: " + utilisateur.getNom() + " (ID: " + utilisateur.getId() + ")");
      System.out.println("Date d'emprunt: " + emprunt.getDateEmprunt());
      System.out.println("Date d'échéance: " + emprunt.getDateEcheance());
      System.out.println("Durée: " + dureeMaximale + " jours");
      System.out.println("Emprunts restants pour cet utilisateur: " + utilisateur.getEmpruntsRestants());
      System.out.println("=====================================\n");
    } catch (Exception e) {
      System.out.println("\n*** ERREUR: " + e.getMessage() + " ***\n");
    }
  }

  public void retournerDocument(Utilisateur utilisateur, Document document) {
    try {
      // Rechercher l'emprunt actif correspondant
      Emprunt empruntTrouve = null;
      for (Emprunt e : listeEmprunts) {
        if (e.getDocument().equals(document) && 
            e.getUtilisateur().equals(utilisateur) && 
            !e.isRetourne()) {
          empruntTrouve = e;
          break;
        }
      }

      if (empruntTrouve == null) {
        throw new Exception("Aucun emprunt actif trouvé pour ce document par cet utilisateur.");
      }

      // Calculer les pénalités si retard
      long joursRetard = empruntTrouve.getJoursRetard();
      if (joursRetard > 0) {
        double penalite = document.calculerPenalite((int)joursRetard);
        System.out.println("\n*** ATTENTION: Document en retard de " + joursRetard + " jours! ***");
        System.out.println("Pénalité calculée: " + penalite + " euros");
      }

      // Marquer l'emprunt comme retourné
      empruntTrouve.setRetorne(true);
      document.retourner();
      utilisateur.supprimerEmprunt(empruntTrouve);

      System.out.println("\n=== DOCUMENT RETOURNÉ AVEC SUCCÈS ===");
      System.out.println("Document: " + document.getTitre());
      System.out.println("Emprunteur: " + utilisateur.getNom());
      System.out.println("=======================================\n");
    } catch (Exception e) {
      System.out.println("\n*** ERREUR: " + e.getMessage() + " ***\n");
    }
  }

  public void rechercherDocument(String titreRecherche) {
    System.out.println("\n=== RÉSULTATS POUR '" + titreRecherche + "' ===");

    boolean trouve = false;
    for (Document d : listeDocuments) {
      if (d.getTitre().toLowerCase().contains(titreRecherche.toLowerCase())) {
        System.out.println(d.toStringDetails());
        trouve = true;
      }
    }

    if (!trouve) {
      System.out.println("Aucun document trouvé.");
    }
    System.out.println("=====================================\n");
  }

  public void afficherTousDocuments() {
    System.out.println("\n=== LISTE DE TOUS LES DOCUMENTS ===");
    if (listeDocuments.isEmpty()) {
      System.out.println("Aucun document dans la bibliothèque.");
    } else {
      for (Document d : listeDocuments) {
        System.out.println(d.toStringDetails());
      }
    }
    System.out.println("====================================\n");
  }

  public void afficherTousUtilisateurs() {
    System.out.println("\n=== LISTE DE TOUS LES UTILISATEURS ===");
    if (listeUtilisateurs.isEmpty()) {
      System.out.println("Aucun utilisateur enregistré.");
    } else {
      for (Utilisateur u : listeUtilisateurs) {
        System.out.println("[" + u.getId() + "] " + u.getNom() + " - Type: " + u.getClass().getSimpleName() + 
                         " - Emunts actifs: " + u.getNombreEmpruntsActifs() + "/" + u.getLimiteEmprunt());
      }
    }
    System.out.println("======================================\n");
  }

  public void afficherEmpruntsActifs() {
    System.out.println("\n=== EMPRUNTS ACTIFS ===");
    boolean trouve = false;
    for (Emprunt e : listeEmprunts) {
      if (!e.isRetourne()) {
        System.out.println("Document: " + e.getDocument().getTitre());
        System.out.println("Emprunteur: " + e.getUtilisateur().getNom() + " (ID: " + e.getUtilisateur().getId() + ")");
        System.out.println("Date d'emprunt: " + e.getDateEmprunt());
        System.out.println("Date d'échéance: " + e.getDateEcheance());
        
        if (e.estEnRetard()) {
          System.out.println("*** EN RETARD: " + e.getJoursRetard() + " jours ***");
        } else {
          System.out.println("Jours restants: " + e.getJoursRestants());
        }
        System.out.println("-----------------------------");
        trouve = true;
      }
    }
    if (!trouve) {
      System.out.println("Aucun emprunt actif.");
    }
    System.out.println("======================\n");
  }

  public void afficherEmpruntsUtilisateur(Utilisateur utilisateur) {
    System.out.println("\n=== EMPRUNTS DE " + utilisateur.getNom().toUpperCase() + " ===");
    java.util.List<Emprunt> emprunts = utilisateur.getEmprunts();
    if (emprunts.isEmpty()) {
      System.out.println("Aucun emprunt.");
    } else {
      for (Emprunt e : emprunts) {
        String statut = e.isRetourne() ? "RETOURNÉ" : "ACTIF";
        if (e.estEnRetard()) {
          statut += " - EN RETARD (" + e.getJoursRetard() + " jours)";
        }
        System.out.println("- " + e.getDocument().getTitre() + " [" + statut + "]");
        System.out.println("  Échéance: " + e.getDateEcheance());
      }
    }
    System.out.println("==========================\n");
  }

  public void afficherStatistiques() {
    System.out.println("\n=== STATISTIQUES DE LA BIBLIOTHÈQUE ===");
    System.out.println("Total des documents: " + listeDocuments.size());
    System.out.println("Total des utilisateurs: " + listeUtilisateurs.size());
    
    int documentsDisponibles = 0;
    int documentsEmpruntes = 0;
    for (Document d : listeDocuments) {
      if (d.estDisponible()) {
        documentsDisponibles++;
      } else {
        documentsEmpruntes++;
      }
    }
    System.out.println("Documents disponibles: " + documentsDisponibles);
    System.out.println("Documents empruntés: " + documentsEmpruntes);
    
    int empruntsActifs = 0;
    int empruntsEnRetard = 0;
    for (Emprunt e : listeEmprunts) {
      if (!e.isRetourne()) {
        empruntsActifs++;
        if (e.estEnRetard()) {
          empruntsEnRetard++;
        }
      }
    }
    System.out.println("Emprunts actifs: " + empruntsActifs);
    System.out.println("Emprunts en retard: " + empruntsEnRetard);
    System.out.println("======================================\n");
  }

  public Document rechercherDocumentParTitre(String titre) {
    for (Document d : listeDocuments) {
      if (d.getTitre().toLowerCase().equalsIgnoreCase(titre.toLowerCase())) {
        return d;
      }
    }
    return null;
  }

  public Utilisateur rechercherUtilisateurParId(int id) {
    for (Utilisateur u : listeUtilisateurs) {
      if (u.getId() == id) {
        return u;
      }
    }
    return null;
  }
}
