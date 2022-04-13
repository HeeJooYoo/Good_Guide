<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");
	
	if(!$con){
		echo("데이터베이스연결실패");
		exit;
	}else{
		echo("성공");
	}

	$tourID = $_POST["tourID"];
	$TuserID = $_POST["TuserID"];
	$date = $_POST["date"];
	$sql = mysqli_prepare($con, "SELECT grade, review, date FROM REVIEW WHERE TuserID = ? AND tourID = ? AND date = ?");

	mysqli_stmt_bind_param($sql, "sss", $TuserID, $tourID, $date);
	mysqli_stmt_execute($sql);
   
   	mysqli_stmt_store_result($sql);
   	mysqli_stmt_bind_result($sql, $grade, $review, $date);

	//$response = array();
	$response["success"] = false;

	while(mysqli_stmt_fetch($sql)) {
		//array_push($response, array("grade"=> $grade, "review"=>$review, "date"=>$avgdate);
		$response["success"] = true;
		$response["grade"] = $grade;
		$response["review"] = $review;
	}
	echo json_encode($response);
	mysqli_close($con);
?>