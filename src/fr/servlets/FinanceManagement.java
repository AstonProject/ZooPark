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
			String statSCL = request.getParameter("statusSCL");
			String statRLM = request.getParameter("statusRLM");
			
			// affichage des valeurs en stock
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
					
					//System.out.println(finance);
					//System.out.println(reponseJson);
					
					if (count != lengthList) {
						reponseJson += ",";
					}
				}
				
				reponseJson += "] }";
				
				response.setCharacterEncoding("UTF-8");
				response.getWriter().append(reponseJson);
				
			// validation de l'achat/vente de consommables
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
			
			// affichage de la somme totale des prets a rembourser
			} else if((statSCL != null) && statSCL.equals("okSCL")) {
				int player_id = player.getId();
				
				// creation d'une liste contenant tous les prets non rembourses
				FinancesDAO cdao = new FinancesDAO();
				List<FinanceBean> finances = new ArrayList<>();
				
				finances = cdao.getLoansByPlayer(player_id);
				
				System.out.println(finances);
				
				// preparation de la somme totale a afficher
				int current_loans_value = 0;
				int count = 0;
				
				for (FinanceBean finance : finances) {
					current_loans_value += finance.getSomme();
					count++;
				}
				
				System.out.println("number of loans : " + count + " ,current loans value : " + current_loans_value);
				
				// Mettre au format Json
				String reponseJson = "{\"currentLoansValue\": " + current_loans_value + "}";
				
				System.out.println(reponseJson);
				response.getWriter().append(reponseJson);
		
			// remboursement des differents prets
			} else if((statRLM != null) && statRLM.equals("okRLM")) {
				// recuperation des infos du joueur
				int player_id = player.getId();
				String turn = player.getTurn();
				
				EnclosuresDAO edao = new EnclosuresDAO();
				EnclosureBean enclos0 = new EnclosureBean();
				enclos0 = edao.getEnclosureByLocation(0, 0, player_id);
				
				// creation d'une liste contenant tous les prets non rembourses
				FinancesDAO fdao = new FinancesDAO();
				List<FinanceBean> finances = new ArrayList<>();
			
				finances = fdao.getLoansByPlayer(player_id);
			
				System.out.println(finances);
			
				// preparation de la somme totale a afficher
				int current_payMonthly = 0;
				int count = 0;
			
				for (FinanceBean finance : finances) {
					current_payMonthly += finance.getPayMonthly();
					count++;
				}
				
				System.out.println("number of loans : " + count + " ,mensualite : " + current_payMonthly);
				
				// recuperation du jour
				String jour = player.getTurn().split(",")[1];
				
				// verification du jour
				if (jour.equals("1")) {
					// prelevement le premier du mois
					long money = player.getMoney();
					player.setMoney(money - current_payMonthly);
					
					// mise à jour de la bdd
					for (FinanceBean finance : finances) {
						
						System.out.println("avant remboursement : " + finance);
						
						long emprunt = finance.getSomme();
						long mensualite = finance.getPayMonthly();
						finance.setSomme(emprunt - mensualite);
						
						fdao.updateFinances(finance);
						System.out.println("apres remboursement : " + finance);
						
						// suppression de l'emprunt si remboursement integral
						if (finance.getSomme() == 0) {
							int ligne = finance.getId();
							
							fdao.deleteLoan(ligne);
						}
					}
						
					// envoi de la transaction dans la bdd 
					FinanceBean transaction = new FinanceBean();
					transaction.setType_action("refund");
					transaction.setSomme(current_payMonthly);
					transaction.setLibelle("loan");
					transaction.setTurn(turn);
					transaction.setAnimals_number(count);
					transaction.setPlayer_id(player_id);
					transaction.setEnclosure_id(enclos0.getId());
					transaction.setPayMonthly(0);
						
					fdao.createFinance(transaction);
					
					System.out.println("remboursements : " + transaction);
				
				// mise a jour de la session joueur
				session.setAttribute("user", player);
				PlayersDAO pdao = new PlayersDAO();
				pdao.updatePlayer(player);
				
				}
			}
		}
	}
}
