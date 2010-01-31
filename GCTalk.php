<?php
/*****
 *Database middle man by Leon Blakey
 ******/
 
//connect to database
mysql_connect("localhost","root","") or die(mysql_error());
mysql_select_db("GamersClub") or die(mysql_error());

if(!isset($_GET['mode']))
	die('No mode specified or not found');

$MODE = $_GET['mode'];
switch($MODE) {
	//Is this trying to see if the user is in gamers club?
	case "userExists":
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
	break;
	
	//Is this a gameBrowse tree build request?
	case "buildTree":
		$data = array();		
		$typeQuery = mysql_query("SELECT DISTINCT type FROM games"); //obtain all game types
		while($typeResult = mysql_fetch_array($typeQuery)) {
			$type = $typeResult['type'];
			$gameQuery = mysql_query("SELECT * FROM games WHERE `type`='$type'"); //obtain games of type
			while($gameResult = mysql_fetch_assoc($gameQuery)) {
				$downQuery = mysql_query("SELECT * FROM dirlist WHERE `gameId`='{$gameResult['counter']}'");
				$downArray = array();
				while($downResult = mysql_fetch_array($downQuery)) {
					$downArray[$downResult['name']] = $downResult['folder'];
				}
				$gameResult['dirs'] = $downArray;
				$data[$type][] = addSlashes(json_encode($gameResult));
				
			}
		}
		echo json_encode($data);
	break;
	
	case "getTypes":
		$typeQuery = mysql_query("SELECT DISTINCT type FROM games");
		$type = array();
		$type["0 s"] = "Custom";
		$counter = 0;
		while($typeResult = mysql_fetch_array($typeQuery)) {
			$counter++;
			$type["$counter s"] = $typeResult['type'];
		}
		$counter++;
		$type["$counter s"] = "Select One";
		echo json_encode($type);
	break;
	
	//Is this PasteGame wanting a big file list
	case "makeGameFileList":
	
	break;
	
	//Is this GameBrowsers's download pane trying to get list of tables
	case "getDownloadDirs":
		$game = mysql_real_escape_string($_GET['game']);
		
		//simply get a feild from the db
		$result = mysql_fetch_assoc("SELECT * FROM `games` WHERE `game`='$game'");
	break;
	
	//Is this trying to add a game?
	case "addGame":
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
		
		$infoJson = $json[1];
		//Insert Game Data
		mysql_query(sprintf("INSERT INTO games VALUES (NULL,'%s','%s','%s','%s','%s','%s','%s')",
			mysql_real_escape_string($infoJson['name']),
			mysql_real_escape_string($infoJson['desc']),
			mysql_real_escape_string($infoJson['type']),
			mysql_real_escape_string($infoJson['picture']),
			mysql_real_escape_string($infoJson['addDate']),
			mysql_real_escape_string($infoJson['createDate']),
			mysql_real_escape_string($infoJson['addBy'])))or die("MYSQL ERROR: ".mysql_error());
		$gameId = mysql_insert_id();
		
		//Insert folder data
		mysql_query(sprintf("INSERT INTO dirlist VALUES (NULL,'%s','%s','%s')",
			mysql_real_escape_string($infoJson['uploadName']),
			mysql_real_escape_string($infoJson['dir']),
			$gameId))or die("MYSQL ERROR: ".mysql_error());
		
		//Insert File Data
		$values = "";
		unset($json[1]);
		foreach($json as $currentName => $currentSet) {
			foreach($currentSet as $relativeSrc => $relativeDir) {
					$type = ($currentName==2) ? "DIR" : "FILE";
					$values.= sprintf("('NULL','%s','%s','$type','%s'),",mysql_real_escape_string($relativeSrc),mysql_real_escape_string($relativeDir),$gameId);
			}
		}
		
		$values = substr($values,0,-1);
		mysql_query("INSERT INTO fileList VALUES $values") or die("ERROR: ".mysql_error());
		
		echo "Sucess";
	break;
	
	//No mode exists!
	default:
		die("Invalid Mode.");
	break;
}

?>