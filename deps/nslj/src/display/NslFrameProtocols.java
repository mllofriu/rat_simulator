/* SCCS  %G%---%W%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.awt.*;
import java.awt.image.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import java.awt.event.*;

class NslFrameProtocols extends Dialog implements ActionListener {
    
    private DoubleList list;
    private Button addButton = new Button("Add Protocols");
    private NslExecutive parent; 
    private int moduleType;
    public NslFrameProtocols(NslExecutive executive, String[] leftStrs, String[] rightStrs, int type) {
    	super(executive, "NslFrame Protocols",true);
    	
    	parent = executive;
	moduleType = type;
    	
    	Panel controlPanel = new Panel();    	
    	controlPanel.add(addButton);
    	
    	setLayout(new BorderLayout());
    	add(controlPanel, "North");
    	add(list = new DoubleList(leftStrs, rightStrs), "Center");    	

    	addButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent event) {
    	    	dispose();
		if (moduleType==0) {
    	    	    parent.createOutModuleWithProtocols(list.getRightSideItems());
		} else {
    	    	    parent.createInModuleWithProtocols(list.getRightSideItems());
		}
    	    }
    	});
    	
    	setSize(700, 300);
    	
    }	
    public void actionPerformed(ActionEvent event) {
    	dispose();
    }
    
    
    class DoubleList extends Panel {
	private List left = new List(), right = new List();
	private Panel controlPanel = new ControlPanel(this);
	
	public DoubleList(String[] leftStrs, String[] rightStrs) {
	    GridBagLayout gbl = new GridBagLayout();
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    left.setMultipleMode(true);
	    right.setMultipleMode(true);
	    
	    setLayout(gbl);
	    
	    gbc.fill	= GridBagConstraints.BOTH;
	    gbc.weightx	=1.0;
	    gbc.weighty	=1.0;
	    gbl.setConstraints(left,gbc);
	    
	    gbc.fill	= GridBagConstraints.VERTICAL;
	    gbc.weightx	=0;
	    gbc.weighty	=1.0;
	    gbl.setConstraints(controlPanel,gbc);
	    
	    gbc.fill	= GridBagConstraints.BOTH;
	    gbc.weightx	=1.0;
	    gbc.weighty	=1.0;
	    gbl.setConstraints(right,gbc);
	    
	    add(left);
	    add(controlPanel);
	    add(right);
	    
	    for(int i=0; i<leftStrs.length; ++i)
	    	left.add(leftStrs[i]);

	    for(int i=0; i<rightStrs.length; ++i)
	    	right.add(rightStrs[i]);
	    
	}
	
	public void moveLeftToRight() {
		String[] leftSelected  = left.getSelectedItems();
		int[] 	 leftSelectedIndexes = left.getSelectedIndexes();
		
	        for(int i=0; i<leftSelectedIndexes.length; ++i)
		    left.remove(leftSelectedIndexes[i]-i);
		    
		for(int i=0; i<leftSelected.length; ++i) {		
	    	    right.add(leftSelected[i],i);	
	    	    right.select(i);
	    	}	
	}
	
	public void moveRightToLeft() {
		String[] rightSelected  = right.getSelectedItems();
		int[] 	 rightSelectedIndexes = right.getSelectedIndexes();
		
	        for(int i=0; i<rightSelectedIndexes.length; ++i)
		    right.remove(rightSelectedIndexes[i]-i);
		    
		for(int i=0; i<rightSelected.length; ++i) {		
	    	    left.add(rightSelected[i],i);	
	    	    left.select(i);
	    	}	
	}
	
	public void moveAllRightToLeft() {
		int rightCnt  = right.getItemCount();
		    
		for(int i=0; i<rightCnt; ++i) {		
	    	    left.add(right.getItem(i),i);
	    	    if (right.isIndexSelected(i))
	    	    	left.select(i);
	    	}	
	    	right.removeAll();
	}
		
	public void moveAllLeftToRight() {
		int leftCnt  = left.getItemCount();
		    
		for(int i=0; i<leftCnt; ++i) {		
	    	    right.add(left.getItem(i),i);
	    	    if (left.isIndexSelected(i))
	    	    	right.select(i);
	    	}	
	    	left.removeAll();
	}
	
	public String[] getRightSideItems() {
	    return right.getItems();
	}

	public String[] getRightSideSelectedItems() {
	    return right.getSelectedItems();
	}
	
	public String[] getLeftSideItems() {
	    return left.getItems();
	}

	public String[] getLeftSideSelectedItems() {
	    return left.getSelectedItems();
	}
	
    }
    
    class ControlPanel extends Panel {
	private DoubleList doubleList;
	private Button leftToRight	= new Button(">");    
	private Button allLeftToRight	= new Button(">>");    
	private Button rightToLeft	= new Button("<");    
	private Button allRightToLeft	= new Button("<<");    

	private Font   buttonFont	= new Font("TimeRoman", 
						Font.BOLD, 14);
						
	public ControlPanel(DoubleList dbList) {
	    this.doubleList = dbList;
	    
	    GridBagLayout gbl = new GridBagLayout();
	    GridBagConstraints gbc = new GridBagConstraints();
	    	    
	    setLayout(gbl);
	    
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    gbc.fill	  = GridBagConstraints.HORIZONTAL;
	    gbl.setConstraints(leftToRight,gbc);
	    gbl.setConstraints(allLeftToRight,gbc);
	    gbl.setConstraints(rightToLeft,gbc);
	    gbl.setConstraints(allRightToLeft,gbc);
	
	    add(leftToRight);
	    add(allLeftToRight);
	    add(rightToLeft);
	    add(allRightToLeft);
	    
	    leftToRight.setFont(buttonFont);
	    allLeftToRight.setFont(buttonFont);
	    rightToLeft.setFont(buttonFont);
	    allRightToLeft.setFont(buttonFont);	    

	    leftToRight.addActionListener( new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {
	    	    doubleList.moveLeftToRight();
	    	}
	    });
	    
	    allLeftToRight.addActionListener( new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {
	    	    doubleList.moveAllLeftToRight();
	    	}
	    });
	    
	    rightToLeft.addActionListener( new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {
	    	    doubleList.moveRightToLeft();
	    	}
	    });	
	    
	    allRightToLeft.addActionListener( new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {
	    	    doubleList.moveAllRightToLeft();
	    	}
	    });	
	            
	}	
	
	public Insets getInsets() {
		return new Insets(4,4,4,4);
	}
	
	public void paint(Graphics g) {
	    Dimension size = getSize();
	    g.setColor(Color.black);
	    g.drawRect(0,0,size.width-1,size.height-1);
	    g.setColor(Color.lightGray);
	    g.fill3DRect(1,1,size.width-2,size.height-2,true);
	}
    }
}
