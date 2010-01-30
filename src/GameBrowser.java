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
import javax.swing.tree.*;

import org.jdesktop.swingx.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.json.me.*;

import java.nio.file.*;
public class GameBrowser extends JXMultiSplitPane implements ActionListener,TreeSelectionListener{
	JPanel picPanel, descPanel,downPanel;
	Path gameDir;
	boolean valueChangedRunning = false;
	int valueHash;
	TreeMap<String,Map> gameData = new TreeMap<String,Map>();
	
	/*Returns a completed JPanel for master class*/
	public JXMultiSplitPane generate() {
		System.out.println("GameBrowser Class initiated");
		
		removeAll(); //Might get recalled, so clear panel first
		
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
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
		
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
		try {
			DefaultMutableTreeNode category = null;
        	DefaultMutableTreeNode book = null;
		
			System.out.println("Asking server for game list");
			String response = Globs.webTalk("mode=buildTree");
			//System.out.println("Response: "+response);
			
			//use modified JSONObject to return hashmap iterator
			Iterator dbIter = new JSONObject(response).myHashMap.entrySet().iterator(); 
			
			//Init values
			Map.Entry typeRow = null;
			Iterator typeIter = null;
			String gameRow = "";
			String key = "";
			
			while(dbIter.hasNext()) {
				//currently looping through game types
				typeRow = (Map.Entry)dbIter.next();
				
				//Add key to big array
				key = (String)typeRow.getKey();
				top.add(category = new DefaultMutableTreeNode(key));
				System.out.println("typeRow value string: "+key);
				
				//Note: JSONArray stores values in Vector, not Map
				typeIter = ((JSONArray)typeRow.getValue()).myArrayList.iterator();
				
				while(typeIter.hasNext()) {
					//currently looping through games in game types
					gameRow = (String)typeIter.next();
					
					//Now we have excaped game info json string, need to unescape it
					gameRow = gameRow.replace("\\\"","\"");
					Hashtable gameValue = new JSONObject((String)gameRow).myHashMap;
					
					//Make a TreeMap to add to big class TreeMap
					String name = gameValue.get("name").toString();
					gameData.put(name,(Map)gameValue);
					
					category.add(new DefaultMutableTreeNode(name));
					System.out.println("Name: "+name);
				}
			}
		}
		catch(Exception e) {
      		e.printStackTrace();
      	}
      	
      	//Make it expand
      	tree.expandPath(new TreePath(top.getPath()));
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
        	
        	//Get resultset
        	Map gameInfo = gameData.get(nodeInfo.toString());
        	
        	//setup directory
		  	String dir = gameInfo.get("dir").toString();
		   	picPanel.removeAll();
		   	gameDir = Paths.get(dir);
		   	if(gameDir.notExists() == true) {
		   		System.err.println("Game dir does not exist. Dir: "+dir);
		   		return;
		   	}
            
            /***Setup desccription pane***/
        	descPanel.removeAll();
        	String desc = gameInfo.get("desc").toString();
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
		    Path picPath = gameDir.resolve(gameInfo.get("picture").toString()); 
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
			picPanel.add(new JLabel("<HTML><b>Date Added:</b> " + DateFormat.getDateInstance(DateFormat.FULL).format(Long.valueOf(gameInfo.get("addDate").toString().trim())) + "</HTML>"));
			picPanel.add(new JLabel("<HTML><b>Date Game Created:</b> " + DateFormat.getDateInstance(DateFormat.FULL).format(Long.valueOf(gameInfo.get("createDate").toString().trim())) + "</HTML>"));
			
			/***Setup Download Pane***/
			downPanel.removeAll();
			try {
				Iterator downJSON = new JSONObject(gameInfo.get("picture").toString()).myHashMap.entrySet().iterator();
				while(downJSON.hasNext()) {
					Map.Entry currentDir = (Map.Entry)downJSON.next();
					JButton downButton = new JButton(currentDir.getKey().toString());
					downButton.setActionCommand(currentDir.getValue().toString());
					downButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//Simply pass control to PasteGame
							GamersClub.PasteGame.config(e.getActionCommand());
						}
					});
					downPanel.add(downButton);
				}
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

/*********CODE ARCHIVES**************
 *
 ****************************************
 * Old sql code, dropped in favor of php middeman
 *****************************************
 *ResultSet rs = Globs.conn.createStatement().executeQuery ("SELECT DISTINCT type,`name`,`desc`,`picture`,`download` FROM games");
			while (rs.next()) {
				String typeVal = rs.getString ("type");
				top.add(category = new DefaultMutableTreeNode(typeVal));
				
				ResultSet rs2 = Globs.conn.createStatement().executeQuery ("SELECT * FROM games WHERE `type`='"+typeVal+"'");
				while(rs2.next()) {
					String nameVal = rs2.getString("name");
					category.add(new DefaultMutableTreeNode(nameVal));
				}
			}*/