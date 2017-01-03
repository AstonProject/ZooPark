package fr.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.dao.EnclosuresDAO;


@WebServlet("/enclosureManagment")
public class EnclosureManagment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EnclosureManagment() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					this.getServletContext().getRequestDispatcher("/WEB-INF/enclosureManagement.jsp").forward(request, response);
				}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			// Recuperation de donnees enregistrees dans la session:
			// - les coordonnees d'enclos(ce doGet)
			int locate_x = (int) session.getAttribute("current_locate_x");
			int locate_y = (int) session.getAttribute("current_locate_y");
			int player_id= player.getId();
			
			EnclosuresDAO edao = new EnclosuresDAO();
			EnclosureBean enclosure = new EnclosureBean();
			
			enclosure= edao.getEnclosureByLocation(locate_x, locate_y, player_id);
			System.out.println(enclosure);
			
			// Recuperation des descriptions au format Json pour envoie par
			// ajax
			

			String reponseJson = "{\"specie_id \":" + enclosure.getSpecie_id() +",";
			reponseJson += "\"capacity \":" + enclosure.getCapacity() +",";
			reponseJson += "\"animal_quantiy \":" + enclosure.getAnimal_quantity()+"}";

			System.out.println(reponseJson);

			response.getWriter().append(reponseJson);
			
			
		}
	}

}
