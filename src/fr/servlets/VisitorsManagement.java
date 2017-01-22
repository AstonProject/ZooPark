package fr.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.beans.PlayerBean;
import fr.dao.VisitorsDAO;

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
			String statGST = request.getParameter("statusGST");
			
			System.out.println("statGST "+statGST);
			if ((statGST != null) && statGST.equals("okGST")) {
				VisitorsDAO vdao = new VisitorsDAO();
				int visitorsQty = vdao.countVisitors(player.getId());
				System.out.println("visitorsQty"+ visitorsQty);
			}
		}
	}

}
