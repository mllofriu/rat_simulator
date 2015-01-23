package edu.usf.ratsim.robot.romina;

import java.awt.geom.Point2D;
import java.net.Socket;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

import edu.usf.ratsim.robot.naorobot.protobuf.VisionListener;
import edu.usf.ratsim.robot.virtual.VirtualExpUniverse;

public class SLAMUniverse extends VirtualExpUniverse{

	private Romina romina;

	public SLAMUniverse(String mazeResource) {
		super(mazeResource);
	}

	@Override
	public Point3f getRobotPosition() {
		Point3f p = romina.getRobotPoint();
//		System.out.println(p);
		setRobotPosition(new Point2D.Float(p.x, p.z), romina.getRobotOrientation());
		return romina.getRobotPoint();
	}

	@Override
	public Quat4f getRobotOrientation() {
		Point3f p = romina.getRobotPoint();
		setRobotPosition(new Point2D.Float(p.x, p.z), romina.getRobotOrientation());
		return new Quat4f(0, 1, 0, romina.getRobotOrientation());
	}

	@Override
	public float getRobotOrientationAngle() {
		Point3f p = romina.getRobotPoint();
		setRobotPosition(new Point2D.Float(p.x, p.z), romina.getRobotOrientation());
		return romina.getRobotOrientation();
	}

	public void setRominaRobot(Romina romina) {
		this.romina = romina;
	}

	
}
