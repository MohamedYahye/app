<?php 
	
	/**
	* check if user combination exists
	*/
	class verifyUser {

		private $id;
		private $email;
		protected $dbh;
		protected $user_exists;
		public $response = array();
		
		function __construct() {
			$this->id = $_GET['username'];
			$this->email = $_GET['email'];
			$this->user_exists = false;
			$this->getPost();
		}


		public function getPost(){
			if(isset($_GET['username']) && isset($_GET['email'])){
				if(!empty($_GET['username']) && !empty($_GET['email'])){

					$this->checkDbForuser($this->getId(), $this->getEmail());
					
				}else{
					echo "empty post";
				}
			}
		}


		private function checkDbForuser($_id, $_email){

			require_once("db_connect.php");
			$db = new DB_CONNECT();

			try{
				$dbh = $db->connect();

				$data = "SELECT `ovnummer`, `email` FROM users WHERE ovnummer =:ov AND email = :em";
				$stmt = $dbh->prepare($data);
				$stmt->bindParam(":ov", $_id, PDO::PARAM_INT);
				$stmt->bindParam(":em", $_email);

				$stmt->execute();
				if($row = $stmt->fetchColumn() > 0){
					$this->user_exists = true;
					$this->forward();
				}else{
					$this->user_exists = false;
					
				}

				

			}catch(PDOException $e){
				echo "Error: " . $e->getMessage();
				$dbh->close();
			}
		}

		protected function getId(){
			return $this->id;
		}

		protected function getEmail(){
			return $this->email;
		}
		
		public function does_user_exist(){
			return ($this->user_exists ? 'true' : 'false');
		}

		private function forward(){
			if($this->does_user_exist()){
				$response[] = $this->does_user_exist();
				//$this->storeIdInSession();
			}
		}


		public function storeIdInSession(){
			if($this->does_user_exist() == "true"){
				session_start();

				$id = $this->getId();

				$_SESSION['id'] = $id;

				return $_SESSION['id'];
			}
			
		}

	}




	$test = new verifyUser();

	$response["user"] = $test->does_user_exist();
	echo json_encode($response);

	die();
?>
