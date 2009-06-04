package com.gawkat.flashcard.client.card;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NumberBox extends Composite implements ClickHandler, BlurHandler, ChangeHandler {
  
  private final static int GROW = 1;
  private final static int SHRINK = 2;
  
  public final static int LEFT = 1;
  public final static int RIGHT = 2;
  
  private int doAnimate = 0;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private AddMinus wAddMinus = new AddMinus();
  
  private TextBox tbNumber = new TextBox();
  
  private HorizontalPanel pText = new HorizontalPanel();
  
  private HTML h = new HTML();
  
  private int inumber = 0;
  
  private boolean animationInProgress = false;
  
  private int sizebottom = 55;
  
  private int sizetop = 150;
  
  private int size = sizebottom;
  
  private int change = 0;
  
  public NumberBox(int leftright) {
    
    HorizontalPanel hp = new HorizontalPanel();
    if (leftright == 1) {
      hp.add(wAddMinus);
    }
    hp.add(pText);
    if (leftright == 2) {
      hp.add(wAddMinus);
    }
    
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    // style     
    pWidget.setCellHorizontalAlignment(hp, HorizontalPanel.ALIGN_CENTER);
    hp.setCellVerticalAlignment(wAddMinus, VerticalPanel.ALIGN_MIDDLE);
    h.setStyleName("flashcard-minmax");
    
    // TODO
    tbNumber.addClickHandler(this);
    tbNumber.addBlurHandler(this);
    tbNumber.addChangeHandler(this);
    
    wAddMinus.addChangeHandler(this);
    
    //hp.addStyleName("test1");
    //pText.addStyleName("test2");
    //pWidget.addStyleName("test3");
    
    setDefaultSize();
  }

  public void setNumber(int i) {
    this.inumber = i;
    drawText();
  }
  
  public void doAnimation(boolean b) {
    
    if (b == true) {
      grow();
    } else if (b == false) {
      shrink();
    }
    
  }
  
  private void grow() {
    doAnimate = GROW;
    if (animationInProgress == false) {
      animationInProgress = true;
      setSize();
      animate();
    }
    
  }
  
  private void shrink() {
    doAnimate = SHRINK;
    if (animationInProgress == false) {
      animationInProgress = true;
      setSize();
      animate();
    }
  }
  
  private void animate() {
    
    if (animationInProgress == false) {
      return;
    }
    
    // timer used for a small delay
    Timer t = new Timer() {
      public void run() {
        setSize();
        
        // recursion - animationInProgress controls break
        animate(); 
      }
    };
    t.schedule(1);
    
  }
  
  private void setSize() {
    
    if (doAnimate == GROW) {
      setSizeGrow();
    } else if (doAnimate == SHRINK) {
      setSizeShrink();
    }
    setFontSize();
    
  }
  
  /**
   * grow the font size
   */
  private void setSizeGrow() {
    if (size >= sizetop) {
      animationInProgress = false;
      wAddMinus.setVisible(true);
    }
    size++;
    size++;
    //System.out.println("++size: " + size);
  }
  
  private void setSizeShrink() {
    if (size <= sizebottom) {
      animationInProgress = false;
      
    }
    size--;
    size--;
    //System.out.println("--size: " + size);
    
    if (wAddMinus.isVisible() == true) {
      wAddMinus.setVisible(false);
    }
  }
  
  // TODO - have a custom input box on click
  private void drawInputBox() {
    pWidget.clear();
    // TODO - draw small size then grow it to 100%
  }
  
  private void drawText() {
    pText.clear();
    h = new HTML(Integer.toString(inumber));
    pText.add(h);
    pText.setCellHorizontalAlignment(h, HorizontalPanel.ALIGN_CENTER);
    setFontSize();
  }
  
  private void setFontSize() {
    String style = size + "%";
    DOM.setStyleAttribute(h.getElement(), "fontSize", style);
  }
  
  private void setDefaultSize() {
    size = sizebottom;
    setFontSize();
  }
  
  private void processChange() {
    
    int change = wAddMinus.getChange();
    if (change == AddMinus.PLUS) {
      inumber++;
    } else if (change == AddMinus.MINUS) {
      inumber--;
    }
    drawText();
    fireChange();
  }
  
  /**
   * TODO upgrade this to the gwt event system
   * 
   * @return
   */
  public int getChange() {
    return this.change;
  }
  
  public int getNumber() {
    return inumber;
  }
  
  /**
   * fire a change event
   */
  private void fireChange() {
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public void onClick(ClickEvent event) {
    
  }

  public void onBlur(BlurEvent event) {
    
  }

  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }
  
  public void onChange(ChangeEvent event) {
   
    Widget sender = (Widget) event.getSource();
    
    if (sender == wAddMinus) {
      processChange();
    }
    
  }
  

}
