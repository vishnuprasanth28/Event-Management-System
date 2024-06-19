package com.chainsys.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Part;

import com.chainsys.dao.DbOperation;
import com.chainsys.model.Vendor;
import com.chainsys.model.Venue;


@WebServlet("/Admin")
@MultipartConfig
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
   final DbOperation dbAction = new DbOperation();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        
    }

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String action = request.getParameter("action");

	    if (action != null) {
	        switch (action) {
	            case "addVenue":
	                addVenue(request, response);
	                break;
	            case "delete":
	                deleteVenue(request, response);
	                break;
	            case "addVendor":
	                addVendor(request, response);
	                break;
	            case "updateVenue":
	                updateVenue(request, response);
	                break;
	            default:
	                response.sendRedirect("index.jsp");
	                break;
	        }
	    } else {
	        response.sendRedirect("index.jsp");
	    }
	}

	private void addVenue(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String name = request.getParameter("name");
	    String address = request.getParameter("address");
	    int capacity = Integer.parseInt(request.getParameter("capacity"));
	    String contact = request.getParameter("contact");
	    int amount = Integer.parseInt(request.getParameter("price"));
	    
	    Part filePart = request.getPart("venue_image");
	    InputStream inputStream = filePart.getInputStream();
	    byte[] images = inputStream.readAllBytes();
	    
	    Venue venue = new Venue();
	    venue.setVenueName(name);
	    venue.setAddress(address);
	    venue.setCapacity(capacity);
	    venue.setContactPhone(contact);
	    venue.setPrice(amount);
	    venue.setImage(images);
	    
	    try {
	        dbAction.addVenue(venue);
	        ArrayList<Venue> venueList = (ArrayList<Venue>) dbAction.getAllVenues();
	        request.setAttribute("venueList", venueList);
	        request.getRequestDispatcher("admin.jsp").forward(request, response);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	private void deleteVenue(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    try {
	        int idToDelete = Integer.parseInt(request.getParameter("deleteid"));
	        dbAction.deleteVenue(idToDelete);
	        
	        ArrayList<Venue> venueList = (ArrayList<Venue>) dbAction.getAllVenues();
	        request.setAttribute("venueList", venueList);
	        request.getRequestDispatcher("admin.jsp").forward(request, response);
	    } catch (NumberFormatException | ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	private void addVendor(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String vendorName = request.getParameter("vendorName");
	    String vendorContact = request.getParameter("vendorContact");
	    String vendorType = request.getParameter("vendorType");
	    int price = Integer.parseInt(request.getParameter("price"));
	    
	    Part filesPart = request.getPart("profile_image");
	    InputStream inputStream = filesPart.getInputStream();
	    byte[] image = inputStream.readAllBytes();
	    
	    Vendor vendor = new Vendor();
	    vendor.setVendorName(vendorName);
	    vendor.setContact(vendorContact);
	    vendor.setVendorType(vendorType);
	    vendor.setPrice(price);
	    vendor.setImage(image);
	    
	    try {
	        dbAction.addVendor(vendor);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	private void updateVenue(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    try {
	        int idToUpdate = Integer.parseInt(request.getParameter("venueId"));
	        int capacity = Integer.parseInt(request.getParameter("capacity"));
	        int amount = Integer.parseInt(request.getParameter("price"));
	        String contact = request.getParameter("contact");
	        
	        Venue venue = new Venue();
	        venue.setVenueId(idToUpdate);
	        venue.setCapacity(capacity);
	        venue.setContactPhone(contact);
	        venue.setPrice(amount);
	        
	        dbAction.updateVenueDetails(venue);
	        
	        ArrayList<Venue> venueList = (ArrayList<Venue>) dbAction.getAllVenues();
	        request.setAttribute("venueList", venueList);
	        request.getRequestDispatcher("admin.jsp").forward(request, response);
	    } catch (NumberFormatException | ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

}