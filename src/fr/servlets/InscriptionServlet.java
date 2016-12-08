package fr.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dao.InscriptionFormulaire;
import fr.beans.PlayerBean;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
	
	private static final String ATT_PLAYER = "user";
	private static final String ATT_FORM = "form";
	
	private static final String VUE = "/home.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		InscriptionFormulaire form = new InscriptionFormulaire();
		
		// Appel de la méthode de validation de la requête
		PlayerBean player = form.inscrirePlayer(request);
				
		// RÃ©cupÃ©ration du bean
		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_PLAYER, player);
		request.setAttribute("isValide", form.isValide);
				
		// Transmission à  la JSP en charge de l'affichage des données
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
	
}
