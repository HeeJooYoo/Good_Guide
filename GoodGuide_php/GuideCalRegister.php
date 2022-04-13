<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$tourID = $_POST["tourID"];
	$tourCal = $_POST["tourCal"];
	$guide_possible = $_POST["guide_possible"];

	$statement = mysqli_prepare($con, "SELECT COUNT(tourCal) FROM guide_cal WHERE tourCal = ? AND tourID = ?"); //tourCal에 정보가 있는지 확인

	mysqli_stmt_bind_param($statement, "ss", $tourCal, $tourID);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = false;
	//$response["change"] = false;
	$cnt = null;

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $cnt);

	while(mysqli_stmt_fetch($statement)){
		if($cnt > 0){
			$response["cnt"] = $cnt;
			$response["success"] = true;
			//$response["change"] = $guide_possible;
			$stmt = mysqli_prepare($con, "UPDATE guide_cal SET guide_possible = ? WHERE tourCal = ? AND tourID = ?");
			mysqli_stmt_bind_param($stmt, "sss", $guide_possible, $tourCal, $tourID);
			mysqli_stmt_execute($stmt);
		}else{
			$response["cnt"] = $cnt;
			$response["success"] = true;
			//$response["change"] = $guide_possible;
			$st = mysqli_prepare($con, "INSERT INTO guide_cal VALUES (?,?,?)"); 
			mysqli_stmt_bind_param($st, "sss", $tourID, $tourCal, $guide_possible);
			mysqli_stmt_execute($st);
		}
	}

	echo json_encode($response);


	
?>