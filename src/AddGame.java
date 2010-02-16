/**
 * Class AddGame
 *
 * Makes Main Menu and redirects to other panels
 *
 * @author Leon Blakey/Lord.Quackstar
 */
import com.jgoodies.forms.builder.DefaultFormBuilder;

import com.jgoodies.forms.layout.FormLayout;

import com.l2fprod.common.swing.JDirectoryChooser;

import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.me.JSONObject;

class AddGame extends JPanel implements ActionListener {
	JTextField gameDirField, gameName, gameCreate, typeCustom, uploadName;
	JTextArea gameDesc, descPane;
	JComboBox typeBox;
	 
	JLabel gameDirPath,picPath;
	
	public AddGame() {
		gameDirPath = new JLabel();
		picPath = new JLabel();
	}
	
	public JPanel generate() {
		System.out.println("AddGame Class initiated");
		setLayout(new BorderLayout());

        // Column specs only, rows will be added dynamically.
        FormLayout layout = new FormLayout(
                "left:[40dlu,pref], 3dlu, 80dlu, 16dlu, "
              + "left:[40dlu,pref], 3dlu, 150dlu");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

		//Folder Area
        builder.appendSeparator("Step 1) Select the folder that the game is in");
        builder.append(createBrowse(gameDirPath,"DIR_ONLY"),7);
        builder.nextLine();
		
		//Game Section
		builder.appendSeparator("Step 2) Fill out info about game");
        builder.append("Game Name: ", gameName = new JTextField(20));
        builder.append("<HTML>Upload Name: <br>(IE: Extractor by Zerox, One-time-extract by <br>LordQuackstar, Portable by d3athwilcom3)", uploadName = new JTextField(20));
		builder.nextLine();
		
		//Generate TypePanel for Game Section
		JPanel typePanel = new JPanel();
		String response = Globs.webTalk("mode=getTypes",null,null);
		System.out.println("Response: "+response);
		Object[] typeArray;
		try { 
			typeArray = new JSONObject(response).myHashMap.values().toArray(); 
			typeBox = new JComboBox(typeArray);
			typeBox.addActionListener(this);
			typeBox.setActionCommand("TypeBox");
			typeBox.setSelectedIndex(0);
			typePanel.add(typeBox);
		}
		catch(Exception e) { e.printStackTrace(); }
		typePanel.add(typeCustom = new JTextField("Custom",17));
		typeCustom.setEnabled(false);
		
        builder.append("<HTML>Game Creation Date: <br>(Wikipedia and google are your friends) <br>(XX/YY/ZZZZ Format ONLY)</HTML>", gameCreate = new JTextField(10));
        builder.append("<HTML>Game Type: <br>Select Game Type from menu or select<br>other and type one in.</HTML>",typePanel);
        builder.nextLine();

		//Picture
        builder.appendSeparator("Step 3) Select the best picture for this game");
        builder.append(createBrowse(picPath,"FILE_ONLY"),7);
        builder.nextLine();
        
        //Description
        builder.appendSeparator("Step 4) Describe the game");
        descPane = new JTextArea("Type your description here. (Please try and use wikipedia)",5,100);
		descPane.setBorder(null); 
		descPane.setLineWrap(true);
		descPane.setWrapStyleWord(true);
		descPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		JScrollPane descScroll = new JScrollPane(descPane);
        builder.append(descScroll,7);
        builder.nextLine();
        
        //Submit Button
        JButton submit = new JButton("Submit");
		submit.addActionListener(this);
		builder.appendSeparator("Step 5) Submit");
		builder.append(submit);
		
		//Add to Panel
        add(builder.getPanel());

		return this;
	}
	
	public JComponent addBorderTop(JComponent comp) {
		comp.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		return comp;
	}
	
