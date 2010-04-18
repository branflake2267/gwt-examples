package org.gonevertical.core.client.account.ui;

import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.global.LoadingWidget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Paging extends Composite implements ClickHandler, ChangeHandler {

	// events
	public final static int CHANGE_START = EventManager.PAGE_CHANGESTART;
	public final static int CHANGE_LIMIT = EventManager.PAGE_CHANGELIMIT;

	private int event = 0;

	private VerticalPanel pWidget = new VerticalPanel();

	private HorizontalPanel pPrevPages = new HorizontalPanel();
	private HorizontalPanel pNextPages = new HorizontalPanel();
	private FlowPanel pOnPage = new FlowPanel();
	private FlowPanel pTotalPages = new FlowPanel();
	private LoadingWidget wLoading = new LoadingWidget();

	// buttons
	private PushButton bPrev = new PushButton("<");
	private PushButton bNext = new PushButton(">");
	private PushButton bStart = new PushButton();
	private PushButton bEnd = new PushButton();

	private ListBox lbLimit = new ListBox();

	private long total = 0;
	private int start = 0;
	private int limit = 15; 

	private int firstPage = 0;
	private int onPage = 0;
	private int lastPage = 0;
	private int totalPages = 0;

	/**
	 * constructor - init widget
	 */
	public Paging() {
		bStart.setTitle("Goto to starting page");
		bPrev.setTitle("Previous page");
		bNext.setTitle("Next page");
		bEnd.setTitle("Goto last page");

		pOnPage.setTitle("Current page");
		pTotalPages.setTitle("Total pages");
		lbLimit.setTitle("Display this many records at a time in a page");

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bStart);
		hp.add(new HTML("&nbsp;"));
		hp.add(bPrev);
		hp.add(new HTML("&nbsp;"));
		hp.add(pPrevPages);
		hp.add(new HTML("&nbsp;"));
		hp.add(pOnPage);
		hp.add(new HTML("&nbsp;"));
		hp.add(pNextPages);
		hp.add(new HTML("&nbsp;"));
		hp.add(bNext);
		hp.add(new HTML("&nbsp;"));
		hp.add(bEnd);
		hp.add(new HTML("&nbsp;"));
		hp.add(pTotalPages);
		hp.add(new HTML("&nbsp;"));
		hp.add(lbLimit);
		hp.add(new HTML("&nbsp;"));
		hp.add(wLoading);

		pWidget.add(hp);

		initWidget(pWidget);

		pWidget.setStyleName("Page");

		// observe
		bPrev.addClickHandler(this);
		bNext.addClickHandler(this);
		bStart.addClickHandler(this);
		bEnd.addClickHandler(this);
		lbLimit.addChangeHandler(this);

		// Style
		pWidget.setStyleName("100%");

		bStart.addStyleName("Page-Button");
		bPrev.addStyleName("Page-Button");
		bNext.addStyleName("Page-Button");
		bEnd.addStyleName("Page-Button");
		lbLimit.addStyleName("Page-LbLimit");

		drawLimitChoices();
		
		hp.setCellVerticalAlignment(lbLimit, VerticalPanel.ALIGN_MIDDLE);
		
		pWidget.setVisible(false);
	}

	public int getStart() {
		return start;
	}

	public int getLimit() {
		return limit;
	}
	
	public long getCountOffset() {
		long l = onPage * limit;
		l++; // offset from zero ordinal, start at 1;
	  return l;
	}
	
	/**
	 * set the widget counts at the bottom
	 * 
	 * @param total
	 */
	public void setCounts(long total) {
		this.total = total;
		
		draw();
		
		wLoading.hide();
  }

	/**
	 * set the widget counts at the bottom
	 * 
	 * @param total
	 * @param start
	 * @param limit
	 */
	public void setCounts(long total, int start, int limit) {
		this.total = total;
		this.start = start;
		this.limit = limit;

		draw();
	}
	
	/**
	 * run page calculations
	 */
	private void draw() {
		
		setTotalPages();
		
		if (totalPages > 0) {
			pWidget.setVisible(true);
		} else {
			pWidget.setVisible(false);
		}
		
		setOnPage();
		setFirstPage();
		setLastPage();

		displayPrev();
		displayNext();

		drawPrevPages();
		drawNextPages();
		getOnPage();
		drawTotalPages();

		setLimitAt();
	}

	private void setOnPage() {
		onPage = (int) (start / limit);
	}

	private void setTotalPages() {
		totalPages = (int) (total / limit);
	}

	private void setFirstPage() {
		firstPage = 0;
		bStart.setText("1");
	}

	private void setLastPage() {
		lastPage = (int) (total - limit);
	}

	private void displayPrev() {
		if (onPage == 0) {
			bPrev.setEnabled(false);
			bStart.setEnabled(false);
		}else if (onPage > 0) {
			bPrev.setEnabled(true);
			bStart.setEnabled(true);
		}
	}

	private void displayNext() {
		if (onPage == lastPage) {
			bNext.setEnabled(false);
			bEnd.setEnabled(false);
		} else if (totalPages > 0) {
			bNext.setEnabled(true);
			bEnd.setEnabled(true);
		} else {
			bNext.setEnabled(false);
			bEnd.setEnabled(false);
		}
	}

	private void drawPrevPages() {
		pPrevPages.clear();

		for(int i = onPage - 2; i < onPage; i++) {
			if (i >= 0) {
				drawPageButton(pPrevPages, i);
			}
		}
	}

	private void drawNextPages() {
		pNextPages.clear();
		for(int i = onPage + 1; i < onPage + 3; i++) {
			if (i == lastPage + 1) {
				break;
			}
			if (totalPages > i) {
				drawPageButton(pNextPages, i);
			}
		}
	}

	private void drawPageButton(HorizontalPanel hp, int i) {
		int p = i+1;
		PageButton b = new PageButton(p);
		hp.add(b);
		b.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				PageButton b = (PageButton) event.getSource();
				setPage(b.getPage());
			}
		});
		b.addStyleName("Page-Button");
		b.setTitle("Goto Page " + p);
	}

	private void drawTotalPages() {
		String p = Integer.toString(totalPages);
		bEnd.setText(p);
	}

	private void getOnPage() {
		pOnPage.clear();
		String p = Integer.toString(onPage+1);
		pOnPage.add(new HTML("<b>" + p + "</b>"));
	}

	public int getAction() {
		return this.event;
	}

	private void setNext() {
		if ((start + limit > total)) {
			start = (int) (total - limit);
		} else {
			start += limit;
		}
	}

	private void setPrev() {
		if ((start - limit) < 0) {
			start = 0;
		} else {
			start -= limit;
		}
	}

	private void setStart() {
		start = 0;
	}

	private void setEnd() {
		start = (int) (total - limit);
	}

	private void setPage(int page) {
		start = (page-1) * limit;
		fire(CHANGE_START);
	}

	private void drawLimitChoices() {
		lbLimit.addItem("15");
		lbLimit.addItem("25");
		lbLimit.addItem("50");
		lbLimit.addItem("75");
		lbLimit.addItem("100");
	}

	private void setLimitAt() {
		int sel = 0;
		for (int i=0; i < lbLimit.getItemCount(); i++) {
			String slimit = Integer.toString(limit);
			String value = lbLimit.getItemText(i); 
			if (value.equals(slimit)) {
				sel = i;
				break;
			}
		}
		lbLimit.setSelectedIndex(sel);
	}

	private void setLimit() {
		int sel = lbLimit.getSelectedIndex();
		limit = Integer.parseInt(lbLimit.getItemText(sel));
		fire(CHANGE_LIMIT);
	}

	private void fire(int action) {
		this.event = action;
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		ChangeEvent.fireNativeEvent(nativeEvent, this);
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == bPrev) {
			setPrev();
		} else if (sender == bNext) {
			setNext();
		} else if (sender == bStart) {
			setStart();
		} else if (sender == bEnd) {
			setEnd();
		}
		
		wLoading.show();
		
		fire(CHANGE_START);
	}

	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == lbLimit) {
			setLimit();
		}
	}

	@Override
	public void setVisible(boolean b) {
		if (totalPages == 0 && b == true) {
			b = false;
		}
		super.setVisible(b);
	}




}
