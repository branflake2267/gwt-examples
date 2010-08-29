package org.gonevertical.upload.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Zold_FileUploader extends Composite {

	private VerticalPanel pWidget = new VerticalPanel();
	
	private FormPanel fp = new FormPanel();
	
	public Zold_FileUploader() {
		
		fp.setAction("/upload");
		fp.setEncoding(FormPanel.ENCODING_URLENCODED);
    fp.setMethod(FormPanel.METHOD_POST);
		
		TextBox tbInput = new TextBox();
		tbInput.setName("MyTextInput");
		
		FileUpload fu = new FileUpload();
		fu.setName("myFile");
		
		Button b = new Button("Upload");
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(tbInput);
		vp.add(fu);
		vp.add(b);
		
		fp.add(vp);
		
		
		pWidget.add(fp);
		
		initWidget(pWidget);
		
		b.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fp.submit();
			}
		});
	}
	

}
