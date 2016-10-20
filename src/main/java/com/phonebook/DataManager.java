package com.phonebook;

import java.sql.*;
import java.util.LinkedList;

public class DataManager {
	private Connection dbConnection;
	private final String DBPATH; // = "jdbc:mysql://localhost/nc1_phoneBook";
	private final String USER_LOGIN; // = "nc1";
	private final String USER_PASSWORD; // = "nc1_phoneBook";
	
	public DataManager(String dbPath, String userLogin, String userPassword) throws SQLException {
		DBPATH = dbPath;
		USER_LOGIN = userLogin;
		USER_PASSWORD=userPassword;
		openConnection();
	}
	
	public String getPhoneByName(String name) throws SQLException {
		String phoneNumber = null;	
		
		try{
			String query = "select * from phones where name=?";			
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setString(1,name);
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {				
				phoneNumber = resultSet.getString(3);
			}
			statement.close();
			resultSet.close();
		}catch(SQLException cause) {
			throw new SQLException("Data processing error", cause);
		}
		return phoneNumber;
	}
	
	public LinkedList<String> getNamesList() throws SQLException {
		LinkedList<String> names = new LinkedList<>();
		
		try{
			String query = "select * from phones";			
			PreparedStatement statement = dbConnection.prepareStatement(query);
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				names.add(resultSet.getString(2));
			}
		}catch(SQLException cause) {
			throw new SQLException("Data processing error", cause);
		}
		return names;
	}
	
	public void closeConnection() throws SQLException{
		if(dbConnection!=null) {
			try {
				dbConnection.close();
			}catch(SQLException cause) {			
				throw new SQLException("Cannot close database connection", cause);
			}
		}		
	}
	public void openConnection() throws SQLException{
		if(dbConnection==null) {					
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
				try {
					dbConnection = DriverManager.getConnection(DBPATH, USER_LOGIN, USER_PASSWORD);
				}catch (SQLException cause) {
					throw new SQLException("Cannot get database connection", cause);
				}
			}catch(ClassNotFoundException cause) {
				throw new SQLException("Cannot find MySQL jdbc driver", cause);
			}
		}
	}
}