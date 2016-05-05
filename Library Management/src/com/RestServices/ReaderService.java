package com.RestServices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.DbUtils.DBConnection;
import com.Pojo.Reader;

@Path("/Reader")
public class ReaderService {
    @GET
    @Path("/searchReaderByID/{readerId}")
    @Produces(MediaType.APPLICATION_JSON)
	public Reader getRaderById(@PathParam("readerId")int readerId){
    	Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		int id=0;
		String name;
		String address;
		String phnNumber;
		Reader reader=null;
		try {
			
			String sql;
			sql = "Select * from reader where readerid=?" ;
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, readerId);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next())
			readerId = rs.getInt("readerId");
			name = rs.getString("name");
			address=rs.getString("address");
			phnNumber=rs.getString("phonenumber");
			reader= new Reader();
			reader.setName(name);
			reader.setReaderId(readerId);
			reader.setAddress(address);
			reader.setPhoneNumber(phnNumber);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reader;

		
	}
}
