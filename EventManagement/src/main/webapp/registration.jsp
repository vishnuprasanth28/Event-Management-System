<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registration Form</title>
<style>
  body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f8f9fa;
  }
  
  .container {
    max-width: 500px;
    margin: 50px auto;
    background: #fff;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  }
  
  h2 {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .form-group {
    margin-bottom: 20px;
  }
  
  label {
    font-weight: bold;
    display: block;
    margin-bottom: 5px;
  }
  
  input[type="text"],
  input[type="email"],
  input[type="password"] {
    width: 100%;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
    transition: border-color 0.3s;
  }
  
  input[type="text"]:focus,
  input[type="email"]:focus,
  input[type="password"]:focus {
    border-color: #007bff;
  }
  
  input[type="submit"] {
    width: 100%;
    padding: 10px;
    border: none;
    border-radius: 5px;
    background-color: #007bff;
    color: #fff;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  input[type="submit"]:hover {
    background-color: #0056b3;
  }
  
  .error {
    color: red;
    font-size: 12px;
  }
  
  @media screen and (max-width: 600px) {
    .container {
      width: 90%;
    }
  }
</style>
</head>
<body>

<div class="container">
  <h2>Registration Form</h2>
 
  <form action="UserLogs" method="post">
    <div class="form-group">
      <label for="username">User Name:</label>
      <input type="text" id="username" name="username" required>
    </div>
    <div class="form-group">
      <label for="mobile">Mobile No:</label>
      <input type="tel" id="mobile" name="mobile" pattern="[0-9]{10}" required>
    </div>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" pattern="^(?=.*[a-z])+@(?=.*[a-z])+.(?=.*[a-z]){6,20}$" required>
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[&^%$#@]).{5,}" required>
    </div>
    <div class="form-group">
      <label for="confirmPassword">Confirm Password:</label>
      <input type="password" id="confirmPassword" name="confirmPassword" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[&^%$#@]).{5,}" required>
    </div>
     <input type="hidden" name="action" value="register">
    <button type="submit">Register</button>
  </form>
</div>

<script>
  const form = document.getElementById('registrationForm');
  const password = document.getElementById('password');
  const confirmPassword = document.getElementById('confirmPassword');

  form.addEventListener('submit', function(event) {
    if (password.value !== confirmPassword.value) {
      alert("Passwords do not match");
      event.preventDefault();
    }
  });
</script>

</body>
</html>
