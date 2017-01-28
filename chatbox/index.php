<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Untitled Document</title>
<link rel="stylesheet"  href="css/chatbox.css" />
</head>

<body>
<div id="welcome_page">
<h2>Welocome to Hermes</h2>

<form id="form_2" name="from_2" action="php/verify_user.php" method="post">
<label>User Name:-</label><input type="text" id="name"  name="username" /><br>
<label>Password:-</label><input type="text" id="password" name="" /><br>
<label>Captcha:-</label><input type="text" id=captcha" /><br>


<input type="submit" id="submit"  />
</form>
<a href="forgot_password" id ="forgot_passowrd">Forgot Password?</a>&nbsp;&nbsp;
<a href="new_user" id ="new_user">New User</a>

</div>
<!--<script src="ajax.js" ></script>
-->

</body>
</html>