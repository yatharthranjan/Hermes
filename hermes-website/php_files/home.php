<?php
session_start();
if(empty($_SESSION['username'])&& empty($_SESSION['name']))
   {
header("location:../");
}
?>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>hermes</title>
<link rel="stylesheet" type="text/css" href="../css/main.css"  />
<link rel="stylesheet" type="text/css" href="../css/home.css" />
	<script src="../script/chat.js"></script>
	<script src="../script/dynamic_data.js"></script>
</head>

<body id="body1">
<div id="header">
            <div id="chatLogo">
            <a href="../index.php"><img src="../images/chat.png" id="logo" alt="image"/></a>
            </div>
	<h1 id="title">Welcome to Hermes <span id="userName"><?php echo $_SESSION['name'] ;?> !</span></h1>
           
            <h6 id="subtitle">Your Favourite Chat System</h6>
	<h2> <span id="userInfo" hidden="<?php echo $_SESSION['user_id']; ?>" title="username"><?php echo $_SESSION['username']?></span>  </h2>
   
           <
           </div>
            
            
<div id="subheader_div">
		<div id="profile_picture"></div>
                  <div id="search_div">
<form action="#" method="post">
<input type="search" id="search_box" name="search_box" placeholder="Search Username..." />
<input type="submit" id="search" name="search" style="display:none" /> 
</form>
					  <ul id="search_result"></ul>

</div>
	<div id="friend_request_div" title="Friend Request">
	<div id="count_request_div" class="count_div"><?php 
	
$user_id=$_SESSION['user_id'];
require("select_query.php");

$query="select friend_id from connections where friend_id=$user_id and connection=0 order by time";
$requests= select_query($query);
if($requests!=="empty")
{
	if(isset($_POST['sendRequestData']))
	{
	echo $requests;	
	}
	else
	{
	echo count(json_decode($requests));

	}

}
else
{
	echo "0";
}
		?>
	</div>
		
	</div>
   <div id="notification_div" title="Notifications">
	   <div id="count_notification_div" class="count_div"><?php 
		   
$user_id=$_SESSION['user_id'];

$query="select friend_id from connections where user_id=$user_id and connection=1 and timing=1 order by time limit 10";
	$requests= select_query($query);
if($requests!=="empty")
{
	
	echo count(json_decode($requests));
} 
else
{
	echo "0";
}
?>
		   
		   
		   
		   
		   
		   </div>

   </div>
                   <div id="menu_div">
								<ul id="menu" >
									<li id="submenu_li">
											<ul id="submenu">
												<li>New Chat</li>
												<li>Profile</li>
												<li>Setting</li>
												<li><a href="logout.php">Logout</a></li>

											</ul>

									</li>


								   </ul>

					
					</div>
<ul id="showRequestList"></ul>
</div>

<div id="chat_div">
	<ul id="contacts">

		<?php
		
		
	//require("load_contacts.php");

	?>
	</ul>
	<div id="friend_info">
			<div id="friend_profile_picture"></div>
		<div id="friend_name">Start Chat</div>
	
	
	
	
	
	</div>
	<ul id="chat">
		<li><img src="../images/live-chat.png" /></li>
		
	</ul>
	<div id="message_div">
		<form action="#" method="post">
<input type="text" id="message_box" name="message_box" placeholder="Write here......" />
<input type="submit" id="message" name="message" style="display:none" /> 
</form>
	</div>
	

</div>
	<div id="profile_view" ></div>
</body>
</html>
