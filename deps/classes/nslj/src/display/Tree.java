/*  SCCS - @(#)Tree.java	1.6 - 09/01/99 - 00:15:50 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: Tree.java,v $
//
// Revision 1.2  1997/05/09 22:30:27  danjie
// add some comments and Log
//
//--------------------------------------


package nslj.src.display;

import java.awt.*;
import java.util.Vector;
//import  TreeNode;
import java.awt.event.*;

/**
 * A Tree class which can display Tree datastructres like heirarchical
 * file systems.
 *
 * @author Sandip Chitale
 */
public
// class Tree      extends Panel
class Tree extends Panel implements AdjustmentListener {
  public static int TREE_HASLINES   = 1;
  int mStyle;
  Vector mVector;
  Vector mDisplayVector;
  TreeNode mSelectedNode;
  public String variableSelect;

  int mMaxWidth = -1;

  // Scrolling related stuff.
  int mVOffset = 0;
  Scrollbar mVScrollbar;

  // constants
  public  static int      CELLSIZE        = 20;
  public  static int      CELLOFFSET      = CELLSIZE/2;
  public  static int      IMAGEMARGIN = 1;
  public  static int      IMAGESIZE       = CELLSIZE - (2 * IMAGEMARGIN);
  public  static int      TRIGGERMARGIN = 6;
  public  static int      TRIGGERSIZE = CELLSIZE - (2 * TRIGGERMARGIN);
  
  // constructors
  public Tree() {
    initialize();
  }

  public Tree(int pStyle) {
    initialize();
    mStyle = pStyle;
  }

  public void initialize() {
    mStyle = 0;
    mVector = new Vector();
    mDisplayVector = new Vector();
    mSelectedNode = null;
    createGui();
  }

  public TreeNode getSelectedNode() {
    return mSelectedNode;
  }

  public void createGui() {
    setLayout(new BorderLayout());
    add("East", mVScrollbar = new Scrollbar(Scrollbar.VERTICAL));
    addMouseListener(new TreeMouseAdapter());
    mVScrollbar.addAdjustmentListener(this);
    // could also use a horizontal scrollbar
  }

  // methods to add a root TreeNodes
  public void addTreeNode(TreeNode pNode) {
    pNode.setLevel(0);
    mVector.addElement(pNode);
    pNode.added(this);
  }

  // methods to add a TreeNodes under another
  public void addTreeNode(TreeNode pSuper, TreeNode pSub) {
    int foundAt = mVector.indexOf(pSuper);
    
    if (foundAt == -1) return;
    if (mVector.indexOf(pSub) != -1) return;

    int lLevel = pSuper.getLevel();
    pSub.setLevel(lLevel + 1);

    TreeNode lNode;
    int lCount = mVector.size();
    int i;
    for (i = foundAt + 1; i < lCount; i++) {
      lNode = (TreeNode) mVector.elementAt(i);
      if (lLevel >= lNode.getLevel()) {
	break;
      }
    }
    if (i == lCount) {
      mVector.addElement(pSub);
    } else {
      mVector.insertElementAt(pSub, i);
    }
    pSub.added(this);
  }

  // remove a TreeNode
  public void removeTreeNode(TreeNode pNode) {
    int foundAt = mVector.indexOf(pNode);

    if (foundAt == -1) {
      return;
    }
    removeSubTreeNodes(pNode);
    mVector.removeElementAt(foundAt);
    pNode.deleted(this);
    if (mSelectedNode == pNode) {
      mSelectedNode = null;
    }
  }

  // remove the subtree of a TreeNode
  public void removeSubTreeNodes(TreeNode pNode) {
    int foundAt = mVector.indexOf(pNode);

    if (foundAt == -1) {
      return;
    }

    int lLevel = pNode.getLevel();

    TreeNode lNode;
    int lNodeLevel;
    int i;
    for (i = foundAt + 1; i < mVector.size();) {
      lNode = (TreeNode) mVector.elementAt(i);
      lNodeLevel = lNode.getLevel();
      if (lLevel == lNodeLevel) {
	break;
      }
      if (lLevel < lNodeLevel) {
	mVector.removeElementAt(i);
	lNode.deleted(this);
	if (mSelectedNode == lNode) {
	  mSelectedNode = null;
	}
      }
    }
  }

  // adjust the Scrollbar
  public void refresh() {
    computeDisplayTree();
    invalidate();
    setBounds(getLocation().x ,getLocation().y
	      ,getSize().width ,getSize().height);
    repaint();
  }

