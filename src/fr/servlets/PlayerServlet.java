package fr.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.dao.EnclosuresDAO;
import fr.dao.PlayersDAO;
import fr.utility.ValidationDonnees;
import sun.security.krb5.internal.EncAPRepPart;

@WebServlet("/user")
public class PlayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE = "/WEB-INF/home.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("disconnect")) {
		
			HttpSession session = request.getSession();
			
			if (session != null) {
				session.removeAttribute("user");
				session.invalidate();
				RequestDispatcher rs = request.getRequestDispatcher( VUE );
				rs.include(request, response);
			}
		}
	}
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action.equals("register")) {
			
			// recuperer les donnees du formulaire
			String pseudo = request.getParameter("reg_pseudo");
			String password = request.getParameter("reg_password");
			String confirmation = request.getParameter("reg_confirmation");
			String email = request.getParameter("reg_email");
			
			ValidationDonnees valid = new ValidationDonnees(request);
			
			PlayerBean player = valid.recupDonnees(pseudo, password, confirmation, email);
			
			System.out.println(player);
			
			HttpSession session = request.getSession(false);

			if (player != null) {
				
				session = request.getSession(true);
				
				PlayersDAO objPlayer = new PlayersDAO();
				objPlayer.createPlayer(player);
				
				try {
					player = objPlayer.getByPassword(player.getPseudo(), player.getPassword());
					int player_id = player.getId();
					
					EnclosureBean enclosure = new EnclosureBean();
					enclosure.setPlayer_id(player_id);
					
					EnclosuresDAO objEnclos = new EnclosuresDAO();
					
					for (int x=1; x<6; x++) {
						for (int y=1; y<6; y++) {
							
							enclosure.setLocate_x(x);
							enclosure.setLocate_y(y);
							
							objEnclos.initEnclosure(enclosure);
						}
					}
				
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				session.setAttribute("user", player);
				
				RequestDispatcher rs = request.getRequestDispatcher( VUE );
				rs.forward(request, response);
			} else {
				
				request.setAttribute("valid", valid);
				request.setAttribute("user", player);
				
				this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
			}
			
		} else if (action.equals("connect")) {
			
			String pseudo = request.getParameter("con_pseudo");
			String password = request.getParameter("con_password");
			request.setAttribute("pseudo", pseudo);
			
			PlayersDAO objPlayer = new PlayersDAO();
			PlayerBean player = new PlayerBean();
			
			response.addHeader("Content-Type", "application/json");
			
			try {
				// recuperation d'un utilisateur si pseudo et password ok
				player = objPlayer.getByPassword(pseudo, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (player != null) {
				
				HttpSession session = request.getSession();
				session.setAttribute("user", player);
				RequestDispatcher rs = request.getRequestDispatcher( VUE );
				rs.forward(request, response);
			} else {
				
				request.setAttribute("erreur", "Pseudo ou Password incorrect");
				RequestDispatcher rs = request.getRequestDispatcher( VUE );
				rs.forward(request, response);
			}
			
		}
	}
}