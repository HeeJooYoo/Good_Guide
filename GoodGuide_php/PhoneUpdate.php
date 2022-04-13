<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$userID = $_POST["userID"];
	$userNumber = $_POST["userNumber"];

	$statement = mysqli_prepare ($con, "UPDATE GUIDE_MEMBER SET userNumber = ? WHERE userID = ?");
	mysqli_stmt_bind_param($statement, "ss", $userNumber, $userID);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>