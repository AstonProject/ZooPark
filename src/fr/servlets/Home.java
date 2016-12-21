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
import fr.beans.PlayerBean;
import fr.dao.EnclosuresDAO;

@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Home() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PlayerBean user = (PlayerBean) session.getAttribute("user");
		if(user != null)
		{
			List<EnclosureBean> enclos = null;
			EnclosureBean[][] constructions = new EnclosureBean[5][5];
			EnclosuresDAO edao = new EnclosuresDAO();
			enclos = edao.getAllEnclosures(user.getId());
			int cpt = 1;
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 5; j++){
					constructions[i][j] = enclos.get(cpt);
					cpt++;
				}
			}
			session.setAttribute("construction", constructions);
		}
		this.getServletContext()
			.getRequestDispatcher("/WEB-INF/home.jsp")
			.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
