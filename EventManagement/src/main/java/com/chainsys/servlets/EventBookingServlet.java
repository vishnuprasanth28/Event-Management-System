package com.chainsys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		EventDetails eventDetails = new EventDetails();
		String action = request.getParameter("action");

		String eventType;
		String dateString;
		if (action != null) {
			switch (action) {

			case "getVenue":

				eventType = request.getParameter("eventType");
				dateString = request.getParameter("eventDate");
				java.sql.Date eventDate = java.sql.Date.valueOf(dateString);

				int capacity = Integer.parseInt(request.getParameter("visitorsCount"));
				ArrayList<Venue> availableVenues = new ArrayList<>();
				try {

					availableVenues = (ArrayList<Venue>) dbOperation.getAvailableVenues(eventDate);
					request.setAttribute("venues", availableVenues);
					request.setAttribute("event", eventType);
					request.setAttribute("date", eventDate);
					request.getRequestDispatcher("eventservice.jsp").forward(request, response);
				} catch (ClassNotFoundException | SQLException e) {

					e.printStackTrace();
				}
				break;
			case "getService":

				try {
					eventType = request.getParameter("eventType");
					int venuePrice =Integer.parseInt(request.getParameter("venuePrice"));
					dateString = request.getParameter("date");
					java.sql.Date serviceDate = java.sql.Date.valueOf(dateString);
					int venueId = Integer.parseInt(request.getParameter("venueId"));

					ArrayList<Vendor> availablePhotography = new ArrayList<>();
					ArrayList<Vendor> availableCatering = new ArrayList<>();

					availablePhotography = (ArrayList<Vendor>) dbOperation.getPhotographers(serviceDate);
					availableCatering = (ArrayList<Vendor>) dbOperation.getCatering(serviceDate);
					request.setAttribute("photography", availablePhotography);
					request.setAttribute("catering", availableCatering);
					request.setAttribute("venueId", venueId);
					request.setAttribute("venuePrice", venuePrice);
					request.setAttribute("event", eventType);
					request.getRequestDispatcher("addservice.jsp").forward(request, response);
				} catch (ClassNotFoundException | SQLException e) {

					e.printStackTrace();
				}

				break;

			case "checkout":
				 	eventType = request.getParameter("eventType");
		            int venue = Integer.parseInt(request.getParameter("venue")); 
		            int venuePrice = Integer.parseInt(request.getParameter("venuePrice"));
		            dateString = request.getParameter("date");
		            
		            String photographerId = request.getParameter("selectedPhotographers");
		            int selectedPhotographerId = -1;
		            int photographyPrice = 0;
		            if (photographerId != null && !photographerId.isEmpty()) {
		                selectedPhotographerId = Integer.parseInt(photographerId);
		                photographyPrice = Integer.parseInt(request.getParameter("pricePhoto_" + selectedPhotographerId));
		            } else {
		            	 photographyPrice = 0;
		            	 selectedPhotographerId = -1;
		            }
		            
		            String caterId = request.getParameter("selectedCaterings");
		            int selectedCateringId = -1;
		            int cateringPrice = 0;
		            if (caterId != null && !caterId.isEmpty()) {
		                selectedCateringId = Integer.parseInt(caterId);
		                cateringPrice = Integer.parseInt(request.getParameter("priceCatering_" + selectedCateringId));
		            } else {
		                selectedCateringId = -1;
			            cateringPrice = 0;
		            }
		            
		            double total = venuePrice + photographyPrice + cateringPrice;
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
			        	}catch(Exception e) {
			        		e.printStackTrace();
			        	}
				break;
				
				default: try {
					response.sendRedirect("index.jsp");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
