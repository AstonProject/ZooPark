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

import fr.beans.EmployeeBean;
import fr.beans.EnclosureBean;
import fr.beans.FinanceBean;
import fr.beans.PlayerBean;
import fr.dao.CostsDAO;
import fr.dao.EmployeesDAO;
import fr.dao.EnclosuresDAO;
import fr.dao.FinancesDAO;
import fr.dao.PlayersDAO;

/**
 * Servlet implementation class EmployeesManagement
 */
@WebServlet("/employeesManagement")
public class EmployeesManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public EmployeesManagement() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperation de la session actuelle et du joueur connecte
				HttpSession session = request.getSession(false);
				PlayerBean player = (PlayerBean) session.getAttribute("user");

				if (session != null && player != null) {
					// Puis redirection vers la JSP buildEnclosure
					this.getServletContext().getRequestDispatcher("/WEB-INF/employeesManagement.jsp").forward(request, response);
				}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			String statSEA = request.getParameter("statusSEA");
			String statEmP = request.getParameter("statusEmP");
			String statSEQ = request.getParameter("statusSEQ");
			String statEmED = request.getParameter("statusEmED");
			
			
					
			/**Permettre l'affichage des employees du joueurs**/
			if ((statSEA != null) && statSEA.equals("okSEA")) {
				//Recuperation de l'enclos(0,0) pour determiner quels sont les 
				//employees "libres"
				EnclosuresDAO ecdao = new EnclosuresDAO();
				EnclosureBean e0 =ecdao.getEnclosureByLocation(0, 0, player.getId());
				
				//Recuperation de la liste de tous les employees du joueur
				EmployeesDAO epdao = new EmployeesDAO();
				List<EmployeeBean> employees = new ArrayList<EmployeeBean>();
				employees = epdao.getEmployeesByPlayer(player.getId());
				
				//Definition des compteurs par type d'employee de l'enclos(0,0)
				int countHealer_e0 = 0;
				int countCleaner_e0 = 0;
				int countSecurity_e0 = 0;
				//Definition des compteurs par type d'employee
				int countHealer_total = 0;
				int countCleaner_total = 0;
				int countSecurity_total = 0;
				
				//Incrementation des compteurs selon les cas
				for (EmployeeBean employee : employees) {
					if(employee.getType().equals("healer")){
						countHealer_total += 1;
						if(employee.getEnclosure_id() == e0.getId()){
							countHealer_e0 +=1;
						}
					} else if(employee.getType().equals("cleaner")){
						countCleaner_total += 1;
						if(employee.getEnclosure_id() == e0.getId()){
							countCleaner_e0 +=1;
						}
					} else if(employee.getType().equals("security")){
						countSecurity_total += 1;
						if(employee.getEnclosure_id() == e0.getId()){
							countSecurity_e0 +=1;
						}
					}	
				}
				
				//Mettre au format Json
				String reponseJson = "{\"countHealer_e0\":" + countHealer_e0 + ",";
				reponseJson += "\"countCleaner_e0\":" + countCleaner_e0 + ",";
				reponseJson += "\"countSecurity_e0\":" + countSecurity_e0 + ",";
				reponseJson += "\"countHealer_total\":" + countHealer_total + ",";
				reponseJson += "\"countCleaner_total\":" + countCleaner_total + ",";
				reponseJson += "\"countSecurity_total\":" + countSecurity_total + "}";
				
				//Envoie dans la reponse pour recuperation par showEmployeesAssignment() en JS
				response.getWriter().append(reponseJson);
			} /**fixer les valeurs Min Max des input number**/
			else if ((statSEQ != null) && statSEQ.equals("okSEQ")) {
				EnclosuresDAO ecdao = new EnclosuresDAO();
				EnclosureBean e0 =ecdao.getEnclosureByLocation(0, 0, player.getId());
				
				//Recuperation de la liste de tous les employees du joueur
				EmployeesDAO epdao = new EmployeesDAO();
				List<EmployeeBean> employees = new ArrayList<EmployeeBean>();
				employees = epdao.getEmployeesByPlayer(player.getId());
				
				int maxQty= e0.getEmployee_slot();
				int employeesQty= employees.size();
				
				String responseJson = "{\"maxQty\":" + maxQty +",\"employeesQty\":"+employeesQty+"}";
				response.getWriter().append(responseJson);
			}/**Permettre l'affichage des prix de recrutement**/
			else if ((statEmP != null) && statEmP.equals("okEmP")) {
				// Recuperation des prix via CostsDAO dans un objet Json
				CostsDAO cdao = new CostsDAO();
				JSONObject prices = cdao.getCosts();

				// Envoie des prix dans la reponse pour etre recupere dans la
				// fonction showPrice() dans employeesManagement.js
				response.setContentType("application/json");
				response.getWriter().append(prices.toString());
			}/**Permettre recrutement / licenciement**/
			else if ((statEmED != null) && statEmED.equals("okEmED")) {
				int priceEmED = Integer.parseInt(request.getParameter("priceEmED"));
				int healerQty = Integer.parseInt(request.getParameter("healerQty"));
				int cleanerQty = Integer.parseInt(request.getParameter("cleanerQty"));
				int securityQty = Integer.parseInt(request.getParameter("securityQty"));
				int quantity =  healerQty + cleanerQty + securityQty;
				
				//Recuperation des employees de l'enclos(0,0)
				EnclosuresDAO ecdao = new EnclosuresDAO();
				EnclosureBean e0 =ecdao.getEnclosureByLocation(0, 0, player.getId());
				List<EmployeeBean> employees0 = new ArrayList<EmployeeBean>();
				EmployeesDAO epdao = new EmployeesDAO();
				employees0 = epdao.getEmployeesByEnclosure(e0.getId());
				
				//MAJ du solde joueur
				PlayersDAO pdao = new PlayersDAO();
				long money = player.getMoney();
				player.setMoney(money + priceEmED);
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				
				//MAJ de enclosure_id des employees si licenciement
				if(healerQty < 0 || cleanerQty < 0 || securityQty < 0){
					int countHl = 0;
					int countCl = 0;
					int countSe = 0;
					boolean isDeleted= false;
					
					for (EmployeeBean employee0 : employees0) {
						if(employee0.getType().equals("healer")){
							
							if(countHl < ((healerQty)*(-1))){
								epdao.deleteEmployee(employee0.getId());
							}
							countHl++;
						}
						if(employee0.getType().equals("cleaner")){
							
							if(countCl < ((cleanerQty)*(-1))){
								epdao.deleteEmployee(employee0.getId());
							}
							countCl++;
						} 
						if(employee0.getType().equals("security")){
							
							if(countSe < ((securityQty)*(-1))){
								epdao.deleteEmployee(employee0.getId());
							}
							countSe++;
						} 
						if(countHl==(healerQty*(-1)) && countCl==(cleanerQty*(-1)) && countSe == (securityQty*(-1))){
							isDeleted= true;
						}	
					}
					
					e0 =ecdao.getEnclosureByLocation(0, 0, player.getId());
					int eQty = e0.getEmployee_quantity();
					//Si les employees ont ete delete, MAJ de l'enclos(0,0)
					if(isDeleted == true){
						e0.setEmployee_quantity(eQty + quantity);
						ecdao.updateEnclosure(e0);
					}
				}
				
				// Si achat d'employes
				if(healerQty > 0 || cleanerQty > 0 || securityQty > 0){
					int hl=0;
					int cl=0;
					int se=0;
					int quantityE=0;
					boolean isCreate = false;
					
					if(healerQty > 0){
						for (hl=0 ; hl < healerQty; hl++){
							EmployeeBean emp= new EmployeeBean();
							emp.setType("healer");
							emp.setHealth_gauge(100);
							emp.setDescription("medium");
							emp.setEnclosure_id(e0.getId());
							emp.setPlayer_id(player.getId());
							epdao.createEmployee(emp);
							quantityE++;
						}
					}
					
					if(cleanerQty > 0){
						for (cl=0 ; cl < cleanerQty; cl++){
							EmployeeBean emp= new EmployeeBean();
							emp.setType("cleaner");
							emp.setHealth_gauge(100);
							emp.setDescription("medium");
							emp.setEnclosure_id(e0.getId());
							emp.setPlayer_id(player.getId());
							epdao.createEmployee(emp);
							quantityE++;
						}
					}
					
					if(securityQty > 0){
						for (se=0 ; se < cleanerQty; se++){
							EmployeeBean emp= new EmployeeBean();
							emp.setType("security");
							emp.setHealth_gauge(100);
							emp.setDescription("medium");
							emp.setEnclosure_id(e0.getId());
							emp.setPlayer_id(player.getId());
							epdao.createEmployee(emp);
							quantityE++;
						}
					}
					
					if(hl==healerQty && cl==cleanerQty && se == securityQty){
						isCreate= true;
					}
					e0 =ecdao.getEnclosureByLocation(0, 0, player.getId());
					int QtyE = e0.getEmployee_quantity();
					
					if(isCreate == true){
						e0.setEmployee_quantity(QtyE + quantityE);
						ecdao.updateEnclosure(e0);
					}
				}
				
				// preparation et envoi de la transaction
				FinancesDAO fdao = new FinancesDAO();
				FinanceBean finance = new FinanceBean();

				if (priceEmED < 0) {
					
					finance.setType_action("recruitment");
					finance.setSomme(priceEmED);
					finance.setLibelle(type);
					finance.setTurn(player.getTurn());
					finance.setAnimals_number(animal_quantity);
					finance.setPlayer_id(player.getId());
					finance.setEnclosure_id(enclosure.getId());
					finance.setPayMonthly(0);
				
					fdao.createFinance(finance);
				}
				
				
				
				//Permettre la redirection sur 'home' via Ajax (purshaseAnimals() en JS)
				response.getWriter().append("{\"code\" : \"OK\"}");
			}
		}
	}

}
