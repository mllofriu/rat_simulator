/*
 * World.java
 * Este mdulo sirve de interfaz entre el ambiente virtual y el modelo.
 * Autor: Alejandra Barrera
 * Fecha: 13 de mayo de 2005
 * 
 * Gonzalo Tejera 
 * Version 2
 * Fecha: 11 de agosto de 2010
 * Soporte para levantar configuracion desde archivo de simulacion
 */

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Vector;

import javax.management.OperationsException;
import javax.vecmath.Point3d;
import javax.vecmath.Point4d;

import nslj.src.lang.*;

public class World extends NslModule {
	public NslDinInt0 newHeadAngleRat;
	public NslDinInt0 ActionTaken;
	public NslDoutInt0 currentHeadAngle;

	private int headAngle;
	private int action;
	private int actionDegrees;

	private boolean inicio;
	public static int testingTrialNumber;
	public static int trainingTrialNumber;
	private static final String DEFAULT_DIR=Configuration.getString("Log.DIRECTORY");
	private static final String DEFAULT_FILE_NAME=Configuration.getString("WorldFrame.MAZE_FILE");
	private static final String DEFAULT_FILE= System.getProperty("user.dir")+File.separatorChar+DEFAULT_DIR+File.separatorChar+DEFAULT_FILE_NAME+System.currentTimeMillis()+".log";

	public World(String nslName, NslModule nslParent, int d1, int d2,
			int angleInit) {
		super(nslName, nslParent);
		trainingTrialNumber = 0;
		testingTrialNumber = 0;
		action = 4;
		headAngle = angleInit;
		inicio = true;
		newHeadAngleRat = new NslDinInt0("newHeadAngleRat", this);
		ActionTaken = new NslDinInt0("ActionTaken", this);
		currentHeadAngle = new NslDoutInt0("currentHeadAngle", this);
	}

	public void simRun() {
		if (!inicio) {
			headAngle = newHeadAngleRat.get();
			action = ActionTaken.get();
		} else {
			inicio = false;
 			RobotFactory.getRobot().startRobot();
		}
	 
		//pw.println(Rat.simulation.getCurrenTrial()+"\t"+Rat.simItem.getName()+"\t"+RobotFactory.getRobot().getGlobalCoodinate().x+"\t"+RobotFactory.getRobot().getGlobalCoodinate().y+"\t"+"\t"+Drive.getReward()+"\t"+timeElapsed);
		//pw.flush();
		// Convertir la accion en grados...
		actionDegrees = Utiles.acccion2GradosRelative(action);
		RobotFactory.getRobot().rotateRobot(actionDegrees);

		if (Rat.newTrial) {
			if (Rat.simItem.getType()==SimulationItem.TESTING)
				Drive.setReallyHangry(); // do not explore :D
				
 			RobotFactory.getRobot().startRobot();
		} else
			RobotFactory.getRobot().doAction(actionDegrees);

		currentHeadAngle.set(headAngle);
	}

}
