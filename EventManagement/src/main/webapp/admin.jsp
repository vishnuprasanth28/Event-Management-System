<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.chainsys.model.Venue"%>
<%@ page import="com.chainsys.model.EventDetails"%>
<%
response.setHeader("cache-control", "no-cache,no-store,must-revalidate");
if (session.getAttribute("admin") == null) {
	response.sendRedirect("login.jsp");
	return;
}
String admin = null;

Cookie[] cookies = request.getCookies();
if (cookies != null) {
	for (Cookie cookie : cookies) {
		if ("admin".equals(cookie.getName())) {
	admin = cookie.getValue();
	break;
		}
	}
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Page</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.tab-content {
	padding: 20px;
}

.nav-link {
	font-size: 16px;
	font-weight: bold;
}

.tab-pane {
	padding-top: 20px;
}

.card {
	margin-bottom: 20px;
	border: none;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.card-body {
	padding: 20px;
}

.card-title {
	font-size: 20px;
	font-weight: bold;
	margin-bottom: 10px;
}

.card-text {
	margin-bottom: 5px;
}

.btn-submit {
	background-color: #007bff;
	color: #fff;
	border: none;
	padding: 10px 20px;
	font-size: 16px;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.btn-submit:hover {
	background-color: #0056b3;
}

.form-group {
	margin-bottom: 20px;
}

label {
	font-weight: bold;
}
</style>
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item"><a class="nav-link active"
				id="mystocks-tab" data-toggle="tab" href="#mystocks" role="tab"
				aria-controls="mystocks" aria-selected="true">Profile</a></li>
			<li class="nav-item"><a class="nav-link" id="explore-tab"
				data-toggle="tab" href="#explore" role="tab" aria-controls="explore"
				aria-selected="false">Add Venue</a></li>
			<li class="nav-item"><a class="nav-link" id="watchlists-tab"
				data-toggle="tab" href="#watchlists" role="tab"
				aria-controls="watchlists" aria-selected="false">Add Vendors</a></li>
			<li class="nav-item"><a class="nav-link" id="orders-tab"
				data-toggle="tab" href="#orders" role="tab" aria-controls="orders"
				aria-selected="false">Orders</a></li>
		</ul>
		<div class="tab-content" id="myTabContent">
			<div class="tab-pane fade show active" id="mystocks" role="tabpanel"
				aria-labelledby="mystocks-tab">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title"><%=session.getAttribute("admin")%></h5>
						<h3>Epic Events</h3>
						<form action="LogOutServlet" method="post">
							<button type="submit" class="btn btn-primary">Logout</button>
						</form>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="explore" role="tabpanel"
				aria-labelledby="explore-tab">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Add Venue</h5>

						<form action="Admin" method="post" enctype="multipart/form-data">
							<div class="form-group">
								<label for="venueName">Venue Name:</label> <input type="text"
									id="name" name="name" class="form-control">
							</div>
							<div class="form-group">
								<label for="Address">Location:</label> <input type="text"
									id="address" name="address" class="form-control">
							</div>
							<div class="form-group">
								<label for="capacity">Capacity:</label> <input type="number"
									id="capacity" name="capacity" class="form-control">
							</div>
							<div class="form-group">
								<label for="Contact">Contact :</label> <input type="number"
									id="Contact" name="Contact" class="form-control">
							</div>
							<div class="form-group">
								<label for="Price">Price :</label> <input type="number"
									id="price" name="price" class="form-control">
							</div>
							<div class="form-group">
								<label for="venue_image">Venue Image:</label> <input type="file"
									id="venue_image" name="venue_image" class="form-control-file"
									accept="image/*" required>
							</div>
							<input type="hidden" name="action" value="addVenue">
							<button type="submit" class="btn btn-submit">Submit</button>
						</form>
						<%ArrayList<Venue> venuesList = (ArrayList<Venue>) request.getAttribute("venueList");
						if (venuesList == null || venuesList.isEmpty()) {
							%>
							<div class="alert alert-info" role="alert">
							    No venues available.
							</div>
							<%
							} else {
						
						%>
						<table class="table">
							<thead>
								<tr>
									<th>Venue Id</th>
									<th>Venue Name</th>
									<th>Address</th>
									<th>Capacity</th>
									<th>Contact</th>

									<th>Price</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>

								<%
								
								for (Venue venue : venuesList) {
								%>
								<tr>
									<td><%=venue.getVenueId()%></td>
									<td><%=venue.getVenueName()%></td>
									<td><%=venue.getAddress()%></td>
									<td><%=venue.getCapacity()%></td>
									<td><%=venue.getContactPhone()%></td>
									<td><%=venue.getPrice()%></td>
									<td>
										

										
											<div class="col-sm-12 d-flex justify-content-end mb-2 ">
											<form action="Admin" method="post">
											<input type="hidden" name="action" value="delete" class="p-2"> <input
												type="hidden" name="deleteid"
												value="<%=venue.getVenueId()%>">
											<button class="btn btn-danger" type="submit">Delete</button>
										</form>
											
                    <button class="btn btn-primary btnUpdateVenue  ml-2"
                            data-id="<%=venue.getVenueId()%>"
                            data-capacity="<%=venue.getCapacity()%>"
                            data-price="<%=venue.getPrice()%>"
                            data-phone="<%=venue.getContactPhone()%>">Update</button>
                    
                </div>
										
									</td>
								</tr>
								<%
								}
							}
								%>
							</tbody>
						</table>
						
					

					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="watchlists" role="tabpanel"
				aria-labelledby="watchlists-tab">
				<form action="Admin" method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label for="vendorName">Vendor Name:</label> <input type="text"
							id="vendorName" name="vendorName" class="form-control">
					</div>
					<div class="form-group">
						<label for="vendorContact">Contact:</label> <input type="text"
							id="vendorContact" name="vendorContact" class="form-control">
					</div>
					<div class="form-group">
						<label for="vendorType">Vendor Type:</label> <select
							id="vendorType" name="vendorType" class="form-control">

							<option value="Catering">Catering</option>
							<option value="photography">Photography</option>
							<option value="Decoration">Decoration</option>

						</select>
					</div>
					<div class="form-group">
						<label for="Price">Price :</label> <input type="number" id="price"
							name="price" class="form-control">
					</div>
					<div class="form-group">
						<label for="profile_image">Profile :</label> <input type="file"
							id="profile_image" name="profile_image" class="form-control-file"
							accept="image/*" required>
					</div>
					<input type="hidden" name="action" value="addVendor">
					<button type="submit" class="btn btn-submit">Submit</button>
				</form>

			</div>
			<div class="tab-pane fade" id="orders" role="tabpanel"
				aria-labelledby="orders-tab">
				
				</form>
						<%ArrayList<EventDetails> bookingList = (ArrayList<EventDetails>) request.getAttribute("bookedEvents");
						if (bookingList == null || bookingList.isEmpty()) {
							%>
							<div class="alert alert-info" role="alert">
							    No bookings available.
							</div>
							<%
							} else {
						
						%>
						<table class="table">
							<thead>
								<tr>
									<th>Event Id</th>
									<th>User Name</th>
									<th>Venue Name</th>
									<th>Photography</th>
									<th>Catering</th>
									<th>Event Type</th>
									<th>Event Date</th>

								</tr>
							</thead>
							<tbody>

								<%
								
								for (EventDetails events : bookingList) {
								%>
								<tr>
									<td><%=events.getEventId()  %></td>
									<td><%=events.getUserName() %></td>
									<td><%=events.getVenueName()%></td>
									<td><%=events.getPhotographyName()%></td>
									<td><%=events.getCateringName()%></td>
									<td><%=events.getEventName()%></td>
									<td><%=events.getDateString()%></td>
									<td>
										

										
											<div class="col-sm-12 d-flex justify-content-end mb-2 ">
											<form action="Admin" method="post">
											<input type="hidden" name="action" value="deleteEvent" class="p-2"> <input
												type="hidden" name="deleteid"
												value="<%= events.getEventId()%>">
											<button class="btn btn-danger" type="submit">Delete</button>
										</form>
				<% } } %>
				</div>
		</div>
	</div>

	<!-- Bootstrap and jQuery scripts -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<!-- Existing HTML code... -->

<!-- Modal for Update Form -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="updateModalLabel">Update Venue</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="updateForm" action="Admin" method="post">
                    <div class="form-group">
                        <label for="updateName">Venue Name:</label>
                        <input type="text" id="updateName" name="name" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="updateAddress">Address:</label>
                        <input type="text" id="updateAddress" name="address" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="updateCapacity">Capacity:</label>
                        <input type="number" id="updateCapacity" name="capacity" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="updateContact">Contact:</label>
                        <input type="number" id="updateContact" name="contact" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="updatePrice">Price:</label>
                        <input type="number" id="updatePrice" name="price" class="form-control">
                    </div>
                    <input type="hidden" id="updateVenueId" name="venueId">
                    <input type="hidden" name="action" value="updateVenue">
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Existing HTML continues... -->

<script>
    $(document).ready(function() {
        $('.btnUpdateVenue').on('click', function() {
            // Get data attributes from the button
            var venueId = $(this).data('id');
            var capacity = $(this).data('capacity');
            var price = $(this).data('price');
            var phone = $(this).data('phone');

            // Set values in the modal form fields
            $('#updateVenueId').val(venueId);
            $('#updateName').val($(this).closest('tr').find('td:eq(1)').text().trim()); // Example for Venue Name, adjust according to your table
            $('#updateAddress').val($(this).closest('tr').find('td:eq(2)').text().trim()); // Example for Address
            $('#updateCapacity').val(capacity);
            $('#updateContact').val(phone);
            $('#updatePrice').val(price);

            // Show the modal
            $('#updateModal').modal('show');
        });
    });
</script>

<!-- Bootstrap and jQuery scripts continue... -->
	


</body>
</html>
