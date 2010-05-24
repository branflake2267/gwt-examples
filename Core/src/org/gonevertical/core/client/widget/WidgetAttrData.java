package org.gonevertical.core.client.widget;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WidgetAttrData implements IsSerializable {

	/**
	 * core widget parameter - can view this widget
	 */
	private boolean canView;
	
	/**
	 * core widget parameter - can add another record in this widget
	 */
	private boolean canAdd;
	
	/**
	 * core widget parameter - can 
	 */
	private boolean canEdit;
	
	/**
	 * generic widget parameters
	 */
	private HashMap<String,String> params = null;
	
	/**
	 * constructor
	 */
	public WidgetAttrData() {
	}
	
	public boolean getCanView() {
		return canView;
	}
	
	public boolean getCanAdd() {
		return canAdd;
	}
	
	public boolean getCanEdit() {
		return canEdit;
	}
	
	public void setData(boolean canView, boolean canAdd, boolean canEdit) {
		this.canView = canView;
		this.canAdd = canAdd;
		this.canEdit = canEdit;
	}
	
	public void setParameters(HashMap<String,String> params) {
		this.params = params;
	}
	
}
