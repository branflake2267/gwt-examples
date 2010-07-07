package org.gonevertical.core.client.global;

import com.google.gwt.user.client.ui.ListBox;

public class Global_ListBox {

  /**
   * get selected value(int)
   * 
   * @param lb
   * @return
   */
  public static long getSelectedValue(ListBox lb) {
  	if (lb == null) {
  		System.out.println("Global_Listbox().getSelectedValue() is null");
  		return -1;
  	}
  	if (lb.getItemCount() == 0) {
  		return -1;
  	}
  	
    int sel = lb.getSelectedIndex();
    String value = lb.getValue(sel);
    long r = 0;
    try {
      r = Long.parseLong(value);
    } catch (NumberFormatException e) {
      r = 0;
    }
    return r;
  }
  
  /**
   * set listbox value(int) selected
   * 
   * @param lb
   * @param value
   */
  public static void setSelected(ListBox lb, int value) {
  	if (lb == null) {
  		System.out.println("Global_Listbox().setSelected() is null");
  		return;
  	}
    int sel = 0;
    for (int i=0; i < lb.getItemCount(); i++) {
      int v = 0;
      try {
        v = Integer.parseInt(lb.getValue(i)); 
      } catch (NumberFormatException e) {
        v = 0;
      }
      if (value == v) {
        sel = i;
        break;
      }
    }
    lb.setSelectedIndex(sel);
  }

	public static void setSelected(ListBox lb, long id) {
		int i = (int) id;
		setSelected(lb, i); 
  }
  
}
