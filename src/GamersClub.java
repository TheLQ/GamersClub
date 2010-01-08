/**
 * @(#)GamersClub.java
 *
 * GamersClub Main application
 *
 * @author Leon Blakey/Lord.Quackstar
 * @version 1.00 2009/12/5
 */
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.mysql.jdbc.Driver;
 
public class GamersClub extends JFrame implements ActionListener {
    JPanel contentPane;
    public static JPanel bodyPanel;
    Border bodyBorder;
    
	public GamersClub () {      	
      	/***Init***/
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Will exit when close button is pressed
     	setTitle("Gamers Club Distrobution Service");
       	setMinimumSize(new Dimension(1000,700));
       	initLookAndFeel("System",""); //changes look and feel for interesting gui
       	
       	try {
	    	/***Connect to database***/
			Class.forName("com.mysql.jdbc.Driver");
			Globs.conn = DriverManager.getConnection ("jdbc:mysql://localhost/gamersclub", "root", "");
			System.out.println ("Database connection established");
			
			/***Check User Credentials***/
			String username = System.getProperty("user.name");
			System.out.println("Username: "+username);
			Statement s = Globs.conn.createStatement();
			ResultSet rs = s.executeQuery ("SELECT * FROM users WHERE `username`='"+username+"'");
			int count = 0;
			while (rs.next()) {
				String nameVal = rs.getString ("name");
				System.out.println("name = " + nameVal);
				++count;
			}			
			if(count == 0) {
				JOptionPane.showMessageDialog(null,"You are not in the Gamers Club.");
				System.exit(0);
			}
			else
				System.out.println("Username matches to db");
       	}
      	catch(Exception e) {
      		JOptionPane.showMessageDialog(null,"MYSQL Error in GamersClub:\n"+e.toString());
      		System.exit(0);
      	}
		
          	
      	/***Get Basic Gui configured***/
      	contentPane = new JPanel(); //el massive panel that holds everything
      	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
      	contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      	
      	
      	/***Make Banner***/
      	JLabel bannerLabel = new JLabel("Gamers Club Distrobution Service",null,JLabel.CENTER);
		bannerLabel.setFont(new Font("Serif", Font.PLAIN, 36)); //make it big
		bannerLabel.setMinimumSize(new Dimension(600,100));
		bannerLabel.setBorder(BorderFactory.createLineBorder(Color.black)); //Black line border
		bannerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(bannerLabel,BorderLayout.CENTER);
		
		/***Make Main Body Panel***/
		bodyPanel = new JPanel(new CardLayout());
		bodyBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Home");
		bodyPanel.setBorder(bodyBorder);
		//Start adding panels
		bodyPanel.add(new MainMenu().generate(),"MainMenu");
		bodyPanel.add(new GameBrowser().generate(),"GameBrowser");
		bodyPanel.add(new PeopleBrowser().generate(),"PeopleBrowser");
		bodyPanel.add(new AddGame().generate(),"AddGame");
		CardLayout cl = (CardLayout)(bodyPanel.getLayout());
	    cl.show(bodyPanel, "MainMenu");
		bodyPanel.setMinimumSize(new Dimension(600,500));
		contentPane.add(bodyPanel);
		
		/***Finish Up***/
		add(contentPane); //add to JFrame
		setVisible(true); //make JFrame visible
	}
	
	
	
	/* Description: All the actionListeners for Main Menu
	 * Used by: Hierchy Buttons */
	public void actionPerformed(ActionEvent e) {
    	
    }
    
    /* Description: configures how the gui looks
     * Used by: GamersClub.GamersClub init*/
    public void initLookAndFeel(final String LOOKANDFEEL, final String THEME) {
        String lookAndFeel = null;
       
        if (LOOKANDFEEL != null) {
            if (LOOKANDFEEL.equals("Metal")) {
               lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
               //lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel"; //Alternative
            }
            else if (LOOKANDFEEL.equals("System")) 
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            else if (LOOKANDFEEL.equals("Motif")) 
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            else if (LOOKANDFEEL.equals("GTK")) 
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: "+LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }
            try {
                UIManager.setLookAndFeel(lookAndFeel);
                if (LOOKANDFEEL.equals("Metal")) {
                  if (THEME.equals("DefaultMetal"))
                     MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
                  else if (THEME.equals("Ocean"))
                     MetalLookAndFeel.setCurrentTheme(new OceanTheme());
                  else
                     //MetalLookAndFeel.setCurrentTheme(new TestTheme());
                  UIManager.setLookAndFeel(new MetalLookAndFeel()); 
                }
            } 
            catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"+lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            }
            catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("+lookAndFeel+") on this platform.");
                System.err.println("Using the default look and feel.");
            }
            catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("+lookAndFeel+"), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
      //needed for possible uncaught exceptions
      try {	new GamersClub(); }
      catch(Exception e) {
      		System.out.println("Error message: " + e.toString());
      }
      
    }
}