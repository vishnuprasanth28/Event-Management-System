package com.chainsys.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

import com.chainsys.model.EventDetails;
import com.chainsys.model.User;
import com.chainsys.model.Vendor;
import com.chainsys.model.Venue;
import com.chainsys.util.ConnectionUtil;

public class DbOperation {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String VENUE_ID = "venue_id";
	private static final String VENUE_NAME = "venue_name";
	private static final String VENUE_ADDRESS = "venue_address";
	private static final String VENUE_CAPACITY = "capacity";
	private static final String VENUE_CONTACT_PHONE = "contact_phone";
	private static final String VENUE_PRICE = "price";

	public boolean adminLogin(User user) throws ClassNotFoundException, SQLException {
	    boolean isAdmin = false;
	    Class.forName(JDBC_DRIVER);

	    try (Connection connection = ConnectionUtil.getConnection();
	         PreparedStatement prepareStatement = connection.prepareStatement(
	                 "select username from Users where username=? and password=? and is_admin=1")) {

	        prepareStatement.setString(1, user.getUsername());
	        prepareStatement.setString(2, user.getPassword());
	        ResultSet resultSet = prepareStatement.executeQuery();
	        
	        isAdmin = resultSet.next();
	    }

	    return isAdmin;
	}

	public int getUserId(User user) throws ClassNotFoundException, SQLException {
	    Class.forName(JDBC_DRIVER);
	    int id = 0;

	    try (Connection connection = ConnectionUtil.getConnection();
	         PreparedStatement prepareStatement = connection.prepareStatement(
	                 "select user_id from Users where username=?")) {

	        prepareStatement.setString(1, user.getUsername());
	        try (ResultSet resultSet = prepareStatement.executeQuery()) {
	            while (resultSet.next()) {
	                id = resultSet.getInt("user_id");
	            }
	        }
	    }

	    return id;
	}


	public void insertUser(User user) throws ClassNotFoundException, SQLException {
	    Class.forName(JDBC_DRIVER);

	    try (Connection connection = ConnectionUtil.getConnection();
	         PreparedStatement prepareStatement = connection.prepareStatement(
	                 "INSERT INTO Users (username, password, email, mobile) VALUES (?, ?, ?, ?)")) {

	        prepareStatement.setString(1, user.getUsername());
	        prepareStatement.setString(2, user.getPassword());
	        prepareStatement.setString(3, user.getEmail());
	        prepareStatement.setString(4, user.getMobile());

	        int rows = prepareStatement.executeUpdate();
	        System.out.println("Affected row: " + rows);
	    }
	}


	public boolean userLogin(User user) throws ClassNotFoundException, SQLException {
	    Class.forName(JDBC_DRIVER);
	    boolean isUser = false;

	    try (Connection connection = ConnectionUtil.getConnection();
	         PreparedStatement prepareStatement = connection.prepareStatement(
	                 "select username from Users where username=? and password=? and is_admin=0")) {

	        prepareStatement.setString(1, user.getUsername());
	        prepareStatement.setString(2, user.getPassword());
	        ResultSet resultSet = prepareStatement.executeQuery();
	        
	        isUser = resultSet.next(); 
	    }

	    return isUser;
	}


	public void addVenue(Venue venue) throws ClassNotFoundException, SQLException {
	    Class.forName(JDBC_DRIVER);

	    try (Connection connection = ConnectionUtil.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(
	                 "INSERT INTO Venue (venue_name, venue_address, capacity, contact_phone, price, venue_image) VALUES (?, ?, ?, ?, ?, ?)")) {

	        preparedStatement.setString(1, venue.getVenueName());
	        preparedStatement.setString(2, venue.getAddress());
	        preparedStatement.setInt(3, venue.getCapacity());
	        preparedStatement.setString(4, venue.getContactPhone());
	        preparedStatement.setInt(5, venue.getPrice());
	        preparedStatement.setBytes(6, venue.getImage());

	        int row = preparedStatement.executeUpdate();
	        System.out.println("Affected row: " + row);
	    }
	}


