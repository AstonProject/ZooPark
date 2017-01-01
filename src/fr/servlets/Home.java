package fr.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.dao.EnclosuresDAO;


@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Home() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recuperation de la session existante, si il y'en a pas elle est cree
		HttpSession session = request.getSession();
		//Recupere l'utilisateur en session si il y en a un
		PlayerBean user = (PlayerBean) session.getAttribute("user");
		
		if(user != null){  //Initialisation des 25 emplacements d'enclos disponibles pour le joueur
			List<EnclosureBean> enclos = null;
			EnclosureBean[][] constructions = new EnclosureBean[5][5];
			EnclosuresDAO edao = new EnclosuresDAO();
			//Recuperation des 25 enclos du joueur crees lors de l'inscription de celui-ci
			enclos = edao.getAllEnclosures(user.getId());
			int cpt = 0;
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 5; j++){
					constructions[i][j] = enclos.get(cpt);
					cpt++;
				}
			}
			//Les enclos recuperes de la bdd sont enregistres en session 
			//pour etre utilises dans la jsp Home
			session.setAttribute("construction", constructions);
		}
		this.getServletContext()
			.getRequestDispatcher("/WEB-INF/home.jsp")
			.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Recuperation de la session existante, si il y'en a pas elle est cree
		HttpSession session = request.getSession();
		//Recupere l'utilisateur en session si il y en a un
		PlayerBean user = (PlayerBean) session.getAttribute("user");
		
		if(user != null){  //Recuperation des 25 enclos du joueur via EnclosureDAO
			List<EnclosureBean> enclosures = null;
			EnclosuresDAO edao = new EnclosuresDAO();
			//Recuperation des 25 enclos du joueur crees lors de l'inscription de celui-ci
			enclosures = edao.getAllEnclosures(user.getId());
			System.out.println("enclosures " + enclosures);
			String reponseJson = "{"; 
			int lengthList = enclosures.size();
			int count = 0;
			
			for (EnclosureBean enclosure : enclosures) {
				reponseJson += "\"data"+ enclosure.getLocate_x()+enclosure.getLocate_y()+ "\": [";
				reponseJson += "{\"specie_id\":" + enclosure.getSpecie_id() + ",";
				reponseJson += "\"capacity\":" + enclosure.getCapacity() + ",";
				reponseJson += "\"locate_x\":" + enclosure.getLocate_x() + ",";
				reponseJson += "\"locate_y\":" + enclosure.getLocate_y() + "}";
				reponseJson += "]";
				count++;
				 
				if (count != lengthList) {
					reponseJson += ",";
				}
			}
			reponseJson += "}";
			System.out.println(reponseJson);
			response.getWriter().append(reponseJson);
			
		}
	}

}
