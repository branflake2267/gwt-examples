package com.gonevertical.upload;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

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
  
  private void setup() {
    try {
      jsWin = JSObject.getWindow(this);
    } catch (JSException e) {
      //e.printStackTrace();
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
    
    reset();
    
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
    if (p.matches("\\..*") == true) {
      return;
    }
    files.add(p);
  }

  private void finish() {
    String[] ss = new String[files.size()];
    files.toArray(ss);
    
    for (int i=0; i < ss.length; i++) {
      upload(ss[i]);
    }
    
  }

  private void upload(String path) {
    String bloburl = getBlobUrl();
    
    upload(bloburl, path);
    
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
  
  public void start() {
    
  }
  
  public void stop() {
    try {
      this.finalize();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
  
  public void destroy() {
    try {
      this.finalize();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
  
  private void upload(String bloburl, String path) {
    HttpClient httpclient = new DefaultHttpClient();
    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

    HttpPost httppost = new HttpPost("http://demogaemultifileblobupload.appspot.com/" + bloburl);
    File file = new File(path);

    FileEntity reqEntity = new FileEntity(file, "binary/octet-stream");

    httppost.setEntity(reqEntity);
    reqEntity.setContentType("binary/octet-stream");
    System.out.println("executing request " + httppost.getRequestLine());
    HttpResponse response = null;
    try {
      response = httpclient.execute(httppost);
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    HttpEntity resEntity = response.getEntity();

    System.out.println(response.getStatusLine());
    if (resEntity != null) {
      try {
        System.out.println(EntityUtils.toString(resEntity));
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    if (resEntity != null) {
      try {
        resEntity.consumeContent();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    httpclient.getConnectionManager().shutdown();
    
  }
  
  private String getBlobUrl() {
    
    String s = null;
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httpPost = new HttpPost("http://demogaemultifileblobupload.appspot.com/blob");
    try {
      HttpResponse response = httpclient.execute(httpPost);
      s = response.toString();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return s;
  }
  
  
}
