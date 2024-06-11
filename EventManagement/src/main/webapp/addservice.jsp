<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.Base64" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="com.chainsys.model.Vendor" %>
<%@ page import="com.chainsys.util.ConnectionUtil" %>
<%@ page import="com.chainsys.dao.DbOperation" %>
<%@ page import="com.chainsys.model.Venue" %>
 <%@ page import="java.util.*" %>
    <%@ page import="com.chainsys.model.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Additional Services</title>
 <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <style>
        .card {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
            margin: 10px;
            width: 300px;
        }
        
        .box{
        display: flex;
        }
        
        .card img {
            max-width: 100%;
            height: auto;
        }
        .card h2 {
            font-size: 18px;
            margin-top: 10px;
            margin-bottom: 5px;
        }
        .card p {
            margin: 5px 0;
        }
    </style>
</head>
<body>
<h1>Photography</h1>
<div class="box">


<%
    try {
    	ArrayList<Vendor> availablePhotographer = (ArrayList<Vendor>)request.getAttribute("photography");
        for (Vendor photographer : availablePhotographer) {
        	  String base64Image = Base64.getEncoder().encodeToString(photographer.getImage());
%>
            <div class="card">
            
                <img src="data:image/jpeg;base64,<%=base64Image%>" alt="profile Image">
                <h2><%=photographer.getVendorName()%></h2>
                <p>Contact Phone : <%=photographer.getContact()%></p>
                <p>price : <%=photographer.getPrice()%></p>
               
              <form action="EventBookingServlet" method="post">
                    <input type="hidden" name="date" value="<%= request.getParameter("date") %>">
                    <input type="hidden" name="eventType" value="<%= request.getParameter("event") %>">
                     <input type="checkbox" name="selectedPhotographers" value="<%= photographer.getVendorId() %>">
                </form>
            </div>
       
<%
        }
    } catch (Exception e) {
        out.println("Error retrieving venues: " + e.getMessage());
    }
%>
</div>
<h1>Wine and Dine</h1>
<div class="box">


<%
    try {
    	ArrayList<Vendor> availableCatering = (ArrayList<Vendor>)request.getAttribute("catering");
        for (Vendor catering : availableCatering) {
        	  String base64Image = Base64.getEncoder().encodeToString(catering.getImage());
%>
            <div class="card">
            
                <img src="data:image/jpeg;base64,<%=base64Image%>" alt="profile Image">
                <h2><%=catering.getVendorName()%></h2>
                <p>Contact Phone : <%=catering.getContact()%></p>
                <p>price : <%=catering.getPrice()%></p>
               
               <form action="EventBookingServlet" method="post">
                    <input type="hidden" name="date" value="<%= request.getParameter("date") %>">
                    <input type="hidden" name="eventType" value="<%= request.getParameter("event") %>">
                      <input type="checkbox" name="selectedCaterings" value="<%= catering.getVendorId() %>">
                </form>
            </div>
<%
        }
    } catch (Exception e) {
        out.println("Error retrieving venues: " + e.getMessage());
    }
%>
</div>

<form action="EventBookingServlet" method="post">
    <input type="hidden" name="date" value="<%= request.getParameter("date") %>">
    <input type="hidden" name="eventType" value="<%= request.getParameter("event") %>">
    <input type="submit" class="btn btn-primary" value="Continue">
</form>
</body>
</html>