package fr.insalyon.dasi.positif.metier.service;

import fr.insalyon.dasi.positif.dao.AstrologueDAO;
import fr.insalyon.dasi.positif.dao.ClientDAO;
import fr.insalyon.dasi.positif.dao.ConversationDAO;
import fr.insalyon.dasi.positif.dao.EmployeDAO;
import fr.insalyon.dasi.positif.dao.JpaUtil;
import fr.insalyon.dasi.positif.dao.MediumDAO;
import fr.insalyon.dasi.positif.dao.PersonneDAO;
import fr.insalyon.dasi.positif.dao.TarologueDAO;
import fr.insalyon.dasi.positif.dao.VoyantDAO;
import fr.insalyon.dasi.positif.metier.modele.Astrologue;
import fr.insalyon.dasi.positif.metier.modele.Client;
import fr.insalyon.dasi.positif.metier.modele.Conversation;
import fr.insalyon.dasi.positif.metier.modele.Employe;
import fr.insalyon.dasi.positif.metier.modele.Medium;
import fr.insalyon.dasi.positif.metier.modele.Personne;
import fr.insalyon.dasi.positif.metier.modele.Tarologue;
import fr.insalyon.dasi.positif.metier.modele.Voyant;
import fr.insalyon.dasi.positif.util.AstroTest;
import fr.insalyon.dasi.positif.util.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.RollbackException;

