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

class CopyGame extends JPanel implements ActionListener {
	JLabel indexPLabel, copyPLabel, dbPLabel; //all process labels
	JLabel curTaskLabel, taskStatusLabel; //progress bar label
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
		processList.add(createProcess(indexPLabel,"Indexing Directories"));
		processList.add(createProcess(copyPLabel,"Copying and Renaming Files (Will take a long time)"));
		processList.add(createProcess(dbPLabel,"Submit to database"));
		add(processList);
		
		/***Setup Progress Bar***/
		JPanel progressPanel = new JPanel();
		progressPanel.setLayout(new BoxLayout(progressPanel,BoxLayout.Y_AXIS));
		progressPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(0,40,0,40), 
			BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		progressPanel.add(curTaskLabel = new JLabel());
		curTaskLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		setCurTask("");
		progressPanel.add(taskStatusLabel = new JLabel());
		taskStatusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		setTaskStatus("");
		progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
		progressPanel.add(progressBar);
		add(progressPanel);
		
		return this;
	}
	
	private JLabel createProcess(JLabel label,String text) {
		label = new JLabel();
		label.setText("<html><h3>"+text+"</h3></html>");
		label.setIcon(new ImageIcon(new ImageIcon("red_arrow.png").getImage().getScaledInstance(33, 25,  java.awt.Image.SCALE_SMOOTH)));
		return label;
	}
	
	private void setCurTask(String proc) {
		curTaskLabel.setText("<html><h4>Current Task: "+proc+"</h4></html>");
	}
	
	private void setTaskStatus(String status) {
		taskStatusLabel.setText("<html><h4>Task Status: "+status+"</h4></html>");
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
		
		startCopy();
	}
	
	private void startCopy() {
		System.out.println("Starting copy");
		
		//Setup and run worker thread
		operation = new CopyThread(gamePath, Paths.get(Long.toString(Math.abs(new Random().nextLong()), 36)));
        //operation.addPropertyChangeListener(this);
        operation.execute();
	}
	
	// executes in event dispatch thread
	/*
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
	
	
	/******
	 * Needed Classes For Copying
	 * This Class simply initializes CopyFile class, but does so in the background thread
	 *****/
    class CopyThread extends SwingWorker<Void, String> {        
        private static final int PROGRESS_CHECKPOINT = 100000;
        private Path srcDir, destDir;
        long totalBytes;
        
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
            traverse(new File(srcDir.toUri()));
            
            //initialize FileCopy
            System.out.println("Initiating walk file tree on path" +srcDir.toString());
            Files.walkFileTree(srcDir, new CopyFiles());
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
				totalBytes=totalBytes + (int)f.length();
			}
		}

		
        // process copy task progress data in the event dispatch thread
        @Override
        public void process(List<String> data) {
            if(isCancelled()) { return; }
            
            //Search list to see if currently indexing
            for (String d : data) {
                if(d.equals("index")) {
            		setCurTask("Indexing Files");
            		setTaskStatus("Working...");
            		return;
            	}
            	
            }
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
        
        /*****This actually where the file get copied. Nested and initiated in the swingworker thread***/
		class CopyFiles extends SimpleFileVisitor<Path> {
			Path relativeDir, relativeFile, realDestDir, realDestFile;
			
			@Override
			public FileVisitResult visitFile(Path file,BasicFileAttributes attrs) {
			    //Revitalize to get relative path
			    relativeFile = gamePath.relativize(file);
			    System.out.println("Relative File: " + relativeDir);
			    
			    //Get absolute destination path
			    realDestFile = CopyThread.this.destDir.resolve(relativeFile);
			    System.out.println("Real Destination: " + realDestFile);
			    
			    return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult preVisitDirectory(Path dir) {
			    //Need to revitalize to get relative path
			    relativeDir = gamePath.relativize(dir);
			    System.out.println("Relative Dir: " + relativeDir);
			        
			    //Get destination absolute path
			    realDestDir = CopyThread.this.destDir.resolve(relativeDir);
			    System.out.println("Real Destination: " + realDestDir);
			        
			    //Now create remote directory
			    if(!realDestDir.exists()) {
				    try { realDestDir.createDirectory(); }
				    catch(Exception e) { e.printStackTrace(); }
			    }

			    return FileVisitResult.CONTINUE;
			}
		}
    }
}


/*********CODE ARCHIVES
 *
 *The following was removed because it uses the outdated byte read and write method
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