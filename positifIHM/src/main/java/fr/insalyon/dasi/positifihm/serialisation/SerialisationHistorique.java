/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.positifihm.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.positif.metier.modele.Conversation;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author brunosader
 */
@WebServlet(name = "SerialisationHistorique", urlPatterns = {"/SerialisationHistorique"})
public class SerialisationHistorique extends Serialisation {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SerialisationHistorique</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SerialisationHistorique at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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

    public void serialize(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonArray jsonArrayConvo = new JsonArray();
            List<Conversation> mesConversations = (List<Conversation>) request.getAttribute("conversations");
            request.removeAttribute("conversations");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh");
            for (Conversation uneConvo : mesConversations) {
                JsonObject jsonConvo = new JsonObject();
                jsonConvo.addProperty("id", uneConvo.getId());
                jsonConvo.addProperty("employe", uneConvo.getEmploye().getNom());
                jsonConvo.addProperty("medium", uneConvo.getMedium().getNom());
                jsonConvo.addProperty("idMedium", uneConvo.getMedium().getId());
                jsonConvo.addProperty("debut", dateFormat.format(uneConvo.getDebut()));
                jsonConvo.addProperty("fin", dateFormat.format(uneConvo.getFin()));
                jsonArrayConvo.add(jsonConvo);
            }
            JsonObject jsonConvoContainer = new JsonObject();
            jsonConvoContainer.add("Conversations", jsonArrayConvo);
            Gson gsonHistory = new GsonBuilder().setPrettyPrinting().create();
            gsonHistory.toJson(jsonConvoContainer, out);
        }
    }
}
