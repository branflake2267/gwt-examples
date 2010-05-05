package org.gonevertical.demo.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DateTimeWidget extends Composite implements ClickHandler, FocusHandler {

	public static final int TS_JAVASCRIPT = 1;
	public static final int TS_UNIX = 2;
	private int selectedType = TS_JAVASCRIPT;
	
	
	
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
		
		VerticalPanel pLayout = new VerticalPanel();
		pLayout.add(pInput);
		pLayout.add(pNote);
		pLayout.add(pList);
		
		VerticalPanel pWidget = new VerticalPanel();
		pWidget.add(pLayout);
		
		initWidget(pWidget);
		
		pNote.setVisible(false);
		rb0.setValue(true);
		
		pLayout.addStyleName("gv_datetime_widget");
		tbInput.addStyleName("gv_datetime_input");
		
		pWidget.setWidth("100%");
		pWidget.setCellHorizontalAlignment(pLayout, HorizontalPanel.ALIGN_CENTER);
		
		bNow.addClickHandler(this);
		bConvert.addClickHandler(this);
		
		rb0.addClickHandler(this);
		rb1.addClickHandler(this);
		
		tbInput.addFocusHandler(this);
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
		
		if (selectedType == TS_JAVASCRIPT) {
			// nothing to do
		} else if (selectedType == TS_UNIX) {
			l = (long) (l / .001);
		}
		
		Date date = new Date(l);
		
		drawDate(date);
  }
	
	private void drawDate(Date date) {
	
		if (date == null) {
			return;
		}
		pList.clear();
		
		String sdt = date.toString();
		String sdt2 = date.toGMTString();
		String sdt3 = date.toLocaleString();
		
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int week = date.getDay();
		int day = date.getDate();
		int hour = date.getHours();
		int minutes = date.getMinutes();
		int seconds = date.getSeconds();
		long time = date.getTime();
		int timezoneOffset = date.getTimezoneOffset() / 60;
		int itime = (int) (date.getTime() * .001);
		
		int rows = 13;
		int cols = 2;
		Grid g = new Grid(rows, cols);
		pList.add(g);

		HTML l0 = new HTML("String");
		HTML l0b = new HTML("GMT");
		HTML l0c = new HTML("Locale");
		HTML l1 = new HTML("Year");
		HTML l2 = new HTML("Month");
		HTML l3 = new HTML("Week");
		HTML l4 = new HTML("Day");
		HTML l5 = new HTML("Hour");
		HTML l6 = new HTML("Minutes");
		HTML l7 = new HTML("Seconds");
		HTML l8 = new HTML("Timezone Offset hr");
		HTML l9 = new HTML("Epoch Milliseconds");
		HTML l10 = new HTML("Epoch Seconds");
		
		
		
		HTML s0 = new HTML(sdt);
		HTML s0b = new HTML(sdt2);
		HTML s0c = new HTML(sdt3);
		HTML s1 = new HTML(Integer.toString(year));
		HTML s2 = new HTML(Integer.toString(month));
		HTML s3 = new HTML(Integer.toString(week));
		HTML s4 = new HTML(Integer.toString(day));
		HTML s5 = new HTML(Integer.toString(hour));
		HTML s6 = new HTML(Integer.toString(minutes));
		HTML s7 = new HTML(Integer.toString(seconds));
		HTML s8 = new HTML(Integer.toString(timezoneOffset));
		HTML s9 = new HTML(Long.toString(time));
		HTML s10 = new HTML(Integer.toString(itime));
		
		
		g.setWidget(0, 0, l0);
		g.setWidget(1, 0, l0b);
		g.setWidget(2, 0, l0c);
		g.setWidget(3, 0, l1);
		g.setWidget(4, 0, l2);
		g.setWidget(5, 0, l3);
		g.setWidget(6, 0, l4);
		g.setWidget(7, 0, l5);
		g.setWidget(8, 0, l6);
		g.setWidget(9, 0, l7);
		g.setWidget(10, 0, l8);
		g.setWidget(11, 0, l9);
		g.setWidget(12, 0, l10);
		
		g.setWidget(0, 1, s0);
		g.setWidget(1, 1, s0b);
		g.setWidget(2, 1, s0c);
		g.setWidget(3, 1, s1);
		g.setWidget(4, 1, s2);
		g.setWidget(5, 1, s3);
		g.setWidget(6, 1, s4);
		g.setWidget(7, 1, s5);
		g.setWidget(8, 1, s6);
		g.setWidget(9, 1, s7);
		g.setWidget(10, 1, s8);
		g.setWidget(11, 1, s9);
		g.setWidget(12, 1, s10);
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
  		
  	} else if (sender == rb0 || sender == rb1) {
  		setType();
  		drawNowIntoInput();
  	}
  }

  public void onFocus(FocusEvent event) {
	  Widget sender = (Widget) event.getSource();
	  
	  if (sender == tbInput) {
	  	tbInput.setText("");
	  }
	  
  }




	
}
