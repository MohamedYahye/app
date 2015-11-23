<?php 
	/**
	*  change password;
	*/
	new changePassword();
	




	class changePassword{
		private $user_id;
		public $dbh;
		private $nPass;
		function __construct() {
			if(isset($_POST['id']) && isset($_POST['wachtwoord'])){
				if(!empty($_POST['id']) && !empty($_POST['wachtwoord'])){
					$this->nPass = $_POST['wachtwoord'];
					$this->user_id = $_POST['id'];
					$this->changePass();
				}
			}
		}
		private function changePass(){
			require_once("db_connect.php");
			$db = new DB_CONNECT();
			try{
				$dbh = $db->connect();
				$val = $this->getPass();
				$sql = "UPDATE users set wachtwoord= :num WHERE ovnummer = :ov";
				$stmt = $dbh->prepare($sql);
				$stmt->bindParam(":ov", $this->storedId(), PDO::PARAM_INT);
				$stmt->bindParam(":num", $val, PDO::PARAM_INT);

				$stmt->execute();

				echo "<br/ > done";
			}catch(PDOException $e){
				echo $e->getMessage();
			}
			

		}
		private function getPass(){
			return $this->nPass;
		}
		private function storedId(){
			return $this->user_id;
		}

	}

	

	


?>
