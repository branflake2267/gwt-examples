package org.gonevertical.democanvas.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class TestForm extends Composite {
  private FormPanel formPanel;

  public TestForm() {
    
    VerticalPanel verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    FileUpload fileUpload = new FileUpload();
    fileUpload.setName("File");
    verticalPanel.add(fileUpload);
    
    formPanel = new FormPanel();
    formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
      public void onSubmitComplete(SubmitCompleteEvent event) {
        RootPanel.get().add(new HTML("basic form finished"));
      }
    });
    formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
    formPanel.setMethod(FormPanel.METHOD_POST);
    formPanel.setAction("/TestOut/");
    verticalPanel.add(formPanel);
    
    TextBox textBox = new TextBox();
    textBox.setText("123456");
    textBox.setName("oid");
    formPanel.setWidget(textBox);
    textBox.setSize("100%", "100%");
    
    Button btnSubmit = new Button("Submit");
    btnSubmit.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        formPanel.submit();
      }
    });
    verticalPanel.add(btnSubmit);
  }

  
}
