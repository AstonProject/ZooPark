package fr.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.dao.CostsDAO;
import fr.dao.EnclosuresDAO;
import fr.dao.PlayersDAO;

@WebServlet("/createEnclosure")
public class BuildEnclosureMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recuperation de la session actuelle
		HttpSession session = request.getSession(false);

		if (session != null) {
			// Recuperation des coordonneees de l'enclos a�construire depuis la
			// Home.jsp
			// et enregistrement dans la session
			String locate_x = request.getParameter("x");
			String locate_y = request.getParameter("y");

			session.setAttribute("current_locate_x", locate_x);
			session.setAttribute("current_locate_y", locate_y);

			this.getServletContext().getRequestDispatcher("/WEB-INF/buildEnclosure.jsp").forward(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recuperation de la session actuelle
		HttpSession session = request.getSession(false);
		

		if (session != null) {
			/** Affichage des prix dans la jsp buildEnclosure **/
			// Recuperation des prix d'enclos depuis la CostsDAO
			CostsDAO cdao = new CostsDAO();
			JSONObject prices = cdao.getCosts();

			// Envoie des prix dans la reponse pour etre recupere dans la
			// fonction showPrice() dans buildEnclosure.js
			if (prices != null) {
				response.setContentType("application/json");
				response.getWriter().append(prices.toString());
			}

			/**
			 * Creation d'un nouvel enclos et update du player apr�s achat
			 **/
			/*
			// Recuperation de donnees enregistr�e dans la session:
			// - les coordonn�es d'enclos(ce doGet)
			int locate_x = (int) session.getAttribute("current_locate_x");
			int locate_y = (int) session.getAttribute("current_locate_y");
			
			// - les attributs du joueurs connect� (doPost de PlayerServlet)
			PlayerBean player = (PlayerBean) session.getAttribute("user");

			// Creation d'un objet local enclosure
			EnclosureBean enclosure = new EnclosureBean();

			// recuperation des donn�es json envoyees en Ajax via getForm()
			// dans buildEnclosure.js
			int specie_id = Integer.parseInt(request.getParameter("specie_id"));
			int enclosureCapacity = Integer.parseInt(request.getParameter("capacity"));
			int enclosurePrice = Integer.parseInt(request.getParameter("price"));

			// Creation des objets dao (EnclosuresDAO, PlayersDAO) pour
			// acceder aux m�thodes updates
			EnclosuresDAO edao = new EnclosuresDAO();
			PlayersDAO pdao = new PlayersDAO();

			// Recuperation du solde actuel du joueur (prevoir de changer en
			// double)
			int solde = player.getMoney();

			// Modification de money du player dans la BBD
			player.setMoney(solde - enclosurePrice);
			pdao.updatePlayer(player);

			// Modification des donn�es de l'enclo achet�
			enclosure.setLocate_x(locate_x);
			enclosure.setLocate_y(locate_y);
			enclosure.setSpecie_id(specie_id);
			enclosure.setCapacity(enclosureCapacity);
			edao.updateEnclosure(enclosure);*/
			
		}
	}
}