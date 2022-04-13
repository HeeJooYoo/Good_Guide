<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	//$guideID = $_POST["guideID"];
	$tourID = $_POST["tourID"];
	$statement = mysqli_prepare($con, "SELECT DISTINCT r.grade, r.review, m.TuserName FROM REVIEW r INNER JOIN USER_MEMBER m ON tourID = ? AND m.TuserID = r.TuserID");

	mysqli_stmt_bind_param($statement, "s", $tourID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $grade, $review, $TuserName);

	$response = array();

	while(mysqli_stmt_fetch($statement)) {
		array_push($response, array("TuserName"=> $TuserName, "grade"=>$grade, "review"=>$review));
	}

	echo json_encode(array("reviewData"=>$response));
?>