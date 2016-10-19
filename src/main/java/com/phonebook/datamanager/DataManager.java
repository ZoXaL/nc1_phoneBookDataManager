package com.phonebook.datamanager;

import java.sql.*;
import java.util.HashMap;

public class DataManager {
	static Connection dbConnection;
//	
//	public static void main(String args[]) {
//		System.out.println("Starting db process");
//		DataManager app = new DataManager();
//		HashMap<String,String> phoneTable = app.getPhoneTableMap();
//		System.out.println("Mum Mts — "+phoneTable.get("Mum Mts"));
//		System.out.println("Bye!");
//	}

	public HashMap<String,String> getPhoneTableMap() throws SQLException {
		HashMap<String,String> map = new HashMap<String,String>();
		
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
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from phones");
			while(resultSet.next()) {				
				String name = resultSet.getString(2);
				String phoneNumber = resultSet.getString(3);
				System.out.println(name);
				map.put(name,phoneNumber);
			}
		}catch(SQLException cause) {
			throw new SQLException("Data processing error", cause);
		}
		return map;
	}
	
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