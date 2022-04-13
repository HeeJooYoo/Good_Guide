<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");
	
	
	if(!$con){
		echo("데이터베이스연결실패");
		exit;
	}else{
		echo("성공");
	}

	$tourID = $_POST["tourID"];
	$guide_possible = '0';

	$sql = mysqli_prepare($con, "SELECT tourCal, tourID FROM guide_cal WHERE guide_possible = ? AND tourID = ? ORDER BY tourCal");

	mysqli_stmt_bind_param($sql, "ss", $guide_possible, $tourID);
	mysqli_stmt_execute($sql);
   
   	mysqli_stmt_store_result($sql);
   	mysqli_stmt_bind_result($sql, $tourCal, $tourID);

	$response = array(); //배열로 response를 선언/   

	while(mysqli_stmt_fetch($sql)) {
		array_push($response, array("tourCal"=>$tourCal, "tourID"=>$tourID));
	}

	echo json_encode(array("calData"=>$response));
	mysqli_close($con);
?>