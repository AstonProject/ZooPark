package fr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class LoginZoopark {
	List<String> messages = new ArrayList<String>();
	
	public List<String> tests(HttpServletRequest request) {
	// Chargement du driver JDBC pour MySQL
		try  {
			messages.add( "Chargement du driver..." );
			Class.forName( "com.mysql.jdbc.Driver" );
			messages.add( "Driver chargé !" );
		} catch ( ClassNotFoundException e ) {
			messages.add( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>" + e.getMessage() );
		}
	
		// Connexion à la base de données
		String url= "jdbc:mysql://localhost:3306/zoopark";
		String utilisateur = "root";
		String motDePasse = "root";
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		try {
			messages.add( "Connexion à la base de données..." );
			connexion = (Connection) DriverManager.getConnection( url, utilisateur, motDePasse );
			messages.add( "Connexion réussie !" );
			
			// Création de l'objet gérant les requêtes
			statement = connexion.createStatement();
			messages.add( "Objet requête créé !" );
			
			// Exécution d'une requête de lecture
			resultat = statement.executeQuery( "SELECT id, pseudo, password, email, money FROM player" );
			messages.add( "Requête effectuée !" );
			
			// Récupération des données du résultat de la requête de lecture
			while ( resultat.next() ) {
				int idJoueur = resultat.getInt( "id" );
				String pseudoJoueur = resultat.getString( "pseudo" );
				String passwordJoueur = resultat.getString( "password" );
				String emailJoueur = resultat.getString( "email" );
				int moneyJoueur = resultat.getInt( "money" );
				
				// Formatage des données pour affichage de la JSP finale
				messages.add( "Données retournées par la requête : id = " + idJoueur + ", pseudo = " + pseudoJoueur + ", password = " + passwordJoueur + ", email = " + emailJoueur + ", money = " + moneyJoueur + ".");
			}
		
		} catch  ( SQLException e) {
			messages.add( "Erreur lors de la connexion : <br/>" + e.getMessage() );
		} finally {
			messages.add( "Fermeture de l'objet ResultSet." );
			if (resultat != null) {
				try {
					resultat.close();
				} catch ( SQLException ignore ) {
				}
			}
			messages.add( "Fermeture de l'objet Statement." );
			if (statement != null) {
				try {
					statement.close();
				} catch ( SQLException ignore ) {
				}
			}messages.add( "Fermeture de l'objet Connection." );
			if (connexion != null) {
				try {
					connexion.close();
				} catch ( SQLException ignore ) {
				}
			}
		}
		
		return messages;
	}
}