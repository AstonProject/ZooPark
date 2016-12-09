package fr.validator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class connexionValidator {

	public static int checkUser (String pseudo, String password) {
		int idPlayer = 0;

		try {
			// Chargement des drivers pour mysql
			Class.forName("com.mysql.jdbc.Driver");

			// Crée une connection avec la base de données
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoopark", "root", "root");
			
			// Création de la requête préparée
			PreparedStatement ps = con.prepareStatement("select * from player where pseudo=? and password=?");
			ps.setString(1, pseudo);
			ps.setString(2, password);
			
			// Création d'un resultSet
			ResultSet rs = ps.executeQuery();
			
			// Récupération des données du résultat de la requête de lecture
			while ( rs.next() ) {
				idPlayer = rs.getInt( "id" );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return idPlayer;
		
	}
}