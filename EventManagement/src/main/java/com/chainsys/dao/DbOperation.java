package com.chainsys.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import com.chainsys.model.EventDetails;
import com.chainsys.model.User;
import com.chainsys.model.Vendor;
import com.chainsys.model.Venue;
import com.chainsys.util.ConnectionUtil;

public class DbOperation {

	public boolean adminLogin(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = ConnectionUtil.getConnection();
		String checkUser = "select username from Users where username=? and password=? and is_admin=1";
		PreparedStatement prepareStatement = connection.prepareStatement(checkUser);
		prepareStatement.setString(1, user.getUsername());
		prepareStatement.setString(2, user.getPassword());
		ResultSet resultSet = prepareStatement.executeQuery();

		if (!resultSet.next()) {

			return false;
		} else {
			return true;
		}

	}

	public int getUserId(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		int id = 0;
		Connection connection = ConnectionUtil.getConnection();
		String getId = "select user_id from Users where username=?";
		PreparedStatement prepareStatement = connection.prepareStatement(getId);
		prepareStatement.setString(1, user.getUsername());
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			id = resultSet.getInt("user_id");

		}
		return id;
	}

	public void insertUser(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = ConnectionUtil.getConnection();
		String checkUser = "insert into Users (username,password,email,mobile) values (?,?,?,?)";
		PreparedStatement prepareStatement = connection.prepareStatement(checkUser);
		prepareStatement.setString(1, user.getUsername());
		prepareStatement.setString(2, user.getPassword());

		prepareStatement.setString(3, user.getEmail());
		prepareStatement.setString(4, user.getMobile());

		int rows = prepareStatement.executeUpdate();
		System.out.println("Affected row :" + rows);
	}

	public boolean userLogin(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = ConnectionUtil.getConnection();
		String checkUser = "select username from Users where username=? and password=? and is_admin=0";
		PreparedStatement prepareStatement = connection.prepareStatement(checkUser);
		prepareStatement.setString(1, user.getUsername());
		prepareStatement.setString(2, user.getPassword());
		ResultSet resultSet = prepareStatement.executeQuery();

		if (!resultSet.next()) {

			return false;
		} else {
			return true;
		}
	}

	public void addVenue(Venue venue) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = ConnectionUtil.getConnection();
		String insertVenue = "INSERT INTO Venue (venue_name, venue_address, capacity, contact_phone, price, venue_image) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement = connection.prepareStatement(insertVenue);

		preparedStatement.setString(1, venue.getVenueName());
		preparedStatement.setString(2, venue.getAddress());
		preparedStatement.setInt(3, venue.getCapacity());
		preparedStatement.setString(4, venue.getContactPhone());
		preparedStatement.setInt(5, venue.getPrice());
		preparedStatement.setBytes(6, venue.getImage());

		int row = preparedStatement.executeUpdate();
		System.out.println("Affected row" + row);

	}

	public List<Venue> getAllVenues() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		ArrayList<Venue> venues = new ArrayList<>();
		Connection connection = ConnectionUtil.getConnection();

		String getVenue = "SELECT venue_id,venue_name, venue_address, capacity, contact_phone, price FROM Venue";
		PreparedStatement preparedStatement = connection.prepareStatement(getVenue);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Venue venue = new Venue();
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
		Connection connection = ConnectionUtil.getConnection();
		String deleteVenue = "DELETE FROM Venue WHERE venue_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(deleteVenue);
		preparedStatement.setInt(1, id);
		int row = preparedStatement.executeUpdate();
		System.out.println("affected row" + row);
	}

	public void addVendor(Vendor vendor) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = ConnectionUtil.getConnection();
		String insertVendor = "INSERT INTO vendor (vendor_name, vendor_contact, vendor_type,price, profile) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(insertVendor);
		preparedStatement.setString(1, vendor.getVendorName());
		preparedStatement.setString(2, vendor.getContact());
		preparedStatement.setString(3, vendor.getVendorType());
		preparedStatement.setInt(4, vendor.getPrice());
		preparedStatement.setBytes(5, vendor.getImage());
		int row = preparedStatement.executeUpdate();
		System.out.println("Affected row " + row);
	}

	public static List<Venue> getVenues() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		ArrayList<Venue> venues = new ArrayList<>();
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String getVenue = "SELECT venue_id,venue_name, venue_address, capacity, contact_phone, price,venue_image FROM Venue";
			preparedStatement = connection.prepareStatement(getVenue);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Venue venue = new Venue();
				venue.setVenueId(resultSet.getInt("venue_id"));
				venue.setVenueName(resultSet.getString("venue_name"));
				venue.setAddress(resultSet.getString("venue_address"));
				venue.setCapacity(resultSet.getInt("capacity"));
				venue.setContactPhone(resultSet.getString("contact_phone"));
				venue.setPrice(resultSet.getInt("price"));
				Blob blob = resultSet.getBlob("venue_image");
				byte[] imageBytes = blob.getBytes(1, (int) blob.length());

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

	public List<Venue> getAvailableVenues(Date date,String location) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		ArrayList<Venue> availableVenues = new ArrayList<>();
		Connection connection = ConnectionUtil.getConnection();
		String selectVenue = "SELECT venue_id,venue_name, venue_address, capacity, contact_phone, price,venue_image FROM Venue WHERE venue_id NOT IN (SELECT venue_id FROM Events WHERE event_date = ?)AND venue_address LIKE CONCAT('%', ?, '%')";
		try (PreparedStatement statement = connection.prepareStatement(selectVenue)) {
			statement.setDate(1, date);
			statement.setString(2, location);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Venue venue = new Venue();
					venue.setVenueId(resultSet.getInt("venue_id"));
					venue.setVenueName(resultSet.getString("venue_name"));
					venue.setAddress(resultSet.getString("venue_address"));
					venue.setCapacity(resultSet.getInt("capacity"));
					venue.setContactPhone(resultSet.getString("contact_phone"));
					venue.setPrice(resultSet.getInt("price"));
					Blob blob = resultSet.getBlob("venue_image");
					byte[] imageBytes = blob.getBytes(1, (int) blob.length());
					
					venue.setImage(imageBytes);
					availableVenues.add(venue);
				}
			}
		}
		return availableVenues;
	}

	public List<Vendor> getPhotographers(Date date) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		ArrayList<Vendor> availableVendors = new ArrayList<>();
		Connection connection = ConnectionUtil.getConnection();
		String selectVendors = "SELECT v.vendor_id, v.vendor_name, v.vendor_contact, v.price, v.profile FROM vendor v WHERE v.vendor_type = 'photography' AND NOT EXISTS (SELECT e.photography_id FROM Events e WHERE e.event_date = ? AND e.photography_id = v.vendor_id)";

		try (PreparedStatement statement = connection.prepareStatement(selectVendors)) {
			statement.setDate(1, date);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Vendor vendor = new Vendor();
					vendor.setVendorId(resultSet.getInt("vendor_id"));
					vendor.setVendorName(resultSet.getString("vendor_name"));
					vendor.setContact(resultSet.getString("vendor_contact"));
					vendor.setPrice(resultSet.getInt("price"));
					Blob blob = resultSet.getBlob("profile");
					byte[] imageBytes = blob.getBytes(1, (int) blob.length());
					vendor.setImage(imageBytes);
					availableVendors.add(vendor);
				}
			}
		}
		return availableVendors;
	}

	public List<Vendor> getCatering(Date date) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		ArrayList<Vendor> availableCatering = new ArrayList<>();
		Connection connection = ConnectionUtil.getConnection();
		String selectCatering = "SELECT v.vendor_id, v.vendor_name, v.vendor_contact, v.price, v.profile FROM vendor v WHERE v.vendor_type = 'Catering' AND NOT EXISTS (SELECT e.catering_id FROM Events e WHERE e.event_date = ? AND e.catering_id = v.vendor_id)";
		try (PreparedStatement statement = connection.prepareStatement(selectCatering)) {
			statement.setDate(1, date);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Vendor vendor = new Vendor();
					vendor.setVendorId(resultSet.getInt("vendor_id"));
					vendor.setVendorName(resultSet.getString("vendor_name"));
					vendor.setContact(resultSet.getString("vendor_contact"));
					vendor.setPrice(resultSet.getInt("price"));
					Blob blob = resultSet.getBlob("profile");
					byte[] imageBytes = blob.getBytes(1, (int) blob.length());
					vendor.setImage(imageBytes);
					availableCatering.add(vendor);
				}
			}
		}
		return availableCatering;
	}

	public int updateVenueDetails(Venue venue) throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		int row = 0;
		Connection connection = ConnectionUtil.getConnection();
		String updateVenue = "Update Venue set capacity=?,contact_phone=?,price=? where venue_id=?";

		PreparedStatement preparedStatement = connection.prepareStatement(updateVenue);
		preparedStatement.setInt(1, venue.getCapacity());
		preparedStatement.setString(2, venue.getContactPhone());
		preparedStatement.setInt(3, venue.getPrice());
		preparedStatement.setInt(4, venue.getVenueId());
		row = preparedStatement.executeUpdate();

		return row;
	}
	public void storeEventDetails(EventDetails eventDetails) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		java.sql.Date serviceDate = java.sql.Date.valueOf(eventDetails.getDateString());
		Connection connection = ConnectionUtil.getConnection();
		String eventData ="insert into Events (user_id,event_name,event_date,venue_id,photography_id,catering_id) values (?,?,?,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(eventData);
		preparedStatement.setInt(1,eventDetails.getUserId() );
		preparedStatement.setString(2,eventDetails.getEventType() );
		preparedStatement.setDate(3,serviceDate);
		preparedStatement.setInt(4,eventDetails.getVenueId() );
		preparedStatement.setInt(5,eventDetails.getPhotoGraphyId() );
		preparedStatement.setInt(6,eventDetails.getCateringId() );
		preparedStatement.execute();
	}
}