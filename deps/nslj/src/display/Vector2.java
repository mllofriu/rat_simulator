/*  SCCS - @(#)Vector2.java	1.5 - 09/01/99 - 00:15:52 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

 
package nslj.src.display;
 
import java.awt.*;
import java.util.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import java.lang.Math;
 
public class Vector2 {

  public Vector2()
  {
	x = 0.0000000; y = 0.0000000;

        //System.out.println("The values are set to "+x+" and "+y);
  } 



  public Vector2(double xval, double yval)
  {
	x = xval; y = yval;

	//System.out.print("X = "+x+" Y= "+y);
  } 

  public double xval()
  {
	return x;
  }

  public double yval()
  {
	return y;
  }

  public void xset(double x1)
  {
	x = x1;
	//System.out.println("xset "+x);
  }

  public void yset(double y1)
  {
	y = y1;
	//System.out.println("yset "+y);
  }

  public void xdiff(double x1,  double x2)
  {
	x = x1 - x2;
  }

  public void ydiff(double y1,  double y2)
  {
	y = y1 - y2;
  }

  public Vector2 V3SubII(Vector2 a, Vector2 b)
  {
	Vector2 c=new Vector2();
	
	c.ydiff( b.xval(), b.yval() );
	c.xdiff( a.xval(), b.xval() );
	return(c);

  }

  public Vector2 V2Add_II(Vector2 a, Vector2 b)
  {
	Vector2 c=new Vector2();
	
	c.ydiff( b.xval(), -1*b.yval() );
	c.xdiff( a.xval(), -1*b.xval() );
	return(c);
  }

  public Vector2 V2Scale_III( double s )
  {
	Vector2 result = new Vector2();

	result.xset( s * xval() );	
	result.yset( s * yval() );	
        
	return (result);
  }
  
  public double V2SquaredLength()
  {
	return ( ( xval() * xval() ) + ( yval() * yval() ) ) ;
  }

  public double V2Length()
  {
 	//System.out.println("Length is "+Math.sqrt(V2SquaredLength()));
	return ( Math.sqrt(V2SquaredLength()) );
  }

  public Vector2 V2_Scale( Vector2 v, double newlen )
  {
	double len = v.V2Length();
	if (len != 0.0) { 
		double temp;

		temp = v.xval();
		v.xset( temp * newlen/len ); 

		temp = v.yval();
                v.yset( temp * newlen/len ); 
        }
	return(v);
  }


/* return the dot product of vectors a and b */
  public double V2_Dot(Vector2 a, Vector2 b)
  {
        return( a.xval() * b.xval() +  a.yval() * b.yval() );
  }
 

 
/* return the distance between two points */
  public double V2_DistanceBetween2Points(Vector2 a, Vector2 b)
  {
        double dx = a.xval()  - b.xval();
        double dy = a.yval() - b.yval();
        return(Math.sqrt((dx*dx)+(dy*dy)));
  }

 
/* return vector sum c = a+b */
  public  Vector2 V2_Add(Vector2 a, Vector2 b, Vector2 c)
  {
	c = new Vector2();
        c.xset(  a.xval() + b.xval() );  
        c.yset(  a.yval() + b.yval() );
        return(c);
  }

  public  void V2Negate()
  {
	x *= -1; y *= -1;	
  }


 
/* normalizes the input vector and returns it */
public void V2Normalize()
{
        double len = V2Length();
        if (len != 0.0) { x /= len;  y /= len; }
}
 

 


  public static Vector2[] Vector2(int num)
  {
//System.out.println("hi 1");
	Vector2 [] dummy =  new Vector2[num];
	
//System.out.println("hi 2");
	return(dummy); 
  }
 

  



  private double x;
  private double y;

}
