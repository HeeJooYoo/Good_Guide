<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
	$statement = mysqli_prepare ($con, "SELECT userID FROM GUIDE_MEMBER WHERE userID = ? and userPassword = ?");

	mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword );
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = false;
	$response["userID"] = $userID;

	while (mysqli_stmt_fetch($statement)) {
		$response["success"] = true;
		$response["userID"] = $userID;
		$response["userPassword"] = $userPassword;
	}
	echo json_encode($response);
?>