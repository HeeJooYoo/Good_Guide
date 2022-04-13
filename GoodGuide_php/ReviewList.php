<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$guideID = $_POST["guideID"];
	//$guideID = "mjc";
	$statement = mysqli_prepare($con, "SELECT t.tourName, COUNT(r.tourID), AVG(r.grade), t.tourID FROM REVIEW r INNER JOIN TOUR_INFO t ON guideID = ? AND t.tourID = r.tourID GROUP BY t.tourID");

	mysqli_stmt_bind_param($statement, "s", $guideID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $tourName, $count, $avg, $tourID);

	$response = array();

	while(mysqli_stmt_fetch($statement)) {
		array_push($response, array("tourName"=> $tourName, "count"=>$count, "avg"=>$avg, "tourID"=>$tourID));
	}

	echo json_encode(array("reviewListData"=>$response));
?>