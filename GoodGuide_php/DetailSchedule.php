<?php
	$data = $_POST["data1"];

    $file_path = $data."/";

	if(is_dir($data)) {
		echo "폴더 존재 0";
	} else {
		echo "폴더 존재 X";
		@mkdir($data,0777);
		@chmod($data,0777);
	}

	$file_name = basename( $_FILES['uploaded_file']['name']);
    $file_path = $file_path.$file_name;

    if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
        echo "success";
		echo $file_path;
    } else{
        echo "fail";
    }
 ?>