package com.gawkat.core.client.admin.thingtype;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ThingType extends Composite {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private PushButton bDelete = new PushButton("X");
  
  private TextBox tbName = new TextBox();
  
  private FlowPanel pName = new FlowPanel();
  
  private ThingTypeData thingTypeData = null;
  
  public ThingType() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(tbName);
    hp.add(pName);
    hp.add(bDelete);
    
    pWidget.add(pName);
    
    initWidget(pWidget);
    
    tbName.setVisible(false);
    pName.setVisible(false);
  }

  public void setData(ThingTypeData thingTypeData) {
    this.thingTypeData = thingTypeData;
    draw(false);
  }
  
  private void draw(boolean edit) {
    
    String name = thingTypeData.getName();
    tbName.setText(name);
    pName.clear();
    pName.add(new HTML(name));
    
    if (edit == true) {
      tbName.setVisible(true);
      pName.setVisible(false);
    } else {
      tbName.setVisible(false);
      pName.setVisible(true);
    }
    
  }
}
