<?php

if($_SERVER["HTTP_HOST"] == "localhost") {
	mysql_connect("localhost","root","") or die("MYSQL ERROR: "+mysql_error());
	mysql_select_db("GamersClub") or die("MYSQL ERROR: "+mysql_error());
}
else if($_SERVER["HTTP_HOST"] == "www.evilxproductions.net" || $_SERVER["HTTP_HOST"] == "evilxproductions.net" ) {
	mysql_connect("","","") or die("MYSQL ERROR: "+mysql_error());
	mysql_select_db("expscripts") or die("MYSQL ERROR: "+mysql_error());
}
else {
	die('Invalid DB');
}
?>