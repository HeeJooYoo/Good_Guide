<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$tourID = $_POST["tourID"];
	$tourNum = $_POST["tourNum"];
	$tourPlace = $_POST["tourPlace"];
	$tourExplain = $_POST["tourExplain"];
	$tourImage = $_POST["tourImage"];

	$statement = mysqli_prepare($con, "INSERT INTO TOUR_DETAIL VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE tourPlace = ?, tourImage = ?, tourExplain = ?");
	mysqli_stmt_bind_param($statement, "ssssssss",  $tourID, $tourNum, $tourPlace, $tourImage, $tourExplain, $tourPlace, $tourImage, $tourExplain);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>