package nsl.modules;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import robot.*;
import support.Utiles;

import nslj.src.lang.NslDinDouble1;
import nslj.src.lang.NslModule;

/**
 * 
 */

/**
 * @author gtejera
 *
 */
public class CellsViewer extends NslModule {
	private static final String DEFAULT_MODULE_NAME = "Cells viewer (nombre por defecto)";
	private static final String DEFAULT_FILE_NAME = "deafultActivationLog.data";

	public NslDinDouble1 cellsActivationNSL;
	IRobot robot = RobotFactory.getRobot();
	PrintWriter pw = null;
	
	public CellsViewer(NslModule nslParent, int dim) {
		this(DEFAULT_MODULE_NAME, DEFAULT_FILE_NAME, nslParent, dim);
	}

	public CellsViewer(NslModule nslParent, String filename, int dim) {
		this(DEFAULT_MODULE_NAME, filename, nslParent, dim);
	}

	public CellsViewer(String nslName, String filename, NslModule nslParent,  int dim) {
		super(nslName, nslParent);
		cellsActivationNSL = new NslDinDouble1("activationNSL", this, dim);
		System.err.println("CellsViewer::creando archivo de activacion: " + Utiles.getCurrentDirectoryAbsolute()+ File.separatorChar + filename);

		try {			
			pw = new PrintWriter(new FileWriter(filename, false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void simRun() {
		double [] cellsActivation = cellsActivationNSL.get();
		String line = robot.getGlobalCoodinate().x+ "\t" +robot.getGlobalCoodinate().y;
		
		for (int i=0; i<cellsActivation.length;i++)
				line = line + "\t" + cellsActivation[i];
		line = line + "\n";
		//System.err.print("CellsViewer::line: " + line);
		pw.print(line);
	}

}