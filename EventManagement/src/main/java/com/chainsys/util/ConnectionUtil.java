package com.chainsys.util;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;


public class ConnectionUtil   {
	private ConnectionUtil() {
	    throw new IllegalStateException("Utility class");
	}

	
    public static Connection getConnection() throws  SQLException {
      
      
    	String url ="jdbc:mysql://localhost:3306/event_management";
    	String root ="root";
    	
        return DriverManager.getConnection(url, root, "Mechatronics@1");
    }
}