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
			CostsDAO cdao = new CostsDAO();
			JSONObject prices = cdao.getCosts();
			/** Affichage des prix dans la jsp buildEnclosure **/
			
			// Recuperation des prix d'enclos depuis la CostsDAO
			String statPrices = request.getParameter("statusPrices");
			System.out.println("statprices = "+statPrices);
			String statForm = request.getParameter("statusForm");
			System.out.println("statform = "+statForm);
			
			if ((statPrices != null) && statPrices.equals("okP")) {
				

				// Envoie des prix dans la reponse pour etre recupere dans la
				// fonction showPrice() dans buildEnclosure.js
				if (prices != null) {
					response.setContentType("application/json");
					response.getWriter().append(prices.toString());
				}
			} else if ((statForm != null) &&statForm.equals("okF")) {

				/** Creation d'un nouvel enclos et update du player apres achat **/
				// Recuperation de donnees enregistrees dans la session:
				// - les coordonnees d'enclos(ce doGet)
					int locate_x = (int) session.getAttribute("current_locate_x");
					int locate_y = (int) session.getAttribute("current_locate_y");
				
					System.out.println("locate "+ locate_x +" " +locate_y);
					
					// - les attributs du joueurs connecte (doPost de PlayerServlet)
					PlayerBean player = (PlayerBean) session.getAttribute("user");

					// Creation d'un objet local enclosure
					EnclosureBean enclosure = new EnclosureBean();

					// recuperation des donnees json envoyees en Ajax via getForm()
					// dans buildEnclosure.js
					int specie_id = Integer.parseInt(request.getParameter("specie_id"));
					int enclosureCapacity = Integer.parseInt(request.getParameter("capacity"));
						
					System.out.println("donnees du getForm: sp_id= "+ specie_id +" ec= "+ enclosureCapacity);
						
					// Creation des objets dao (EnclosuresDAO, PlayersDAO) pour
						// acceder aux methodes updates
						EnclosuresDAO edao = new EnclosuresDAO();
						PlayersDAO pdao = new PlayersDAO();
						
					//recupration du prix de l'enclos
						int enclosurePrice=0;
						
						//https://www.mkyong.com/java/json-simple-example-read-and-write-json/
						if (specie_id == 1){
							enclosurePrice = (int) prices.get("enclosureCosts_elephant");
						}else if (specie_id == 2){
							enclosurePrice = (int) prices.get("enclosureCosts_giraffe");
						}else if (specie_id == 3){
							enclosurePrice = (int) prices.get("enclosureCosts_lion");
						}else if (specie_id == 3){
							enclosurePrice = (int) prices.get("enclosureCosts_camel");
						}
						System.out.println("enclosurePrice: " + enclosurePrice );
					
						
			}
		}
	}
				/*
				

				

				

				// Modification de money du player dans la BBD
				player.setMoney(solde - enclosurePrice);
				pdao.updatePlayer(player);

				// Modification des donn�es de l'enclo achete
				enclosure.setLocate_x(locate_x);
				enclosure.setLocate_y(locate_y);
				enclosure.setSpecie_id(specie_id);
				enclosure.setCapacity(enclosureCapacity);
				edao.updateEnclosure(enclosure);
				System.out.println("finish");*/

			

	
}