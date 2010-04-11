package org.gonevertical.loc.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SideScore extends Composite {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private Grid grid = null;
  
  private int rows = 10;
  
  private int width = 50;
  private int height = 365;
  
  private int score = -1;
  
  public SideScore(int player) {
    
    int r = rows;
    int c = 1;
    grid = new Grid(r,c);
    
    pWidget.add(grid);
    pWidget.add(new HTML("Player " + player));
    
    initWidget(pWidget);
  
    grid.setSize(Integer.toString(width), Integer.toString(height));
  
    drawScoreStyle();
  }
  
  public void addScore() {
    score++;
    drawScore();
    
    if (score >= rows) {
      // TODO winner
    }
  }
  
  public void resetScore() {
    score = -1;
    //drawScoreStyle();
    drawScore();
  }
  
  /**
   * draw score
   */
  private void drawScore() {
    
    for (int i=0; i < rows; i++) {
      
      int reverse = rows - 1 - i;
      grid.getCellFormatter().addStyleName(reverse, 0, getStyle(i));
      
      if (i >= score) {
        break;
      }
      
    }
  }
  
  /**
   * draw default score style
   */
  private void drawScoreStyle() {
    for (int i=0; i < rows; i++) {
      grid.getCellFormatter().addStyleName(i, 0, "Score-Grid");
      grid.getCellFormatter().removeStyleName(i, 0, "Score-Low");
      grid.getCellFormatter().removeStyleName(i, 0, "Score-Mid");
      grid.getCellFormatter().removeStyleName(i, 0, "Score-High");
    }
  }
  
  /**
   * get score for level
   * 
   * @param index
   * @return
   */
  private String getStyle(int index) {
    String style = "";
    if (index >= 0 && index <= 3) {
      style = "Score-Low";
    } else if (index > 3 && index <= 7) {
      style = "Score-Mid";
    } else if (index > 7 && index <= 10) {
      style = "Score-High";
    }
    return style;
  }
  
}
