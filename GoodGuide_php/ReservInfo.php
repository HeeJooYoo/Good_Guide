<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$tourID = $_POST["tourID"];
	$TuserID = $_POST["TuserID"];
	$date = $_POST["date"];
	$pNum = $_POST["pNum"];
	$price = $_POST["price"];
	$total = $_POST["total"];

	$statement = mysqli_prepare($con, "INSERT INTO RESERV_INFO VALUES (?,?,?,?,?,?)"); 
	mysqli_stmt_bind_param($statement, "ssssss", $tourID, $TuserID, $date, $pNum, $price, $total);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>