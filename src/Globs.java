/**
 * Method Globs
 *
 * Contains all the global functions needed
 *
 * @author Leon Blakey/Lord.Quackstar
 */
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JComponent;

public class Globs {
	public static Connection conn;
	public static JComponent setSize(JComponent comp, int height, int width) {
     	Dimension size = comp.getPreferredSize();
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
	
	public static String webTalk(String url) {
		return webTalk(url,null);
	}
	
	public static String webTalk(String url,String postVars) {
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done visiting url");
		return allLine.trim();
	}	
}