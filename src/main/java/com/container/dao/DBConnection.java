package com.container.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static volatile DBConnection instance = null;

	DBConnection(){
		if(instance != null){
			throw new RuntimeException("getInstance method");
		}
	}


	public static DBConnection getInstance(){
		 if(instance == null){
			 synchronized (DBConnection.class){
				 if(instance == null){
					 instance = new DBConnection();
				 }
			 }
		 }
		 return instance;
	}


	public static Connection getConnectionToDatabase() {
		Connection connection = null;

		synchronized (DBConnection.class) {
			try {
				// load the driver class
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("MySQL JDBC Driver Registered!");

				// get hold of the DriverManager
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtrackerdb", "root", "");

			} catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			}

			if (connection != null) {
				System.out.println("Connection made to DB!");
			}
		}
		return connection;
	}

}
