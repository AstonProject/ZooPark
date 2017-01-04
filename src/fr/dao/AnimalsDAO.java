package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.beans.AnimalBean;
import fr.utility.ConnectionDB;

/** DAO to perform queries on animals **/
public class AnimalsDAO {
	private Connection connection;
	
	/** Constructor**/
	public AnimalsDAO() {
		connection = ConnectionDB.getConnection();
	}
	
	/** Global methods **/
	public boolean createAnimal(AnimalBean animal) {
		PreparedStatement st = null;
		int res = 0; 
		
		try {
			st = connection.prepareStatement("INSERT INTO animal (name, hungry_gauge, health_gauge, description, specie_id, enclosure_id) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, animal.getName());
			st.setInt(2, animal.getHungry_gauge());
			st.setInt(3, animal.getHealth_gauge());
			st.setString(4, animal.getDescription());
			st.setInt(5, animal.getSpecie_id());
			st.setInt(6, animal.getEnclosure_id());

			res = st.executeUpdate();
			
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				animal.setId(generatedKeys.getInt(1));
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
	
	public AnimalBean getAnimalById(int idAnimal) {
		AnimalBean animal = new AnimalBean();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM animal WHERE id=?");
			preparedStatement.setInt(1, idAnimal);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				animal.setId(rs.getInt("id"));
				animal.setName(rs.getString("name"));
				animal.setHungry_gauge(rs.getInt("hungry_gauge"));
				animal.setHealth_gauge(rs.getInt("health_gauge"));
				animal.setDescription(rs.getString("description"));
				animal.setSpecie_id(rs.getInt("specie_id"));
				animal.setEnclosure_id(rs.getInt("enclosure_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return animal;
	}

	public List<AnimalBean> getAllAnimals() {
		List<AnimalBean> animals = new ArrayList<AnimalBean>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM animal");
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				AnimalBean animal = new AnimalBean();
				
				animal.setId(rs.getInt("id"));
				animal.setName(rs.getString("name"));
				animal.setHungry_gauge(rs.getInt("hungry_gauge"));
				animal.setHealth_gauge(rs.getInt("health_gauge"));
				animal.setDescription(rs.getString("description"));
				animal.setSpecie_id(rs.getInt("specie_id"));
				animal.setEnclosure_id(rs.getInt("enclosure_id"));

				animals.add(animal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return animals;
	}
	
	public boolean updateAnimal(AnimalBean animal) {
		int res = 0;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE animal SET name=?, hungry_gauge=?, health_gauge=?, description=?, specie_id=?, enclosure_id=? WHERE id=?");
			preparedStatement.setString(1, animal.getName());
			preparedStatement.setInt(2, animal.getHungry_gauge());
			preparedStatement.setInt(3, animal.getHealth_gauge());
			preparedStatement.setString(4, animal.getDescription());
			preparedStatement.setInt(5, animal.getSpecie_id());
			preparedStatement.setInt(6, animal.getEnclosure_id());
			preparedStatement.setInt(7, animal.getId());

			res = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (res > 0);
	}

	public boolean deleteAnimal(int idAnimal) {
		int res = 0;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM animal WHERE id=?");
			preparedStatement.setInt(1, idAnimal);

			res = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (res > 0);
	}
	
	public List<AnimalBean> getAnimalsByEnclosure(int enclosure_id) {
		List<AnimalBean> animals = new ArrayList<AnimalBean>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM animal WHERE enclosure_id=?");
			preparedStatement.setInt(1, enclosure_id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				AnimalBean animal = new AnimalBean();
				animal.setId(rs.getInt("id"));
				animal.setName(rs.getString("name"));
				animal.setHungry_gauge(rs.getInt("hungry_gauge"));
				animal.setHealth_gauge(rs.getInt("health_gauge"));
				animal.setDescription(rs.getString("description"));
				animal.setSpecie_id(rs.getInt("specie_id"));
				animal.setEnclosure_id(rs.getInt("enclosure_id"));

				animals.add(animal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return animals;
		}
	
	public List<AnimalBean> getAnimalsByPlayer(int player_id) {
		List<AnimalBean> animals = new ArrayList<AnimalBean>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM animal INNER JOIN enclosure ON animal.enclosure_id = enclosure.id AND enclosure.player_id = player_id");
			preparedStatement.setInt(1, player_id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				AnimalBean animal = new AnimalBean();
				animal.setId(rs.getInt("id"));
				animal.setName(rs.getString("name"));
				animal.setHungry_gauge(rs.getInt("hungry_gauge"));
				animal.setHealth_gauge(rs.getInt("health_gauge"));
				animal.setDescription(rs.getString("description"));
				animal.setSpecie_id(rs.getInt("specie_id"));
				animal.setEnclosure_id(rs.getInt("enclosure_id"));

				animals.add(animal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return animals;
		}
	
}