  // compute the display Tree
  protected void computeDisplayTree() {
    int                     lCount;
    TreeNode                lNode;
    int                     lLevel;
    int                     lDepth = 0;

    mDisplayVector.removeAllElements();

    lCount  = mVector.size();
    for (int i = 0; i < lCount; i++) {
      lNode = (TreeNode) mVector.elementAt(i);
      lLevel = lNode.getLevel();
      if (lLevel <= lDepth) {
	mDisplayVector.addElement(lNode);

	lDepth = lLevel;
	if (lNode.isExpandable()) {
	  if (lNode.isExpanded()) {
	    lDepth++;
	  } 
        }
      }
    }
  }

  public void adjustmentValueChanged(AdjustmentEvent evt) {
    switch (evt.getAdjustmentType()) {
    case AdjustmentEvent.BLOCK_DECREMENT :
    case AdjustmentEvent.BLOCK_INCREMENT :
    case AdjustmentEvent.TRACK :
    case AdjustmentEvent.UNIT_INCREMENT :
    case AdjustmentEvent.UNIT_DECREMENT :
      mVOffset = evt.getValue();
      if (mVOffset < 0) 
	mVOffset = 0;
      update(getGraphics());
    }
  }
  // detect selection of a TreeNode
  private class TreeMouseAdapter extends MouseAdapter {
    public void mousePressed(MouseEvent pe) {
      // mStatusbar.setText(pe.toString());
      int px = pe.getX();
      int py = pe.getY();

      int lIndex = (py / CELLSIZE) + mVOffset;
      if (lIndex < mDisplayVector.size()) {
	TreeNode lNode = (TreeNode) mDisplayVector.elementAt(lIndex);
	mSelectedNode = lNode;
	int     lLevel = lNode.getLevel();
	Rectangle lRect = new Rectangle((lLevel * CELLSIZE) + TRIGGERMARGIN
					,((py / CELLSIZE) * CELLSIZE) + TRIGGERMARGIN
					,TRIGGERSIZE ,TRIGGERSIZE);
	
	// modification by Danjie
	String variableName = "."+lNode.toString();
	int ll = lLevel-1;
	TreeNode llNode;

	if(lNode.nodeType==1) {
	  for(int i = lIndex-1; i>=0; i--) {
	    llNode = (TreeNode) mDisplayVector.elementAt(i);
	    if(llNode.getLevel()==ll) {
	      variableName ="."+llNode.toString()+variableName;
	      ll--;
	    }
	  }
	  variableSelect = variableName;
	  //System.out.println(variableName);
	}
    

	if (lRect.contains(px, py)) {
	  if (lNode.isExpandable())
	    {
	      lNode.expandCollapse((Tree) pe.getSource(), pe.getModifiers());
	      refresh();
	    }
	} else {
	  lNode.select((Tree) pe.getSource(), pe.getModifiers());
	  repaint();
	}
      }
    }
  }

  // scrollbar stuff 
  public synchronized void setBounds(int x, int y, int w,int h) {
    super.setBounds(x, y, w, h);
    mVScrollbar.setValues(mVOffset
			  ,(getSize().height/CELLSIZE)
			  ,0
			  ,mDisplayVector.size());
    mVScrollbar.setBlockIncrement(getSize().height/CELLSIZE);
    mVScrollbar.setUnitIncrement(1);

  }

