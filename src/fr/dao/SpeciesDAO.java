package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.beans.SpecieBean;
import fr.utility.ConnectionDB;

/** DAO to perform queries on species **/
public class SpeciesDAO {
	private Connection connection;
	
	/** Constructor**/
	public SpeciesDAO() {
		connection = ConnectionDB.getConnection();
	}
	
	/** Global methods **/
	public boolean createSpecie(SpecieBean specie) {
		PreparedStatement st = null;
		int res = 0; 
		
		try {
			st = connection.prepareStatement("INSERT INTO specie (name, food_factor, food_type, description) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, specie.getName());
			st.setInt(2, specie.getFood_factor());
			st.setString(3, specie.getFood_type());
			st.setString(4, specie.getDescription());

			res = st.executeUpdate();
			
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				specie.setId(generatedKeys.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			
			if (st != null) {
				try {
					st.close(); 
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return (res > 0);
	}
	
	public SpecieBean getSpecieById(int idSpecie) {
		SpecieBean specie = new SpecieBean();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM specie WHERE id=?");
			preparedStatement.setInt(1, idSpecie);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				specie.setId(rs.getInt("id"));
				specie.setName(rs.getString("name"));
				specie.setFood_factor(rs.getInt("food_factor"));
				specie.setFood_type(rs.getString("food_type"));
				specie.setDescription(rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return specie;
	}

	public List<SpecieBean> getAllSpecies() {
		List<SpecieBean> species = new ArrayList<SpecieBean>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM specie");
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				SpecieBean specie = new SpecieBean();
				
				specie.setId(rs.getInt("id"));
				specie.setName(rs.getString("name"));
				specie.setFood_factor(rs.getInt("food_factor"));
				specie.setFood_type(rs.getString("food_type"));
				specie.setDescription(rs.getString("description"));

				species.add(specie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return species;
	}
	
	public boolean updateSpecie(SpecieBean specie) {
		int res = 0;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE specie SET name=?, food_factor=?, food_type=?, description=? WHERE id=?");
			preparedStatement.setString(1, specie.getName());
			preparedStatement.setInt(2, specie.getFood_factor());
			preparedStatement.setString(3, specie.getFood_type());
			preparedStatement.setString(4, specie.getDescription());
			preparedStatement.setInt(5, specie.getId());

			res = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (res > 0);
	}

	public boolean deleteSpecie(int idSpecie) {
		int res = 0;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM specie WHERE id=?");
			preparedStatement.setInt(1, idSpecie);

			res = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (res > 0);
	}
	
	/** Specific method **/
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
