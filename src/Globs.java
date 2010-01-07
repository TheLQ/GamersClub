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
import javax.swing.plaf.metal.*;
import java.io.*;
import javax.swing.filechooser.*;
import javax.swing.text.*;
import java.sql.*;
public class Globs {
	public static Connection conn;
	public static void setSize(JComponent comp, int height, int width) {
     	Dimension size = comp.getPreferredSize();
     	if(height!=0) {
      		size.height = height;
     	}
     	else if(width!=0) {
     		size.width = width;
     	}
     	comp.setMinimumSize(size);
    }
    
    public static void setFormat(JComponent comp, int size, Color background) {
        comp.setBackground(background);
    	comp.setFont(new Font(comp.getFont().getName(),comp.getFont().getStyle(),size));
    }
    
    public static JButton buttonSetup(String name, ActionListener al) {
    	JButton main = new JButton(name);
    	main.addActionListener(al);
    	return main;
    }


    
    /* Description: Changes body to a new panel (removes code duplication)
	 * Used by: MainMenu.actionPerformed and main menu buttons*/
	public static void switchBody(JComponent newBody) {
		System.out.println("Switching Body Panels");
		GamersClub.bodyPanel.removeAll();
		GamersClub.bodyPanel.add(newBody);
		GamersClub.bodyPanel.revalidate();
		GamersClub.bodyPanel.repaint();
	}
	
	
}

/*		String userName = "lhsgamersclub";
        String password = "iU5QG*JzfGak8HE8qT";
		String url = "jdbc:mysql://SQL09.FREEMYSQL.NET/gamersclub";*/