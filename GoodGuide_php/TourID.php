<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$guideID = $_POST["guideID"];
	$tourID = $_POST["tourID"];

	$statement = mysqli_prepare ($con, "SELECT COUNT(tourName) FROM TOUR_INFO WHERE guideID = ?");

	mysqli_stmt_bind_param($statement, "s", $guideID);
	mysqli_stmt_execute($statement);
	$cnt = null;
	mysqli_stmt_bind_result($statement, $cnt);
	
	$response = array();
	$response["success"] = false;

	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["count"] = $cnt;
	}

	echo json_encode($response);
?>