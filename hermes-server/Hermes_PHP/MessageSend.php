<?php
$con=mysqli_connect("localhost","id865672_admin","Admin","id865672_hermesdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}

$fk_SenderUserIDV= $_GET['fk_SenderUserIDV'];
$fk_ChatIDV= $_GET['fk_ChatIDV'];
$ContentV= $_GET['ContentV'];

$result = mysqli_query($con, " CALL sp_Message_Insert ( $fk_SenderUserIDV, $fk_ChatIDV, $ContentV) "  );

if($result === true) {
    echo 'True';
}
else{
    echo 'False';
}
mysqli_close($con);
?>