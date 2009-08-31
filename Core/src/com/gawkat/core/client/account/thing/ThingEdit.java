package com.gawkat.core.client.account.thing;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.account.thingstuff.ThingStuffs;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypes;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThingEdit extends Composite implements ClickHandler {
  
  private ClientPersistence cp = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private ThingStuffs wStuff = null;
  
  private ThingData thingData = null;

  private ThingTypesData thingTypesData = null;
  
  private VerticalPanel pTop = new VerticalPanel();
  private VerticalPanel pStuff = new VerticalPanel();
  
  private TextBox tbKey = new TextBox();
  
  private PushButton bChangePassword = new PushButton("Change Password");
  
  public ThingEdit(ClientPersistence cp) {
    this.cp = cp;
    
    wStuff = new ThingStuffs(cp);
    
    pWidget.add(pTop);
    pWidget.add(pStuff);
    
    initWidget(pWidget);
    
    bChangePassword.addClickHandler(this);
  }
  
  public void setData(ThingData thingData, ThingTypesData thingTypesData) {
    this.thingData = thingData;
    this.thingTypesData  = thingTypesData;
  }
  
  public void draw() {
    drawTop();
    drawStuff();
  }
  
  private void drawStuff() {
    pStuff.clear();
    pStuff.add(wStuff);
    wStuff.setData(thingData);
    wStuff.draw();
  }
  
  private void drawTop() {
    pTop.clear();
    ThingTypeData thingTypeData = thingTypesData.getThingType(thingData.getThingTypeId());
    
    tbKey.setText(thingData.getKey());
    
    HorizontalPanel hp = new HorizontalPanel();
    pTop.add(hp);
    
    hp.add(new HTML("Type: " + thingTypeData.getName()));
    hp.add(new HTML("&nbsp;&nbsp;&nbsp;"));
    hp.add(new HTML("Id: " + thingData.getThingId()));

    Grid grid = new Grid(2, 2);
    pTop.add(grid);
    
    HTML l1 = new HTML("Username");
    HTML l2 = new HTML("Password");
    
    HorizontalPanel bButtons = new HorizontalPanel();
    bButtons.add(bChangePassword);
    
    grid.setWidget(0, 0, l1);
    grid.setWidget(0, 1, tbKey);
    
    grid.setWidget(1, 0, l2);
    grid.setWidget(1, 1, bButtons);
    
  }
  
  private void changePassword() {
    // TODO
  }
  
  public void save() {
    wStuff.save();
  }
  
  public void clear() {
    wStuff.clear();
  }

  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == bChangePassword) {
      changePassword();
    }
    
  }

}
