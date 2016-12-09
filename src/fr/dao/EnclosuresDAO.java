package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.beans.EnclosureBean;
import fr.utility.ConnectionDB;

/** DAO to perform queries on enclosures **/
public class EnclosuresDAO {
	private Connection connection;

	public EnclosuresDAO() {
		connection = ConnectionDB.getConnection();
	}

	public void addEnclosure(EnclosureBean enclosure) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO player (locate_x(), locate_y(), capacity(), animal_quantity(), cleanliness_gauge(), employee_slot(), employee_quantity(), specie_id(), player_id() ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			preparedStatement.setInt(1, enclosure.getLocate_x());
			preparedStatement.setInt(2, enclosure.getLocate_y());
			preparedStatement.setInt(3, enclosure.getCapacity());
			preparedStatement.setInt(4, enclosure.getAnimal_quantity());
			preparedStatement.setInt(5, enclosure.getCleanliness_gauge());
			preparedStatement.setInt(6, enclosure.getEmployee_slot());
			preparedStatement.setInt(7, enclosure.getEmployee_quantity());
			preparedStatement.setInt(8, enclosure.getSpecie_id());
			preparedStatement.setInt(9, enclosure.getPlayer_id());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePlayer(EnclosureBean enclosure) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE player SET locate_x=?, locate_y=?, capacity=?, animal_quantity=?, cleanliness_gauge=?, employee_slot=?, employee_quantity=?, specie_id=?, player_id=?  WHERE id=?");
			preparedStatement.setInt(1, enclosure.getLocate_x());
			preparedStatement.setInt(2, enclosure.getLocate_y());
			preparedStatement.setInt(3, enclosure.getCapacity());
			preparedStatement.setInt(4, enclosure.getAnimal_quantity());
			preparedStatement.setInt(5, enclosure.getCleanliness_gauge());
			preparedStatement.setInt(6, enclosure.getEmployee_slot());
			preparedStatement.setInt(7, enclosure.getEmployee_quantity());
			preparedStatement.setInt(8, enclosure.getSpecie_id());
			preparedStatement.setInt(9, enclosure.getPlayer_id());
			preparedStatement.setInt(10, enclosure.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteEnclosure(int idEnclosure) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enclosure WHERE id=?");
			preparedStatement.setInt(1, idEnclosure);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public EnclosureBean getEnclosureById(int idEnclosure) {
		EnclosureBean enclosure = new EnclosureBean();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM enclosure WHERE id=?");
			preparedStatement.setInt(1, idEnclosure);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				enclosure.setId(rs.getInt("id"));
				enclosure.setLocate_x(rs.getInt("locate_x"));
				enclosure.setLocate_y(rs.getInt("locate_y"));
				enclosure.setCapacity(rs.getInt("capacity"));
				enclosure.setAnimal_quantity(rs.getInt("animal_quantity"));
				enclosure.setCleanliness_gauge(rs.getInt("cleanliness_gauge"));
				enclosure.setEmployee_slot(rs.getInt("employee_slot"));
				enclosure.setEmployee_quantity(rs.getInt("employee_quantity"));
				enclosure.setEmployee_quantity(rs.getInt("employee_quantity"));
				enclosure.setSpecie_id(rs.getInt("specie_id"));
				enclosure.setPlayer_id(rs.getInt("player_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enclosure;
	}

	public List<EnclosureBean> getAllEnclosures(int player_id) {
		List<EnclosureBean> enclosures = new ArrayList<EnclosureBean>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM enclosure WHERE player_id=?");
			preparedStatement.setInt(1, player_id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				EnclosureBean enclosure = new EnclosureBean();
				enclosure.setId(rs.getInt("id"));
				enclosure.setLocate_x(rs.getInt("locate_x"));
				enclosure.setLocate_y(rs.getInt("locate_y"));
				enclosure.setCapacity(rs.getInt("capacity"));
				enclosure.setAnimal_quantity(rs.getInt("animal_quantity"));
				enclosure.setCleanliness_gauge(rs.getInt("cleanliness_gauge"));
				enclosure.setEmployee_slot(rs.getInt("employee_slot"));
				enclosure.setEmployee_quantity(rs.getInt("employee_quantity"));
				enclosure.setEmployee_quantity(rs.getInt("employee_quantity"));
				enclosure.setSpecie_id(rs.getInt("specie_id"));
				enclosure.setPlayer_id(rs.getInt("player_id"));

				enclosures.add(enclosure);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enclosures;
	}

}