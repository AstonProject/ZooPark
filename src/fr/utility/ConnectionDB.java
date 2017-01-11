package fr.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
	private static Connection connection;

	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/zoopark?user=root&password=root";
	static String user = "root";
	static String password = "root";

	static {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static Connection getConnection() {
		return connection;
	}

	public static void disconnection(ResultSet result, Statement statement) {
		if (result != null) {
			try {
				result.close();
			} catch (SQLException ignore) {
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ignore) {
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ignore) {
			}
		}
	}
}

