package com.gawkat.core.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
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

  /**
   * init widget
   */
  public LoadingWidget() {

    String sImage = "/images/loading.gif";
    Image image = new Image(sImage);

    pWidget.add(image);

    initWidget(pWidget);

    pWidget.setStyleName("LoadingImage");

    hide();
  }

  /**
   * hide
   */
  public void hide() {
    pWidget.setVisible(false);
  }

  /**
   * show
   */
  public void show() {
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
      }
    };
    t.schedule(amountOfTime);
  }

  public void hideTimed(int amountOfTime) {
    this.amountOfTime = amountOfTime;
    hideTimed();
  }


}
