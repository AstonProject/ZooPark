package fr.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.beans.PlayerBean;
import fr.utility.EnclosureManagementUtil;

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
			String statSLE = request.getParameter("statusSLE");
			String statME = request.getParameter("statusME");
			String statSE = request.getParameter("statusSE");
			String statSQ = request.getParameter("statusSQ");
			String statG = request.getParameter("statusG");
			String statER= request.getParameter("statusER");
			String statAP = request.getParameter("statusAPrices");
			String statEP = request.getParameter("statusEPrices");
			String statusEUP = request.getParameter("statusEUP");
			String statPRA = request.getParameter("statusPRA");
			String statRE = request.getParameter("statusRE");
			String statUE = request.getParameter("statusUE");

			/**Permettre affichage des animaux de l'enclos, showAnimals() en JS**/
			if ((statSA != null) && statSA.equals("okSA")) {
				EnclosureManagementUtil.showAnimals(request, response);
			} /**visualiser les employes assignables a l'enclos	**/
			else if ((statSLE != null) && statSLE.equals("okSLE")) {
				EnclosureManagementUtil.showAEmployees(request, response);
			} /**Permettre le deplacement des employes dans l'enclos, moveEmployees() en JS	**/
			else if ((statME != null) && statME.equals("okME")) {
				EnclosureManagementUtil.moveEmployees(request, response);
			}/**Permettre affichage des employes dans l'enclos,showEmployees() en JS	**/
			else if ((statSE != null) && statSE.equals("okSE")) {
				EnclosureManagementUtil.showEmployeesIn(request, response);	
			}/**permettre affichage des jauges**/
			else if ((statG != null) && statG.equals("okG")) {
				EnclosureManagementUtil.showGauges(request, response);
			}/** fixer les valeurs Min Max de l'input number **/
			 else if ((statSQ != null) && statSQ.equals("okSQ")) {
				 EnclosureManagementUtil.setMinMax(request, response);	
			 } /**permettre un resize**/
			 else if ((statER != null) && statER.equals("okER")) {
				 EnclosureManagementUtil.resize(request, response);
			 }/**permettre affichage des prix**/
			else if ((statAP != null) ||(statEP != null) || (statusEUP != null)) {
				EnclosureManagementUtil.showPrices(request, response, statusEUP, statEP, statAP);
			}/**permettre achat ou revente des animaux**/
			else if ((statPRA != null) && statPRA.equals("okPRA")) {
				EnclosureManagementUtil.purchaseResaleA(request, response);
			}/**permettre la revente d'un enclos et de tous ses animaux**/
			else if ((statRE != null) && statRE.equals("okRE")) {
				EnclosureManagementUtil.resaleE(request, response);
			}/**Executer un resize**/
			else if ((statUE != null) && statUE.equals("okUE")) {
				EnclosureManagementUtil.executeResize(request, response);
			}
		}
	}

}
