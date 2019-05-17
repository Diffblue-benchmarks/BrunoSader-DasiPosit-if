/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.positifihm.action;

import com.google.gson.JsonObject;
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
@WebServlet(name = "ActionInscription", urlPatterns = {"/ActionInscription"})
public class ActionInscription extends Action {

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
            out.println("<title>Servlet ActionInscription</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ActionInscription at " + request.getContextPath() + "</h1>");
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

    public void act(HttpServletRequest request) {
        Service s = new Service();
        String nom = (String) request.getParameter("surname");
        String prenom = (String) request.getParameter("name");
        String email = (String) request.getParameter("email");
        String mdp = (String) request.getParameter("password");
        String confirme = (String) request.getParameter("confirm");
        String adresse = (String) request.getParameter("adress");
        String tel = (String) request.getParameter("tel");
        Date dateNaissance = new Date();
        // Date dateNaissance = (Date) request.getParameter("birthday");
        if (mdp.equals(confirme)) {
            Client c = new Client(nom, prenom, mdp, email, tel, dateNaissance, adresse);
            boolean connected = s.sInscrire(c);
            request.setAttribute("verified", true);
            request.setAttribute("connected", connected);
            request.setAttribute("client", c);
        } else {
            request.setAttribute("verified", false);
            request.setAttribute("connected", false);
        }
    }

}
