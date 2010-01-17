import java.awt.*;
 import java.awt.event.*;
 import java.io.*;

 public class RedirectedFrame extends Frame {
    TextArea aTextArea = new TextArea();
    PrintStream aPrintStream  = 
       new PrintStream(
         new FilteredStream(
           new ByteArrayOutputStream()));

    boolean logFile;

    RedirectedFrame(boolean logFile) {
       this.logFile = logFile;
       System.setOut(aPrintStream);
       System.setErr(aPrintStream);
       setTitle("Error message");
       setSize(500,300);
       setLayout(new BorderLayout());
       add("Center" , aTextArea);
       displayLog();
       addWindowListener
          (new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                dispose();
                }
             }
          );
       }

    class FilteredStream extends FilterOutputStream {
       public FilteredStream(OutputStream aStream) {
          super(aStream);
          }

       public void write(byte b[]) throws IOException {
          String aString = new String(b);
          aTextArea.append(aString);
          }

       public void write(byte b[], int off, int len) throws IOException {
          String aString = new String(b , off , len);
          aTextArea.append(aString);
          if (logFile) {
             FileWriter aWriter = new FileWriter("error.log", true);
             aWriter.write(aString);
             aWriter.close();
             }
          }
       }

    public void displayLog() {
       Dimension dim = getToolkit().getScreenSize();
       Rectangle abounds = getBounds();
       Dimension dd = getSize();
       setLocation((dim.width - abounds.width) / 2,
          (dim.height - abounds.height) / 2);
       setVisible(true);
       requestFocus();
       }

    public static void main(String s[]){
       try {
          // force an exception for demonstration purpose
          Class.forName("unknown").newInstance();
          }
       catch (Exception e) { 
          // for applet, always RedirectedFrame(false)
          RedirectedFrame r = new RedirectedFrame(true);
          e.printStackTrace();
          }
       }
    }
