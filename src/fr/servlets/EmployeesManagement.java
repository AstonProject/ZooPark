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
import fr.beans.PlayerBean;
import fr.dao.CostsDAO;
import fr.dao.EmployeesDAO;
import fr.dao.EnclosuresDAO;

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
			
			//Recuperation de la liste de tous les employees du joueur
			EmployeesDAO epdao = new EmployeesDAO();
			List<EmployeeBean> employees = new ArrayList<EmployeeBean>();
			employees = epdao.getEmployeesByPlayer(player.getId());
					
			/**Permettre l'affichage des employees du joueurs**/
			if ((statSEA != null) && statSEA.equals("okSEA")) {
				//Recuperation de l'enclos(0,0) pour determiner quels sont les 
				//employees "libres"
				EnclosuresDAO ecdao = new EnclosuresDAO();
				EnclosureBean e0 =ecdao.getEnclosureByLocation(0, 0, player.getId());
				
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
				int maxQty= e0.getEmployee_slot();
				int employeesQty= employees.size();
				
				String responseJson = "{\"maxQty\":" + maxQty +",\"employeesQty\":"+employeesQty+"}";
				response.getWriter().append(responseJson);
			}
			/**Permettre l'affichage des prix de recrutement**/
			else if ((statEmP != null) && statEmP.equals("okEmP")) {
				// Recuperation des prix via CostsDAO dans un objet Json
				CostsDAO cdao = new CostsDAO();
				JSONObject prices = cdao.getCosts();

				// Envoie des prix dans la reponse pour etre recupere dans la
				// fonction showPrice() dans employeesManagement.js
				response.setContentType("application/json");
				response.getWriter().append(prices.toString());
			}
		}
	}

}
