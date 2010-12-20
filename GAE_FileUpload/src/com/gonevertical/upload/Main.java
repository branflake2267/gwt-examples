package com.gonevertical.upload;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Main {

  private JFrame frame;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Main window = new Main();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public Main() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 705, 331);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    FileUpload fileUpload = new FileUpload();
    frame.getContentPane().add(fileUpload, BorderLayout.CENTER);
    
  }
}
