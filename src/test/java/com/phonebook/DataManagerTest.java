package com.phonebook;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.phonebook.DataManager;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class DataManagerTest {
	private final static String DBPATH = "jdbc:mysql://localhost/nc1_phoneBook";
	private final static String USER_LOGIN = "nc1";
	private final static String USER_PASSWORD = "nc1_phoneBook";
	
	private static DataManager dataManager;
	private static Object[] phoneSetExample;
	
	@BeforeClass
	public static void setupDataManager() throws SQLException{
		dataManager = new DataManager(DBPATH, USER_LOGIN, USER_PASSWORD);
		phoneSetExample = new Object[]{"Mum","80295853985"};
	}
	
	public Object[] setExamples() {
		return new Object[]{
			new Object[]{"Mum","80295853985"},
			new Object[]{"Dad","+375299021710"},
			new Object[]{"Gleb","+375296949195"}
		};
	}
	
	public Object[] nameExamples() {
		return new Object[]{"Sanya","Anya"};
	}
	
    @Test
    @Parameters(method="setExamples")
    public void getPhoneByNameTest(String name, String phoneNumber) throws SQLException {
    	assertEquals(phoneNumber, dataManager.getPhoneByName(name));
    }
    
    @Test
    @Parameters(method="nameExamples")
    public void getNamesListTest(String name) throws SQLException {
    	LinkedList<String> namesList = dataManager.getNamesList();
    	assertTrue(namesList.contains(name));
    }
    
    @Test()
    public void wrongKeyShouldReturnWrongValue() throws SQLException {
    	String name = (String)phoneSetExample[0];
    	String phoneNumber = (String)phoneSetExample[1];
    	phoneNumber+="0";
    	assertNotEquals(phoneNumber, dataManager.getPhoneByName(name));
    }
    
    @Test
    public void returnNotNullTest() throws SQLException {
        String answer = dataManager.getPhoneByName((String)phoneSetExample[0]);
        assertNotNull(answer);        
    }

}
