import javax.swing.*;
import java.awt.Component;
import java.awt.event.*;
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

class CopyGame extends JPanel implements ActionListener {
	JLabel indexPLabel, copyPLabel, dbPLabel; //all process labels
	JLabel taskStatusLabel,dirLocations; //progress bar label
	String picPath, gameName;
	Path gamePath;
	Date gameCreate;
	public JProgressBar progressBar;
	private CopyThread operation;
	
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
	public void config(String gameDir, String picPath, String gameName, Date gameDate) {
		Globs.switchBody("CopyGame");
		if(JOptionPane.showConfirmDialog(null, "Do you wish to start? \n NOTE: YOU CANNOT EXIT ONCE THE PROCESS IS STARTED!! \n MAKE SURE YOU HAVE ENOUGH TIME TO COPY THE GAME, ESPICALLY WITH LARGE ONE'S!!")!=JOptionPane.YES_OPTION) {
			Globs.switchBody("AddGame");
			return;
		}
		gamePath = Paths.get(gameDir);
		this.picPath = picPath;
		this.gameName = gameName;
		this.gameCreate = gameCreate;
		
		System.out.println("Starting copy");
		
		//Setup and run worker thread
		operation = new CopyThread(gamePath, obscurePath().toAbsolutePath());
        operation.execute();
	}
	
	public Path obscurePath() {
		return Paths.get(Long.toString(Math.abs(new Random().nextLong()), 36));
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
			    catch(Exception e) { e.printStackTrace(); }
            }
            
            //Calculate total data 
            System.out.println("Starting Directory Transverse");
            publish(new CopyData("index"));
            traverse(srcDir);
            publish(new CopyData("Index Done"));
            
            //initialize FileCopy
            System.out.println("Initiating walk file tree on path: " +srcDir.toString());
            Files.walkFileTree(srcDir, new CopyFiles());
            publish(new CopyData("Copy Done"));
            
