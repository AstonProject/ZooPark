package fr.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.beans.PlayerBean;
import fr.utility.BuildEnclosureUtil;


@WebServlet("/createEnclosure")
public class BuildEnclosureMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recuperation de la session actuelle et du joueur connecte
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			// Recuperation des coordonneees de l'enclos a construire depuis la
			// Home.jsp
			// et enregistrement dans la session
			int locate_x = Integer.parseInt(request.getParameter("x"));
			int locate_y = Integer.parseInt(request.getParameter("y"));

			session.setAttribute("current_locate_x", locate_x);
			session.setAttribute("current_locate_y", locate_y);

			// Puis redirection vers la JSP buildEnclosure
			this.getServletContext().getRequestDispatcher("/WEB-INF/buildEnclosure.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recuperation de la session actuelle et du joueur connecte
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			
			// Recuperation des parametres status envoyes par les fonctions ajax
			// showDescription() showPrice() et getForm()
			// pour effectuer des redirections dans ce doPost
			String statDescriptions = request.getParameter("statusDescriptions");
			String statPrices = request.getParameter("statusPrices");
			String statForm = request.getParameter("statusForm");

			/** Recuperation des description d'enclos **/
			if ((statDescriptions != null) && statDescriptions.equals("okD")) {
				BuildEnclosureUtil.showDescriptions(request, response);
			} /** Affichage des prix dans la jsp buildEnclosure **/
			
			else if ((statPrices != null) && statPrices.equals("okP")) {
				BuildEnclosureUtil.showPrices(request, response);
			}
			/** Creation d'un nouvel enclos et update du player apres achat **/
			else if ((statForm != null) && statForm.equals("okF")) {
				BuildEnclosureUtil.createEnclosure(request, response);
			} 
		}
	}
}