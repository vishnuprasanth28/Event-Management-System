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
import com.chainsys.model.User;
import com.chainsys.model.Venue;




@WebServlet("/UserLogs")
public class UserLogs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final DbOperation dbCheck = new DbOperation();
	

	
	public UserLogs() {
		super();
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String action = request.getParameter("action");

	    if (action != null) {
	        switch (action) {
	            case "admin":
	                adminLogin(request, response);
	                break;
	            case "register":
	                registerUser(request, response);
	                break;
	            case "user":
	                userLogin(request, response);
	                break;
	            default:
	                System.out.println("Invalid action");
	                break;
	        }
	    } else {
	        System.out.println("No action provided");
	    }
	}

	private void adminLogin(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String name = request.getParameter("Username");
	    String password = request.getParameter("Password");
	    
	    User user = new User();
	    user.setUsername(name);
	    user.setPassword(password);

	    HttpSession session = request.getSession();

	    try {
	        if (dbCheck.adminLogin(user)) {
	            session.setAttribute("admin", user.getUsername());
	            ArrayList<Venue> venueList = (ArrayList<Venue>) dbCheck.getAllVenues();
	            request.setAttribute("venueList", venueList);
	            request.getRequestDispatcher("admin.jsp").forward(request, response);
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	private void registerUser(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {
	    
	    String username = request.getParameter("username");
	    String mobile = request.getParameter("mobile");
	    String email = request.getParameter("email");
	    String confirmPassword = request.getParameter("confirmPassword");
	    
	    User user = new User();
	    user.setUsername(username);
	    user.setPassword(confirmPassword);
	    user.setMobile(mobile);
	    user.setEmail(email);

	    try {
	        dbCheck.insertUser(user);
	        response.sendRedirect("registration.jsp");
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	private void userLogin(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {
	    
	    String userName = request.getParameter("Username");
	    String userPass = request.getParameter("Password");
	    
	    User user = new User();
	    user.setUsername(userName);
	    user.setPassword(userPass);

	    HttpSession session = request.getSession();

	    try {
	        if (dbCheck.userLogin(user)) {
	            user.setUserId(dbCheck.getUserId(user));
	            session.setAttribute("user", user);
	            response.sendRedirect("index.jsp");
	        } else {
	            response.sendRedirect("login.jsp?error=true");
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

}