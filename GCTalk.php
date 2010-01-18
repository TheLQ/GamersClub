<?php
/*****
 *Database middle man by Leon Blakey
 ******/
 
//connect to database
mysql_connect("localhost","root","") or die(mysql_error());
mysql_select_db("GamersClub") or die(mysql_error());

//are we recieving information?
if($_GET['mode']=="send") {
	//parse incomming data
	$rawinput = $_POST['data'];
	$reader = new XMLReader();
	$reader->XML($request);
}
?>