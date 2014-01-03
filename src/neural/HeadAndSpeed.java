package neural;
/**
 * 
 */

/**
 * @author gtejera
 * Version: 1
 * Fecha: 20 de agosto de 2012.
 */

import nslj.src.lang.NslDinDouble0;
import nslj.src.lang.NslDoutDouble0;
import nslj.src.lang.NslModule;
import java.awt.geom.Point2D;

import robot.*;


public class HeadAndSpeed extends NslModule {
	private static final String DEFAULT_MODULE_NAME = "Direccion de la cabeza y velocidad (nombre por defecto)";
	// la salida de esta capa es la velocidad y la direccion de la cabeza
	public NslDoutDouble0 speed = new NslDoutDouble0("speed", this);
	public NslDoutDouble0 headDirection = new NslDoutDouble0("headDirection", this);

	IRobot robot = RobotFactory.getRobot();

	public HeadAndSpeed(NslModule nslParent) {
		this(DEFAULT_MODULE_NAME, nslParent);
	}
	
	public HeadAndSpeed(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
	}

	public void simRun() {
		speed.set(robot.getSpeed());
		headDirection.set(robot.getHeadDirection());
	}
}
