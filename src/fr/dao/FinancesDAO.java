package fr.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.beans.FinanceBean;
import fr.utility.ConnectionDB;

public class FinancesDAO {

	private Connection connection;

	/** Constructor**/
	public FinancesDAO() {
		connection = ConnectionDB.getConnection();
	}
	
	public void createFinance (FinanceBean finance) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("INSERT INTO finance (type_action, somme, libelle, date, animals_number, ^player_id, specie_id, enclosure_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, finance.getType_action());
			st.setInt(2, finance.getSomme());
			st.setString(3, finance.getLibelle());
			st.setDate(4, (Date) finance.getDate());
			st.setInt(5, finance.getAnimals_number());
			st.setInt(6, finance.getPlayer_id());
			st.setInt(7, finance.getSpecie_id());
			st.setInt(8, finance.getEnclosure_id());
			
			st.executeUpdate();
			
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				finance.setId(generatedKeys.getInt(1));
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
	
	public FinanceBean getFinanceById(int idFinance) {
		PreparedStatement st = null;
		ResultSet rs = null;		
		FinanceBean finance = new FinanceBean();
		
		try {
			st = connection.prepareStatement("SELECT * FROM finance WHERE id=?");
			st.setInt(1, idFinance);
			rs = st.executeQuery();

			if (rs.next()) {
				finance.setType_action(rs.getString("type_action"));
				finance.setSomme(rs.getInt("somme"));
				finance.setLibelle(rs.getString("libelle"));
				finance.setDate(rs.getDate("date"));
				finance.setAnimals_number(rs.getInt("animals_number"));
				finance.setPlayer_id(rs.getInt("player_id"));
				finance.setSpecie_id(rs.getInt("specie_id"));
				finance.setEnclosure_id(rs.getInt("enclosure_id"));

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
		
		return finance;
	}
	
	public List<FinanceBean> getFinancesByPlayer(int player_id) {
		List<FinanceBean> finances = new ArrayList<FinanceBean>();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("SELECT * FROM finance WHERE player_id=?");
			st.setInt(1, player_id);
			rs = st.executeQuery();
			

			while (rs.next()) {
				FinanceBean finance = new FinanceBean();

				finance.setId(rs.getInt("id"));
				finance.setType_action(rs.getString("type_action"));
				finance.setSomme(rs.getInt("somme"));
				finance.setLibelle(rs.getString("libelle"));
				finance.setDate(rs.getDate("date"));
				finance.setAnimals_number(rs.getInt("animals_number"));
				finance.setPlayer_id(rs.getInt("player_id"));
				finance.setSpecie_id(rs.getInt("specie_id"));
				finance.setEnclosure_id(rs.getInt("enclosure_id"));

				finances.add(finance);
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
		return finances;
	}
	
	public List<FinanceBean> getAllFinances() {
		List<FinanceBean> finances = new ArrayList<FinanceBean>();
		
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.createStatement();
			rs = st.executeQuery("SELECT * FROM finance");

			while (rs.next()) {
				FinanceBean finance = new FinanceBean();

				finance.setId(rs.getInt("id"));
				finance.setType_action(rs.getString("type_action"));
				finance.setSomme(rs.getInt("somme"));
				finance.setLibelle(rs.getString("libelle"));
				finance.setDate(rs.getDate("date"));
				finance.setAnimals_number(rs.getInt("animals_number"));
				finance.setPlayer_id(rs.getInt("player_id"));
				finance.setSpecie_id(rs.getInt("specie_id"));
				finance.setEnclosure_id(rs.getInt("enclosure_id"));

				finances.add(finance);
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
		return finances;
	}
	
	public void updateFinances(FinanceBean finance) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("UPDATE finance SET type_action=?, somme=?, libelle=?, date=?, animals_number=?, player_id=?, specie_id=?, enclosure_id=? WHERE id=?");
			st.setString(1, finance.getType_action());
			st.setInt(2, finance.getSomme());
			st.setString(3, finance.getLibelle());
			st.setDate(4, (Date) finance.getDate());
			st.setInt(5, finance.getAnimals_number());
			st.setInt(6, finance.getPlayer_id());
			st.setInt(7, finance.getSpecie_id());
			st.setInt(8, finance.getEnclosure_id());
			st.setInt(9, finance.getId());


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
	
	public void deleteFinances(int idPlayer) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("DELETE FROM finance WHERE id=?");
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
