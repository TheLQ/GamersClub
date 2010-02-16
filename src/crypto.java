/**
 * @(#)crypto.java
 *
 *
 * @author 
 * @version 1.00 2010/2/15
 */

import org.apache.commons.lang.StringEscapeUtils;

public class crypto {

    public static String encode(String unenc) {
   		String newString = unenc;
	    
	   	//Encode html entities and reverse
	   	newString = StringEscapeUtils.escapeHtml(newString);
	   	newString = reverse(newString);
	   	newString = StringEscapeUtils.escapeHtml(newString);
	   	newString = reverse(newString);
	    
	   	//encode 4 times with base64, reverse, and converted to hex
		for(int i=0;i<3;i++) {
			newString = Base64.encodeString(newString);
			newString = reverse(newString); 
			newString = asciiToHex(newString);
		}
		
		//Base64 encode again and reverse
	   	newString = Base64.encodeString( newString );
	   	newString = reverse(newString);
    	
    	return newString;
    }

    public static String decode(String unenc) {
   		String newString = unenc;
	    
	    //Unreverse and decode Base64
	    newString = reverse(newString);
	    newString = Base64.decodeString( newString );
	    
	    //decpde 4 times with base64, reverse, and converted back to ascii
		for(int i=0;i<3;i++) {
			newString = hexToAscii(newString);
			newString = reverse(newString); 
			newString = Base64.decodeString(newString);
		}
	    
	   	//Encode html entities and reverse
	   	newString = reverse(newString);
	   	newString = StringEscapeUtils.unescapeHtml(newString);
	   	newString = reverse(newString);
	   	newString = StringEscapeUtils.unescapeHtml(newString);
    	
    	return newString;
    }
    
	public static String reverse(String source) {
	    int i, len = source.length();
	    StringBuffer dest = new StringBuffer(len);
	    for (i = (len - 1); i >= 0; i--)
	    	dest.append(source.charAt(i));
	    return dest.toString();
	}
	
	public static String asciiToHex(String source) {
        StringBuilder hex = new StringBuilder();
        for (int i=0; i < source.length(); i++) {
            hex.append(Integer.toHexString(source.charAt(i)));
        }        
        return hex.toString().toUpperCase();
	}	
		
	  public static String hexToAscii(String hex){
	 
		  StringBuilder sb = new StringBuilder();
	 
		  //49204c6f7665204a617661 split into two characters 49, 20, 4c...
		  for( int i=0; i<hex.length()-1; i+=2 ){
		      //grab the hex in pairs
		      String output = hex.substring(i, (i + 2));
		      //convert hex to decimal
		      int decimal = Integer.parseInt(output, 16);
		      //convert the decimal to character
		      sb.append((char)decimal);
		  }
	 
		  return sb.toString();
	  }
	/*
	public static void main(String[] args) {
		System.out.println(decode(encode("Muhahahahahahahahahah{}''\"\"")));		
	}*/
}