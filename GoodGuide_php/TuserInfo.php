<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$TuserID = $_POST["TuserID"];
	$statement = mysqli_prepare($con, "SELECT TuserName, TuserNumber, TuserAddress FROM USER_MEMBER WHERE TuserID = ?");

	mysqli_stmt_bind_param($statement, "s", $TuserID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $TuserName, $TuserNumber, $TuserAddress);

	$response = array();
	$response["success"] = false;

	while(mysqli_stmt_fetch($statement)) {
		$response["success"] = true;
		$response["TuserName"] = $TuserName;
		$response["TuserNumber"] = $TuserNumber;
		$response["TuserAddress"] = $TuserAddress;
	}

	echo json_encode($response);
?>