package com.bellanovascoffee.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.HTML;

public class HTMLInclude extends HTML {
	
	public HTMLInclude(final String url) {
		
		super();
		
		final HTMLInclude widget = this;

		try {
			new RequestBuilder(RequestBuilder.GET, url).sendRequest("", 
					new RequestCallback() {
				
						public void onError(Request request, Throwable exception) {
							GWT.log("HTMLInclude: error fetching " + url, exception);
						}

						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								widget.setHTML(response.getText());
							} else {
								GWT.log("HTMLInclude: bad code when fetching "
										+ url + "[" + response.getStatusCode()
										+ "]", null);
							}
						}

					});
		} catch (RequestException e) {
			GWT.log("HTMLInclude: exception thrown fetching " + url, e);
		}

	}
}
