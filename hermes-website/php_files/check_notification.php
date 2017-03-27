	if(isset($_POST['sendRequestData']))
<?php

session_start();
require("select_query.php");

$user_id=$_SESSION['user_id'];

$query="select * from notification where friend_id=$user_id order by time";
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