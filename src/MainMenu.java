/**
 * Method MainMenu
 *
 * Everything needed for the game browser
 *
 * @author Leon Blakey/Lord.Quackstar
 */
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends JPanel implements ActionListener {
	
	public JPanel generate() {
		System.out.println("Inside of MainMenu Class");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(buttonMake("List Games"));
		add(buttonMake("List People"));
		add(buttonMake("Profile"));
		add(buttonMake("Options"));
		add(buttonMake("Help"));
		
		revalidate(); // this GUI needs relayout
        repaint();

		return this;
	}
	
	public JButton buttonMake(String text) {
		JButton main = new JButton(text);
		main.addActionListener(this);
		//Globs.setSize(main,20,0);
		main.setAlignmentX(Component.CENTER_ALIGNMENT);
		Globs.setTextSize(main,15);
		return main;
	}
	
	public void actionPerformed(ActionEvent e) {
    	//NOTE: MainMenu uses this
    	String cmd = e.getActionCommand();
    	if(cmd.equals("List Games")) {
    		Globs.switchBody("GameBrowser");
    	}
    	else if(cmd.equals("List People")) {
    		Globs.switchBody("PeopleBrowser");
    	}
    	else if(cmd.equals("Profile")) {
    		
    	}
    	else if(cmd.equals("Options")) {
    		
    	}
    	else if(cmd.equals("Help")) {
    		
    	}
    }
}
