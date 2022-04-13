<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");
	if(mysqli_connect_errno($con)) {echo "DB 立加 角菩";} else {echo "DB 立加 己傍";}
	
	$tourID = $_POST["tourID"];
  	$tourName = $_POST["tourName"];
	$tourPrice = $_POST["tourPrice"];
	$tourCity = $_POST["tourCity"];
	$tourCountry = $_POST["tourCountry"];
	$minNum = $_POST["minNum"];
	$maxNum = $_POST["maxNum"];
	$meetPlace = $_POST["meetPlace"];
	$meetTime = $_POST["meetTime"];
	$estTime = $_POST["estTime"];
	$mapImage = $_POST["mapImage"];
	$guideID = $_POST["guideID"];

	$statement = mysqli_prepare($con, "INSERT INTO TOUR_INFO(tourID, tourName, tourPrice, tourCity, tourCountry, minNum, maxNum, meetPlace, meetTime, estTime, mapImage, guideID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"); 
	mysqli_stmt_bind_param($statement, "ssssssssssss", $tourID, $tourName, $tourPrice, $tourCity, $tourCountry, $minNum, $maxNum, $meetPlace, $meetTime, $estTime, $mapImage, $guideID);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;
	
	echo json_encode($response);
?>