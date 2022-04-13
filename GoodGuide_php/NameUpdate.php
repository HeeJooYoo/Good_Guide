<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$userID = $_POST["userID"];
	$userName = $_POST["userName"];

	$statement = mysqli_prepare ($con, "UPDATE GUIDE_MEMBER SET userName = ? WHERE userID = ?");
	mysqli_stmt_bind_param($statement, "ss", $userName, $userID);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>