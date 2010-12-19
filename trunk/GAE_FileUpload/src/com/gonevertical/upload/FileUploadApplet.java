package com.gonevertical.upload;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

public class FileUploadApplet extends JApplet {

  //private String url = "http://demogaemultifileblobupload.appspot.com";
  private String url = "http://127.0.0.1:8888";
  //private String url = "http://ourvirtualtours.appspot.com"; //http://ourvirtualtours.appspot.com/

  private ArrayList<File> files;

  private JFileChooser jfc;

  private JTextField tbBase;

  private File dir;

  private JLabel lbUrl;

  // like username and password oauth style
  public String accessToken = "accessTokenTest";
  public String accessSecret = "accessSecretTest";

  public FileUploadApplet() {
    getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

    JPanel panel = new JPanel();
    panel.setSize(500, 500);
    getContentPane().add(panel, BorderLayout.CENTER);

    JButton btnChooseDirectory = new JButton("Choose Directory");
    btnChooseDirectory.setBounds(18, 129, 153, 29);
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
    panel.setLayout(null);
    panel.add(btnChooseDirectory);

    tbBase = new JTextField();
    tbBase.setText("/serve");
    tbBase.setBounds(194, 73, 422, 28);
    panel.add(tbBase);
    tbBase.setColumns(10);

    JLabel lblSetGaeVirtual = new JLabel("Remote Virtual Directory");
    lblSetGaeVirtual.setBounds(18, 79, 176, 16);
    panel.add(lblSetGaeVirtual);

    JLabel lblUploadUrl = new JLabel("Upload Url");
    lblUploadUrl.setBounds(18, 31, 176, 16);
    panel.add(lblUploadUrl);

    lbUrl = new JLabel("url");
    lbUrl.setBounds(194, 31, 422, 16);
    panel.add(lbUrl);

    setup();
  }

  private void setup() {
    getLbUrl().setText(url);
  }

  public void setUrl(String url) {
    this.url = url;
    if (url == null) {
      url = "http://127.0.0.1:8888";
    }
    getLbUrl().setText(url);
  }

  public void setAccess(String accessToken, String accessSecret) {
    this.accessToken = accessToken;
    this.accessSecret = accessSecret;
  }

  /**
   * TODO ?
   */
  public void init() {

    try {
      SwingUtilities.invokeAndWait(new Runnable() {
        public void run() {


        }
      });
    } catch (Exception e) {
      System.err.println("createGUI didn't complete successfully");
    }

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
    
    upload(bloburl, file);

  }

  private void click() {
    if (jfc == null) {
      jfc = new JFileChooser();
      jfc.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

          String acction = e.getActionCommand();
          if (acction.equals("ApproveSelection") == false) {
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
    }
  }

  public void destroy() {
    try {
      this.finalize();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  private void upload(String bloburl, File file) {
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
    }

    try {
      entity.addPart("FilePath", new StringBody(file.getAbsolutePath(), Charset.forName("UTF-8")));
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }

    try {
      entity.addPart("DirectorySelected", new StringBody(getDirectorySelected(), Charset.forName("UTF-8")));
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }

    try {
      entity.addPart("VirtualPath", new StringBody(getVirtualPath(), Charset.forName("UTF-8")));
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }

    if (accessToken != null) {
      try {
        entity.addPart("AccessToken", new StringBody(getAccessToken(), Charset.forName("UTF-8")));
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
      }
    }

    if (accessSecret != null) {
      try {
        entity.addPart("AccessSecret", new StringBody(getAccessSecret(), Charset.forName("UTF-8")));
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
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
    } catch (IOException e) {
      e.printStackTrace();
    }
    HttpEntity resEntity = response.getEntity();

    System.out.println(response.getStatusLine());
    if (resEntity != null) {
      try {
        System.out.println(EntityUtils.toString(resEntity));
      } catch (ParseException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    client.getConnectionManager().shutdown();
  }

  private String getAccessToken() {
    return accessToken;
  }

  private String getAccessSecret() {
    return accessSecret;
  }

  private String getBlobUrl() {

    MultipartEntity entity = new MultipartEntity();

    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
    nameValuePairs.add(new BasicNameValuePair("AccessToken", getAccessToken()));  
    nameValuePairs.add(new BasicNameValuePair("AccessSecret", getAccessSecret()));  
        
    String url = this.url + "/blob";

    HttpClient client = new DefaultHttpClient();
    client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
    HttpPost post = new HttpPost(url);
    try {
      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }  

    System.out.println("executing request " + post.getRequestLine());

    HttpResponse response = null;
    try {
      response = client.execute(post);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    HttpEntity resEntity = response.getEntity();

    String s = "";
    if (resEntity != null) {
      try {
        //System.out.println(EntityUtils.toString(resEntity));
        s = EntityUtils.toString(resEntity);
      } catch (ParseException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    client.getConnectionManager().shutdown();

    if (s != null) {
      s = s.replaceAll("\n", "");
    }
    
    return s;
  }

  private String getVirtualPath() {
    return tbBase.getText();
  }

  private String getDirectorySelected() {
    return dir.getAbsolutePath();
  }
  public JLabel getLbUrl() {
    return lbUrl;
  }
}
