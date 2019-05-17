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
import fr.insalyon.dasi.positif.metier.modele.Client;
import fr.insalyon.dasi.positif.metier.modele.Personne;
import fr.insalyon.dasi.positif.metier.service.Service;
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
@WebServlet(name = "SerialisationConnexion", urlPatterns = {"/SerialisationConnexion"})
public class SerialisationConnexion extends Serialisation {

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
            out.println("<title>Servlet SerialisationConnexion</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SerialisationConnexion at " + request.getContextPath() + "</h1>");
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
            Service s = new Service();
            HttpSession session = request.getSession(true);
            JsonObject jsonmyConnnection = new JsonObject();
            Personne p = (Personne) request.getAttribute("personne");
            request.removeAttribute("personne");
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
                JsonObject jsonPers = new JsonObject();
                jsonArrayPersonnes.add(jsonPers);
                jsonmyConnnection.add("personne", jsonArrayPersonnes);
                Gson mygson = new GsonBuilder().setPrettyPrinting().create();
                mygson.toJson(jsonmyConnnection, out);
            }
        }
    }
}
