package com.gonevertical.upload;

import javax.swing.JApplet;
import java.awt.BorderLayout;

public class FileUploadApplet extends JApplet {
  private FileUpload fileUpload;

  /**
   * Create the applet.
   */
  public FileUploadApplet() {
    
    fileUpload = new FileUpload();
    getContentPane().add(fileUpload, BorderLayout.CENTER);

  }

  public FileUpload getFileUpload() {
    return fileUpload;
  }
  
  public void setAccess(String accessToken, String accessSecret) {
    fileUpload.setAccess(accessToken, accessSecret);
  }
  
  public void setUrl(String url) {
    fileUpload.setUrl(url);
  }
}
