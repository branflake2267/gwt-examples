package com.gonevertical.upload;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

public class FileUpload extends JPanel {

  //private String url = "http://demogaemultifileblobupload.appspot.com";
  private String url = "http://127.0.0.1:8888";

  private ArrayList<File> files;

  private JFileChooser jfc;

  private JTextField tbBase;

  private File dir;

  // like username and password oauth style
  public String accessToken = "accessTokenTest";
  public String accessSecret = "accessSecretTest";
  private JButton bChooseDir; 
  private JTextField tbUrl;
  private JTextField tbUsername;
  private JTextField tbPassword;

  public FileUpload() {
    setLayout(null);
    
    JPanel panel = new JPanel();
    panel.setBounds(0, 0, 705, 325);
    add(panel);

    bChooseDir = new JButton("Choose Directory");
    bChooseDir.setBounds(18, 129, 153, 29);
    bChooseDir.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        click();
      }
    });
    bChooseDir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        click();
      }
    });
    panel.setLayout(null);
    panel.add(bChooseDir);

    tbBase = new JTextField();
    tbBase.setBounds(194, 73, 422, 28);
    tbBase.setText("/serve");
    panel.add(tbBase);
    tbBase.setColumns(10);

    JLabel lblSetGaeVirtual = new JLabel("Remote Virtual Directory");
    lblSetGaeVirtual.setBounds(18, 79, 176, 16);
    panel.add(lblSetGaeVirtual);

    JLabel lblUploadUrl = new JLabel("Upload URL");
    lblUploadUrl.setBounds(18, 31, 176, 16);
    panel.add(lblUploadUrl);
    
    JLabel lblV = new JLabel("v3");
    lblV.setBounds(18, 257, 61, 16);
    panel.add(lblV);
    
    tbUrl = new JTextField();
    tbUrl.setBounds(194, 25, 419, 28);
    panel.add(tbUrl);
    tbUrl.setColumns(10);
    
    JLabel lblUsername = new JLabel("Username");
    lblUsername.setBounds(18, 189, 176, 16);
    panel.add(lblUsername);
    
    JLabel lblPassword = new JLabel("Password");
    lblPassword.setBounds(18, 229, 176, 16);
    panel.add(lblPassword);
    
    tbUsername = new JTextField();
    tbUsername.setBounds(194, 183, 419, 28);
    panel.add(tbUsername);
    tbUsername.setColumns(10);
    
    tbPassword = new JTextField();
    tbPassword.setBounds(194, 217, 422, 28);
    panel.add(tbPassword);
    tbPassword.setColumns(10);

    setup();
  }

  private void setup() {
    
    //getBChooseDir().setEnabled(false);
    
    setUrl(url);
  }

  public void setUrl(String url) {
    this.url = url;
    if (url == null) {
      url = "http://127.0.0.1:8888";
    }
    getTbUrl().setText(url);
  }

  public void setAccess(String accessToken, String accessSecret) {
    this.accessToken = accessToken;
    this.accessSecret = accessSecret;
    
    getTbUsername().setText(accessToken);
    getTbPassword().setText(accessSecret);
    
    // set choosedirectory enabled
    getBChooseDir().setEnabled(true);
  }

  private void reset() {
    files = new ArrayList<File>();
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
    if (file.getName().matches("\\..*") == true) {
      return;
    }
    files.add(file);
  }

  private void finish() {
    for (int i=0; i < files.size(); i++) {
      upload(files.get(i));
    }

  }

  private void upload(File file) {
    String bloburl = getBlobUrl();

    if (bloburl == null || bloburl.contains("_ah") == false) {
      return;
    }
    
    uploadWSec(bloburl, file);
  }

  private void uploadWSec(final String bloburl, final File file) {
    try {
      AccessController.doPrivileged(new PrivilegedAction() {
        public Object run() {
          upload(bloburl, file);
          return null;
        }
      });
    } catch (Exception e) {
      dialog("Priv Error: " + e.toString());
      e.printStackTrace();
    }
  }

  private void click() {
    if (jfc == null) {
      jfc = new JFileChooser();
      jfc.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {

          JFileChooser chooser = (JFileChooser) evt.getSource();
          if (JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand())) {
            return;
          }
          
          dir = jfc.getSelectedFile();
          
          if (dir == null) {
            return;
          }
          process(dir.getAbsolutePath());

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
      dialog(e.toString());
    }
  }

  public void destroy() {
    try {
      this.finalize();
    } catch (Throwable e) {
      dialog(e.toString());
      e.printStackTrace();
    }
  }

  private void upload(String bloburl, File file) {
    try {
      if (url.contains("127.") == true) {
        bloburl = url + bloburl;
      }
      
      MultipartEntity entity = new MultipartEntity();
      FileBody uploadFilePart = new FileBody(file);
      entity.addPart("File", uploadFilePart);

      try {
        entity.addPart("FileName", new StringBody(file.getName(), Charset.forName("UTF-8")));
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
        dialog(e1.toString());
      }

      try {
        entity.addPart("FilePath", new StringBody(file.getAbsolutePath(), Charset.forName("UTF-8")));
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
        dialog(e1.toString());
      }

      try {
        entity.addPart("DirectorySelected", new StringBody(getDirectorySelected(), Charset.forName("UTF-8")));
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
        dialog(e1.toString());
      }

      try {
        entity.addPart("VirtualPath", new StringBody(getVirtualPath(), Charset.forName("UTF-8")));
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
        dialog(e1.toString());
      }

      if (accessToken != null) {
        try {
          entity.addPart("AccessToken", new StringBody(getAccessToken(), Charset.forName("UTF-8")));
        } catch (UnsupportedEncodingException e1) {
          e1.printStackTrace();
          dialog(e1.toString());
        }
      }

      if (accessSecret != null) {
        try {
          entity.addPart("AccessSecret", new StringBody(getAccessSecret(), Charset.forName("UTF-8")));
        } catch (UnsupportedEncodingException e1) {
          e1.printStackTrace();
          dialog(e1.toString());
        }
      }
      
      HttpClient client = new DefaultHttpClient();
      client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
      HttpPost post = new HttpPost(bloburl);
      post.setEntity(entity);

      System.out.println("executing request " + post.getRequestLine());

      HttpResponse response = null;
      try {
        response = client.execute(post);
      } catch (ClientProtocolException e) {
        e.printStackTrace();
        dialog(e.toString());
      } catch (IOException e) {
        e.printStackTrace();
        dialog(e.toString());
      }
      HttpEntity resEntity = response.getEntity();

      System.out.println(response.getStatusLine());
      if (resEntity != null) {
        try {
          System.out.println(EntityUtils.toString(resEntity));
        } catch (ParseException e) {
          e.printStackTrace();
          dialog(e.toString());
        } catch (IOException e) {
          e.printStackTrace();
          dialog(e.toString());
        }
      }

      client.getConnectionManager().shutdown();
    } catch (Exception e) {
      dialog("upload() Error: " + e.toString());
      e.printStackTrace();
    }
  }

  private String getAccessToken() {
    return getTbUsername().getText().trim();
  }

  private String getAccessSecret() {
    return getTbPassword().getText().trim();
  }

  private String getBlobUrl() {

    String s = "";
    try {
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
      nameValuePairs.add(new BasicNameValuePair("AccessToken", getAccessToken()));  
      nameValuePairs.add(new BasicNameValuePair("AccessSecret", getAccessSecret()));  
          
      String url = getUrl() + "/blob";

      HttpClient client = new DefaultHttpClient();
      client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
      HttpPost post = new HttpPost(url);
      try {
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
        dialog(e1.toString());
      }  
      
      System.out.println("executing request " + post.getRequestLine());

      HttpResponse response = null;
      try {
        response = client.execute(post);
      } catch (ClientProtocolException e) {
        e.printStackTrace();
        dialog(e.toString());
      } catch (IOException e) {
        e.printStackTrace();
        dialog(e.toString());
      }
      HttpEntity resEntity = response.getEntity();
      
      s = "";
      if (resEntity != null) {
        try {
          //System.out.println(EntityUtils.toString(resEntity));
          s = EntityUtils.toString(resEntity);
        } catch (ParseException e) {
          e.printStackTrace();
          dialog(e.toString());
        } catch (IOException e) {
          e.printStackTrace();
          dialog(e.toString());
        }
      }

      client.getConnectionManager().shutdown();

      if (s != null) {
        s = s.replaceAll("\n", "");
      }
      
    } catch (Exception e) {
      dialog("getBlobUrl() Error: " + e.toString());
      e.printStackTrace();
    }
    
    return s;
  }
  
  public void dialog(String s) {
    JOptionPane.showMessageDialog(null, "debug: " + s);
  }
  
  private String getUrl() {
    return tbUrl.getText().trim();
  }

  private String getVirtualPath() {
    return tbBase.getText();
  }

  private String getDirectorySelected() {
    return dir.getAbsolutePath();
  }

  public JButton getBChooseDir() {
    return bChooseDir;
  }
  public JTextField getTbUrl() {
    return tbUrl;
  }
  public JTextField getTbUsername() {
    return tbUsername;
  }
  public JTextField getTbPassword() {
    return tbPassword;
  }
}
