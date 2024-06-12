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

					dateString = request.getParameter("date");
					java.sql.Date serviceDate = java.sql.Date.valueOf(dateString);
					int venueId = Integer.parseInt(request.getParameter("venueId"));

					ArrayList<Vendor> availablePhotography = new ArrayList<>();
					ArrayList<Vendor> availableCatering = new ArrayList<>();

					availablePhotography = dbOperation.getPhotographers(serviceDate);
					availableCatering = dbOperation.getCatering(serviceDate);
					request.setAttribute("photography", availablePhotography);
					request.setAttribute("catering", availableCatering);
					request.getRequestDispatcher("addservice.jsp").forward(request, response);
				} catch (ClassNotFoundException | SQLException e) {

					e.printStackTrace();
				}

				break;

			case "checkout":
				eventType = request.getParameter("eventType");

				dateString = request.getParameter("date");
				String photographerId = request.getParameter("selectedPhotographers");
				String photographyPrice= request.getParameter("pricePhoto");
				String caterId = request.getParameter("selectedCaterings");
				// if (photographerId!= null && caterId!= null ) {
				int photoGraphyId = Integer.parseInt(photographerId);
				int cateringId = Integer.parseInt(caterId);
				System.out.println(photoGraphyId);
				System.out.println(cateringId);
				// }else {

				// }

				break;
			}
		}
	}
}
