package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.utility.ConnectionDB;

public class SpecieDAO {
	private Connection connection;
	
	public SpecieDAO() {
		connection = ConnectionDB.getConnection();
	}
	
	
	public List<String> getDescriptions() {
		List<String> descriptions = new ArrayList<String>();
		//Requete pour recuperer toute les descriptions
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT description FROM specie");
			
			ResultSet rs = preparedStatement.executeQuery();
		//Replissage de la Arraylist descriptions avec les resultats de la requete
			while (rs.next()) {
				String description;
				description= (rs.getString("description"));

				descriptions.add(description);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return descriptions;
	}
}
