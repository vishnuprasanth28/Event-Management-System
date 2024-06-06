package com.chainsys.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ConnectionUtil   {
	
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
      
      //  Class.forName("com.mysql.cj.jdbc.Driver");
    	String url ="jdbc:mysql://localhost:3306/event_management";
    	String root ="root";
    	
        return DriverManager.getConnection(url, root, "Mechatronics@1");
    }
}