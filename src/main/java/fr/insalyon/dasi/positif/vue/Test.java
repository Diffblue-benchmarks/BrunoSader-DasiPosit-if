package fr.insalyon.dasi.positif.vue;

import fr.insalyon.dasi.positif.dao.JpaUtil;
import fr.insalyon.dasi.positif.metier.modele.Client;
import fr.insalyon.dasi.positif.metier.modele.Conversation;
import fr.insalyon.dasi.positif.metier.modele.Employe;
import fr.insalyon.dasi.positif.metier.modele.Medium;
import fr.insalyon.dasi.positif.metier.modele.Personne;
import fr.insalyon.dasi.positif.metier.service.Service;
import fr.insalyon.dasi.positif.util.AstroTest;
import fr.insalyon.dasi.positif.util.Saisie;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe principale pour les tests et la démonstration.
 *
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class Test {
     
    /**
     * Méthode main(): point d'entrée de ce programme de test.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Initialisation du JpaUtil
        JpaUtil.init();
        
        /*Service service = new Service();
        System.out.println("\n\n========== INITIALISATION ==========");
        System.out.println("INITIALISATION DE LA BASE DE DONNEES AVEC DES VOYANTS ET DES EMPLOYES");
        Service.initialisation();*/
        
        demonstrationIHM();
        //demonstrationTest();
        
        // Libération du JpaUtil
        JpaUtil.destroy();
    }
    
