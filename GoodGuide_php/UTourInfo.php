<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$tourID = $_POST["tourID"];
	//$tourID = "daeun1";
	$statement = mysqli_prepare($con, "SELECT COUNT(grade), AVG(grade) FROM REVIEW WHERE tourID = ?");

	mysqli_stmt_bind_param($statement, "s", $tourID);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = false;
	$count = null;

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $count, $avg);

	while(mysqli_stmt_fetch($statement)) {
		if($count > 0){
			$statement = mysqli_prepare($con, "SELECT tourName, tourPrice, tourCity, tourCountry, minNum, maxNum, meetPlace, meetTime, estTime, mapImage FROM TOUR_INFO WHERE tourID = ?");
		
			mysqli_stmt_bind_param($statement, "s", $tourID);
			mysqli_stmt_execute($statement);
			mysqli_stmt_store_result($statement);
			mysqli_stmt_bind_result($statement, $tourName, $tourPrice, $tourCity, $tourCountry, $minNum, $maxNum, $meetPlace, $meetTime, $estTime, $mapImage);
			while(mysqli_stmt_fetch($statement)) {
				$response["success"] = true;
				$response["tourName"] = $tourName;
				$response["tourPrice"] = $tourPrice;
				$response["tourCity"] = $tourCity;
				$response["tourCountry"] = $tourCountry;
				$response["minNum"] = $minNum;
				$response["maxNum"] = $maxNum;
				$response["meetPlace"] = $meetPlace;
				$response["meetTime"] = $meetTime;
				$response["estTime"] = $estTime;
				$response["mapImage"] = $mapImage;
				$response["avg"] = $avg;
			}
		}else{
			$statement = mysqli_prepare($con, "SELECT tourName, tourPrice, tourCity, tourCountry, minNum, maxNum, meetPlace, meetTime, estTime, mapImage FROM TOUR_INFO WHERE tourID = ?");
		
			mysqli_stmt_bind_param($statement, "s", $tourID);
			mysqli_stmt_execute($statement);
			mysqli_stmt_store_result($statement);
			mysqli_stmt_bind_result($statement, $tourName, $tourPrice, $tourCity, $tourCountry, $minNum, $maxNum, $meetPlace, $meetTime, $estTime, $mapImage);
			while(mysqli_stmt_fetch($statement)) {
				$response["success"] = true;
				$response["tourName"] = $tourName;
				$response["tourPrice"] = $tourPrice;
				$response["tourCity"] = $tourCity;
				$response["tourCountry"] = $tourCountry;
				$response["minNum"] = $minNum;
				$response["maxNum"] = $maxNum;
				$response["meetPlace"] = $meetPlace;
				$response["meetTime"] = $meetTime;
				$response["estTime"] = $estTime;
				$response["mapImage"] = $mapImage;
				$response["avg"] = "0";
			}
		}
	}

	echo json_encode($response);
?>