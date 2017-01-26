package fr.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.beans.PlayerBean;
import fr.utility.EmployeesManagementUtil;

/**
 * Servlet implementation class EmployeesManagement
 */
@WebServlet("/employeesManagement")
public class EmployeesManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public EmployeesManagement() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperation de la session actuelle et du joueur connecte
				HttpSession session = request.getSession(false);
				PlayerBean player = (PlayerBean) session.getAttribute("user");

				if (session != null && player != null) {
					// Puis redirection vers la JSP buildEnclosure
					this.getServletContext().getRequestDispatcher("/WEB-INF/employeesManagement.jsp").forward(request, response);
				}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			String statSEA = request.getParameter("statusSEA");
			String statEmP = request.getParameter("statusEmP");
			String statSEQ = request.getParameter("statusSEQ");
			String statEmED = request.getParameter("statusEmED");
		
			/**Permettre l'affichage des employees du joueurs**/
			if ((statSEA != null) && statSEA.equals("okSEA")) {
				EmployeesManagementUtil.showEmployees(request, response);
			} /**fixer les valeurs Min Max des input number**/
			else if ((statSEQ != null) && statSEQ.equals("okSEQ")) {
				EmployeesManagementUtil.setMinMax(request, response);
			}/**Permettre l'affichage des prix de recrutement**/
			else if ((statEmP != null) && statEmP.equals("okEmP")) {
				EmployeesManagementUtil.showPrice(request, response);
			}/**Permettre recrutement / licenciement**/
			else if ((statEmED != null) && statEmED.equals("okEmED")) {
				EmployeesManagementUtil.engageDismiss(request, response);
			}
		}
	}

}
