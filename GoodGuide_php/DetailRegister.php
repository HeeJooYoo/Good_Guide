<?php
	$data = $_POST["data1"]; // newImage

    	$file_path = $data."/"; // newImage/

	if(is_dir($data)) {
		echo "폴더가 존재합니다.";
	} else {
		echo "폴더 존재하지 않습니다.";
		@chmod($data,0777);
		@mkdir($data,0777);
	}

    	$file_path = $file_path.basename( $_FILES['uploaded_file']['name']); // newImage/이름.jpg
	
    	if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
        	echo "success";
    	} else{
        	echo "fail";
    	}

	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	if(mysqli_connect_errno($con)) {echo "DB 접속 실패";} else {echo "DB 접속 성공";}

	$tourID = $_POST["tourID"];
	$tourNum = $_POST["tourNum"];
	$tourPlace = $_POST["tourPlace"];
	$tourImage = $_POST["tourImage"];
	$tourExplain = $_POST["tourExplain"];

	$statement = mysqli_prepare($con, "INSERT INTO TOUR_DETAIL VALUES (?,?,?,?,?)");
	mysqli_stmt_bind_param($statement, "sssss", $tourID, $tourNum, $tourPlace, $tourImage, $tourExplain);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = false;

	echo json_encode($response);
?>