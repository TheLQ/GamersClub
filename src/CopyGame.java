/*******************************
 * CopyGame by Leon Blakey
 *
 * @desc: Copies the game that the user submits
 *******************************/

import javax.swing.*;
import java.awt.Component;
import java.awt.event.*;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.border.*;
import java.nio.file.*;
import java.util.Date;
import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.nio.channels.*;
import java.nio.file.attribute.*;
import java.lang.*;
import org.json.me.*;
import java.net.*;
import java.text.*;

class CopyGame extends JPanel implements ActionListener {
	JLabel indexPLabel, copyPLabel, dbPLabel; //all process labels
	JLabel taskStatusLabel,dirLocations; //progress bar label
	String picPath, gameName;
	Path gamePath;
	public JProgressBar progressBar;
	private CopyThread operation;
	String gameDesc, gameType, gameDate, uploadName;
	
	public JPanel generate() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		/***Setup Process List***/
		JPanel processList = new JPanel();
		processList.setLayout(new BoxLayout(processList,BoxLayout.Y_AXIS));
		processList.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		processList.add(indexPLabel = createProcess("Indexing Directories"));
		processList.add(copyPLabel = createProcess("Copying and Renaming Files (Will take a long time)"));
		processList.add(dbPLabel = createProcess("Submit to database"));
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
				indexPLabel.setIcon(greenIcon); 
				break;
			case 2: 
				indexPLabel.setIcon(checkIcon); 
				copyPLabel.setIcon(greenIcon); 
				break;
			case 3: 
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
	public void config(String gameDir, String picPath, String gameName, Long gameDate, String gameDesc, String gameType, String uploadName) {
		Globs.switchBody("CopyGame");
		if(JOptionPane.showConfirmDialog(null, "Do you wish to start? \n NOTE: YOU CANNOT EXIT ONCE THE PROCESS IS STARTED!! \n MAKE SURE YOU HAVE ENOUGH TIME TO COPY THE GAME, ESPICALLY WITH LARGE ONE'S!!")!=JOptionPane.YES_OPTION) {
			Globs.switchBody("AddGame");
			return;
		}
		gamePath = Paths.get(gameDir);
		this.picPath = picPath;
		this.gameName = gameName;
		this.gameDate = gameDate.toString();
		this.gameDesc = gameDesc;
		this.gameType = gameType;
		this.uploadName = uploadName;
		
		System.out.println("Starting copy");
		
		//Setup and run worker thread
		operation = new CopyThread(gamePath, obscurePath());
        operation.execute();
	}
	
	public Path obscurePath() {
		return Paths.get(UUID.randomUUID().toString().replace("-","")); //Huge generated string
	}
	
	/******
	 * Needed Classes For Copying
	 * This Class simply initializes CopyFile class, but does so in the background thread
	 *****/
    class CopyThread extends SwingWorker<Void, CopyData> {        
        private static final int PROGRESS_CHECKPOINT = 100000;
        private Path srcDir, destDir;
        long totalBytes;
        int progress;
        TreeMap<Path,Path> dirList = new TreeMap<Path,Path>();
		TreeMap<Path,Path> fileList = new TreeMap<Path,Path>();
		Path newPicPath;
        
        CopyThread(Path src, Path dest) {
            this.srcDir = src;
            this.destDir = dest;
        }
        
