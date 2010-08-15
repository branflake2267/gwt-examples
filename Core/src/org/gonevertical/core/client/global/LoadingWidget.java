package org.gonevertical.core.client.global;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * make a loading img appear
 * 
 * @author branflake2267
 *
 */
public class LoadingWidget extends Composite {

  private FlowPanel pWidget = new FlowPanel();

  // in milliseconds, amount of time before hiding
  private int amountOfTime = 3000;

	private FlowPanel pNote = new FlowPanel();

  /**
   * init widget
   */
  public LoadingWidget() {

    String sImage = "/images/loading.gif";
    Image image = new Image(sImage);

    HorizontalPanel hp = new HorizontalPanel();
    hp.add(image);
    hp.add(pNote);
    
    pWidget.add(hp);

    initWidget(pWidget);

    pWidget.setStyleName("core-loadingimage");

    hide();
    
    pNote.setVisible(false);
  }

  /**
   * hide
   */
  public void hide() {
  	pNote.setVisible(false);
    pWidget.setVisible(false);
    pNote.clear();
  }

  /**
   * show
   */
  public void show() {
  	pNote.clear();
    pWidget.setVisible(true);
  }

  /**
   *  hide timed
   */
  public void hideTimed() {
    pWidget.setVisible(true);

    Timer t = new Timer() {
      public void run() {
        pWidget.setVisible(false);
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
		pNote.clear();
		pNote.add(new HTML("&nbsp;" + html));
		pNote.setVisible(true);
  }


}