            //Update to db
            //publish(new CopyData("FileCopy"))
            
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
				catch (Exception ex) { ex.printStackTrace(); }
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
            	else if(d.type.equals("Copy Done")) {
            		currentTask(3);
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
            } catch (InterruptedException e) {
                
            } catch (CancellationException e) {
                System.out.println("Copy operation canceled.\n");
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
			Path relativeDir, relativeFile, realDestDir, realDestFile;
			long bytesCopied = 0;
			TreeMap<Path,Path> dirList = new TreeMap<Path,Path>();
			TreeMap<Path,Path> fileList = new TreeMap<Path,Path>();
			
			@Override
			public FileVisitResult visitFile(Path file,BasicFileAttributes attrs) {
			    relativeFile = srcDir.relativize(file); //Obtain relative path from revitalizing gamePath and current file
			    
			    //Get obscurified path
			    int nameCount = (relativeFile.getNameCount()<=1) ? 1 :  relativeFile.getNameCount()-1; //prevent root files from calling parent
			    Path obscParent = dirList.get(relativeFile.subpath(0,nameCount));
			    System.out.println("Name Count: "+relativeFile.getNameCount()+" | Relative File: "+relativeFile+" | Parent: "+obscParent);
			    if(obscParent == null && nameCount != 1) {
			    	//thats not supposed to happen...
			    	System.err.println("Can't find parent folder! obscParent is null!");
			    	cancel(true);
			    }
			    Path relativeDest = null;
			    if(obscParent == null)
			    	relativeDest = destDir.resolve(obscurePath()); //for root files
			    else
			    	relativeDest = obscParent.resolve(obscurePath());
			    
			    //add to fileList for
			    fileList.put(relativeFile,relativeDest);
			    
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
			
			@Override
			public FileVisitResult preVisitDirectory(Path dir) {
			    relativeDir = srcDir.relativize(dir); //Obtain relative path from revitalizing gamePath and current directory
			    
			    //The inital directory is null, so...
			    if(relativeDir==null) {
			    	realDestDir = destDir.resolve(relativeDir);
			    	return FileVisitResult.CONTINUE;
			    }
			    	
			    //Generate destination directory
			    Path relativeDest = null;
			    int nameCount = (relativeDir.getNameCount()==1) ? 1 :  relativeDir.getNameCount()-1;
			    Path obscParent = dirList.get(relativeDir.subpath(0,nameCount));
			    System.out.println("Name Count: "+relativeDir.getNameCount()+" | Relative Dir: "+relativeDir+" | Parent: "+relativeDir.subpath(0,nameCount));
			    if(obscParent != null) { //found it
			    	relativeDest = obscParent.resolve(obscurePath());
			    	System.out.println("Relative Dir Dest: "+relativeDest);
			    	dirList.put(relativeDir,relativeDest);
			    	realDestDir = destDir.resolve(relativeDest); 
			    	dirList.put(relativeDir,relativeDest);
			    }
			    else { //didn't find it, must be in root
			    	Path obscPath = obscurePath();
			    	realDestDir = destDir.resolve(obscPath); 
			    	dirList.put(relativeDir,obscPath);
			    }
			    	
			    //realDestDir = destDir.resolve(relativeDest); //Obtain absolute destination directory by combining destDir and relativeDir
			    
			    //Now create remote directory
			    if(!realDestDir.exists()) {
				    try { realDestDir.createDirectory(); }
				    catch(Exception e) { e.printStackTrace(); }
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


/*********CODE ARCHIVES
 *
 *Removed due to being useless because process runs on the edt thread
 	// executes in event dispatch thread
    public void propertyChange(PropertyChangeEvent event) {
    	if (event.getPropertyName().equals("progress")) {            
        	// get the % complete from the progress event
            // and set it on the progress monitor
            System.out.println(progressBar != null);
            System.out.println(event.getNewValue() != null);
            if (event.getPropertyName().equals("progress"))
            	progressBar.setValue(((Integer)event.getNewValue()).intValue());         
        }
    }
 *
******************************************************************************
*The following was removed because it uses the outdated byte read and write method
******************************************************************************
                    File destFile = new File(destDir, f.getName());
                    long previousLen = 0;
                    
                    try {
                        InputStream in = new FileInputStream(f);
                        OutputStream out = new FileOutputStream(destFile);                    
                        byte[] buf = new byte[1024];
                        int counter = 0;
                        int len;
                        
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                            counter += len;
                            bytesCopied += (destFile.length() - previousLen);
                            previousLen = destFile.length();
                            if (counter > PROGRESS_CHECKPOINT || bytesCopied == totalBytes) {
                                // get % complete for the task
                                progress = (int)(100*(bytesCopied / totalBytes));
                                counter = 0;
                                CopyData current = new CopyData(progress, f.getName(),
                                                                getKiloBytes(totalBytes),
                                                                getKiloBytes(bytesCopied));

                                // set new value on bound property
                                // progress and fire property change event
                                setProgress(progress);
                                
                                // publish current progress data for copy task
                                publish(current);
                            }
                        }
                        in.close();
                        out.close();
                    }
                    catch (FileNotFoundException e) {
                    	System.out.println("ERROR IN COPYING FILE: "+e.getMessage());
                    	e.printStackTrace();
                    } 
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
******************************************************************************
* Folowing Code Removed due to not correctly trnsversing the file tree, as of copy transfering to filetreewalker
******************************************************************************
    class CopyFiles extends SwingWorker<Void, CopyData> {        
        private static final int PROGRESS_CHECKPOINT = 100000;
        private File srcDir;
        private File destDir;
        File[] files;
        int totalBytes = 0;
        
        CopyFiles(File src, File dest) {
            this.srcDir = src;
            this.destDir = dest;
        }
        
        // perform time-consuming copy task in the worker thread
        @Override
        public Void doInBackground() {
            int progress = 0;
            
            // initialize bound property progress (inherited from SwingWorker)
            setProgress(0);
            // get the files to be copied from the source directory
            System.out.println("Indexing Directory");
			publish(new CopyData("index"));
            traverse(srcDir);
            // determine the scope of the task
            long bytesCopied = 0;

            while (progress < 100 && !isCancelled()) {                 
                // copy the files to the destination directory
                for (File f : files) {
					try {
						File destFile = new File(destDir, f.getName());
						FileChannel in = new FileInputStream(f).getChannel();
	        			FileChannel out = new FileOutputStream(destFile).getChannel();
			           	long size = in.size();
			           	long position = 0;
			           	while (position < size) {
			           		position += in.transferTo(position, PROGRESS_CHECKPOINT, out);
			           		
			           		//transfer of progress data complete, calculate info
			           		bytesCopied += destFile.length();
			           		progress = (int)(100*(getKiloBytes(bytesCopied) / getKiloBytes(totalBytes)));
                            CopyData current = new CopyData(progress, f.getName(),getKiloBytes(totalBytes),getKiloBytes(bytesCopied));

                            // set new value on bound property
                            // progress and fire property change event
                            try {
                            	setProgress(progress);
                            }
                            catch(Exception e) {
                            	System.out.println("------------------ERROR-----------------");
					            System.out.println("Progress: " +progress+ " KB Copied: "+getKiloBytes(bytesCopied)+" KB Total: "+getKiloBytes(totalBytes)+" KB Divided: "+(getKiloBytes(totalBytes) / getKiloBytes(bytesCopied)));
					            e.printStackTrace();
					            System.out.println("--------------END ERROR-----------------");
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
	            }
            }
            return null;
        }
		
		// Custom method to recursivly obtain files
		public final void traverse( final File f ) {
			if (f.isDirectory()) {
				final File[] childs = f.listFiles();
				for( File child : childs ) {
					traverse(child);
				}
				return;
			}
			else {
				if(files == null)
					files = new File[0];
				else {
					List<File> temp = new ArrayList<File>(files.length + 1);
					temp.add(f);
					temp.addAll(Arrays.asList(files));
					files = (File[]) temp.toArray(new File[temp.size()]);
				}
				totalBytes=totalBytes + (int)f.length();
			}
		}

		
        // process copy task progress data in the event dispatch thread
        @Override
        public void process(List<CopyData> data) {
            if(isCancelled()) { return; }
            CopyData update  = new CopyData(0, "", 0, 0);
            for (CopyData d : data) {
                // progress updates may be batched, so get the most recent
                if(d.type.equals("index")) {
            		setCurTask("Indexing Files");
            		setTaskStatus("Working...");
            		return;
            	}
                if (d.getKiloBytesCopied() > update.getKiloBytesCopied()) {
                    update = d;
                }
            }
            
            // update the progress monitor's status note with the
            // latest progress data from the copy operation, and
            // additionally append the note to the console
            String progressNote =  "Progress: "+update.getProgress()+"; Now copying " + update.getFileName() + "<br>" + update.getKiloBytesCopied() + " of " + totalBytes + " kb copied.";
            
            //System.out.println(progressNote);  
            setCurTask("Copying Files");
            setTaskStatus(progressNote);
            setBarProgress(update.getProgress());
        }
        
        // perform final updates in the event dispatch thread
        @Override
        public void done() {
            try {
                // call get() to tell us whether the operation completed or 
                // was canceled; we don't do anything with this result
                Void result = get();
                System.out.println("Copy operation completed.\n");                
            } catch (InterruptedException e) {
                
            } catch (CancellationException e) {
                // get() throws CancellationException if background task was canceled
                System.out.println("Copy operation canceled.\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        private long getKiloBytes(long totalBytes) {
            return Math.round(totalBytes / 1024);
        }
    }
    
    /*****This is the container class for the current file progress****
    class CopyData {        
        private int progress;
        private String fileName;
        private long totalKiloBytes;
        private long kiloBytesCopied;
        public String type;
        
        CopyData(String type) {
        	if(type.equals("index"))
        		this.type = "index";
        }
        
        CopyData(int progress, String fileName, long totalKiloBytes, long kiloBytesCopied) {
            this.progress = progress;
            this.fileName = fileName;
            this.totalKiloBytes = totalKiloBytes;
            this.kiloBytesCopied = kiloBytesCopied;
            this.type = "";
        }

        int getProgress() {
            return progress;
        }
        
        String getFileName() {
            return fileName;
        }

        long getTotalKiloBytes() {
            return totalKiloBytes;
        }

        long getKiloBytesCopied() {
            return kiloBytesCopied;
        }
        
        String getType() {
        	return type;
        }
    }
**/