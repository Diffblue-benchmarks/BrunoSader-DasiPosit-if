/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.positifihm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.positif.dao.JpaUtil;
import fr.insalyon.dasi.positif.metier.modele.Client;
import fr.insalyon.dasi.positif.metier.service.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            String todo = (String) request.getParameter("todo");
            Service s = new Service();
            switch(todo) {
                case "inscription":
                String nom = (String) request.getParameter("surname");
                String prenom = (String) request.getParameter("name");
                String email = (String) request.getParameter("login");
                String mdp = (String) request.getParameter("password");
                String confirme = (String) request.getParameter("confirm");
                String adresse = (String) request.getParameter("adress");
                String tel = (String) request.getParameter("tel");
                //String dateNaissance = (String)request.getParameter("birthday");
                Date dateNaissance = new Date();
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
                gson.toJson(jsonConnnected,out);
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