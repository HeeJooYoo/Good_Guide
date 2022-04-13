<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");
	//$guideID = "mjc";
	$guideID = $_POST["guideID"];
	$statement = mysqli_prepare($con, "SELECT t.tourID, t.tourName, d.tourImage FROM TOUR_INFO t INNER JOIN TOUR_DETAIL d ON t.tourID = d.tourID AND tourNum = 1 AND t.guideID = ?");

	mysqli_stmt_bind_param($statement, "s", $guideID);
	mysqli_stmt_execute($statement);
   
   	mysqli_stmt_store_result($statement);
   	mysqli_stmt_bind_result($statement, $tourID, $tourName, $tourImage);

	$response = array();
	
	while(mysqli_stmt_fetch($statement)){
		array_push($response, array("tourID"=>$tourID, "tourName"=>$tourName, "tourImage"=>$tourImage));
	
	}
	
	echo json_encode(array("tourData"=>$response));
	mysqli_close($con);
?>