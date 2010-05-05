package org.gonevertical.demo.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DateTimeWidget extends Composite implements ClickHandler {

	public static final int TS_JAVASCRIPT = 1;
	public static final int TS_UNIX = 2;
	private int selectedType = TS_JAVASCRIPT;
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	private VerticalPanel pInput = new VerticalPanel();
	private VerticalPanel pNote = new VerticalPanel();
	private VerticalPanel pList = new VerticalPanel();
	
	private TextBox tbInput = new TextBox();
	private PushButton bConvert = new PushButton("Convert");
	private PushButton bNow = new PushButton("Now");
	
	private RadioButton rb0 = new RadioButton("rbOption", "Javascript / Milliseconds");
	private RadioButton rb1 = new RadioButton("rbOption", "Unix / Seconds");
	
	/**
	 * constructor
	 */
	public DateTimeWidget() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(tbInput);
		hp.add(new HTML("&nbsp;"));
		hp.add(bNow);
		hp.add(new HTML("&nbsp;"));
		hp.add(bConvert);
		
		pInput.add(rb0);
		pInput.add(rb1);	
		pInput.add(hp);
		
		pWidget.add(pInput);
		pWidget.add(pNote);
		pWidget.add(pList);
		
		initWidget(pWidget);
		
		pNote.setVisible(false);
		rb0.setValue(true);
		
		pWidget.addStyleName("gv_datetime_widget");
		tbInput.addStyleName("gv_datetime_input");
		
		bNow.addClickHandler(this);
		bConvert.addClickHandler(this);
	}
	
	private void drawNote(String note) {
		
		if (note == null || note.trim().length() == 0) {
			return;
		}
		pNote.setVisible(true);
		
		HTML h = new HTML(note);
		h.addStyleName("gv_datetime_note");
		
		pNote.clear();
		pNote.add(h);
		
		Timer t = new Timer() {
			public void run() {
				pNote.setVisible(false);
				pNote.clear();
			}
		};
		t.schedule(4000);
	}
	
	private void drawConvert() {
		
		long l = getDt();
		
		if (l < 0) {
			drawNote("I think you number needs to be bigger. Try again.");
			return;
		}
		
		if (selectedType == TS_JAVASCRIPT) {
			// nothing to do
		} else if (selectedType == TS_UNIX) {
			l = (long) (l / .001);
		}
		
		Date date = new Date(l);
		String sdt = date.toString();
		
		drawDate(sdt);
  }
	
	private void drawDate(String sdt) {
		
		if (sdt == null || sdt.trim().length() == 0) {
			return;
		}
		
		HTML h = new HTML(sdt);
		h.addStyleName("gv_datetime_string");
		
	  pList.clear();
	  pList.add(h);
  }
	
	/**
	 * draw current javascript time stamp
	 */
	private void drawNowIntoInput() {
		Date dt = new Date();
		long l = dt.getTime();	
		
		if (selectedType == TS_JAVASCRIPT) {
			tbInput.setText(Long.toString(l));
			
		} else if (selectedType == TS_UNIX) {
			int i = (int) (l * .001);
			tbInput.setText(Integer.toString(i));
		}
		
		drawConvert();
  }

	private long getDt() {
		String s = tbInput.getText();
		
		long l = 0;
		
		try {
	    l = Long.parseLong(s);
    } catch (NumberFormatException e) {
	    l = -1;
	    drawNote("Was that a number? Try again.");
    }
		
		return l;
	}
	
	private void setType() {
		boolean a = rb0.getValue();
		boolean b = rb1.getValue();
		if (a == true) {
			selectedType = TS_JAVASCRIPT;
		} else if (b == true) {
			selectedType = TS_UNIX;
		}
	}

  public void onClick(ClickEvent event) {
  	Widget sender = (Widget) event.getSource();
  	
  	setType();
  	
  	if (sender == bConvert) {
  		drawConvert();
  	} else if (sender == bNow) {
  		drawNowIntoInput();
  	}
  }




	
}
