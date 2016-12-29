package fr.utility;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import fr.dao.CostsDAO;

public class CalculateEnclosurePrice {

	/** Methode retournant le prix de l'enclos choisi**/
	static public long CalEP(HttpServletRequest request){
		
		// recuperation des donnees json envoyees en Ajax via getForm()
		// dans buildEnclosure.js
		int specie_id = Integer.parseInt(request.getParameter("specie_id"));
		int enclosureCapacity = Integer.parseInt(request.getParameter("capacity"));
		
		//Recuperation des prix de CostsDAO dans un object Json
				CostsDAO cdao = new CostsDAO();
				JSONObject prices = cdao.getCosts();
				
		//calcul du prix selon les cas
		long enclosurePrice=0;
		long finalPrice=0;
		
			//Recuperation du prix unitaire d'un enclos a partir de l'object json
		if (specie_id == 1){
				enclosurePrice = (long) prices.get("enclosureCosts_elephant");
		}else if (specie_id == 2){
				enclosurePrice = (long) prices.get("enclosureCosts_giraffe");
		}else if (specie_id == 3){
				enclosurePrice = (long) prices.get("enclosureCosts_lion");
		}else if (specie_id == 4){
				enclosurePrice = (long) prices.get("enclosureCosts_camel");
		}
		
			//Calcul du prix selon la taille
		if (enclosureCapacity == 5){
			finalPrice = enclosurePrice;
		}else if (enclosureCapacity == 10){
			finalPrice = enclosurePrice*2;
		}else if (enclosureCapacity == 15){
			finalPrice = enclosurePrice*3;
		}
		
		return finalPrice;
		
	}
}
