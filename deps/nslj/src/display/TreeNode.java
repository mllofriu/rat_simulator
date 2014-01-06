/*  SCCS - @(#)TreeNode.java	1.5 - 09/01/99 - 00:15:52 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: TreeNode.java,v $
// Revision 1.2  1997/05/09 22:30:27  danjie
// add some comments and Log
//
//--------------------------------------


package nslj.src.display;

import java.awt.Image;
//import Tree;

/**
 * A TreeNode class which can be used as a base clas to implemnt a node
 * Tree datastructres like heirarchical file systems.
 *
 * Derive a class from TreeNode to override the methods
 * like added, deleted, select and expandCollapse
 *
 * @author Sandip Chitale
 */
public
class TreeNode
{
        Object          mObject;
        Image           mDefaultImage;
        int                     mLevel;
        Image           mCollapseImage;
        boolean         mIsExpanded;
// changed by danjie

        public int nodeType;    // 1 leaf 0 nonleaf

        public
        TreeNode(Object pObject
                        ,Image  pDefaultImage
                        ,Image  pCollapseImage)
        {
                mObject                 = pObject;
                mDefaultImage   = pDefaultImage;
                mLevel                  = 10000;
                mCollapseImage  = pCollapseImage;
                mIsExpanded             = false;
                nodeType=0;
                }

        public
        TreeNode(Object pObject
                        ,Image  pDefaultImage)
        {
                mObject                 = pObject;
                mDefaultImage   = pDefaultImage;
                mLevel                  = 10000;
                mCollapseImage  = null;
                mIsExpanded             = false;
                nodeType=1;
        }

        // derived class should override this
        public
        void
        added(Tree      pFromTree)
        {
        }

        // derived class should override this
        public
        void
        deleted(Tree    pFromTree)
        {
        }

        // derived class should override this
        public
        void
        select(Tree     pFromTree, int pModifiers)
        {
        }

        // derived class should override this
        public
        void
        expandCollapse(Tree     pFromTree, int pModifiers)
        {
                if (isExpandable())
                {
                        toggleExpanded();
                }
        }

        // various accesor fuctions
        public
        boolean
        isExpandable()
        {
                return(!(mCollapseImage == null));
        }

        public
        void
        setExpandable(Image pCollapseImage)
        {
                mCollapseImage  = pCollapseImage;
        }

        public
        void
        unsetExpandable()
        {
                mCollapseImage  = null;
        }

        public
        boolean
        isExpanded()
        {
                return(mIsExpanded);
        }

        public
        void
        setExpanded()
        {
                if (isExpandable())
                {
                        mIsExpanded = true;
                }
        }

        public
        void
        unsetExpanded()
        {
                if (isExpandable())
                {
                        mIsExpanded = false;
                }
        }

        public
        void
        toggleExpanded()
        {
                if (isExpanded())
                {
                        unsetExpanded();
                }
                else
                {
                        setExpanded();
                }
        }

        public
        Object
        getObject()
        {
                return(mObject);
        }

        public
        Image
        getDefaultImage()
        {
                return(mDefaultImage);
        }

        public
        void
        setDefaultImage(Image pDefaultImage)
        {
                mDefaultImage = pDefaultImage;
        }

        public
        int
        getLevel()
        {
                return(mLevel);
        }

        public
        void
        setLevel(int pLevel)
        {
                mLevel = pLevel;
        }

        public
        Image
        getCollapseImage()
        {
                return(mCollapseImage);
        }

        public
        String
        toString()
        {
                return(mObject.toString());
        }

        public
        boolean
        equals(TreeNode pOther)
        {
                return(mObject.equals(pOther.getObject()));
        }
}
