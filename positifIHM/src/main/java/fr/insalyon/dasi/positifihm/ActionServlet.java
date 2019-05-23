package fr.insalyon.dasi.positifihm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.positif.dao.JpaUtil;
import fr.insalyon.dasi.positif.metier.service.Service;
import fr.insalyon.dasi.positifihm.action.Action;
import fr.insalyon.dasi.positifihm.action.ActionCaracteristiqueMediums;
import fr.insalyon.dasi.positifihm.action.ActionCommencerConsultation;
import fr.insalyon.dasi.positifihm.action.ActionConnexion;
import fr.insalyon.dasi.positifihm.action.ActionConsulterMediums;
import fr.insalyon.dasi.positifihm.action.ActionDetailConvo;
import fr.insalyon.dasi.positifihm.action.ActionGenererVoyance;
import fr.insalyon.dasi.positifihm.action.ActionHistorique;
import fr.insalyon.dasi.positifihm.action.ActionInscription;
import fr.insalyon.dasi.positifihm.action.ActionProfil;
import fr.insalyon.dasi.positifihm.action.ActionProfilEmployer;
import fr.insalyon.dasi.positifihm.action.ActionStat;
import fr.insalyon.dasi.positifihm.action.ActionTerminerVoyance;
import fr.insalyon.dasi.positifihm.serialisation.Serialisation;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationAccueilEmploye;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationCaracteristiqueMediums;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationConnexion;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationConsulterMediums;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationDetailConvo;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationHistorique;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationInscription;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationProfil;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationProfilEmploye;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationRecupererPrediction;
import fr.insalyon.dasi.positifihm.serialisation.SerialisationStat;
import java.io.IOException;
import java.io.PrintWriter;
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
                case "detailConvo":
                    action = new ActionDetailConvo();
                    action.act(request);
                    serialisation = new SerialisationDetailConvo();
                    serialisation.serialize(request, response);
                    break;
                case "statistiques":
                    action = new ActionStat();
                    action.act(request);
                    serialisation = new SerialisationStat();
                    serialisation.serialize(request, response);
                    break;
                case "consulterMediums":
                    action = new ActionConsulterMediums();
                    action.act(request);
                    serialisation = new SerialisationConsulterMediums();
                    serialisation.serialize(request, response);
                    break;
                case "caractMedium":
                    action = new ActionCaracteristiqueMediums();
                    action.act(request);
                    serialisation = new SerialisationCaracteristiqueMediums();
                    serialisation.serialize(request, response);
                    break;
                case "commencerConsultation":
                    action = new ActionCommencerConsultation();
                    action.act(request);
                    JsonObject jsonStart = new JsonObject();
                    jsonStart.addProperty("done", true);
                    Gson gsonStart = new GsonBuilder().setPrettyPrinting().create();
                    gsonStart.toJson(jsonStart, out);
                    break;
                case "retournerClient":
                    action = new ActionProfil();
                    action.act(request);
                    serialisation = new SerialisationProfil();
                    serialisation.serialize(request, response);
                    break;
                case "profilEmploye":
                    action = new ActionProfilEmployer();
                    action.act(request);
                    serialisation = new SerialisationProfilEmploye();
                    serialisation.serialize(request, response);
                    break;
                case "accueilEmploye":
                    action = new ActionProfilEmployer();
                    action.act(request);
                    serialisation = new SerialisationAccueilEmploye();
                    serialisation.serialize(request, response);
                    break;
                case "genererVoyance":
                    action = new ActionGenererVoyance();
                    action.act(request);
                    JsonObject json = new JsonObject();
                    json.addProperty("bool", true);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    gson.toJson(json, out);
                    break;
                case "afficherPrediction":
                    serialisation = new SerialisationRecupererPrediction();
                    serialisation.serialize(request, response);
                    break;
                case "terminerVoyance":
                    action = new ActionTerminerVoyance();
                    action.act(request);
                    JsonObject jsonDone = new JsonObject();
                    jsonDone.addProperty("done", true);
                    Gson gsonDone = new GsonBuilder().setPrettyPrinting().create();
                    gsonDone.toJson(jsonDone, out);
                    break;
                case "deconnexion":
                    JsonObject jsonPers = new JsonObject();
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
