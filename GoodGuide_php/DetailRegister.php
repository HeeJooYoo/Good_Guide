<?php
	$data = $_POST["data1"]; // newImage

    	$file_path = $data."/"; // newImage/

	if(is_dir($data)) {
		echo "������ �����մϴ�.";
	} else {
		echo "���� �������� �ʽ��ϴ�.";
		@chmod($data,0777);
		@mkdir($data,0777);
	}

    	$file_path = $file_path.basename( $_FILES['uploaded_file']['name']); // newImage/�̸�.jpg
	
    	if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
        	echo "success";
    	} else{
        	echo "fail";
    	}

	$con = mysqli_connect("localhost", "goodguide", "rntrkdlem", "goodguide");

	if(mysqli_connect_errno($con)) {echo "DB ���� ����";} else {echo "DB ���� ����";}

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