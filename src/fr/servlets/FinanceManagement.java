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
import fr.beans.FinanceBean;
import fr.beans.PlayerBean;
import fr.dao.EnclosuresDAO;
import fr.dao.FinancesDAO;
import fr.dao.PlayersDAO;

@WebServlet("/financeManagement")
public class FinanceManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FinanceManagement() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			// redirection vers la JSP financeManagement
			this.getServletContext().getRequestDispatcher("/WEB-INF/financeManagement.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			String statGT = request.getParameter("statusGT");
			String statGFF = request.getParameter("statusGFF");
			System.out.println(statGT);
			
			if ((statGT != null) && statGT.equals("okGT")) {
				// creation de la liste des transactions
				FinancesDAO fdao = new FinancesDAO();
				String reponseJson = "{\"data\": [";
				int player_id = player.getId();
				
				List<FinanceBean> finances = fdao.getFinancesByPlayer(player_id);
				int lengthList = finances.size();
				int count = 0;
				
				for (FinanceBean finance : finances) {
					reponseJson += "{\"turn\":\"" + finance.getTurn() + "\",";
					reponseJson += "\"type_action\":\"" + finance.getType_action() + "\",";
					reponseJson += "\"libelle\":\"" + finance.getLibelle() + "\",";
					reponseJson += "\"animals\":\"" + finance.getLibelle().split(" ")[0] + "\",";
					reponseJson += "\"animals_number\":\"" + finance.getAnimals_number() + "\",";
					reponseJson += "\"somme\":\"" + finance.getSomme() + "\"}";
					count++;
					
					System.out.println(finance);
					System.out.println(reponseJson);
					
					if (count != lengthList) {
						reponseJson += ",";
					}
				}
				
				reponseJson += "] }";
				
				response.setCharacterEncoding("UTF-8");
				response.getWriter().append(reponseJson);
				
			} else if((statGFF != null) && statGFF.equals("okGFF")) {
				int player_id = player.getId();
				
				// recuperation du tour du player
				String turn = player.getTurn();
				
				// recuperation de l'id de l'enclos0
				EnclosuresDAO edao = new EnclosuresDAO();
				EnclosureBean enclos0 = new EnclosureBean();
				enclos0 = edao.getEnclosureByLocation(0, 0, player_id);
				
				// creation de la transaction à envoyer dans la bdd
				String type_action = request.getParameter("type_action");
				int somme = Integer.parseInt(request.getParameter("somme"));
				int payMonthly = Integer.parseInt(request.getParameter("payMonthly"));
				
				FinancesDAO fdao = new FinancesDAO();
				FinanceBean finance = new FinanceBean();
				finance.setType_action(type_action);
				finance.setSomme(somme);
				finance.setLibelle("money");
				finance.setTurn(turn);
				finance.setAnimals_number(0);
				finance.setPlayer_id(player_id);
				finance.setEnclosure_id(enclos0.getId());
				finance.setPayMonthly(payMonthly);
				
				fdao.createFinance(finance);
				
				// mise a jour du compte en banque
				int loan = Integer.parseInt(request.getParameter("loan"));
				long money = player.getMoney();
				
				player.setMoney( money + loan );
				
				session.setAttribute("user", player);
				PlayersDAO pdao = new PlayersDAO();
				pdao.updatePlayer(player);
				
				// creation de la reponse Json
				response.getWriter().append("{\"code\" : \"OK\"}");
			}
		}
	}
}
