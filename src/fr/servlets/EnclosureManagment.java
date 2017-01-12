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
			String statSLE = request.getParameter("statusSLE");
			String statSE = request.getParameter("statusSE");
			String statSQ = request.getParameter("statusSQ");
			String statG = request.getParameter("statusG");
			String statAP = request.getParameter("statusAPrices");
			String statEP = request.getParameter("statusEPrices");
			String statPRA = request.getParameter("statusPRA");
			String statRE = request.getParameter("statusRE");
			
			System.out.println("SLE: "+statSLE ); 
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
				
				
			} /**visualiser les employes assignables a l'enclos	**/
			else if ((statSLE != null) && statSLE.equals("okSLE")) {
				//Recuperation des employees de l'enclos(0,0)
				EnclosureBean e0 =ecdao.getEnclosureByLocation(0, 0, player_id);
				
				EmployeesDAO epdao = new EmployeesDAO();
				List<EmployeeBean> employees = new ArrayList<EmployeeBean>();
				employees = epdao.getEmployeesByEnclosure(e0.getId());
				
				int employeeQty = enclosure.getEmployee_quantity();
				
				int lengthList = employees.size();
				boolean isHealer= false;
				boolean isCleaner= false;
				boolean isSecurity= false;
				
				System.out.println("employees "+ employees);
				if (lengthList > 0) {
					for (EmployeeBean employee : employees) {
						if (employee.getType() == "healer"){
							isHealer= true;
						} else if(employee.getType() == "cleaner"){
							isCleaner= true;
						} else if(employee.getType() == "security"){
							isSecurity= true;
						}
					}
				}
				
				String reponseJson = "{\"isHealer\":\"" + isHealer + "\", \"isCleaner\":\"" + isCleaner
						+ "\", \"isSecurity\":\"" + isSecurity + "\", \"employeeQty\":" + employeeQty + "}";

				System.out.println("showEmployeesList" + reponseJson);
				response.getWriter().append(reponseJson);

			} /**Permettre affichage des employes dans l'enclos,showEmployees() en JS	**/
			else if ((statSE != null) && statSE.equals("okSE")) {
				// Recuperation de l'id de l'enclos selectionne et de ses employ�s
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
				
			}/**permettre affichage des jauges**/
			else if ((statG != null) && statG.equals("okG")) {
				//Recuperation de la jauge cleanness (dans enclosure)
				int cleanness= enclosure.getCleanliness_gauge();
				
				//Recuperation des jauges hungry/health (dans animals)
				AnimalsDAO andao= new AnimalsDAO();
				List<AnimalBean> animals = andao.getAnimalsByEnclosure(enclosure.getId());
				
				int totalHungry=0;
				int totalHealth=0;
				int hungry=0;
				int health=0;

				if(animals.size() != 0){
					for (AnimalBean animal : animals) {
						totalHungry += animal.getHungry_gauge();
						totalHealth += animal.getHealth_gauge();
					}
					
					//moyenne des jauges
					hungry= totalHungry/animals.size();
					health= totalHealth/animals.size();
				}

				//Envoie au format Json dans la reponse pour la fonction showGauges() en JS
				String reponseJson = "{\"cleanness\":"+cleanness+", \"hungry\":"+hungry +", \"health\":"+health+"}";
				response.getWriter().append(reponseJson);
			}
			/** fixer la valeur de l'input number **/
			 else if ((statSQ != null) && statSQ.equals("okSQ")) {
					//recuperation capacity et animal_quantity
					int capacity = enclosure.getCapacity();
					int animal_quantity = enclosure.getAnimal_quantity();
					
					//calcul de la capacite restante
					int rest = capacity - animal_quantity;
					int min = animal_quantity * -1;
					
					
					String responseJson = "{\"rest\":" + rest +",\"min\":"+min+"}";
					response.getWriter().append(responseJson);
			 }
			/**permettre affichage des prix**/
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
					long enclosure_price= ((long) prices.get("enclosureCosts_"+name)*(enclosure.getCapacity()/5));
					long animal_price= ((long)prices.get(name+"Costs"))*enclosure.getAnimal_quantity();
					long total_priceEAP= (long) ((enclosure_price + animal_price)*(0.75));

					//enregistrement dans la session en cas de revente
					session.setAttribute("total_priceEAP", total_priceEAP);
					//Envoie au format Json dans la reponse pour la fonction showAEPrice() en JS
					String reponseJson = "{\"total_price\":"+total_priceEAP+"}";
					response.getWriter().append(reponseJson);
					
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
				
				if(quantity < 0){
				player.setMoney((long) (money - (finalPrice)*(0.75)));
				}else{
					player.setMoney(money - finalPrice);
				}
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				
				money = player.getMoney();
				
				/**Creation des des animaux si achat ou revente**/
				AnimalsDAO andao= new AnimalsDAO();
				
				//si achat
				if(quantity > 0 && quantity <= (enclosure_capacity-animal_quantity)){
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
					}
				
				}//si revente
				else if(quantity < 0 && quantity <= (animal_quantity)){
					//Recuperation de la liste des animaux de l'enclos selectionne
					List<AnimalBean> animals = andao.getAnimalsByEnclosure(enclosure_id);
					
					int count = 0;
					for (AnimalBean animal : animals) {
						//Recuperation de l'id des animaux a delete
						andao.deleteAnimal(animal.getId());
						count++;
						
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
				
				player.setMoney(money + total_priceEAP);
				
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				
				//Delete de l'enclos
				enclosure.setCapacity(0);
				enclosure.setAnimal_quantity(0);
				enclosure.setCleanliness_gauge(100);
				enclosure.setEmployee_quantity(0);//A modifier quand les enployes seront crees
				
				ecdao.updateEnclosure(enclosure);
				
				//Recuperation de l'id de l'enclos selectionne et de ses employees pour deplacement dans l'enclos(0,0)
				int enclosure_id = enclosure.getId();

				EmployeesDAO epdao = new EmployeesDAO();
				List<EmployeeBean> employees = new ArrayList<EmployeeBean>();
				employees = epdao.getEmployeesByEnclosure(enclosure_id);

				int lengthList = employees.size();
				EnclosureBean e0 =ecdao.getEnclosureByLocation(0, 0, player.getId());
				
				if (lengthList > 0) {
					for (EmployeeBean employee : employees) {
						employee.setEnclosure_id(e0.getId());
						epdao.updateEmployee(employee);
						e0.setEmployee_quantity(e0.getEmployee_quantity() + 1);
						ecdao.updateEnclosure(e0);
					}
				}

				//Delete des animaux
				AnimalsDAO andao= new AnimalsDAO();
				List<AnimalBean> animals = andao.getAnimalsByEnclosure(enclosure.getId());

				for (AnimalBean animal : animals) {
					//Recuperation de l'id des animaux a delete
					andao.deleteAnimal(animal.getId());	
				}				
				//Permettre la redirection sur 'home' via Ajax (purshaseAnimals() en JS)
				response.getWriter().append("{\"code\" : \"OK\"}");
			}
		}
	}

}
