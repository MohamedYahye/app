<?php
	require_once("config.php");
			
	//var_dump($_POST);
	// check for required fields;
	if(isset($_POST['user_id']) && isset($_POST['reason_absence'])){

		// include connect class
		

		$response = array();
		$id = (int)$_POST['user_id'];
		$absence = $_POST['reason_absence'];
		// insert rows
		$query = "INSERT INTO `afmelden_db` (`user_id`, `reason_absence`) VALUES ($id,'$absence')";
		$result = mysqli_query($mysqli, $query);

		if($result){
			// success message
			//$response['success'] = 1;
			$response['message'] = "successfully inserted";

			die(json_encode($response));
		}else{
			// failed to insert row;
			$response['success'] = 0;
			$response['message'] = "Oeps!, an error has occured";

			die(json_encode($response));
		}
	}else{
		$response['success'] = 0;
		$response['message'] = "required field(s) missing";

		die(json_encode($response));
	}

	
?>