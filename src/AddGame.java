import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.nio.file.*;
import java.net.URI;
import javax.swing.filechooser.*;
import java.io.*;
import java.text.*;
import java.util.*;

import com.l2fprod.common.swing.JDirectoryChooser;

class AddGame extends JPanel implements ActionListener {
	JTextField gameDirField, gameName, gameCreate;
	JTextArea gameDesc;
	 
	JLabel gameDirPath,picPath;
	
	public AddGame() {
		gameDirPath = new JLabel();
		picPath = new JLabel();
	}
	
	public JPanel generate() {
		System.out.println("AddGame Class initiated");
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		/***Setup Title***/
		add(new JLabel("<HTML><H1><U>Adding Game to Database</U></H1></HTML>"));
		
		/***Setup Select Game Area***/
		add(addBorderTop(createBrowse("1) Select the folder that the game is in.",gameDirPath,"DIR_ONLY")));
		
		/***Setup Game Info Area***/
		JPanel gameInfo = new JPanel();
		gameInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
		gameInfo.setLayout(new BoxLayout(gameInfo,BoxLayout.Y_AXIS));
		gameInfo.add(new JLabel("Step 2) Fill out info about game",JLabel.LEFT));
		gameInfo.add(new JLabel("Game Name: "));
		gameInfo.add(gameName = new JTextField(50));
		gameInfo.add(new JLabel("<HTML>Game Creation Date: <br>(XX/YY/ZZZZ Format ONLY)</HTML>"));
		gameInfo.add(gameCreate = new JTextField(10));
		Globs.setSize(gameInfo,100,0);
		add(addBorderTop(gameInfo));
		
		/***Setup Picture Browse Area***/
		add(addBorderTop(createBrowse("Step 3) Select the best picture for this game: ",picPath,"FILE_ONLY")));
		
		/***Setup Submit button***/
		JButton submit = new JButton("Submit");
		submit.setAlignmentX(Component.LEFT_ALIGNMENT);
		submit.addActionListener(this);
		add(submit);
		
		return this;
	}
	
	public JComponent addBorderTop(JComponent comp) {
		comp.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		return comp;
	}
	
	public JPanel createBrowse(String instr,final JLabel usePath, final String useWhat) {
		final JPanel browsePanel = new JPanel();
		browsePanel.setLayout(new BorderLayout());
		//add Instructions
		browsePanel.add(new JLabel(instr,JLabel.LEFT),BorderLayout.PAGE_START);
		//add Current folder field
		final JTextField browseField=new JTextField(100);
		browseField.setEditable(false);
		browsePanel.add(browseField,BorderLayout.CENTER);
		//add browse button
		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(useWhat.equals("DIR_ONLY")) {
					JDirectoryChooser chooser = new JDirectoryChooser();
					int choice = chooser.showOpenDialog(null);
					if(choice == JDirectoryChooser.CANCEL_OPTION) {
						System.out.println("User Canceled");					
						return;
					}
					String gamePath = chooser.getSelectedFile().getAbsolutePath();
					usePath.setText(gamePath);
					System.out.println("Dialog Selection: " + gamePath);
					browseField.setText(gamePath);
				}
				else if(useWhat.equals("FILE_ONLY")) {
					JFileChooser fc = new JFileChooser();
    				fc.setFileFilter(new FileNameExtensionFilter("All Pictures","tiff" ,"tif","gif","jpeg","jpg","bmp","png"));
					int returnVal = fc.showOpenDialog(null);

		            if (returnVal != JFileChooser.APPROVE_OPTION) {
		            	System.out.println("User Canceled");					
						return;
		            }
		            String gamePath = fc.getSelectedFile().getAbsolutePath();
		            usePath.setText(gamePath);
		            System.out.println("Dialog Selection: " + gamePath);
					browseField.setText(gamePath);
				}
			}
		});
		browsePanel.add(browseButton,BorderLayout.LINE_END);
		browsePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		Globs.setSize(browsePanel,55,0);
		return browsePanel;
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(cmd.equals("Submit")) {
			/***DO IMPORTANT FORM CHECKING***/
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			java.util.Date date = null;
			File srcDir = new File(gameDirPath.getText());
			if(gameDirPath.getText().isEmpty()) {
				System.out.println("Game Dir Path String "+gameDirPath.toString());
				JOptionPane.showMessageDialog(null,"You must select the folder that the game is in!");
				return;
			}
        	else if (!(srcDir.exists() && (srcDir.listFiles() != null && srcDir.listFiles().length > 0))) {
        		JOptionPane.showMessageDialog(null,"Selected Game Directory is empty!");
				return;
        	}
			else if(picPath.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,"You must select an image!");
				return;
			}
			else if(gameName.getText().isEmpty() ) {
				JOptionPane.showMessageDialog(null,"Name to short or not specified!");
				return;
			}
			else if((gameCreate.getText().length() != 10) || (gameCreate.getText().charAt(6) == '/') || (gameCreate.getText().charAt(3) == '/')) {
				JOptionPane.showMessageDialog(null,"Must use correct date format!");
				return;
			}
			try { date = (java.util.Date)formatter.parse(gameCreate.getText()); }
			catch (Exception ex) { JOptionPane.showMessageDialog(null,"Must use correct date format! \nError:"+ex.toString()); }
		
			/***Form checking done, no errors, start copy***/
			GamersClub.CopyGame.config(gameDirPath.getText(),picPath.getText(),gameName.getText(),date);
		}
    }
}