//     //  ======================
//       ||   DEMONSTRATION  ||
//     *  ======================
//     *
//     */
    
    /**
     * IHM Console pour la démonstration
     */
    public static void demonstrationIHM(){
        Service service = new Service();
        /*System.out.println("\n\n========== INITIALISATION ==========");
        System.out.println("INITIALISATION DE LA BASE DE DONNEES AVEC DES VOYANTS ET DES EMPLOYES");*/
       // Service.initialisation();
        
        boolean exit=false;
      //  Service.initialisation();
        // Boucle principale
        while(exit==false){
            System.out.println("\n===============================");
            System.out.println("           POSIT'IF");
            System.out.println("===============================");
            System.out.println("Vous souhaitez : ");
            System.out.println("(1) Vous connecter à votre compte ?");
            System.out.println("(2) Vous inscrire à POSIT'IF ?");
            System.out.println("(3) Quitter POSIT'IF ?");
            int choixConnInsc = Saisie.lireInteger("Entrez votre choix : ", Arrays.asList(1,2,3));

            boolean connecte = false;
            Personne p = null;
            
            while(connecte == false){
                switch(choixConnInsc){
                    case 1 :
                        System.out.println("\n===============================");
                        System.out.println("          CONNEXION");
                        System.out.println("===============================");
                        String email = Saisie.lireChaine("Email : ");
                        String mdp = Saisie.lireChaine("Mot de Passe : ");
                        p = service.seConnecter(email,mdp);
                        if(p == null){
                            break;
                        }
                        connecte = true;
                        break;

                    case 2 :
                        boolean inscr = false;
                        System.out.println("\n===============================");
                        System.out.println("         INSCRIPTION");
                        System.out.println("===============================");
                        String nom = Saisie.lireChaine("Nom : ");
                        String prenom = Saisie.lireChaine("Prénom : ");
                        String email1 = Saisie.lireChaine("Email : ");
                        String mdp1 = Saisie.lireChaine("MotDePasse : ");
                        String tel = Saisie.lireChaine("Nº de Téléphone : ");
                        String naiss = Saisie.lireChaine("Date de naissance (JJ/MM/AAAA) : ");
                        String adresse = Saisie.lireChaine("Adresse : ");

                        //Conversion date de naissance
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyy");
                        Date d = null;
                        try {
                            d = sdf.parse(naiss);
                            Client c = new Client(nom, prenom, mdp1, email1, tel, d, adresse);
                            p = c;
                            inscr = service.sInscrire(c);
                        } catch (ParseException ex) {
                            System.out.println("La date de naissance que vous avez saisie est incorrecte, veuillez-rééssayer\n");
                        }

                        if(inscr == false){
                            break;
                        }
                        connecte = true;
                        break;
                    
                    case 3:
                        exit=true;
                        connecte=true;
                        break;
                }
            }
            
            // UTILISATEUR CONNECTÉ
            
            if(exit==true){
                break;
            }
            
            else if(p.getClass() == Client.class){ //CLIENT
                int choixClient = 0;
                while(choixClient !=3){
                    System.out.println("\n===============================");
                    System.out.println("BONJOUR " + p.getPrenom().toUpperCase());
                    System.out.println("===============================");
                    System.out.println("Que souhaitez-vous faire ?");
                    System.out.println("    (1) Consulter la liste des Médiums");
                    System.out.println("    (2) Demander une voyance");
                    System.out.println("    (3) Déconnexion");
                    choixClient = Saisie.lireInteger("Entrez votre choix : ", Arrays.asList(1,2,3));

                    switch(choixClient){
                        case 1: //Liste des médiums
                            List<Medium>  mediums = service.obtenirTousMediums();
                            System.out.println("\n===============================");
                            System.out.println("       LISTE DES MÉDIUMS");
                            System.out.println("===============================");
                            for(int i=0; i<mediums.size(); i++){
                                System.out.println("    -" + mediums.get(i));
                            }
                            break;
                        case 2: //Demander voyance
                            List<Medium>  mediums1 = service.obtenirTousMediums();
                            System.out.println("\n===============================");
                            System.out.println("      DEMANDE DE VOYANCE");
                            System.out.println("===============================");
                            System.out.println("Veuillez choisir le médium pour la voyance :");
                            List<Integer> choix = new ArrayList();
                            for(int i=0; i<mediums1.size(); i++){
                                System.out.println("    ("+i+") " + mediums1.get(i));
                                choix.add(i);
                            }
                            int choixMedium = Saisie.lireInteger("Entrez votre choix : ", choix);
                            System.out.println("\n===============================");
                            System.out.println("  NOTIFICATION POUR L'EMPLOYÉ");
                            System.out.println("===============================");
                            Client c = (Client) p;
                            Conversation conv = service.demanderVoyance (c, mediums1.get(choixMedium) );
                            Employe employeConversationClaude = conv.getEmploye();

                             try {
                                Thread.sleep(6000);
                            } catch (InterruptedException ex) {
                                ex.hashCode();
                            }
                             
                            System.out.println("\n===============================");
                            System.out.println("PROFIL ET HISTORIQUE DU CLIENT");
                            System.out.println("  (CONSULTÉ PAR L'EMPLOYÉ)");
                            System.out.println("===============================");
                            System.out.println(c);
                            System.out.println("======= HISTORIQUE ========");
                            List<Conversation> historique = c.getConversations();
                            for(int i=0; i<historique.size(); i++){
                                System.out.println(historique.get(i));
                            }
                            
                            Saisie.lireChaine("Tapez quelque chose pour accepter la demande : ");
                            
                            System.out.println("\n=======================================");
                            System.out.println("ACCEPTATION DE LA DEMANDE PAR L'EMPLOYÉ ");
                            System.out.println("=======================================");
                            service.AccepterVoyance(conv);  

                            System.out.println("\n========================================");
                            System.out.println("PRÉDICTION POUR LE CLIENT (CÔTÉ EMPLOYÉ)");
                            System.out.println("========================================");
                            int noteAmour = Saisie.lireInteger("Note pour Amour (0 à 4) :", Arrays.asList(0,1,2,3,4));
                            int noteSante = Saisie.lireInteger("Note pour Santé (0 à 4) :", Arrays.asList(0,1,2,3,4));
                            int noteTravail = Saisie.lireInteger("Note pour Travail (0 à 4) :", Arrays.asList(0,1,2,3,4));

                            List<String> predictions = Service.ObtenirPredictions(conv.getClient(), noteAmour, noteSante, noteTravail); 
                            System.out.println("PRÉDICTIONS : ");
                            for(int i=0; i<predictions.size(); i++){
                                System.out.println("    " + predictions.get(i));
                            }

                            System.out.println("\n========================================");
                            System.out.println("            VOYANCE EN COURS");
                            System.out.println("========================================");
                            int finVoyance = 0;
                            while(finVoyance!=1){
                                finVoyance = Saisie.lireInteger("Entrez 1 pour terminer la voyance : ",Arrays.asList(1));
                            }
                            Service.TerminerVoyance(conv);

                            System.out.println("\n========================================");
                            System.out.println("      COMMENTAIRE DE FIN DE VOYANCE");
                            System.out.println("========================================");
                            String commentaire = Saisie.lireChaine("Entrez un commentaire sur la voyance réalisée : ");
                            Service.CommenterVoyance(conv,commentaire);
                            break;
                            
                        case 3: //Déconnexion
                            System.out.println("\n========================================");
                            System.out.println("AU REVOIR " + p.getPrenom().toUpperCase() + " :)");
                            System.out.println("========================================");
                            break;
                    }   
                }
            }
            else if(p.getClass() == Employe.class){ //EMPLOYÉ
                int choixEmploye = 0;
                while(choixEmploye != 4){
                    System.out.println("\n===============================");
                    System.out.println("BONJOUR " + p.getPrenom().toUpperCase());
                    System.out.println("       TABLEAU DE BORD");
                    System.out.println("===============================");
                    System.out.println("Que souhaitez vous faire ?");
                    System.out.println("    (1) Histogramme de Voyances par Médium");
                    System.out.println("    (2) Histogramme de Voyances par Employé");
                    System.out.println("    (3) Répartition des Voyances par Employé");
                    System.out.println("    (4) Déconnexion");
                    choixEmploye = Saisie.lireInteger("Entrez votre choix : ", Arrays.asList(1,2,3,4));
                    
                    switch (choixEmploye){
                        case 1: //HISTOGRAMME VOYANCES PAR MÉDIUM
                            HashMap<String,Integer> stats1 = service.ObtenirHistogrammeVoyancesParMedium();
                            System.out.println("\n========================================");
                            System.out.println("    HISTOGRAMME VOYANCES PAR MÉDIUM");
                            System.out.println("========================================");
                            Set cles = stats1.keySet();
                            Iterator it = cles.iterator();
                            while (it.hasNext()){
                               String cle = (String) it.next(); 
                               int valeur = stats1.get(cle); 
                               System.out.println("     - " + cle + " : " + valeur);
                            }
                            System.out.println("\n");
                            break;
                        
                        case 2: //HISTOGRAME DE VOYANCES PAR EMPLOYÉ
                            HashMap<String,Integer> stats2 = Service.ObtenirHistogrammeVoyancesParEmploye();
                            System.out.println("\n========================================");
                            System.out.println("   HISTOGRAME DE VOYANCES PAR EMPLOYÉ");
                            System.out.println("========================================");
                            Set cles2 = stats2.keySet();
                            Iterator it2 = cles2.iterator();
                            while (it2.hasNext()){
                               String cle = (String) it2.next(); 
                               int valeur = stats2.get(cle); 
                               System.out.println("     - " + cle + " : " + valeur);
                            }
                            System.out.println("\n");
                            break;
                        case 3: //RÉPARTITION DES VOYANCE PAR EMPLOYÉ
                            HashMap<String,Float> stats3 = Service.ObtenirCamembertVoyancesParEmploye();
                            System.out.println("\n========================================");
                            System.out.println("  RÉPARTITION DES VOYANCE PAR EMPLOYÉ");
                            System.out.println("========================================");
                            Set cles3 = stats3.keySet();
                            Iterator it3 = cles3.iterator();
                            while (it3.hasNext()){
                               String cle = (String) it3.next(); 
                               float valeur = stats3.get(cle); 
                               System.out.println("     - " + cle + " : " + valeur);
                            }
                            System.out.println("\n");
                            break;
                    }
                }
            }
        }
    }
    
    public static void demonstrationTest(){
        
        Service service = new Service();
        System.out.println("\n\n========== INITIALISATION ==========");
        System.out.println("INITIALISATION DE LA BASE DE DONNEES AVEC DES VOYANTS ET DES EMPLOYES");
        Service.initialisation();
               
        System.out.println("\n\n========== CONNEXIONS D'EMPLOYES ==========");
        System.out.println("CONNEXION DE LIAM :");
        System.out.println("RESULTAT = " + service.seConnecter("liam.bette@posit.if", "toto123"));
        
        System.out.println("\nCONNEXION DE ALEXIS :");
        System.out.println("RESULTAT = " + service.seConnecter("alexis.bosio@posit.if", "123456"));
              
               
        System.out.println("\nCONNEXION DE ZOUHAIR (mauvais identifiant) :");
        System.out.println("RESULTAT = " + service.seConnecter("souhait@csdkn.lef", "GXzr"));
        
        System.out.println("\nCONNEXION DE ZOUHAIR (mauvais mot de passe) :");
        System.out.println("RESULTAT = " + service.seConnecter("alexis.bosio@posit.if", "mauvais mot de passe"));
        
        System.out.println("\n\n========== INSCRIPTION DE CLIENTS ==========");
        Client gerard = new Client("Mentor", "Gerard", "password1", "email1@gmail.com", "0624578675", new Date(97,05,28), "Chine");
        System.out.println("INSCRIPTION DE GERARD :");
        System.out.println("RESULTAT = " + service.sInscrire(gerard));
        
        Client claude = new Client("Retu", "Claude", "LTce", "claude.lost@google.com", "0624578675", new Date(85,05,28), "Chine");
        System.out.println("INSCRIPTION DE CLAUDE :");
        System.out.println("RESULTAT = " + service.sInscrire(claude));
            
        /**/
        
        System.out.println("\n\n========== CONNEXIONS DE CLIENTS ==========");
        System.out.println("CONNEXION DE CLAUDE :");
        Personne claudeConnecte = service.seConnecter("claude.lost@google.com", "LTce");
        System.out.println("RESULTAT = " + claudeConnecte);
        
        System.out.println("\nCONNEXION DE GERARD :");
        Personne jeanConnecte = service.seConnecter("email1@gmail.com", "password1");
        System.out.println("RESULTAT = " + jeanConnecte);
        
        System.out.println("\nCONNEXION DE GERARD (mauvaise adresse) :");
        System.out.println("RESULTAT = " + service.seConnecter("mauvaise adresse", "password1"));
        
        System.out.println("\nCONNEXION DE GERARD (mauvais mot de passe) :");
        System.out.println("RESULTAT = " + service.seConnecter("email1@gmail.com", "mauvais mot de passe"));
        
        
        System.out.println("\n\n========== DEMANDES D'INFORMATIONS SUR LES MEDIUMS ==========");       
        System.out.println("\nDEMANDE TOUS LES MEDIUMS :");
        List<Medium>  mediums = service.obtenirTousMediums();
        System.out.println("RESULTAT = ");
        for(int i=0; i<mediums.size(); i++){
            System.out.println("    -" + mediums.get(i));
        }
        
        System.out.println("\n\n========== DEMANDES DE VOYANCE ==========");
        System.out.println("CLAUDE DEMANDE MEDIUM :");
        Conversation conversationClaude = service.demanderVoyance (claude, mediums.get(0) );
        Employe employeConversationClaude = conversationClaude.getEmploye();
        System.out.println("RESULTAT = " + conversationClaude);
        
        System.out.println("\n\n========== ACCEPTATION DE VOYANCE ==========");
        System.out.println("ACCEPTATION DE LA VOYANCE DE CLAUDE :");
        service.AccepterVoyance(conversationClaude);  
        System.out.println("RESULTAT = " + conversationClaude);
        
        System.out.println("\n\n========== DEMANDE DE PREDICTIONS POUR UN CLIENT ==========");
        System.out.println("DEMANDE DE PREDICTIONS POUR CLAUDE :");
        List<String> predictions = Service.ObtenirPredictions(conversationClaude.getClient(), 2, 0, 4); 
        System.out.println("RESULTAT = ");
        for(int i=0; i<predictions.size(); i++){
            System.out.println("    " + predictions.get(i));
        }    
        
        System.out.println("\n\n========== FIN DE VOYANCE ==========");
        System.out.println("Attente de 2 secondes...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.hashCode();
        }
        System.out.println("FIN DE LA VOYANCE DE CLAUDE (" + new Date() +") :");
        Service.TerminerVoyance(conversationClaude);
        System.out.println("RESULTAT = " + conversationClaude);
            
        
        System.out.println("\n\n========== COMMENTER UNE VOYANCE ==========");
        System.out.println("COMMENTER LA VOYANCE De CLAUDE :");
        Service.CommenterVoyance(conversationClaude,"Claude est complexé par la petite taille de son phalus.");
        System.out.println("RESULTAT = " + conversationClaude);
        
        
        System.out.println("\n\n========== DEMANDER LES STATISTIQUES ==========");
        System.out.println("\nDEMANDER L'HISTOGRAMME DE VOYANCES PAR MEDIUM :");
        HashMap<String,Integer> stats1 = service.ObtenirHistogrammeVoyancesParMedium();
        System.out.println("RESULTAT = ");
        Set cles = stats1.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()){
           String cle = (String) it.next(); 
           int valeur = stats1.get(cle); 
           System.out.println("     - " + cle + " : " + valeur);
        }
        
        System.out.println("\nDEMANDER L'HISTOGRAMME DE VOYANCES PAR EMPLOYE :");
        HashMap<String,Integer> stats2 = Service.ObtenirHistogrammeVoyancesParEmploye();
        System.out.println("RESULTAT = ");
        Set cles2 = stats2.keySet();
        Iterator it2 = cles2.iterator();
        while (it2.hasNext()){
           String cle = (String) it2.next(); 
           int valeur = stats2.get(cle); 
           System.out.println("     - " + cle + " : " + valeur);
        }
        
        System.out.println("\nDEMANDER LE CAMEMBERT DE VOYANCES PAR EMPLOYE :");
        HashMap<String,Float> stats3 = Service.ObtenirCamembertVoyancesParEmploye();
        System.out.println("RESULTAT = ");
        Set cles3 = stats3.keySet();
        Iterator it3 = cles3.iterator();
        while (it3.hasNext()){
           String cle = (String) it3.next(); 
           float valeur = stats3.get(cle); 
           System.out.println("     - " + cle + " : " + valeur);
        }
        
        System.out.println("\n\n");
    }
    
