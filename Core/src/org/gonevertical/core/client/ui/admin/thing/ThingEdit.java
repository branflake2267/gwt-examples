package org.gonevertical.core.client.ui.admin.thing;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.global.Global_Date;
import org.gonevertical.core.client.global.Global_ListBox;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;
import org.gonevertical.core.client.ui.Ui;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffs;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypesData;
import org.gonevertical.core.client.ui.login.ChangePassword;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class ThingEdit extends Ui implements ClickHandler, ChangeHandler, MouseOverHandler, MouseOutHandler {

	private VerticalPanel pWidget = new VerticalPanel();

	private VerticalPanel pTop = new VerticalPanel();

	private VerticalPanel pThingTypes = new VerticalPanel();

	private ListBox lbThingTypes = new ListBox();

	private ThingStuffs wStuff;
	private ThingStuffs wStuffAbout;

	private TextBox tbKey = new TextBox();

	private PushButton bChangePassword = new PushButton("Change Password");

	private ThingData thingData;

	private ThingTypesData thingTypesData;

	private ThingStuffsData thingsStuffData;

	private int editingIndex = 0;
	
	private VerticalPanel pOwner = new VerticalPanel();
	private DateBox tbStartDt = new DateBox();
	private DateBox tbEndDt = new DateBox();
	private TextBox tbRank = new TextBox();
	private FlowPanel pCreatedBy = new FlowPanel();
	private FlowPanel pCreatedDt = new FlowPanel();
	private FlowPanel pUpdatedBy = new FlowPanel();
	private FlowPanel pUpdatedDt = new FlowPanel();

	public ThingEdit(ClientPersistence cp) {
		super(cp);

		wStuff = new ThingStuffs(cp);
		wStuff.setWidgetType(ThingStuffs.WIDGETTYPE_THINGSTUFF);

		wStuffAbout = new ThingStuffs(cp);
		wStuffAbout.setWidgetType(ThingStuffs.WIDGETTYPE_THINGSTUFFABOUT);

		pThingTypes.add(lbThingTypes);

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(wStuff);
		hp.add(wStuffAbout);

		// layout
		pWidget.add(new HTML("&nbsp;"));
		pWidget.add(pTop);
		pWidget.add(new HTML("&nbsp;"));
		pWidget.add(pThingTypes);
		pWidget.add(hp);

		initWidget(pWidget);

		bChangePassword.addClickHandler(this);

		wStuff.addChangeHandler(this);
		wStuffAbout.addChangeHandler(this);
		wStuffAbout.addMouseOutHandler(this);

		// don't observe thing stuff mouse overs when in about
		wStuffAbout.ignoreMouseOver(true);

		//wStuff.addStyleName("test3");
	}

	public void setData(ThingData thingData, ThingTypesData thingTypesData) {
		this.thingData = thingData;
		this.thingTypesData  = thingTypesData;
	}

	/**
	 * inital draw - get stuff from rpc
	 */
	public void draw() {

		wStuff.clear();
		wStuffAbout.clear();

		drawTop();

		drawThingTypes();
		
		drawStartDt();
		drawEndDt();
		drawRank();
		drawCreatedBy();
		drawCreatedDt();
		drawUpdatedBy();
		drawUpdatedDt();
		drawOwners();
		
		ThingStuffDataFilter filter = new ThingStuffDataFilter();
		filter.setThingId(thingData.getThingId());

		// TODO - will need to interact with wStuff and wStuff About
		filter.setLimit(0, 100);

		getThingStuffRpc(filter);
	}

	private void drawStartDt() {
		tbStartDt.setValue(thingData.getStartOf());
  }

	private void drawEndDt() {
		tbEndDt.setValue(thingData.getEndOf());
  }

	private void drawRank() {
  	String s = "0";
  	if (thingData.getRank() != null) {
  		s = Double.toString(thingData.getRank());
  	} 
  	tbRank.setText(s);
  }

	private void drawCreatedBy() {
		pCreatedBy.clear();
		String s = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		if (thingData.getCreatedBy() > 0) {
			s = Long.toString(thingData.getCreatedBy());
		}
		HTML h = new HTML(s);
		pCreatedBy.add(h);
  }

	private void drawCreatedDt() {
		pCreatedDt.clear();
	  String s = Global_Date.getDate_Eng(thingData.getDateCreated());
	  HTML h = new HTML(s);
	  pCreatedDt.clear();
	  pCreatedDt.add(h);
  }

	private void drawUpdatedBy() {
		pUpdatedBy.clear();
		String s = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		if (thingData.getUpdatedBy() > 0) {
			s = Long.toString(thingData.getUpdatedBy());
		}
		HTML h = new HTML(s);
		pUpdatedBy.add(h);
  }

	private void drawUpdatedDt() {
		pUpdatedDt.clear();
	  String s = Global_Date.getDate_Eng(thingData.getDateUpdated());
	  HTML h = new HTML(s);
	  pUpdatedDt.clear();
	  pUpdatedDt.add(h);
  }

	private void drawOwners() {
		pOwner.clear();
		String s = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		if (thingData.getOwners() == null || thingData.getOwners().length == 0) {
			s = "";
		} else {
			for (int i=0; i < thingData.getOwners().length; i++) {
				s += Long.toString(thingData.getOwners()[i]);
				if (i < thingData.getOwners().length -1) {
					s += ",";
				}
			}
		}
		HTML h = new HTML(s);
		pOwner.add(h);
  }

	private void drawThingTypes() {
		lbThingTypes.clear();
		
		if (thingTypesData == null || thingTypesData.getThingTypeData() == null) {
			return;
		}
		
		for (int i=0; i < thingTypesData.getThingTypeData().length; i++) {
			String item = thingTypesData.getThingTypeData()[i].getName();
			String value = Long.toString(thingTypesData.getThingTypeData()[i].getId());
			lbThingTypes.addItem(item, value);
		}

		if (thingData.getThingTypeId() > 0) {
			Global_ListBox.setSelected(lbThingTypes, thingData.getThingTypeId());
		}

	}

	/**
	 * draw the thing stuffs
	 * 
	 * @param thingStuffsData
	 */
	private void process(ThingStuffsData thingStuffsData) {
		this.thingsStuffData = thingStuffsData;

		wStuff.draw(thingData, thingStuffsData);
	}

	/**
	 * after save this is the return
	 * 
	 * @param thingData
	 */
	private void processThingData(ThingData thingData) {
		if (thingData == null) {
			System.out.println("ThingEdit.processThingData(): Something went wrong");
			return;
		}
		this.thingsStuffData = thingData.getThingStuffsData();

		wStuff.draw(thingData, thingData.getThingStuffsData());

		// TODO - redraw the index we are on in for waboutstuff after save
		// clear for now
		wStuffAbout.clear();

	}

	/**
	 * draw about stuff on the right
	 *   mouse over in thing stuff will cause this
	 * 
	 * @param thingStuffsData
	 */
	private void drawAbout(ThingStuffData thingStuffData) {

		ThingStuffsData thingStuffsData_About = null;
		if (thingStuffData != null) {
			thingStuffsData_About = thingStuffData.getThingStuffsAbout();

		} else {
			thingStuffsData_About = new ThingStuffsData();
		}

		// if the about is new we need to create it to store in the types
		if (thingStuffsData_About == null) {
			thingStuffsData_About = new ThingStuffsData();
		}

		thingStuffsData_About.setThingStuffTypesData(this.thingsStuffData.getThingStuffTypesData());

		wStuffAbout.draw(thingData, thingStuffsData_About);

	}

	private void drawTop() {
		pTop.clear();

		tbKey.setText(thingData.getKey());

		HorizontalPanel hp = new HorizontalPanel();
		pTop.add(hp);

		hp.add(new HTML("Id: " + thingData.getThingId()));

		int rows = 5;
		int columns = 6;
		Grid grid = new Grid(rows, columns);
		pTop.add(grid);

		HTML l1 = new HTML("Username");
		HTML l2 = new HTML("Password");
		HTML l3 = new HTML("Owners");
		HTML l4 = new HTML("StartDt");
		HTML l5 = new HTML("EndDt");
		HTML l6 = new HTML("Rank");
		HTML l7 = new HTML("CreatedBy");
		HTML l8 = new HTML("CreatedDt");
		HTML l9 = new HTML("UpdatedBy");
		HTML l10 = new HTML("UpdatedDt");
		

		HorizontalPanel bButtons = new HorizontalPanel();
		bButtons.add(bChangePassword);

		// column 1
		grid.setWidget(0, 0, l1);
		grid.setWidget(0, 1, tbKey);

		grid.setWidget(1, 0, l2);
		grid.setWidget(1, 1, bButtons);
		
		grid.setWidget(2, 0, l4);
		grid.setWidget(2, 1, tbStartDt);
		
		grid.setWidget(3, 0, l5);
		grid.setWidget(3, 1, tbEndDt);
		
		grid.setWidget(4, 0, l6);
		grid.setWidget(4, 1, tbRank);


		grid.setWidget(0, 2, l7);
		grid.setWidget(0, 3, pCreatedBy);
		
		grid.setWidget(1, 2, l8);
		grid.setWidget(1, 3, pCreatedDt);
		
		grid.setWidget(2, 2, l9);
		grid.setWidget(2, 3, pUpdatedBy);
		
		grid.setWidget(3, 2, l10);
		grid.setWidget(3, 3, pUpdatedDt);
		
		// column 2
		grid.setWidget(0, 4, l3);
		grid.setWidget(0, 5, pOwner);
	}

	private void changePassword() {
		ChangePassword p = new ChangePassword(cp);
		p.draw(thingData);
		p.center();
	}

	private String getKey() {
		String s = null;
		s = tbKey.getText().trim();
		return s;
	}

	/**
	 * save the data to server
	 */
	public void save() {

		//thing
		ThingData td = thingData;
		td.setKey(getKey()); // TODO - remove this, needs to be a dialog box
		td.setThingTypeId(getThingTypeId());
		td.setStartOf(tbStartDt.getValue());
		td.setEndOf(tbEndDt.getValue()); 
		td.setRank(getRank());

		//thing stuff
		ThingStuffData[] thingStuffData = wStuff.getData();

		ThingStuffsData thingStuffsData = new ThingStuffsData();
		thingStuffsData.setThingStuffData(thingStuffData);

		td.setThingStuffsData(thingStuffsData);

		ThingDataFilter filter = new ThingDataFilter();
		filter.setThingId(thingData.getThingId());

		//saveThingStuffsData(thingStuffData);
		saveThingRpc(td, filter);
	}
	
	private Double getRank() {
	  String s = tbRank.getValue().trim();
	  Double d = null;
	  if (s.length() != 0) {
	  	try {
	      d = Double.parseDouble(s);
      } catch (NumberFormatException e) {
      	d = null;
      }
	  }
	  return d;
  }

	private long getThingTypeId() {
		long l = Global_ListBox.getSelectedValue(lbThingTypes);
		return l;
	}

	/**
	 * before a new moused over lets 
	 * 
	 * @param index - editing index, caused by mouse event in thing stuff
	 */
	private void updateAboutStuff(int index) {

		// < -1 nothing selected yet
		if (index < 0) { 
			return;
		}

		// get about stuff that was modified
		ThingStuffData[] tsd = wStuffAbout.getData();

		if (tsd == null || tsd.length == 0) {
			return;
		}

		// update the thingstuff array 
		wStuff.setAboutThingStuffData(index, tsd);
	}

	private void setUpdateAboutStuff() {

		//System.out.println("ThingEdit.setUpdateAboutStuff(): updating about stuff");

		editingIndex = wStuff.getEditingIndex();
		updateAboutStuff(editingIndex);
	}

	public void onClick(ClickEvent event) {

		Widget sender = (Widget) event.getSource();

		if (sender == bChangePassword) {
			changePassword();
		}

	}

	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();

		if (sender == wStuff) {

			int changeEvent = wStuff.getChangeEvent();

			if (changeEvent == EventManager.THINGSTUFFABOUT_PREMOUSEOVER) { // thing stuff moused over, lets update before we change the data
				setUpdateAboutStuff();

			} else if (changeEvent == EventManager.THINGSTUFFABOUT_MOUSEOVER) { // this comes from mouse over int thing stuff
				editingIndex = wStuff.getEditingIndex(); // what index was moused over
				drawAbout(wStuff.getAboutThingStuffData());// update thing about stuff for editing
			}

		} 

	}

	public void onMouseOver(MouseOverEvent event) {

		Widget sender = (Widget) event.getSource();

		if (sender == wStuffAbout) {

		}

	}

	public void onMouseOut(MouseOutEvent event) {

		Widget sender = (Widget) event.getSource();

		if (sender == wStuffAbout) {

			System.out.println("ThingEdit.onMouseOut(): mouse out on wAboutStuff");

			setUpdateAboutStuff();
		}
	}

	private void getThingStuffRpc(ThingStuffDataFilter filter) {

		cp.showLoading(true);

		rpc.getThingStuffData(cp.getAccessToken(), filter, new AsyncCallback<ThingStuffsData>() {
			public void onSuccess(ThingStuffsData thingStuffsData) {
				process(thingStuffsData);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});

	}

	@Deprecated
	private void saveThingStuffsData(ThingStuffData[] thingStuffData) {

		cp.showLoading(true);

		ThingStuffDataFilter filter = new ThingStuffDataFilter();
		filter.setThingId(thingData.getThingId());

		rpc.saveThingStuffData(cp.getAccessToken(), filter, thingStuffData, new AsyncCallback<ThingStuffsData>() {
			public void onSuccess(ThingStuffsData thingStuffsData) {
				process(thingStuffsData);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});

	}

	protected void saveThingRpc(ThingData td, ThingDataFilter filter) {

		cp.showLoading(true);

		rpc.saveThing(cp.getAccessToken(), filter , td, new AsyncCallback<ThingData>() {
			public void onSuccess(ThingData td) {
				processThingData(td);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});

	}






}