/**
 * Classe qui regroupe tous les services utiles à l'application.
 * 
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class Service {
    
    public Service() {
    }

    /**
     * Permet l'inscription d'un nouveau Client (Ajout dans la base)
     *
     * @param client Le nouveau client à ajouter
     * @return Vrai si l'inscription à été réalisée
     */
    public boolean sInscrire(Client client) {
        JpaUtil.creerEntityManager();
        try {
            // Transaction Persistence
            JpaUtil.ouvrirTransaction();
            ClientDAO.creer(client);
            JpaUtil.validerTransaction();

            envoiMailInscription(client, 0);
            JpaUtil.fermerEntityManager();
            return true;

        } catch (RollbackException e) {
            envoiMailInscription(client, 1);
            JpaUtil.fermerEntityManager();
            return false;
        }
    }

    /**
     * Recherche la personne dans la base de donnée à l'aide de son adresse
     * email et de son mot de passe.
     *
     * @param email L'adresse email de la personne
     * @param motDePasse Le mot de passe de la personne
     * @return La Personne à condition qu'elle existe dans la base.
     */
    public Personne seConnecter(String email, String motDePasse) {
        JpaUtil.creerEntityManager();
        Personne personne = PersonneDAO.obtenir(email);
        JpaUtil.fermerEntityManager();
        if (personne != null && personne.getMotDePasse().equals(motDePasse)) {
            System.out.println("Vous êtes connecté !");
            return personne;
        } else {
            System.out.println("Votre email ou votre mot de passe est incorrect !");
            return null;
        }
    }

    /**
     * Récupère la liste de tous les Mediums de la base de donnée.
     *
     * @return La liste des Mediums
     */
    public List<Medium> obtenirTousMediums() {
        JpaUtil.creerEntityManager();
        List<Medium> listesMediums = MediumDAO.obtenirTous();
        JpaUtil.fermerEntityManager();
        return listesMediums;
    }

    /**
     * Créé une conversation qui met en relation un Client et un Medium
     * interprété par un employé.
     * Envoie une notification à l'employé (console)
     *
     * @param client Le client qui demande la conversation
     * @param medium Le medium avec qui il veut réaliser la conversation
     * @return La conversation, null si le médium n'est pas disponible
     */
    public Conversation demanderVoyance(Client client, Medium medium) {

        JpaUtil.creerEntityManager();
        Employe employe = (Employe) EmployeDAO.obtenirEmployePourVoyance(medium);

        if (employe == null) {
            return null;
        }
        employe.setDisponible(false);
        Conversation conversation = new Conversation(employe, medium, client);
        employe.addConversation(conversation);
        medium.addConversation(conversation);
        client.addConversation(conversation);
        
        // Transaction
        JpaUtil.ouvrirTransaction();
        ConversationDAO.creer(conversation);
        MediumDAO.modifier(medium);
        ClientDAO.modifier(client);
        EmployeDAO.modifier(employe);    
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
        // Envoi notification de demande de voyance à l'employé
        envoiNotificationEmploye(conversation);
        
        return conversation;
    }

    /** 
     * Accepte la voyance et envoi une notification au client
     * @param conversation la conversation entre le client et le medium
     */
    public void AccepterVoyance(Conversation conversation) {
        conversation.setDebut(new Date());
        
        //Transaction
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        ConversationDAO.modifier(conversation);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
        envoiNotificationClient(conversation);
    }

    /**
     * Met fin à la voyance (date de fin) et rend disponible l'employé
     * @param conversation la conversation entre le client et le medium.
     */
    public static void TerminerVoyance(Conversation conversation) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        // MAJ de la date de fin de la conversation
        conversation.setFin();
        
        // MAJ de la disponibilité de l'employé
        Employe employe = conversation.getEmploye();
        employe.setDisponible(true);
        EmployeDAO.modifier(employe);
        ConversationDAO.modifier(conversation);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    /**
     * Ajoute un commentaire à la voyance
     * @param conversation La conversation à laquelle ajouter le commentaire
     * @param commentaire Le texte du commentaire
     */
    public static void CommenterVoyance(Conversation conversation, String commentaire) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        // MAJ du commentaire de la conversation
        conversation.setCommentaire(commentaire);
        ConversationDAO.modifier(conversation);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    /**
     * Cette méthode permet d'obtenir des predictions astrologiques personnalisées.
     * @param client Le client pour lequel on souhaite avoir des prédictions.
     * @param amour Une note en amour de 1 PAS BON à 4 BON
     * @param sante Une note en sante de 1 PAS BON à 4 BON
     * @param travail Une note de 1 PAS BON à 4 BON
     * @return La liste des predictions dans l'ordre suivant (amour, sante, travail) et null si une erreur s'est produite.
     */
    public static List<String> ObtenirPredictions(Client client, int amour, int sante, int travail)
    {
        AstroTest astro = new AstroTest();
        try {
            return astro.getPredictions(client.getCouleur(), client.getAnimal(), amour, sante, travail);
        } catch (IOException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Cette méthode permet d'obtenir les valeurs de l'histogramme du nombre de voyances par médium.
     * @return Un dictionnaire des couples (clé = nom du medium, valeur = nombre de voyance).
     */
    public HashMap<String,Integer> ObtenirHistogrammeVoyancesParMedium()
    {
        List<Medium> mediums = obtenirTousMediums();
        
        HashMap<String,Integer> histogramme = new HashMap<>();
        mediums.forEach((m) -> {
            String nom = m.getNom();
            Integer nbVoyances = m.getConversations().size();
            histogramme.put(nom, nbVoyances);
        });
        return histogramme;
    }
    
    
    /**
     * Cette méthode permet d'obtenir les valeurs de l'histogramme du nombre de voyances par employé.
     * @return Un dictionnaire des couples (clé = prénom et nom de l'employe, valeur = nombre de voyance)
     */
    public static HashMap<String,Integer> ObtenirHistogrammeVoyancesParEmploye()
    {
        JpaUtil.creerEntityManager();
        List<Employe> employes = EmployeDAO.obtenirTous();
        JpaUtil.fermerEntityManager();
        
        HashMap<String,Integer> histogramme = new HashMap<>();
        employes.forEach((e) -> {
            String nomPrenom = e.getPrenom() + ' ' + e.getNom();
            Integer nbVoyances = e.getConversations().size();
            histogramme.put(nomPrenom, nbVoyances);
        });
        return histogramme;
    }
    
    /**
     * Cette méthode permet d'obtenir les valeurs du camembert du pourcentage de voyances par employé.
     * @return Un dictionnaire des couples (clé = prénom et nom de l'employe, valeur = pourcentage de voyance)
     */
    public static HashMap<String,Float> ObtenirCamembertVoyancesParEmploye()
    {
        JpaUtil.creerEntityManager();
        List<Employe> employes = EmployeDAO.obtenirTous();
        JpaUtil.fermerEntityManager();
        
        int totalVoyances = 0;
        for(Employe e : employes){
            totalVoyances += e.getConversations().size();
        }
        
        HashMap<String,Float> camembert = new HashMap<>();
        for(Employe e : employes){
            String nomPrenom = e.getPrenom() + ' ' + e.getNom();
            Integer nbVoyances = e.getConversations().size();
            camembert.put(nomPrenom, (float) nbVoyances/totalVoyances );
        }
        return camembert;
    }

    /**
     * Simule l'envoi d'un mail de confirmation ou d'erreur dans la console
     * suite à l'inscription d'un nouveau client.
     *
     * @param c le nouveau client
     * @param statut pour différencier le message de confirmation (0) et
     * d'erreur(1)
     */
    public void envoiMailInscription(Client c, int statut) {
        StringWriter corps = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(corps);

        mailWriter.println("Bonjour " + c.getPrenom() + ",");
        mailWriter.println();

        switch (statut) {
            case 0: // Inscription confirmée
                mailWriter.println("  Nous vous confirmons votre inscription au service POSIT'IF.");
                mailWriter.println("  Votre numéro de client est : " + c.getId() +".");
                break;

            case 1: // Erreur d'inscription
                mailWriter.println("  Votre inscription au service POSIT'IF a malencontreusement échoué...");
                mailWriter.println("  Merci de recommencer ultérieurement.");
                break;
        }
        mailWriter.println();
        mailWriter.println("  Cordialement,");
        mailWriter.println();
        mailWriter.println("    L'équipe POSIT'IF");

        Message.envoyerMail("contact@posit.if", c.getEmail(), "Bienvenue chez POSIT'IF", corps.toString());
    }
    
    /**
     * Envoie une notification à l'employé chargé d'incarner un médium
     * @param conv la conversation avec le client
     */
    public void envoiNotificationEmploye(Conversation conv) {
        StringWriter corps = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(corps);
        
        mailWriter.println("Voyance demandée pour " 
                          + conv.getClient().getPrenom() + " " 
                          + conv.getClient().getNom() + "(#" 
                          + conv.getClient().getId() + ")");
        mailWriter.println("Médium à incarner : " + conv.getMedium().getNom());
        
        String telDest = conv.getEmploye().getNumeroTel();
        Message.envoyerNotification(telDest, corps.toString());
    }
    
    /**
     * Envoie une notification au client chargé d'incarner un médium
     * @param conv la conversation avec le client
     */
    public void envoiNotificationClient(Conversation conv) {
        StringWriter corps = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(corps);
        
        String date = new SimpleDateFormat("dd/MM/yyyy").format(conv.getDebut());
        
        mailWriter.println("Votre demande de voyance du " + date 
                         + " a bien été enregistrée. ");
        mailWriter.println("Vous pouvez dès à présent me contacter au "
                         + conv.getEmploye().getNumeroTel() +".");
        mailWriter.println("A tout de suite !");
        mailWriter.println("Posit'ifement vôtre, "+ conv.getMedium().getNom());
        
        String telDest = conv.getClient().getNumeroTel();
        Message.envoyerNotification(telDest, corps.toString());
    }

    /**
     * Initialisation en "dur" de la liste des mediums et des employés dans la
     * base de données.
     */
    public static void initialisation() {
        JpaUtil.creerEntityManager();

        // Employés Init
        Employe e1 = new Employe(true, "Bette", "Liam", "toto123", "liam.bette@posit.if", "0600000001");
        Employe e2 = new Employe(true, "Bosio", "Alexis", "123456", "alexis.bosio@posit.if", "0600000002");
        Employe e3 = new Employe(true, "Remy", "Thibault", "blabli123", "thibault.remy@posit.if", "0600000003");

        // Mediums
        Voyant m1 = new Voyant("Boule de Cristal", "Gwenaël", "Spécialiste des grandes conversations au-delà de TOUTES les frontières.");
        Voyant m2 = new Voyant("Marc de Café", "Professeur Maxwell", "Votre avenir est devant vous : regardons le ensemble !");
        Tarologue m3 = new Tarologue("Mme Irma", "Comprenez votre entourage grâce à mes cartes ! Résultats rapides.");
        Tarologue m4 = new Tarologue("Endora", "Mes cartes répondront à toutes vos questions personnelles.");
        Astrologue m5 = new Astrologue("Serena", "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé.", "École Normale Supérieur d'Astrologie (ENS-Astro)", "2006");
        Astrologue m6 = new Astrologue("Mr M. Histaire-Hyeux", "Avenir, avenir, que nous réserves-tu ? N'attendez plus, demandez à me consulter !", "Institut des Nouveaux Savoirs Astrologiques", "2010");

        // Preparation des listes de mediums et d'employés
        List listeEmp1 = new ArrayList();
        listeEmp1.add(e1);
        listeEmp1.add(e3);

        List listeEmp2 = new ArrayList();
        listeEmp2.add(e2);
        listeEmp2.add(e3);

        List listeEmp3 = new ArrayList();
        listeEmp3.add(e1);
        listeEmp3.add(e2);

        List listeEmp4 = new ArrayList();
        listeEmp3.add(e3);

        List listeMed1 = new ArrayList();
        listeMed1.add(m1);
        listeMed1.add(m3);
        listeMed1.add(m6);

        List listeMed2 = new ArrayList();
        listeMed2.add(m2);
        listeMed2.add(m3);
        listeMed2.add(m5);

        List listeMed3 = new ArrayList();
        listeMed3.add(m1);
        listeMed3.add(m2);
        listeMed3.add(m4);
        listeMed3.add(m5);
        listeMed3.add(m6);

        // Ajout des listes
        m1.setEmployes(listeEmp1);
        m2.setEmployes(listeEmp2);
        m3.setEmployes(listeEmp3);
        m4.setEmployes(listeEmp4);
        m5.setEmployes(listeEmp2);
        m6.setEmployes(listeEmp1);

        e1.setMediums(listeMed1);
        e2.setMediums(listeMed2);
        e3.setMediums(listeMed3);

        JpaUtil.ouvrirTransaction();
        EmployeDAO.creer(e1);
        EmployeDAO.creer(e2);
        EmployeDAO.creer(e3);

        VoyantDAO.creer(m1);
        VoyantDAO.creer(m2);
        TarologueDAO.creer(m3);
        TarologueDAO.creer(m4);
        AstrologueDAO.creer(m5);
        AstrologueDAO.creer(m6);
        JpaUtil.validerTransaction();
    }
    
    public Client getClientParId(long id){
        JpaUtil.creerEntityManager();
        Client personne = ClientDAO.obtenirClientParId(id);
        JpaUtil.fermerEntityManager();
        if (personne != null) {
            return personne;
        } else {
            return null;
        }
    }
    
     public Conversation getConversationParId(long id){
        JpaUtil.creerEntityManager();
        Conversation convo = ConversationDAO.obtenirConversationParId(id);
        JpaUtil.fermerEntityManager();
        if (convo != null) {
            return convo;
        } else {
            return null;
        }
    }
    
    public Employe getEmployeParId(long id){
        JpaUtil.creerEntityManager();
        Employe personne = EmployeDAO.obtenirEmployeParId(id);
        JpaUtil.fermerEntityManager();
        if (personne != null) {
            return personne;
        } else {
            return null;
        }
    }
    
    public Personne getPersonneParId(Personne p){
        JpaUtil.creerEntityManager();
        Personne personne = ClientDAO.obtenirClientParId(p.getId());
        JpaUtil.fermerEntityManager();
        if (personne != null) {
            return personne;
        } else {
            return null;
        }
    }
    
    public Medium getMediumParId(Long id){
        JpaUtil.creerEntityManager();
        Medium med = MediumDAO.obtenirMediumParId(id);
        JpaUtil.fermerEntityManager();
        return med;
    }
 
}