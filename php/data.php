<?php 
	/**
	* get user info uit de afmelde_db;
	*/

	if(isset($_POST['Student_id'])){
		if(!empty($_POST['Student_id'])){
			new userdata();
		}else{
			die(json_encode("No user selected"));
		}
		
	}

	class userData {
		public $data = array();
		
		function __construct() {
			# code...
			$id = $_GET['Student_id'];
			

			require_once("db_connect.php");
			$db = new DB_CONNECT();
			$dbh = $db->connect();
			try{

				$sql = "SELECT user_id, reason_absence, datetest FROM afmelden_db WHERE user_id=:id";
				$stmt = $dbh->prepare($sql);
				$stmt->bindParam(":id", $id, PDO::PARAM_INT);

				$stmt->execute();

				while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
					
					$this->data[] = $row;
				}
			}catch(PDOException $e){
				echo json_encode($e->getMessage());
				$dbh->close();
			}
		}

		
		

	}
	$userdata = new Userdata();


	foreach($userdata->data as $user):
		$data['student_id'] = $user['user_id'];
		$data['absence_value'] = $user['reason_absence'];
		$data['date'] = $user['datetest'];
		echo json_encode($data);
	endforeach;

	die();
?>