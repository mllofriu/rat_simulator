package neural;
import java.awt.geom.Point2D;

import support.Utiles;

public class GridCell {
	private static final double HD_OFFSET = 120*Math.PI/180.0; //angulo en radianes de desplacamiento entre las direcciones preferidas de las head
	private static final double HD_0 = 0; //angulo en radianes para la direccion preferida 0
	private static final double HD_120 = 120*Math.PI/180.0; //idem direccion preferida 120
	private static final double HD_240 = 240*Math.PI/180.0; //idem direccion preferida 240
	private static final double HD_60 = 60*Math.PI/180.0; //idem direccion preferida
	private static final double HD_180 = 180*Math.PI/180.0; //idem direccion preferida
	private static final double HD_300 = 300*Math.PI/180.0; //idem direccion preferida 
	public static final double DEFAULT_PREFERED_DIRECTION  = HD_60;
	public static final double DEFAULT_PREFERED_DIRECTIONS [] = {HD_60,HD_180,HD_300};
	private static final int CANT_HD_CELL = 3;
	
	public static final double ACTIVATION_BIAS = 1.8;
	public static double  DEFAULT_F_SOMA = 6.42;
	public static final double DEFAULT_A = 0;
	public static final double DEFAULT_B = 0; //Math.PI;

	HDGridCell hdGridCells [] = new HDGridCell[CANT_HD_CELL];

	public GridCell() {
		this(DEFAULT_F_SOMA, DEFAULT_PREFERED_DIRECTIONS, DEFAULT_A, DEFAULT_B);
	}
	
	public GridCell(double fSoma, Point2D.Double initPoint) {
		this(fSoma,DEFAULT_PREFERED_DIRECTION, initPoint);
	}
	
	public GridCell(double fSoma, double preferedDirection, double A,double B) {
		hdGridCells[0] = new HDGridCell(fSoma, preferedDirection, B);
		hdGridCells[1] = new HDGridCell(fSoma, preferedDirection+HD_OFFSET, Math.sqrt(3)*A/2-B/2);
		hdGridCells[2] = new HDGridCell(fSoma, preferedDirection+2*HD_OFFSET, -Math.sqrt(3)*A/2-B/2);
	}

	public GridCell(double fSoma, double preferedDirection, Point2D.Double initPoint) {
		double angle = Utiles.headDirection(new Point2D.Double(), initPoint);
		double speed = Utiles.speed(new Point2D.Double(), initPoint);
		
		//System.out.println("GridCell::constructor::angle: "+ angle+". speed:"+speed);
		
		hdGridCells[0] = new HDGridCell(fSoma, preferedDirection, 0);
		hdGridCells[1] = new HDGridCell(fSoma, preferedDirection+HD_OFFSET, 0);
		hdGridCells[2] = new HDGridCell(fSoma, preferedDirection+2*HD_OFFSET, 0);
		doStep(angle, speed);
	}

	public GridCell(double fSoma, double []preferedDirections, double A,double B) {
		hdGridCells[0] = new HDGridCell(fSoma, preferedDirections[0], B);
		hdGridCells[1] = new HDGridCell(fSoma, preferedDirections[1], Math.sqrt(3)*A/2-B/2);
		hdGridCells[2] = new HDGridCell(fSoma, preferedDirections[2], -Math.sqrt(3)*A/2-B/2);
	}

	// ejecuta un desplazamiento del animal y retorna el potencial de accion
	public double doStep(double headDirection, double speed) {
		double result=1;
		for (int i=0;i<CANT_HD_CELL;i++)
			result=result*hdGridCells[i].doStep(headDirection, speed);
		return Math.abs(result);
	}

	/**
	 * 
	 */
	public void reset() {
		for (int i=0;i<CANT_HD_CELL;i++)
			hdGridCells[i].reset();
	}
}