	public JPanel createBrowse(final JLabel usePath, String useWhat) {
		final JPanel browsePanel = new JPanel();
		browsePanel.setLayout(new BorderLayout());
		//add Current folder field
		final JTextField browseField=new JTextField(100);
		browseField.setEditable(false);
		browsePanel.add(browseField,BorderLayout.CENTER);
		//add browse button
		JButton browseButton = new JButton("Browse");
		browseButton.setActionCommand(useWhat);
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = e.getActionCommand();
				if(type.equals("DIR_ONLY")) {
					System.out.println("Generating Directory Chooser");
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
				else if(type.equals("FILE_ONLY")) {
					System.out.println("Generating File Chooser");
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
		//Globs.setSize(browsePanel,45,0);
		return browsePanel;
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(cmd.equals("TypeBox")) {
			if(typeCustom == null) return;
			JComboBox cb = (JComboBox)e.getSource();
        	String selected = (String)cb.getSelectedItem();
        	if(selected.equals("Custom")) {
        		System.out.println("Change in TypeBox, making sure typeCustom is enabled");
        		JOptionPane.showMessageDialog(this,"IMPORTANT: This is an important part of the system. Keep it short, use acronyms, \nand make sure it is correct. It is very hard to fix broken types.");
        		typeCustom.setEnabled(true);
        	}
        	else {
        		System.out.println("Change in TypeBox, making sure typeCustom is disabled");
        		typeCustom.setEnabled(false);
        	}
		}
		
		if(cmd.equals("Submit")) {
			/***DO IMPORTANT FORM CHECKING***/
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			formatter.setLenient(false);
			Date date = null;
			File srcDir = new File(gameDirPath.getText());
			if(gameDirPath.getText().isEmpty()) {
				System.out.println("Game Dir Path String "+gameDirPath.toString());
				JOptionPane.showMessageDialog(this,"You must select the folder that the game is in!");
				return;
			}
        	else if (!(srcDir.exists() && (srcDir.listFiles() != null && srcDir.listFiles().length > 0))) {
        		JOptionPane.showMessageDialog(this,"Selected Game Directory is empty!");
				return;
        	}
			else if(picPath.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this,"You must select an image!");
				return;
			}
			else if(gameName.getText().isEmpty() ) {
				JOptionPane.showMessageDialog(this,"Name to short or not specified!");
				return;
			}
			else if((gameCreate.getText().length() != 10) || (gameCreate.getText().charAt(6) == '/') || (gameCreate.getText().charAt(3) == '/')) {
				JOptionPane.showMessageDialog(this,"Must use correct date format!");
				return;
			}
			else if(descPane.getText().equals("Type your description here. (Please try and use wikipedia)")) {
				JOptionPane.showMessageDialog(this,"Must fill out a description! (Wikipedia and google are your friends)");
				return;
			}
			else if(typeBox.getSelectedIndex() == 0) {
				System.out.println("typeBox index num: "+typeBox.getSelectedIndex());
				JOptionPane.showMessageDialog(this,"Must Select a game type");
				return;
			}
			else if(typeCustom.getText().equals("Custom") && typeBox.getSelectedIndex() == typeBox.getItemCount() ) {
				System.out.println("typeBox index num: "+typeBox.getSelectedIndex());
				JOptionPane.showMessageDialog(this,"Custom game type must have a name");
				return;
			}
			else if(uploadName.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this,"Upload name not specified!");
				return;
			}
			try { date = formatter.parse(gameCreate.getText()); }
			catch (Exception ex) { JOptionPane.showMessageDialog(this,"Must use correct date format! \nError:"+ex.toString()); return;}
			System.out.println(date.toString());
			/***Form checking done, no errors, start copy***/
			String type = (typeBox.getSelectedIndex() == typeBox.getItemCount()-1) ? typeCustom.getText() : (String)typeBox.getSelectedItem();
			GamersClub.CopyGame.config(gameDirPath.getText(),picPath.getText(),gameName.getText(),date.getTime(),descPane.getText(),type,uploadName.getText());
		}
    }
}
