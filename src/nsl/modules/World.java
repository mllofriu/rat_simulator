package nsl.modules;
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

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import robot.RobotFactory;
import support.Utiles;

public class World extends NslModule {
	public NslDoutInt0 actionTaken;

	public World(String nslName, NslModule nslParent) {
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
