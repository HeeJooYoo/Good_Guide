<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$TuserID = $_POST["TuserID"];
	$TuserPassword = $_POST["TuserPassword"];
	$TuserName = $_POST["TuserName"];
	$TuserNumber = $_POST["TuserNumber"];
	$TuserAddress = $_POST["TuserAddress"];
	
	$statement = mysqli_prepare($con, "INSERT INTO user_member VALUES (?,?,?,?,?)"); 
	mysqli_stmt_bind_param($statement, "sssss", $TuserID, $TuserPassword, $TuserName, $TuserNumber, $TuserAddress);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);

?>