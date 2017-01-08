package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.beans.MessageBean;
import fr.utility.ConnectionDB;

public class MessagesDAO {
private Connection connection;
	
	/** Constructor**/
	public MessagesDAO() {
		connection = ConnectionDB.getConnection();
	}
	
	/** Global methods **/
	public boolean createMessage(MessageBean message) {
		PreparedStatement st = null;
		int res = 0; 
		
		try {
			st = connection.prepareStatement("INSERT INTO message (title, content, turn, player_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, message.getTitle());
			st.setString(2, message.getContent());
			st.setString(3, message.getTurn());
			st.setInt(4, message.getPlayer_id());

			res = st.executeUpdate();
			
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				message.setId(generatedKeys.getInt(1));
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
	
	public MessageBean getMessageById(int idMessage) {
		MessageBean message = new MessageBean();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM message WHERE id=?");
			preparedStatement.setInt(1, idMessage);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				message.setId(rs.getInt("id"));
				message.setTitle(rs.getString("title"));
				message.setContent(rs.getString("content"));
				message.setTurn(rs.getString("turn"));
				message.setPlayer_id(rs.getInt("player_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return message;
	}

	public List<MessageBean> getAllMessages() {
		List<MessageBean> messages = new ArrayList<MessageBean>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM message");
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				MessageBean message = new MessageBean();
				
				message.setId(rs.getInt("id"));
				message.setTitle(rs.getString("title"));
				message.setContent(rs.getString("content"));
				message.setTurn(rs.getString("turn"));
				message.setPlayer_id(rs.getInt("player_id"));

				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public boolean updateMessage(MessageBean message) {
		int res = 0;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE message SET title=?, content=? WHERE id=?");
			preparedStatement.setString(1, message.getTitle());
			preparedStatement.setString(2, message.getContent());
			preparedStatement.setInt(3, message.getId());

			res = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (res > 0);
	}

	public boolean deleteMessage(int idMessage) {
		int res = 0;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM message WHERE id=?");
			preparedStatement.setInt(1, idMessage);

			res = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (res > 0);
	}
	
	/** Specific method **/
	public List<MessageBean> getMessagesByPlayerId(int idPlayer) {
		List<MessageBean> messages = new ArrayList<MessageBean>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM message WHERE player_id=?");
			preparedStatement.setInt(1, idPlayer);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				MessageBean message = new MessageBean();
				message.setId(rs.getInt("id"));
				message.setTitle(rs.getString("title"));
				message.setContent(rs.getString("content"));
				message.setTurn(rs.getString("turn"));
				message.setPlayer_id(rs.getInt("player_id"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
}
