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
			String statDV = request.getParameter("statusDV");
			String statNV = request.getParameter("statusNV");
			System.out.println("statNV " +statNV);
			VisitorsDAO vdao = new VisitorsDAO();
			//Recuperation de la satisfaction global
			int satisfaction= (int) session.getAttribute("satisfaction");
			
			if ((statGV != null) && statGV.equals("okGV")) {
				AnimalsDAO andao = new AnimalsDAO();
				PlayersDAO pdao = new PlayersDAO();
				int animalsQty = andao.countAnimalsByPlayer(player.getId());
				int visitorsQty = (int) Math.round((satisfaction/10)*(animalsQty/25));
				
				if(visitorsQty < 1){
					visitorsQty = 1;
				}
				
				int count = 0;
				//Creation des visiteurs
				VisitorBean visitor = new VisitorBean();
				visitor.setCoins(5000);
				visitor.setPlayer_id(player.getId());
				visitor.setSatisfaction_gauge(satisfaction);
				
				for(count = 0; count < visitorsQty; count++){		
					vdao.createVisitor(visitor);	
				}
				//Entrer d'argent pour le joueur
				long money= player.getMoney();
				player.setMoney(money + visitorsQty*10000);
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				
				money= player.getMoney();
				
				System.out.println("player "+player);
				int visitors = vdao.countVisitors(player.getId());
				
				int visitorsA= visitors*100;
				//MAJ visiteurs dans la JSP
				session.setAttribute("visitors", visitorsA);
				
				String responseJson = "{\"visitors\":" + visitorsA+",";
				responseJson += "\"money\":" + money+"}";
				
				response.getWriter().append(responseJson);
			} else if ((statDV != null) && statDV.equals("okDV")) {
				System.out.println("journée visiteurs");
			} else if ((statNV != null) && statNV.equals("okNV")) {
				System.out.println("delete visiteurs");	
				vdao.deleteVisitor(player.getId());
				
				int visitorsA= 0;
				//MAJ visiteurs dans la JSP
				session.setAttribute("visitors", visitorsA);
				
				String responseJson = "{\"visitors\":" + visitorsA+"}";
				response.getWriter().append(responseJson);
			}
		}
	}

}
