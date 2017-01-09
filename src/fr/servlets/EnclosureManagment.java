package fr.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import fr.beans.AnimalBean;
import fr.beans.EmployeeBean;
import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.beans.SpecieBean;
import fr.dao.AnimalsDAO;
import fr.dao.CostsDAO;
import fr.dao.EmployeesDAO;
import fr.dao.EnclosuresDAO;
import fr.dao.PlayersDAO;
import fr.dao.SpeciesDAO;

@WebServlet("/enclosureManagment")
public class EnclosureManagment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EnclosureManagment() {
		super();

	}

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
			this.getServletContext().getRequestDispatcher("/WEB-INF/enclosureManagement.jsp").forward(request,
					response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			
			String statSA = request.getParameter("statusSA");
			String statSE = request.getParameter("statusSE");
			String statAP = request.getParameter("statusAPrices");
			String statEP = request.getParameter("statusEPrices");
			String statPRA = request.getParameter("statusPRA");
			String statRE = request.getParameter("statusRE");
			
			System.out.println("statusEPrices: "+statEP);
			// Recuperation de donnees enregistrees dans la session:
			// - les coordonnees d'enclos(ce doGet)
			int locate_x = (int) session.getAttribute("current_locate_x");
			int locate_y = (int) session.getAttribute("current_locate_y");
			int player_id = player.getId();

			EnclosuresDAO ecdao = new EnclosuresDAO();
			EnclosureBean enclosure = new EnclosureBean();

			enclosure = ecdao.getEnclosureByLocation(locate_x, locate_y, player_id);

			/**Permettre affichage des animaux de l'enclos, showAnimals() en JS**/
			if ((statSA != null) && statSA.equals("okSA")) {
				// Recuperation des descriptions au format Json pour envoie par
				// ajax

				String reponseJson = "{\"specie_id\":" + enclosure.getSpecie_id() + ",";
				reponseJson += "\"capacity\":" + enclosure.getCapacity() + ",";
				reponseJson += "\"animal_quantity\":" + enclosure.getAnimal_quantity() + "}";

				response.getWriter().append(reponseJson);
				
				/**Permettre affichage des employés dans l'enclos,showEmployees() en JS	**/
			} else if ((statSE != null) && statSE.equals("okSE")) {
				// Recuperation de l'id de l'enclos selectionne et de ses employés
				int enclosure_id = enclosure.getId();

				EmployeesDAO epdao = new EmployeesDAO();
				List<EmployeeBean> employees = new ArrayList<EmployeeBean>();

				employees = epdao.getEmployeesByEnclosure(enclosure_id);

				// Recuperation des employes au format Json pour envoie par
				// ajax
				String reponseJson = "{";
				int lengthList = employees.size();
				int count = 1;

				if (lengthList > 0) {
					for (EmployeeBean employee : employees) {
						String type = employee.getType();

						reponseJson += "\"type" + count + "\":\"" + type + "\"";
						count++;

						if (count != (lengthList+1)) {
							reponseJson += ",";
						}
					}
					reponseJson += "}";

					response.getWriter().append(reponseJson);
				}
				
			}/**permettre affichage des prix**/
			else if ((statAP != null) ||(statEP != null)) {
				CostsDAO cdao = new CostsDAO();
				JSONObject prices = cdao.getCosts();
				
				int specie_id = enclosure.getSpecie_id();
				SpeciesDAO spdao= new SpeciesDAO();
				SpecieBean specie= spdao.getSpecieById(specie_id);
				String name= specie.getName();
				
				// Recuperation des prix unitaire d'un animal de type"name"
				// via CostsDAO 
				if( statEP == null && statAP.equals("okAP")){
					long unit_price= (long) prices.get(name+"Costs");

					//Envoie au format Json dans la reponse pour la fonction showAEPrice() en JS
					String reponseJson = "{\"unit_price\":"+unit_price+"}";
					response.getWriter().append(reponseJson);
				}// Recuperation des prix de l'enclos et de tous les animaux
				 // en cas de revente d'enclos
				else if(statAP == null && statEP.equals("okEP")){
					long enclosure_price= (long) prices.get("enclosureCosts_"+name);
					long animal_price= ((long)prices.get(name+"Costs"))*enclosure.getAnimal_quantity();
					long total_priceEAP= enclosure_price + animal_price;

					//enregistrement dans la session en cas de revente
					session.setAttribute("total_priceEAP", total_priceEAP);
					//Envoie au format Json dans la reponse pour la fonction showAEPrice() en JS
					String reponseJson = "{\"total_price\":"+total_priceEAP+"}";
					response.getWriter().append(reponseJson);
					System.out.println("total_price json: "+reponseJson);
					
				}
				
			}/**permettre achat ou revente des animaux**/
			else if ((statPRA != null) && statPRA.equals("okPRA")) {
				PlayersDAO pdao = new PlayersDAO();
				//Recuperation de la quantite d'animaux demande
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				//Recuperation des caracteristique de l'enclos necessaire aux differentes
				//MAJ dans la BDD
				int enclosure_id= enclosure.getId();
				int specie_id= enclosure.getSpecie_id();
				int animal_quantity= enclosure.getAnimal_quantity();
				int enclosure_capacity = enclosure.getCapacity();

				//MAj du nb d'animaux dans l'enclos dans la bdd
				enclosure.setAnimal_quantity(animal_quantity+quantity);
				ecdao.updateEnclosure(enclosure);
				System.out.println("ajout dans l'enclos");
				
				// Recuperation des prix unitaire d'un animal du type indique 
				// via CostsDAO dans un objet Json
				CostsDAO cdao = new CostsDAO();
				JSONObject prices = cdao.getCosts();
					
				SpeciesDAO spdao= new SpeciesDAO();
				SpecieBean specie= spdao.getSpecieById(specie_id);
				String name= specie.getName();
				long unit_price= (long) prices.get(name+"Costs");
				System.out.println("price= "+ unit_price);
				
				//MAJ du solde du player dans la BDD et en session
				long finalPrice= unit_price*quantity;
				long money = player.getMoney();
				System.out.println("solde AV achat/revente: "+ money);
				player.setMoney(money - finalPrice);
				
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				
				money = player.getMoney();
				System.out.println("solde AP achat/revente: "+ money);
				/**Creation des des animaux si achat ou revente**/
				AnimalsDAO andao= new AnimalsDAO();
				
				//si achat
				if(quantity > 0 && quantity <= (enclosure_capacity-animal_quantity)){
					System.out.println("entree boucle create animal");

					//Creation des animaux achetes
					for (int i=0; i < quantity; i++){
						
						AnimalBean animal= new AnimalBean();
						animal.setName(specie.getName());
						animal.setHungry_gauge(0);
						animal.setHealth_gauge(100);
						animal.setDescription(specie.getDescription());
						animal.setSpecie_id(specie_id);
						animal.setEnclosure_id(enclosure_id);		
						
						//Ajout dans la BDD des animaux achetes
						andao.createAnimal(animal);
						System.out.println("animal" + i +" create sur: " + quantity);
					}
				
				}//si revente
				else if(quantity < 0 && quantity <= (animal_quantity)){
					System.out.println("entree boucle delete animal");
					//Recuperation de la liste des animaux de l'enclos selectionne
					List<AnimalBean> animals = andao.getAnimalsByEnclosure(enclosure_id);
					
					int count = 0;
					for (AnimalBean animal : animals) {
						//Recuperation de l'id des animaux a delete
						andao.deleteAnimal(animal.getId());
						
						count++;
						System.out.println("animal_id: "+animal.getId()+" a ete delete");
						//Arret lorsque le bon nombre est atteint
						if (count == (quantity*(-1))) {
							break;
						}
					}
				}
				
				//Permettre la retiraction sur \home via Ajax (purshaseAnimals() en JS)
				response.getWriter().append("{\"code\" : \"OK\"}");
			}
			/**permettre la revente d'un enclos et de tous ses animaux**/
			else if ((statRE != null) && statRE.equals("okRE")) {
				//MAJ du solde du player dans la BDD et en session
				PlayersDAO pdao = new PlayersDAO();
				
				long total_priceEAP = (long) session.getAttribute("total_priceEAP");
				long money = player.getMoney();
				
				System.out.println("solde AV achat/revente: "+ money);
				player.setMoney(money + total_priceEAP);
				
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				
				money = player.getMoney();
				System.out.println("solde AP achat/revente: "+ money);
				//Delete de l'enclos
				enclosure.setCapacity(0);
				enclosure.setAnimal_quantity(0);
				enclosure.setCleanliness_gauge(0);
				enclosure.setEmployee_quantity(0);//A modifier quand les enployes seront crees
				
				ecdao.updateEnclosure(enclosure);
				//Delete des animaux
				AnimalsDAO andao= new AnimalsDAO();
				List<AnimalBean> animals = andao.getAnimalsByEnclosure(enclosure.getId());

				for (AnimalBean animal : animals) {
					//Recuperation de l'id des animaux a delete
					andao.deleteAnimal(animal.getId());
	
					System.out.println("animal_id: "+animal.getId()+" a ete delete");	
				}
				
				//Permettre la retiraction sur \home via Ajax (purshaseAnimals() en JS)
				response.getWriter().append("{\"code\" : \"OK\"}");
			}

		}
	}

}
