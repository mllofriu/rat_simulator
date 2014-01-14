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

import Schemas.Drive;
import robot.RobotFactory;
import simulation.SimulationItem;
import support.Configuration;
import support.Utiles;
import nslj.src.lang.*;

public class World extends NslModule {
	public NslDoutInt0 actionTaken;

	public World(String nslName, NslModule nslParent, int d1, int d2,
			int angleInit) {
		super(nslName, nslParent);
		actionTaken = new NslDoutInt0("ActionTaken", this);

	}

	/**
	 * This simRun commands the robot to perform the selected action by the ActionSelection module.
	 */
	public void simRun() {
		// For visualization purposes
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// Move the robot according to the last selected action taken
		int actionDegrees = Utiles.acccion2GradosRelative(actionTaken.get());
		RobotFactory.getRobot().doAction(actionDegrees);
		
	}

}
