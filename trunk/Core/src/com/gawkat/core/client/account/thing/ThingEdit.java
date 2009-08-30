package com.gawkat.core.client.account.thing;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypes;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ThingEdit extends Composite {
  
  private ClientPersistence cp = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private ThingData thingData = null;

  private ThingTypesData thingTypesData = null;
  
  private HorizontalPanel pTop = new HorizontalPanel();
  private VerticalPanel pStuff = new VerticalPanel();
  
  private PushButton bAdd = new PushButton("Add Data");

  public ThingEdit(ClientPersistence cp) {
    this.cp = cp;
    
    pWidget.add(pTop);
    pWidget.add(pStuff);
    
    initWidget(pWidget);
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
    ThingStuffTypes wStuff = new ThingStuffTypes(cp);
    pStuff.add(wStuff);
    wStuff.setData(thingData);
  }
  
  private void drawTop() {
    ThingTypeData thingTypeData = thingTypesData.getThingType(thingData.getThingTypeId());
    
    pTop.add(new HTML("Id: " + thingData.getThingId()));
    pTop.add(new HTML("&nbsp;&nbsp;"));
    pTop.add(new HTML("Type: " + thingTypeData.getName()));
    pTop.add(new HTML("&nbsp;&nbsp;"));
    pTop.add(new HTML("Key: " + thingData.getKey()));
  }

}
