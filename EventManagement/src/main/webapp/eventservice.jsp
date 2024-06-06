<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>

<%@ page import="java.util.List" %>

<%@ page import="com.chainsys.util.ConnectionUtil" %>
<%@ page import="com.chainsys.dao.DbOperation" %>
<%@ page import="com.chainsys.model.Venue" %>
<!DOCTYPE html>
<html>
<head>
    <title>Venue Details</title>
    <style>
        .card {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
            margin: 10px;
            width: 300px;
            float: left;
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

<h1>Venue Details</h1>

<%
    try {
        List<Venue> venues = DbOperation.getVenues();
        for (Venue venue : venues) {
        	  String base64Image = Base64.getEncoder().encodeToString(venue.getImage());
%>
            <div class="card">
            
                <img src="data:image/jpeg;base64,<%=base64Image%>" alt="Venue Image">
                <h2><%=venue.getVenueName()%></h2>
                <p>Address: <%=venue.getAddress()%></p>
                <p>Capacity: <%=venue.getCapacity()%></p>
                <p>Contact Phone: <%=venue.getContactPhone()%></p>
                <p>Price: <%=venue.getPrice()%></p>
            </div>
<%
        }
    } catch (Exception e) {
        out.println("Error retrieving venues: " + e.getMessage());
    }
%>

</body>
</html>
