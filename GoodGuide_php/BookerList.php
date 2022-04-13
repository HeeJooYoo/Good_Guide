<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");
	//$guideID = "daeun";
	$guideID = $_POST["guideID"];

	$statement = mysqli_prepare ($con, "SELECT t.tourID, t.tourName, r.date FROM TOUR_INFO t INNER JOIN RESERV_INFO r ON t.tourID = r.tourID AND t.guideID = ? GROUP BY t.tourID, r.date ORDER BY r.date");

	mysqli_stmt_bind_param($statement, "s", $guideID);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $tourID, $tourName, $date);

	$response = array();
		
	while(mysqli_stmt_fetch($statement)){
		array_push($response, array("tourID"=>$tourID, "tourName"=>$tourName, "date"=>$date));
	}

	echo json_encode($response);
	mysqli_close($con);
?>