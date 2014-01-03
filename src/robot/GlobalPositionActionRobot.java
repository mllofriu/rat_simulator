package robot;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Arrays;
import java.util.Vector;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import support.Configuration;
import support.IPoints;
import support.Utiles;

/**
 * 
 */

/**
 * @author gtejera
 *
 */
public class GlobalPositionActionRobot implements IRobot {

	private static final int N_LANDMARKS = 4;
	private Vector<Point2D.Double> points;
	private int actualPoint = 0;
	
	public GlobalPositionActionRobot() {
		try {
			points = ((IPoints)Configuration.getObject("GlobalPositionActionRobot.Points")).getDataPoints();
			//points.add(0, HasselmoGridCellLayer.DEFAULT_INITIAL_POINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// devuelve la magnitud de la velocidad del robot
	public double getSpeed() {
		return Utiles.speed(points.elementAt(actualPoint), points.elementAt(actualPoint+1));
	}

	// devuelve la direccion de la cabeza del robot
	public double getHeadDirection() {
		return Utiles.headDirection(points.elementAt(actualPoint), points.elementAt(actualPoint+1));
	}
	
	int porcentaje = -1;
	/* (non-Javadoc)
	 * @see IRobot#doAction(int)
	 */
	@Override
	public void doAction(int grados) {
		// TODO Auto-generated method stub
		
		if (100*actualPoint/points.size()>porcentaje) {
			porcentaje++;
			System.err.println("GlobalPosition::porcentaje de procesamiento de puntos " + porcentaje);
		}
		actualPoint++;
		if ((actualPoint+1)>=points.size()) {
			System.err.println("GlobalPositionActionRobot::Todos los puntos han sido procesados");
			System.exit(0);
		}
			
	}



	/* (non-Javadoc)
	 * @see IRobot#getGlobalCoodinate()
	 */
	@Override
	public Double getGlobalCoodinate() {
		// TODO Auto-generated method stub
		return points.elementAt(actualPoint+1);
	}

	/* (non-Javadoc)
	 * @see IRobot#getGlobalDirection()
	 */
	@Override
	public double getGlobalDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	private static boolean[] affordances = new boolean[IRobot.CANT_ACCIONES];
	static {
	Arrays.fill(affordances, true); // inicializo todos los affordances
	}
	/* (non-Javadoc)
	 * @see IRobot#affordances()
	 */
	@Override
	public boolean[] affordances() {
		return affordances;
	}

	/* (non-Javadoc)
	 * @see IRobot#findFood()
	 */
	@Override
	public boolean findFood() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see IRobot#startRobot()
	 */
	@Override
	public void startRobot() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see IRobot#findLandmarks()
	 */
	@Override
	public Double[] findLandmarks() {
		// TODO Auto-generated method stub
		return new Double[N_LANDMARKS];
	}

	/* (non-Javadoc)
	 * @see IRobot#getColorsLandmarks()
	 */
	@Override
	public Color[] getColorsLandmarks() {
		// TODO Auto-generated method stub
		return new Color[N_LANDMARKS];
	}

	/* (non-Javadoc)
	 * @see IRobot#getPanoramica()
	 */
	@Override
	public BufferedImage getPanoramica() {
		// TODO Auto-generated method stub
		return null;
	}

}
