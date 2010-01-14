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

class CopyGame extends JPanel implements ActionListener {
	JLabel indexPLabel, copyPLabel, dbPLabel; //all process labels
	JLabel curTaskLabel, taskStatusLabel; //progress bar label
	String gamePath,picPath;
	String gameName;
	Date gameCreate;
	JProgressBar progressBar;
	private CopyFiles operation;
	
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
		JProgressBar progressBar = new JProgressBar(0, 100);
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
		gamePath = gameDir;
		this.picPath = picPath;
		this.gameName = gameName;
		this.gameCreate = gameCreate;
		
		startCopy();
	}
	
	private void startCopy() {
		System.out.println("Starting copy");
		
		File srcDir = new File(gamePath);
		File destDir = new File(Long.toString(Math.abs(new Random().nextLong()), 36));
		
		//Setup and run worker thread
		operation = new CopyFiles(srcDir, destDir);
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
	 * Most of code thanks to http://blogs.sun.com/CoreJavaTechTips/entry/making_progress_with_swing_s
	 *****/
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
			           		progress = (int)(100*(bytesCopied / totalBytes));
                            CopyData current = new CopyData(progress, f.getName(),getKiloBytes(totalBytes),getKiloBytes(bytesCopied));

                            // set new value on bound property
                            // progress and fire property change event
                            setProgress(progress);
                                
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
            
            setCurTask("Copying Files");
            setTaskStatus(progressNote);
            progressBar.setValue(update.getProgress());   
            
            System.out.println(progressNote);     
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
}


/*********CODE ARCHIVES
 *
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
                }*/