package com.gawkat.walletinventory.client.item;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Item extends Composite implements ClickHandler, FocusHandler, BlurHandler {

  private FocusPanel pWidget = new FocusPanel();
  private FlowPanel pName = new FlowPanel();
  private FlowPanel pNote = new FlowPanel();
  private FlowPanel pDelete = new FlowPanel();
  
  // name of item
  private TextBox tbName = new TextBox();
  
  // note about the item
  private TextBox tbNote = new TextBox();
  
  // delete button
  private PushButton bDelete = new PushButton("X");
  
  private ItemData itemData = null;
  
  /**
   * constructor - init widget
   */
  public Item() {
      
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(pName);
    hp.add(new HTML("&nbsp;"));
    hp.add(pNote);
    hp.add(new HTML("&nbsp;"));
    hp.add(pDelete);
    
    initWidget(pWidget);
    
    pWidget.addStyleName("wi-item");
    pName.addStyleName("wi-item-name");
    pNote.addStyleName("wi-item-note");
    pDelete.addStyleName("wi-item-delete");
    
    bDelete.addClickHandler(this);
    pWidget.addFocusHandler(this);
    pWidget.addBlurHandler(this);
  }
  
  public void setData(ItemData itemData) {
    this.itemData = itemData;
    drawName();
    drawNote();
  }
  
  private void drawName() {
    pName.clear();
    HTML h = new  HTML(itemData.getName());
    pName.add(h);
  }
  
  private void drawNote() {
    pNote.clear();
    HTML h = new HTML(itemData.getNote());
    pNote.add(h);
  }
  
  private void drawEditDelete() {
    pDelete.clear();
    pDelete.add(bDelete);
  }
  
  private void drawEditName() {
    tbName.setText(itemData.getName());
    pName.clear();
    pName.add(tbName);
  }
  
  private void drawEditNote() {
    tbNote.setText(itemData.getNote());
    pNote.clear();
    pNote.add(tbNote);
  }

  private void edit(boolean b) {
    if (b == true) {
      drawEdit();
    } else {
      drawView();
    }
  }
  
  // TODO
  private void delete() {
    
  }
  
  private void drawView() {
    itemData.setName(tbName.getText().trim());
    itemData.setNote(tbNote.getText().trim());
    drawName();
    drawNote();
  }

  private void drawEdit() {
    drawEditName();
    drawEditNote();
    drawEditDelete();
  }

  public ItemData getData() {
    String name = getName();
    String note = getNote();
    ItemData itemData = new ItemData();
    itemData.setName(name);
    itemData.setNote(note);
    return itemData;
  }

  private String getName() {
    return tbName.getText().trim();
  }
  
  private String getNote() {
    return tbNote.getText().trim();
  }

  public void onClick(ClickEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == bDelete) {
      delete();
    }
  }

  public void onFocus(FocusEvent event) {
    edit(true);
  }

  public void onBlur(BlurEvent event) {
    edit(false);
  }

  
  
}
