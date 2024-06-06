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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.chainsys.dao.DbOperation;
import com.chainsys.model.Vendor;
import com.chainsys.model.Venue;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
@MultipartConfig
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Venue venue = new Venue(); 
    Vendor vendor = new Vendor();
    DbOperation dbAction = new DbOperation();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		InputStream inputStream = null;
		
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		 if (action != null) {
		        switch (action) {
		            case "addVenue":
		            			String name = request.getParameter("name");
		            			String address = request.getParameter("address");
		            			int capacity =Integer.parseInt(request.getParameter("capacity"));
		            			String contact = request.getParameter("Contact");
		            			int amount = Integer.parseInt(request.getParameter("price"));
		            			Part filePart = request.getPart("venue_image");
		            	        if (filePart != null) {
		            	            // Debugging messages
		            	            System.out.println(filePart.getName());
		            	            System.out.println(filePart.getSize());
		            	            System.out.println(filePart.getContentType());
		            	            // Obtain the input stream of the uploaded file
		            	            inputStream = filePart.getInputStream();
		            	        }
		            	        byte[] images =null;
		            	        if(inputStream !=null) {
		            	        	images = inputStream.readAllBytes();
		            	        }
		            	        
		            	        venue.setVenueName(name);
		            	        venue.setAddress(address);
		            	        venue.setCapacity(capacity);
		            	        venue.setContactPhone(contact);
		            	        venue.setPrice(amount);
		            	        venue.setImage(images);
					try {
						dbAction.addVenue(venue);
						}
					 catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
					    ArrayList<Venue> venueList = dbAction.getAllVenues();
					    System.out.println(venueList);
					    request.setAttribute("venueList", venueList);
					    request.getRequestDispatcher("admin.jsp").forward(request, response);
					} catch (ClassNotFoundException | SQLException e) {
					    e.printStackTrace();
					    // Handle exception appropriately
					}

						break;
						
		            case "delete":
		                try {
		                    int idToDelete = Integer.parseInt(request.getParameter("deleteid"));
		              
		                    dbAction.deleteVenue(idToDelete);
		                } catch (NumberFormatException | ClassNotFoundException | SQLException e) {
		                    e.printStackTrace();
		                    
		                }	
		                break;
		                
		            case "addVendor" :
		            	 String vendorName = request.getParameter("vendorName");
		                 String vendorContact = request.getParameter("vendorContact");
		                 String vendorType = request.getParameter("vendorType");
		                 vendor.setVendorName(vendorName);
		                 vendor.setContact(vendorContact);
		                 vendor.setVendorType(vendorType);
		                 
					try {
						dbAction.addVendor(vendor);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	}

}
}
}