<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$TuserID = $_POST["TuserID"];
	$TuserName = $_POST["TuserName"];

	$statement = mysqli_prepare ($con, "UPDATE USER_MEMBER SET TuserName = ? WHERE TuserID = ?");
	mysqli_stmt_bind_param($statement, "ss", $TuserName, $TuserID);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>