<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.chainsys.model.User" %>
<%@ page import="com.chainsys.model.EventDetails" %>
<%@ page import="com.chainsys.model.Venue" %>
<%@ page import="com.chainsys.model.Vendor" %>
 <%@ page import="java.util.*" %>
    
    <%

User user = (User) session.getAttribute("user");
boolean isLoggedIn = (user != null);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="#"><img src="images/epic-events-high-resolution-logo.png" alt="Company Logo" style="height: 40px;"></a>
    <span class="navbar-text mr-auto">EPIC EVENTS</span>
    <ul class="navbar-nav ml-auto">
      <li class="nav-item">
        <a class="nav-link" href="#">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="service.jsp">Services</a>
      </li>
       <% if (!isLoggedIn) { %>
                <!-- Show Sign Up and Sign In only if the user is not logged in -->
                <li class="nav-item">
                    <a class="nav-link" href="registration.jsp">Sign Up</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Sign In
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="signIn.jsp">Admin</a>
                        <a class="dropdown-item" href="user.jsp">User</a>
                    </div>
                </li>
                <% } else { %>
                <!-- Show user's name if logged in -->
                <li class="nav-item">
                    <span class="nav-link">Hii <%= user.getUsername() %></span>
                    
                </li>
                <li class="nav-item">
                      <form action="LogOutServlet" method="post">
                        <button type="submit" class="btn btn-primary">Logout</button>
                    </form>
                </li>
                <% } %>
            </ul>
  </div>
</nav>



    <div class="container">
    <%EventDetails eventDetails =(EventDetails)request.getAttribute("event"); %>
       
        <div>
            <h2>User Information</h2>
            
            <p>User Name: <%= user.getUsername() %></p>
        </div>
        <div>
            <h2>Event Information</h2>
            <p>Event Type: <%= eventDetails.getEventType()%></p>
            <p>Date: <%= eventDetails.getDateString() %></p>
        </div>
        <div>
            <h2>Selected Photography </h2>
            <p>Photography Vendor ID: <%= eventDetails.getPhotoGraphyId() %></p>
            <p>Photography Vendor Price: <%= eventDetails.getPhotographyPrice() %></p>
        </div>
        <div>
            <h2>Selected Catering</h2>
            <p>Catering ID: <%= eventDetails.getCateringId() %></p>
            <p>Catering Price: <%= eventDetails.getCateringPrice() %></p>
        </div>
        <div>
            <h2>Estimated Amount</h2>
            
            <p>Total: <%= eventDetails.getEstimatedPrice() %></p>
        </div>
    </div>
</body>
</html>
