<?
require("config.php");

$ovnummer = $_POST['ovnummer'];
$wachtwoord = $_POST['wachtwoord'];

if(!empty($_POST)){
	if(empty($_POST['ovnummer']) || empty($_POST['wachtwoord'])){
		$response['success'] = 0;
		$response['message'] = "one or more fields are empty";
		die(json_encode($response));
	}

	$query = "SELECT * from users WHERE ovnummer = '$ovnummer' and wachtwoord = '$wachtwoord'";
	$sql = mysqli_query($mysqli, $query);

	$row = mysqli_fetch_array($sql);

	if(!empty($row)){
		$response['success'] = 1;
		$response['message'] = "you have been successfully logged in";

		die(json_encode($response));
	}else{
		$response['success'] = 0;
		$response['message'] = "invalid username or password";

		die(json_encode($response));
	}
}else{
	$response['success'] = 0;
	$response['message'] = "one or both fields are empty";

	die(json_encode($response));
}

$mysqli->close();

?>