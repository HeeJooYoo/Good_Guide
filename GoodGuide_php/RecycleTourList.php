<?php
	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	$statement = "SELECT t.tourID, t.tourName, t.tourPrice, t.estTime, d.tourImage FROM TOUR_INFO t INNER JOIN TOUR_DETAIL d ON t.tourID = d.tourID AND tourNum = 1";
	$result = mysqli_query ($con, $statement);

	$response = array();
	
	if (!$result) {
   	 	echo 'Could not run query: ' . mysql_error();
    	exit;
	}
	while($rs = mysqli_fetch_array($result)){
		array_push($response, array("tourID"=>$rs["tourID"], "tourName"=>$rs["tourName"], "price"=>$rs["tourPrice"], "estTime"=>$rs["estTime"], "tourImage"=>$rs['tourImage']));
	
	}
	
	echo json_encode(array("tourData"=>$response));
	mysqli_close($con);
?>