<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$TuserID = $_POST["TuserID"];
	$TuserAddress = $_POST["TuserAddress"];

	$statement = mysqli_prepare ($con, "UPDATE USER_MEMBER SET TuserAddress = ? WHERE TuserID = ?");
	mysqli_stmt_bind_param($statement, "ss", $TuserAddress, $TuserID);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>