  // paint at correct offset
  public void paint(Graphics pg) {
    int                     lCount;
    TreeNode                lNode;
    int                     lLevel = -1;
    int                     lPrevLevel = -1;
    Image                   lImage;
    int                     lWidth;
    FontMetrics lFM = pg.getFontMetrics();

    mMaxWidth = -1;
    pg.setColor(Color.gray);
    if ((mStyle & TREE_HASLINES) != 0) {
      int j = (getSize().height/CELLSIZE) + 1;
      for (int ii = 0; ii < j; ii++) {
	int i = ii - mVOffset;
	pg.drawRect(0, (i * CELLSIZE), getSize().width, CELLSIZE);
      }
    }
    lCount  = mDisplayVector.size();
    for (int ii = 0; ii < lCount; ii++) {
      int i = ii - mVOffset;
      lNode = (TreeNode) mDisplayVector.elementAt(ii);
      lPrevLevel = lLevel;
      lLevel = lNode.getLevel();
      if (lNode == mSelectedNode) {
	pg.setColor(Color.black);
	pg.fillRect(((lLevel + 2) * CELLSIZE), (i * CELLSIZE)
		    ,lFM.stringWidth(lNode.toString()) + (2 * TRIGGERMARGIN)
		    ,CELLSIZE);
      }

      pg.setColor(Color.gray);
      pg.drawLine((lLevel * CELLSIZE) + (CELLSIZE/2)
		  ,(i * CELLSIZE) + CELLSIZE/2
		  ,((lLevel + 1) * CELLSIZE) + CELLSIZE/2
		  ,(i * CELLSIZE) + CELLSIZE/2);
      if (ii + 1 < lCount) {
	if (((TreeNode) mDisplayVector.elementAt(ii + 1)).getLevel() >= lLevel) {
	  if (((TreeNode) mDisplayVector.elementAt(ii + 1)).getLevel() > lLevel) {
	    pg.drawLine(((lLevel + 1) * CELLSIZE) + (CELLSIZE/2)
			,(i * CELLSIZE) + CELLSIZE/2
			,((lLevel + 1) * CELLSIZE) + CELLSIZE/2
			,((i+1) * CELLSIZE) + CELLSIZE/2);
	  }
	  int j, k;
	  for (j = ii + 1, k = -1; j < lCount; j++) {
	    if (((TreeNode) mDisplayVector.elementAt(j)).getLevel() == lLevel) {
	      k = j;
	      break;
	    }
	    if (((TreeNode) mDisplayVector.elementAt(j)).getLevel() < lLevel) {
	      break;
	    }
	  }
	  if (j < lCount) {
	    if (k != -1) {
	      pg.drawLine((lLevel * CELLSIZE) + (CELLSIZE/2)
			  ,(i * CELLSIZE) + CELLSIZE/2
			  ,((lLevel) * CELLSIZE) + CELLSIZE/2
			  ,((k - mVOffset) * CELLSIZE) + CELLSIZE/2);
	    }
	  }
	}
      }

      if (lNode.isExpandable()) {
	pg.setColor(Color.white);
	pg.fillRect((lLevel * CELLSIZE) + TRIGGERMARGIN
		    ,(i * CELLSIZE) + TRIGGERMARGIN
		    ,TRIGGERSIZE, TRIGGERSIZE);
	pg.setColor(Color.black);
	pg.drawRect((lLevel * CELLSIZE) + TRIGGERMARGIN
		    ,(i * CELLSIZE) + TRIGGERMARGIN
		    ,TRIGGERSIZE, TRIGGERSIZE);
	pg.setColor(Color.black);
	pg.drawLine((lLevel * CELLSIZE) + TRIGGERMARGIN
		    ,(i * CELLSIZE) + TRIGGERMARGIN + (TRIGGERSIZE/2)
		    ,(lLevel * CELLSIZE) + TRIGGERMARGIN + TRIGGERSIZE
		    ,(i * CELLSIZE) + TRIGGERMARGIN + (TRIGGERSIZE/2));
      }
      pg.setColor(Color.black);
      if (lNode.isExpanded()) {
	lImage = lNode.getCollapseImage();
      } else {
	if (lNode.isExpandable()) {
	  pg.drawLine((lLevel * CELLSIZE) + TRIGGERMARGIN + (TRIGGERSIZE/2)
		      ,(i * CELLSIZE) + TRIGGERMARGIN
		      ,(lLevel * CELLSIZE) + TRIGGERMARGIN + (TRIGGERSIZE/2)
		      ,(i * CELLSIZE) + TRIGGERMARGIN + TRIGGERSIZE);
	}
	lImage = lNode.getDefaultImage();
      }
      pg.drawImage(lImage
		   ,((lLevel + 1) * CELLSIZE) + IMAGEMARGIN
		   ,(i * CELLSIZE) + IMAGEMARGIN
		   ,IMAGESIZE, IMAGESIZE, this);
      if (lNode == mSelectedNode) {
	pg.setColor(Color.white);
      } else {
	pg.setColor(Color.black);
      }
      pg.drawString(lNode.toString()
		    ,((lLevel + 2) * CELLSIZE) + TRIGGERMARGIN
		    ,((i + 1) * CELLSIZE) - TRIGGERMARGIN);
      lWidth = ((lLevel + 2) * CELLSIZE) + lFM.stringWidth(lNode.toString()) + (2 * TRIGGERMARGIN);
      if (mMaxWidth < lWidth) {
	mMaxWidth = lWidth;
      }
    }
  }
}
