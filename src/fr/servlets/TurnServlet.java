package fr.servlets;

import java.io.IOException;
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
import fr.dao.AnimalsDAO;
import fr.dao.CostsDAO;
import fr.dao.EmployeesDAO;
import fr.dao.EnclosuresDAO;
import fr.dao.PlayersDAO;

@WebServlet("/newturn")
public class TurnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TurnServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperation de la session actuelle et du joueur connecte
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");
		if(player != null){
			String[] turn = new String[2];
			turn = player.getTurn().split(",");
			String reponseJson = "{\"hour\": "+turn[0]+", \"day\": "+turn[1]+", \"month\": "+turn[2]+"}";
			response.getWriter().append(reponseJson);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperation de la session actuelle et du joueur connecte
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");
		if(player != null){
			int player_id = player.getId();
			PlayersDAO pdao = new PlayersDAO();
			String enclosureM = request.getParameter("enclosureM");
			String onEnclosure = request.getParameter("onEnclosure");
			String newTime = request.getParameter("newTime");
			String newMonth = request.getParameter("newMonth");
			System.out.println(enclosureM);
			if(newMonth != null && newMonth.equals("ok")){
				EmployeesDAO emdao = new EmployeesDAO();
				CostsDAO costs = new CostsDAO();
				JSONObject obj = costs.getCosts();
				long cleanerSalary = (long) obj.get("salaryCosts_cleaner");
				long healerSalary = (long) obj.get("salaryCosts_healer");
				long securitySalary = (long) obj.get("salaryCosts_security");
				List<EmployeeBean> employees = emdao.getEmployeesByPlayer(player_id);
				int countCleaner = 0;
				int countHealer = 0;
				int countSecurity = 0;
				for(EmployeeBean employee: employees){
					if(employee.getType().equals("cleaner")){
						countCleaner++;
					}
					else if(employee.getType().equals("healer")){
						countHealer++;
					}
					else if(employee.getType().equals("security")){
						countSecurity++;
					}
				}
				System.out.println("countCleaner="+countCleaner);
				System.out.println("countHealer="+countHealer);
				System.out.println("countSecurity="+countSecurity);
				long totalCleanerSalaries = countCleaner*cleanerSalary;
				long totalHealerSalaries = countHealer*healerSalary;
				long totalSecuritySalaries = countSecurity*securitySalary;
				long totalSalaries = totalCleanerSalaries + totalHealerSalaries + totalSecuritySalaries;
				System.out.println(totalSalaries);
				player.setMoney(player.getMoney()-totalSalaries);
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				String responseJson = "{ \"money\":"+player.getMoney()+"}";
				response.getWriter().append(responseJson);
			}
			if(newTime != null){
				player.setTurn(newTime);
				session.setAttribute("user", player);
				pdao.updatePlayer(player);
				if(player.getMoney() <= -1000000){
					response.sendRedirect("home");
					request.setAttribute("perdu", "ok");
				}
			}
			if(enclosureM != null && enclosureM.equals("ok")){
				EnclosuresDAO edao = new EnclosuresDAO();
				List<EnclosureBean> enclosures = edao.getAllEnclosures(player_id);
				
				for(EnclosureBean enclosure: enclosures){
					EmployeesDAO emdao = new EmployeesDAO();
					List<EmployeeBean> employees = emdao.getEmployeesByEnclosure(enclosure.getId());
					AnimalsDAO adao = new AnimalsDAO();
					List<AnimalBean> animals = adao.getAnimalsByEnclosure(enclosure.getId());
					List<AnimalBean> weakAnimals = adao.getWeakestAnimals(enclosure.getId());
					int countCleaner = 0;
					for(EmployeeBean employee: employees){
						if(employee.getType().equals("healer")){
							for(AnimalBean animal: weakAnimals){
								if(animal.getHealth_gauge() < 99){
									animal.setHealth_gauge(animal.getHealth_gauge()+2);
								}
								if(animal.getHungry_gauge() > 1){
									animal.setHungry_gauge(animal.getHungry_gauge()-2);
								}
							}
						}
						if(employee.getType().equals("cleaner")){
							countCleaner++;
							if(enclosure.getCleanliness_gauge() < 98){
								enclosure.setCleanliness_gauge(enclosure.getCleanliness_gauge()+3);
							}
						}
					}
					for(AnimalBean animal: animals){
						animal.setHealth_gauge(animal.getHealth_gauge()-1);
						animal.setHungry_gauge(animal.getHungry_gauge()+1);
						adao.updateAnimal(animal);
					}
					if(countCleaner == 0 && enclosure.getCleanliness_gauge() >= (Math.round(animals.size()))){
						enclosure.setCleanliness_gauge(enclosure.getCleanliness_gauge()-(Math.round(animals.size())));
					}
					edao.updateEnclosure(enclosure);
				}

				if(onEnclosure != null && onEnclosure.equals("ok")){
					int current_locate_x = (int) session.getAttribute("current_locate_x");
					int current_locate_y = (int) session.getAttribute("current_locate_y");
					System.out.println("X="+current_locate_x+" Y="+current_locate_y);
					EnclosureBean enclosure = edao.getEnclosureByLocation(current_locate_x, current_locate_y, player_id);
					AnimalsDAO adao = new AnimalsDAO();
					List<AnimalBean> animals = adao.getAnimalsByEnclosure(enclosure.getId());
					
					int cleanGauge = enclosure.getCleanliness_gauge();
					int totalHealth = 0;
					int totalHungry = 0;
					int moyHungry = 0;
					int moyHealth = 0;
					if(animals.size() != 0){
						for(AnimalBean animal: animals){
							totalHealth += animal.getHealth_gauge();
							totalHungry += animal.getHungry_gauge();
						}
						moyHealth = totalHealth/animals.size();
						moyHungry = totalHungry/animals.size();
					}
					String reponseJson = "{\"clean\":"+cleanGauge+", \"hungry\":"+moyHungry+", \"health\":"+moyHealth+"}";
					response.getWriter().append(reponseJson);
				}
			}
		}
		
	}

}