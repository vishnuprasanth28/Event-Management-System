package com.chainsys.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.dao.DbOperation;
import com.chainsys.model.EventDetails;
import com.chainsys.model.User;

/**
 * Servlet implementation class PaymentServlet
 */
@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final DbOperation dbActivity = new DbOperation();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	 
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("user");
		int userId =user.getUserId();
		String eventType = request.getParameter("event");
        int venue = Integer.parseInt(request.getParameter("venueId")); 
        int photographyId = Integer.parseInt(request.getParameter("photographyVendorId"));
        int cateringId= Integer.parseInt(request.getParameter("cateringVendorId"));
        String dateString = request.getParameter("date");
        
        EventDetails eventDetails = new EventDetails();
        eventDetails.setCateringId(cateringId);
        eventDetails.setDateString(dateString);
        eventDetails.setEventType(eventType);
        eventDetails.setPhotoGraphyId(photographyId);
        eventDetails.setUserId(userId);
        eventDetails.setVenueId(venue);
        
        try {
			dbActivity.storeEventDetails(eventDetails);
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
        
        response.sendRedirect("payment.jsp");
        
	}

}
