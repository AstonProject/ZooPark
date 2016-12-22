package fr.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.dao.CostsDAO;
import fr.dao.EnclosuresDAO;
import fr.dao.PlayersDAO;


@WebServlet("/createEnclosure")
public class BuildEnclosureMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Recuperation des coordonnées de l'enclos à construire depuis la Home.jsp
		String locate_x=null;
		String locate_y=null;
		locate_x = request.getParameter("x");
		locate_y = request.getParameter("y");
		
		//Envoie des coordonnées à la jsp BuildEnclosure
		if(locate_x  != null && locate_y != null){
		request.setAttribute( "locate_x", locate_x );
		request.setAttribute( "locate_y", locate_y );
		this.getServletContext().getRequestDispatcher("/WEB-INF/buildEnclosure.jsp").forward(request, response);
		//int locat_x = Integer.parseInt(x);
		//int locat_y = Integer.parseInt(y);
		}
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recuperation des prix d'enclos depuis la CostsDAO
		CostsDAO cdao = new CostsDAO();
		JSONObject prices = cdao.getCosts();
		
		// Envoie des prix dans la réponse pour être recupéré dans la fonction showPrice (buildEnclosure.js) 
		if( prices != null){
		response.setContentType("application/json");
		response.getWriter().append(prices.toString());
		}
		
		//Creation d'un nouvel enclos et update du player
		HttpSession session = request.getSession();
		EnclosuresDAO edao= new EnclosuresDAO();
		PlayersDAO pdao = new PlayersDAO();
		EnclosureBean enclosure = new EnclosureBean();
		//Recuperation des données du joueurs enregistrées dans la session
		PlayerBean player = (PlayerBean) session.getAttribute("user");
		int money= player.getMoney();
		
		
		
		
	}
}