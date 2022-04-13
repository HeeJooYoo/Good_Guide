<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$userID = $_POST["userID"];
	$statement = mysqli_prepare($con, "SELECT userID FROM GUIDE_MEMBER WHERE userID = ?");

	mysqli_stmt_bind_param($statement, "s", $userID);
	mysqli_stmt_execute($statement);

	$response["newID"] = true;

	while(mysqli_stmt_fetch($statement)) {
		$response["newID"] = false;
		$response["userID"] = $userID;
	}

	echo json_encode($response);
?>