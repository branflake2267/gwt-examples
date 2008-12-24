package com.tribling.gwt.test.oauth.client.account;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateUserAccount extends DialogBox implements ClickListener {

  public VerticalPanel pWidget = new VerticalPanel();
  
  // inputs
  // username (email)
  public TextBox tbU1 = new TextBox();
  public TextBox tbU2 = new TextBox();
  
  // password
  public TextBox tbP1 = new TextBox();
  public TextBox tbP2 = new TextBox();
  
  public PushButton bCreateAccount = new PushButton();
  
  // terms of use container
  public TextArea terms = new TextArea();
  
  public CreateUserAccount() {
    
    setTitle("Create New Account");
    
    setWidget(pWidget);
    
    draw();
    
    bCreateAccount.addClickListener(this);
  }
  
  public void draw() {
    
    HTML title = new HTML("Create New Account");

    Label lu1 = new Label("Email");
    Label lu2 = new Label("Verify Email");
    
    Label lp1 = new Label("Password");
    Label lp2 = new Label("Verify Password");
    
    // size of input table
    int r = 4;
    int c = 2;
    
    Grid grid = new Grid(r, c);
    
    // username
    grid.setWidget(0, 0, lu1);
    grid.setWidget(0, 1, tbU1);
    grid.setWidget(1, 0, lu2);
    grid.setWidget(1, 1, tbU2);
    
    // password
    grid.setWidget(2, 0, lp1);
    grid.setWidget(2, 1, tbP1);
    grid.setWidget(3, 0, lp2);
    grid.setWidget(3, 1, tbP2);
    
    pWidget.add(title);
    pWidget.add(grid);
    pWidget.add(bCreateAccount);
    
  }

  public void onClick(Widget sender) {
    
    if (sender == bCreateAccount) {
      
    }
    
  }
  
  
}
