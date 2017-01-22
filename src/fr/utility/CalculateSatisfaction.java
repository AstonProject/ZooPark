package fr.utility;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.dao.EnclosuresDAO;

public class CalculateSatisfaction {
	static public int calcSatisfaction(HttpServletRequest request){
		HttpSession session = request.getSession();
		//Recupere l'utilisateur en session si il y en a un
		PlayerBean user = (PlayerBean) session.getAttribute("user");
		int playerId = user.getId();
		EnclosuresDAO edao = new EnclosuresDAO();
		List<EnclosureBean> enclosures = edao.getAllEnclosures(playerId);
		int cleanliness = 0;
		int nbrAnimals = 0;
		for(EnclosureBean enclosure: enclosures){
			nbrAnimals += enclosure.getAnimal_quantity();
			cleanliness += enclosure.getCleanliness_gauge();
		}
		int moyCleanliness = cleanliness/enclosures.size();
		int totalSatisfaction = (moyCleanliness/100 + nbrAnimals/375)*100;
		return totalSatisfaction;
	}
}
