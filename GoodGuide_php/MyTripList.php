<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$TuserID = $_POST["TuserID"];
	//$TuserID = "jm";
	$statement = mysqli_prepare($con, "SELECT DISTINCT t.tourName, r.tourID, r.TuserID, r.date, r.pNum, r.price, r.total FROM RESERV_INFO r INNER JOIN TOUR_INFO t ON TuserID = ? AND t.tourID = r.tourID ORDER BY r.date");

	mysqli_stmt_bind_param($statement, "s", $TuserID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $tourName, $tourID, $TuserID, $date, $pNum, $price, $total);

	$response = array();

	while(mysqli_stmt_fetch($statement)) {
		array_push($response, array("tourName"=> $tourName, "tourID"=>$tourID, "TuserID"=>$TuserID, "date"=>$date, "pNum"=>$pNum, "price"=>$price, "total"=>$total));
	}

	echo json_encode(array("reservData"=>$response));
?>