<?php
require( "database_connection.php" );
$user = $_SESSION[ 'user_id' ];
$query = "CALL sp_ChatAll_select($user)";

if ( $result = $pdo->query( $query ) ) {
	$arr = NULL;
	while ( $row = $result->fetch( PDO::FETCH_ASSOC ) ) {
		$arr[] = $row;


	}
	if ( count( $arr ) > 0 ) {


		for ( $i = 0; $i < count( $arr ); $i++ )

		{
			echo "<li id='" . $arr[ $i ][ 'ChatID' ] . "' title=''><span>FRENDID:" . $arr[ $i ][ 'Name' ] . " </span></li>";

		}


	} else {

		echo "<li id='empty_list'><span>No friends To chat</span><span></span></li>";
	}


} else {
	var_dump( $pdo->errorInfo() );

}
//echo $p=hash("Sha256","yatharth");	
unset( $pdo );






?>