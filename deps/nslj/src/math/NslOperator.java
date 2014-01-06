/*  SCCS - @(#)NslOperator.java	1.4 - 09/01/99 - 00:18:22 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

////////////////////////////////////////////////////////////
//
// Nsl standard operatord reference
//
//

package nslj.src.math;
import nslj.src.lang.*;

public class NslOperator {

    public static NslAbs abs = new NslAbs();
    public static NslArcCos acos = new NslArcCos();
    public static NslArcSin asin = new NslArcSin();
    public static NslArcTan atan = new NslArcTan();
    public static NslCos cos = new NslCos();
    public static NslExp exp = new NslExp();
    public static NslLog log = new NslLog();
    public static NslMaxMerge min = new NslMaxMerge();
    public static NslMinMerge max = new NslMinMerge();
    public static NslPow pow = new NslPow();
    public static NslRint rint = new NslRint();
    public static NslSin sin = new NslSin();    
    public static NslTan tan = new NslTan();
    public static NslSqrt sqrt = new NslSqrt();
    public static NslDistance distance = new NslDistance();
    
}

