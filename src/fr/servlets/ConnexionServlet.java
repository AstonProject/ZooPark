package fr.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.validator.connexionValidator;

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rs = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
		rs.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		request.setAttribute("mail", email);

		if (connexionValidator.checkUser(email, password)) {
			RequestDispatcher rs = request.getRequestDispatcher("/WEB-INF/home.jsp");
			rs.forward(request, response);
		} else {
			out.println("Username or Password incorrect");
			RequestDispatcher rs = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
			rs.include(request, response);
		}
	}

}