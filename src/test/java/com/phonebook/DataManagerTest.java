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
	private static DataManager dataManager;
	private static Object[] onePhoneSet;
	
	@BeforeClass
	public static void setupDataManager() throws SQLException{
		dataManager = new DataManager();
		onePhoneSet = new Object[]{"Mum","80295853985"};
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
    	String name = (String)onePhoneSet[0];
    	String phoneNumber = (String)onePhoneSet[1];
    	phoneNumber+="0";
    	assertNotEquals(phoneNumber, dataManager.getPhoneByName(name));
    }
    
    @Test
    public void returnNotNullTest() throws SQLException {
        String answer = dataManager.getPhoneByName((String)onePhoneSet[0]);
        assertNotNull(answer);        
    }

}
