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
import fr.beans.PlayerBean;
import fr.dao.ConsumableDAO;
import fr.dao.CostsDAO;
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
			String statSCQ = request.getParameter("statusSCQ");
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
					} else if(consumable.getName().equals("fish")) {
						countFish_stock = consumable.getQuantity();
					}else if(consumable.getName().equals("straw_bale")) {
						countStrawBale_stock = consumable.getQuantity();
					}
				}
				
				/*// incrementation du compteur
				for (ConsumablesBean consumable : consumables) {
					if(consumable.getName().equals("meat")) {
						countMeat_stock += 1;
					}
					else if(consumable.getName().equals("fish")) {
						countFish_stock += 1;
					}
					else if(consumable.getName().equals("straw_bale")) {
						countStrawBale_stock += 1;
					}
				}*/
				
				System.out.println("meat : " + countMeat_stock + ", fish : " + countFish_stock + ", straw_bale : " + countStrawBale_stock);
				
				//Mettre au format Json
				String reponseJson = "{\"countMeatStock\": " + countMeat_stock + ", ";
				reponseJson += "\"countFishStock\": " + countFish_stock + ", ";
				reponseJson += "\"countStrawBaleStock\": " + countStrawBale_stock + "}";
				
				System.out.println(reponseJson);
				
				//Envoie dans la reponse pour recuperation 
				response.getWriter().append(reponseJson);
				
				
				
			/* // fixation de la valeur minimale pour revente
			} else if ((statSCQ != null) && statSCQ.equals("okSCQ")) {
				ConsumableDAO cdao = new ConsumableDAO();
				List<ConsumablesBean> consumables =new ArrayList<>();
				
				consumables = cdao.getConsumablesByPlayer(player.getId());
				
				int maxQty= e0.getEmployee_slot();
				int employeesQty= employees.size();
				
				String responseJson = "{\"maxQty\":" + maxQty +",\"employeesQty\":"+employeesQty+"}";
				response.getWriter().append(responseJson); */
				
			/*// permettre l'affichage du prix
			} else if ((statSCP != null) && statSCP.equals("okSCP")) {
				// Recuperation des prix via CostsDAO dans un objet Json
				CostsDAO cdao = new CostsDAO();
				JSONObject prices = cdao.getCosts();

				// Envoie des prix dans la reponse pour etre recupere dans la fonction showConsumablePrice()
				response.setContentType("application/json");
				response.getWriter().append(prices.toString()); */
				
			/*// permettre l'achat et la vente
			} else if ((statGCF != null) && statGCF.equals("okGCF")) {
				int priceConsumable = Integer.parseInt(request.getParameter("priceConsumable"));
				int meatQuantity = Integer.parseInt(request.getParameter("meatQuantity"));
				int fishQuantity = Integer.parseInt(request.getParameter("fishQuantity"));
				int strawBaleQuantity = Integer.parseInt(request.getParameter("strawBaleQuantity"));
				
				// MAJ des stocks du joueur
				int player_id = player.getId();
				
				ConsumableDAO cdao = new ConsumableDAO();
				ConsumablesBean consumable = new ConsumablesBean();
				consumable.setPlayer_id(player_id);
				
				for (int i=1; i<4; i++) {
					if (i == 1) {
						consumable.setName("meat");
						consumable.setQuantity(meatQuantity);
					} else if (i == 2) {
						consumable.setName("fish");
						consumable.setQuantity(fishQuantity);
					} else if (i == 3) {
						consumable.setName("straw_bale");
						consumable.setQuantity(strawBaleQuantity);
					}
					
					cdao.updateQuantityByNameAndPlayer(consumable);
				}
				
				// MAJ du solde joueur
				PlayersDAO pdao = new PlayersDAO();
				long money = player.getMoney();
				player.setMoney(money + priceConsumable);
				pdao.updatePlayer(player);
				session.setAttribute("user", player);
				System.out.println("money: "+ money);
				System.out.println("playerAUP: "+ player); */
				
			}
			
			//Permettre la redirection sur 'home'
			//response.getWriter().append("{\"code\" : \"OK\"}");
			
		}
	}
}
