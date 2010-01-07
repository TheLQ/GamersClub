/**
 * Class GameBrowser
 *
 * A subclass containing everything needed to handle the game browser
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
import java.util.*;
import java.text.*;

import org.jdesktop.swingx.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.nio.file.*;
public class GameBrowser extends JXMultiSplitPane implements ActionListener,TreeSelectionListener{
	JPanel picPanel, descPanel;
	Path gameDir;
	boolean valueChangedRunning = false;
	
	/*Returns a completed JPanel for master class*/
	public JXMultiSplitPane generate() {
		System.out.println("GameBrowser Class initiated");
		
		/***Configure layout***/		
		getMultiSplitLayout().setModel(MultiSplitLayout.parseModel("(ROW (LEAF name=sidebar weight=0.25) (COLUMN weight=0.75 (ROW weight=0.75 (LEAF name=pic weight=0.25) (LEAF name=desc weight=0.75)) (LEAF name=download weight=0.25)))))"));
		add(buildTreeMenu(),"sidebar");
		add(picPanel = new JPanel(),"pic");
		add(descPanel = new JPanel(),"desc");
		add(new JButton("download"),"download");
		
		picPanel.setLayout(new GridLayout(1,1));
		picPanel.add(new JLabel("Please Select a Game",JLabel.CENTER));
		descPanel.setLayout(new BoxLayout(descPanel,BoxLayout.Y_AXIS));
		return this;
	}
	
	private JScrollPane buildTreeMenu() {
        //Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Game Broser");
		
        //Create a tree that allows one selection at a time.
        JTree tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
		
        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        
        JScrollPane treeView = new JScrollPane(tree);
        
        /***Start adding nodes***/
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;
		
		try {
			ResultSet rs = Globs.conn.createStatement().executeQuery ("SELECT DISTINCT type,`name`,`desc`,`picture`,`download` FROM games");
			while (rs.next()) {
				String typeVal = rs.getString ("type");
				top.add(category = new DefaultMutableTreeNode(typeVal));
				
				ResultSet rs2 = Globs.conn.createStatement().executeQuery ("SELECT * FROM games WHERE `type`='"+typeVal+"'");
				while(rs2.next()) {
					String nameVal = rs2.getString("name");
					category.add(new DefaultMutableTreeNode(nameVal));
				}
			}
		}
		catch(Exception e) {
      		System.out.println("Error message: " + e.toString());
      	}
		return treeView;
    }
    
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.getNewLeadSelectionPath().getLastPathComponent();
    	
    	if(valueChangedRunning == true) 
    		return; //already running
    	else
    		valueChangedRunning = true;
    	
    	
    	//is anything selected?
    	if (node == null)
    		return;

    	Object nodeInfo = node.getUserObject();
    	if (node.isLeaf()) {
        	System.out.println("Leaf Selected: "+nodeInfo);
        	try {
        		ResultSet rs = Globs.conn.createStatement().executeQuery ("SELECT * FROM games WHERE `name`='"+nodeInfo+"'");
        		rs.next();
        		
        		//setup directory
		      	String dir = rs.getString("dir");
		      	picPanel.removeAll();
		      	gameDir = Paths.get(dir);
		      	if(gameDir.notExists() == true) {
		      		System.out.println("Game dir does not exist");
		      		System.exit(0);
		      	}
        		
        		//Setup desccription pane
        		descPanel.removeAll();
        		String desc = rs.getString("desc");
		    	JTextArea descPane = new JTextArea(desc);
		      	descPane.setEditable(false);
		      	descPane.setBorder(null); 
		      	descPane.setLineWrap(true);
		      	descPanel.add(descPane);
		      	
		      	//setup image
		      	picPanel.removeAll();
		      	JLabel gamePic = new JLabel("",JLabel.CENTER);
		      	Path picPath = gameDir.resolve(rs.getString("picture"));
				if(picPath.exists()!=true) {
			    	System.out.println("Game provided image not avalible");
			    	gamePic.setIcon(new ImageIcon("noImage.jpg"));
			    }
			    else {
			    	gamePic.setIcon(new ImageIcon(picPath.toString()));
			    }
			    picPanel.add(gamePic);
			    System.out.println(Calendar.getInstance().getTimeInMillis());
			    System.out.println("Date Added: " + DateFormat.getDateInstance(DateFormat.FULL).format((int)rs.getString("addDate")));
			}
        	catch(Exception ex) {
      			System.out.println("Error message: " + ex.toString());
      		}
    	} 
    	else {
        	System.out.println("NodeList Selected: "+nodeInfo); 
    	}
    	repaint();
		revalidate();
		valueChangedRunning = false;
    }
	
	public void actionPerformed(ActionEvent e) {
    }
}
