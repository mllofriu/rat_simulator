import javax.vecmath.Point2d;


public class RobotAldebaranNAO implements IRobot {
	
	private native void avanzar();
	private native void girar(int angulo);
	private native int [][] getImage();

	public boolean[] affordances() {
		// TODO Auto-generated method stub
		return null;
	}

	public void doAction(int grados) {
		// TODO Auto-generated method stub

	}

	public Point2d getGlobalCoodinate() {
		// TODO Auto-generated method stub
		return null;
	}

	public int[][] getPanoramica() {
		// TODO Auto-generated method stub
		return null;
	}

	public void startRobot() {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) {
	    new RobotAldebaranNAO().avanzar();
	}

	// cargo la biblioteca del sistema libNAO.so
	static {
	    System.loadLibrary("NAO");
	}

	@Override
	public void rotateRobot(int actionDegrees) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean findFood() {
		// TODO Auto-generated method stub
		return false;
	}

}