// ============ TESTS ======================
    /**
     * Fonction de Test d'inscription
     */
    public static void testerInscription() {
        Service service = new Service();

        Calendar calendar1 = new GregorianCalendar(1996, 1, 30);
        Date dateNais1 = calendar1.getTime();
        Client e1 = new Client("Mentor", "Gerard", "password1", "email1@gmail.com", "0624578675", dateNais1, "Chine");
        Client e2 = new Client("Chirac", "Jacques", "password1", "elysee@gmail.com", "0610203040", dateNais1, "France");

        service.sInscrire(e1);
        service.sInscrire(e2);
    }

    /**
     * Test de la connexion d'un client à POSIT'IF
     */
    public static void testerConnexionClient() {
        Service service = new Service();
        
        // Client inexistant
        Personne e = service.seConnecter("blabla", "blabla");

        // Client existant mais mauvais mot de passe
        Personne e1 = service.seConnecter("email1@gmail.com", "blabla");

        // Client existant et bon mot de passe
        Personne e2 = service.seConnecter("email1@gmail.com", "password1");

        // Employé existant
        Personne e3 = service.seConnecter("alexis.bosio@posit.if", "123456");
    }

    /**
     * Méthode test pour afficher tous les médiums de la base de données.
     */
    public static void testObtenirTousLesMediums() {
        Service service = new Service();
        List<Medium> listeMed = service.obtenirTousMediums();
        System.out.println("Voici tous les mediums :");
        for (int i = 0; i < listeMed.size(); i++) {
            System.out.println("-" + listeMed.get(i).toString());
        }
    }

    /**
     * Méthode de Test de l'API Astronet.
     */
    public static void TestAstroTest() {
        Service service = new Service();
        System.out.println("\n========== DEBUT TEST ASTROTEST ==========");
        AstroTest astro = new AstroTest();
        //Client c = new Client("Mentor","Gerard","password1","email1@gmail.com","0624578675",new Date(97,05,28),"Chine");
        Client c = (Client) service.seConnecter("email1@gmail.com", "password1");
        System.out.println("Client initial = " + c);

        List profil = null;
        try {
            profil = astro.getProfil(c.getPrenom(), (java.sql.Date) c.getDateNaissance());
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Profil reçu = " + profil);

        if (profil != null) {
            c.setSigneZodiaque((String) profil.get(0));
            c.setSigneChinois((String) profil.get(1));
            c.setCouleur((String) profil.get(2));
            c.setAnimal((String) profil.get(3));
            System.out.println("Client perfectionné = " + c);
        }

        List predictions = null;
        try {
            predictions = astro.getPredictions(c.getCouleur(), c.getAnimal(), 4, 0, 4);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Prédictions reçues = " + predictions);

        System.out.println("========== FIN TEST ASTROTEST ==========");
    }

    /**
     * Méthode de Test pour les prédictions
     */
    public static void TestServicePrediction() {
        Calendar calendar1 = new GregorianCalendar(1996, 1, 30);
        Date dateNais1 = calendar1.getTime();
        System.out.println("\n========== DEBUT TEST SERVICE PREDICTIONS ==========");
        Client c = new Client("Mentor", "Gerard", "password1", "email1@gmail.com", "0624578675", dateNais1, "Chine");

        List predictions = Service.ObtenirPredictions(c, 4, 0, 4);
        System.out.println("Prédictions reçues = " + predictions);
        System.out.println("========== FIN TEST SERVICE PREDICTIONS ==========");
    }

    /**
     * Méthode de test pour les statistiques.
     */
    public static void TestServiceGraphiques() {
        Calendar calendar1 = new GregorianCalendar(1996, 1, 30);
        Date dateNais1 = calendar1.getTime();
        System.out.println("\n========== DEBUT TEST SERVICE GRAPHIQUES ==========");
        Client c = new Client("Mentor", "Gerard", "password1", "email1@gmail.com", "0624578675", dateNais1, "Chine");
        Service service = new Service();
        service.sInscrire(c);

        List<Medium> mediums = service.obtenirTousMediums();
        
        //Conv 1
        Conversation conversation = service.demanderVoyance(c, mediums.get(2));
        Service.TerminerVoyance(conversation);
        
        //Conv 2
        conversation = service.demanderVoyance(c, mediums.get(2));
        Service.TerminerVoyance(conversation);
        
        //Conv 3
        conversation = service.demanderVoyance(c, mediums.get(1));
        Service.TerminerVoyance(conversation);
        
        //Conv 4
        conversation = service.demanderVoyance(c, mediums.get(2));
        Service.TerminerVoyance(conversation);
        
        //Conv 5
        conversation = service.demanderVoyance(c, mediums.get(2));
        Service.TerminerVoyance(conversation);
        
        //Conv 6
        conversation = service.demanderVoyance(c, mediums.get(2));
        Service.TerminerVoyance(conversation);
        
        //Conv 7
        conversation = service.demanderVoyance(c, mediums.get(2));
        Service.TerminerVoyance(conversation);
        
        //Conv 8
        conversation = service.demanderVoyance(c, mediums.get(3));
        Service.TerminerVoyance(conversation);
        
        //Conv 9
        conversation = service.demanderVoyance(c, mediums.get(3));
        Service.TerminerVoyance(conversation);
        
        //Conv 10
        conversation = service.demanderVoyance(c, mediums.get(4));
        Service.TerminerVoyance(conversation);
        
        //Conv 11
        conversation = service.demanderVoyance(c, mediums.get(4));
        Service.TerminerVoyance(conversation);
        
        //Conv 12
        conversation = service.demanderVoyance(c, mediums.get(4));
        Service.TerminerVoyance(conversation);
        
        System.out.println("\nhistogramme voyances/mediums = " + service.ObtenirHistogrammeVoyancesParMedium());
        System.out.println("\nhistogramme voyances/employe = " + Service.ObtenirHistogrammeVoyancesParEmploye());
        System.out.println("\ncamembert voyances/employe = " + Service.ObtenirCamembertVoyancesParEmploye());
        System.out.println("========== FIN TEST SERVICE GRAPHIQUES ==========");
    }
}
