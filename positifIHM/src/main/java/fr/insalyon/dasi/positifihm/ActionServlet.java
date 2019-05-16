package fr.insalyon.dasi.positifihm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.positif.dao.JpaUtil;
import fr.insalyon.dasi.positif.metier.modele.Client;
import fr.insalyon.dasi.positif.metier.modele.Conversation;
import fr.insalyon.dasi.positif.metier.modele.Employe;
import fr.insalyon.dasi.positif.metier.modele.Medium;
import fr.insalyon.dasi.positif.metier.modele.Personne;
import fr.insalyon.dasi.positif.metier.service.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author brunosader
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(true);
            String todo = (String) request.getParameter("todo");
            Service s = new Service();
            Client monClient;
            JsonObject jsonPers = new JsonObject();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh");
            switch (todo) {
                case "inscription":
                    String nom = (String) request.getParameter("surname");
                    String prenom = (String) request.getParameter("name");
                    String email = (String) request.getParameter("email");
                    String mdp = (String) request.getParameter("password");
                    String confirme = (String) request.getParameter("confirm");
                    String adresse = (String) request.getParameter("adress");
                    String tel = (String) request.getParameter("tel");
                    // String dateNaissance = (String)request.getParameter("birthday");
                    Date dateNaissance = new Date();
                    // Date dateNaissance = (Date) request.getParameter("birthday");
                    JsonObject jsonConnnected = new JsonObject();
                    if (mdp.equals(confirme)) {
                        Client c = new Client(nom, prenom, mdp, email, tel, dateNaissance, adresse);
                        boolean connected = s.sInscrire(c);
                        jsonConnnected.addProperty("verified", true);
                        jsonConnnected.addProperty("connected", connected);
                    } else {
                        jsonConnnected.addProperty("verified", false);
                        jsonConnnected.addProperty("connected", false);
                    }
                    JsonObject jsonContainer = new JsonObject();
                    jsonContainer.add("log", jsonConnnected);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    gson.toJson(jsonConnnected, out);
                    break;
                case "connecter":
                    String myemail = (String) request.getParameter("login");
                    String mymdp = (String) request.getParameter("password");
                    JsonObject jsonmyConnnection = new JsonObject();
                    Personne p = s.seConnecter(myemail, mymdp);

                    if (p == null) {
                        jsonmyConnnection.addProperty("client", false);
                        jsonmyConnnection.addProperty("employe", false);
                        Gson mygson = new GsonBuilder().setPrettyPrinting().create();
                        mygson.toJson(jsonmyConnnection, out);
                    } else {
                        session.setAttribute("personneConnectee", p);
                        Client client = s.getClientParId(p.getId());
                        if (client != null) {
                            jsonmyConnnection.addProperty("client", true);

                        } else {
                            jsonmyConnnection.addProperty("employe", true);
                        }
                        JsonArray jsonArrayPersonnes = new JsonArray();
                        jsonPers = new JsonObject();
                        jsonArrayPersonnes.add(jsonPers);
                        jsonmyConnnection.add("personne", jsonArrayPersonnes);
                        Gson mygson = new GsonBuilder().setPrettyPrinting().create();
                        mygson.toJson(jsonmyConnnection, out);
                    }
                    break;
                case "historique":
                    Personne pers = (Personne) session.getAttribute("personneConnectee");
                    monClient = s.getClientParId(pers.getId());
                    List<Conversation> mesConversations = monClient.getConversations();
                    JsonArray jsonArrayConvo = new JsonArray();
                    for (Conversation uneConvo : mesConversations) {
                        JsonObject jsonConvo = new JsonObject();
                        jsonConvo.addProperty("id", uneConvo.getId());
                        jsonConvo.addProperty("employe", uneConvo.getEmploye().getNom());
                        jsonConvo.addProperty("medium", uneConvo.getMedium().getNom());
                        jsonConvo.addProperty("debut", dateFormat.format(uneConvo.getDebut()));
                        jsonConvo.addProperty("fin", dateFormat.format(uneConvo.getFin()));
                        jsonArrayConvo.add(jsonConvo);
                    }
                    JsonObject jsonConvoContainer = new JsonObject();
                    jsonConvoContainer.add("Conversations", jsonArrayConvo);
                    Gson gsonHistory = new GsonBuilder().setPrettyPrinting().create();
                    gsonHistory.toJson(jsonConvoContainer, out);
                    break;
                case "consulterMediums":
                    List<Medium> listeMediums = s.obtenirTousMediums();
                    JsonArray jsonArrayMediums = new JsonArray();
                    for (Medium unMedium : listeMediums) {
                        jsonPers = new JsonObject();
                        jsonPers.addProperty("id", unMedium.getId());
                        jsonPers.addProperty("nom", unMedium.getNom());
                        jsonPers.addProperty("descriptif", unMedium.getDescriptif());
                        jsonArrayMediums.add(jsonPers);
                    }
                    JsonObject jsonMediumContainer = new JsonObject();
                    jsonMediumContainer.add("Mediums", jsonArrayMediums);
                    Gson gsonMedium = new GsonBuilder().setPrettyPrinting().create();
                    gsonMedium.toJson(jsonMediumContainer, out);
                    break;
                case "retournerClient":
                    Personne per = (Personne) session.getAttribute("personneConnectee");
                    monClient = s.getClientParId(per.getId());
                    jsonPers = new JsonObject();
                    jsonPers.addProperty("nom", monClient.getNom());
                    jsonPers.addProperty("prenom", monClient.getPrenom());
                    jsonPers.addProperty("zodiac", monClient.getSigneZodiaque());
                    jsonPers.addProperty("chinois", monClient.getSigneChinois());
                    jsonPers.addProperty("totem", monClient.getAnimal());
                    jsonPers.addProperty("couleur", monClient.getCouleur());
                    Gson mygson = new GsonBuilder().setPrettyPrinting().create();
                    mygson.toJson(jsonPers, out);
                    break;
                case "deconnexion":
                    jsonPers = new JsonObject();
                    session.removeAttribute("personneConnectee");
                    Gson decogson = new GsonBuilder().setPrettyPrinting().create();
                    decogson.toJson(jsonPers, out);
                    break;
            }
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
