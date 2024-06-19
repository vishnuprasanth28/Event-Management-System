package com.chainsys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.chainsys.dao.DbOperation;
import com.chainsys.model.EventDetails;
import com.chainsys.model.Vendor;
import com.chainsys.model.Venue;


@WebServlet("/EventBookingServlet")
public class EventBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final DbOperation dbOperation = new DbOperation();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventBookingServlet() {
		super();
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String action = request.getParameter("action");

	    if (action != null) {
	        switch (action) {
	            case "getVenue":
	                handleGetVenue(request, response);
	                break;
	            case "getService":
	                handleGetService(request, response);
	                break;
	            case "checkout":
	                handleCheckout(request, response);
	                break;
	            default:
	                response.sendRedirect("index.jsp");
	                break;
	        }
	    } else {
	        response.sendRedirect("index.jsp");
	    }
	}

	private void handleGetVenue(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String eventType = request.getParameter("eventType");
	    String dateString = request.getParameter("eventDate");
	    String location = request.getParameter("location");
	    java.sql.Date eventDate = java.sql.Date.valueOf(dateString);

	    try {
	        ArrayList<Venue> availableVenues = (ArrayList<Venue>) dbOperation.getAvailableVenues(eventDate, location);
	        request.setAttribute("venues", availableVenues);
	        request.setAttribute("event", eventType);
	        request.setAttribute("date", eventDate);
	        request.getRequestDispatcher("eventservice.jsp").forward(request, response);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	private void handleGetService(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String eventType = request.getParameter("eventType");
	    int venuePrice = Integer.parseInt(request.getParameter("venuePrice"));
	    String dateString = request.getParameter("date");
	    java.sql.Date serviceDate = java.sql.Date.valueOf(dateString);
	    int venueId = Integer.parseInt(request.getParameter("venueId"));

	    try {
	        ArrayList<Vendor> availablePhotography = (ArrayList<Vendor>) dbOperation.getPhotographers(serviceDate);
	        ArrayList<Vendor> availableCatering = (ArrayList<Vendor>) dbOperation.getCatering(serviceDate);
	        
	        request.setAttribute("photography", availablePhotography);
	        request.setAttribute("catering", availableCatering);
	        request.setAttribute("venueId", venueId);
	        request.setAttribute("venuePrice", venuePrice);
	        request.setAttribute("event", eventType);
	        
	        request.getRequestDispatcher("addservice.jsp").forward(request, response);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	private void handleCheckout(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String eventType = request.getParameter("eventType");
	    int venue = Integer.parseInt(request.getParameter("venue"));
	    int venuePrice = Integer.parseInt(request.getParameter("venuePrice"));
	    String dateString = request.getParameter("date");
	    
	    String photographerId = request.getParameter("selectedPhotographers");
	    int selectedPhotographerId = -1;
	    int photographyPrice = 0;
	    if (photographerId != null && !photographerId.isEmpty()) {
	        selectedPhotographerId = Integer.parseInt(photographerId);
	        photographyPrice = Integer.parseInt(request.getParameter("pricePhoto_" + selectedPhotographerId));
	    }
	    
	    String caterId = request.getParameter("selectedCaterings");
	    int selectedCateringId = -1;
	    int cateringPrice = 0;
	    if (caterId != null && !caterId.isEmpty()) {
	        selectedCateringId = Integer.parseInt(caterId);
	        cateringPrice = Integer.parseInt(request.getParameter("priceCatering_" + selectedCateringId));
	    }
	    
	    double total = venuePrice + photographyPrice + (double) cateringPrice;
	    
	    EventDetails eventDetails = new EventDetails();
	    eventDetails.setCateringPrice(cateringPrice);
	    eventDetails.setPhotographyPrice(photographyPrice);
	    eventDetails.setEventType(eventType);
	    eventDetails.setDateString(dateString);
	    eventDetails.setEstimatedPrice(total);
	    eventDetails.setVenueId(venue);
	    eventDetails.setVenuePrice(venuePrice);
	    eventDetails.setCateringId(selectedCateringId);
	    eventDetails.setPhotoGraphyId(selectedPhotographerId);
	    
	    request.setAttribute("event", eventDetails);
	    
	    try {
	        request.getRequestDispatcher("checkout.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
