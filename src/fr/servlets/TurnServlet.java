package fr.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.beans.AnimalBean;
import fr.beans.EmployeeBean;
import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.dao.AnimalsDAO;
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
			System.out.println(enclosureM);
			if(!request.getParameter("newTime").equals(null)){
				player.setTurn(request.getParameter("newTime"));
				session.setAttribute("user", player);
				pdao.updatePlayer(player);
			}
			if(enclosureM != null && enclosureM.equals("ok")){
				EnclosuresDAO edao = new EnclosuresDAO();
				int locate_x = (int) session.getAttribute("current_locate_x");
				int locate_y = (int) session.getAttribute("current_locate_y");
				EnclosureBean enclosure = edao.getEnclosureByLocation(locate_x, locate_y, player_id);
				EmployeesDAO emdao = new EmployeesDAO();
				List<EmployeeBean> employees = emdao.getEmployeesByEnclosure(enclosure.getId());
				AnimalsDAO adao = new AnimalsDAO();
				List<AnimalBean> weakAnimals = adao.getWeakestAnimals(enclosure.getId());
				for(EmployeeBean employee: employees){
					if(employee.getType().equals("healer")){
						int count = 0;
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
						int count = 0;
						if(enclosure.getCleanliness_gauge() < 99){
							enclosure.setCleanliness_gauge(enclosure.getCleanliness_gauge()+2);
						}
					}
				}
				for(AnimalBean animal: weakAnimals){
					animal.setHealth_gauge(animal.getHealth_gauge()-1);
					animal.setHungry_gauge(animal.getHungry_gauge()+1);
					adao.updateAnimal(animal);
				}
				enclosure.setCleanliness_gauge(enclosure.getCleanliness_gauge()-1);
				edao.updateEnclosure(enclosure);
				int cleanGauge = enclosure.getCleanliness_gauge();
				List<AnimalBean> animals = adao.getAnimalsByEnclosure(enclosure.getId());
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
