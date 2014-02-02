/*  SCCS - @(#)TemporaryBezier.java	1.6 - 09/01/99 - 00:15:51 */

//--------------------------------------
// $Log: TemporaryBezier.java,v $
//
// Revision 1.1  1998/02/02 23:52:39  erhan
// TemporaryBezier & etc
//
// Revision 1.4  1997/05/09 22:30:23  danjie
// add some comments and Log
//
//--------------------------------------


/*
 *Copyright(c)1997 USC Brain Project. email nsl@java.usc.edu
 */


package nslj.src.display;

 
import java.lang.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import nslj.src.lang.*;
import nslj.src.system.*;
import java.lang.Math;
 

public class TemporaryBezier extends NslCanvas
{


 private Vector2 priv=new  Vector2();


 public void TemporaryBezier() 
 {
  
    end = new Vector2();

 }


 public Vector2 V2SubII(Vector2 a, Vector2 b)
  {
        Vector2 c=new Vector2();
 
        c.xset( a.xval() - b.xval() );
        c.yset( a.yval() - b.yval() );
        return(c);
 
  }

 
/*
 * ComputeLeftTangent, ComputeRightTangent, ComputeCenterTangent :
 *Approximate unit tangents at endpoints and "center" of digitized curve
 */
public  Vector2 ComputeLeftTangent(Vector2 [] d, int end)
    /* d;   Digitized points*/
    /* end; Index to "left" end of region */
{
    Vector2     tHat1;
    tHat1 = V2SubII(d[end+1], d[end]);
    //tHat1 = V2Normalize(tHat1);
    //System.out.println("end "+end+" d[end+1] "+d[end+1].xval()+" "+d[end+1].yval()+" d[end] "+d[end].xval()+" "+d[end].yval()+" IN LEFT TANGENT:"+tHat1.xval()+" "+tHat1.yval() );
    tHat1.V2Normalize();
    //--System.out.println("IN LEFT TANGENT:"+tHat1.xval()+" "+tHat1.yval() );
    return tHat1;
}
 
public Vector2 ComputeRightTangent(Vector2 [] d, int end)
   /* d;                      Digitized points            */
   /* end;          Index to "right" end of region */
{
    Vector2     tHat2;
    tHat2 = V2SubII(d[end-1], d[end]);
    //tHat2 = V2Normalize(tHat2);
    tHat2.V2Normalize();
    //--System.out.println("IN RIGHT TANGENT:"+tHat2.xval()+" "+tHat2.yval() );
    return tHat2;
}
 
 
public  Vector2 ComputeCenterTangent(Vector2 [] d, int center)
  /* d;                     Digitized points */

