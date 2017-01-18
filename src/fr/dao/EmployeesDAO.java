package fr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.beans.EmployeeBean;
import fr.utility.ConnectionDB;

/** DAO to perform queries on employees **/
public class EmployeesDAO {
	private Connection connection;
	
	/** Constructor**/
	public EmployeesDAO() {
		connection = ConnectionDB.getConnection();
	}
	
	public boolean createEmployee(EmployeeBean employee) {
		PreparedStatement st = null;
		int res = 0; 
		
		try {
			st = connection.prepareStatement("INSERT INTO employee (type, health_gauge, description, enclosure_id, player_id) VALUES (?, ?, ?, ?, ?)");
			st.setString(1, employee.getType());
			st.setInt(2, employee.getHealth_gauge());
			st.setString(3, employee.getDescription());
			st.setInt(4, employee.getEnclosure_id());
			st.setInt(5, employee.getPlayer_id());

			res = st.executeUpdate();
			
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
	
	
	public EmployeeBean getEmployeeById(int idEmployee) {
		EmployeeBean employee = new EmployeeBean();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee WHERE id=?");
			preparedStatement.setInt(1, idEmployee);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				employee.setId(rs.getInt("id"));
				employee.setType(rs.getString("type"));
				employee.setHealth_gauge(rs.getInt("health_gauge"));
				employee.setDescription(rs.getString("description"));
				employee.setEnclosure_id(rs.getInt("enclosure_id"));
				employee.setPlayer_id(rs.getInt("player_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}
	
	public List<EmployeeBean> getEmployeesByEnclosure(int enclosure_id) {
		List<EmployeeBean> employees = new ArrayList<EmployeeBean>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM employee WHERE enclosure_id=?");
			preparedStatement.setInt(1, enclosure_id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				EmployeeBean employee = new EmployeeBean();
				employee.setId(rs.getInt("id"));
				employee.setType(rs.getString("type"));
				employee.setHealth_gauge(rs.getInt("health_gauge"));
				employee.setDescription(rs.getString("description"));
				employee.setEnclosure_id(rs.getInt("enclosure_id"));
				employee.setPlayer_id(rs.getInt("player_id"));

				employees.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
		}
	
	public List<EmployeeBean> getEmployeesByPlayer(int player_id) {
		List<EmployeeBean> employees = new ArrayList<EmployeeBean>();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM employee WHERE player_id=?");
			preparedStatement.setInt(1, player_id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				EmployeeBean employee = new EmployeeBean();
				employee.setId(rs.getInt("id"));
				employee.setType(rs.getString("type"));
				employee.setHealth_gauge(rs.getInt("health_gauge"));
				employee.setDescription(rs.getString("description"));
				employee.setEnclosure_id(rs.getInt("enclosure_id"));
				employee.setPlayer_id(rs.getInt("player_id"));

				employees.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
		}
	
	public void updateEmployee(EmployeeBean employee) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE employee SET type=?, health_gauge=?, description=?, enclosure_id=?, player_id=? WHERE id=?");
			preparedStatement.setString(1, employee.getType());
			preparedStatement.setInt(2, employee.getHealth_gauge());
			preparedStatement.setString(3, employee.getDescription());
			preparedStatement.setInt(4, employee.getEnclosure_id());
			preparedStatement.setInt(5, employee.getPlayer_id());
			preparedStatement.setInt(6, employee.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteEmployee(int idEmployee) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE id=?");
			preparedStatement.setInt(1, idEmployee);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
