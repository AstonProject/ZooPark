package fr.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.beans.PlayerBean;
import fr.dao.PlayersDAO;

@WebServlet("/newturn")
public class TurnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TurnServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperation de la session actuelle et du joueur connecte
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");
		PlayersDAO pdao = new PlayersDAO();
		if(!request.getParameter("newTime").equals(null)){
			player.setTurn(request.getParameter("newTime"));
			session.setAttribute("user", player);
			pdao.updatePlayer(player);
		}
		
	}

}
