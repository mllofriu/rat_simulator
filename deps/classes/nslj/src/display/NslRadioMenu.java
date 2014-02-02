/*
 *Copyright(c) 1997 USC Brain Project. email nsl@java.usc.edu
 */

/**
   @author Nikunj Mehta
*/

package nslj.src.display;

import java.awt.*;
import java.awt.event.*;

public class NslRadioMenu extends Menu {
  public NslRadioMenu(String name, String labels[]) {
    super(name);
    for (int i = 0; i < labels.length; i++) 
      super.add(new CheckboxMenuItem(labels[i], false));
  }

  public boolean selectItem(CheckboxMenuItem item) {
    return selectItem(item.getLabel());
  }
  
  public boolean selectItem(String s) {
    int numItems = getItemCount();
    boolean found = false;
    for (int i = 0; i < numItems; i++) {
      if (s.equals(getItem(i).getLabel())) {
	((CheckboxMenuItem) getItem(i)).setState(true);
	found = true;
       } else {
	((CheckboxMenuItem) getItem(i)).setState(false);
     }
    }
    return found;
  }

  public synchronized void addItemListener(ItemListener l) {
    int numItems = getItemCount();
    for (int i = 0; i < numItems; i++) {
	((CheckboxMenuItem) getItem(i)).addItemListener(l);
    }
  }

  public synchronized void removeItemListener(ItemListener l) {
    int numItems = getItemCount();
    for (int i = 0; i < numItems; i++) {
	((CheckboxMenuItem) getItem(i)).removeItemListener(l);
    }
  }
}
