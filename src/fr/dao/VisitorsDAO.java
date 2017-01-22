package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.beans.EmployeeBean;
import fr.beans.FinanceBean;
import fr.beans.PlayerBean;
import fr.beans.VisitorBean;
import fr.utility.ConnectionDB;

public class VisitorsDAO {

	private Connection connection;
	
	/** Constructor**/
	public VisitorsDAO() {	
		connection = ConnectionDB.getConnection();
	}

	/** Global methods **/
	public void createVisitor(VisitorBean visitor) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("INSERT INTO visitor (satisfaction_gauge, coins, player_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, visitor.getSatisfaction_gauge());
			st.setInt(2, visitor.getCoins());
			st.setInt(3, visitor.getPlayer_id());

			st.executeUpdate();
			
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				visitor.setId(generatedKeys.getInt(1));
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

	public VisitorBean getVisitorById (int idVisitor) {
		PreparedStatement st = null;
		ResultSet rs = null;		
		VisitorBean visitor = new VisitorBean();
		
		try {
			st = connection.prepareStatement("SELECT * FROM visitor WHERE id=?");
			st.setInt(1, idVisitor);
			rs = st.executeQuery();

			if (rs.next()) {
				visitor.setId(rs.getInt("id"));
				visitor.setSatisfaction_gauge(rs.getInt("satisfaction_gauge"));
				visitor.setCoins(rs.getInt("coins"));
				visitor.setPlayer_id(rs.getInt("player_id"));
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
		
		return visitor;
	}
	
	public VisitorBean getVisitorByPlayerId (int player_id) {
		PreparedStatement st = null;
		ResultSet rs = null;		
		VisitorBean visitor = new VisitorBean();
		
		try {
			st = connection.prepareStatement("SELECT * FROM visitor WHERE player_id=?");
			st.setInt(1, player_id);
			rs = st.executeQuery();

			if (rs.next()) {
				visitor.setId(rs.getInt("id"));
				visitor.setSatisfaction_gauge(rs.getInt("satisfaction_gauge"));
				visitor.setCoins(rs.getInt("coins"));
				visitor.setPlayer_id(rs.getInt("player_id"));
				
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
		
		return visitor;
	}
	
	public void updateVisitor (VisitorBean visitor) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("UPDATE visitor SET satisfaction_gauge=?, coins=?, player_id=? WHERE id=?");
			st.setInt(1, visitor.getSatisfaction_gauge());
			st.setInt(2, visitor.getCoins());
			st.setInt(3, visitor.getPlayer_id());
			st.setInt(4, visitor.getId());

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

	public void deleteVisitor(int player_id) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("DELETE FROM visitor WHERE id=?");
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

	public int countVisitors(int player_id) {
		int count = 0;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(visitor.id) FROM visitor WHERE player_id=?");
			preparedStatement.setInt(1, player_id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}
	
}
