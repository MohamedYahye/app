<?php
require("config.php");

$ovnummer = $_REQUEST["ovnummer"];
$wachtwoord = $_REQUEST["wachtwoord"];
$email = $_REQUEST["email"];
$leerjaar = $_REQUEST["leerjaar"];

$flag["code"] = 0;

if($query = mysqli_query($mysqli, "INSERT INTO `users` ( `ovnummer`, `wachtwoord`, `email`, `leerjaar`) VALUES ('$ovnummer', '$wachtwoord', '$email', '$leerjaar')")){
	$flag['code'] = 1;
}

print(json_encode($flag));

$mysqli->close();

?>