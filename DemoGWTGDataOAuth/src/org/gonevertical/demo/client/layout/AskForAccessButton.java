package org.gonevertical.demo.client.layout;

import org.gonevertical.demo.client.EventManager;
import org.gonevertical.demo.client.rpc.RpcInit;
import org.gonevertical.demo.client.rpc.RpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class AskForAccessButton extends Composite {

  private PushButton bAsk;
  
  private int event;

  private RpcServiceAsync rpc;
  private HTML hNote;

  public AskForAccessButton() {
    
    VerticalPanel verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    verticalPanel.add(horizontalPanel);
    
    bAsk = new PushButton("Ask For Access");
    bAsk.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        ask();
      }
    });
    horizontalPanel.add(bAsk);
    
    hNote = new HTML("", true);
    horizontalPanel.add(hNote);
    horizontalPanel.setCellHorizontalAlignment(hNote, HasHorizontalAlignment.ALIGN_CENTER);
    horizontalPanel.setCellVerticalAlignment(hNote, HasVerticalAlignment.ALIGN_MIDDLE);
    
    setup();
  }
  
  private void setup() {
    rpc = RpcInit.init();
    
    bAsk.setVisible(true);
  }

  private void ask() {
    String url = GWT.getHostPageBaseURL() + "askforaccess?do=ask";
    open(url);
  }

  /**
   * open window, then start a process to watch for it to close
   * 
   * @param url - oauth approval url
   */
  private void open(String url) {
    openWindow(url);
    loop();
  }

  /**
   * open new window
   * 
   * @param url - oauth approval url
   * @return
   */
  private final native void openWindow(String url) /*-{
    $wnd.winHandle = $wnd.open(url, "_blank", null);
  }-*/;
  
  /**
   * is popup window still open
   * 
   * @return - boolean
   */
  private final native boolean isWindowClosed() /*-{
    var b = false;
    // TODO check for undefined window
    if ($wnd.winHandle.closed == true) {
      b = true;
    }
    return b;
  }-*/;
  
  /**
   * loop over and over watching for window to close
   * TODO - add a recursive boundary, like only check 1000 times
   */
  private void loop() {
    Timer t = new Timer() {
      public void run() {
        if (isWindowClosed() == true) {
          setWindowClosed();
          return;
        }
        loop();
      }
    };
    t.schedule(100);
  }
  
  /**
   * call this when the window closes
   */
  public void setWindowClosed() {
    
  }
  
  public int getChangeEvent() {
    return event;
  }
  
  private void fireChange(int event) {
    this.event = event;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }

  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }
  
  private void getHasToken() {
    rpc.getHasToken(new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean bol) {
        processHasToken(bol);
      }
      public void onFailure(Throwable caught) {
      }
    });
  }
  
  protected void processHasToken(Boolean bol) {
    if (bol == null) {
      hNote.setHTML(SafeHtmlUtils.fromTrustedString("Try again"));
      
    } else if (bol == false) {
      hNote.setHTML(SafeHtmlUtils.fromTrustedString("Try again"));
      
    } else if (bol == true) {
      bAsk.setVisible(false);
      hNote.setHTML(SafeHtmlUtils.fromTrustedString("Granted"));
      fireChange(EventManager.OAUTHTOKEN_RETRIEVED);
    }
  }
  
  public PushButton getBAskForAccess() {
    return bAsk;
  }
  public HTML getHNote() {
    return hNote;
  }
}
