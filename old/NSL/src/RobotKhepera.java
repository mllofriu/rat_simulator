//import java.io.IOException;
//
//import javax.vecmath.Point2d;
//
//
//public class RobotKhepera implements IRobot {
//	private final double UMBRAL_ACTIVACION_IR = 0.5;
//	private static final String SERVER_IP=Configuration.getString("RobotKhepera.SERVER_IP");
//	private static final int SERVER_PORT=Configuration.getInt("RobotKhepera.SERVER_PORT");
//
//	private KheperaClient robot = new KheperaClient();
//	/*
//	 * El affordance se determina a partir de los sensores IR ubicados alrededor del perimetro del robot
//	 * Los affordances 0 y  se activan cuando el sensor 8 no detecta nada,
//	 * el affordance 1 con elsensor 0, el 2 con el 1, ...
//	 * el 4 con un promedio de los sensores 3 y 4,
//	 * el 5 con el 5, idem 6 y 7
//	 */
//	public boolean[] affordances() {
//		boolean[] result = new boolean[IRobot.CANT_ACCIONES];
//		long irSens[];
//		try {
//			irSens = robot.getIRs();
//			result[0]=(irSens[8]<UMBRAL_ACTIVACION_IR);
//			result[8]=result[0];
//			result[1]=(irSens[0]<UMBRAL_ACTIVACION_IR);
//			result[2]=(irSens[1]<UMBRAL_ACTIVACION_IR);
//			result[3]=(irSens[2]<UMBRAL_ACTIVACION_IR);
//			result[4]=(((irSens[3]+irSens[4])/2)<UMBRAL_ACTIVACION_IR);
//			result[5]=(irSens[5]<UMBRAL_ACTIVACION_IR);
//			result[6]=(irSens[6]<UMBRAL_ACTIVACION_IR);
//			result[7]=(irSens[7]<UMBRAL_ACTIVACION_IR);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	@Override
//	public void doAction(int grados) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public Point2d getGlobalCoodinate() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int[][] getPanoramica() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void startRobot() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void rotateRobot(int actionDegrees) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean findFood() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//}
