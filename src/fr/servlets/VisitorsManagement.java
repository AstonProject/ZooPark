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
import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.beans.VisitorBean;
import fr.dao.AnimalsDAO;
import fr.dao.EnclosuresDAO;
import fr.dao.PlayersDAO;
import fr.dao.VisitorsDAO;
import fr.utility.CalculateSatisfaction;

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
			
			VisitorsDAO vdao = new VisitorsDAO();
			EnclosuresDAO edao = new EnclosuresDAO();
			PlayersDAO pdao = new PlayersDAO();
			int satisfaction = edao.getSatisfaction(player.getId());
			
			if ((statGV != null) && statGV.equals("okGV")) {
				AnimalsDAO andao = new AnimalsDAO();
				
				int animalsQty = andao.countAnimalsByPlayer(player.getId());
				int visitorsQty = (int) Math.round((satisfaction + animalsQty)/10);
				
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
				int visitors = vdao.countVisitors(player.getId());
				
				int visitorsA= visitors*100;
				//MAJ visiteurs dans la JSP
				session.setAttribute("visitors", visitorsA);
				
				String responseJson = "{\"visitors\":" + visitorsA+",";
				responseJson += "\"money\":" + money+"}";
				
				response.getWriter().append(responseJson);
			} else if ((statDV != null) && statDV.equals("okDV")) {
				EnclosureBean e0 =edao.getEnclosureByLocation(0, 0, player.getId());
				//MAJ satisfaction des visiteurs
				double satisfactionG =  CalculateSatisfaction.calcSatisfaction(request);	
				int totalS=0;
				int globalS=0;
				List<VisitorBean> visitors = (vdao.getVisitorsByPlayerId(player.getId()));
				int coins=0;
				for(VisitorBean visitor: visitors){
					int variation = -10 + (int) (Math.random() * ((10 - (-10))));
					totalS= (int) (satisfactionG + (satisfactionG*variation/100));
					globalS +=totalS;
					visitor.setSatisfaction_gauge(totalS);
					//si la satisfaction est seprieure 50, le visiteur depense de l'argent
					if(totalS > 50 && visitor.getCoins()>0){
						visitor.setCoins(visitor.getCoins() - 1000);
						coins +=1000;
					}		
					vdao.updateVisitor(visitor);
				}
				//Maj de la satisfaction globale
				globalS = globalS / visitors.size();
			
				e0.setCapacity(globalS);
				edao.updateEnclosure(e0);
				session.setAttribute("satisfaction", globalS);
				
				
				
				//Depenses des visiteurs dans le zoo
				player.setMoney(player.getMoney() + coins);
				pdao.updatePlayer(player);
				session.setAttribute("user", player);	
			
				String responseJson = "{\"satisfaction\":" + globalS+",";
				responseJson += "\"money\":" + player.getMoney()+"}";
				response.getWriter().append(responseJson);
			} else if ((statNV != null) && statNV.equals("okNV")) {	
				vdao.deleteVisitors(player.getId());
				
				int visitorsA= 0;
				//MAJ visiteurs dans la JSP
				session.setAttribute("visitors", visitorsA);
				
				String responseJson = "{\"visitors\":" + visitorsA+"}";
				response.getWriter().append(responseJson);
			}
		}
	}

}
