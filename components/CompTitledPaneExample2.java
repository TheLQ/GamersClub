// Example from http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html

/* (swing1.1.1) */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * @version 1.0 08/12/99
 */
public class CompTitledPaneExample2 extends JFrame {
    public static void main(String[] args){ 
        try{ 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        } 
        final JPanel proxyPanel = new JPanel(); 
        proxyPanel.add(new JLabel("Proxy Host: ")); 
        proxyPanel.add(new JTextField("proxy.xyz.com")); 
        proxyPanel.add(new JLabel("  Proxy Port")); 
        proxyPanel.add(new JTextField("8080")); 
        final JCheckBox checkBox = new JCheckBox("Use Proxy", true); 
        checkBox.setFocusPainted(false); 
        ComponentTitledBorder componentBorder = 
                new ComponentTitledBorder(checkBox, proxyPanel 
                , BorderFactory.createEtchedBorder()); 
        checkBox.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e){ 
                boolean enable = checkBox.isSelected(); 
                Component comp[] = proxyPanel.getComponents(); 
                for(int i = 0; i<comp.length; i++){ 
                    comp[i].setEnabled(enable); 
                } 
            } 
        }); 
        proxyPanel.setBorder(componentBorder); 
        JFrame frame = new JFrame("ComponentTitledBorder - santhosh@in.fiorano.com"); 
        Container contents = frame.getContentPane(); 
        contents.setLayout(new FlowLayout()); 
        contents.add(proxyPanel); 
        frame.pack(); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setVisible(true); 
    } 

}