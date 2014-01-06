import javax.vecmath.Point4d;


/*
 * SimulationItem.java
 * Este modulo representa un item simulable 
 * Autor: Gonzalo Tejera
 * Fecha: 11 de agosto de 2010
 */
public class SimulationItem {
	public static final int HABITUATION=0;
	public static final int TRAINING=1;
	public static final int TESTING=2;
		
	private Point4d initialPosition;
	private int repetitions;
	private long time;
	private int type;
	private String name;

	public SimulationItem(String name, int type, Point4d initialPosition, int repetitions, long time) {
		super();
		this.name =name;
		this.type = type;
		this.initialPosition = initialPosition;
		this.repetitions = repetitions;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public Point4d getInitialPosition() {
		return initialPosition;
	}

	public int getRepetitions() {
		return repetitions;
	}

	public long getTime() {
		return time;
	}

	public void decRepetitions() {
		repetitions--;
	}

	public void setTime(long time) {
		this.time=time; 
	}

	
}
