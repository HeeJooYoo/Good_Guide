<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
	$userName = $_POST["userName"];
	$userNumber = $_POST["userNumber"];
	$userAddress = $_POST["userAddress"];

	$statement = mysqli_prepare($con, "INSERT INTO GUIDE_MEMBER VALUES (?,?,?,?,?)"); //
	mysqli_stmt_bind_param($statement, "sssss", $userID, $userPassword, $userName, $userNumber, $userAddress);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);

?>