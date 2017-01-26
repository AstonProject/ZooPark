package fr.utility;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import fr.beans.EnclosureBean;
import fr.beans.FinanceBean;
import fr.beans.PlayerBean;
import fr.beans.SpecieBean;
import fr.dao.CostsDAO;
import fr.dao.EnclosuresDAO;
import fr.dao.FinancesDAO;
import fr.dao.PlayersDAO;
import fr.dao.SpeciesDAO;

public class BuildEnclosureUtil {
	
	/** Recuperation des description d'enclos via SpecieDAO lorsque
		showDescription() est appelee (buildEnclosure.js)
	 * @throws IOException **/
	static public void showDescriptions(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// Recuperation des descriptions via SpecieDAO dans une
		// ArrayList
		SpeciesDAO spdao = new SpeciesDAO();
		List<String> descriptions = spdao.getDescriptions();

		// Recuperation des descriptions au format Json pour envoie par
		// ajax
		String reponseJson = "{";
		int lengthList = descriptions.size();
		int count = 0;

		for (String description : descriptions) {
			reponseJson += "\"description" + count + "\":\"" + description + "\"";
			count++;

			if (count != lengthList) {
				reponseJson += ",";
			}
		}
		reponseJson += "}";

		response.getWriter().append(reponseJson);
	}
	
	/** Affichage des prix dans la jsp buildEnclosure **/
	// Recuperation des prix d'enclos via CostsDAO lorsque showPrice()
	// est appelee (buildEnclosure.js)
	static public void showPrices(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// Recuperation des prix via CostsDAO dans un objet Json
		CostsDAO cdao = new CostsDAO();
		JSONObject prices = cdao.getCosts();

		// Envoie des prix dans la reponse pour etre recupere dans la
		// fonction showPrice() dans buildEnclosure.js
		response.setContentType("application/json");
		response.getWriter().append(prices.toString());
	}
	
	/** Creation d'un nouvel enclos et update du player apres achat **/
	// Si getForm() est appelee (buildEnclosure.js)
	static public void createEnclosure(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");
		// Recuperation de donnees enregistrees dans la session:
		// - les coordonnees d'enclos(ce doGet)
		int locate_x = (int) session.getAttribute("current_locate_x");
		int locate_y = (int) session.getAttribute("current_locate_y");

		// - les attributs du joueurs connecte (doPost de PlayerServlet)

		//Recuperation de l'enclo de la bdd
		EnclosuresDAO ecdao = new EnclosuresDAO();
		EnclosureBean enclosure = new EnclosureBean();
		
		int player_id = player.getId();
		enclosure = ecdao.getEnclosureByLocation(locate_x, locate_y, player_id);
		
		// recuperation des donnees json envoyees en Ajax via getForm()
		// dans buildEnclosure.js
		int specieId = Integer.parseInt(request.getParameter("specie_id"));
		int enclosureCapacity = Integer.parseInt(request.getParameter("capacity"));

		// Creation des objets dao (EnclosuresDAO, PlayersDAO) pour
		// acceder aux methodes updates
		EnclosuresDAO edao = new EnclosuresDAO();
		PlayersDAO pdao = new PlayersDAO();

		// recuperation du prix de l'enclos via la classe
		// CalculateEnclosurePrice
		long finalPrice = CalculateEnclosurePrice.CalEP(request);
		
		// Recuperation du solde du joueur
		long money = player.getMoney();

		// Modification de money du player dans la BBD
		player.setMoney(money - finalPrice);

		pdao.updatePlayer(player);
		
		// Modification des donnees de l'enclo achete
		enclosure.setCapacity(enclosureCapacity);
		enclosure.setSpecie_id(specieId);

		edao.updateEnclosure(enclosure);

		// mise a jour des donnees du joeur en session
		session.setAttribute("user", player);

		// envoi de la transaction dans la bdd finance
		FinancesDAO fdao = new FinancesDAO();
		FinanceBean finance = new FinanceBean();
		
		if (finalPrice > 0) {
			
			// recuperation du nom de l'espece
			int specie_id = enclosure.getSpecie_id();
			SpeciesDAO spdao= new SpeciesDAO();
			SpecieBean specie= spdao.getSpecieById(specie_id);
			String name= specie.getName();
			
			finance.setType_action("purchase");
			finance.setSomme(finalPrice);
			finance.setLibelle("enclosure of " + name);
			finance.setTurn(player.getTurn());
			finance.setAnimals_number(0);
			finance.setPlayer_id(player.getId());
			finance.setEnclosure_id(enclosure.getId());
			finance.setPayMonthly(0);
		
			fdao.createFinance(finance);
		}
		
		// Puis redirection vers la servlet home via le callback de
		// getForm()

		response.getWriter().append("{\"code\" : \"OK\"}");
	}
}
