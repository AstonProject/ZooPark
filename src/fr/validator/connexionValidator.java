package fr.validator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class connexionValidator {

	public static boolean checkUser(String email, String password) {
		boolean st = false;

		try {
			// Chargement des drivers pour mysql
			Class.forName("com.mysql.jdbc.Driver");

			// Crée une connection avec la base de données
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoopark", "root", "root");
			
			// Création de la requête préparée
			PreparedStatement ps = con.prepareStatement("select * from player where email=? and password=?");
			ps.setString(1, email);
			ps.setString(2, password);
			
			// Création d'un resultSet
			ResultSet rs = ps.executeQuery();
			
			// Lit le retour de la requête et l'affecte à la variable st (si il y a retour, la variable st vaut true)
			st = rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
}