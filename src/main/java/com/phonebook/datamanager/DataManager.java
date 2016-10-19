package com.phonebook.datamanager;

import java.sql.*;
import java.util.LinkedList;

public class DataManager {
	static Connection dbConnection;
	
	public String getPhoneByName(String name) throws SQLException {
		String phoneNumber = null;
		
		if(dbConnection==null) {
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
				try {
					dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/nc1_phoneBook","root","mi7689040xa");
				}catch (SQLException cause) {
					throw new SQLException("Cannot get database connection", cause);
				}
			}catch(ClassNotFoundException cause) {
				throw new SQLException("Cannot find MySQL jdbc driver", cause);
			}
		}		
		
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
		LinkedList<String> names = new LinkedList<String>();
		
		if(dbConnection==null) {
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
				try {
					dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/nc1_phoneBook","root","mi7689040xa");
				}catch (SQLException cause) {
					throw new SQLException("Cannot get database connection", cause);
				}
			}catch(ClassNotFoundException cause) {
				throw new SQLException("Cannot find MySQL jdbc driver", cause);
			}
		}		
		
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