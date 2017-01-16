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
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			
			FinancesDAO fdao = new FinancesDAO();
			List<FinanceBean> finances = new ArrayList<>();
			
			finances = fdao.getFinancesByPlayer(player.getId());
			
			request.setAttribute("finances", finances);
			// redirection vers la JSP financeManagement
			this.getServletContext().getRequestDispatcher("/WEB-INF/financeManagement.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			
			long current_money = player.getMoney();
			
			//recuperation de la valeur du pret
			//???
			
			// player.setMoney(current_money + /*valleur du pret */);
			
		}
	}

}
