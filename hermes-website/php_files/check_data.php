<?php
session_start();
$user_id=$_SESSION['user_id'];
require("select_query.php");


	if(isset($_POST['sendRequestData']))
	{
$query="select * from users where user_id in(select user_id from connections where friend_id=$user_id and connection=0 order by time)";

	}
if(isset($_POST['updateRequestData']))
	{
		$data=json_decode($_POST['updateRequestData']);
		$r=$data->response;
		$friend_id=$data->friend_id;
		 $friend_id;
		
		if($r==="accept")
		{
	$query="update connections set connection=1,timing=1 where friend_id=$user_id and user_id=$friend_id and connection=0";
		}
		else if($r="reject")
		{
		$query="delete from connections where friend_id=$user_id and user_id=$friend_id and connection=0";
	
		}
	}
	else if(isset($_POST['sendNotificationData']))

	{
$query="select * from users where user_id in (select friend_id from connections where user_id=$user_id
and connection=1 and timing=1 order by time)";

	}
else if(isset($_POST['seeAllNotificationData']))

	{
$query="select * from users where user_id in(select friend_id from connections where user_id=$user_id
and connection=1 and timing=0 order by time)";

	}
else if(isset($_POST['updateNotificationData']))

	{
		$friend_id=$_POST['updateNotificationData'];
$query="update connections set timing =1 where user_id=$user_id and firend_di=$friend_id";

	}
echo select_query($query);




?>