package org.gonevertical.demo.client;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class ToolTipWidget implements MouseOverHandler, MouseOutHandler {

	private FocusPanel pWidget = new FocusPanel();

	private boolean stayOn = false;
	
	/**
	 * constructor
	 */
	public ToolTipWidget() {
		
		pWidget.setVisible(false);
		RootPanel.get().add(pWidget);
		
		pWidget.setStyleName("Tooltip");
		
		register();
		
		pWidget.addMouseOverHandler(this);
		pWidget.addMouseOutHandler(this);
	}
	
	private void register() {
		registerJsni(this);
	}
	
	public static native void registerJsni(ToolTipWidget ttw) /*-{
	
  	$wnd.showTooltip = function(elementId, html) {
  		ttw.@org.gonevertical.demo.client.ToolTipWidget::showTooltip(Ljava/lang/String;Ljava/lang/String;)(elementId, html);
  	}
  	
  	$wnd.showTooltip_xy = function(x, y, html) {
  		ttw.@org.gonevertical.demo.client.ToolTipWidget::showTooltip(IILjava/lang/String;)(x, y, html);
  	}
  	
  	$wnd.hideTooltip = function() {
  		ttw.@org.gonevertical.demo.client.ToolTipWidget::hideTooltip()();
  	}
  	
  }-*/;
	
	public void showTooltip(int x, int y, String html) {
		
		stayOn = false;
		
		pWidget.add(new HTML(html));
		
		RootPanel.get().setWidgetPosition(pWidget, x, y);
		
		pWidget.setVisible(true);
		
	}
	
	public void showTooltip(String elementId, String html) {
		
		if (elementId == null) {
			Window.alert("error tooltip: forgot ElementId");
		} 
		
		if (html == null) {
			Window.alert("error tooltip: forgot html");
		}
		
		stayOn = false;
		
		pWidget.add(new HTML(html));
		
		Element e = RootPanel.get(elementId).getElement();
		int top = e.getAbsoluteTop();
		
		int left = 5;
		if (e.getAbsoluteRight() > 5) {
			left = ((e.getAbsoluteRight() - e.getAbsoluteLeft()) / 2) + e.getAbsoluteLeft();
		}
		
		RootPanel.get().setWidgetPosition(pWidget, left, top);
		
		pWidget.setVisible(true);
	
	}
	
	public void hideTooltip() {

		Timer t = new Timer() {
			public void run() {
				hide();
			}
		};
		t.schedule(5);
	}
	
	private void hide() {
		if (stayOn == true) {
			return;
		}
		
		pWidget.clear();
		pWidget.setVisible(false);
	}

	@Override
  public void onMouseOver(MouseOverEvent event) {
		stayOn = true;
  }

	@Override
  public void onMouseOut(MouseOutEvent event) {
		stayOn = false;
	  hide();
  }
	
	
	
}
