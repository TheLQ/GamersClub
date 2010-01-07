/**
 * Class PeopleBrowser
 *
 * A subclass containing everything needed to handle the people browser
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
class PeopleBrowser extends JPanel implements ActionListener{
	
	public JPanel generate() {
		System.out.println("PeopleBrowser Class initiated");
		
		
		add(new JLabel("This is PeopleBrowser"));
		return this;
	}
	public void actionPerformed(ActionEvent e) {
    }
}
