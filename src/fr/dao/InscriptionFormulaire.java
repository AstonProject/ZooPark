package fr.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import fr.beans.PlayerBean;

public class InscriptionFormulaire {

	private static final String CHAMP_PSEUDO = "reg_pseudo";
	private static final String CHAMP_EMAIL = "reg_email";
	private static final String CHAMP_PASSWORD = "reg_password";
	private static final String CHAMP_CONF = "reg_confirmation";
	
	public boolean isValide = false;

	final String regex = "[a-zA-Z0-9]+.?[a-zA-Z0-9]+@[a-z0-9]+\\.[a-z]{2,3}";
	
	String resultat;
	public String getResultat() {
		return resultat;
	}
	
	Map<String, String> erreurs = new HashMap<String, String>();
	public Map<String, String> getErreurs() {
		return erreurs;
	}

	private void validationEmail (String saisie) throws Exception {
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(saisie);
		if ((saisie != null && saisie.trim().length()<3) || !matcher.matches())
			throw new Exception("L'email doit contenir au moins 3 caractères ou la saisie n'est pas un email.");
	}
	
	private void validationPassword (String saisie) throws Exception {
		
		if (saisie != null && saisie.trim().length()<3)
			throw new Exception("Le mot de passe doit contenir au moins 3 caractères.");
	}
	
	private void validationConf (String saisie, String reg_password) throws Exception {
		if (saisie != null && !(saisie.equals(reg_password)))
			throw new Exception("La confirmation est différente du mot de passe.");
	}
		
	private void validationPseudo (String saisie) throws Exception {
		if (saisie != null && saisie.trim().length()<3)
			throw new Exception("Le nom d'utilisateur doit contenir au moins 3 caractères.");
	}
	
	// Logique de validation
	public PlayerBean inscrirePlayer (HttpServletRequest request) {
			
		// Récupération des champs du formulaire
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String email = request.getParameter(CHAMP_EMAIL);
		String password = request.getParameter(CHAMP_PASSWORD);
		String confirmation = request.getParameter(CHAMP_CONF);
		
		PlayerBean player = new PlayerBean();
		player.setPseudo(pseudo);
		player.setEmail(email);
		player.setPassword(password);
		player.setMoney(10000);
				
		// Vérification des saisies
		try {
			validationPseudo(pseudo);
		} catch (Exception e) {
			erreurs.put(CHAMP_PSEUDO, e.getMessage());
		}
		
		try {
			validationEmail(email);
		} catch (Exception e) {
				erreurs.put(CHAMP_EMAIL, e.getMessage());
		}
		
		try {
			validationPassword(password);
		} catch (Exception e) {
			erreurs.put(CHAMP_PASSWORD, e.getMessage());
		}
		
		try {
			validationConf(confirmation, password);
		} catch (Exception e) {
			erreurs.put(CHAMP_CONF, e.getMessage());
		}
		
		// Initialisation du résultat
		if (erreurs.isEmpty()) {
			resultat = "Inscription réussie";
			isValide = true;
		} else {
				resultat = "Echec de l'inscription";
		}
		
		return player;
	}
}