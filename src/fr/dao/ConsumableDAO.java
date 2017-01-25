package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.beans.ConsumablesBean;
import fr.beans.FinanceBean;
import fr.utility.ConnectionDB;

public class ConsumableDAO {

	private Connection connection;
	
	public ConsumableDAO() {
		connection = ConnectionDB.getConnection();
	}
	
	public void createConsumable (ConsumablesBean consumable) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("INSERT INTO consumables (name, quantity, player_id, type) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, consumable.getName());
			st.setInt(2, consumable.getQuantity());
			st.setInt(3, consumable.getPlayer_id());
			st.setString(4, consumable.getType());
			
			st.executeUpdate();
			
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				consumable.setId(generatedKeys.getInt(1));
			}
			
		} catch (SQLException e) {
			System.out.println("On a une erreur dans la requete");
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
	
	public ConsumablesBean getConsumableById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		ConsumablesBean consumable = null;
		
		try {
			st = connection.prepareStatement("SELECT * FROM consumables WHERE id=?");
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				consumable = new ConsumablesBean();
				consumable.setName(rs.getString("name"));
				consumable.setQuantity(rs.getInt("quantity"));
				consumable.setPlayer_id(rs.getInt("player_id"));
				consumable.setType(rs.getString("type"));
			}
			
		} catch (SQLException e) {
			System.out.println("On a une erreur dans la requete");
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
		
		return consumable;
	}
	
	public List<ConsumablesBean> getConsumablesByPlayer (int player_id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<ConsumablesBean> consumables = new ArrayList<>();
		
		try {
			st = connection.prepareStatement("SELECT * FROM consumables WHERE player_id=?");
			st.setInt(1, player_id);
			rs = st.executeQuery();
			
			while (rs.next()) {
				
				// creation du consumable
				ConsumablesBean consumable = new ConsumablesBean();
				consumable.setId(rs.getInt("id"));
				consumable.setName(rs.getString("name"));
				consumable.setQuantity(rs.getInt("quantity"));
				consumable.setPlayer_id(rs.getInt("player_id"));
				consumable.setType(rs.getString("type"));

				// ajout a la liste des consumables
				consumables.add(consumable);
				
			}
			
		} catch (SQLException e) {
			System.out.println("On a une erreur dans la requete");
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
		return consumables;
	}
	
	public List<ConsumablesBean> getAllConsumables() {
		List<ConsumablesBean> consumables = new ArrayList<ConsumablesBean>();
		
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.createStatement();
			rs = st.executeQuery("SELECT * FROM consumables");

			while (rs.next()) {
				ConsumablesBean consumable = new ConsumablesBean();

				// creation du consumable
				consumable.setId(rs.getInt("id"));
				consumable.setName(rs.getString("name"));
				consumable.setQuantity(rs.getInt("quantity"));
				consumable.setPlayer_id(rs.getInt("player_id"));
				consumable.setType(rs.getString("type"));

				// sauvegarde du consumable dans la liste
				consumables.add(consumable);
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
		return consumables;
	}
	
	public void updateConsumables(ConsumablesBean consumable) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("UPDATE consumables SET name=?, quantity=?, player_id=?, type=? WHERE id=?");
			st.setString(1, consumable.getName());
			st.setInt(2, consumable.getQuantity());
			st.setInt(3, consumable.getPlayer_id());
			st.setString(4, consumable.getType());
			st.setInt(5, consumable.getId());

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
	
	public void updateQuantityByNameAndPlayer(ConsumablesBean consumable) {
		
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("UPDATE consumables SET quantity=? WHERE name=? AND player_id=?");
			st.setInt(1, consumable.getQuantity());
			st.setString(2, consumable.getName());
			st.setInt(3, consumable.getPlayer_id());
			
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
	
	public void deleteConsumables(int idPlayer) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("DELETE FROM consumables WHERE id=?");
			st.setInt(1, idPlayer);

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
