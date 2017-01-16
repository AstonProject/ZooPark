package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.beans.FoodBean;
import fr.utility.ConnectionDB;

public class FoodsDAO {
	private Connection connection;
	
	/** Constructor**/
	public FoodsDAO() {
		connection = ConnectionDB.getConnection();
	}

	/** Global methods **/
	public void createFood (FoodBean food) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("INSERT INTO food (title, quantity, player_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, food.getTitle());
			st.setInt(2, food.getQuantity());
			st.setInt(3, food.getPlayer_id());

			st.executeUpdate();
			
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				food.setId(generatedKeys.getInt(1));
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
	}

	public FoodBean getFoodById (int idFood) {
		PreparedStatement st = null;
		ResultSet rs = null;		
		FoodBean food = new FoodBean();
		
		try {
			st = connection.prepareStatement("SELECT * FROM food WHERE id=?");
			st.setInt(1, idFood);
			rs = st.executeQuery();

			if (rs.next()) {
				food.setId(rs.getInt("id"));
				food.setTitle(rs.getString("title"));
				food.setQuantity(rs.getInt("quantity"));
				food.setPlayer_id(rs.getInt("player_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (st != null) {
				try {
					st.close(); 
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return food;
	}
	
	public FoodBean getFoodByPlayerId (int player_id) {
		PreparedStatement st = null;
		ResultSet rs = null;		
		FoodBean food = new FoodBean();
		
		try {
			st = connection.prepareStatement("SELECT * FROM food WHERE player_id=?");
			st.setInt(1, player_id);
			rs = st.executeQuery();

			if (rs.next()) {
				food.setId(rs.getInt("id"));
				food.setTitle(rs.getString("title"));
				food.setQuantity(rs.getInt("quantity"));
				food.setPlayer_id(rs.getInt("player_id"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (st != null) {
				try {
					st.close(); 
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return food;
	}
	
	public void updateFood (FoodBean food) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("UPDATE food SET title=?, qunatity=?, player_id=? WHERE id=?");
			st.setString(1, food.getTitle());
			st.setInt(2, food.getQuantity());
			st.setInt(3, food.getPlayer_id());
			st.setInt(4, food.getId());

			st.executeUpdate();
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
	}

	public void deleteFood (int player_id) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("DELETE FROM food WHERE id=?");
			st.setInt(1, player_id);

			st.executeUpdate();

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
	}
	
}
