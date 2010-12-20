package org.gonevertical.MultiFileUpload.client;

import org.gonevertical.MultiFileUpload.client.blobs.Blobs;
import org.gonevertical.MultiFileUpload.client.rpc.RpcInit;
import org.gonevertical.MultiFileUpload.client.rpc.RpcServiceAsync;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DirUploader extends Composite {
  private RpcServiceAsync rpc;
  private VerticalPanel verticalPanel;
  private Blobs blobs;
  private Image image;
  private HTML htmlThisImageHis;
  private HTML html;
  private HTML htmlnbsp_1;
  private HTML htmlnbsp;

  public DirUploader() {

    verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    html = new HTML("&nbsp;", true);
    verticalPanel.add(html);
    
    htmlnbsp = new HTML("&nbsp;", true);
    verticalPanel.add(htmlnbsp);
    
    htmlThisImageHis = new HTML("This image his hosted virtually in Googles Big Table, which was uploaded via the Java Applet", true);
    verticalPanel.add(htmlThisImageHis);
    
    image = new Image("/serve/keystonedata/keystone1/preview.jpg");
    verticalPanel.add(image);
    
    htmlnbsp_1 = new HTML("&nbsp;", true);
    verticalPanel.add(htmlnbsp_1);
    
    blobs = new Blobs();
    verticalPanel.add(blobs);

    setup();
  }

  private void setup() {

    String applet = "<applet archive=\"" +
    		"/applet/choosedir/SSignedApplet.jar," +
    		"/applet/choosedir/lib/commons-logging-1.1.1.jar," +
    		"/applet/choosedir/lib/httpclient-4.1-beta1.jar," +
    		"/applet/choosedir/lib/httpcore-4.1.jar," +
    		"/applet/choosedir/lib/httpcore-nio-4.1.jar," +
    		"/applet/choosedir/lib/httpmime-4.1-beta1.jar\" " +
    		"" +
        "code=\"com.gonevertical.upload.FileUploadApplet\" " +
        "jnlp_href=\"/applet/choosedir/FileUpload.jnlp\" " +
        "width=\"700\" " +
        "height=\"300\" " +
        "id=\"applet\" MAYSCRIPT>" +
        "</applet>";
    
    html.setHTML(applet);
    
    rpc = RpcInit.init();
    
    blobs.getBlobsList();
    
    Timer t = new Timer() {
      public void run() {
        String accessToken = "AccessTokenFromDirUploader";
        String accessSecret = "AccessSecretFromDirUploader";
        setAccess(accessToken, accessSecret);
      }
    };
    t.schedule(2000);
  }

  /**
   * interact with the applet - send it oauth access credentials
   * 
   * @param accessToken
   * @param accessSecret
   */
  public static native void setAccess(String accessToken, String accessSecret) /*-{
     var app = $wnd.document.getElementById("applet");
     app.setAccess(accessToken, accessSecret);
  }-*/;


  private void getBlobUrl(final String path) {
    rpc.getBlobStoreUrl(new AsyncCallback<String>() {
      public void onSuccess(String bloburl) {
        //process(path, bloburl);
      }
      public void onFailure(Throwable caught) {
      }
    });
  }

  public VerticalPanel getVerticalPanel() {
    return verticalPanel;
  }

}
