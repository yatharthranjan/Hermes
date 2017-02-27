<?php

session_start();
require("select_query.php");
 $userInfo= $_SESSION['user_id'];

if(isset($_POST['friendInfo']))
{

$friendInfo=$_POST['friendInfo'];
$query="select user_id,friend_id ,msg from chatbox where (user_id=$userInfo and friend_id=$friendInfo) 
or (user_id=$friendInfo and friend_id=$userInfo) order by messagetime" ;
	echo select_query($query);
}
elseif(!empty($_POST['search_term']))
{
	
$search_term=$_POST['search_term'];
	
	$query="select user_id,username from users where user_id<>$userInfo and username like '$search_term%' limit 10";
	echo select_query($query);
}
elseif(!empty($_POST['load_profile']))
{
	
$user_id=$_POST['load_profile'];
	
	$query="select * from users where user_id=$user_id";
	echo select_query($query);
}
elseif(!empty($_POST['send_request']))
{
	$friend_id=$_POST['send_request'];
 	$userInfo= $_SESSION['user_id'];
$query="select id from connections where user_id=$userInfo and friend_id=$friend_id";
	$arr=select_query($query);
	if($arr==="empty")
	{
			$query="insert into connections(user_id,friend_id,connection) values($userInfo,$friend_id,0)";
			echo insert_query($query,$friend_id);
	}
	else
	{
				$arr=array("already_requested",$friend_id);

					echo $jsonstr=json_encode($arr);

	}
}
else
	{
die("empty");
}



function insert_query($query,$friend_id)
	{
		require("database_connection.php");

		if($result=$pdo->exec($query))
			{
				
				$arr=array("sent",$friend_id);

					echo $jsonstr=json_encode($arr);

				
				
			}
			else
			{
				var_dump($pdo->errorInfo());	
			}
		
		
		
	}
?>