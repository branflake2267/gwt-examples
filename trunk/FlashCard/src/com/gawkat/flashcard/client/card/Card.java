package com.gawkat.flashcard.client.card;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Card extends Composite {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pA = new VerticalPanel();
  
  private VerticalPanel pO = new VerticalPanel();
  
  private VerticalPanel pB = new VerticalPanel();
  
  private MathData mathData = null;
  
  public Card() {
    
    pWidget.add(pA);
    pWidget.add(pO);
    pWidget.add(pB);
    
    initWidget(pWidget);
  
    pWidget.setStyleName("card");
  }

  private void setA(int a) {
    String s = Integer.toString(a);
    pA.clear();
    HTML h = new HTML(s);
    pA.add(h);
  }
  
  private void setO(int o) {
    pO.clear();
    HTML h = new HTML(MathData.getOperator(o));
    pO.add(h);
  }
  
  private void setB(int b) {
    String s = Integer.toString(b);
    pB.clear();
    HTML h = new HTML(s);
    pB.add(h);
  }
  
  public void setMathData(MathData mathData) {
    if (mathData == null) {
      mathData = new MathData();
    }
    this.mathData = mathData;
    drawMathData();
  }
  
  private void drawMathData() {
    setA(mathData.getA());
    setO(mathData.getOperator());
    setB(mathData.getB()); 
  }
  
  
}
