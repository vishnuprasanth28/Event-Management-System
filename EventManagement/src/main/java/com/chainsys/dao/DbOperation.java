package com.chainsys.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.chainsys.model.User;
import com.chainsys.model.Vendor;
import com.chainsys.model.Venue;
import com.chainsys.util.ConnectionUtil;


public class DbOperation {
		
	public boolean adminLogin(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection= ConnectionUtil.getConnection();
		String checkUser = "select username from Users where username=? and password=? and is_admin=1";
		PreparedStatement prepareStatement = connection.prepareStatement(checkUser);
		prepareStatement.setString(1, user.getUsername());
		prepareStatement.setString(2, user.getPassword());
		ResultSet resultSet = prepareStatement.executeQuery();

		if(!resultSet.next()) {
			
			return false;
		} 
		else {
			return true;
		}
		
	}
	
	
	
	
	public void insertUser(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection= ConnectionUtil.getConnection();
		String checkUser = "insert into Users (username,password,email,mobile) values (?,?,?,?)";
		PreparedStatement prepareStatement = connection.prepareStatement(checkUser);
		prepareStatement.setString(1, user.getUsername());
		prepareStatement.setString(2, user.getPassword());
	
		prepareStatement.setString(3, user.getEmail());
		prepareStatement.setString(4, user.getMobile());
		
		int rows = prepareStatement.executeUpdate();
		System.out.println("Affected row :"+rows);
	}
	public boolean userLogin(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection= ConnectionUtil.getConnection();
		String checkUser = "select username from Users where username=? and password=? and is_admin=0";
		PreparedStatement prepareStatement = connection.prepareStatement(checkUser);
		prepareStatement.setString(1, user.getUsername());
		prepareStatement.setString(2, user.getPassword());
		ResultSet resultSet = prepareStatement.executeQuery();

		if(!resultSet.next()) {
			
			return false;
		} 
		else {
			return true;
		}
}
	public void addVenue(Venue venue) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection= ConnectionUtil.getConnection();
		String insertVenue ="INSERT INTO Venue (venue_name, venue_address, capacity, contact_phone, price, venue_image) VALUES (?, ?, ?, ?, ?, ?)";	
		
		PreparedStatement preparedStatement = connection.prepareStatement(insertVenue);

		preparedStatement.setString(1, venue.getVenueName());
		preparedStatement.setString(2, venue.getAddress());
		preparedStatement.setInt(3, venue.getCapacity());
		preparedStatement.setString(4, venue.getContactPhone());
		preparedStatement.setInt(5, venue.getPrice());
		preparedStatement.setBytes(6, venue.getImage());

		int row = preparedStatement.executeUpdate();
		System.out.println("Affected row"+row);

	}
	public ArrayList<Venue> getAllVenues() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Venue venue = new Venue();
		ArrayList<Venue> venues = new ArrayList<>();
		Connection connection= ConnectionUtil.getConnection();

       
            
           
            String getVenue = "SELECT venue_id,venue_name, venue_address, capacity, contact_phone, price FROM Venue";
            PreparedStatement preparedStatement = connection.prepareStatement(getVenue);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                
                venue.setVenueId(resultSet.getInt("venue_id"));
                venue.setVenueName(resultSet.getString("venue_name"));
                venue.setAddress(resultSet.getString("venue_address"));
                venue.setCapacity(resultSet.getInt("capacity"));
                venue.setContactPhone(resultSet.getString("contact_phone"));
                venue.setPrice(resultSet.getInt("price"));
                
                venues.add(venue);
            }
			return venues; 
        
}
	public void deleteVenue(int id) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection= ConnectionUtil.getConnection();
		String deleteVenue = "DELETE FROM Venue WHERE venue_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(deleteVenue);
		 preparedStatement.setInt(1, id);
		 int row =preparedStatement.executeUpdate();
		 System.out.println("affected row"+ row);
	}
	
	public void addVendor(Vendor vendor) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection= ConnectionUtil.getConnection();
		String insertVendor ="INSERT INTO vendor (vendor_name, vendor_contact, vendor_type) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(insertVendor);
		preparedStatement.setString(1, vendor.getVendorName());
		preparedStatement.setString(2, vendor.getContact());
		preparedStatement.setString(3, vendor.getVendorType());
		int row = preparedStatement.executeUpdate();
		System.out.println("Affected row "+row);
		}
	
	public static ArrayList<Venue> getVenues() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Venue venue = new Venue();
		ArrayList<Venue> venues = new ArrayList<>();
		Connection connection= ConnectionUtil.getConnection();
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
            try {
           
            String getVenue = "SELECT venue_id,venue_name, venue_address, capacity, contact_phone, price,venue_image FROM Venue";
            preparedStatement = connection.prepareStatement(getVenue);
             resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                
                venue.setVenueId(resultSet.getInt("venue_id"));
                venue.setVenueName(resultSet.getString("venue_name"));
                venue.setAddress(resultSet.getString("venue_address"));
                venue.setCapacity(resultSet.getInt("capacity"));
                venue.setContactPhone(resultSet.getString("contact_phone"));
                venue.setPrice(resultSet.getInt("price"));
                Blob blob = resultSet.getBlob("venue_image");
                byte[] imageBytes = blob.getBytes(1, (int) blob.length());
             //   String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                venue.setImage(imageBytes);
                venues.add(venue);
            }
            } finally {
                
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
			return venues; 
        
}
}