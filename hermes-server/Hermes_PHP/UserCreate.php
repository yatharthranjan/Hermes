<?php
$con=mysqli_connect("localhost","id865672_admin","Admin","id865672_hermesdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}

$UsernameV= $_GET['UsernameV'];
$PasswordV= $_GET['PasswordV'];
$EmailV= $_GET['EmailV'];
$NameV= $_GET['NameV'];

$result = mysqli_query($con, "CALL sp_User_Insert( $UsernameV, $PasswordV, $EmailV, $NameV)" );

if($result == true) {
    echo 'True';
}
else{
    echo 'False';
}
mysqli_close($con);
?>