<?php
$username=$_POST['username'];
$password=$_POST['password'];

try
{
$pdo=new PDO("mysql:dbname=chatbox;host=localhost","root","");
}
catch(PDOException $e)
{
	echo $e->getMessage();
	die();
}
$query="select username from users where username='$username' and password='$password'";
if($result=$pdo->query($query))
{
	$row=$result->fetch(PDO::FETCH_ASSOC);
	if(count($row)>0)
	{
	echo "successfully login";	
	echo count($row['username']);
	}
	else
	{
		echo "Invalid login";
	}
		
	
	
}
else
{
print_r($pdo->errorInfo())	;
}


?>