        // perform time-consuming copy task in the worker thread
        @Override
        public Void doInBackground() {
            //Create remote directory if it dosen't exist
            if(!destDir.exists()) {
            	try { destDir.createDirectory(); }
			    catch(Exception e) { e.printStackTrace(); cancel(true); }
            }
            
            //Calculate total data 
            System.out.println("Starting Directory Transverse");
            publish(new CopyData("index"));
            traverse(srcDir);
            publish(new CopyData("Index Done"));
            
            //Copy Picture
            try {
	            System.out.println("Resizing and Saving Picture");
				newPicPath = destDir.resolve(obscurePath());
				ImageIcon resizedImage = new ImageIcon(new ImageIcon(picPath.toString()).getImage());
				if(resizedImage.getIconHeight() > 300)
					resizedImage = new ImageIcon(resizedImage.getImage().getScaledInstance(-1, 300,  Image.SCALE_SMOOTH));
				if(resizedImage.getIconWidth() > 300)
					resizedImage = new ImageIcon(resizedImage.getImage().getScaledInstance(300, -1,  Image.SCALE_SMOOTH));
	            BufferedImage resizedBImage = new BufferedImage (resizedImage.getIconWidth(), resizedImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
				resizedBImage.getGraphics().drawImage(resizedImage.getImage(), 0 , 0, null);
				ImageIO.write(resizedBImage, "png",new File(newPicPath.toString())); 
            }
            catch(Exception e) {
            	e.printStackTrace();
            }
            
            //Copy Files
            System.out.println("Initiating walk file tree on path: " +srcDir.toString());
            Files.walkFileTree(srcDir, new CopyFiles());
            
            //Update to db
            publish(new CopyData("DB Update"));
            updateDB();
            
            return null;
        }
		
		//Custom method to recursivly obtain files
		public final void traverse(Path f ) {
			publish(new CopyData("index"));
			try {
				//assume this is a directory
				DirectoryStream<Path> stream = f.newDirectoryStream();
				for(Path file : stream) {
					traverse(file);
				}
			}
			catch(NotDirectoryException e) {
				//Couldn't open directory stream on path, must be a file
				try { totalBytes+=(long)f.newByteChannel().size();  }
				catch (Exception ex) { ex.printStackTrace(); cancel(true); }
			}
			catch(Exception e) {
				e.printStackTrace();
				cancel(true);
			}
		}
		
		/***Update to DB***/
		public void updateDB() {
			//publish(new CopyData("DB Update"));
			
			//add to db
			try {
				//First make a part
				JSONObject master = new JSONObject();
				JSONObject dirJson = new JSONObject();
				JSONObject infoJson = new JSONObject();
				JSONObject fileJson = new JSONObject();
				infoJson.put("name",gameName);
				infoJson.put("desc",gameDesc);
				infoJson.put("type",gameType);
				infoJson.put("picture",newPicPath.toString());
				infoJson.put("dir",destDir.toString());
				infoJson.put("uploadName",uploadName);
				infoJson.put("createDate",gameDate);
				infoJson.put("addDate",System.currentTimeMillis());
				infoJson.put("addBy",GamersClub.uid);
				for (Map.Entry<Path, Path> entry : dirList.entrySet()) {
					dirJson.put(entry.getKey().toString(),entry.getValue().toString());
					System.out.println("DirAdding: Key: "+entry.getKey()+" | Value: "+entry.getValue());
				}
				
				for (Map.Entry<Path, Path> entry : fileList.entrySet()) {
					fileJson.put(entry.getKey().toString(),entry.getValue().toString());
					System.out.println("FileAdding: Key: "+entry.getKey()+" | Value: "+entry.getValue());
				}
				master.put("1",infoJson);
				master.put("2",dirJson);
				master.put("3",fileJson);
				String data = URLEncoder.encode("data", "UTF-8") + "=" + URLEncoder.encode(master.toString(), "UTF-8"); 
				
				System.out.println("Buiding done, submitting to website");
				String response = Globs.webTalk("mode=addGame",data);
				
    			if(response.equals("Sucess"))
    				System.out.println("Website Response: Sucess");
    			else {
    				System.err.println("Update failed. Website Response: "+response);
    				cancel(true);
    			}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
        // process copy task progress data in the event dispatch thread
        @Override
        public void process(List<CopyData> data) {
            if(isCancelled()) { return; }
            CopyData update  = new CopyData(null,null, 0);
            for (CopyData d : data) {
                // progress updates may be batched, so get the most recent
                if(d.type.equals("index")) {
            		currentTask(1);
            		setTaskStatus("Working...");
            		dirLocations.setText("<HTML><h4>Source Directory: "+srcDir.toString()+"<br>Target Directory: "+destDir.toString()+"</HTML>");
            		return;
            	}
            	else if(d.type.equals("Index Done")) {
            		currentTask(2);
            		return;
            	}
            	else if(d.type.equals("DB Update")) {
            		currentTask(3);
            		setBarProgress(100);
            		return;
            	}
                if (d.kiloBytesCopied > update.kiloBytesCopied) {
                    update = d;
                }
            }
            
            String progressNote =  "Source File:  " + srcDir.relativize(update.srcFilePath).toString()+
            						"<p>Destination File:  "+destDir.relativize(update.destFilePath).toString()+
              						"<p>" + update.kiloBytesCopied + " of " + getKiloBytes(totalBytes) + " kb copied.";
            
            //System.out.println(progressNote);  
            currentTask(2);
            setTaskStatus(progressNote);
            setBarProgress(progress);
        }
        
        // perform final updates in the event dispatch thread
        @Override
        public void done() {
            try {
                Void result = get();
                System.out.println("Copy operation completed.\n");
                setBarProgress(100); //sometimes process isn't called to finish up, so...
                JOptionPane.showMessageDialog(null,"Upload Complete! Press okay to continue");
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
        
        /*****This actually where the file get copied. Nested and initiated in the swingworker thread***/
		class CopyFiles extends SimpleFileVisitor<Path> {
			Path relativeDir, relativeFile, relativeDest, realDestDir, realDestFile;
			long bytesCopied = 0;
			
			@Override
			public FileVisitResult visitFile(Path file,BasicFileAttributes attrs) {
			    relativeFile = srcDir.relativize(file); //Obtain relative path from revitalizing gamePath and current file
			    
			    //Get obscurified path
			    relativeDest = obscurePath(); //simply drop into root of dest
			    realDestFile = destDir.resolve(relativeDest);
			    fileList.put(relativeFile,relativeDest); //add to file list
			    
			    realDestFile = destDir.resolve(relativeDest); //Obtain absolute destination file by combining relativeFile and obscParent
				try {
					FileChannel in = new FileInputStream(file.toString()).getChannel();
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
	                    CopyData current = new CopyData(file,realDestFile,getKiloBytes(bytesCopied));
	                    try {
	                       	setProgress(progress);
	                    }
	                    catch(Exception e) {
	                       	System.out.println("------------------ERROR-----------------");
				            System.out.println("Progress: " +progress+ " | Bytes Copied: "+bytesCopied+" | Bytes Total: "+totalBytes+" | Bytes Divided: "+(int)((100*(float)bytesCopied / totalBytes)));
				            e.printStackTrace();
				            System.out.println("--------------END ERROR-----------------");
	                    	
	                    	//kill to prevent runaway erros
	                    	cancel(true);
	                    }
	                              
	                    // publish current progress data for copy task
	                    publish(current);
			    	}
			        out.close();
			        in.close();
				}
				catch (FileNotFoundException e) {
	               	System.out.println("ERROR IN COPYING FILE: "+e.getMessage());
	               	e.printStackTrace();
	            } 
	            catch (IOException e) {
	            	e.printStackTrace();
	            }
	            return FileVisitResult.CONTINUE;
	       	}
		}
    }
    
    /*****This is the container class for the current file progress****/
    class CopyData {
        public Path srcFilePath, destFilePath;
        public long kiloBytesCopied;
        public String type;
        
        CopyData(String type) {
        	this.type = type;        		
        }
        
        CopyData(Path srcFilePath, Path destFilePath, long kiloBytesCopied) {
        	this.destFilePath = destFilePath;
            this.srcFilePath = srcFilePath;
            this.kiloBytesCopied = kiloBytesCopied;
            this.type = "";
        }
    }
}