package org.gonevertical.MultiFileUpload.client;

import org.gonevertical.MultiFileUpload.client.blobs.Blobs;
import org.gonevertical.MultiFileUpload.client.rpc.RpcInit;
import org.gonevertical.MultiFileUpload.client.rpc.RpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DirUploader extends Composite {
  private HTML htmlImReconfiguringThe;
  private RpcServiceAsync rpc;
  private VerticalPanel verticalPanel;
  private Blobs blobs;
  private Image image;
  private HTML htmlThisImageHis;

  public DirUploader() {

    verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);

    HorizontalPanel horizontalPanel = new HorizontalPanel();
    verticalPanel.add(horizontalPanel);

    htmlImReconfiguringThe = new HTML("I'll include the applet (java app), which uploads the entire directory recurively, here later for uploading to the directory. In the meantime it works good in eclipse debugger. I have to figure out how to include the external libraries.<br/>\n<br/>\n<br/>", true);
    horizontalPanel.add(htmlImReconfiguringThe);
    
    htmlThisImageHis = new HTML("This image his hosted virtually in Googles Big Table, which was uploaded via the Java App.", true);
    verticalPanel.add(htmlThisImageHis);
    
    image = new Image("/serve/keystonedata/keystone1/preview.jpg");
    verticalPanel.add(image);
    
    blobs = new Blobs();
    verticalPanel.add(blobs);

    setup();
  }

  private void setup() {
    
    /* can't get it to work in applet form. applet works in debugger great. Its about including other libraries
    String applet = "<applet archive=\"/applet/choosedir/SSignedApplet.jar\" " +
    "code=\"com.gonevertical.upload.FileUploadApplet\" " +
    "jnlp_href=\"/applet/choosedir/FileUpload.jnlp\" " +
    "width=\"200\" " +
    "height=\"50\" " +
    "id=\"applet\" MAYSCRIPT>" +
    "</applet>";
    html.setHTML(applet);
    */


    rpc = RpcInit.init();
    
    blobs.getBlobsList();
  }

  public HTML getHtml() {
    return htmlImReconfiguringThe;
  }

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
