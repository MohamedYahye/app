<?php 

	class DB_CONNECT{
		//$dbh = null;
		function __construct() {
			$this->connect();
		}

		function __destruct(){
			$this->close();
		}


		function connect(){
			$host = "localhost";
			$user = "md347423db314051";
			$pass = "123";
			$db = "md347423db314051";


			$dbh = new PDO("mysql:host=$host;dbname=$db", $user, $pass);
			$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

			return $dbh;
		}


		function close(){
			return $dbh = null;
		}
	}


?>