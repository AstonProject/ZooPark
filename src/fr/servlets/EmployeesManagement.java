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

import fr.beans.EmployeeBean;
import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
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
			System.out.println("statSEA: "+ statSEA);
			
			if ((statSEA != null) && statSEA.equals("okSEA")) {
				//Recuperation de la liste de tous les employees du joueur
				EmployeesDAO epdao = new EmployeesDAO();
				List<EmployeeBean> employees = new ArrayList<EmployeeBean>();
				employees = epdao.getEmployeesByPlayer(player.getId());
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
			}
		}
	}

}
