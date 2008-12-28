package com.tribling.gwt.test.oauth.client.account;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
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
  
  public PushButton bCreateAccount = new PushButton("Create Account");
  public PushButton bClose = new PushButton("Close");
  
  // terms of use container
  public TextArea taTerms = new TextArea();
  
  public CheckBox cbAccept = new CheckBox("Accept terms of use and privacy agreement?");
  
  /**
   * constructor - init widget
   */
  public CreateUserAccount() {
    
    setAnimationEnabled(true);
    
    setTitle("Create New Account");
    
    setWidget(pWidget);
    
    draw();
    
    bCreateAccount.addClickListener(this);
    bClose.addClickListener(this);
  }
  
  public void draw() {
    tbU1.setWidth("300px");
    tbU2.setWidth("300px");
    tbP1.setWidth("300px");
    tbP2.setWidth("300px");
    taTerms.setWidth("100%");
    
    taTerms.setEnabled(false);
    
    String t = "Create New Account";
    
    setText(t);
    HTML title = new HTML(t);

    Label lu1 = new Label("Email");
    Label lu2 = new Label("Verify Email");
    
    Label lp1 = new Label("Password");
    Label lp2 = new Label("Verify Password");
    
    HorizontalPanel hpBottom = new HorizontalPanel();
    hpBottom.add(bClose);
    hpBottom.add(new HTML("&nbsp;&nbsp;"));
    hpBottom.add(bCreateAccount);
    
    // size of input table
    int r = 5;
    int c = 2;
    
    Grid grid = new Grid(r, c);
    
    // username
    grid.setWidget(0, 0, lu1);
    grid.setWidget(0, 1, tbU1);
    grid.setWidget(1, 0, lu2);
    grid.setWidget(1, 1, tbU2);
    
    // spacer
    grid.setWidget(2, 0, new HTML("&nbsp;"));
    grid.setWidget(2, 1, new HTML("&nbsp;"));
    
    // password
    grid.setWidget(3, 0, lp1);
    grid.setWidget(3, 1, tbP1);
    grid.setWidget(4, 0, lp2);
    grid.setWidget(4, 1, tbP2);
    
    pWidget.add(title);
    pWidget.add(grid);
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(new HTML("Terms Of Use"));
    pWidget.add(taTerms); 
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(cbAccept);
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(hpBottom);
    
    
    
  }

  public void onClick(Widget sender) {
    
    if (sender == bCreateAccount) {
      
    } else if (sender == bClose) {
      this.hide();
      // lets reset the historyToken, in-case user decides to click on create account agian
      History.newItem("account_Login");
    }
    
  }
  
  private void createAccount() {
    
  }
  
  
}
