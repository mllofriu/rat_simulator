/*  SCCS - @(#)NslOutFile.java	1.6 - 09/01/99 - 00:15:46 */

/*
 *Copyright(c) 1997 USC Brain Project. email nsl@java.usc.edu
 */

package nslj.src.display;

import java.lang.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import nslj.src.lang.*;
import nslj.src.system.*;


/**
 * The class to implement function of saving file
 * in various file formats
 *
 */



public final class NslOutFile
{

    public static boolean readFromFile(String name, double [][][]values, int start, int time_offset){


             // need to handle file exception here
             try
             {
             int type, mrows, ncols, imagf, namelen, total_time;

		//System.out.println("Reading from file " + name +" [ "+time_offset+" ] ");
                FileInputStream fin = new FileInputStream(name);
                DataInputStream din = new DataInputStream(fin);


	     // Check machine type first
             Properties sysProperty = System.getProperties();
             String osName = sysProperty.getProperty("os.name");
//             System.out.println(osName+"SS");


                 // Big Endian, double precision, numeric data
                 type = 1000;
                 // Using the first variable name as output matrix name
                 namelen = name.length();

 

                type = din.readInt();
                mrows = din.readInt();
                ncols = din.readInt();
                imagf = din.readInt();
//                namelen = din.readInt();

 //               for (int k=0;k<namelen;k++) din.readChar();
//                name = din.readChars();

                //values = new double[mrows][ncols][time_offset];
	  
	    for (total_time = start; total_time < start + time_offset; total_time++) {
// System.out.println(" Time  [last_pos "+ total_time  +" "+ mrows +" "+ ncols+"] ");
  	     for(int i=0; i<mrows; i++) {
               for(int j=0; j<ncols; j++)
		{
		    values[i][j][total_time - start] = din.readDouble();
	//System.out.print(" "+ values[i][j][total_time - start]);
 		}
//  System.out.println(" ");
         } 
     }
             }
             catch(IOException e)
             {
               System.out.println("Error: File Operation Error in NslOutFile.java");
             }

//System.out.println("COMPLETED READING!");

	return true;     
      }




    public static boolean outFromVariable(NslVariable variable, double [][][]values, int start_time, int end_time, int time_offset)
	{

         if ((start_time >=0) && (end_time >=0) && (time_offset >=0)) {

             int type, mrows, ncols, imagf, namelen, total_time, i;

//             double [][][]values;

             String variable_name = variable.info.nslGetName();

         
	     
	     Integer intval = new Integer( time_offset*(1+ (start_time/time_offset)) );


             String name = variable_name + intval.toString();           

//             values = new double[variable.get_x()][variable.get_y()][time_offset];
             
           for (i = time_offset*(start_time/time_offset); 
                  i< time_offset*(end_time/time_offset); 
                  i += time_offset) {
//System.out.println("\n\n\n\n\nHEY!!!!!"+ i + "[ "+ start_time + " " + end_time +"]\n");
             //readFromFile(name, values, i, time_offset*((start_time+time_offset)/time_offset));
             readFromFile(name, values, i, time_offset);
           }
	
             return true;     
          }
	  return false;

	}


    public static boolean outVariable(NslVariable variable, int start_time, int end_time, int offset)
	{
             int type, mrows, ncols, imagf, namelen, total_time;

             String variable_name = variable.info.nslGetName();
	     
	     Integer intval = new Integer( offset );


             String name = variable_name + intval.toString();
             
             // need to handle file exception here
             try
             {
                //FileOutputStream fout = new FileOutputStream("Test.mat");

//System.out.println("Printing at file " + name);
                FileOutputStream fout = new FileOutputStream(name);
                DataOutputStream dout = new DataOutputStream(fout);


	     // Check machine type first
             Properties sysProperty = System.getProperties();
             String osName = sysProperty.getProperty("os.name");
//             System.out.println(osName+"SS");


                 // Big Endian, double precision, numeric data
                 type = 1000;

                 // Get rows and columns
                 mrows = variable.info.getDimension(0);
                 ncols = variable.info.getDimension(1);

                 // Only real data
                 imagf = 0;

                 // Using the first variable name as output matrix name
                 namelen = name.length();

 

                dout.writeInt(type);
                dout.writeInt(mrows);
                dout.writeInt(ncols);
                dout.writeInt(imagf);
  //              dout.writeInt(namelen);
  //              dout.writeChars(name);

	    for (total_time = start_time; total_time <= end_time ; total_time++) {
//System.out.print("Time printed "+ total_time);
// System.out.println(" Time  [last_pos "+ variable.get_last_data_position() + " total time " + variable.get_absolute_last_data_position() +" "+ mrows +" "+ ncols+") "+ (time+time_offset)+ " " +total_time);
  	     for(int i=0; i<mrows; i++) {
               for(int j=0; j<ncols; j++)
		{
		    //dout.writeDouble(((NslVariable)variable_list.elementAt(j)).data[0][0][i]);
		    dout.writeDouble(variable.data[i][j][total_time]);
	//System.out.print(" "+ variable.data[i][j][total_time]);
 		}
   //System.out.println(" ");
         } 
     }//System.out.println();
             }
             catch(IOException e)
             {
               System.out.println("File Operation Error in NslOutFile.java");
             }

	return true;     

	}


/*
    public static boolean outToBezierVariable(Vector2 []CtrlPts)
        {
	     int len = CtrlPts.length;


        }
*/

