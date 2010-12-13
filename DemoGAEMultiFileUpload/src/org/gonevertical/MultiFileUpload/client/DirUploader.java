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
  private HTML html;
  private RpcServiceAsync rpc;
  private VerticalPanel verticalPanel;
  private VerticalPanel vpForm;

  public DirUploader() {

    verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);

    HorizontalPanel horizontalPanel = new HorizontalPanel();
    verticalPanel.add(horizontalPanel);

    html = new HTML("Choose Directory", true);
    horizontalPanel.add(html);

    vpForm = new VerticalPanel();
    verticalPanel.add(vpForm);

    setup();
  }

  private void setup() {
    registerSetSelectedFiles(this);

    String applet = "<applet archive=\"/applet/choosedir/SSignedApplet.jar\" " +
    "code=\"com.gonevertical.upload.FileUploadApplet\" " +
    "jnlp_href=\"/applet/choosedir/FileUpload.jnlp\" " +
    "width=\"200\" " +
    "height=\"50\" " +
    "id=\"applet\" MAYSCRIPT>" +
    "</applet>";
    html.setHTML(applet);



    rpc = RpcInit.init();
  }

  /**
   * setup javascript method for applet to use after the directory is chosen
   * @param duw
   */
  public static native void registerSetSelectedFiles(DirUploader duw) /*-{
    $wnd.setSelectedFiles = function(files) {

        var app = $wnd.document.getElementById('applet');
        var filess = new Array();
        filess = app.getFiles();

        //$wnd.alert("test: " + files.length + " filess: " + filess.length);

        // pass back to gwt
        duw.@org.gonevertical.MultiFileUpload.client.DirUploader::setFilesSelected(Lcom/google/gwt/core/client/JsArrayString;)(filess);
      }
  }-*/;

  public void setFilesSelected(JsArrayString files) {

    if (files == null || files.length() == 0) {
      return;
    }

    /**
     * work around, b/c files.get(i), doesn't work. bug in gwt i think.
     */
    String[] s = files.toString().split(",");


    for (int i=0; i < s.length; i++) {
      startProcess(s[i]);
    }

  }

  private void startProcess(String path) {
    getBlobUrl(path);
  }

  private void process(String path, String bloburl) {
    System.out.println("path" + path + " bloblurl: " + bloburl);
    send(path, bloburl);
  }

  public HTML getHtml() {
    return html;
  }

  private void getBlobUrl(final String path) {
    rpc.getBlobStoreUrl(new AsyncCallback<String>() {
      public void onSuccess(String bloburl) {
        process(path, bloburl);
      }
      public void onFailure(Throwable caught) {
      }
    });
  }

  private void send(String file, String url) {
    //vpForm.clear();
    FormPanel form = new FormPanel();
    vpForm.add(form);

    form.setAction(url);
    form.setEncoding(FormPanel.ENCODING_MULTIPART);
    form.setMethod(FormPanel.METHOD_POST);
    form.setWidget(getFormElements(file));
    //form.add(getHidden());

    // add submit handler
    form.addSubmitHandler(new SubmitHandler() {
      public void onSubmit(SubmitEvent event) {
        //Window.alert("subby");
      }
    });

    // add submit complete handler
    form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
      public void onSubmitComplete(SubmitCompleteEvent event) {
        String results = event.getResults();
        System.out.println("results: " + results);
      }
    });

    form.submit();
    
    //vpForm.clear();
  }

  private VerticalPanel getFormElements(String file) {

    FileUpload f = new FileUpload();

    HTML h = new HTML("<input type=\"file\" name=\"myFile\" value=\"" + file + "\">");

    VerticalPanel vp = new VerticalPanel();
    vp.add(h);


    return vp;
  }



  public VerticalPanel getVerticalPanel() {
    return verticalPanel;
  }
  public VerticalPanel getVpForm() {
    return vpForm;
  }
}
