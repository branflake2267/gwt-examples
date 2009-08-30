package com.gawkat.core.client.account.thingstuff;

import com.gawkat.core.client.Row;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ThingStuff extends Composite {
  
  private Row pWidget = new Row();

  private ListBox lbTypes = new ListBox();
  
  private FlowPanel pInput = new FlowPanel();
  
  private TextBox tbValue = new TextBox();

  private ThingStuffData thingStuffData = null;
  
  public ThingStuff() {
    
    pWidget.add(lbTypes);
    pWidget.add(pInput);
    
    initWidget(pWidget);
  }
  
  public void setData(ThingStuffTypesData thingStuffTypesData, ThingStuffData thingStuffData) {
    this.thingStuffData = thingStuffData;
    drawListBoxTypes(thingStuffTypesData);
    drawInput();
  }
  
  private void drawInput() {
    String value = thingStuffData.getValue();
    pInput.clear();
    pInput.add(tbValue);
    tbValue.setValue(value);
  }
  
  private void drawListBoxTypes(ThingStuffTypesData thingStuffTypesData) {
    lbTypes.clear();
    for (int i=0; i < thingStuffTypesData.thingStuffTypeData.length; i++) {
      String item = thingStuffTypesData.thingStuffTypeData[i].getName();
      String value = Long.toString(thingStuffTypesData.thingStuffTypeData[i].getId());
      lbTypes.addItem(item, value);
    }
  }
}
