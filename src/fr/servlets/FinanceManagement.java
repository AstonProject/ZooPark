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

import fr.beans.FinanceBean;
import fr.beans.PlayerBean;
import fr.dao.FinancesDAO;

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
			System.out.println(statGT);
			
			if ((statGT != null) && statGT.equals("okGT")) {
				// creation de la liste des transactions
				FinancesDAO fdao = new FinancesDAO();
				String reponseJson = "{\"data\": [";
				int player_id = player.getId();
				Integer lastIdFinance = (Integer) session.getAttribute("lastIdFinance");
				
				if (lastIdFinance == null) {
					lastIdFinance = 0;
				}
				
				List<FinanceBean> finances = fdao.getFinancesFromIdByPlayer(lastIdFinance, player_id);
				int lengthList = finances.size();
				int count = 0;
				
				for (FinanceBean finance : finances) {
					reponseJson += "{\"date\":\"" + finance.getDate() + "\",";
					reponseJson += "\"type_action\":\"" + finance.getType_action() + "\",";
					reponseJson += "\"libelle\":\"" + finance.getLibelle() + "\",";
					reponseJson += "\"somme\":\"" + finance.getSomme() + "\"}";
					count++;
					
					System.out.println(finance);
					System.out.println(reponseJson);
					
					if (count != lengthList) {
						reponseJson += ",";
					} else {
						session.setAttribute("lastIdFinance", finance.getId());
					}
				}
				
				reponseJson += "] }";
				
				response.setCharacterEncoding("UTF-8");
				response.getWriter().append(reponseJson);
			}
		}
	}
}
