package org.gonevertical.core.client.ui.admin.join;

import com.google.gwt.user.client.rpc.IsSerializable;

public class JoinDataFilter implements IsSerializable {

	public static final int TYPE_THING_THINGSTUFF = 1;
	
	private int type = 0;
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
}
