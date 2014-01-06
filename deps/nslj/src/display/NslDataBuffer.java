/* SCCS  @(#)NslDataBuffer.java	1.6---09/01/99--00:15:43 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

//--------------------------------------
// $Log: NslDataBuffer.java,v $
// Revision 1.4  1997/05/09 22:30:24  danjie
// add some comments and Log
//
//--------------------------------------

package nslj.src.display;

import java.util.*;
import nslj.src.lang.*;
import nslj.src.system.*;


public class NslDataBuffer
{

    public NslDataBuffer(int x, int y, int length)
    {
         indexVector = new Vector(x*y);
         for(int i=0; i<x; i++)
            for(int j=0; j<y; j++)
               {
                  indexVector.addElement(new Vector(length));
               }
         x_dimension = x;
         y_dimension = y;
         bufferLength = length;
         startIndex = 0;
         endIndex = 0;
    }

    /* public NslDataBuffer(int x, int y)      //using default length
    {
         NslDataBuffer(x, y, 1024);
    }
*/

 /*   public void setNewLength(int newLength)
    {
    }
    public void saveData(int i, int j, float data)
    {
    }
    public float getData(int i, int j)
    {
    }
    public int getLength()
    {
    }
    public float getDataAt(int i, int j, int location)
    {
    }*/

    private int bufferLength;
    private int startIndex, endIndex;
    private Vector indexVector;
    private int x_dimension, y_dimension;
}


