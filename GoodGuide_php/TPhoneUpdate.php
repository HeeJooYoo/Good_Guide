<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$TuserID = $_POST["TuserID"];
	$TuserNumber = $_POST["TuserPhone"];

	$statement = mysqli_prepare ($con, "UPDATE USER_MEMBER SET TuserNumber = ? WHERE TuserID = ?");
	mysqli_stmt_bind_param($statement, "ss", $TuserNumber, $TuserID);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>