<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");
	//$tourID = "daeun1";
	//$date = "2018-11-22";
	$tourID = $_POST["tourID"];
	$date = $_POST["date"];
	$statement = mysqli_prepare($con, "SELECT u.TuserName, r.pNum FROM RESERV_INFO r INNER JOIN USER_MEMBER u ON r.TuserID = u.TuserID AND r.tourID = ? AND r.date = ?");

	mysqli_stmt_bind_param($statement, "ss", $tourID, $date);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userName, $pNum);

	$response = array();
		
	while(mysqli_stmt_fetch($statement)){
		array_push($response, array("userName"=>$userName, "pNum"=>$pNum));
	}

	echo json_encode(array("bookerListData"=>$response));
	mysqli_close($con);
?>