    public static boolean outToVariable(NslVariable variable, int time_offset)
	{
             int type, mrows, ncols, imagf, namelen, total_time;

             String variable_name = variable.info.nslGetName();
	     
	     Integer intval = new Integer( time );


             String name = variable_name + intval.toString();           
             
             // need to handle file exception here
             try
             {
                //FileOutputStream fout = new FileOutputStream("Test.mat");

//System.out.println("Printing at file " + name);
                FileOutputStream fout = new FileOutputStream(name);
                DataOutputStream dout = new DataOutputStream(fout);


	     // Check machine type first
             Properties sysProperty = System.getProperties();
             String osName = sysProperty.getProperty("os.name");
//             System.out.println(osName+"SS");


                 // Big Endian, double precision, numeric data
                 type = 1000;

                 // Get rows and columns
                 mrows = variable.info.getDimension(0);
                 ncols = variable.info.getDimension(1);

                 // Only real data
                 imagf = 0;

                 // Using the first variable name as output matrix name
                 namelen = name.length();

 

                dout.writeInt(type);
                dout.writeInt(mrows);
                dout.writeInt(ncols);
                dout.writeInt(imagf);
  //              dout.writeInt(namelen);
  //              dout.writeChars(name);
	  
	    //for (total_time = time - time_offset; total_time < time; total_time++) {
	    for (total_time = 0; total_time < time_offset; total_time++) {
//System.out.print("Time printed "+ total_time);
// System.out.println(" Time  [last_pos "+ variable.get_last_data_position() + " total time " + variable.get_absolute_last_data_position() +" "+ mrows +" "+ ncols+") "+ (time+time_offset)+ " " +total_time);
  	     for(int i=0; i<mrows; i++) {
               for(int j=0; j<ncols; j++)
		{
		    //dout.writeDouble(((NslVariable)variable_list.elementAt(j)).data[0][0][i]);
		    dout.writeDouble(variable.data[i][j][total_time]);
	//System.out.print(" "+ variable.data[i][j][total_time]);
 		}
   //System.out.println(" ");
         } 
     }//System.out.println();
             }
             catch(IOException e)
             {
               System.out.println("File Operation Error in NslOutFile.java");
             }

	return true;     

	}

    public static boolean outToMatlab(NslCanvas ndc, String fileName)
	{
             int type, mrows, ncols, imagf, namelen;

             Vector variable_list = ndc.get_variable_list();
             NslVariable variable = ndc.get_display_variable();
             String variable_name = variable.info.nslGetName();
             String name = variable_name+"0";           
//System.out.println("String name = "+ name);
             
             // need to handle file exception here
             try
             {
                //FileOutputStream fout = new FileOutputStream("Test.mat");
                FileOutputStream fout = new FileOutputStream(fileName);
                DataOutputStream dout = new DataOutputStream(fout);


	     // Check machine type first
             Properties sysProperty = System.getProperties();
             String osName = sysProperty.getProperty("os.name");
//             System.out.println(osName+"SS");


                 // Big Endian, double precision, numeric data
                 type = 1000;

                 // Get rows and columns
                 mrows = variable.get_last_data_position();
                 ncols = variable_list.size();

                 // Only real data
                 imagf = 0;

                 // Using the first variable name as output matrix name
                 namelen = name.length();

 

//                dout.writeInt(type);
                dout.writeInt( variable.get_last_data_position() );
//                dout.writeInt(mrows);
//               dout.writeInt(ncols);
		dout.writeInt( variable.info.getDimension(0) );
		dout.writeInt( variable.info.getDimension(1) );


//System.out.println("Time: "+ variable.get_last_data_position() + "Dimensions: "+ variable.get_x() + " " + variable.get_y() );
 //               dout.writeInt(imagf);
  //              dout.writeInt(namelen);
   //             dout.writeChars(name);
	  
  	     //for(int i=0; i<mrows; i++)
  	     for(int i=0; i< variable.info.getDimension(0); i++)
                //for(int j=0; j<ncols; j++)
                for(int j=0; j< variable.info.getDimension(1); j++)
	          for (int k=0;k < variable.get_last_data_position(); k++)
		{
		    //dout.writeDouble(((NslVariable)variable_list.elementAt(j)).data[i][j][k]);
		    dout.writeDouble(variable.data[i][j][k]);
//System.out.println("debug:OutFile: i+" "+j+" "+k+" "variable.data[i][j][k]);
 		}
             }
             catch(IOException e)
             {
                System.out.println("File Operation Error in NslOutFile.java");
             }


	return true;     

	}

    public static boolean outToGnuplot(NslCanvas ndc, String fileName)
	{
                    

	     
        return true;
	}

    public static boolean outToPlotmtv(NslCanvas ndc, String fileName)
	{
              

	     
        return true;
	}

    public static boolean outToPLplot(NslCanvas ndc, String fileName)
	{
              

	     

        return true;
	}


 	static public int time=0;


}
