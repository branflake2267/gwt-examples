package org.gonevertical.upload.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Hidden;

public class FileUploaderWidget extends Composite {
	
	private RpcServiceAsync rpc;
	
	private VerticalPanel pWidget;
	private FormPanel form;
	private VerticalPanel formElements;
	private FileUpload fileUpload;
	private Button button;
	private VerticalPanel pResponse;
	
	/**
	 * constructor - init widget
	 */
	public FileUploaderWidget() {
		
		initWidget(getTheWidget());
		
		// get url needed for blobstore
		rpc = RpcInit.init();
		
		// get the form url for the blobstore
		getFormUrl();
	}
	
	private VerticalPanel getTheWidget() {
		if (pWidget == null) {
			pWidget = new VerticalPanel();
			pWidget.add(getFormPanel());
			pWidget.add(getPResponse());
		}
		return pWidget;
	}
	
	private FormPanel getFormPanel() {
		if (form == null) {
			form = new FormPanel();
			form.setAction("/upload");
			form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);
			form.setWidget(getFormElements());
			//form.add(getHidden());
			
			// add submit handler
	    form.addSubmitHandler(new SubmitHandler() {
				public void onSubmit(SubmitEvent event) {
					if (fileUpload.getFilename().length() == 0) {
	          Window.alert("Did you select a file?");
	          event.cancel();
	        }
				}
			});
	    
	    // add submit complete handler
	    form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
				public void onSubmitComplete(SubmitCompleteEvent event) {
					button.setEnabled(false);
					String results = event.getResults();
					pResponse.add(new HTML(results));
					getFormUrl();
				}
			});
	    
		}
		return form;
	}
	
	private VerticalPanel getFormElements() {
		if (formElements == null) {
			formElements = new VerticalPanel();
			formElements.setSize("100%", "100%");
			formElements.add(getFileUpload());
			formElements.add(getButton());
		}
		return formElements;
	}
	
	private FileUpload getFileUpload() {
		if (fileUpload == null) {
			fileUpload = new FileUpload();
			fileUpload.setName("myFile");
		}
		return fileUpload;
	}
	
	private Button getButton() {
		if (button == null) {
			button = new Button("Upload");
			button.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					form.submit();
				}
			});
			button.setEnabled(false);
		}
		return button;
	}
	
	private void getFormUrl() {
		
		rpc.getBlobStoreUrl(new AsyncCallback<String>() {
			public void onSuccess(String url) {
				form.setAction(url);
				button.setEnabled(true);
				
				System.out.println("retrieved url for blob store: " + url);
			}

			public void onFailure(Throwable caught) {
				Window.alert("Something went wrong with the rpc call.");
			}
		});
		
	}
	private VerticalPanel getPResponse() {
		if (pResponse == null) {
			pResponse = new VerticalPanel();
		}
		return pResponse;
	}
}