	public List<Venue> getAllVenues() throws ClassNotFoundException, SQLException {
	    Class.forName(JDBC_DRIVER);

	    ArrayList<Venue> venues = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    try {
	        connection = ConnectionUtil.getConnection();
	        String getVenue = "SELECT venue_id, venue_name, venue_address, capacity, contact_phone, price FROM Venue";
	        preparedStatement = connection.prepareStatement(getVenue);
	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            Venue venue = new Venue();
	            venue.setVenueId(resultSet.getInt(VENUE_ID));
	            venue.setVenueName(resultSet.getString(VENUE_NAME));
	            venue.setAddress(resultSet.getString(VENUE_ADDRESS));
	            venue.setCapacity(resultSet.getInt(VENUE_CAPACITY));
	            venue.setContactPhone(resultSet.getString(VENUE_CONTACT_PHONE));
	            venue.setPrice(resultSet.getInt(VENUE_PRICE));

	            venues.add(venue);
	        }
	    } finally {
	        
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }
	    }
	    
	    return venues;
	}

	
	

	public void deleteVenue(int id) throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		try(Connection connection = ConnectionUtil.getConnection()){
		String deleteVenue = "DELETE FROM Venue WHERE venue_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(deleteVenue);
		preparedStatement.setInt(1, id);
		int row = preparedStatement.executeUpdate();
		System.out.println("affected row" + row);
		}catch (SQLException e) {
	        e.printStackTrace();
	        
	        throw e;
	    }

	}

	public void addVendor(Vendor vendor) throws ClassNotFoundException, SQLException {
	    Class.forName(JDBC_DRIVER);

	    try (Connection connection = ConnectionUtil.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(
	                 "INSERT INTO vendor (vendor_name, vendor_contact, vendor_type, price, profile) VALUES (?, ?, ?, ?, ?)")) {

	        preparedStatement.setString(1, vendor.getVendorName());
	        preparedStatement.setString(2, vendor.getContact());
	        preparedStatement.setString(3, vendor.getVendorType());
	        preparedStatement.setInt(4, vendor.getPrice());
	        preparedStatement.setBytes(5, vendor.getImage());

	        int row = preparedStatement.executeUpdate();
	        System.out.println("Affected row " + row);
	    }
	}


	public static List<Venue> getVenues() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);

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
				venue.setVenueId(resultSet.getInt(VENUE_ID));
				venue.setVenueName(resultSet.getString(VENUE_NAME));
				venue.setAddress(resultSet.getString(VENUE_ADDRESS));
				venue.setCapacity(resultSet.getInt(VENUE_CAPACITY));
				venue.setContactPhone(resultSet.getString(VENUE_CONTACT_PHONE));
				venue.setPrice(resultSet.getInt(VENUE_PRICE));
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
			
			
		}
		return venues;

	}

	public List<Venue> getAvailableVenues(Date date,String location) throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);

		ArrayList<Venue> availableVenues = new ArrayList<>();
		Connection connection = ConnectionUtil.getConnection();
		String selectVenue = "SELECT venue_id,venue_name, venue_address, capacity, contact_phone, price,venue_image FROM Venue WHERE venue_id NOT IN (SELECT venue_id FROM Events WHERE event_date = ?)AND venue_address LIKE CONCAT('%', ?, '%')";
		try (PreparedStatement statement = connection.prepareStatement(selectVenue)) {
			statement.setDate(1, date);
			statement.setString(2, location);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Venue venue = new Venue();
					venue.setVenueId(resultSet.getInt(VENUE_ID));
					venue.setVenueName(resultSet.getString(VENUE_NAME));
					venue.setAddress(resultSet.getString(VENUE_ADDRESS));
					venue.setCapacity(resultSet.getInt(VENUE_CAPACITY));
					venue.setContactPhone(resultSet.getString(VENUE_CONTACT_PHONE));
					venue.setPrice(resultSet.getInt(VENUE_PRICE));
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
		Class.forName(JDBC_DRIVER);

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
		Class.forName(JDBC_DRIVER);

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

		Class.forName(JDBC_DRIVER);
		int row = 0;
		PreparedStatement preparedStatement=null;
		try(Connection connection = ConnectionUtil.getConnection()){
		String updateVenue = "Update Venue set capacity=?,contact_phone=?,price=? where venue_id=?";

		preparedStatement = connection.prepareStatement(updateVenue);
		preparedStatement.setInt(1, venue.getCapacity());
		preparedStatement.setString(2, venue.getContactPhone());
		preparedStatement.setInt(3, venue.getPrice());
		preparedStatement.setInt(4, venue.getVenueId());
		row = preparedStatement.executeUpdate();

		
	}finally {
		if (preparedStatement != null) {
			preparedStatement.close();}
	}return row;
	}
	public void storeEventDetails(EventDetails eventDetails) throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		PreparedStatement preparedStatement=null;
		java.sql.Date serviceDate = java.sql.Date.valueOf(eventDetails.getDateString());
		try(Connection connection = ConnectionUtil.getConnection()){
		String eventData ="insert into Events (user_id,event_name,event_date,venue_id,photography_id,catering_id) values (?,?,?,?,?,?)";
		preparedStatement = connection.prepareStatement(eventData);
		preparedStatement.setInt(1,eventDetails.getUserId() );
		preparedStatement.setString(2,eventDetails.getEventType() );
		preparedStatement.setDate(3,serviceDate);
		preparedStatement.setInt(4,eventDetails.getVenueId() );
		preparedStatement.setInt(5,eventDetails.getPhotoGraphyId() );
		preparedStatement.setInt(6,eventDetails.getCateringId() );
		preparedStatement.execute();
	}finally {
		if (preparedStatement != null) {
			preparedStatement.close();}
	}
	}
	public List<EventDetails> getEvents() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);

		List<EventDetails> bookedEvents = new ArrayList<>();
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Connection connection = ConnectionUtil.getConnection();
		String selectEvents =  "SELECT e.event_id, u.username AS user_name, v.venue_name, " +
                "pv.vendor_name AS photography_vendor, cv.vendor_name AS catering_vendor, " +
                "e.event_name, e.event_date " +
                "FROM Events e " +
                "INNER JOIN Users u ON e.user_id = u.user_id " +
                "INNER JOIN Venue v ON e.venue_id = v.venue_id " +
                "LEFT JOIN Vendor pv ON e.photography_id = pv.vendor_id " +
                "LEFT JOIN Vendor cv ON e.catering_id = cv.vendor_id";
				
				
		try (PreparedStatement statement = connection.prepareStatement(selectEvents)) {
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					EventDetails eventDetails = new EventDetails();
					eventDetails.setEventId(resultSet.getInt("event_id"));
					eventDetails.setUserName(resultSet.getString("user_name"));
					eventDetails.setVenueName(resultSet.getString("venue_name"));
					eventDetails.setPhotographyName(resultSet.getString("photography_vendor"));
					eventDetails.setCateringName(resultSet.getString("catering_vendor"));
					eventDetails.setEventName(resultSet.getString("event_name"));
					Date eventDate =resultSet.getDate("event_date");
					String formattedDate = dateFormat.format(eventDate);
					eventDetails.setDateString(formattedDate);
					bookedEvents.add(eventDetails);
				}
			}
		}
		return bookedEvents;
	}
}
