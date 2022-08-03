package dao;

import java.sql.*;

import jakarta.servlet.http.HttpServlet;

public class DAOConnection  extends HttpServlet {
	public Connection connection = null;
	
	public Connection buildConnection() {
		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			String url = "jdbc:mysql://localhost:3306/jsw_bank";
			String userName = "root";
			String password = "jsw@2022";
			connection = DriverManager.getConnection(url, userName, password);
		}
		catch(Exception e) {
			System.out.println("------------------- Error IN DAO --------------------\n "+e.getMessage());
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}