<?php
   $con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

   $statement = "SELECT TOUR_INFO.tourID, TOUR_INFO.tourName, TOUR_INFO.tourPrice, TOUR_INFO.estTime, TOUR_DETAIL.tourImage FROM TOUR_INFO,TOUR_DETAIL,REVIEW WHERE TOUR_INFO.tourID = REVIEW.tourID AND TOUR_DETAIL.tourID = TOUR_INFO.tourID AND REVIEW.tourID = TOUR_DETAIL.tourID GROUP BY TOUR_INFO.tourID ORDER BY AVG(REVIEW.grade) DESC, tourID DESC";
   $result = mysqli_query ($con, $statement);
   $response = array();

   if (!$result) {
          echo 'Could not run query: ' . mysql_error();
       exit;
   }
   while($rs = mysqli_fetch_array($result)){
      array_push($response, array("tourID"=>$rs["tourID"], "tourName"=>$rs["tourName"], "price"=>$rs["tourPrice"], "estTime"=>$rs["estTime"], "tourImage"=>$rs['tourImage']));
   } 
   echo json_encode(array("famoustourData"=>$response));
   mysqli_close($con);
?>

