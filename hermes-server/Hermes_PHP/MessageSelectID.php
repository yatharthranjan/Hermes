<?php
$con=mysqli_connect("localhost","id865672_admin","Admin","id865672_hermesdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}

$MessageIDV= $_GET['MessageIDV'];

$result = mysqli_query($con, "CALL sp_Message_Select( $MessageIDV)" );

if($result == true) {
    $emparray = array();
    while($row =mysqli_fetch_assoc($result))
    {
        $emparray[] = $row;
    }
    echo json_encode($emparray);
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>