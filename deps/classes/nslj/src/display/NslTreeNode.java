/*  SCCS - @(#)NslTreeNode.java	1.6 - 09/01/99 - 00:15:51 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NSLTreeNode.java,v $
//
//--------------------------------------


package nslj.src.display;

//import TreeNode;
//import Tree;
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.applet.*;


// Derive a class from TreeNode to override the methods
// like added, deleted, select and expandCollapse
class NslTreeNode extends TreeNode
{
        NslVariableInput mNslVariableInput;
        public
        NslTreeNode(Object po, Image pDefault, NslVariableInput pNslVariableInput)
        {
                super(po, pDefault);
                mNslVariableInput = pNslVariableInput;
        }
        public
        NslTreeNode(Object po, Image pDefault, Image pCollapse, NslVariableInput pNslVariableInput)
        {
                super(po, pDefault, pCollapse);
                mNslVariableInput = pNslVariableInput;
        }

        public
        void
        added(Tree pTree)
        {
                super.added(pTree);
                mNslVariableInput.mTextArea.append(toString() + " added.\n");
        }

        public
        void
        deleted(Tree pTree)
        {
                super.added(pTree);
                mNslVariableInput.mTextArea.append(toString() + " deleted.\n");
        }
        public
        void
        select(Tree pTree, int pModifier)
        {

	//System.out.println("A SELECTION WAS MADE!!!");
                super.select(pTree, pModifier);
                mNslVariableInput.mTextArea.append(toString() + " selected.\n");
        }
        public
        void
        expandCollapse(Tree pTree, int pModifier)
        {
                super.expandCollapse(pTree, pModifier);
                mNslVariableInput.mTextArea.append(toString() + " expanded/collapsed.\n");
        }
}

