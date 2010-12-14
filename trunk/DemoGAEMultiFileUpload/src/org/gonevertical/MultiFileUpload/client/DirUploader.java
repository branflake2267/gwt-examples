package org.gonevertical.MultiFileUpload.client;

import org.gonevertical.MultiFileUpload.client.rpc.RpcInit;
import org.gonevertical.MultiFileUpload.client.rpc.RpcServiceAsync;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

public class DirUploader extends Composite {
  private HTML htmlImReconfiguringThe;
  private RpcServiceAsync rpc;
  private VerticalPanel verticalPanel;
  private VerticalPanel vpForm;

  public DirUploader() {

    verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);

    HorizontalPanel horizontalPanel = new HorizontalPanel();
    verticalPanel.add(horizontalPanel);

    htmlImReconfiguringThe = new HTML("I'm reconfiguring the way the applet loads. This will take me a few more days. So far it works great in debugger.", true);
    horizontalPanel.add(htmlImReconfiguringThe);

    vpForm = new VerticalPanel();
    verticalPanel.add(vpForm);

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
  public VerticalPanel getVpForm() {
    return vpForm;
  }
}
