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
import fr.utility.CalculateEnclosurePrice;

@WebServlet("/createEnclosure")
public class BuildEnclosureMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recuperation de la session actuelle
		HttpSession session = request.getSession(false);

		if (session != null) {
			// Recuperation des coordonneees de l'enclos a�construire depuis
			// la
			// Home.jsp
			// et enregistrement dans la session
			int locate_x = Integer.parseInt(request.getParameter("x"));
			int locate_y = Integer.parseInt(request.getParameter("y"));

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
			
			//Recuperation des parametres status envoyes par les fonctions ajax showPrice() et getForm()
			//pour effectuer des redirections dans ce doPost
			String statPrices = request.getParameter("statusPrices");
			String statForm = request.getParameter("statusForm");
			
			// Recuperation des prix d'enclos depuis la CostsDAO lorsque showPrice() est appelee
			if ((statPrices != null) && statPrices.equals("okP")) {
				CostsDAO cdao = new CostsDAO();
				JSONObject prices = cdao.getCosts();

				// Envoie des prix dans la reponse pour etre recupere dans la
				// fonction showPrice() dans buildEnclosure.js
				if (prices != null) {
					response.setContentType("application/json");
					response.getWriter().append(prices.toString());
				}
			} 
			/** Creation d'un nouvel enclos et update du player apres achat **/
			//Si getForm() est appelee
			else if ((statForm != null) &&statForm.equals("okF")) {

				
				// Recuperation de donnees enregistrees dans la session:
				// - les coordonnees d'enclos(ce doGet)
					int locate_x = (int) session.getAttribute("current_locate_x");
					int locate_y = (int) session.getAttribute("current_locate_y");

					// - les attributs du joueurs connecte (doPost de PlayerServlet)
					PlayerBean player = (PlayerBean) session.getAttribute("user");

					// Creation d'un objet local enclosure
					EnclosureBean enclosure = new EnclosureBean();

					// recuperation des donnees json envoyees en Ajax via getForm()
					// dans buildEnclosure.js
					int specieId = Integer.parseInt(request.getParameter("specie_id"));
					int enclosureCapacity = Integer.parseInt(request.getParameter("capacity"));

					// Creation des objets dao (EnclosuresDAO, PlayersDAO) pour
					// acceder aux methodes updates
					EnclosuresDAO edao = new EnclosuresDAO();
					PlayersDAO pdao = new PlayersDAO();
						
					//recuperation du prix de l'enclos via la classe CalculateEnclosurePrice
					long finalPrice= CalculateEnclosurePrice.CalEP(request);

					// Recuperation de l'id et du solde du joueur
					int playerId= player.getId();
					long money = player.getMoney();
						
					// Modification de money du player dans la BBD
					player.setMoney(money - finalPrice);
					
					pdao.updatePlayer(player);
						
					// Modification des donnees de l'enclo achete	
					enclosure.setCapacity(enclosureCapacity);
					enclosure.setSpecie_id(specieId);
					enclosure.setPlayer_id(playerId);
					enclosure.setLocate_x(locate_x);
					enclosure.setLocate_y(locate_y);
						
					edao.buyEnclosure(enclosure);	
			}
		}
	}
				
}