package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.beans.EnclosureBean;
import fr.beans.PlayerBean;
import fr.utility.ConnectionDB;

public class DaoHomeBuildMenu {
	private Connection connection;

	public DaoHomeBuildMenu() {
		connection = ConnectionDB.getConnection();
	}

	public PlayerBean getPlayerById(int idPlayer) {
		PlayerBean player = new PlayerBean();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from player where id=?");
			preparedStatement.setInt(1, idPlayer);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				player.setId(rs.getInt("id"));
				player.setPseudo(rs.getString("pseudo"));
				player.setPassword(rs.getString("password"));
				player.setEmail(rs.getString("email"));
				player.setMoney(rs.getInt("money"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return player;
	}
	
	public EnclosureBean getEnclosureById(int idEnclosure) {
		EnclosureBean enclosure = new EnclosureBean();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from enclosure where id=?");
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
		List<EnclosureBean>enclosures = new ArrayList<EnclosureBean>();
		  try {
		   Statement statement = connection.createStatement();
		   ResultSet rs = statement.executeQuery("select * from enclosure WHERE player_id=?");
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