  /*center;          Index to point inside region        */
{
    Vector2     V1, V2, tHatCenter;
 
    V1 = V2SubII(d[center-1], d[center]);
    V2 = V2SubII(d[center], d[center+1]);

    tHatCenter = new Vector2();
    tHatCenter.xset ( (V1.xval()  + V2.xval() )/2.0);
    tHatCenter.yset ( (V1.yval()  + V2.yval() )/2.0);
    tHatCenter.V2Normalize();
    return tHatCenter;
}
 

/*
 *  ChordLengthParameterize :
 *      Assign parameter values to digitized points
 *      using relative distances between points.
 */
public double [] ChordLengthParameterize(Vector2 [] d, int first, int last)
    /*      *d;              Array of digitized points */
    /*     first, last;           Indices defining region     */
{
    int         i;
    double      [] u;                     /*  Parameterization            */
 
 //--System.out.println("ENTERING ChordLengthParametrize");
    u = new double [last-first+1];
/*    u = (double *)malloc((unsigned)(last-first+1) * sizeof(double)); */
 
    u[0] = 0.0;
    for (i = first+1; i <= last; i++) {
                u[i-first] = u[i-first-1] +
                                V2DistanceBetween2Points(d[i], d[i-1]);
    }
 
    for (i = first + 1; i <= last; i++) {
                u[i-first] = u[i-first] / u[last-first];

 //--System.out.print(" "+u[i-first]);
  
    }
 
 
 //--System.out.println("\nEXITING ChordLengthParametrize");
    return(u);
}
 
 
 
/*
 *  ComputeMaxError :
 *      Find the maximum squared distance of digitized points
 *      to fitted curve.
*/
public  double ComputeMaxError(Vector2 [] d, int first, int last, 
	    		      Vector2 [] bezCurve, double [] u, int [] splitPoint)
    /* d;     Array of digitized points   */
    /* first, last;   Indices defining region     */
    /* BezierCurve bezCurve  Fitted Bezier curve         */
    /* u;     Parameterization of points  */
    /* splitPoint;  Point of maximum error      */
{
    int         i;
    double      maxDist;                /*  Maximum error               */
    double      dist;           /*  Current error               */
    Vector2      P;                      /*  Point on curve              */
    Vector2     v;                      /*  Vector from point to curve  */
 
//System.out.println("ENTERING MAX ERROR"+splitPoint[0]);
    splitPoint[0] = (last - first + 1)/2;
  /*  *splitPoint = (last - first + 1)/2; */
    maxDist = 0.0;
    for (i = first + 1; i < last; i++) {
                P = BezierII(3, bezCurve, u[i-first]);
                v = V2SubII(P, d[i]);
                dist = v.V2SquaredLength();
               /*  dist = V2SquaredLength(&v); */
                if (dist >= maxDist) {
                maxDist = dist;
                /* *splitPoint = i; */
                splitPoint[0] = i;
                }
    }
//System.out.println("EXITING MAX ERROR"+splitPoint[0]);
//--System.out.println("splitPoint "+splitPoint[0]+" and maxDist "+maxDist);
    return (maxDist);
}



/*
 *  Reparameterize:
 *      Given set of points and their parameterization, try to find
 *   a better parameterization.
 *
 */
 public double [] Reparameterize(Vector2 [] d, int first, int last, double [] u, 
			      Vector2 [] bezCurve)
    /* d;  Array of digitized points   */
    /* first, last;   Indices defining region     */
    /* u;   Current parameter values    */
    /* bezCurve;    Current fitted curve        */
{
    int         nPts = last-first+1;
    int         i;
    double      [] uPrime = new double [nPts];  /*  New parameter values     */
 

/*    uPrime = (double *)malloc(nPts * sizeof(double)); */
    for (i = first; i <= last; i++) {
           uPrime[i-first] = NewtonRaphsonRootFind(bezCurve, d[i], u[i-first]);

 //-- System.out.print(" "+uPrime[i-first]);
    }
 //-- System.out.println();

    return (uPrime);
}
 



/*
 *  NewtonRaphsonRootFind :
 *      Use Newton-Raphson iteration to find better root.
 */
public double NewtonRaphsonRootFind(Vector2 [] Q, Vector2 P, double u)
    /* Q;      Current fitted curve        */
    /* P;      Digitized point             */
    /* u;      Parameter value for "P"     */
{
    double              numerator, denominator;
    Vector2  [] Q1 = new Vector2[3];
    Vector2  [] Q2= new Vector2[2];   /*  Q' and Q''                  */
    Vector2              Q_u, Q1_u, Q2_u; /*u evaluated at Q, Q', & Q''  */
    double              uPrime;         /*  Improved u                  */
    int                 i;
   
    /* Compute Q(u)     */
    Q_u = BezierII(3, Q, u);
   
    /* Generate control vertices for Q' */
    for (i = 0; i <= 2; i++) {
		double x1 = Q[i+1].xval() - Q[i].xval();
		double y1 = Q[i+1].yval() - Q[i].yval();

		Q1[i] = new Vector2();
                Q1[i].xset (x1 * 3.0);
                Q1[i].yset (y1 * 3.0);
    }
   
    /* Generate control vertices for Q'' */
    for (i = 0; i <= 1; i++) {
		double x1 = Q[i+1].xval() - Q[i].xval();
		double y1 = Q[i+1].yval() - Q[i].yval();

		Q2[i] = new Vector2();
                Q2[i].xset ( x1 * 2.0);
                Q2[i].yset ( y1 * 2.0);
    }
   
    /* Compute Q'(u) and Q''(u) */
    Q1_u = BezierII(2, Q1, u);
    Q2_u = BezierII(1, Q2, u);
   
    /* Compute f(u)/f'(u) */
    numerator = (Q_u.xval() - P.xval()) * (Q1_u.xval()) + (Q_u.yval() - P.yval()) * (Q1_u.yval());
    denominator = (Q1_u.xval()) * (Q1_u.xval()) + (Q1_u.yval()) * (Q1_u.yval()) +
                          (Q_u.xval() - P.xval()) * (Q2_u.xval()) + (Q_u.yval() - P.yval()) * (Q2_u.yval());
   
    /* u = u - f(u)/f'(u) */
    uPrime = u - (numerator/denominator);
    return (uPrime);
}




/*
 *  FitCurve :
 *      Fit a Bezier curve to a set of digitized points
 */
//void FitCurve(Vector2 []d, int nPts, double error, PrintStream ps, int i1, int j1)
void FitCurve(Vector2 []d, int nPts, double error, PrintWriter pw)
  /*     *d;          Array of digitized points   */
  /*         nPts;   Number of digitized points  */
  /*      error;   User-defined error squared  */
 /*   FILE        *fp; */
{
    Vector2     tHat1, tHat2;   /*  Unit tangent vectors at endpoints */

    int ii, jj;

    //-- System.out.println("FITTING A CURVE!!!\n\n\n\n");
    //-- for (ii=0;ii<nPts;ii++)
    //--  System.out.println(" "+d[ii].xval()+" "+d[ii].yval());
    //-- System.out.println("!!!\n\n\n\n");
 
    tHat1 = ComputeLeftTangent(d, 0);
    tHat2 = ComputeRightTangent(d, nPts - 1);
//printf("\n\nThere are %d nPts.\n", nPts);
 //-- System.out.println("There are "+nPts+" nPts.");
 //-- System.out.println("tHat1 "+tHat1.xval()+" "+tHat1.yval()+" tHat2 "+tHat2.xval()+" "+tHat2.yval() );
    
    FitCubic(d, 0, nPts - 1, tHat1, tHat2, error, pw);
   // FitCubic(d, 0, nPts - 1, tHat1, tHat2, error, ps, i1, j1);
}

 
/*
 *  FitCubic :
 *      Fit a Bezier curve to a (sub)set of digitized points
 */
public void FitCubic(Vector2 [] d, int first, int last, 
		     Vector2 tHat1, Vector2 tHat2, double error, PrintWriter pw)
 //Vector2 tHat1, Vector2 tHat2, double error, PrintStream ps, int i1, int j1)
    /*   d;      Array of digitized points */
    /*   first, last;   Indices of first and last pts in region */
    /*   tHat1, tHat2; Unit tangent vectors at endpoints */
    /*    error;   User-defined error squared     */
/*    FILE        *fp; */
{
    Vector2 [] bezCurve; /*Control points of fitted Bezier curve*/
    double  [] u;             /*  Parameter values for point  */
    double  [] uPrime;        /*  Improved parameter values */
    double      maxError;       /*  Maximum fitting error        */
    int         splitPoint;     /*  Point to split point set at  */
    int         nPts;           /*  Number of points in subset  */
    double      iterationError; /*Error below which you try iterating  */
    int         maxIterations = 4; /*  Max times to try iterating  */
    Vector2     tHatCenter;     /* Unit tangent vector at splitPoint */
    int         i;
 
    iterationError = error * error;
    nPts = last - first + 1;


    splitPoint = nPts; /* MAKE SURE THIS IS CORRECT!! */
 
   //printf("Error is %f (nPts = %d -- last %d first %d)\n", error, nPts, last, f irst);
   //-- System.out.println("Error is "+error+" (nPts = "+ nPts+" -- last "+last+" first "+first+")");
 
    /*  Use heuristic if region only has two points in it */
    if (nPts == 2) {
            double dist = V2DistanceBetween2Points(d[last], d[first]) / 3.0;
 
                bezCurve = new Vector2 [4];
		for (i=0;i<4; i++) bezCurve[i] = new Vector2();

/*                bezCurve = (Point2 *)malloc(4 * sizeof(Point2)); */
                bezCurve[0] = d[first];
                bezCurve[3] = d[last];
//-- System.out.println("BEZ CURVE is "+bezCurve[0].xval()+" "+bezCurve[0].yval()+" and "+bezCurve[3].xval()+" "+bezCurve[3].yval());			
//-- System.out.println("BEZ CURVE is "+bezCurve[1].xval()+" "+bezCurve[1].yval()+" and "+bezCurve[2].xval()+" "+bezCurve[2].yval());			
                V2Add(bezCurve[0], V2Scale(tHat1, dist), bezCurve[1]);
                V2Add(bezCurve[3], V2Scale(tHat2, dist), bezCurve[2]);
//                DrawBezierCurve(3, bezCurve, fp);
                //DrawBezierCurve(3, bezCurve, ps, i1, j1);
                DrawBezierCurve(3, bezCurve, pw );
                return;
    }
 
    /*  Parameterize points, and attempt to fit curve */
    u = ChordLengthParameterize(d, first, last);
    //for (i=1;i<5;i++) System.out.print(" "+u[i]);
    //System.out.print("\n");

    bezCurve = GenerateBezier(d, first, last, u, tHat1, tHat2);
 
//    printf("Value %4.3f\n", sizeof(bezCurve)/sizeof(Point2));
 
    /*  Find max deviation of points to fitted curve */

		int [] split = new int[1];
	//-- System.out.println("BEFORE: SPLITPOINT "+splitPoint);
		split[0] = splitPoint;
	//maxError=1;	
    maxError = ComputeMaxError(d, first, last, bezCurve, u, split);
		splitPoint = split[0];
	//-- System.out.println("AFTER: SPLITPOINT "+splitPoint);
    if (maxError < error) {
                //DrawBezierCurve(3, bezCurve, fp);
                //DrawBezierCurve(3, bezCurve, ps, i1, j1);
                DrawBezierCurve(3, bezCurve, pw);
                //free((void *)u);
             //   free(u);
                //free((void *)bezCurve);
             //   free(bezCurve);
//    printf("Returning from maxerror %f\n", maxError);
                return;
    }
    /*  If error not too large, try some reparameterization  */
    /*  and iteration */
    if (maxError < iterationError) {
    //printf("Returning from maxerror %f and iteration error  %f\n", maxError, it erationError);
                for (i = 0; i < maxIterations; i++) {

		/* int [] split = new int[1]; */
	
		split[0] = splitPoint;

                uPrime = Reparameterize(d, first, last, u, bezCurve);
                bezCurve = GenerateBezier(d, first, last, uPrime, tHat1, tHat2)
;
                maxError = ComputeMaxError(d, first, last, bezCurve, uPrime, split);
		splitPoint = split[0];

                if (maxError < error) {
                        //DrawBezierCurve(3, bezCurve, fp);
                        //DrawBezierCurve(3, bezCurve, ps, i1, j1);
                        DrawBezierCurve(3, bezCurve, pw);
                        //free((void *)u);
                      //  free(u);
                        //free((void *)bezCurve);
                      //  free(bezCurve);
                        return;
            }
            //free((void *)u);
          //  free(u);
            u = uPrime;
        }
    }
 
 
    /* Fitting failed -- split at max error point and fit recursively */
    //free((void *)u);
  //  free(u);
    //free((void *)bezCurve);
 //   free(bezCurve);
    tHatCenter = ComputeCenterTangent(d, splitPoint);
   //FitCubic(d, first, splitPoint, tHat1, tHatCenter, error, fp);
//   FitCubic(d, first, splitPoint, tHat1, tHatCenter, error, ps,i1,j1);
   FitCubic(d, first, splitPoint, tHat1, tHatCenter, error, pw);
    //V2Negate(&tHatCenter);
    tHatCenter.V2Negate();
    //FitCubic(d, splitPoint, last, tHatCenter, tHat2, error, fp);
 //   FitCubic(d, splitPoint, last, tHatCenter, tHat2, error, ps,i1,j1);

  if (indexControlPoints < MaxCtrlPts) 
    FitCubic(d, splitPoint, last, tHatCenter, tHat2, error, pw);

System.out.println("EXITING FITCUBIC");
}
 


 
/*
 *  GenerateBezier :
 *  Use least-squares method to find Bezier control points for region.
 *
 */
public Vector2 []  GenerateBezier(Vector2 [] d , int first, int last, 
                                   double uPrime[], 
                                   Vector2 tHat1, Vector2 tHat2)
    /* d:  Array of digitized points   */
    /* first last:  Indices defining region     */
    /* uPrime: Parameter values for region */
    /* tHat1, tHat2:  Unit tangents at endpoints  */
{
    int         i;
    double C[][]  = new double[2][2];     /* Matrix C             */
    double []   X = new double [2];        /* Matrix X                     */
    Vector2 A[][] ;/* Precomputed rhs for eqn      */
    int         nPts;                   /* Number of pts in sub-curve */
    double      det_C0_C1,              /* Determinants of matrices     */
                det_C0_X,
                        det_X_C1;
    double      alpha_l,                /* Alpha values, left and right */
                alpha_r;
    Vector2     tmp;                    /* Utility variable             */
    Vector2 []  bezCurve = new Vector2[4];   /* RETURN bezier curve ctl pts  */


//-- System.out.println("ENTERING GENERATE BEZIER "+tHat1.xval()+" "+tHat1.yval()+" "+tHat2.xval()+" "+tHat2.yval());
 
    for (i=0; i<4; i++) bezCurve[i] = new Vector2();

    
/*    bezCurve = (Vector2 *)malloc(4 * sizeof(Vector2)); */
    nPts = last - first + 1;
 

    A = new Vector2 [nPts][2];
 
    /* Compute the A's  */
    for (i = 0; i < nPts; i++) {
                Vector2 v1 = new Vector2();
                Vector2 v2 = new Vector2();
		
                v1.xset ( tHat1.xval() );
                v1.yset ( tHat1.yval() );
                v2.xset ( tHat2.xval() );
                v2.yset ( tHat2.yval() );
//-- System.out.println("PT: "+i);
//-- System.out.print("that1 length "+tHat1.V2Length()+" "+tHat1.xval()+" "+tHat1.yval() );
//-- System.out.print("that2 length "+tHat2.V2Length()+" "+tHat2.xval()+" "+tHat2.yval() );
     //-- System.out.print("Before: "+v1.xval() +" "+v1.yval() );
                V2Scale(v1, B1(uPrime[i]));
     //-- System.out.println("after : "+v1.xval() +" "+v1.yval() );
     //-- System.out.print("Before: "+v2.xval() +" "+v2.yval() );
                V2Scale(v2,  B2(uPrime[i]));
     //-- System.out.println("after : "+v2.xval() +" "+v2.yval() );

                A[i][0] = new Vector2();
                A[i][0].xset( v1.xval() );
                A[i][0].yset( v1.yval() );
                A[i][1] = new Vector2();
                A[i][1].xset ( v2.xval() );
                A[i][1].yset ( v2.yval() );
	//-- System.out.println("A[i][0] "+A[i][0].xval()+" "+A[i][0].yval());
	//-- System.out.println("A[i][1] "+A[i][1].xval()+" "+A[i][1].yval());
	//-- System.out.println("B1 "+B1(uPrime[i])+" B2 "+B2(uPrime[i]));
    }
 
    /* Create the C and X matrices      */
    C[0][0] = 0.0;
    C[0][1] = 0.0;
    C[1][0] = 0.0;
    C[1][1] = 0.0;
    X[0]    = 0.0;
    X[1]    = 0.0;
 
    for (i = 0; i < nPts; i++) {
        C[0][0] += V2Dot(A[i][0], A[i][0]);
                C[0][1] += V2Dot(A[i][0], A[i][1]);
/*                                      C[1][0] += V2Dot(A[i][0], A[i][1]);*/
                C[1][0] = C[0][1];
                C[1][1] += V2Dot(A[i][1], A[i][1]);
 
                tmp = V2SubII(d[first + i],
                V2AddII(
                  V2ScaleIII(d[first], B0(uPrime[i])),
                        V2AddII(
                                V2ScaleIII(d[first], B1(uPrime[i])),
                                                V2AddII(
                                        V2ScaleIII(d[last], B2(uPrime[i])),
                                        V2ScaleIII(d[last], B3(uPrime[i]))))));
 
 
        X[0] += V2Dot(A[i][0], tmp);
        X[1] += V2Dot(A[i][1], tmp);
    }

	//-- System.out.println("C "+C[0][0]+" "+C[0][1]+" "+C[1][0]+" "+C[1][1]);
	//-- System.out.println("X "+X[0]+" "+X[1]);
 
    /* Compute the determinants of C and X      */
    det_C0_C1 = C[0][0] * C[1][1] - C[1][0] * C[0][1];
    det_C0_X  = C[0][0] * X[1]    - C[0][1] * X[0];
    det_X_C1  = X[0]    * C[1][1] - X[1]    * C[0][1];
 
    /* Finally, derive alpha values     */
    if (det_C0_C1 == 0.0) {
                det_C0_C1 = (C[0][0] * C[1][1]) * 10e-12;
    }
    alpha_l = det_X_C1 / det_C0_C1;
    alpha_r = det_C0_X / det_C0_C1;

//-- System.out.println("\n\n\nalpha_l "+alpha_l+" alpha_r "+alpha_r);
 
 
    /*  If alpha negative, use the Wu/Barsky heuristic (see text) */
 
    if (alpha_l < 0.0 || alpha_r < 0.0) {
	 	int ddd;
                double  dist = V2DistanceBetween2Points(d[last], d[first])/3.0;

		for (ddd=0;ddd<4;ddd++) bezCurve[ddd] = new Vector2();
 
                bezCurve[0] = d[first];
                bezCurve[3] = d[last];
	//-- System.out.println("HERE!!!!");

                V2Add(bezCurve[0], V2Scale(tHat1, dist), bezCurve[1]);
                V2Add(bezCurve[3], V2Scale(tHat2, dist), bezCurve[2]);
                return (bezCurve);
    }
 
    /*  First and last control points of the Bezier curve are */
    /*  positioned exactly at the first and last data points */
    /*  Control points 1 and 2 are positioned an alpha distance out */
    /*  on the tangent vectors, left and right, respectively */
    bezCurve[0] = new Vector2();
    bezCurve[1] = new Vector2();
    bezCurve[2] = new Vector2();
    bezCurve[3] = new Vector2();
    bezCurve[0].xset( d[first].xval() );
    bezCurve[0].yset( d[first].yval() );
    bezCurve[3].xset( d[last].xval() );
    bezCurve[3].yset( d[last].yval() );
//--System.out.println("EDW??? 1");
    V2Add(bezCurve[0], V2Scale(tHat1, alpha_l), bezCurve[1]);

//--System.out.println("EDW??? 2");
    V2Add(bezCurve[3], V2Scale(tHat2, alpha_r), bezCurve[2]);

//--System.out.println("BEZ_CURVE is "+bezCurve[0].xval()+" "+bezCurve[0].yval()+" and "+bezCurve[3].xval()+" "+bezCurve[3].yval());			
//--System.out.println("BEZ_CURVE is "+bezCurve[1].xval()+" "+bezCurve[1].yval()+" and "+bezCurve[2].xval()+" "+bezCurve[2].yval());			

//System.out.println("EXITING GENERATE BEZIER");
    return (bezCurve);
}

 
/*
 *  B0, B1, B2, B3 :
 *      Bezier multipliers
 */
static double B0(double u)
{
    double tmp = 1.0 - u;
    return (tmp * tmp * tmp);
}
 
 
static double B1(double u)
{
    double tmp = 1.0 - u;
    return (3 * u * (tmp * tmp));
}
 
static double B2(double u)
{
    double tmp = 1.0 - u;
    return (3 * u * u * tmp);
}
 
static double B3(double u)
{
    return (u * u * u);
}



 
/*
 *  Bezier :
 *      Evaluate a Bezier curve at a particular parameter value
 *
 */
public  Vector2 BezierII(int degree, Vector2 [] V, double t)
    /*  degree;   The degree of the bezier curve       */
    /*     *V; Array of control points              */
    /*     t;Parametric value to find point for   */
{
    int         i, j;
    Vector2      Q;              /* Point on curve at parameter t        */
    Vector2   [] Vtemp = new Vector2 [degree+1];         /* Local copy of control points         */
 

    /* Copy array       
    Vtemp = (Point2 *)malloc((unsigned)((degree+1)
                                * sizeof (Point2)));
   */
    for (i = 0; i <= degree; i++) {
		Vtemp[i] = new Vector2();
                Vtemp[i].xset( V[i].xval() );
                Vtemp[i].yset( V[i].yval() );
    }
 
    /* Triangle computation     */
    for (i = 1; i <= degree; i++) {
                for (j = 0; j <= degree-i; j++) {
		double x = Vtemp[j].xval();
		double x1 = Vtemp[j+1].xval();
		double y = Vtemp[j].yval();
		double y1 = Vtemp[j+1].yval();
                Vtemp[j].xset (  (1.0 - t) * x + t * x1 );
                Vtemp[j].yset ( (1.0 - t) * y + t * y1 );
//	      //-- System.out.print("("+Vtemp[j].xval()+" "+Vtemp[j].yval()+")" );
                }
    }
 
    Q = Vtemp[0];
	      //-- System.out.print("("+Vtemp[0].xval()+" "+Vtemp[0].yval()+")" );
/*    free((void *)Vtemp); */
    return Q;
}


 
  public Vector2 V2Scale( Vector2 v, double newlen )
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
 
/* return vector sum c = a+b */
  public  Vector2 V2Add(Vector2 a, Vector2 b, Vector2 c)
  {
        //c = new Vector2();
        c.xset(  a.xval() + b.xval() );
        c.yset(  a.yval() + b.yval() );
//--System.out.println("V2Add: "+a.xval()+" + "+b.xval()+" = "+c.xval() );
//--System.out.println("V2Add: "+a.yval()+" + "+b.yval()+" = "+c.yval()+"\n");
        return(c);
  }

 
 
/* return the dot product of vectors a and b */
  public double V2Dot(Vector2 a, Vector2 b)
  {
        return( a.xval() * b.xval() +  a.yval() * b.yval() );
  }
 
