package fr.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dao.LoginZoopark;

@WebServlet("/TestVue")
public class LoginZooparkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String ATT_MESSAGES = "messages";
	public static final String VUE="/WEB-INF/TestVue.jsp";
		
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginZoopark test = new LoginZoopark();
		
		// Récupération des messages
		List<String> messages = test.tests(request);
		
		// Enregistrement des messages dans l'objet requête
		request.setAttribute(ATT_MESSAGES, messages);
		
		// Transmission vers la page en charge de l'affichage des résultats
		this.getServletContext().getRequestDispatcher(VUE).forward(request,  response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
