package fr.utility;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.beans.VisitorBean;
import fr.dao.AnimalsDAO;
import fr.dao.EnclosuresDAO;
import fr.dao.PlayersDAO;
import fr.dao.VisitorsDAO;

public class VisitorsManagementUtil {

	/**Generer des visiteurs**/
	static public void generateVisitors(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");
		VisitorsDAO vdao = new VisitorsDAO();
		EnclosuresDAO edao = new EnclosuresDAO();
		PlayersDAO pdao = new PlayersDAO();
		int satisfaction = edao.getSatisfaction(player.getId());
		
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
	}
	
	/** Gerer la journée des visiteurs**/
	static public void visitorsDay(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");
		VisitorsDAO vdao = new VisitorsDAO();
		EnclosuresDAO edao = new EnclosuresDAO();
		PlayersDAO pdao = new PlayersDAO();
		
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
			//si la satisfaction est suffisante, le visiteur depense de l'argent
			if(totalS > 90 && visitor.getCoins()>999){
				visitor.setCoins(visitor.getCoins() - 1000);
				coins +=1000;
			} else if(totalS > 50 && visitor.getCoins()>499){
				visitor.setCoins(visitor.getCoins() - 500);
				coins +=500;
				
			}	else if(totalS > 25 && visitor.getCoins()>249){
				visitor.setCoins(visitor.getCoins() - 250);
				coins +=250;
			}			
			vdao.updateVisitor(visitor);
		}
		//Maj de la satisfaction globale
		if(visitors.size()>0){
			globalS = globalS / visitors.size();
			e0.setCapacity(globalS);
			edao.updateEnclosure(e0);
			session.setAttribute("satisfaction", globalS);
		}
		
		//Depenses des visiteurs dans le zoo
		player.setMoney(player.getMoney() + coins);
		pdao.updatePlayer(player);
		session.setAttribute("user", player);	
	
		String responseJson = "{\"satisfaction\":" + globalS+",";
		responseJson += "\"money\":" + player.getMoney()+"}";
		response.getWriter().append(responseJson);
	}
	
	/** Depart des visiteurs la nuit**/
	static public void deleteVisitors(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");
		VisitorsDAO vdao = new VisitorsDAO();
		
		vdao.deleteVisitors(player.getId());
		
		int visitorsA= 0;
		//MAJ visiteurs dans la JSP
		session.setAttribute("visitors", visitorsA);
		
		String responseJson = "{\"visitors\":" + visitorsA+"}";
		response.getWriter().append(responseJson);
	}
}
