/**
 * Method Globs
 *
 * Contains all the global functions needed
 *
 * @author Leon Blakey/Lord.Quackstar
 */
 import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
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
	
	
}

/*		String userName = "lhsgamersclub";
        String password = "iU5QG*JzfGak8HE8qT";
		String url = "jdbc:mysql://SQL09.FREEMYSQL.NET/gamersclub";*/