package com.tribling.gwt.test.calendar.client;


import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RootPanel;

public class GridExample  {

	  public GridExample() {
		  
	    
		  
		int Height = 6; //depth of grid cells
		int Width = 7; //width of grid cells
	    Grid grid = new Grid(Height, Width);

	    grid.setBorderWidth(1);
	    
	   
	    for (int row = 0; row < Height; ++row) {
	      for (int col = 0; col < Width; ++col)
	        grid.setText(row, col, "" + row + ", " + col);
	    }

	    // Just for good measure, let's put a button in the center.
	    //g.setWidget(2, 2, new Button("Does nothing, but could"));

	    // You can use the CellFormatter to affect the layout of the grid's cells.
	    //g.getCellFormatter().setWidth(0, 2, "27px");

	    RootPanel.get().add(grid);
	  }
	}
