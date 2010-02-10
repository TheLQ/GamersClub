 /**
 * Class GameBrowser
 *
 * Pastes the randomized game to the users folder of choice. Note that this is mostly a copy of CopyGame)
 *
 * @author Leon Blakey/Lord.Quackstar
 */
import java.awt.Component;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.nio.channels.FileChannel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.CancellationException;

import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import com.l2fprod.common.swing.JDirectoryChooser;

import org.json.me.JSONObject;

class PasteGame extends JPanel implements ActionListener {
	JLabel copyPLabel, dbPLabel; //all process labels
	JLabel taskStatusLabel,dirLocations; //progress bar label
	public JProgressBar progressBar;
	private CopyThread operation;
	
	public JPanel generate() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		/***Setup Process List***/
		JPanel processList = new JPanel();
		processList.setLayout(new BoxLayout(processList,BoxLayout.Y_AXIS));
		processList.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		processList.add(dbPLabel = createProcess("Fetching List from Database"));
		processList.add(copyPLabel = createProcess("Copying and Renaming Files (Will take a long time)"));
		add(processList);
		
		/***Setup Progress Bar***/
		JPanel progressPanel = new JPanel();
		progressPanel.setLayout(new BoxLayout(progressPanel,BoxLayout.Y_AXIS));
		progressPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(0,40,0,40), 
			BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		progressPanel.add(taskStatusLabel = new JLabel("",JLabel.LEFT));
		Globs.setTextSize(taskStatusLabel,20);
		taskStatusLabel.setMinimumSize(progressPanel.getMaximumSize());
		setTaskStatus("");
		progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
		progressPanel.add(progressBar);
		dirLocations = new JLabel("",JLabel.CENTER);
		dirLocations.setAlignmentX(Component.CENTER_ALIGNMENT);
		Globs.setTextSize(dirLocations,20);
		progressPanel.add(dirLocations);
		add(progressPanel);
		
