<?php
/*****
 *Database middle man by Leon Blakey
 ******/
 
//connect to database
mysql_connect("localhost","root","") or die("MYSQL ERROR: "+mysql_error());
mysql_select_db("GamersClub") or die("MYSQL ERROR: "+mysql_error());

if(!isset($_GET['mode']))
	die('No mode specified or not found');

$MODE = $_GET['mode'];
switch($MODE) {
	//Is this trying to see if the user is in gamers club?
	case "userExists":
		$user = $_GET['user'];
		$query = mysql_query("SELECT * FROM users WHERE `username`='$user'") or die("MYSQL ERROR: "+mysql_error());
		$numRows = mysql_num_rows($query);
		if($numRows == 0) {
			//user dosen't exist, return false
			echo "false";
		}
		else
			echo json_encode(mysql_fetch_assoc($query));
	break;
	
	//Is this a gameBrowse tree build request?
	case "buildTree":
		$data = array();		
		$typeQuery = mysql_query("SELECT DISTINCT type FROM games") or die("MYSQL ERROR: "+mysql_error()); //obtain all game types
		while($typeResult = mysql_fetch_array($typeQuery)) {
			$type = $typeResult['type'];
			$gameQuery = mysql_query("SELECT * FROM games WHERE `type`='$type'") or die("MYSQL ERROR: "+mysql_error()); //obtain games of type
			while($gameResult = mysql_fetch_assoc($gameQuery)) {
				$downQuery = mysql_query("SELECT * FROM dirlist WHERE `gameId`='{$gameResult['counter']}'") or die("MYSQL ERROR: "+mysql_error());
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
		$typeQuery = mysql_query("SELECT DISTINCT type FROM games") or die("MYSQL ERROR: "+mysql_error());
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
		$folderName = $_GET['folderName'];
		
		
		//Get the folderID of the game folder
		$folderQuery = mysql_query("SELECT * FROM `dirlist` WHERE `folder`='$folderName'") or die("MYSQL ERROR: "+mysql_error());
		if(mysql_num_rows($folderQuery) != 1)
			die("MYSQL Error: Folder matches "+mysql_num_rows($folderQuery)+" folders!");
		$folderArray = mysql_fetch_assoc($folderQuery);
		$folderID = $folderArray['counter'];
		
		//Now get everything with that folder id
		$fileQuery = mysql_query("SELECT * FROM `filelist` WHERE `folderID`='$folderID' ORDER BY `type` ASC") or die("MYSQL ERROR: "+mysql_error());
		$fileArray = array();
		$dirArray = array();
		$counter = 0;
		while($result = mysql_fetch_assoc($fileQuery)) {
			$counter++;
			if($result['type'] == "FILE")
				$fileArray[$result['Dest']] = $result['Source'];
			else if($result['type'] == "DIR")
				$dirArray[$counter." s"] = $result['Source'];
			else
				die('ERROR: Type field isn\'t FILE or DIR, its '+$result['type']);
		}
		$fileArray = json_encode($fileArray);
		$dirArray = json_encode($dirArray);
		$endArray = array("o1" => $fileArray, "o2" => $dirArray, "o3" => $folderArray['byteSize']);
		echo json_encode($endArray);
	break;
	
	//Is this trying to add a game?
	case "addGame":
		//parse incomming data
		$rawinput = $_POST['data'];
		$json = json_decode($rawinput,true);
		
		switch(json_last_error()) {
			case JSON_ERROR_DEPTH:
				die(' - Maximum stack depth exceeded');
			break;
			case JSON_ERROR_CTRL_CHAR:
				die(' - Unexpected control character found');
			break;
			case JSON_ERROR_SYNTAX:
				die(" - Syntax error, malformed JSON <br>$rawinput");
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
		mysql_query(sprintf("INSERT INTO dirlist VALUES (NULL,'%s','%s','%s','%s')",
			mysql_real_escape_string($infoJson['uploadName']),
			mysql_real_escape_string($infoJson['dir']),
			mysql_real_escape_string($gameId),
			mysql_real_escape_string($infoJson['folderSize'])))or die("MYSQL ERROR: ".mysql_error());
		
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
		mysql_query("INSERT INTO fileList VALUES $values") or die("MYSQL ERROR: "+mysql_error());
		
		return "Sucess";
	break;
	
	case "updateProfile":
		$rawJSON = $_POST['data'];
		$json = json_decode($rawJSON ,true);
		
		switch(json_last_error()) {
			case JSON_ERROR_DEPTH:
				return " - Maximum stack depth exceeded";
			break;
			case JSON_ERROR_CTRL_CHAR:
				return "- Unexpected control character found";
			break;
			case JSON_ERROR_SYNTAX:
				return " - Syntax error, malformed JSON <br>$rawinput";
			break;
			case JSON_ERROR_NONE:
				//do nothing
			break;
		}
		
		$avatar = ($json['avatar'] == "") ? null : mysql_real_escape_string($json['avatar'])
		
		$query = sprintf("UPDATE users SET name = '%s', gamersTag = '%s', gradeNum = '%s', bestAt = '%s', `favGames` = '%s', `avatar` = '%s', `desc` = '%s' WHERE counter = '%s'",
			mysql_real_escape_string($json['realName']),
			mysql_real_escape_string($json['gamersTag']),
			mysql_real_escape_string($json['grade']),
			mysql_real_escape_string($json['bestAt']),
			mysql_real_escape_string($json['favGame']),
			$avatar,
			mysql_real_escape_string($json['descPane']),
			mysql_real_escape_string($json['userid']));
		//.echo $query;
		mysql_query($query) or die("MYSQL ERROR: ".mysql_error());
		
		echo "Sucess";
	break;
	
	case "reportProfile":
		$userId = mysql_real_escape_string($_GET['userid']);
		
		$query = mysql_query("SELECT * FROM users WHERE counter = '$userId'");
		if(mysql_num_rows($query) == 0)
			die("User ID Invalid");
		
		echo json_encode(mysql_fetch_array($query));
	break;
	
	//No mode exists!
	default:
		die("Invalid Mode.");
	break;
}

?>