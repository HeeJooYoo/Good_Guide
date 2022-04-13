<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");
	
	$guideID = $_POST["guideID"];

	$statement = mysqli_prepare ($con, "SELECT tourID, tourName FROM TOUR_INFO WHERE guideID = ?");

	mysqli_stmt_bind_param($statement, "s", $guideID);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $tourID, $tourName);

	$response = array();
		
	while(mysqli_stmt_fetch($statement)){
		array_push($response, array("tourID"=>$tourID, "tourName"=>$tourName));
	}

	echo json_encode($response);
	mysqli_close($con);
?>