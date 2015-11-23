<?
	$host = "localhost";
	$user = "md347423db314051";
	$pass = "123";
	$db = "md347423db314051";

	$mysqli = new mysqli($host, $user, $pass, $db);
	if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}