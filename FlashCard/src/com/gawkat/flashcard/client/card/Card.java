package com.gawkat.flashcard.client.card;

import com.gawkat.flashcard.client.NumberBox;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Card extends Composite implements FocusHandler, BlurHandler {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private FocusPanel pA = new FocusPanel();
  private FocusPanel pO = new FocusPanel();
  private FocusPanel pB = new FocusPanel();
  
  private HorizontalPanel pAi = new HorizontalPanel();
  private HorizontalPanel pOi = new HorizontalPanel();
  private HorizontalPanel pBi = new HorizontalPanel();
  
  private NumberBox mina = new NumberBox();
  private NumberBox maxa = new NumberBox();
  
  private MathData mathData = null;
  
  /**
   * constructor
   */
  public Card() {
    
    HorizontalPanel hp1 = new HorizontalPanel();
    HorizontalPanel hp2 = new HorizontalPanel();
    HorizontalPanel hp3 = new HorizontalPanel();
    
    hp1.add(mina);
    hp1.add(pAi);
    hp1.add(maxa);
    
    hp2.add(pOi);
    
    hp3.add(pBi);
    
    // focus panels - init controls off this
    pA.add(hp1);
    pO.add(hp2);
    pB.add(hp3);
    
    // main widget
    pWidget.add(pA);
    pWidget.add(pO);
    pWidget.add(pB);
    
    initWidget(pWidget);
  
    pA.addFocusHandler(this);
    pA.addBlurHandler(this);
    pO.addFocusHandler(this);
    pO.addBlurHandler(this);
    pB.addFocusHandler(this);
    pB.addBlurHandler(this);
    
    // style
    pWidget.setStyleName("flashcard-card");
    pA.setWidth("100%");
    pO.setWidth("100%");
    pB.setWidth("100%");
    hp1.setWidth("100%");
    hp2.setWidth("100%");
    hp3.setWidth("100%");
    
    hp1.setCellHorizontalAlignment(pAi, HorizontalPanel.ALIGN_CENTER);
    hp2.setCellHorizontalAlignment(pOi, HorizontalPanel.ALIGN_CENTER);
    hp3.setCellHorizontalAlignment(pBi, HorizontalPanel.ALIGN_CENTER);
    
    //pA.setStyleName("test1");
    //pO.setStyleName("test2");
    //pB.setStyleName("test3");
    //pAi.setStyleName("test4");
  }

  private void setAi(int a) {
    pAi.clear();
    String s = Integer.toString(a);
    HTML h = new HTML(s);
    pAi.add(h);
    h.setStyleName("flashcard-number");
  }
  
  private void setOi(int o) {
    pOi.clear();
    HTML h = new HTML(MathData.getOperator(o));
    pOi.add(h);
    h.setStyleName("flashcard-operator");
    pOi.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
  }
  
  private void setBi(int b) {
    pBi.clear();
    String s = Integer.toString(b);
    HTML h = new HTML(s);
    pBi.add(h);
    h.setStyleName("flashcard-number");
  }
  
  public void setMathData(MathData mathData) {
    if (mathData == null) {
      mathData = new MathData();
    }
    this.mathData = mathData;
    
    mina.setNumber(mathData.getMinA());
    maxa.setNumber(mathData.getMaxA());
    
    drawMathData();
  }
  
  private void drawMathData() {
    setAi(mathData.getA());
    setOi(mathData.getOperator());
    setBi(mathData.getB()); 
  }

  private void drawAControls(boolean b) {
    mina.display(b);
    maxa.display(b);
  }
  
  public void onFocus(FocusEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == pA) {
      drawAControls(true);
      
    } else if (sender == pO) {
      
    } else if (sender == pB) {
      
    }
    
  }

  public void onBlur(BlurEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == pA) {
      drawAControls(false);
    } else if (sender == pO) {
      
    } else if (sender == pB) {
      
    }
    
  }
  
  
}
