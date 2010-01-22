import javax.swing.*;
import javax.swing.event.*;
import java.util.Properties;
import javax.swing.JTree.*;

public class TreeRootHide{
  public static void main(String[] args) {
    JFrame frame = new JFrame("Root Hide of tree");
    Properties prop = System.getProperties();
    JTree tree = new JTree(prop);
    tree.setRootVisible(false);
    JScrollPane scroll = new JScrollPane(tree);
    frame.getContentPane().add(scroll, "Center");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.setSize(350,400);
    frame.setVisible(true);
  }
}