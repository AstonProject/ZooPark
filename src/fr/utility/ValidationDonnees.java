package fr.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import fr.beans.PlayerBean;
import fr.dao.PlayersDAO;

public class ValidationDonnees {
	
	public boolean isValide = false;
	
	final static String regex = "[a-zA-Z0-9]+.?[a-zA-Z0-9]+@[a-z0-9]+\\.[a-z]{2,3}";
	
	public String resultat;
	public String getResultat() {
		return resultat;
	}
	
	public Map<String, String> erreurs = new HashMap<String, String>();
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	public PlayerBean inscrirePlayer (HttpServletRequest request) {
		
		// recuperation des donnees du formulaire
		String pseudo = request.getParameter("reg_pseudo");
		String password = request.getParameter("reg_password");
		String confirmation = request.getParameter("reg_confirmation");
		String email = request.getParameter("reg_email");
		
		PlayerBean player = new PlayerBean();
		player.setPseudo(pseudo);
		player.setPassword(password);
		player.setEmail(email);
		
		// verification des donnees saisies
		try {
			validationPseudo(pseudo);
		} catch (Exception e) {
			erreurs.put("pseudo", e.getMessage());
		}
		
		try {
			validationEmail(email);
		} catch (Exception e) {
			erreurs.put("email", e.getMessage());
		}
		
		try {
			validationPassword(password);
		} catch (Exception e) {
			erreurs.put("password", e.getMessage());
		}
		
		try {
			validationConf(confirmation, password);
		} catch (Exception e) {
			erreurs.put("confirmation", e.getMessage());
		}
		
		// Initialisation du resultat
		if (erreurs.isEmpty()) {
			isValide = true;
		} else {
			resultat = "Inscription non valide";
			player = null;
		}
		
		return player;
	}
	
	public void validationEmail (String saisie) throws Exception {
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(saisie);
		
		PlayersDAO pdao = new PlayersDAO();
		PlayerBean player = new PlayerBean();
		
		player = pdao.getByEmail(saisie);
		
		if (saisie != null && saisie.trim().length()<3) {
			throw new Exception("L'email doit contenir au moins 3 caractères !");
		} else if (saisie != null && !matcher.matches()) {
			throw new Exception("La saisie n'est pas un email !");
		} else if (saisie != null && saisie.equals(player.getEmail())) {
			throw new Exception("Cet email existe déjà !");
		}
	}
	
	public void validationPassword (String saisie) throws Exception {
		
		if (saisie != null && saisie.trim().length()<3) {
			throw new Exception("Le mot de passe doit contenir au moins 3 caractères.");
		}
	}
	
	public void validationConf (String saisie, String reg_password) throws Exception {
		if (saisie != null && !(saisie.equals(reg_password))) {
			throw new Exception("La confirmation est différente du mot de passe.");
		}
	}
		
	public void validationPseudo (String saisie) throws Exception {
		
		PlayersDAO pdao = new PlayersDAO();
		PlayerBean player = new PlayerBean();
		
		player = pdao.getByPseudo(saisie);
		
		if (saisie != null && saisie.trim().length()<3) {
			throw new Exception("Votre pseudo doit contenir au moins 3 caractères.");
		} else if (saisie != null && saisie.equals(player.getPseudo())) {
			throw new Exception("Ce pseudo existe déjà !");
		}
	}
	
}
