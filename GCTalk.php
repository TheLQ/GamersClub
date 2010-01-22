<?php
/*****
 *Database middle man by Leon Blakey
 ******/
 
//connect to database
mysql_connect("localhost","root","") or die(mysql_error());
mysql_select_db("GamersClub") or die(mysql_error());

if(!isset($_GET['mode'])) {
	die('No mode specified or not found');
}

$MODE = $_GET['mode'];

//Is this trying to see if the user is in gamers club?
if($MODE == "userExists") {
	$user = $_GET['user'];
	$query = mysql_query("SELECT * FROM users WHERE `username`='$user'") or die(mysql_error());
	echo mysql_error();
	$numRows = mysql_num_rows($query);
	echo mysql_error();

	if($numRows == 0) {
		//user dosen't exist, return false
		echo "false";
	}
	else {
		$result = mysql_fetch_array($query) or die(mysql_error());
		$info = array();
		$info['name'] = $result['name'];
		$info['uid'] = $result['counter'];
		$info['admin'] = $result['admin'];
		$info['username'] = $result['username'];
		echo json_encode($info);
	}
}

//Is this a gameBrowse tree build request?
else if($MODE == "buildTree") {
	$data = array();
	$typeQuery = mysql_query("SELECT DISTINCT type FROM games");
	while($typeResult = mysql_fetch_array($typeQuery)) {
		$type = $typeResult['type'];
		$gameQuery = mysql_query("SELECT * FROM games WHERE `type`='$type'");
		while($gameResult = mysql_fetch_assoc($gameQuery)) {
			$data[$type][] = addSlashes(json_encode($gameResult));
		}
	}
	echo json_encode($data);
}

//Is this trying to add a game?
else if($MODE =="addGame") {
	//parse incomming data
	$rawinput = $_POST['data'];
	$json = json_decode($rawinput,true);
	
	switch(json_last_error()) {
        case JSON_ERROR_DEPTH:
            echo ' - Maximum stack depth exceeded';
        break;
        case JSON_ERROR_CTRL_CHAR:
            echo ' - Unexpected control character found';
        break;
        case JSON_ERROR_SYNTAX:
            echo ' - Syntax error, malformed JSON';
			echo $rawinput;
        break;
        case JSON_ERROR_NONE:
            //do nothing
        break;
    }
	
	$values = "";
	foreach($json as $currentName => $currentSet) {
		foreach($currentSet as $relativeSrc => $relativeDir) {
				$type = ($currentName==1) ? "DIR" : "FILE";
				$values.= sprintf("('%s','%s','$type'),",mysql_real_eascape_string($relativeSrc),mysql_real_eascape_string($relativeDir));
		}
	}
	
	$values = substr($values,0,-1);
	$query = "INSERT INTO fileList (`Source`,`Dest`,`type`) VALUES $values";
	mysql_query($query) or die("ERROR: ".mysql_error());
	
	echo "Sucess";
}
else {
	die("Invalid Mode.");
}

?>