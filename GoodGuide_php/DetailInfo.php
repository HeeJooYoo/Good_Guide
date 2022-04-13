<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$tourID = $_POST["tourID"];
	$statement = mysqli_prepare($con, "SELECT tourNum, tourPlace, tourImage, tourExplain FROM TOUR_DETAIL WHERE tourID = ? ORDER BY tourNum");

	mysqli_stmt_bind_param($statement, "s", $tourID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $tourNum, $tourPlace, $tourImage, $tourExplain);

	$response = array();
	//$response["success"] = false;

	while(mysqli_stmt_fetch($statement)) {
		array_push($response, array("tourNum"=>$tourNum, "tourPlace"=>$tourPlace, "tourImage"=>$tourImage, "tourExplain"=>$tourExplain));
	}

	echo json_encode(array("tourDetailData"=>$response));
	mysqli_close($con);
?>