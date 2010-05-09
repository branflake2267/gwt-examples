package com.gawkat.demo.client.layout;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LinksWidget extends Composite {

	private ClientPersistence cp;
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	public LinksWidget(ClientPersistence cp) {
		this.cp = cp;
		
		initWidget(pWidget);
		
		draw();
	}
	
	private void draw() {
		
		Hyperlink h1 = new Hyperlink("Home", "dce_home");
		Hyperlink h2 = new Hyperlink("Content Test", "dce_test");
		Hyperlink h3 = new Hyperlink("Core Account Admin", "account_Home");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(h1);
		hp.add(new HTML("&nbsp;&nbsp;"));
		hp.add(h2);
		hp.add(new HTML("&nbsp;&nbsp;"));
		hp.add(h3);
		
		pWidget.add(hp);
		
		pWidget.addStyleName("dce_layout_links");
	}
	
}
