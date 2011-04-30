package org.gonevertical.democanvas.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * make a loading img appear
 * 
 * @author branflake2267
 *
 */
public class LoadingWidget extends Composite {

  /**
   * time before hiding in ms
   */
  private int amountOfTime = 3000;

	private int increment;

	private FlowPanel pNote;

  private HTML spacer = new HTML("&nbsp;&nbsp;");
	
  /**
   * init widget
   */
  public LoadingWidget() {
    spacer.setVisible(false);
    
    VerticalPanel pWidget = new VerticalPanel();
    initWidget(pWidget);
    
    HorizontalPanel hp = new HorizontalPanel();
    pWidget.add(hp);
    pWidget.setCellVerticalAlignment(hp, HasVerticalAlignment.ALIGN_MIDDLE);
    
    String sImage = "/images/loading.gif";
    Image image = new Image(sImage);
    hp.add(spacer );
    hp.add(image);
    hp.setCellVerticalAlignment(image, HasVerticalAlignment.ALIGN_MIDDLE);
    
    pNote = new FlowPanel();
    hp.add(pNote);
    hp.setCellVerticalAlignment(pNote, HasVerticalAlignment.ALIGN_MIDDLE);

    pWidget.setStyleName("core-loadingimage");

    hide();
  }

  /**
   * hide
   */
  public void hide() {
    if (increment > 0) {
      increment--;
    }
    
    if (increment <= 0) { // don't hide until all check back in
      increment = 0;
      
      setVisible(false);
      pNote.setVisible(false);
      pNote.clear();
    }
    
  }
  
  /**
   * hide dispite multiple increments
   */
  public void hideAll() {
    increment = 0;
    hide();
  }

  public void show() {
    increment++;
  	pNote.clear();
    setVisible(true);
  }
  
  public void show(String text) {    
    increment++;
    setHTML(text);
    setVisible(true);
  }
  
  public void showLoading(boolean b) {
    if (b == true) {
      show();
    } else {
      hide();
    }
  }

  /**
   *  hide timed
   */
  public void hideTimed() {
    setVisible(true);

    Timer t = new Timer() {
      public void run() {
        setVisible(false);
        pNote.clear();
        pNote.setVisible(false);
      }
    };
    t.schedule(amountOfTime);
  }

  /**
   * hide timed
   * @param amountOfTime
   */
  public void hideTimed(int amountOfTime) {
    this.amountOfTime = amountOfTime;
    hideTimed();
  }

  /**
   * add html to the loading icon on the right
   * @param html
   */
	public void setHTML(String html) {
	  if (html == null) {
	    return;
	  }
		pNote.clear();
		pNote.add(new HTML("&nbsp;" + html));
		pNote.setVisible(true);
  }

	public void setSpacerLeft(boolean visible) {
	  spacer.setVisible(visible);
	}
}
