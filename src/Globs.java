/**
 * Class Globs
 *
 * Utility class and method holder
 *
 * @author Leon Blakey/Lord.Quackstar
 */
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

import java.net.URL;
import java.net.URLConnection;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.lang.reflect.Array;

public class Globs {
	public static JComponent setSize(JComponent comp, int height, int width) {
     	Dimension size = comp.getMaximumSize();
     	if(height!=0) {
      		size.height = height;
     	}
     	else if(width!=0) {
     		size.width = width;
     	}
     	comp.setMinimumSize(size);
     	comp.setMaximumSize(size);
     	comp.setPreferredSize(size);
     	return comp;
    }
    
    public static void setTextSize(JComponent comp, int size) {
    	comp.setFont(new Font(comp.getFont().getName(),comp.getFont().getStyle(),size));
    }
    
    public static JButton buttonSetup(String name, ActionListener al) {
    	JButton main = new JButton(name);
    	main.addActionListener(al);
    	return main;
    }


    
    /* Description: Changes body to a new panel (removes code duplication)
	 * Used by: MainMenu.actionPerformed and main menu buttons*/
	public static void switchBody(String newBody) {
		CardLayout cl = (CardLayout)(GamersClub.bodyPanel.getLayout());
	    cl.show(GamersClub.bodyPanel, newBody);

	}
	
	public static String webTalk(String url,String postVars,String lookFor) {
		String allLine = "";
		try {
			URL ourURL = new URL("http://localhost:80/GamersClub/GCTalk.php?"+url);
			System.out.println("Visiting url: http://localhost:80/GamersClub/GCTalk.php?"+url);
	    	URLConnection conn = ourURL.openConnection();
	    	
	    	conn.setDoOutput(true);
	    	OutputStreamWriter wr = null;
	    	if(postVars != null) {
	    		wr = new OutputStreamWriter(conn.getOutputStream());
		    	wr.write(postVars);
	    		wr.flush();
	    	}
	    	
	    	// Get the response
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    	String line;
	    	while ((line = rd.readLine()) != null)
	        	allLine+=line;
	    	if(postVars != null)
	    		wr.close();
	    	rd.close();
	    	allLine = allLine.trim();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(lookFor != null && !lookFor.equals(allLine)) {
			return "ERROR /n "+allLine;
		}
		
		System.out.println("Done visiting url");
		return allLine.trim();
	}
	
	public static ImageIcon resizePic(String iconLoc,int width,int height) {
		ImageIcon resizedImage = new ImageIcon(iconLoc);
		if(resizedImage.getIconHeight() > height)
			resizedImage = new ImageIcon(resizedImage.getImage().getScaledInstance(-1, height,  Image.SCALE_SMOOTH));
		if(resizedImage.getIconWidth() > width)
			resizedImage = new ImageIcon(resizedImage.getImage().getScaledInstance(width, -1,  Image.SCALE_SMOOTH));
		return resizedImage;
	}
	public static Path obscurePath() {
		return Paths.get(UUID.randomUUID().toString().replace("-","")); //Huge generated string
	}
	public static Object[] mergeArrays(Object[]... arrays) {
		List list = new ArrayList();
		
		for( Object[] array : arrays )
			list.addAll( Arrays.asList( array ) );
		
		return (Object[]) Array.newInstance( arrays[0][0].getClass(), list.size() );
	}
	
	public static class CopyData {
        public Path srcFilePath, destFilePath;
        public long kiloBytesCopied;
        public String type;
        
        public CopyData(String type) {
        	this.type = type;        		
        }
        
        public CopyData(Path srcFilePath, Path destFilePath, long kiloBytesCopied) {
        	this.destFilePath = destFilePath;
            this.srcFilePath = srcFilePath;
            this.kiloBytesCopied = kiloBytesCopied;
            this.type = "";
        }
    }
}
