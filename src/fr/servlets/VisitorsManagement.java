package fr.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.beans.PlayerBean;
import fr.beans.VisitorBean;
import fr.dao.AnimalsDAO;
import fr.dao.PlayersDAO;
import fr.dao.VisitorsDAO;

@WebServlet("/visitorsManagement")
public class VisitorsManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public VisitorsManagement() {
        super();
     
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			String statGV = request.getParameter("statusGV");
			
			VisitorsDAO vdao = new VisitorsDAO();
			//Recuperation de la satisfaction global
			int satisfaction= (int) session.getAttribute("satisfaction");
			System.out.println("satisfaction "+satisfaction);
			if ((statGV != null) && statGV.equals("okGV")) {
				AnimalsDAO andao = new AnimalsDAO();
				PlayersDAO pdao = new PlayersDAO();
				int animalsQty = andao.countAnimalsByPlayer(player.getId());
				System.out.println("animalsQty " +animalsQty);
				int visitorsQty = satisfaction*(animalsQty/2);
				
				
				//Creation des visiteurs
				for(int count = 0; count <= visitorsQty; count++){
					
					VisitorBean visitor = new VisitorBean();
					visitor.setCoins(5000);
					visitor.setPlayer_id(player.getId());
					visitor.setSatisfaction_gauge(satisfaction);
					vdao.createVisitor(visitor);
				}
				//Mise a joueur dans la JSP
				session.setAttribute("visitors", visitorsQty);
				
				//Entrer d'argent pour le joueur
				long money= player.getMoney();
				player.setMoney(money + visitorsQty*100);
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				
			}
		}
	}

}
