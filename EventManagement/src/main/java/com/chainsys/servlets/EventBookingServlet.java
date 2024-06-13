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

/**
 * Servlet implementation class EventBookingServlet
 */
@WebServlet("/EventBookingServlet")
public class EventBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbOperation dbOperation = new DbOperation();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventBookingServlet() {
		super();
	}

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

					availableVenues = dbOperation.getAvailableVenues(eventDate);
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

					availablePhotography = dbOperation.getPhotographers(serviceDate);
					availableCatering = dbOperation.getCatering(serviceDate);
					request.setAttribute("photography", availablePhotography);
					request.setAttribute("catering", availableCatering);
					request.setAttribute("venueId", venueId);
					request.setAttribute("venuePrice", venuePrice);
					request.getRequestDispatcher("addservice.jsp").forward(request, response);
				} catch (ClassNotFoundException | SQLException e) {

					e.printStackTrace();
				}

				break;

			case "checkout":
				eventType = request.getParameter("eventType");
				System.out.println(eventType);
				int venue = Integer.parseInt(request.getParameter("venue")); 
				String venuepriceStr = request.getParameter("venuePrice");
				dateString = request.getParameter("date");
				String photographerId = request.getParameter("selectedPhotographers");
				String photographyPriceStr= request.getParameter("pricePhoto");
				String caterId = request.getParameter("selectedCaterings");
				String cateringPriceStr =request.getParameter("priceCatering");
				
				venuepriceStr = venuepriceStr != null ? venuepriceStr.trim() : null;
				
				  photographyPriceStr = photographyPriceStr != null ? photographyPriceStr.trim() : null;
				  cateringPriceStr = cateringPriceStr != null ? cateringPriceStr.trim() : null;
				 int photoGraphyId = (photographerId != null && !photographerId.isEmpty()) ? Integer.parseInt(photographerId) : -1;
			        int cateringId = (caterId != null && !caterId.isEmpty()) ? Integer.parseInt(caterId) : -1;

			        int VenuePrice = (venuepriceStr != null && !venuepriceStr.isEmpty()) ? Integer.parseInt(venuepriceStr) : -1;
			        int photographyPrice = (photographyPriceStr != null && !photographyPriceStr.isEmpty()) ? Integer.parseInt(photographyPriceStr) : -1;
			        int cateringPrice = (cateringPriceStr != null && !cateringPriceStr.isEmpty()) ? Integer.parseInt(cateringPriceStr) : -1;
			        double total;
			        if (photographyPrice>0 && cateringPrice>0) {
			        	total=photographyPrice+cateringPrice+VenuePrice;
			        	eventDetails.setPhotographyPrice(photographyPrice);
			        	eventDetails.setCateringPrice(cateringPrice);
			        	
			        }else if(photographyPrice<0) {
			        	total=cateringPrice+VenuePrice;
			        	eventDetails.setCateringPrice(cateringPrice);
			        	eventDetails.setPhotographyPrice(photographyPrice);
			        	
			        }else if(cateringPrice<0) {
			        	total=photographyPrice+VenuePrice;
			        	eventDetails.setCateringPrice(cateringPrice);
			        	eventDetails.setPhotographyPrice(photographyPrice);
			        }else {
			        	total=VenuePrice;
			        	eventDetails.setPhotographyPrice(photographyPrice);
			        	eventDetails.setCateringPrice(cateringPrice);
			        }
			        eventDetails.setEventType(eventType);
			        eventDetails.setDateString(dateString);
			        eventDetails.setEstimatedPrice(total);
			        eventDetails.setVenueId(venue);
			        eventDetails.setVenuePrice(VenuePrice);
			        eventDetails.setCateringId(cateringId);
			        eventDetails.setPhotoGraphyId(photoGraphyId);
			        request.setAttribute("event", eventDetails);
			       

			        response.sendRedirect("checkout.jsp");
				break;
				
				default: response.sendRedirect("index.jsp");
			}
		}
	}
}