  public Vector2 V2AddII(Vector2 a, Vector2 b)
  {
        Vector2 c=new Vector2();
 
        c.xset( a.xval() + b.xval() );
        c.yset( a.yval() + b.yval() );
        return(c);
  }
 
  public Vector2 V2ScaleIII( Vector2 a, double s )
  {
        Vector2 result = new Vector2();
 
        result.xset( s * a.xval() );
        result.yset( s * a.yval() );
 
        return (result);
  }
 
/* return the distance between two points */
  public double V2DistanceBetween2Points(Vector2 a, Vector2 b)
  {
        double dx = a.xval()  - b.xval();
        double dy = a.yval() - b.yval();
//	System.out.println("Distance: "+Math.sqrt((dx*dx)+(dy*dy))+"\n\n");
        return(Math.sqrt((dx*dx)+(dy*dy)));
  }








//public void DrawBezierCurve(int n, Vector2 [] curve, PrintStream ps, int i1, int j1)
public void DrawBezierCurve(int n, Vector2 [] curve, PrintWriter pw)
{
  int i;


   //System.out.println("\n\n\n\n\n\n\n");
  for (i=0; i<n; i++){
   System.out.println("CtrlPt["+i+"] = ("+curve[i].xval()+","+curve[i].yval());
   pw.println(curve[i].xval()+" "+curve[i].yval() );
   System.out.println("Controlpoints "+ indexControlPoints+" size of ControlPoints"+ ControlPoints.length );

//   if (!ControlPoints[ indexControlPoints ]) 
   ControlPoints[ indexControlPoints ] = new Vector2();
   ControlPoints[ indexControlPoints ].xset ( curve[i].xval() ); 
   ControlPoints[ indexControlPoints ].yset ( curve[i].yval() ); 
System.out.println("New point "+ indexControlPoints +" "+ControlPoints[ indexControlPoints ].xval()+ " "+ ControlPoints[ indexControlPoints ].yval() );
/*
*/
   indexControlPoints++;
  }


  if (indexControlPoints >= MaxCtrlPts) {

	NslOutOfBoundsBezier ap = new NslOutOfBoundsBezier();
	
	return;

  }

  if ((curve[n].xval() == end.xval()) &&
      (curve[n].yval() == end.yval()) ) {
   System.out.println("CtrlPt["+n+"] = ("+curve[n].xval()+","+curve[n].yval());
   pw.println(curve[n].xval()+" "+curve[n].yval() );
 //  if (ControlPoints[ indexControlPoints ] == 0) 
   ControlPoints[ indexControlPoints ] = new Vector2();
   ControlPoints[ indexControlPoints ].xset ( curve[i].xval() ); 
   ControlPoints[ indexControlPoints ].yset ( curve[i].yval() ); 
/*
*/

  }
   //System.out.println("\n\n\n\n\n\n\n");

  System.out.println("INDEX IS "+ indexControlPoints);
/*
  for (i=0;i < indexControlPoints; i++)
 System.out.println("CtrlPt["+i+"] = ("+ControlPoints[i].xval()+","+ControlPoints[i].yval());
*/
}

/*
 *  fitting_main:
 *      Example of how to use the curve-fitting code.  Given an array
 *   of points and a tolerance (squared error between points and
 *      fitted curve), the algorithm will generate a piecewise
 *      cubic Bezier representation that approximates the points.
 *      When a cubic is generated, the routine "DrawBezierCurve"
 *      is called, which outputs the Bezier curve just created
 *      (arguments are the degree and the control points, respectively).
 *
 */

public int times(int ti)
{
	 int num = -1;

	 for (int ii=0;ii< ti; ii++)
		num = -1* num;

	 return(num);
}

public void fitting_main(int drawing_time)
{
     //Vector2 d[][] = new Vector2 [x_dimension][y_dimension];
     //Vector2 [] d = new Vector2 [drawing_time];



     Vector2 d[] = new Vector2 [ MaxCtrlPts ];

     end = new Vector2();

     for (int kk=0;kk< MaxCtrlPts ;kk++){
	d[kk]= new Vector2();
	d[kk].xset( times(kk) * kk );
	//d[kk].xset( kk );
	//d[kk].xset( Math.sin(3*((double)kk) * 3.14156/180.0) );
	d[kk].yset(  MaxCtrlPts  * Math.cos(((double)kk) * 3.14156/180.0) );

System.out.println(" X "+ d[kk].xval() +" Y "+d[kk].yval());

        if (kk==( MaxCtrlPts -1)) { 
			end.xset( d[kk].xval() );
        		end.yset( d[kk].yval() );
System.out.println(" END "+ end.xval() +" "+end.yval());
        }
     }


    /* 
     d[0] = new Vector2();
     d[0].xset(0.0); d[0].yset(0.0);
     d[1] = new Vector2();
     //d[1].xset(0.0); d[1].yset(0.5);
     d[1].xset(0.0); d[1].yset(1.0);
     d[2] = new Vector2();
     //d[2].xset(1.1); d[2].yset(1.4);
     d[2].xset(0.); d[2].yset(2.);
     d[3] = new Vector2();
     //d[3].xset(2.1); d[3].yset(1.6);
     d[3].xset(0.); d[3].yset(3.);
     d[4] = new Vector2();
     //d[4].xset(3.2); d[4].yset(1.1);
     d[4].xset(0.); d[4].yset(4.);
     d[5] = new Vector2();
     //d[5].xset(3.3); d[5].yset(1.07);
     d[5].xset(0.); d[5].yset(5.);
     d[6] = new Vector2();
     //d[6].xset(3.6); d[6].yset(0.07);
     d[6].xset(73.4); d[6].yset(-6.0);
     d[7] = new Vector2();
     //d[7].xset(0.8); d[7].yset(0.29);
     d[7].xset(0.); d[7].yset(7.);
     d[8] = new Vector2();
     //d[8].xset(3.85); d[8].yset(0.59);
     d[8].xset(0.); d[8].yset(8.);
     d[9] = new Vector2();
     //d[9].xset(4.0); d[9].yset(0.2);
     d[9].xset(0.0); d[9].yset(9.);
     d[10] = new Vector2();
     //d[10].xset(4.0); d[10].yset(0.0);
     d[10].xset(0.0); d[10].yset(10.0);

*/

/* 
    static Vector2 d[11] = {
        { 0.0, 0.0 },
        { 0.0, 0.5 },
        { 1.1, 1.4 },
        { 2.1, 1.6 },
        { 3.2, 1.1 },
        { 3.3, 1.07 },
        { 3.6, 0.07 },
        { 3.8, 0.29 },
        { 3.85, 0.59 },
        { 4.0, 0.2 },
        { 4.0, 0.0 },
    };
*/

   ControlPoints = new Vector2 [  MaxCtrlPts  ];
   indexControlPoints = 0;
 
   try {
    PrintWriter pw = new PrintWriter ( new FileOutputStream("debug.log"), true );
 
    double      error = 0.1;          /*  Squared error */
/*    FILE        *fp; */
    int pts, i, j,k ;
    float x,y,z;
     pts=  MaxCtrlPts ;
 System.out.print("Fitting curves at time "+drawing_time+"\n\n\n");

			
			
    FitCurve(d, pts, error, pw);		 /* Fit the Bezier curves */
 
 
    //fclose(fp);
   }
   catch(IOException e) { }
}
 


    private float y_max=100, y_min=0;

    private int data_x_size, data_y_size, x_dimension, y_dimension;
    private int last_data_pos, draw_time;
    private float [][][] data;
    private float []xdata;
    private float []ydata;
    private float [][][] tempox;
    private float [][][] tempoy;
    private Color boxColor=Color.black;
    private int  VCount;

    private Vector2 end;

    public static int dummyv;
    public static float []xy;
    public static Vector Xvariable, Yvariable;
    public static NslFrame frame;
    public static String xname;
    public static String yname;

    public Vector2 [] ControlPoints;
    public int indexControlPoints;

    public int MaxCtrlPts = 150 ; // for testing purposes
   
 

}
