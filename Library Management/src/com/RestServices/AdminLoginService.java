package com.RestServices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.DbUtils.DBConnection;
import com.Pojo.Administrator;

@Path("/AdminLoginService")
public class AdminLoginService {

	public static void main(String[] args) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		String pwd="";
		try {
			
			String sql;
			sql = "Select Password from administrator where Id=?" ;
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1, "Vinit");
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next())
			pwd = rs.getString(1);
			System.out.println(pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return pwd;

	}

	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Administrator getPassword(@PathParam("userId") String userId) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		String pwd="";
		Administrator admin=null;
		try {
			
			String sql;
			sql = "Select Password from administrator where Id= ?" ;
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1, userId);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next())
			pwd = rs.getString("Password");
			admin=new Administrator(userId, pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin;

	}

}
