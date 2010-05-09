package org.gonevertical.core.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;

public class BreadCrumb extends Composite {

	private HorizontalPanel pWidget = new HorizontalPanel();

	/**
	 * constructor - init widget
	 */
	public BreadCrumb() {
		initWidget(pWidget);
	}

	public void setCrumb(String crumbName, String link, boolean showLink) {

		if (showLink == true) {
			Hyperlink h = new Hyperlink(crumbName, link);
			pWidget.add(h);
		
		} else if (showLink == false) {
			
			HTML h = new HTML("<a>" + crumbName + "</a>");
			pWidget.add(h);			
		}


		pWidget.add(new HTML("&nbsp;&#187;&nbsp;"));

	}

	/**
	 * constructor - init widget
	 */
	public BreadCrumb(String crumbName, String link) {

		Hyperlink h = new Hyperlink(crumbName, link);

		pWidget.add(h);
		pWidget.add(new HTML("&nbsp;&#187;&nbsp;"));

		initWidget(pWidget);
	}

	/**
	 * constructor - init widget
	 */
	public BreadCrumb(String crumbName) {

		HTML h = new HTML("<a>" + crumbName + "</a>");

		pWidget.add(h);
		pWidget.add(new HTML("&nbsp;&#187;&nbsp;"));

		initWidget(pWidget);
	}


}
