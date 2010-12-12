package org.gonevertical.MultiFileUpload.client;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTML;

public class DirUploaderWidget extends Composite {
  private HTML html;

  public DirUploaderWidget() {
    
    VerticalPanel verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    verticalPanel.add(horizontalPanel);
    
    html = new HTML("Choose Directory", true);
    horizontalPanel.add(html);
    
    setup();
  }
  
  private void setup() {
    String applet = "<applet archive=\"/applet/choosedir/SSignedApplet.jar\" code=\"com.gonevertical.upload.FileUploadApplet\" jnlp_href=\"/applet/choosedir/FileUpload.jnlp\" width=\"200\" height=\"50\" id=\"applet\"></applet>";
    html.setHTML(applet);
    
    registerSetSelectedFiles(this);
  }
  
  /**
   * setup javascript method for applet to use after the directory is chosen
   * @param duw
   */
  public static native void registerSetSelectedFiles(DirUploaderWidget duw) /*-{
    $wnd.setSelectedFiles = function(files) {
        duw.@org.gonevertical.MultiFileUpload.client.DirUploaderWidget::setFilesSelected(Lcom/google/gwt/core/client/JsArrayString;)(files);
      }
  }-*/;
  
  public void setFilesSelected(JsArrayString files) {
    Window.alert("setFilesSelected");
  }

  public HTML getHtml() {
    return html;
  }
}
