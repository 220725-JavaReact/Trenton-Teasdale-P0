package com.revature.P0.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static ConnectionFactory connectionFactory;
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private ConnectionFactory() {
		
	}
	public static ConnectionFactory getInstance() {
		if(connectionFactory == null) connectionFactory = new ConnectionFactory();
		return connectionFactory;
	}
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Secrets.url, Secrets.user, Secrets.pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
