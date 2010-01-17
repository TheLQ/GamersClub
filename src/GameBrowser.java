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
import java.sql.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

import org.jdesktop.swingx.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.nio.file.*;
public class GameBrowser extends JXMultiSplitPane implements ActionListener,TreeSelectionListener{
	JPanel picPanel, descPanel,downPanel;
	Path gameDir;
	boolean valueChangedRunning = false;
	int valueHash;
	
	/*Returns a completed JPanel for master class*/
	public JXMultiSplitPane generate() {
		System.out.println("GameBrowser Class initiated");
		
		/***Configure layout***/		
		getMultiSplitLayout().setModel(MultiSplitLayout.parseModel("(ROW (LEAF name=sidebar weight=0.25) (COLUMN weight=0.75 (ROW weight=0.6 (LEAF name=pic weight=0.25) (LEAF name=desc weight=0.75)) (LEAF name=download weight=0.4)))))"));
		add(buildTreeMenu(),"sidebar");
		add(picPanel = new JPanel(),"pic");
		add(descPanel = new JPanel(),"desc");
		add(downPanel = new JPanel(),"download");
		picPanel.add(new JLabel("Please Select a Game",JLabel.CENTER));
		
		picPanel.setLayout(new BoxLayout(picPanel,BoxLayout.Y_AXIS));
		descPanel.setLayout(new BoxLayout(descPanel,BoxLayout.Y_AXIS));
		downPanel.setLayout(new BoxLayout(downPanel,BoxLayout.Y_AXIS));	
		
		return this;
	}
	
	private JPanel buildTreeMenu() {
        //Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Game Broser");
		
        //Create a tree that allows one selection at a time.
        JTree tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
		
        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        
        /***Configure Pane***/
        JScrollPane treeView = new JScrollPane(tree);
        JPanel treePanel = new JPanel();
        treePanel.setLayout(new BoxLayout(treePanel,BoxLayout.Y_AXIS));
        treePanel.add(treeView);
        JButton addGame = new JButton ("Add Game");
        addGame.addActionListener(this);
        treePanel.add(addGame);
        
        
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
      		e.printStackTrace();
      	}
      	
      	
		return treePanel;
    }
    
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.getNewLeadSelectionPath().getLastPathComponent();
    	int currentHash = e.hashCode();
    	
    	if(valueHash == currentHash) {
    		System.out.println("ValueHash: Already running, exit");
    		return; //already running
    	}
    	else {
    		System.out.println("ValueHash: Not running. Setting var as true and continuing");
    		valueHash=currentHash;
    	}
    	
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
        		
        		/***Setup desccription pane***/
        		descPanel.removeAll();
        		String desc = rs.getString("desc");
		    	JTextArea descPane = new JTextArea(desc);
		      	descPane.setEditable(false);
		      	descPane.setBorder(null); 
		      	descPane.setLineWrap(true);
		      	descPane.setWrapStyleWord(true);
		      	descPane.setBackground(this.getBackground());
		      	descPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		      	descPanel.add(descPane);
		      	
		      	/***Setup Picture Pane***/
		      	picPanel.removeAll();
		      	JLabel gamePic = new JLabel("",JLabel.CENTER);
		      	Path picPath = gameDir.resolve(rs.getString("picture"));
		      	ImageIcon gamePicIcon;
				if(picPath.exists()!=true) {
			    	System.out.println("Game provided image not avalible");
			    	gamePicIcon = new ImageIcon("noImage.jpg");
			    }
			    else {
			    	gamePicIcon = new ImageIcon(picPath.toString());
			    }
			    //overcomplicated way to resize
			    gamePic.setIcon(new ImageIcon(gamePicIcon.getImage().getScaledInstance(300, -1,  Image.SCALE_SMOOTH)));  
			    picPanel.add(gamePic);
			    //add dates
			    picPanel.add(new JLabel("<HTML><b>Date Added:</b> " + DateFormat.getDateInstance(DateFormat.FULL).format(Long.valueOf(rs.getString("addDate").trim())) + "</HTML>"));
			    picPanel.add(new JLabel("<HTML><b>Date Game Created:</b> " + DateFormat.getDateInstance(DateFormat.FULL).format(Long.valueOf(rs.getString("createDate").trim())) + "</HTML>"));
			
				/***Setup Download Pane***/
				
			}
        	catch(Exception ex) {
      			ex.printStackTrace();
      		}
    	} 
    	else {
        	System.out.println("NodeList Selected: "+nodeInfo); 
    	}
    	repaint();
		revalidate();
		System.out.println("Running");
		this.valueChangedRunning = false;
    }
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(cmd.equals("Add Game")) {
			Globs.switchBody("AddGame");
		}
    }
}
