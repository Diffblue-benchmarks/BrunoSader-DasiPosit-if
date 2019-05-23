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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rgall
 */
@WebServlet(name = "SerialisationStat", urlPatterns = {"/SerialisationStat"})
public class SerialisationStat extends Serialisation {

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
            out.println("<title>Servlet SerialisationStatVoyancesMed</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SerialisationStatVoyancesMed at " + request.getContextPath() + "</h1>");
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
            JsonArray jsonArrayStat = new JsonArray();
            JsonArray jsonArrayStat2 = new JsonArray();
            
            HashMap<String,Integer> Stats1 = (HashMap<String,Integer>) request.getAttribute("stats1");
            request.removeAttribute("stats1");
            
            HashMap<String,Integer> Stats2 = (HashMap<String,Integer>) request.getAttribute("stats2");
            request.removeAttribute("stats2");
         
            Set cles = Stats1.keySet();
            Iterator it = cles.iterator();
            while (it.hasNext()){
               Object cle = it.next(); // tu peux typer plus finement ici
               Object val = Stats1.get(cle).toString() ; // tu peux typer plus finement ici
               JsonObject jsonStat = new JsonObject();
               jsonStat.addProperty("personne", (String) cle);
               jsonStat.addProperty("valeur", (String) val);
               jsonArrayStat.add(jsonStat);
            
            }
            
            Set cles2 = Stats2.keySet();
            Iterator it2 = cles2.iterator();
            while (it2.hasNext()){
               Object cle = it2.next(); // tu peux typer plus finement ici
               Object val = Stats2.get(cle).toString() ; // tu peux typer plus finement ici
               JsonObject jsonStat2 = new JsonObject();
               jsonStat2.addProperty("personne", (String) cle);
               jsonStat2.addProperty("valeur", (String) val);
               jsonArrayStat2.add(jsonStat2);
            
            }
         
            JsonObject jsonStatContainer = new JsonObject();
            jsonStatContainer.add("stats1", jsonArrayStat);
            jsonStatContainer.add("stats2", jsonArrayStat2);
            Gson gsonHistory = new GsonBuilder().setPrettyPrinting().create();
            gsonHistory.toJson(jsonStatContainer, out);
        }
    }
}
