<?php
/*****Encrypting functions*******/
function ascii2hex($ascii) {
	$hex = '';
	for ($i = 0; $i < strlen($ascii); $i++) {
		$byte = strtoupper(dechex(ord($ascii{$i})));
		$byte = str_repeat('0', 2 - strlen($byte)).$byte;
		$hex.=$byte;
	}
	return $hex;
}

function encrypt_text($cleartext) {
	$newString = $cleartext;
	
	//Break down into html characters twice
	$newString = str_replace("","",htmlentities($newString));
	$newString = strrev($newString); 
	$newString = str_replace("","",htmlentities($newString));
	$newString = strrev($newString); 
	
	//encode 4 times with base64, reverse, and converted to hex
	for($i=0;$i<3;$i++) {
		$newString = base64_encode($newString);
		$newString = strrev($newString); 
		$newString = ascii2hex($newString);
	}
	
	//and encode with base 64 (reversed) one more time
	$newString = base64_encode($newString);
	$newString = strrev($newString); 
	
	//send it back
	return $newString;
}

/*******Decrypting functions*******/
function hex2ascii($hex){
	$ascii='';
	$hex=str_replace(" ", "", $hex);
	for($i=0; $i<strlen($hex); $i=$i+2) {
		$ascii.=chr(hexdec(substr($hex, $i, 2)));
	}
	return($ascii);
}
function decrypt_text($cleartext) {
	$newString = $cleartext;
	
	//Do EVERYTHING in REVERSE
	
	//Reverse back to normal and decode base64
	$newString = strrev($newString); 
	$newString = base64_decode($newString);
	
	//Convert back to hex, reverse, and decode base64
	for($i=0;$i<3;$i++) {
		$newString = hex2ascii($newString);
		$newString = strrev($newString); 
		$newString = base64_decode($newString);
	}
	
	//reverse, decode html entities
	$newString = strrev($newString); 
	$newString = html_entity_decode($newString);
	$newString = strrev($newString); 
	$newString = html_entity_decode($newString);
	
	//send it back
	return $newString;
}
?>