		return this;
	}
	
	private JLabel createProcess(String text) {
		JLabel label = new JLabel();
		label.setText(text);
		Globs.setTextSize(label,20);
		label.setIcon(new ImageIcon(new ImageIcon("red_arrow.png").getImage().getScaledInstance(33, 25,  java.awt.Image.SCALE_SMOOTH)));
		return label;
	}
	
	private void setTaskStatus(String status) {
		taskStatusLabel.setText("<html>Task Status: "+status+"</html>");
	}
	
	private void currentTask(int num) {
		ImageIcon checkIcon = new ImageIcon(new ImageIcon("checkmark.png").getImage().getScaledInstance(33, 25,  java.awt.Image.SCALE_SMOOTH));
		ImageIcon greenIcon = new ImageIcon(new ImageIcon("green_arrow.png").getImage().getScaledInstance(33, 25,  java.awt.Image.SCALE_SMOOTH));
		switch(num) {
			case 1: 
				copyPLabel.setIcon(greenIcon); 
				break;
			case 2: 
				copyPLabel.setIcon(checkIcon); 
				dbPLabel.setIcon(greenIcon); 
				break;
			default: System.out.println("Invalid num specified"); break;
		}
	}
	
	private void setBarProgress(int progress) {
		try {
            progressBar.setValue(progress);
        }
        catch(Exception e) {
           	System.out.println("------------------ERROR-----------------");
            System.out.println("progressBar: "+progressBar);
            e.printStackTrace();
            System.out.println("--------------END ERROR-----------------");
        }
	}
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
	}
	
	/*******************************************************************************************************
	 * Start File Transfer Area
	 *******************************************************************************************************/
	public void config(String gameSrc) {
		Globs.switchBody("PasteGame");
		
		//Get destination Dir
		System.out.println("Generating Directory Chooser");
		JDirectoryChooser chooser = new JDirectoryChooser();
		int choice = chooser.showOpenDialog(null);
		if(choice == JDirectoryChooser.CANCEL_OPTION) {
			System.out.println("User Canceled");					
			return;
		}
		String gameDest = chooser.getSelectedFile().getAbsolutePath();
		System.out.println("Dialog Selection: " + gameDest);
		
		if(JOptionPane.showConfirmDialog(null, "Do you wish to start? \n NOTE: YOU CANNOT EXIT ONCE THE PROCESS IS STARTED!! \n MAKE SURE YOU HAVE ENOUGH TIME TO COPY THE GAME, ESPICALLY WITH LARGE ONE'S!!")!=JOptionPane.YES_OPTION) {
			Globs.switchBody("GameBrowser");
			return;
		}
		
		System.out.println("Starting copy");
		
		//Setup and run worker thread
		operation = new CopyThread(gameSrc, gameDest);
        operation.execute();
	}
	
	/******
	 * Needed Classes For Copying
	 * This Class simply initializes CopyFile class, but does so in the background thread
	 *****/
    class CopyThread extends SwingWorker<Void, Globs.CopyData> {        
        private static final int PROGRESS_CHECKPOINT = 100000;
        private Path srcDir, destDir;
        long totalBytes;
        int progress,downNum;
        Hashtable dirList = new Hashtable();
		Hashtable fileList = new Hashtable();
		Path newPicPath;
		String gameName;
		long bytesCopied = 0;
        
        CopyThread(String gameSrc, String gameDest) {
			this.srcDir = Paths.get(gameSrc);
			this.destDir = Paths.get(gameDest);
        }
        
        // perform time-consuming copy task in the worker thread
        @Override
        public Void doInBackground() {
            //Create remote directory if it dosen't exist
            if(!destDir.exists()) {
            	try { destDir.createDirectory(); }
			    catch(Exception e) { e.printStackTrace(); cancel(true); }
            }
            
            //Obtain File list
            System.out.println("Fetching all games from db");
            publish(new Globs.CopyData("dbfetch"));
            String response = Globs.webTalk("mode=makeGameFileList&folderName="+srcDir.toString(),null,null);
            try {
            	Hashtable responseJSON = new JSONObject(response).myHashMap;
            	fileList = new JSONObject(responseJSON.get("o1").toString()).myHashMap;
            	dirList  = new JSONObject(responseJSON.get("o2").toString()).myHashMap;
            	totalBytes = Long.parseLong(responseJSON.get("o3").toString());
            }
            catch(Exception e) {
            	e.printStackTrace();
            	cancel(true);
            }
            
            //Copy files by iterating over table
            System.out.println("Initiating file copy on path: " +srcDir.toString());
         	copyFiles();
         	
         	return null;
        }
		
		/***Copy files obtained from db***/
		public void copyFiles() {
			try {
				//First need to create directories
				Iterator dirItr = dirList.entrySet().iterator();
				while(dirItr.hasNext()) {
					Map.Entry<String, String> currentKey = (Map.Entry<String, String>)dirItr.next();
					Path dir = destDir.resolve(Paths.get(currentKey.getValue().toString()));
					System.out.println("Creating dir path: "+dir.toString());
					Files.createDirectories(dir); //recusivley create dirs
				}
				
				//Now can copy files
				Iterator fileItr = fileList.entrySet().iterator();
				
				while(fileItr.hasNext()) {
					Map.Entry currentFile = (Map.Entry)fileItr.next();
					Path relativeSrcFile = srcDir.resolve(Paths.get(currentFile.getKey().toString()));
					Path relativeDestFile = Paths.get(currentFile.getValue().toString());
					Path realDestFile = destDir.resolve(relativeDestFile);
					
					//Copy the file
					FileChannel in = new FileInputStream(relativeSrcFile.toString()).getChannel();
	        		FileChannel out = new FileOutputStream(realDestFile.toString()).getChannel();
			       	long size = in.size();
			       	long presize = 0;
			       	long position = 0;
			       
			       	while (position < size) {
				      	position += in.transferTo(position, PROGRESS_CHECKPOINT, out);
				       		
				       	//transfer of progress data complete, calculate info
				       	bytesCopied += out.size() - presize;
				       	presize = out.size();
				       	progress = (int)(100*((float)bytesCopied / (float)totalBytes));
	                    Globs.CopyData current = new Globs.CopyData(relativeSrcFile,realDestFile,getKiloBytes(bytesCopied));
	                    setProgress(progress);
	                    publish(current);
					}
				}
			}
			catch(Exception e) {
				System.out.println("------------------ERROR-----------------");
				System.out.println("Progress: " +progress+ " | Bytes Copied: "+bytesCopied+" | Bytes Total: "+totalBytes+" | Bytes Divided: "+(int)((100*(float)bytesCopied / totalBytes)));
				e.printStackTrace();
				System.out.println("--------------END ERROR-----------------");
	                    	
	            //kill to prevent runaway erros
	            cancel(true);
			}
		}
		
		
		
        // process copy task progress data in the event dispatch thread
        @Override
        public void process(List<Globs.CopyData> data) {
            if(isCancelled()) { return; }
            Globs.CopyData update  = new Globs.CopyData(null,null, 0);
            for (Globs.CopyData d : data) {
                // progress updates may be batched, so get the most recent
                if(d.type.equals("dbfetch")) {
            		currentTask(1);
            		setTaskStatus("Working...");
            		dirLocations.setText("<HTML><h4>Source Directory: "+srcDir.toString()+"<br>Target Directory: "+destDir.toString()+"</HTML>");
            		return;
            	}
                if (d.kiloBytesCopied >= update.kiloBytesCopied) {
                    update = d;
                }
            }
            
            String progressNote =  "Source File:  " + srcDir.relativize(update.srcFilePath).toString()+
            						"<p>Destination File:  "+destDir.relativize(update.destFilePath).toString()+
              						"<p>" + update.kiloBytesCopied + " of " + getKiloBytes(totalBytes) + " kb copied.";
            currentTask(2);
	        setTaskStatus(progressNote);
	        setBarProgress(progress);
            //System.out.println(progressNote);  
        }
        
        // perform final updates in the event dispatch thread
        @Override
        public void done() {
            try {
                Void result = get();
                System.out.println("Copy operation completed.\n");
                setBarProgress(100); //sometimes process isn't called to finish up, so...
                JOptionPane.showMessageDialog(null,"Download Complete! Press okay to continue");
                GamersClub.GameBrowser.generate(); //regenerate menu
                Globs.switchBody("GameBrowser");
            } catch (InterruptedException e) {
                
            } catch (CancellationException e) {
                System.err.println("Copy operation canceled.\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        //Format the KB to a nice value
        private long getKiloBytes(long totalBytes) {
            return Math.round(totalBytes / 1024);
        }
    }
}
