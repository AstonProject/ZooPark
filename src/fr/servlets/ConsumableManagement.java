package fr.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import fr.beans.ConsumablesBean;
import fr.beans.EnclosureBean;
import fr.beans.FinanceBean;
import fr.beans.PlayerBean;
import fr.dao.ConsumableDAO;
import fr.dao.CostsDAO;
import fr.dao.EnclosuresDAO;
import fr.dao.FinancesDAO;
import fr.dao.PlayersDAO;


@WebServlet("/consumableManagement")
public class ConsumableManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ConsumableManagement() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recuperation de la session actuelle et du joueur connecte
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			// Puis redirection vers la JSP consumableManagement
			this.getServletContext().getRequestDispatcher("/WEB-INF/consumableManagement.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			String statSCS = request.getParameter("statusSCS");
			String statSCP = request.getParameter("statusSCP");
			String statGCF = request.getParameter("statusGCF");
			
			// affichage des consommables en stock
			if ((statSCS != null) && statSCS.equals("okSCS")) {
				
				// recuperation des consommables en stock
				ConsumableDAO cdao = new ConsumableDAO();
				List<ConsumablesBean> consumables = new ArrayList<>();
				
				consumables = cdao.getConsumablesByPlayer(player.getId());
				
				System.out.println(consumables);
				
				// definition des stocks
				int countMeat_stock = 0;
				int countFish_stock = 0;
				int countStrawBale_stock = 0;
				
				// recuperation des quantites par nom
				for (ConsumablesBean consumable : consumables) {
					if(consumable.getName().equals("meat")) {
						countMeat_stock = consumable.getQuantity();
					} else if (consumable.getName().equals("fish")) {
						countFish_stock = consumable.getQuantity();
					} else if (consumable.getName().equals("straw_bale")) {
						countStrawBale_stock = consumable.getQuantity();
					}
				}
				
				System.out.println("meat : " + countMeat_stock + ", fish : " + countFish_stock + ", straw_bale : " + countStrawBale_stock);
				
				//Mettre au format Json
				String reponseJson = "{\"countMeatStock\": " + countMeat_stock + ", ";
				reponseJson += "\"countFishStock\": " + countFish_stock + ", ";
				reponseJson += "\"countStrawBaleStock\": " + countStrawBale_stock + "}";
				
				System.out.println(reponseJson);
				
				//Envoie dans la reponse pour recuperation 
				response.getWriter().append(reponseJson);
				
			// permettre l'affichage du prix
			} else if ((statSCP != null) && statSCP.equals("okSCP")) {
				// Recuperation des prix via CostsDAO dans un objet Json
				CostsDAO cdao = new CostsDAO();
				JSONObject prices = cdao.getCosts();

				// Envoie des prix dans la reponse pour etre recupere dans la fonction showConsumablePrice()
				response.setContentType("application/json");
				response.getWriter().append(prices.toString());
				
			// permettre l'achat et la vente
			} else if ((statGCF != null) && statGCF.equals("okGCF")) {
				EnclosuresDAO ecdao = new EnclosuresDAO();
				EnclosureBean e0 =ecdao.getEnclosureByLocation(0, 0, player.getId());
				
				int priceConsumable = Integer.parseInt(request.getParameter("priceConsumable"));
				int meatQuantity = Integer.parseInt(request.getParameter("meatQuantity"));
				int fishQuantity = Integer.parseInt(request.getParameter("fishQuantity"));
				int strawBaleQuantity = Integer.parseInt(request.getParameter("strawBaleQuantity"));
				int quantityConsumable = 0;
				
				System.out.println("add meat : " + meatQuantity + ", add fish : " + fishQuantity + ", add straw_bale : " + strawBaleQuantity);
				
				// MAJ des stocks du joueur
				int player_id = player.getId();
				
				// recuperation des consommables
				ConsumableDAO cdao = new ConsumableDAO();
				List<ConsumablesBean> consumables = new ArrayList<>();
				
				consumables = cdao.getConsumablesByPlayer(player.getId());
				
				System.out.println(consumables);
				
				// recuperation des quantites par nom
				for (ConsumablesBean consumable : consumables) {
					
					consumable.setPlayer_id(player_id);
					
					if(consumable.getName().equals("meat")) {
						int stockMeat = consumable.getQuantity();
						consumable.setQuantity(stockMeat + meatQuantity);
						quantityConsumable += meatQuantity;
					} else if(consumable.getName().equals("fish")) {
						int stockFish = consumable.getQuantity();
						consumable.setQuantity(stockFish + fishQuantity);
						quantityConsumable += fishQuantity;
					}else if(consumable.getName().equals("straw_bale")) {
						int stockStrawBale = consumable.getQuantity();
						consumable.setQuantity(stockStrawBale + strawBaleQuantity);
						quantityConsumable += strawBaleQuantity;
					}
					
					cdao.updateConsumable(consumable);
				}
				
				// MAJ du solde joueur
				PlayersDAO pdao = new PlayersDAO();
				long money = player.getMoney();
				
				String action = "";
				long sum = 0;
				
				if (priceConsumable < 0) {
					action = "purchase";
					sum = priceConsumable;
				} else if  (priceConsumable > 0) {
					action = "sale";
					sum = (long)(priceConsumable * (-1));
				}
				
				player.setMoney(money + sum);
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				System.out.println("money: "+ money);
				System.out.println("playerAUP: "+ player);
				
				// preparation et envoi de la transaction
				FinancesDAO fdao = new FinancesDAO();
				FinanceBean finance = new FinanceBean();

				if (priceConsumable != 0) {
					
					finance.setType_action(action);
					finance.setSomme(Math.abs(sum));
					finance.setLibelle("consumable");
					finance.setTurn(player.getTurn());
					finance.setAnimals_number(Math.abs(quantityConsumable));
					finance.setPlayer_id(player.getId());
					finance.setEnclosure_id(e0.getId());
					finance.setPayMonthly(0);
					
					fdao.createFinance(finance);
				}
				
				//Permettre la redirection sur 'home'
				response.getWriter().append("{\"code\" : \"OK\"}");
				
			}
		}
	}
}
