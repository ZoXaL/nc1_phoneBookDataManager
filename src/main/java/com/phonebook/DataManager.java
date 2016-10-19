package com.phonebook;

import java.sql.*;
import java.util.LinkedList;

public class DataManager {
	private static Connection dbConnection;
	private final static String DBPATH = "jdbc:mysql://localhost/nc1_phoneBook";
	private final static String USER_LOGIN = "nc1";
	private final static String USER_PASSWORD = "nc1_phoneBook";
	
	public DataManager() throws SQLException {
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
	
	public String getPhoneByName(String name) throws SQLException {
		String phoneNumber = null;	
		
		try{
			String statementString = "select * from phones where name=?";			
			PreparedStatement statement = dbConnection.prepareStatement(statementString);
			statement.setString(1,name);
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {				
				phoneNumber = resultSet.getString(3);
			}
		}catch(SQLException cause) {
			throw new SQLException("Data processing error", cause);
		}
		return phoneNumber;
	}
	
	public LinkedList<String> getNamesList() throws SQLException {
		LinkedList<String> names = new LinkedList<>();
		
		try{
			String statementString = "select * from phones";			
			PreparedStatement statement = dbConnection.prepareStatement(statementString);
			
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
}