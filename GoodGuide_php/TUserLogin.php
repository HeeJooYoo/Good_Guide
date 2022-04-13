<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$TuserID = $_POST["TuserID"];
	$TuserPassword = $_POST["TuserPassword"];
	$statement = mysqli_prepare ($con, "SELECT TuserID FROM USER_MEMBER WHERE TuserID = ? and TuserPassword = ?");

	mysqli_stmt_bind_param($statement, "ss", $TuserID, $TuserPassword);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = false;
	$response["TuserID"] = $TuserID;

	while (mysqli_stmt_fetch($statement)) {
		$response["success"] = true;
		$response["TuserID"] = $TuserID;
	}
	echo json_encode($response);
?>