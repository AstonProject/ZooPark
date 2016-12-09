package fr.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.beans.PlayerBean;
import fr.dao.DaoHomeBuildMenu;
import fr.dao.InscriptionFormulaire;
import fr.validator.connexionValidator;

@WebServlet("/user")
public class PlayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String ATT_PLAYER = "player";
	private static final String ATT_FORM = "form";
	
	private static final String VUE = "WEB-INF/home.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equals("register")) {
			
			InscriptionFormulaire form = new InscriptionFormulaire();
			
			// Appel de la méthode de validation de la requête
			PlayerBean player = form.inscrirePlayer(request);
					
			// Récupération du bean
			request.setAttribute(ATT_FORM, form);
			request.setAttribute(ATT_PLAYER, player);
			request.setAttribute("isValide", form.isValide);
					
			// Transmission à la JSP en charge de l'affichage des données
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
			
		} else if (action.equals("connect")) {
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();

			String pseudo = request.getParameter("con_pseudo");
			String password = request.getParameter("con_password");
			request.setAttribute("pseudo", pseudo);

			int id = connexionValidator.checkUser(pseudo, password);
			
			if (id != 0) {
				
				DaoHomeBuildMenu objPlayer = new DaoHomeBuildMenu();
				
				PlayerBean player = objPlayer.getPlayerById(id);
				
				HttpSession session = request.getSession();
				session.setAttribute("user", player);
				
				RequestDispatcher rs = request.getRequestDispatcher( VUE );
				rs.forward(request, response);
				
			} else {
				
				request.setAttribute("erreur", "Pseudo ou Password incorrect");
				RequestDispatcher rs = request.getRequestDispatcher( VUE );
				rs.include(request, response);
				
			}
		}
	}
	
}
