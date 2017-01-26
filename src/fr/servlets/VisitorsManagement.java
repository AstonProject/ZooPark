package fr.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.beans.PlayerBean;
import fr.utility.VisitorsManagementUtil;

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

			/**Generer des visiteurs**/
			if ((statGV != null) && statGV.equals("okGV")) {
				VisitorsManagementUtil.generateVisitors(request, response);
			} /** Gerer la journée des visiteurs**/
			else if ((statDV != null) && statDV.equals("okDV")) {
				VisitorsManagementUtil.visitorsDay(request, response);
			} /** Depart des visiteurs la nuit**/
			else if ((statNV != null) && statNV.equals("okNV")) {	
				VisitorsManagementUtil.deleteVisitors(request, response);
			}
		}
	}

}
