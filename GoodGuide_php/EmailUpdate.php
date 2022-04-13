<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$userID = $_POST["userID"];
	$userEmail = $_POST["userEmail"];

	$statement = mysqli_prepare ($con, "UPDATE GUIDE_MEMBER SET userEmail = ? WHERE userID = ?");
	mysqli_stmt_bind_param($statement, "ss", $userEmail, $userID);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>