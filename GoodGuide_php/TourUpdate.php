<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$tourID = $_POST["tourID"];
	$tourName = $_POST["tourName"];
	$tourPrice = $_POST["tourPrice"];
	$tourCity = $_POST["tourCity"];
	$tourCountry = $_POST["tourCountry"];
	$minNum = $_POST["minNum"];
	$maxNum = $_POST["maxNum"];
	$meetPlace = $_POST["meetPlace"];
	$meetTime = $_POST["meetTime"];
	$estTime = $_POST["estTime"];
	$mapImage = $_POST["mapImage"];

	$statement = mysqli_prepare($con, "UPDATE TOUR_INFO SET tourName = ?, tourPrice = ?, tourCity = ?, tourCountry = ?, minNum = ?, maxNum = ?, meetPlace = ?, meetTime = ?, estTime = ?, mapImage = ? WHERE tourID = ?");
	mysqli_stmt_bind_param($statement, "sssssssssss", $tourName, $tourPrice, $tourCity, $tourCountry, $minNum, $maxNum, $meetPlace, $meetTime, $estTime, $mapImage, $tourID);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>