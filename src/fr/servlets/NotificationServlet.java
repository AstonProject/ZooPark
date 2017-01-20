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
import fr.beans.MessageBean;
import fr.beans.PlayerBean;
import fr.dao.MessagesDAO;

@WebServlet("/notifications")
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NotificationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recuperation de la session existante, si il y'en a pas elle est cree
		HttpSession session = request.getSession();
		//Recupere l'utilisateur en session si il y en a un
		PlayerBean user = (PlayerBean) session.getAttribute("user");
		this.getServletContext()
		.getRequestDispatcher("/WEB-INF/notifications.jsp")
		.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recuperation de la session existante, si il y'en a pas elle est cree
		HttpSession session = request.getSession();
		//Recupere l'utilisateur en session si il y en a un
		PlayerBean user = (PlayerBean) session.getAttribute("user");
		String action = request.getParameter("action");
		if(action != null){
			if(action.equals("delete")){
				int id = Integer.parseInt(request.getParameter("id"));
				MessagesDAO mdao = new MessagesDAO();
				mdao.deleteMessage(id);
				response.sendRedirect("notifications");
			}
		}
		else
		{
			MessagesDAO mdao = new MessagesDAO();
			List<MessageBean> messages = new ArrayList<>();
			System.out.println(user.getId());
			messages = mdao.getMessagesByPlayerId(user.getId());
			String reponseJson = "{";
			int lengthList = messages.size();
			int count = 0;
	
			if (messages.size() > 0) {
				for (MessageBean message : messages) {
					reponseJson += "\"message"+count+"\":{";
					reponseJson += "\"id\":\"" + message.getId() + "\",";
					reponseJson += "\"title\":\"" + message.getTitle() + "\",";
					reponseJson += "\"content\":\"" + message.getContent() + "\"}";
					count++;
	
					if (count != (lengthList)) {
						reponseJson += ",";
					}
				}
				reponseJson += "}";
				response.setContentType("application/json");
				response.getWriter().append(reponseJson);
				
			}
		}
	}

}
