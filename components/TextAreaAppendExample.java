package com.zcage.log;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.ByteArrayInputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import javax.swing.JScrollPane;

public class TextAreaAppendExample {

	private Logger logger = Logger.getLogger(this.getClass().getName());  //  @jve:decl-index=0:
	private java.util.logging.Logger jdkLogger = java.util.logging.Logger.getLogger(this.getClass().getName());  //  @jve:decl-index=0:
	 	
	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="7,7"
	private JPanel jContentPane = null;
	private JTextArea textArea = null;
	private JButton log4JButton = null;
	private JTextField logTextJTextField = null;

	private JLabel jLabel = null;
	private JScrollPane jScrollPane = null;
	private JButton LogJdkButton = null;
	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setSize(800, 450);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("TextArea Appender Example - Log4J and JDK1.4 Logging");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints.gridy = 2;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = GridBagConstraints.BOTH;
			gridBagConstraints31.gridy = 1;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.weighty = 1.0;
			gridBagConstraints31.gridwidth = 3;
			gridBagConstraints31.insets = new Insets(8, 8, 8, 8);
			gridBagConstraints31.gridx = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridwidth = 3;
			gridBagConstraints2.insets = new Insets(8, 8, 0, 0);
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			jLabel = new JLabel();
			jLabel.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel.setText("<html>The <font color=blue><b>JTextArea</b></font> below is connected to the Log4J logging via use of the TextAreaAppender. And to the JDK1.4 logging by the TextAreaHandler. Of course, both of these mechanism have a boat load of configuration that can be done. Log4J definitely seems more refined and easier to work with. See code for some details.<br><br><b><u>Meat of the code</u></b><br><code><font background-color=yellow>&nbsp;&nbsp;&nbsp;&nbsp;...<br>&nbsp;&nbsp;&nbsp;&nbsp;TextAreaAppender.setTextArea(getMyTextArea()); // Log4J<br>&nbsp;&nbsp;&nbsp;&nbsp;TextAreaHandler.setTextArea(getMyTextArea());&nbsp;&nbsp;// JDK1.4<br>&nbsp;&nbsp;&nbsp;&nbsp;...</font></code></html>");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(4, 4, 4, 8);
			gridBagConstraints3.gridx = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(4, 8, 4, 4);
			gridBagConstraints1.gridy = 2;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getLog4JButton(), gridBagConstraints1);
			jContentPane.add(getLogTextJTextField(), gridBagConstraints3);
			jContentPane.add(jLabel, gridBagConstraints2);
			jContentPane.add(getJScrollPane(), gridBagConstraints31);
			jContentPane.add(getLogJdkButton(), gridBagConstraints);
		}
		return jContentPane;
	}

	/**
	 * This method initializes textArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getLogJTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
			textArea.setText("");
			textArea.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
		return textArea;
	}

	/**
	 * This method initializes log4JButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLog4JButton() {
		if (log4JButton == null) {
			log4JButton = new JButton();
			log4JButton.setText("Log4J");
			log4JButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logger.info("(Log4J) " + getLogTextJTextField().getText());
				}
			});
		}
		return log4JButton;
	}

	/**
	 * This method initializes logTextJTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLogTextJTextField() {
		if (logTextJTextField == null) {
			logTextJTextField = new JTextField();
			logTextJTextField.setText("The Earth is warming and this will cause severe trouble...(Sample log message)");
		}
		return logTextJTextField;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getLogJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes LogJdkButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLogJdkButton() {
		if (LogJdkButton == null) {
			LogJdkButton = new JButton();
			LogJdkButton.setText("Log JDK");
			LogJdkButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jdkLogger.info("(Jdk14) " + getLogTextJTextField().getText());
				}
			});
		}
		return LogJdkButton;
	}

	/**
	 * Launches this application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TextAreaAppendExample textAreaAppendExample = new TextAreaAppendExample();
				textAreaAppendExample.getJFrame().setLocation(100,100);
				textAreaAppendExample.getJFrame().setVisible(true);
				
				setupLog4JAppender(textAreaAppendExample.getLogJTextArea());
				textAreaAppendExample.logger.info("Test message for log4j");
				
				setupJdkLoggerHandler(textAreaAppendExample.getLogJTextArea());
				textAreaAppendExample.jdkLogger.info("Test message for jdkLogger");
			}
		});
	}

	protected static void setupLog4JAppender(JTextArea jTextArea) {
		// This code attaches the appender to the text area
		TextAreaAppender.setTextArea(jTextArea);
		
		// Normally configuration would be done via a log4j.properties
		// file found on the class path, but here we will explicitly set
		// values to keep it simple.
		//
		// Great introduction to Log4J at http://logging.apache.org/log4j/docs/manual.html
		//
		// Could also have used straight code like: app.logger.setLevel(Level.INFO);
		Properties logProperties = new Properties();
		logProperties.put("log4j.rootLogger", "INFO, CONSOLE, TEXTAREA");
		logProperties.put("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender"); // A standard console appender
		logProperties.put("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout"); //See: http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		logProperties.put("log4j.appender.CONSOLE.layout.ConversionPattern", "%d{HH:mm:ss} [%12.12t] %5.5p %40.40c: %m%n");

		logProperties.put("log4j.appender.TEXTAREA", "com.zcage.log.TextAreaAppender");  // Our custom appender
		logProperties.put("log4j.appender.TEXTAREA.layout", "org.apache.log4j.PatternLayout"); //See: http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		logProperties.put("log4j.appender.TEXTAREA.layout.ConversionPattern", "%d{HH:mm:ss} %5.5p %40.40c: %m%n");
		
		PropertyConfigurator.configure(logProperties);
	}

	protected static void setupJdkLoggerHandler(JTextArea logJTextArea) {
		// This code attaches the handler to the text area
		TextAreaHandler.setTextArea(logJTextArea);
		
		// Normally configuration would be done via a properties file
		// that would be read in with LogManager.getLogManager().readConfiguration()
		// But I create an inputstream here to keep it local.
		// See JAVA_HOME/jre/lib/logging.properties for more description of these settings.
		//
		StringBuffer buf = new StringBuffer();
		buf.append("handlers = com.zcage.log.TextAreaHandler, java.util.logging.ConsoleHandler"); // A default handler and our custom handler
		buf.append("\n");
		buf.append(".level = INFO"); // Set the default logging level see: C:\software\sun\jdk141_05\docs\api\index.html
		buf.append("\n");
		buf.append("com.zcage.log.TextAreaHandler.level = INFO"); // Custom Handler logging level
		buf.append("\n");
		buf.append("java.util.logging.ConsoleHandler.level = INFO"); // Custom Handler logging level
		buf.append("\n");
//		buf.append("java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter"); //
//		buf.append("\n");
//		buf.append("com.zcage.log.TextAreaHandler.formatter = java.util.logging.SimpleFormatter"); //
//		buf.append("\n");
//		buf.append("java.awt.KeyboardFocusManager.level = INFO");  // Set the logging level for this logger  
		
		try {
			java.util.logging.LogManager.getLogManager().readConfiguration(
					new ByteArrayInputStream(buf.toString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
