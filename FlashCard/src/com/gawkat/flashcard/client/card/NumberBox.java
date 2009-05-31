package com.gawkat.flashcard.client.card;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NumberBox extends Composite implements ClickHandler, BlurHandler {
  
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
  
  private int sizebottom = 50;
  
  private int sizetop = 150;
  
  private int size = sizebottom;
  
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
    hp.setWidth("100%");
    pText.setWidth("100%");
    
    
    // TODO
    tbNumber.addClickHandler(this);
    tbNumber.addBlurHandler(this);
    
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
  }
  
  // TODO - have a custom input box on click
  private void drawInputBox() {
    pWidget.clear();
    // TODO - draw small size then grow it to 100%
  }
  
  private void drawText() {
    pText.clear();
    h = new HTML(Integer.toString(inumber));
    h.setStyleName("flashcard-minmax");
    pText.add(h);
    pText.setCellHorizontalAlignment(h, HorizontalPanel.ALIGN_CENTER);
  }
  
  private void setFontSize() {
    String style = size + "%";
    DOM.setStyleAttribute(h.getElement(), "fontSize", style);
  }
  
  private void setDefaultSize() {
    size = sizebottom;
    setFontSize();
  }
  
  public void onClick(ClickEvent event) {
    
  }

  public void onBlur(BlurEvent event) {
    
  }
  

}
