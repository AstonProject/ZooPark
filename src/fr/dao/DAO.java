package fr.dao;

import java.sql.Connection;

public class DAO {
	private Connection connection;

	 public DAO() {
	  connection = DBUtility.getConnection();
	 }
}
