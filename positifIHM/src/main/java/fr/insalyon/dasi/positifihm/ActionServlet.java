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
import fr.insalyon.dasi.positifihm.action.Action;
import fr.insalyon.dasi.positifihm.action.ActionConnexion;
import fr.insalyon.dasi.positifihm.action.ActionHistorique;
import fr.insalyon.dasi.positifihm.action.ActionInscription;
import fr.insalyon.dasi.positifihm.action.ActionProfil;
import fr.insalyon.dasi.positifihm.serialisation.Serialisation;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationConnexion;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationHistorique;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationInscription;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationProfil;
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
            Action action;
            Serialisation serialisation;
            switch (todo) {
                case "inscription":
                    action = new ActionInscription();
                    action.act(request);
                    serialisation = new SerialisationInscription();
                    serialisation.serialize(request, response);
                    break;
                case "connecter":
                    action = new ActionConnexion();
                    action.act(request);
                    serialisation = new SerialisationConnexion();
                    serialisation.serialize(request, response);
                    break;
                case "historique":
                    action = new ActionHistorique();
                    action.act(request);
                    serialisation = new SerialisationHistorique();
                    serialisation.serialize(request, response);
                    break;
                case "consulterMediums":
                    List<Medium> listeMediums = s.obtenirTousMediums();
                    JsonArray jsonArrayMediums = new JsonArray();
                    for (Medium unMedium : listeMediums) {
                        jsonPers = new JsonObject();
                        jsonPers.addProperty("nom", unMedium.getNom());
                        jsonPers.addProperty("id", unMedium.getId());
                        jsonArrayMediums.add(jsonPers);
                    }
                    JsonObject jsonMediumContainer = new JsonObject();
                    jsonMediumContainer.add("Mediums", jsonArrayMediums);
                    Gson gsonMedium = new GsonBuilder().setPrettyPrinting().create();
                    gsonMedium.toJson(jsonMediumContainer, out);
                    break;
                case "caractMedium":
                    String chaine = request.getParameter("myId");
                    Long id = Long.parseLong(chaine);
                    /*
                    //Medium Med = s.getMediumParId(id);
                    jsonPers = new JsonObject();
                    jsonPers.addProperty("nom", Med.getNom());
                    jsonPers.addProperty("desc", Med.getDescriptif());
                    JsonObject jsonMediumContainerBis = new JsonObject();
                    jsonMediumContainerBis.add("Medium", jsonPers);
                    Gson gsonMediumBis = new GsonBuilder().setPrettyPrinting().create();
                    gsonMediumBis.toJson(jsonMediumContainerBis, out);
                    */
                    break;
                case "retournerClient":
                    action = new ActionProfil();
                    action.act(request);
                    serialisation = new SerialisationProfil();
                    serialisation.serialize(request, response);
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
