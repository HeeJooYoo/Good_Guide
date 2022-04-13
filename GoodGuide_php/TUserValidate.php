<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$TuserID = $_POST["TuserID"];
	$statement = mysqli_prepare($con, "SELECT TuserID FROM USER_MEMBER WHERE TuserID = ?");

	mysqli_stmt_bind_param($statement, "s", $TuserID);
	mysqli_stmt_execute($statement);

	$response["TnewID"] = true;

	while(mysqli_stmt_fetch($statement)) {
		$response["TnewID"] = false;
		$response["TuserID"] = $TuserID;
	}

	echo json_encode($response);
?>