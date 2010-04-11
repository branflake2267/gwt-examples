package org.gonevertical.demo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class BibleInfoWidget extends Composite {

	private RpcCallServiceAsync rpc;

	// main widget panel
	private VerticalPanel pWidget = new VerticalPanel();
	private LoadingWidget loading = new LoadingWidget();
	private VerticalPanel pBibleTable = new VerticalPanel();

	// table for the bible info
	private Grid grid = null;

	/**
	 * constructor - init the composite widget for use 
	 */
	public BibleInfoWidget() {

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(new HTML("Books in the Bible Information&nbsp;"));
		hp.add(loading);

		pWidget.add(hp);
		pWidget.add(pBibleTable);

		// init widget, this can be added into the rootpanel
		initWidget(pWidget);

		// init the rpc
		rpc = RpcInit.initRpc();
	}

	public void draw() {

		// start the process
		getBibleInfo();
		
	}

	/**
	 * draw bible info to screen after rpc callback
	 * 
	 * @param bibleData
	 */
	private void drawBibleInfo(BibleData[] bibleData) {

		// if null nothing to do, then exit
		// this will prevent errors from showing up
		if (bibleData == null) {
			return;
		}

		int rows = bibleData.length;

		// set up the table the bible info will go into. 
		// I already init the grid var above so I can reference it other methods in this instance.
		grid = new Grid(rows+1, 3);
		pBibleTable.add(grid);

		Label lBook = new Label("Book of Bible");
		Label lHmC = new Label("Chapters");
		Label lHmV = new Label("Verses");

		// tool-tip hover
		lBook.setTitle("Book of the Bible");
		lHmC.setTitle("How many chapters are in this book.");
		lHmV.setTitle("How many verses are in this book.");

		// label row - Starts with 0 ordinal
		grid.setWidget(0, 0, lBook);
		grid.setWidget(0, 1, lHmC);
		grid.setWidget(0, 2, lHmV);

		// go through the books of the bible
		for (int i = 0; i < rows; i++) {

			String howManyChapters = Integer.toString(bibleData[i].howManyChapters);
			String howManyVerses = Integer.toString(bibleData[i].howManyVerses);

			grid.setWidget(i+1, 0, new HTML(bibleData[i].book));
			grid.setWidget(i+1, 1, new HTML(howManyChapters));
			grid.setWidget(i+1, 2, new HTML(howManyVerses));

			// row style
			boolean even = i % 2 == 0;
			String style = "";
			if (even == true) {
				style = "rs-even";
			} else {
				style = "rs-odd";		
			}
			grid.getRowFormatter().setStyleName(i+1, style);
		}

		grid.setStyleName("bibleTable");

	}

	/**
	 * rpc request to get the bible info. 
	 * 
	 * on return, draw the data to screen
	 */
	private void getBibleInfo() {

		// draw loading
		loading.show();

		// remote procedure call to the server to get the bible info
		rpc.getBibleInfo(new AsyncCallback<BibleData[]>() {
			
			public void onSuccess(BibleData[] bibleData) {
				
				// draw bible info
				drawBibleInfo(bibleData);


				// hide loading
				loading.hide();
			}
			
			public void onFailure(Throwable caught) {
				RootPanel.get().add(new HTML(caught.toString()));
				
			}
		});
	}


}
