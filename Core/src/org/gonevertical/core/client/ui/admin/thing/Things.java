package org.gonevertical.core.client.ui.admin.thing;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.ui.Ui;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypesData;
import org.gonevertical.core.client.ui.widgets.Paging;
import org.gonevertical.core.client.ui.widgets.Row;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Things extends Ui implements ClickHandler, ChangeHandler {

	private VerticalPanel pWidget = new VerticalPanel();

	private ThingEdit wEdit = null;

	private ThingFilter wFilter;

	private VerticalPanel pMenu = new VerticalPanel();

	private VerticalPanel pListTop = new VerticalPanel();
	private VerticalPanel pList = new VerticalPanel();
	private int[] widths = new int[6];

	private PushButton bAdd = new PushButton("Add");
	private PushButton bSave = new PushButton("Save");
	private PushButton bBack = new PushButton("Back");
	private PushButton bFilter = new PushButton("Filter");

	// use for edit
	private ThingTypesData thingTypesData = null;

	private Paging wPage = new Paging();

	private boolean selectionOn;

	private int changeEvent = 0;

	private long selectedThingId;

	public Things(ClientPersistence cp) {
		super(cp);

		wFilter = new ThingFilter(cp);
		wEdit = new ThingEdit(cp);

		pWidget.add(wFilter);
		pWidget.add(pMenu);
		pWidget.add(pListTop);
		pWidget.add(pList);
		pWidget.add(wEdit);
		pWidget.add(wPage);

		initWidget(pWidget);

		// defaults
		drawEdit(false);

		drawMenu();

		bAdd.addClickHandler(this);
		bSave.addClickHandler(this);
		bBack.addClickHandler(this);
		wPage.addChangeHandler(this);
		bFilter.addClickHandler(this);

		//pList.addStyleName("test1");
		//wEdit.addStyleName("test2");
		//pWidget.addStyleName("test3");

		// no need for now
		//pWidget.setCellHorizontalAlignment(wPage, HorizontalPanel.ALIGN_CENTER);
	}

	/**
	 * turn on a the selection button and modify buttons off
	 * @param b
	 */
	public void setSelection(boolean b) {
		selectionOn = b;

		drawEdit(false);
	}

	public void draw() {

		// get filter choices
		wFilter.draw();

		// hide edit stuff
		drawEdit(false);

		// get things to show
		getThingsRpc();
	}

	public void drawMenu() {

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bBack);
		hp.add(new HTML("&nbsp;"));
		hp.add(bAdd);
		hp.add(new HTML("&nbsp;"));
		hp.add(bSave);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;"));
		hp.add(bFilter);

		pMenu.add(hp);
	}

	private void drawTopRow() {
		pListTop.clear();
		HTML l1 = new HTML("#");
		HTML l2 = new HTML("Id");
		HTML l3 = new HTML("ThingType");
		HTML l4 = new HTML("UserName");
		HTML l5 = new HTML("Name");
		HTML l6 = new HTML("&nbsp;");

		l1.setStyleName("core-row-top");
		l2.setStyleName("core-row-top");
		l3.setStyleName("core-row-top");
		l4.setStyleName("core-row-top");
		l5.setStyleName("core-row-top");
		l6.setStyleName("core-row-top");

		Row th = new Row();
		th.add(l1, HorizontalPanel.ALIGN_CENTER);
		th.add(l2, HorizontalPanel.ALIGN_CENTER);
		th.add(l3, HorizontalPanel.ALIGN_CENTER);
		th.add(l4, HorizontalPanel.ALIGN_CENTER);
		th.add(l5, HorizontalPanel.ALIGN_CENTER);
		th.add(l6, HorizontalPanel.ALIGN_CENTER);

		pListTop.add(th);

		widths = Row.getMaxWidths(widths, th.getWidths());
	}

	private void process(ThingsData thingsData) {

		pList.clear();

		if (thingsData == null) {
			return;
		}

		if (thingsData.getThingData() == null) {
			return;
		}

		// set choices
		thingTypesData = thingsData.getThingTypesData();

		ThingData[] t = thingsData.getThingData();

		if (t.length == 0) {
			return;
		}

		drawTopRow();

		wPage.setCounts(thingsData.getTotal());

		long count = wPage.getCountOffset();
		for (int i=0; i < t.length; i++) {
			ThingTypeData thingTypeData = thingsData.getThingTypesData().getThingType(t[i].getThingTypeId());
			addThing(count, t[i], thingTypeData);
			count++;
		}

		setWidths();
	}

	private void setWidths() {
		for (int i=0; i < pList.getWidgetCount(); i++) {
			Thing t = (Thing) pList.getWidget(i);
			widths = Row.getMaxWidths(widths, t.getRow().getWidths());
		}

		for (int i=0; i < pList.getWidgetCount(); i++) {
			Thing t = (Thing) pList.getWidget(i);
			t.getRow().setWidths(widths);
		}

		Row th = (Row) pListTop.getWidget(0);
		th.setWidths(widths);
	}

	private Thing addThing(long count, ThingData thingData, ThingTypeData thingTypeData) {
		Thing t = new Thing(cp);
		t.setSelectionOn(selectionOn);
		t.setData(count, thingData, thingTypeData);
		pList.add(t);
		widths = Row.getMaxWidths(widths, t.getRow().getWidths());
		t.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Thing tt = (Thing) event.getSource();
				int changeEvent = tt.getChangeEvent();
				if (changeEvent == EventManager.THING_VIEW) {
					view(tt.getData());
					
				} else if (changeEvent == EventManager.THING_EDIT) {
					edit(tt.getData());
					
				} else if (changeEvent == EventManager.THING_SELECT) {
					fireSelected(tt.getThingData());
				}
			}
		});
		return t;
	}

	protected void fireSelected(ThingData thingData) {
		selectedThingId = 0;
		long thingId = 0;
		if (thingData == null) {
			thingId = 0;
		} else {
			thingId = thingData.getThingId();
		}
		selectedThingId = thingId;
		fireChange(EventManager.THING_SELECT);
  }

	private void add() {
		int i = pList.getWidgetCount();
		ThingData thingData = new ThingData();
		Thing t = addThing(i, thingData, null);
		t.getRow().setWidths(widths);
		setWidths();
	}

	private void save() {

		// when edit mode is on, skip the saving of the rest of the things
		if (wEdit.isVisible() == true) {
			wEdit.save();
			return;
		}

		int wc = pList.getWidgetCount();
		if (wc == 0) {
			return;
		}

		ThingData[] thingData = new ThingData[wc];

		for (int i=0; i < thingData.length; i++) {
			Thing tt = (Thing) pList.getWidget(i);
			ThingData td = tt.getData();
			thingData[i] = td;
		}
		saveThingsRpc(thingData);
	}

	private void view(ThingData thingData) {

	}

	private void drawEdit(boolean b) {
		if (b == true) {
			wFilter.setVisible(false);
			pListTop.setVisible(false);
			pList.setVisible(false);
			wEdit.setVisible(true);
			bBack.setVisible(true);
			bAdd.setVisible(false);
			bSave.setVisible(true);
			wPage.setVisible(false);
			bFilter.setVisible(false);

		} else if (b == false ) {
			wFilter.setVisible(true);
			pListTop.setVisible(true);
			pList.setVisible(true);
			wEdit.setVisible(false);
			bBack.setVisible(false);
			bAdd.setVisible(true);
			bSave.setVisible(true);
			wPage.setVisible(true);
			bFilter.setVisible(true);
		}
	}

	private void edit(ThingData thingData) {
		drawEdit(true);
		wEdit.setData(thingData, thingTypesData);
		wEdit.draw();
	}

	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == bAdd) {
			add();

		} else if (sender == bSave) {
			save();

		} else if (sender == bBack) {
			drawEdit(false);

		} else if (sender == bFilter) {
			getThingsRpc();

		}

	}

	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();

		if (sender == wPage) {
			getThingsRpc();
		}

	}
	
  public int getChangeEvent() {
    return changeEvent;
  }

	private void fireChange(int changeEvent) {
		this.changeEvent  = changeEvent;
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		ChangeEvent.fireNativeEvent(nativeEvent, this);
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	private void getThingsRpc() {

		cp.showLoading(true);

		ThingDataFilter filter = new ThingDataFilter();
		filter.setThingTypeId(wFilter.getThingTypeIds());
		filter.setThingIdLink(wFilter.getThingIdLinker());
		filter.setLimit(wPage.getStart(), wPage.getLimit());

		rpc.getThings(cp.getAccessToken(), filter, new AsyncCallback<ThingsData>() {
			public void onSuccess(ThingsData thingsData) {
				process(thingsData);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});

	}

	private void saveThingsRpc(ThingData[] thingData) {

		cp.showLoading(true);

		ThingDataFilter filter = new ThingDataFilter();
		filter.setThingTypeId(wFilter.getThingTypeIds());
		filter.setThingIdLink(wFilter.getThingIdLinker());
		filter.setLimit(wPage.getStart(), wPage.getLimit());

		rpc.saveThings(cp.getAccessToken(), filter, thingData, new AsyncCallback<ThingsData>() {
			public void onSuccess(ThingsData thingData) {
				process(thingData);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});

	}

	public long getSelectedThingId() {
	  return selectedThingId;
  }

	public void setThingLinkers(long thingIdLinker) {
		wFilter.setLinkers(thingIdLinker);
  }




}
