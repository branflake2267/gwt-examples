package org.gonevertical.core.client.ui.account;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.DeleteDialog;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffs;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class EmailWidget extends Composite implements ClickHandler {

	private ClientPersistence cp;
	
	private RpcCoreServiceAsync rpc;

	private PushButton bDelete;
	private TextBox tbEmail;
	private ThingStuffData tsd;
	
	private DeleteDialog wdelete = new DeleteDialog();

	public EmailWidget(ClientPersistence cp) {
		this.cp = cp;
		
		FlexTable flexTable = new FlexTable();
		initWidget(flexTable);

		flexTable.setSize("287px", "33px");

		tbEmail = new TextBox();
		flexTable.setWidget(0, 0, tbEmail);
		flexTable.getCellFormatter().setWidth(0, 0, "240px");
		tbEmail.setWidth("100%");

		HTML htmlnbspnbsp = new HTML("&nbsp;", true);
		flexTable.setWidget(0, 1, htmlnbspnbsp);

		bDelete = new PushButton("X");
		flexTable.setWidget(0, 2, bDelete);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		
		rpc = RpcCore.initRpc();
		
		bDelete.addClickHandler(this);
	}

	public void setData(ThingStuffData tsd) {
		this.tsd = tsd;

		if (tsd == null) {
			return;
		}

		String text = tsd.getValue();
		tbEmail.setText(text);
	}
	
	public ThingStuffData getThingStuffData() {
		String value = tbEmail.getText().trim();
		if (tsd == null) {
			tsd = new ThingStuffData();
			tsd.setThingStuffTypeId(ThingStuffTypeData.THINGSTUFFTYPE_EMAIL);
		}
		tsd.setValue(value);
	  return tsd;
  }

	private void delete() {
		wdelete.center();
		wdelete.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {

				DeleteDialog dd = (DeleteDialog) event.getSource();

				int changeEvent = dd.getChangeEvent();

				if (changeEvent == EventManager.DELETE_YES && tsd != null && tsd.getStuffId() > 0) {

					deleteRpcThingStuff(tsd.getStuffId());

				} else {
					deleteIt(true);
				}
			}
		});
	}

	private void deleteIt(boolean b) {
		if (b == true) {
			this.removeFromParent();
		} else {
			Window.alert("Wasn't able to delete it. Please try again?");
		}

		// TODO fire redraw
	}

	/**
	 * TODO make sure one can't feed any random thignStuffId to delete
	 * 
	 * @param thingStuffId
	 */
	public void deleteRpcThingStuff(long thingStuffId) {

		rpc.deleteThingStuffData(cp.getAccessToken(), thingStuffId, new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean b) {
				deleteIt(b);
			}
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});
	}

  public void onClick(ClickEvent event) {
  	
  	Widget sender = (Widget) event.getSource();
  	
  	if (sender == bDelete) {
  		delete();
  	}
  	
  }


}
