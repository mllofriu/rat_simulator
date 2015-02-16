package edu.usf.ratsim.experiment.task;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.geom.Point2D;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.vecmath.Point3f;
import javax.vecmath.Point4f;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.robot.naorobot.protobuf.VisionListener;
import edu.usf.ratsim.robot.romina.Romina;
import edu.usf.ratsim.support.ElementWrapper;
import edu.usf.ratsim.support.Utiles;

public class PlaceRobotByHand implements ExperimentTask {

	private static final String STR_INIT_POS = "initialPosition";
	private static final float EPS_ROT = (float) (Math.PI / 16);
	private static final float EPS_DIST = .1f;
	private Point4f initPos;
	VisionListener vl;

	public PlaceRobotByHand(Point4f initPos) {
		this.initPos = initPos;
	}

	public PlaceRobotByHand(ElementWrapper taskParams,
			Map<String, Point4f> points) {
		String initPosName = taskParams.getChildText(STR_INIT_POS);
		initPos = points.get(initPosName);
		
		vl = VisionListener.getVisionListener();
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		AudioClip ac = null;
		try {
			ac = Applet.newAudioClip(new URL("file:///home/biorob/alarm.wav"));
			ac.play();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// System.out.println("Place robot");
		// boolean ready = false;
		// while (!ready){
		// try {
		// System.out.println("Is the robot in place? [y/n]");
		// ready = System.in.read() == 'y';
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		Romina robot = Romina.getRomina();
		Point3f dest = new Point3f(0, 0, 0);
		float destOrient = (float) (Math.PI / 2);
		
		// Wait for position
		while (! vl.hasPosition())
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		while (vl.getRobotPoint().distance(dest) > EPS_DIST) {
			Point3f p = vl.getRobotPoint();
			float orientToPoint = (float) Math
					.atan2(dest.y - p.y, dest.x - p.x);
			while (Math.abs(Utiles.angleDiff(vl.getRobotOrientation(),
					orientToPoint)) > EPS_ROT / 2) {
				robot.rotate(EPS_ROT 

						* Math.signum(Utiles.angleDiff(
								vl.getRobotOrientation(), orientToPoint)));
				try {
					vl.getRobotOrientation();
					Thread.sleep(200);
					vl.getRobotOrientation();
					Thread.sleep(200);
					vl.getRobotOrientation();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (vl.getRobotPoint().distance(dest) > EPS_DIST) {
				robot.forward();
			}
		}

		while (Math.abs(Utiles.angleDiff(vl.getRobotOrientation(), destOrient)) > EPS_ROT / 4) {
			robot.rotate(EPS_ROT 

					* Math.signum(Utiles.angleDiff(vl.getRobotOrientation(),
							destOrient)));
			try {
				vl.getRobotOrientation();
				Thread.sleep(200);
				vl.getRobotOrientation();
				Thread.sleep(200);
				vl.getRobotOrientation();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// robot.stop();

		if (ac != null)
			ac.stop();

		univ.setRobotPosition(new Point2D.Float(initPos.x, initPos.y),
				initPos.w);
	}
}
