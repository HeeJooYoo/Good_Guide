<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$tourID = $_POST["tourID"];
	$statement = mysqli_prepare($con, "SELECT * FROM TOUR_INFO WHERE tourID = ?");

	mysqli_stmt_bind_param($statement, "s", $tourID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $tourID, $tourName, $tourPrice, $tourCity, $tourCountry, $minNum, $maxNum, $meetPlace, $meetTime, $estTime, $mapImage, $guideID);

	$response = array();
	$response["success"] = false;

	while(mysqli_stmt_fetch($statement)) {
		$response["success"] = true;
		$response["tourID"] = $tourID;
		$response["tourName"] = $tourName;
		$response["tourPrice"] = $tourPrice;
		$response["tourCity"] = $tourCity;
		$response["tourCountry"] = $tourCountry;
		$response["minNum"] = $minNum;
		$response["maxNum"] = $maxNum;
		$response["meetPlace"] = $meetPlace;
		$response["meetTime"] = $meetTime;
		$response["estTime"] = $estTime;
		$response["mapImage"] = $mapImage;
		$response["guideID"] = $guideID;
	}

	echo json_encode($response);
?>