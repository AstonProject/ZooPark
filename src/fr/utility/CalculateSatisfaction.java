package fr.utility;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.beans.AnimalBean;
import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.dao.AnimalsDAO;
import fr.dao.EnclosuresDAO;

public class CalculateSatisfaction {
	static public double calcSatisfaction(HttpServletRequest request){
		HttpSession session = request.getSession();
		//Recupere l'utilisateur en session si il y en a un
		PlayerBean user = (PlayerBean) session.getAttribute("user");
		int playerId = user.getId();
		EnclosuresDAO edao = new EnclosuresDAO();
		AnimalsDAO andao = new AnimalsDAO();
		//Recuperation des jauges de proprete d'enclos
		List<EnclosureBean> enclosures = edao.getAllEnclosures(playerId);
		double cleanliness = 0;
		double nbrAnimals = 0;
		for(EnclosureBean enclosure: enclosures){
			nbrAnimals += enclosure.getAnimal_quantity();
			cleanliness += enclosure.getCleanliness_gauge();
		}
		double moyCleanliness = (double)(cleanliness/enclosures.size());
		
		//Recuperation des jauges de sante et faim des animaux 
		List<AnimalBean> animals = andao.getAnimalsByPlayer(playerId);
		double hangry = 0;
		double health = 0;
		for(AnimalBean animal: animals){
			hangry +=  animal.getHungry_gauge();
			health +=  animal.getHealth_gauge();
		}
		double moyHangry = (double)(hangry/animals.size());
		double moyhealth = (double)(health/animals.size());
		double moyAnimal = (double)(nbrAnimals/375);
		
		//Ponderer la satisfaction globale
		double totalSatisfaction=100;
		totalSatisfaction=(double)((moyCleanliness/2 + moyhealth/2 - moyHangry )/100 + moyAnimal);
	
		System.out.println("totalSatisfaction: " + totalSatisfaction); 
		
		totalSatisfaction=(double)(totalSatisfaction *50);
		System.out.println("totalSatisfaction*50: " + totalSatisfaction);
		
	
		return totalSatisfaction;
		
	}
}
