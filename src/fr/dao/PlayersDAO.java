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
	
	/** Constructor**/
	public PlayersDAO() {
		connection = ConnectionDB.getConnection();
	}

	/** Global methods **/
	public void createPlayer(PlayerBean player) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("INSERT INTO player (pseudo, password, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, player.getPseudo());
			st.setString(2, player.getPassword());
			st.setString(3, player.getEmail());

			st.executeUpdate();
			
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				player.setId(generatedKeys.getInt(1));
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

	public void updatePlayer(PlayerBean player) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("UPDATE player SET pseudo=?, password=?, email=?, money=? WHERE id=?");
			st.setString(1, player.getPseudo());
			st.setString(2, player.getPassword());
			st.setString(3, player.getEmail());
			st.setInt(4, player.getMoney());
			st.setInt(5, player.getId());

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

	public void deletePlayer(int idPlayer) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("DELETE FROM player WHERE id=?");
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

	public PlayerBean getPlayerById(int idPlayer) {
		PreparedStatement st = null;
		ResultSet rs = null;		
		PlayerBean player = new PlayerBean();
		
		try {
			st = connection.prepareStatement("SELECT * FROM player WHERE id=?");
			st.setInt(1, idPlayer);
			rs = st.executeQuery();

			if (rs.next()) {
				player.setId(rs.getInt("id"));
				player.setPseudo(rs.getString("pseudo"));
				player.setPassword(rs.getString("password"));
				player.setEmail(rs.getString("email"));
				player.setMoney(rs.getInt("money"));
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
		
		return player;
	}
	
	public PlayerBean getByPseudo (String pseudo) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		PlayerBean player = new PlayerBean();
		
		try {
			
			st = connection.prepareStatement("SELECT pseudo FROM player WHERE pseudo=? ");
			
			st.setString(1, pseudo);
			rs = st.executeQuery(); 
			
			if (rs.next()) {
				player.setPseudo(rs.getString("pseudo"));
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
		
		return player;
	}
	

	public PlayerBean getByEmail (String email) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		PlayerBean player = new PlayerBean();
		
		try {
			
			st = connection.prepareStatement("SELECT email FROM player WHERE email=? ");
			
			st.setString(1, email);
			rs = st.executeQuery();
			
			if (rs.next()) {
				player.setEmail(rs.getString("email"));
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
		
		return player;
	}
	
	public PlayerBean getByPassword (String pseudo, String password) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		PlayerBean player = null;
		
		try {
			
			st = connection.prepareStatement("SELECT * FROM player WHERE pseudo=? AND password=?");
			
			st.setString(1, pseudo);
			st.setString(2, password);
			rs = st.executeQuery();
			
			if (rs.next()) {
				player = new PlayerBean();
				player.setId(rs.getInt("id"));
				player.setPseudo(rs.getString("pseudo"));
				player.setPassword(rs.getString("password"));
				player.setEmail(rs.getString("email"));
				player.setMoney(rs.getInt("money"));
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
		
		return player;
	}

	public List<PlayerBean> getAllplayers() {
		List<PlayerBean> players = new ArrayList<PlayerBean>();
		
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.createStatement();
			rs = st.executeQuery("SELECT * FROM player");

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
		return players;
	}

	/** Specific methods **/
	public Integer getPlayerMoneyById(int idPlayer) {
		Integer money=0;
		PreparedStatement st = null;
		ResultSet rs = null;	
			
		try {
			st = connection.prepareStatement("SELECT money FROM player WHERE id=?");
			st.setInt(1, idPlayer);
			rs = st.executeQuery();

			if (rs.next()) {
				money = rs.getInt("money");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // tres impportant : on ferme tout !!!
			
			if (rs != null) {
				try {
					rs.close(); // on ferme le resultat de la requete
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (st != null) {
				try {
					st.close(); // on ferme l'objet requete
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return money;
	}

}