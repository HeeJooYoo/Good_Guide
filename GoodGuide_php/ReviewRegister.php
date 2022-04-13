<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");
	if(mysqli_connect_errno($con)) { echo "DB 立加 角菩"; } else { echo "DB 立加 己傍"; }
	
	$TuserID = $_POST["TuserID"];
	$tourID = $_POST["tourID"];
	$grade = $_POST["grade"];
	$review = $_POST["review"];
	$date = $_POST["date"];

	$statement = mysqli_prepare($con, "INSERT INTO REVIEW(TuserID, tourID, grade, review, date) VALUES (?,?,?,?,?)"); 
	mysqli_stmt_bind_param($statement, "sssss", $TuserID, $tourID, $grade, $review, $date);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;
	
	echo json_encode($response);
?>