<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$userID = $_POST["userID"];
	$statement = mysqli_prepare($con, "SELECT userName, userNumber, userAddress FROM GUIDE_MEMBER WHERE userID = ?");

	mysqli_stmt_bind_param($statement, "s", $userID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userName, $userNumber, $userAddress);

	$response = array();
	$response["success"] = false;

	while(mysqli_stmt_fetch($statement)) {
		$response["success"] = true;
		$response["userName"] = $userName;
		$response["userNumber"] = $userNumber;
		$response["userAddress"] = $userAddress;
	}

	echo json_encode($response);
?>