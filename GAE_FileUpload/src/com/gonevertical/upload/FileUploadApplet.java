package com.gonevertical.upload;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

public class FileUploadApplet extends JApplet {

  private JSObject jsWin;
  
  private ArrayList<String> files;

  private JFileChooser jfc;
  
  public FileUploadApplet() {
  }
  
  public void init() {

    try {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
              
              JPanel panel = new JPanel();
              getContentPane().add(panel, BorderLayout.CENTER);
              
              JButton btnChooseDirectory = new JButton("Choose Directory");
              btnChooseDirectory.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                  click();
                }
              });
              btnChooseDirectory.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                  click();
                }
              });
              panel.add(btnChooseDirectory);
                  
              setup();
            }
        });
    } catch (Exception e) {
        System.err.println("createGUI didn't complete successfully");
    }
    
  }
  
  public void test() {
    JOptionPane.showMessageDialog(null, "I am an alert box!");
  }
  
  private void testJs() {
    JSObject window = JSObject.getWindow(this);
    window.call("fromJava", null);
  }
  
  private void setup() {
    try {
      jsWin = JSObject.getWindow(this);
    } catch (JSException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    reset();
  }
  
  private void reset() {
    files = new ArrayList<String>();
  }
  
  public void process(String f) {
    
    if (f == null || f.trim().length() == 0) {
      return;
    }
    
    File file = new File(f);
    
    if (file.isDirectory() == false) {
      addFile(file);
      finish();
      return;
    }
    
    loop(file);
    
    finish();
  }
  
  private void loop(File dir) {
    
    if (dir == null) {
      return;
    }
    
    if (dir.isDirectory() == false) {
      return;
    }
    
    File[] files = dir.listFiles();
    
    for (int i=0; i < files.length; i++) {
    
      if (files[i].isDirectory() == true) {
        loop(files[i]);
        
      } else {
        addFile(files[i]);
      }
      
    }
    
  }

  private void addFile(File file) {
    String p = file.getAbsolutePath();
    files.add(p);
  }

  private void finish() {
    String[] ss = new String[files.size()];
    System.out.println("length: " + ss.length);
    files.toArray(ss);
    jsWin.call("setSelectedFiles", new Object[] {ss});
  }

  private void click() {
    if (jfc == null) {
      jfc = new JFileChooser();
      jfc.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          File f = jfc.getSelectedFile();
          
          if (f == null) {
            return;
          }
          process(f.getAbsolutePath());
        }
      });
    }
    
    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    jfc.setVisible(true);
    jfc.showDialog(this, "Select Directory");
    
  }
}
