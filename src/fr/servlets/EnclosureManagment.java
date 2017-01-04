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

import fr.beans.EmployeeBean;
import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.dao.EmployeesDAO;
import fr.dao.EnclosuresDAO;

@WebServlet("/enclosureManagment")
public class EnclosureManagment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EnclosureManagment() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recuperation de la session actuelle et du joueur connecte
		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {
			// Recuperation des coordonneees de l'enclos a construire depuis la
			// Home.jsp
			// et enregistrement dans la session
			int locate_x = Integer.parseInt(request.getParameter("x"));
			int locate_y = Integer.parseInt(request.getParameter("y"));

			session.setAttribute("current_locate_x", locate_x);
			session.setAttribute("current_locate_y", locate_y);

			// Puis redirection vers la JSP buildEnclosure
			this.getServletContext().getRequestDispatcher("/WEB-INF/enclosureManagement.jsp").forward(request,
					response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		PlayerBean player = (PlayerBean) session.getAttribute("user");

		if (session != null && player != null) {

			String statSA = request.getParameter("statusSA");
			String statSE = request.getParameter("statusSE");

			// Recuperation de donnees enregistrees dans la session:
			// - les coordonnees d'enclos(ce doGet)
			int locate_x = (int) session.getAttribute("current_locate_x");
			int locate_y = (int) session.getAttribute("current_locate_y");
			int player_id = player.getId();

			EnclosuresDAO ecdao = new EnclosuresDAO();
			EnclosureBean enclosure = new EnclosureBean();

			enclosure = ecdao.getEnclosureByLocation(locate_x, locate_y, player_id);
			System.out.println(enclosure);

			if ((statSA != null) && statSA.equals("okSA")) {
				// Recuperation des descriptions au format Json pour envoie par
				// ajax

				String reponseJson = "{\"specie_id\":" + enclosure.getSpecie_id() + ",";
				reponseJson += "\"capacity\":" + enclosure.getCapacity() + ",";
				reponseJson += "\"animal_quantity\":" + enclosure.getAnimal_quantity() + "}";

				System.out.println(reponseJson);

				response.getWriter().append(reponseJson);
			} else if ((statSE != null) && statSE.equals("okSE")) {
				// Recuperation de l'id de l'enclos selectionne
				int enclosure_id = enclosure.getId();

				EmployeesDAO epdao = new EmployeesDAO();
				List<EmployeeBean> employees = new ArrayList<EmployeeBean>();

				employees = epdao.getEmployeesByEnclosure(enclosure_id);

				// Recuperation des employes au format Json pour envoie par
				// ajax
				String reponseJson = "{";
				int lengthList = employees.size();
				int count = 0;

				if (employees.size() > 0) {
					for (EmployeeBean employee : employees) {
						String type = employee.getType();

						reponseJson += "\"type" + count + "\":\"" + type + "\"";
						count++;

						if (count != lengthList) {
							reponseJson += ",";
						}
					}
					reponseJson += "}";

					System.out.println(reponseJson);

					response.getWriter().append(reponseJson);
				}
			}

		}
	}

}
