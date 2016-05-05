package com.DbUtils;
import java.sql.*;

public class DBConnection {

	static final String driver="com.mysql.jdbc.Driver";
	static final String URL="jdbc:mysql://localhost/library_management";
	final static String userName="root";
	final static String passowd="Panduit123";
	public static Connection getConnection(){
		
		
		Connection conn=null;
		 System.out.println("Connecting to database...");
		 
	     try {
	    	 Class.forName(driver);
			conn = DriverManager.getConnection(URL,userName,passowd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return conn;
	}
	

}
