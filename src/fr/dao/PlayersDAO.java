package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.beans.PlayerBean;
import fr.utility.ConnectionDB;

/** DAO to perform queries on players **/
public class PlayersDAO {
	private Connection connection;

	public PlayersDAO() {
		connection = ConnectionDB.getConnection();
	}

	/** Global methods **/
	public void addStagiaire(PlayerBean player) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO player (pseudo, password, email) VALUES (?, ?, ?)");
			preparedStatement.setString(1, player.getPseudo());
			preparedStatement.setString(2, player.getPassword());
			preparedStatement.setString(3, player.getEmail());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePlayer(PlayerBean player) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE player SET pseudo=?, password=?, email=?, money=? WHERE id=?");
			preparedStatement.setString(1, player.getPseudo());
			preparedStatement.setString(2, player.getPassword());
			preparedStatement.setString(3, player.getEmail());
			preparedStatement.setInt(4, player.getMoney());
			preparedStatement.setInt(5, player.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteStagiaire(int idPlayer) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM player WHERE id=?");
			preparedStatement.setInt(1, idPlayer);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public PlayerBean getPlayerById(int idPlayer) {
		PlayerBean player = new PlayerBean();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player WHERE id=?");
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

	public List<PlayerBean> getAllplayers() {
		List<PlayerBean> players = new ArrayList<PlayerBean>();

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM player");

			while (rs.next()) {
				PlayerBean player = new PlayerBean();
				player.setId(rs.getInt("id"));
				player.setPseudo(rs.getString("pseudo"));
				player.setPassword(rs.getString("password"));
				player.setEmail(rs.getString("email"));
				player.setMoney(rs.getInt("money"));

				players.add(player);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return players;
	}

	// Specific methods
	public Integer getPlayerMoneyById(int idPlayer) {
			Integer money=0;
			
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT money FROM player WHERE id=?");
			preparedStatement.setInt(1, idPlayer);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				money = rs.getInt("money");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return money;
	}
}