<?php
	require ("database_connection.php");

$query="select user_id,name, login,lastseen from users where user_id 
in(select friend_id from connections where user_id=".$_SESSION['user_id']." )";

if($result=$pdo->query($query))
		{
			$arr=NULL;
			while($row=$result->fetch(PDO::FETCH_ASSOC))
				{
					$arr[]=$row;
				

				}
			if(count($arr)>0)
			{
				for($i=0;$i<count($arr);$i++)
				echo "<li id='".['user_id']."'><span>".$row['name']."</span><span class='activity ".
					(!empty($row['login'])?"online":"lastseen")."'></span></li>";
				
			}
			else{
				
				echo "<li id='empty_list'><span>No friends To chat</span><span></span></li>";
			}
				//$jsonstr=json_encode($arr);
			
				
		}
		else
		{
		var_dump($pdo->errorInfo());	
			
		}

unset($pdo);






?>