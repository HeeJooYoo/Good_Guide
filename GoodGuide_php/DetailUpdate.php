<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$tourID = $_POST["tourID"];
	$tourNum = $_POST["tourNum"];
	$tourPlace = $_POST["tourPlace"];
	$tourExplain = $_POST["tourExplain"];
	$tourImage = $_POST["tourImage"];

	$statement = mysqli_prepare($con, "UPDATE TOUR_DETAIL SET tourPlace = ?, tourExplain = ?,  tourImage = ? WHERE tourID = ? AND tourNum = ?");
	mysqli_stmt_bind_param($statement, "sssss", $tourPlace, $tourExplain, $tourImage, $tourID, $tourNum);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>