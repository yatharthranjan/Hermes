<?php
$con=mysqli_connect("localhost","id865672_admin","Admin","id865672_hermesdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}

$ChatIDV= $_GET['ChatIDV'];
$userIDV= $_GET['userIDV'];

$result = mysqli_query($con, "CALL sp_Chat_Update( $ChatIDV, $userIDV)" );

if($result == true) {
        echo 'True';
}
else{ 
   echo 'False';
}
mysqli_close($con);
?>