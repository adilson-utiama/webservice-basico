package br.com.carrowebservice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String db = "jdbc:mysql://localhost/webservice";
		String user = "root";
		String password = "";
		try {
			return (Connection) DriverManager.getConnection(db, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
