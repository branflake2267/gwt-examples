package com.gawkat.flashcard.client.card;

import com.gawkat.flashcard.client.NumberBox;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Card extends Composite implements MouseOverHandler, MouseOutHandler {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private FocusPanel pA = new FocusPanel();
  private FocusPanel pO = new FocusPanel();
  private FocusPanel pB = new FocusPanel();
  
  private HorizontalPanel pAi = new HorizontalPanel();
  private HorizontalPanel pOi = new HorizontalPanel();
  private HorizontalPanel pBi = new HorizontalPanel();
  
  private NumberBox mina = new NumberBox();
  private NumberBox maxa = new NumberBox();
  
  private NumberBox minb = new NumberBox();
  private NumberBox maxb = new NumberBox();
  
  private MathData mathData = null;
  
  /**
   * constructor
   */
  public Card() {
    
    HorizontalPanel hp1 = new HorizontalPanel();
    hp1.add(mina);
    hp1.add(pAi);
    hp1.add(maxa);
    
    HorizontalPanel hp2 = new HorizontalPanel();
    hp2.add(pOi);
    
    HorizontalPanel hp3 = new HorizontalPanel();
    hp3.add(minb);
    hp3.add(pBi);
    hp3.add(maxb);
    
    // focus panels - init controls off this
    pA.add(hp1);
    pO.add(hp2);
    pB.add(hp3);
    
    // main widget
    pWidget.add(pA);
    pWidget.add(pO);
    pWidget.add(pB);
    
    initWidget(pWidget);
  
    pA.addMouseOverHandler(this);
    pO.addMouseOverHandler(this);
    pB.addMouseOverHandler(this);
    
    pA.addMouseOutHandler(this);
    pO.addMouseOutHandler(this);
    pB.addMouseOutHandler(this);
    
    // style
    pWidget.addStyleName("flashcard-card");
    pA.setWidth("100%");
    pO.setWidth("100%");
    pB.setWidth("100%");
    hp1.setWidth("100%");
    hp2.setWidth("100%");
    hp3.setWidth("100%");
    

    hp1.setCellHorizontalAlignment(pAi, HorizontalPanel.ALIGN_CENTER);
    hp1.setCellHorizontalAlignment(mina, HorizontalPanel.ALIGN_CENTER);
    hp1.setCellHorizontalAlignment(maxa, HorizontalPanel.ALIGN_CENTER);
    hp1.setCellVerticalAlignment(mina, HorizontalPanel.ALIGN_BOTTOM);
    hp1.setCellVerticalAlignment(maxa, HorizontalPanel.ALIGN_BOTTOM);
    
    hp2.setCellHorizontalAlignment(pOi, HorizontalPanel.ALIGN_CENTER);
    
    hp3.setCellHorizontalAlignment(pBi, HorizontalPanel.ALIGN_CENTER);
    hp3.setCellHorizontalAlignment(minb, HorizontalPanel.ALIGN_CENTER);
    hp3.setCellHorizontalAlignment(maxb, HorizontalPanel.ALIGN_CENTER);
    hp3.setCellVerticalAlignment(minb, HorizontalPanel.ALIGN_BOTTOM);
    hp3.setCellVerticalAlignment(maxb, HorizontalPanel.ALIGN_BOTTOM);
    
    mina.setWidth("50px");
    pAi.setWidth("50px");
    maxa.setWidth("50px");
    
    minb.setWidth("50px");
    pBi.setWidth("50px");
    maxb.setWidth("50px");
    

    //pA.addStyleName("test1");
    //pO.addStyleName("test2");
    //pB.addStyleName("test3");
    //pAi.addStyleName("test4");
    //hp1.addStyleName("test5");
    //mina.addStyleName("test3");
  }

  private void setAi(int a) {
    pAi.clear();
    String s = Integer.toString(a);
    HTML h = new HTML(s);
    pAi.add(h);
    h.addStyleName("flashcard-number");
    pAi.setCellHorizontalAlignment(h, HorizontalPanel.ALIGN_CENTER);
  }
  
  private void setOi(int o) {
    pOi.clear();
    HTML h = new HTML(MathData.getOperator(o));
    pOi.add(h);
    h.addStyleName("flashcard-operator");
    pOi.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
    pOi.setCellHorizontalAlignment(h, HorizontalPanel.ALIGN_CENTER);
  }
  
  private void setBi(int b) {
    pBi.clear();
    String s = Integer.toString(b);
    HTML h = new HTML(s);
    pBi.add(h);
    h.addStyleName("flashcard-number");
    pBi.setCellHorizontalAlignment(h, HorizontalPanel.ALIGN_CENTER);
  }
  
  public void setMathData(MathData mathData) {
    if (mathData == null) {
      mathData = new MathData();
    }
    this.mathData = mathData;
    
    mina.setNumber(mathData.getMinA());
    maxa.setNumber(mathData.getMaxA());
    
    minb.setNumber(mathData.getMinB());
    maxb.setNumber(mathData.getMaxB());
    
    drawMathData();
  }
  
  private void drawMathData() {
    setAi(mathData.getA());
    setOi(mathData.getOperator());
    setBi(mathData.getB()); 
  }

  private void drawControlsA(boolean b) {
    mina.doAnimation(b);
    maxa.doAnimation(b);
  }
  
  private void drawControlsB(boolean b) {
    minb.doAnimation(b);
    maxb.doAnimation(b);
  }

  public void onMouseOver(MouseOverEvent event) {
    Widget sender = (Widget) event.getSource();
    
    if (sender == pA) {
      drawControlsA(true);
      
    } else if (sender == pO) {
      
    } else if (sender == pB) {
      drawControlsB(true);
    }
    
  }

  public void onMouseOut(MouseOutEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == pA) {
      drawControlsA(false);
    } else if (sender == pO) {
      
    } else if (sender == pB) {
      drawControlsB(false);
    }
    
  }
  
  
}
