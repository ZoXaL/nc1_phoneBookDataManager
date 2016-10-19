package com.phonebook.datamanager;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class DataManagerTest {
	private static DataManager dataManager;
	private static Object[] onePhoneSet;
	
	@BeforeClass
	public static void setupDataManager() {
		dataManager = new DataManager();
		onePhoneSet = new Object[]{"Mum","80295853985"};
	}
	
	public Object[] dataManagerReturnSets() {
		return new Object[]{
			new Object[]{"Mum","80295853985"},
			new Object[]{"Dad","+375299021710"},
			new Object[]{"Gleb","+375296949195"}
		};
	}
    @Test
    @Parameters(method="dataManagerReturnSets")
    public void dataManagerTest(String name, String phoneNumber) throws SQLException {
    	assertEquals(phoneNumber, dataManager.getPhoneByName(name));
    }
    
    @Test()
    public void wrongKeyShouldReturnWrongValue() throws SQLException {
    	String name = (String)onePhoneSet[0];
    	String phoneNumber = (String)onePhoneSet[1];
    	phoneNumber+="0";
    	assertNotEquals(phoneNumber, dataManager.getPhoneByName(name));
    }
    
    @Test
    public void dataManagerReturnNotNullTest() throws SQLException {
        String answer = dataManager.getPhoneByName((String)onePhoneSet[0]);
        assertNotNull(answer);        
    }

}
