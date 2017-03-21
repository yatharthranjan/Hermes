<?php
$con=mysqli_connect("localhost","id865672_admin","Admin","id865672_hermesdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}

$user_username = $_GET['user_username'];
$user_password = $_GET['user_password'];

$result = mysqli_query($con,"CALL sp_User_Select($user_username,$user_password)");


if($result == true) {
    $emparray = array();
    while($row =mysqli_fetch_assoc($result))
    {
        $emparray[] = $row;
    }
    echo json_encode($emparray);
}
else{
     echo "FALSE";
   // echo '{"query_result":"FAILURE"}';
    
}
mysqli_close($con);
?>