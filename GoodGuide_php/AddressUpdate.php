<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$userID = $_POST["userID"];
	$userAddress = $_POST["userAddress"];

	$statement = mysqli_prepare ($con, "UPDATE GUIDE_MEMBER SET userAddress = ? WHERE userID = ?");
	mysqli_stmt_bind_param($statement, "ss", $userAddress, $userID);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>