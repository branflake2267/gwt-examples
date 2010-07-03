package org.gonevertical.core.client.ui;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.DeleteDialog;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

import com.google.gwt.user.client.ui.Composite;

public abstract class Ui extends Composite {

	protected ClientPersistence cp;
	
	protected RpcCoreServiceAsync rpc = null;
  
	protected DeleteDialog wdelete = new DeleteDialog();

	public Ui(ClientPersistence cp) {
		this.cp = cp;
		rpc = RpcCore.initRpc();
	}
	
	
	
}
