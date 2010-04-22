package org.gonevertical.demo.client;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class ToolTipWidget implements MouseOverHandler, MouseOutHandler {

	private ToolTipPanel pWidget = new ToolTipPanel();
	private HTML wHtml = new HTML();
	
	private boolean stayOn = false;
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	private String type = null;
	
	private int scrollLeft = 0;
	private int scrollTop = 0;
	

	/**
	 * constructor
	 */
	public ToolTipWidget() {

		pWidget.setVisible(false);
		pWidget.add(wHtml);
		
		RootPanel.get().add(pWidget);
		
		pWidget.setStyleName("Tooltip");
		
		register();
		
		pWidget.addMouseOverHandler(this);
		pWidget.addMouseOutHandler(this);
	}
	
	private void register() {
		registerMouse();
		registerJsni(this);
	}
	
	/**
	 * register mouse and scroll events
	 */
	private void registerMouse() {
		
		/* deprecated
		DOM.addEventPreview(new EventPreview() {
			public boolean onEventPreview(Event event) {
				mouseX = event.getClientX();
				mouseY = event.getClientY();
				return false;
			}
		});
		*/
		
		/* deprecated
		Event.addEventPreview(new EventPreview() {
			public boolean onEventPreview(Event event) {
				mouseX = event.getClientX();
				mouseY = event.getClientY();
				return false;
			}
		});
		*/
		
		// observe mouse x and y 
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				NativeEvent e = event.getNativeEvent();
				
				type = e.getType();
				mouseX = e.getClientX();
				mouseY = e.getClientY();
				
				// test
				int wheel = e.getMouseWheelVelocityY();
				
				// debug
				System.out.println("x: " + mouseX + " y: " + mouseY + " wheel: " + wheel + " type: " + type);
			}
		});
		
		// observe scroll event, so we can offset x and y for tooltip
		Window.addWindowScrollHandler(new ScrollHandler() {
			public void onWindowScroll(ScrollEvent event) {
				
				scrollLeft = event.getScrollLeft();
				scrollTop = event.getScrollTop();
				
				// debug
				System.out.println("scrollLeft: " + scrollLeft + " scrollTop: " + scrollTop);
			}
		});
	}
	
	public static native void registerJsni(ToolTipWidget ttw) /*-{
	
  	$wnd.showTooltip_id = function(elementId, html) {
  		ttw.@org.gonevertical.demo.client.ToolTipWidget::showTooltip(Ljava/lang/String;Ljava/lang/String;)(elementId, html);
  	}
  	
  	$wnd.showTooltip_xy = function(x, y, html) {
  		ttw.@org.gonevertical.demo.client.ToolTipWidget::showTooltip(IILjava/lang/String;)(x, y, html);
  	}
  	
    $wnd.showTooltip = function(html) {
  		ttw.@org.gonevertical.demo.client.ToolTipWidget::showTooltip(Ljava/lang/String;)(html);
  	}
  	
  	$wnd.hideTooltip = function() {
  		ttw.@org.gonevertical.demo.client.ToolTipWidget::hideTooltip()();
  	}
  	
  }-*/;
	
	/**
	 * show tooltip 
	 * 
	 * @param x
	 * @param y
	 * @param html
	 */
	public void showTooltip(int x, int y, String html) {
		
		stayOn = false;
		
		wHtml.setHTML(html);
		
		RootPanel.get().setWidgetPosition(pWidget, x, y);
		
		pWidget.setVisible(true);
		
	}
	
	/**
	 * show tooltip
	 * 
	 * @param elementId - id of element
	 * @param html
	 */
	public void showTooltip(String elementId, String html) {
		
		if (elementId == null) {
			Window.alert("error tooltip: forgot ElementId");
		} 
		
		if (html == null) {
			Window.alert("error tooltip: forgot html");
		}
		
		stayOn = false;
		
		wHtml.setHTML(html);
		
		Element e = RootPanel.get(elementId).getElement();
		int top = e.getAbsoluteTop();
		
		int left = 5;
		if (e.getAbsoluteRight() > 5) {
			left = ((e.getAbsoluteRight() - e.getAbsoluteLeft()) / 2) + e.getAbsoluteLeft();
		}
		
		RootPanel.get().setWidgetPosition(pWidget, left, top);
		
		pWidget.setVisible(true);
	}
	
	/**
	 * show tooltip 
	 * 
	 * @param html
	 */
	public void showTooltip(String html) {
		
		stayOn = false;
		
		wHtml.setHTML(html);
	  
	  int x = scrollLeft + mouseX;
	  int y = scrollTop + mouseY;
		
		RootPanel.get().setWidgetPosition(pWidget, x, y);
		
		pWidget.setVisible(true);
	}
	
	/**
	 * hid tooltip
	 */
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
		
		pWidget.setVisible(false);
	}

  public void onMouseOver(MouseOverEvent event) {
		stayOn = true;
  }

  public void onMouseOut(MouseOutEvent event) {
		stayOn = false;
	  hide();
  }
	
	
	
}
